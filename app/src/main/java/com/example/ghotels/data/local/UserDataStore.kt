package com.example.ghotels.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.ghotels.data.model.UserDto
import com.google.gson.Gson
import kotlinx.coroutines.flow.first

class UserDataStore(private val context: Context) {

    // Uso un contexto
    private val Context.dataStore by preferencesDataStore(name = "user_prefs")

    companion object {
        private val USER_KEY = stringPreferencesKey("user_json")
    }

    private val gson = Gson()

    suspend fun saveUser(user: UserDto) {
        val json = gson.toJson(user)
        context.dataStore.edit { prefs ->
            prefs[USER_KEY] = json
        }
    }

    suspend fun getUser(): UserDto? {
        val prefs = context.dataStore.data.first()
        val json = prefs[USER_KEY]
        return json?.let { gson.fromJson(it, UserDto::class.java) }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}