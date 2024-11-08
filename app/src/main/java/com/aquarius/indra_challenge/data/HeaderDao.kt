package com.aquarius.indra_challenge.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HeaderDao {

    @Query("SELECT * FROM header LIMIT 1")
    fun getHeader(): LiveData<HeaderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeader(header: HeaderEntity)

    @Query("DELETE FROM header")
    suspend fun clearHeaders()
}