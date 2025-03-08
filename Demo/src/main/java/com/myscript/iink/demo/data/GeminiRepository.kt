package com.myscript.iink.demo.data

import com.google.ai.client.generativeai.GenerativeModel
import com.myscript.iink.demo.BuildConfig
import com.myscript.iink.demo.util.Dlog
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiRepository @Inject constructor() {

    private val generativeModel = GenerativeModel(
        // The Gemini 1.5 models are versatile and work with most use cases
        modelName = "gemini-1.5-flash",
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = BuildConfig.apiKey,
    )

    suspend fun generateText(text: String): String {
        Dlog.d("text: $text")

        val prompt = "Given the following input text, " +
                "provide an Inline Text Prediction by naturally continuing the sentence, " +
                "matching its context, tone, and style: " +
                "and ensure that the capitalization of the original text remains unchanged" +
                "\"$text\""
        val response = generativeModel.generateContent(prompt)
        return response.text.orEmpty()
    }
}
