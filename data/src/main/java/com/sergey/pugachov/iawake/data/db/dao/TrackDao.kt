package com.sergey.pugachov.iawake.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sergey.pugachov.iawake.data.db.entity.TrackEntity

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tracks: List<TrackEntity>)

    @Query("SELECT * FROM track WHERE programId=:programId")
    suspend fun getTracksByProgramId(programId: String): List<TrackEntity>

}