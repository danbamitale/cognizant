package com.danbamitale.cognizanttest.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Album (
    @PrimaryKey val id: Long,
    val title: String
)
