package com.example.harry_potter_app.remote.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteCharacter::class], version = 1)
abstract class HarryPotterDatabase : RoomDatabase() {
    abstract fun favoriteCharacterDao(): FavoriteCharacterDao

    companion object {
        @Volatile
        private var INSTANCE: HarryPotterDatabase? = null

        fun getDatabase(context: Context): HarryPotterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HarryPotterDatabase::class.java,
                    "unscramble_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}