package com.example.draftmakerhelper.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeaknessEntity (
    @PrimaryKey val uId: Int,
    @ColumnInfo(name="card_name") val cardName: String?,
    @ColumnInfo(name="picture_id") val pictureId: String?,
    @ColumnInfo(name="faction") val faction: String?,
    @ColumnInfo(name="core_set") val core_set: String?,
)