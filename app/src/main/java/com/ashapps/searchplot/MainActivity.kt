package com.ashapps.searchplot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set the year EditText to invisible
        txtYear?.visibility = View.INVISIBLE

        //hide the action bar
        supportActionBar?.hide()

        //plot type movie or series according to the radio buttons choice
        //default type: movie
        var plotType: String = "movie"

        //The listener of a drawableEnd button for clear a TextInputEditText
        txtSearch?.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val textView = v as EditText
                if (event.x >= textView.width - textView.compoundPaddingEnd) {
                    textView.setText("") //Clear a view, example: EditText or TextView
                    return@OnTouchListener true
                }
            }
            false
        })

        //change the visibility of the year editText according to the checkbox (checkBoxYear)
        checkBoxYear?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                txtYear.visibility = View.VISIBLE
            } else {
                txtYear.visibility = View.INVISIBLE
            }
        }

        //change the value of plotType according to the radioGroup (plotTypeRadioGroup)
        plotTypeRadioGroup?.setOnCheckedChangeListener { _, checkedId ->

            when (checkedId) {
                R.id.radioMovie ->                          // do operations specific to this selection
                    plotType = "movie"
                R.id.radioSeries ->                         // do operations specific to this selection
                    plotType = "series"
            }
        }

        //set the search button on click listener
        btnFind.setOnClickListener {
            searchPlot(plotType)
        }
    }


    private fun searchPlot(plotType: String) {
        // create a search object and send it to the single plot activity
        val searchObject: Search
        //val singlePlotActivityIntent = Intent(this, SinglePlotActivity::class.java)
        val listPlotActivityIntent = Intent(this, ListPlotActivity::class.java)
        val plotActivityIntent = Intent(this, PlotActivity::class.java)
        val plotName = txtSearch!!.text.toString().trim()
        searchObject = if (checkBoxYear!!.isChecked) {
            val plotYear = txtYear!!.text.toString()
            Search(plotName, plotYear, plotType)

        } else {
            Search(plotName, plotType)
        }
        //use the intent to pass the search object to the list activity
        listPlotActivityIntent.putExtra("plot_search", searchObject)
        startActivity(listPlotActivityIntent)

    }
}
