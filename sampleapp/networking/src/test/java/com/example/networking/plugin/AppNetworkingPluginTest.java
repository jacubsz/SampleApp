package com.example.networking.plugin;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.networking.api.ToDoApi;
import com.example.networking.model.ToDoItem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.subscribers.TestSubscriber;

@RunWith(MockitoJUnitRunner.class)
public class AppNetworkingPluginTest {

    @Mock
    ToDoApi toDoApi;

    @Test
    public void getItemsMapsSingleApiResponseToFlowable() {
        int id = 1;
        String content = "TEST 1";
        boolean checked = false;

        ToDoItem testItem = new ToDoItem(id, content, checked);
        com.github.jacubsz.sampleapp.contract.model.ToDoItem verificationItem = new com.github.jacubsz.sampleapp.contract.model.ToDoItem(id, content, checked);
        when(toDoApi.getItems()).thenReturn(Single.just(Collections.singletonList(testItem)));

        AppNetworkingPlugin networkingPlugin = new AppNetworkingPlugin(toDoApi);
        TestSubscriber<List<com.github.jacubsz.sampleapp.contract.model.ToDoItem>> testSubscriber = networkingPlugin.getItems().test();

        testSubscriber.assertValue(Collections.singletonList(verificationItem));
        testSubscriber.assertNoErrors();
        verify(toDoApi).getItems();
    }

    @Test
    public void updateItemMapsSingleApiResponseToCompletable() {
        int id = 1;
        String content = "TEST 1";
        boolean checked = false;

        ToDoItem verificationItem = new ToDoItem(id, content, checked);
        com.github.jacubsz.sampleapp.contract.model.ToDoItem testItem = new com.github.jacubsz.sampleapp.contract.model.ToDoItem(id, content, checked);
        when(toDoApi.updateItem(id, verificationItem)).thenReturn(Single.just(verificationItem));

        AppNetworkingPlugin networkingPlugin = new AppNetworkingPlugin(toDoApi);
        TestObserver<Void> testSubscriber = networkingPlugin.updateItem(testItem).test();

        testSubscriber.assertNoErrors();
        testSubscriber.assertComplete();
        verify(toDoApi).updateItem(id, verificationItem);
    }

    @Test
    public void insertItemsCallsApiWithObjects() {
        int id1 = 1;
        String content1 = "TEST 1";
        boolean checked1 = false;

        int id2 = 2;
        String content2 = "TEST 2";
        boolean checked2 = true;

        ToDoItem verificationItem1 = new ToDoItem(id1, content1, checked1);
        ToDoItem verificationItem2 = new ToDoItem(id2, content2, checked2);
        com.github.jacubsz.sampleapp.contract.model.ToDoItem testItem1 = new com.github.jacubsz.sampleapp.contract.model.ToDoItem(id1, content1, checked1);
        com.github.jacubsz.sampleapp.contract.model.ToDoItem testItem2 = new com.github.jacubsz.sampleapp.contract.model.ToDoItem(id2, content2, checked2);
        when(toDoApi.saveItem(verificationItem1)).thenReturn(Single.just(verificationItem1));
        when(toDoApi.saveItem(verificationItem2)).thenReturn(Single.just(verificationItem2));

        AppNetworkingPlugin networkingPlugin = new AppNetworkingPlugin(toDoApi);
        TestObserver<Void> testSubscriber = networkingPlugin.insertItems(Arrays.asList(testItem1, testItem2)).test();

        testSubscriber.assertNoErrors();
        testSubscriber.assertComplete();
        verify(toDoApi).saveItem(verificationItem1);
        verify(toDoApi).saveItem(verificationItem2);
    }

    @Test
    public void deletesItemWithApi() {
        int id = 1;
        String content = "TEST 1";
        boolean checked = false;

        com.github.jacubsz.sampleapp.contract.model.ToDoItem testItem = new com.github.jacubsz.sampleapp.contract.model.ToDoItem(id, content, checked);
        when(toDoApi.deleteItem(id)).thenReturn(Completable.complete());

        AppNetworkingPlugin networkingPlugin = new AppNetworkingPlugin(toDoApi);
        TestObserver<Void> testSubscriber = networkingPlugin.deleteItem(testItem).test();

        testSubscriber.assertNoErrors();
        testSubscriber.assertComplete();
        verify(toDoApi).deleteItem(id);
    }
}