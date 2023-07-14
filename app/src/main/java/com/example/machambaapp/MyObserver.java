package com.example.machambaapp;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyObserver implements LifecycleObserver {

    public MyObserver(){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        // Lógica a ser executada quando o LifecycleOwner é criado
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        // Lógica a ser executada quando o LifecycleOwner é iniciado
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        // Lógica a ser executada quando o LifecycleOwner é retomado
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        // Lógica a ser executada quando o LifecycleOwner é pausado
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        // Lógica a ser executada quando o LifecycleOwner é parado
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        // Lógica a ser executada quando o LifecycleOwner é destruído
    }
}
