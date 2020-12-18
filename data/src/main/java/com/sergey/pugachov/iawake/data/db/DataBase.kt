package com.sergey.pugachov.iawake.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sergey.pugachov.iawake.data.db.dao.ProgramDao
import com.sergey.pugachov.iawake.data.db.dao.TrackDao
import com.sergey.pugachov.iawake.data.db.entity.ProgramEntity
import com.sergey.pugachov.iawake.data.db.entity.TrackEntity

@Database(entities = [ProgramEntity::class, TrackEntity::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {

    abstract fun programDao(): ProgramDao

    abstract fun trackDao(): TrackDao

}