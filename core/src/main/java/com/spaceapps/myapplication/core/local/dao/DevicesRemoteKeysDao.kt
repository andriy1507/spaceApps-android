package com.spaceapps.myapplication.core.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spaceapps.myapplication.core.models.local.devices.DeviceRemoteKey

@Dao
interface DevicesRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<DeviceRemoteKey>)

    @Query("SELECT * FROM DeviceRemoteKeys WHERE id=:id")
    suspend fun remoteKeysByIdAndQuery(id: Int): DeviceRemoteKey?

    @Query("DELETE FROM DeviceRemoteKeys")
    suspend fun clearAll()
}
