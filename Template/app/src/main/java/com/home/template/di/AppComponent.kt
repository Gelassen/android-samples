package com.home.template.di

import android.content.Context
import dagger.Component

@Component(modules = arrayOf(SchedulersModule::class))
interface AppComponent : IComponent {
}