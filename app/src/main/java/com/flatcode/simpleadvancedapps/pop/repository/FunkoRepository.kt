package com.flatcode.simpleadvancedapps.pop.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.flatcode.simpleadvancedapps.pop.db.PopDao
import com.flatcode.simpleadvancedapps.pop.model.PopItem
import com.flatcode.simpleadvancedapps.utils.DATA
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FunkoRepository @Inject constructor(
    @ApplicationContext private val context: Context, private val popDao: PopDao
) {

    val pops: LiveData<List<PopItem>> = popDao.getAllPops()

    suspend fun refreshPops() {
        withContext(Dispatchers.IO) {
            try {
                val jsonString = context.assets.open(DATA.FILE_POP).bufferedReader().use {
                    it.readText()
                }

                val jsonArray = JSONArray(jsonString)
                val listData = mutableListOf<PopItem>()
                val limit = jsonArray.length()

                for (i in 0 until limit) {
                    if (listData.size >= 200) break

                    val item = jsonArray.getJSONObject(i)
                    val img = item.optString("imageName", "")
                    val name = item.optString("title", DATA.UNKNOWN)

                    val seriesJson = item.optJSONArray("series")
                    val seriesList = mutableListOf<String>()
                    if (seriesJson != null) {
                        for (j in 0 until seriesJson.length()) {
                            seriesList.add(seriesJson.getString(j))
                        }
                    }

                    if (img.isEmpty() || img.contains(
                            "placeholder", ignoreCase = true
                        ) || !img.startsWith("http") || name == DATA.UNKNOWN || name.isBlank() || seriesList.any {
                            it.contains("Keychain", true) || it.contains(
                                "Pocket", true
                            ) || it.contains("Pins", true)
                        } || img.contains("Keychains", ignoreCase = true)
                    ) {
                        continue
                    }

                    val series = if (seriesList.isNotEmpty()) {
                        seriesList.joinToString(", ")
                    } else {
                        DATA.UNKNOWN
                    }

                    listData.add(PopItem(i, name, img, series))
                }

                if (listData.isNotEmpty()) {
                    popDao.deleteAllPops()
                    popDao.insertPops(listData)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}