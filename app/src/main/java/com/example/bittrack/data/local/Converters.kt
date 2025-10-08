package com.example.bittrack.data.local

import androidx.room.TypeConverter
import com.example.bittrack.meta.model.TransactionCategory

object Converters {

    @TypeConverter
    @JvmStatic
    fun categoryToString(value: TransactionCategory?): String? = value?.name

    @TypeConverter
    @JvmStatic
    fun stringToCategory(value: String?): TransactionCategory =
        value?.let { TransactionCategory.fromName(it) } ?: TransactionCategory.OTHER
}
