package com.example.jogodeadvinhar

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jogodeadvinhar.NomeDAO
import com.example.jogodeadvinhar.Nome

@Database(
    entities = [Pontuacao::class, Nome::class, Lista::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pontuacaoDAO(): PontuacaoDAO
    abstract fun nomeDAO(): NomeDAO

    abstract fun listaDAO(): ListaDAO
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            } else {
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
    }
}