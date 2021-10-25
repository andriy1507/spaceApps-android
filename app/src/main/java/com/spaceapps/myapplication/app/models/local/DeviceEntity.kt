package com.spaceapps.myapplication.app.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.spaceapps.myapplication.app.models.remote.profile.Platform

@Entity(tableName = "Devices")
data class DeviceEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val manufacturer: String,
    val model: String,
    val osVersion: String,
    val platform: Platform
)
