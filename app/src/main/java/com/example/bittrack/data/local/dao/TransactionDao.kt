package com.example.bittrack.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bittrack.data.local.model.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun pagingSource(): PagingSource<Int, TransactionEntity>

    @Query(
        """
            SELECT COALESCE(
                SUM(CASE WHEN type = 'INCOME' THEN amount ELSE -amount END),
                0.0
            ) FROM transactions
        """
    )
    fun getBalance(): Flow<Double>
}
