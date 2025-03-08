// Copyright @ MyScript. All rights reserved.

package com.myscript.iink.demo

import android.app.Application
import com.myscript.iink.demo.di.DemoModule
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class IInkApplication : Application() {

    companion object {
        lateinit var DemoModule: DemoModule
    }

    override fun onCreate() {
        super.onCreate()
        DemoModule = DemoModule(this)
    }

    override fun onTerminate() {
        DemoModule.close()
        super.onTerminate()
    }
}
