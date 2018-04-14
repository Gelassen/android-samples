package com.home.template.di

import com.home.template.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(SchedulersModule::class))
interface AppComponent : IComponent {
    fun inject(mainActivity: MainActivity)
}