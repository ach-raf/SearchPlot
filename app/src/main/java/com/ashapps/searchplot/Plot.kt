package com.ashapps.searchplot

import java.io.Serializable

class Plot: Serializable {
    var poster: String? = null
    var title: String? = null
    var pgRating: String? = null
    var duration: String? = null
    var genre: String? = null
    var releaseDate: String? = null
    var country: String? = null
    var imdbRating: String? = null
    var imdbVotes: String? = null
    var fullPlot: String? = null
    var imdbScore: String? = null
        get() {
            field = "$imdbRating/$imdbVotes"
            return field
        }
    var plotInfo: String? = null
        get() {
            field = pgRating + " | " +
                    duration + " | " +
                    genre + " | " +
                    releaseDate + " | " +
                    country
            return field
        }
        private set

    constructor() {}
    constructor(poster: String?, title: String?, pg_rating: String?, duration: String?, genre: String?, release_date: String?, country: String?, imdb_rating: String?, imdb_votes: String?, full_plot: String?) {
        this.poster = poster
        this.title = title
        this.pgRating = pg_rating
        this.duration = duration
        this.genre = genre
        this.releaseDate = release_date
        this.country = country
        this.imdbRating = imdb_rating
        this.imdbVotes = imdb_votes
        this.fullPlot = full_plot
    }
    constructor(title: String?) {this.title = title}


}


