package com.myscript.iink.demo.data.tensonflow

import android.content.Context
import com.myscript.iink.demo.util.Dlog
import dagger.hilt.android.qualifiers.ApplicationContext
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextPredictor @Inject constructor(
    @ApplicationContext context: Context,
    private val tokenizer: Tokenizer,
) {
    private var interpreter: Interpreter

    init {
        interpreter = Interpreter(loadModelFile(context))
    }

    private fun loadModelFile(context: Context): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd("gpt2_model.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, fileDescriptor.startOffset, fileDescriptor.declaredLength)
    }

    fun predictNextWord(inputText: String): String {
        val tokenizedInput = tokenizer.tokenizeInput(inputText)

        val inputBuffer = TensorBuffer.createFixedSize(intArrayOf(1, tokenizedInput.size), DataType.FLOAT32)
        inputBuffer.loadArray(tokenizedInput)

        val outputBuffer = TensorBuffer.createFixedSize(intArrayOf(1, 50), DataType.FLOAT32)

        interpreter.run(inputBuffer.buffer, outputBuffer.buffer)

        val maxIndex = outputBuffer.floatArray
            .withIndex()
            .maxByOrNull { it.value }?.index ?: -1

        if (maxIndex < 0 || maxIndex >= tokenizer.vocabSize()) {
            Dlog.e("Invalid token index: $maxIndex (out of vocabulary range)")
            return "[UNK]"
        }

        val predictedWord = tokenizer.decodeToken(maxIndex)

        return predictedWord
    }
}

