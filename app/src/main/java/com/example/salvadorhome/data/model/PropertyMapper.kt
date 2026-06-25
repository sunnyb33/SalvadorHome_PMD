package com.example.salvadorhome.data.model

import com.example.salvadorhome.features.properties.model.Property
import com.example.salvadorhome.features.properties.model.PropertyCategory

fun PropertyEntity.toProperty(): Property {
    return Property(
        id = id,
        title = title,
        location = location,
        pricePerNight = pricePerNight,
        capacity = capacity,
        category = PropertyCategory.valueOf(category),
        imageRes = imageRes,
        isFavorite = isFavorite
    )
}

fun Property.toEntity(description: String = ""): PropertyEntity {
    return PropertyEntity(
        id = id,
        title = title,
        location = location,
        description = description,
        pricePerNight = pricePerNight,
        capacity = capacity,
        category = category.name,
        imageRes = imageRes,
        isFavorite = isFavorite
    )
}