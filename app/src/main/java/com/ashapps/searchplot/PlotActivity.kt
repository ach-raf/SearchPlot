package com.ashapps.searchplot

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.jacksonandroidnetworking.JacksonParserFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_plot.*
import org.json.JSONException
import org.json.JSONObject

class PlotActivity : AppCompatActivity() {
    private val tag = "PlotActivity"
    private val imdbClient = ImdbWrapper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plot)
        //hide the action bar
        supportActionBar?.hide()

        //activate vertical scroll for full plot textView
        txt_full_plot?.movementMethod = ScrollingMovementMethod()


        val intent = intent
        var imdbExternalLink: Uri? = null

        try {
            imdbExternalLink = intent.data
            assert(imdbExternalLink != null)
            getPlotData(imdbClient.getOmdbUrl(imdbExternalLink.toString()))
            Log.d(tag, "imdbExternalLink : $imdbExternalLink")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(tag, "No external link found : $e.printStackTrace()).toString()")
        }
        // Capture the layout's TextView and set the string as its text
        if (imdbExternalLink == null) {
            val plotSearch = intent.getSerializableExtra("plot_search") as Search
            Log.d(tag, "plot_search : $plotSearch")
            val url = if (plotSearch.plotYear != null) {
                imdbClient.getSinglePlotUrlByTitle(
                    plotSearch.plotName,
                    plotSearch.plotType,
                    plotSearch.plotYear
                )
            } else {
                imdbClient.getSinglePlotUrlByTitle(plotSearch.plotName, plotSearch.plotType, null)
            }
            getPlotData(url)
        }
    }


    private fun loadImage(url: String?, imageView: ImageView?) {
        Picasso.get().load(url).into(imageView)
    }

    private fun plotToView(response: JSONObject) {
        val plot: Plot = imdbClient.imdbResponseToPlot(response)
        loadImage(plot.poster, img_view_plot_poster)
        txt_plot_title!!.text = plot.title
        txt_plot_info!!.text = plot.plotInfo
        txt_imdb_rating!!.text = plot.imdbScore
        txt_full_plot!!.text = plot.fullPlot
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
                    plotToView(response)
                }

                override fun onError(error: ANError) {
                    // handle error
                    error.printStackTrace()
                }
            })
    }


}
