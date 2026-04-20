package pl.projekt1.malin.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ArcgisResponse(
    val features: List<Feature> = emptyList()
)

@Serializable
data class Feature(
    val attributes: Attributes? = null,
    val geometry: Geometry
)

@Serializable
data class Attributes(
    val qr_text: String? = null
)

@Serializable
data class Geometry(
    val x: Double,
    val y: Double
)
