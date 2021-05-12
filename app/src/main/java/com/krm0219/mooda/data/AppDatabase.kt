package com.krm0219.mooda.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.krm0219.mooda.data.room.Converters
import com.krm0219.mooda.data.room.DiaryDAO
import com.krm0219.mooda.data.room.DiaryData

@Database(entities = [DiaryData::class], version = 8)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun diaryDao(): DiaryDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {

            if (INSTANCE == null) {
                // 여러 스레드가 동시에 접근 불가. 동기적으로 접근
                synchronized(AppDatabase::class) {

                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "test1.db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }

            return INSTANCE
        }
    }
}