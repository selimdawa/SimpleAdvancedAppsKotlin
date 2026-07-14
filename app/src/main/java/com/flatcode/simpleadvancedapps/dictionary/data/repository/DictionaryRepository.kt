package com.flatcode.simpleadvancedapps.dictionary.data.repository

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.flatcode.simpleadvancedapps.utils.Constants
import com.flatcode.simpleadvancedapps.dictionary.data.local.WordDao
import com.flatcode.simpleadvancedapps.dictionary.data.local.WordEntity
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONArray
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Singleton
class DictionaryRepository @Inject constructor(
    private val wordDao: WordDao, private val requestQueue: RequestQueue
) {

    suspend fun getDefinition(word: String): String {
        val cachedWord = wordDao.getWordDefinition(word)
        if (cachedWord != null) {
            return cachedWord.definition
        }

        val url = "${Constants.DICTIONARY_BASIC_URL}$word?key=${Constants.DICTIONARY_API_KEY}"
        val response = fetchFromNetwork(url)
        val definition = extractDefinitionFromJson(response)

        wordDao.insertWord(WordEntity(word = word, definition = definition))

        return definition
    }

    private suspend fun fetchFromNetwork(url: String): String =
        suspendCancellableCoroutine { continuation ->
            val stringRequest = StringRequest(
                Request.Method.GET,
                url,
                { response -> continuation.resume(response) },
                { error -> continuation.resumeWithException(Exception(error.message)) })
            requestQueue.add(stringRequest)
            continuation.invokeOnCancellation { stringRequest.cancel() }
        }

    private fun extractDefinitionFromJson(response: String): String {
        val jsonArray = JSONArray(response)
        val firstIndex = jsonArray.getJSONObject(0)
        val getShotDefinition = firstIndex.getJSONArray(Constants.SHORT_DEF)
        return getShotDefinition.getString(0)
    }
}
