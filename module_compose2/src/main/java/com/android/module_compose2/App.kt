package com.android.module_compose2

import android.app.Application
import com.android.module_compose2.loading2.LoadingComponentDefaults
import com.android.module_compose2.loading2.LoadingComponentDefaultsImpl

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        LoadingComponentDefaults.set(LoadingComponentDefaultsImpl())
    }
}