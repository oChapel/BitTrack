package com.example.bittrack.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.bittrack.data.local.dao.TransactionDao
import com.example.bittrack.data.mappers.toDomain
import com.example.bittrack.data.mappers.toEntity
import com.example.bittrack.domain.models.Transaction
import com.example.bittrack.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(
    private val transactionDao: TransactionDao
) : TransactionRepository {

    override fun getPagedTransactions(): Flow<PagingData<Transaction>> {
        return Pager(
            config = PagingConfig(
                pageSize = TRANSACTIONS_PAGE_SIZE,
                initialLoadSize = TRANSACTIONS_PAGE_SIZE,
                prefetchDistance = TRANSACTIONS_PAGE_SIZE / 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { transactionDao.pagingSource() }
        )
            .flow
            .map { pagingData -> pagingData.map { it.toDomain() } }
    }

    override suspend fun saveTransaction(transaction: Transaction) {
        transactionDao.save(transaction.toEntity())
    }

    override fun getBalance(): Flow<Double> = transactionDao.getBalance()

    companion object {
        private const val TRANSACTIONS_PAGE_SIZE = 20
    }
}
