package com.example.bittrack.data.local

import androidx.room.TypeConverter
import com.example.bittrack.core.model.TransactionCategory
import com.example.bittrack.core.model.TransactionType

object Converters {

    @TypeConverter
    @JvmStatic
    fun categoryToString(value: TransactionCategory): String = value.name

    @TypeConverter
    @JvmStatic
    fun stringToCategory(value: String?): TransactionCategory =
        value?.let { TransactionCategory.fromName(it) } ?: TransactionCategory.OTHER

    @TypeConverter
    @JvmStatic
    fun typeToString(value: TransactionType): String = value.name

    @TypeConverter
    @JvmStatic
    fun stringToType(value: String?): TransactionType =
        value?.let { TransactionType.fromName(it) } ?: TransactionType.EXPENSE

}
