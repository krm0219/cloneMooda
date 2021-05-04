package com.krm0219.mooda.data.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    // object
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: T)

    // array of object
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg entity: T)

    @Delete
    fun delete(entity: T)

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(entity: T)
}