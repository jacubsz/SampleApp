package com.github.jacubsz.sampleapp.persistence.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface ToDoItemDao {

    @Query("SELECT * FROM todoitem")
    fun getAll(): Flowable<List<ToDoItem>>

    @Update
    fun update(item: ToDoItem): Completable

    @Insert
    fun insertAll(vararg items: ToDoItem): Completable

    @Delete
    fun delete(item: ToDoItem): Completable

}