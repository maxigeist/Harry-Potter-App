package com.example.harry_potter_app.remote.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_3_4 = object : Migration(3,4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE IF EXISTS FavoriteHouses")
        database.execSQL("DROP TABLE IF EXISTS FavoriteCharacters")
        database.execSQL("DROP TABLE IF EXISTS FavoriteBooks")
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS FavoriteHouses (
                `index` INTEGER PRIMARY KEY NOT NULL
            )
            """.trimIndent()
        )
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS FavoriteCharacters (
                `index` INTEGER PRIMARY KEY NOT NULL
            )
            """.trimIndent()
        )
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS FavoriteBooks (
                `index` INTEGER PRIMARY KEY NOT NULL
            )
            """.trimIndent()
        )
    }
}

@Database(entities = [FavoriteCharacter::class, FavoriteHouse::class, FavoriteBook::class], version = 4)
abstract class HarryPotterDatabase : RoomDatabase() {
    abstract fun favoriteCharacterDao(): FavoriteCharacterDao
    abstract fun favoriteHouseDao(): FavoriteHouseDao
    abstract fun favoriteBookDao(): FavoriteBookDao

    companion object {
        @Volatile
        private var INSTANCE: HarryPotterDatabase? = null

        fun getDatabase(context: Context): HarryPotterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HarryPotterDatabase::class.java,
                    "harry_potter_database"
                )
                        .addMigrations(MIGRATION_3_4)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}