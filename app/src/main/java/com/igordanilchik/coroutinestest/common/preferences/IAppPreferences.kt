package com.igordanilchik.coroutinestest.common.preferences

/**
 * @author Igor Danilchik
 */
interface IAppPreferences {
    fun putJSON(key: String, value: Any): IAppPreferences
    fun <T> getJSON(key: String, cls: Class<T>): T?
    fun remove(key: String)
    fun getString(key: String, def: String): String
    fun <T> getAllObjects(prefix: String, clazz: Class<T>): List<T>
}
