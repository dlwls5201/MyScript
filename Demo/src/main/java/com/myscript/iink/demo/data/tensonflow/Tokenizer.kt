
package com.myscript.iink.demo.data.tensonflow

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Tokenizer @Inject constructor(@ApplicationContext context: Context) {
    private var vocab: Map<String, Int> = emptyMap()
    private var inverseVocab: Map<Int, String> = emptyMap()

    init {
        loadVocab(context)
    }

    private fun loadVocab(context: Context) {
        val inputStream = context.assets.open("gpt2_vocab.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(jsonString)

        val vocabMap = mutableMapOf<String, Int>()
        jsonObject.keys().forEach { key ->
            vocabMap[key] = jsonObject.getInt(key)
        }

        vocab = vocabMap
        inverseVocab = vocabMap.entries.associate { (k, v) -> v to k }
    }

    fun vocabSize(): Int {
        return vocab.size
    }

    fun tokenizeInput(inputText: String): IntArray {
        val tokens = inputText.split(" ").mapNotNull { vocab[it] }.toIntArray()
        return tokens
    }

    fun decodeToken(tokenId: Int): String {
        return inverseVocab[tokenId] ?: "[UNK]"
    }
}
