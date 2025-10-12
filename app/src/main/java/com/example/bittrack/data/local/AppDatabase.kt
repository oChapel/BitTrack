package com.example.bittrack.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bittrack.data.local.dao.TransactionDao
import com.example.bittrack.data.local.model.TransactionEntity

@Database(
    entities = [TransactionEntity::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}
