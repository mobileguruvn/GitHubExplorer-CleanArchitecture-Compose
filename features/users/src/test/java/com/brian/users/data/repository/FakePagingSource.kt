package com.brian.users.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState

class FakePagingSource<T : Any>(
    private val items: List<T>,
) : PagingSource<Int, T>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1
        val start = (page - 1) * params.loadSize
        val end = (start + params.loadSize).coerceAtMost(items.size)
        val data = if (start < items.size) items.subList(start, end) else emptyList()

        val nextKey = if (end < items.size) page + 1 else null

        return LoadResult.Page(
            data = data,
            prevKey = if (page == 1) null else page - 1,
            nextKey = nextKey
        )
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? = null
}