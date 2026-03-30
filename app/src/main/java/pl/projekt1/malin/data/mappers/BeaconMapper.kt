package pl.projekt1.malin.data.mappers

import pl.projekt1.malin.data.dto.BeaconDto
import pl.projekt1.malin.domain.model.Beacon

fun BeaconDto.toDomain() = Beacon(
    uid = uid,
    name = name,
    longitude = longitude,
    latitude = latitude,
    floorId = floorId
)