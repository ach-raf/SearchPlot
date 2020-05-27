package com.ashapps.searchplot

import java.io.Serializable

class Search : Serializable {
    var plotName: String
    var plotYear: String? = null
    var plotType: String

    constructor(plot_name: String, plot_year: String?, plot_type: String) {
        this.plotName = plot_name
        this.plotYear = plot_year
        this.plotType = plot_type
    }

    constructor(plot_name: String, plot_type: String) {
        this.plotName = plot_name
        this.plotType = plot_type
    }

}