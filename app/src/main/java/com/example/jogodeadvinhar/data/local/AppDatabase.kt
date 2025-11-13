package com.example.jogodeadvinhar.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jogodeadvinhar.data.model.Lista
import com.example.jogodeadvinhar.data.model.Nome
import com.example.jogodeadvinhar.data.model.Pontuacao

@Database(
    entities = [Pontuacao::class, Nome::class, Lista::class],

    version = 4
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