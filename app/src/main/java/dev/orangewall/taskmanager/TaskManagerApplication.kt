package dev.orangewall.taskmanager

import android.app.Application

class TaskManagerApplication : Application(){
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer()
    }
}