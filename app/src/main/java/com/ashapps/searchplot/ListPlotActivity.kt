package com.ashapps.searchplot

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.jacksonandroidnetworking.JacksonParserFactory
import kotlinx.android.synthetic.main.activity_list_plot.*
import kotlinx.android.synthetic.main.activity_list_plot.loader
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class ListPlotActivity : AppCompatActivity() {
    private val tag = "ListPlotActivity"
    private val imdbClient = ImdbWrapper()
    private var plotItemsList = arrayListOf<PlotItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_plot)
        //hide the action bar
        supportActionBar?.hide()
        recycler_view.layoutManager = LinearLayoutManager(this)


        val intent = intent


        val plotSearch = intent.getSerializableExtra("plot_search") as Search
        val omdbUrl = if (plotSearch.plotYear != null) {
            imdbClient.getMultiplePlotUrlByTitle(
                plotSearch.plotName,
                plotSearch.plotType,
                plotSearch.plotYear
            )
        } else {
            imdbClient.getMultiplePlotUrlByTitle(plotSearch.plotName, plotSearch.plotType, null)
        }
        getPlotData(omdbUrl)

    }

    private fun sendToPlotSingleActivity(selected_plot: Plot) {

    }

    private fun getPlotData(omdb_url: String?) {
        //initialize AndroidNetworking to fetch json data later
        AndroidNetworking.initialize(applicationContext)
        /* set the JacksonParserFactory like below */
        loader.visibility = View.VISIBLE
        AndroidNetworking.setParserFactory(JacksonParserFactory())
        AndroidNetworking.get(omdb_url)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    /* handling the response */
                    loader.visibility = View.GONE
                    try {
                        val rating = response.getString("imdbRating")
                        Log.d(tag, "rating response : $rating")

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    showResults(response)
                }

                override fun onError(error: ANError) {
                    // handle error
                    error.printStackTrace()
                }
            })
    }

    private fun showResults(plotObject: JSONObject) {
        val plotArray = plotObject["Search"] as JSONArray

        var plotList = arrayListOf<PlotItem>()
        lateinit var plotItem: PlotItem
        for (i in 0 until plotArray.length()) {
            val row: JSONObject = plotArray.getJSONObject(i)
            val posterUrl = row.getString("Poster")
            val title = row.getString("Title")
            val year = row.getString("Year")
            plotList.add(PlotItem(posterUrl, title, year.toInt()))
        }
        Log.d(tag, "before : $plotList")
        val sortedList = plotList.sortedByDescending { it.year }

        Log.d(tag, "after : $sortedList")

        recycler_view.adapter = PlotAdapter(ArrayList(sortedList))
        recycler_view.setHasFixedSize(true)

    }

    private fun showPlotList(plotList: MutableList<PlotItem>) {

    }
}
