package com.sergey.pugachov.iawake.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "track",
    foreignKeys = [
        ForeignKey(
            entity = ProgramEntity::class,
            parentColumns = ["id"],
            childColumns = ["programId"],
            onDelete = CASCADE
        )]
)
data class TrackEntity(
    @PrimaryKey
    val id: String,
    val programId: String,
    val title: String,
    val url: String,
    val duration: Long
)