package com.example.networking.api;


import com.example.networking.model.ToDoItem;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ToDoApi {

    @GET("/")
    Single<List<ToDoItem>> getItems();

    @POST("/")
    Single<ToDoItem> saveItem(ToDoItem toDoItem);

    @DELETE("/{id}")
    Completable deleteItem(@Path("id") Integer id);

    @PATCH("/{id}")
    Single<ToDoItem> updateItem(@Path("id") Integer id, ToDoItem updatedItem);

}
