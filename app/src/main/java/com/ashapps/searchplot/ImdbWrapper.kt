package com.ashapps.searchplot

import android.content.Context
import android.net.Uri
import android.util.Log
import org.json.JSONObject

class ImdbWrapper() {
    private val TAG = "ImdbWrapper"
    private val baseUrl = "www.omdbapi.com"
    private val omdbApiKey = "953e6c6f"
    private lateinit var plot: Plot
    private val searchByTitleSingle = "t"
    private val searchByTitleMultiple = "s"
    private val searchById = "i"
    private val searchByYear = "y"

    private fun getImdbId(imdb_url: String): String {
        return imdb_url.split("/").toTypedArray()[4]
    }

    private fun getDataUrlById(id: String?): String {
        return getPlotDetailsUrl(searchById, id, null, null)
    }
    fun getOmdbUrl(imdb_url:String): String{
        return getDataUrlById(getImdbId(imdb_url))
    }

    fun getSinglePlotUrlByTitle(title: String?, type: String?, year: String?): String {
        return getPlotDetailsUrl(searchByTitleSingle, title, type, year)
    }
    fun getMultiplePlotUrlByTitle(title: String?, type: String?, year: String?): String {
        return getPlotDetailsUrl(searchByTitleMultiple, title, type, year)
    }

    private fun getPlotDetailsUrl(
        query: String?, search: String?,
        type: String?, year: String?
    ): String {
        //query can be i for id, t for title s for search
        //https://www.omdbapi.com/ parameters for more info.

        val urlBuilder = Uri.Builder()
        urlBuilder.scheme("https")
            .authority(baseUrl)
            .appendPath("")
            .appendQueryParameter(query, search)
            .appendQueryParameter("type", type)

        if (year != null) {
            urlBuilder.appendQueryParameter(searchByYear, year)
        }

        urlBuilder.appendQueryParameter("apikey", omdbApiKey)
        return urlBuilder.build().toString()
    }

    fun imdbResponseToPlot(response: JSONObject): Plot {
        plot = Plot()
        plot.poster = response.getString("Poster")
        plot.title = response.getString("Title")
        plot.pgRating = response.getString("Rated")
        plot.duration = response.getString("Runtime")
        plot.genre = response.getString("Genre")
        plot.releaseDate = response.getString("Released")
        plot.country = response.getString("Country")
        plot.imdbRating = response.getString("imdbRating")
        plot.imdbVotes = response.getString("imdbVotes")
        plot.fullPlot = response.getString("Plot")
        return plot
    }


}