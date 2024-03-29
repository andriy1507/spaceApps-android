package com.spaceapps.myapplication.core.models.local.devices

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DeviceRemoteKeys")
data class DeviceRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "nextKey")
    val nextKey: Int?,
    @ColumnInfo(name = "prevKey")
    val prevKey: Int?
)
