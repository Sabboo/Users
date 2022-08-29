package com.saber.users.test


import com.saber.users.api.RemoteDataSource
import com.saber.users.data.Result
import com.saber.users.db.LocalDataSource
import com.saber.users.repository.UsersRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import com.saber.users.data.User
import kotlinx.coroutines.flow.last


@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryImplTest {

    private lateinit var repository: UsersRepositoryImpl

    private val remoteDataSource: RemoteDataSource = mock()
    private val localDataSource: LocalDataSource = mock()
    private val mockUser: User = mock()

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        repository =
            UsersRepositoryImpl(localDataSource, remoteDataSource, coroutinesRule.testDispatcher)
    }

    @Test
    fun fetchListFromNetworkInCaseOfEmptyCache() = runTest {
        whenever(localDataSource.retrieveItems()).thenReturn(emptyList())
        whenever(remoteDataSource.fetchUsers()).thenReturn(Result.success(emptyList()))

        val results = repository.getUsersFlow().last()

        assertThat(results).isEqualTo(Result.success(emptyList<User>()))
        verify(localDataSource, atLeastOnce()).retrieveItems()
        verify(remoteDataSource, atMost(1)).fetchUsers()
    }

    @Test
    fun fetchListFromCacheIfExist() = runTest {
        whenever(localDataSource.retrieveItems()).thenReturn(listOf(mockUser))

        val results = repository.getUsersFlow().last()

        assertThat(results).isEqualTo(Result.success(listOf(mockUser)))

        verify(localDataSource, atLeastOnce()).retrieveItems()
        verify(remoteDataSource, atMost(0)).fetchUsers()
    }

}