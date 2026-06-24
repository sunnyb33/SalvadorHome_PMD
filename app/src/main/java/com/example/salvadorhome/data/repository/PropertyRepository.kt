package com.example.salvadorhome.data.repository

import com.example.salvadorhome.data.local.dao.PropertyDao
import com.example.salvadorhome.data.model.PropertyEntity

class PropertyRepository(
    private val propertyDao: PropertyDao
) {

    suspend fun getProperties(): List<PropertyEntity> {
        return propertyDao.getAllProperties()
    }

    suspend fun addProperty(property: PropertyEntity) {
        propertyDao.insertProperty(property)
    }
}