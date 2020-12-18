package com.sergey.pugachov.iawake.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "program")
data class ProgramEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val imageUrl: String,
    val thumbnailUrl: String
)