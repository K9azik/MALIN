package pl.projekt1.malin.data.network

import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Query

@Serializable
data class ArcgisResponse(val features: List<Feature> = emptyList())

@Serializable
data class Feature(val geometry: Geometry)

@Serializable
data class Geometry(val x: Double, val y: Double)

interface ArcgisService {
    @GET("server/rest/services/SION2_Geoopisy/sion_topo_qrcode/MapServer/0/query?outSR=4326&f=json&outFields=*&returnGeometry=true")
    suspend fun fetchLocation(
        @Query("where") where: String
    ): ArcgisResponse
}
