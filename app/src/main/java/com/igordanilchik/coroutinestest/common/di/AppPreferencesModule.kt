package com.igordanilchik.coroutinestest.common.di

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.igordanilchik.coroutinestest.common.preferences.AppPreferences
import com.igordanilchik.coroutinestest.common.preferences.IAppPreferences
import dagger.Module
import dagger.Provides

/**
 * @author Igor Danilchik
 */
@Module
class AppPreferencesModule {

    @Provides
    fun preferences(context: Context, objectMapper: ObjectMapper): IAppPreferences = AppPreferences(context, objectMapper)

}