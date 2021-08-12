package com.example.networking.plugin;

import androidx.annotation.NonNull;

import com.example.networking.api.ToDoApi;
import com.github.jacubsz.sampleapp.contract.datasource.ToDoItemsDataSource;
import com.github.jacubsz.sampleapp.contract.model.ToDoItem;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class AppNetworkingPlugin implements ToDoItemsDataSource {

    private ToDoApi api;

    @Inject
    public AppNetworkingPlugin(ToDoApi api) {
        this.api = api;
    }

    @NonNull
    @Override
    public Flowable<List<ToDoItem>> getItems() {
        return api.getItems()
                .map(items -> {
                    Stream<ToDoItem> itemsStream = items.stream()
                            .map(item -> new ToDoItem(item.getId(), item.getContent(), item.getChecked()));
                    return itemsStream.collect(Collectors.toList());
                }
        ).toFlowable();
    }

    @NonNull
    @Override
    public Completable updateItem(@NonNull ToDoItem item) {
        return api
                .updateItem(item.getId(), new com.example.networking.model.ToDoItem(item.getId(), item.getContent(), item.getChecked()))
                .ignoreElement();
    }

    @NonNull
    @Override
    public Completable insertItems(@NonNull List<ToDoItem> items) {
        Stream<Completable> completables = items.stream().map(item -> api.saveItem(new com.example.networking.model.ToDoItem(item.getId(), item.getContent(), item.getChecked())).ignoreElement());
        return Completable.concat(completables.collect(Collectors.toList()));
    }

    @NonNull
    @Override
    public Completable deleteItem(@NonNull ToDoItem item) {
        return api.deleteItem(item.getId());
    }
}
