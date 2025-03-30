package com.brian.test.testutils

import androidx.paging.PagingData
import androidx.paging.PagingDataDiffer
import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher


@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T : Any> collectPagingData(
    pagingData: PagingData<T>,
    diffCallback: DiffUtil.ItemCallback<T> = defaultDiffCallback()
): List<T> {
    val items = mutableListOf<T>()
    val differ = object : PagingDataDiffer<T>(diffCallback, StandardTestDispatcher()) {
        override suspend fun presentNewList(
            previousList: NullPaddedList<T>,
            newList: NullPaddedList<T>,
            lastAccessedIndex: Int,
            onListPresentable: () -> Unit
        ) {
            items.clear()
            for (i in 0 until newList.size) {
                newList[i]?.let { items.add(it) }
            }
            onListPresentable()
        }
    }

    differ.collectFrom(pagingData, StandardTestDispatcher())
    return items
}

private fun <T : Any> defaultDiffCallback() = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
}
