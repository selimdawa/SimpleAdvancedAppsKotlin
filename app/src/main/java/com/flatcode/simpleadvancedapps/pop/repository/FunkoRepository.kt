package com.flatcode.simpleadvancedapps.pop.repository

import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.pop.models.PopItem
import org.jsoup.Jsoup
import java.io.IOException

class FunkoRepository {

    fun getFunkoPops(): MutableList<PopItem> {
        val listData = mutableListOf<PopItem>()
        try {
            val url = DATA.BASE_URL_POP
            val doc = Jsoup.connect(url).get()
            val pops = doc.select(".wikitable:first-of-type tr")

            for (i: Int in 1 until 200) {
                val name = pops.select("th:nth-last-of-type(1)")
                    .eq(i)
                    .text()

                val imgUrl = pops.select("td:nth-of-type(1)").eq(i - 1)

                val img = if (imgUrl.toString() == "<td> </td>") {
                    DATA.IMAGE_POP
                } else {
                    imgUrl.select("a").attr("href")
                }

                val series = pops.select("td:nth-of-type(4)")
                    .eq(i - 1)
                    .text()

                listData.add(PopItem(i, name, img, series))
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return listData
    }
}