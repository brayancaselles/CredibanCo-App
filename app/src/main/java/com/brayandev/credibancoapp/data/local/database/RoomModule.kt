package com.brayandev.credibancoapp.data.local.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val TRANSACTION_DATA_BASE = "transaction_database"

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, TransactionDataBase::class.java, TRANSACTION_DATA_BASE)
            .build()

    @Provides
    @Singleton
    fun provideTransactionDao(dataBase: TransactionDataBase) = dataBase.transactionDao()
}
