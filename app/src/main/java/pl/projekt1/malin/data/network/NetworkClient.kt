package pl.projekt1.malin.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://arcgis.cenagis.edu.pl/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val arcgisService: ArcgisService = retrofit.create(ArcgisService::class.java)
}
