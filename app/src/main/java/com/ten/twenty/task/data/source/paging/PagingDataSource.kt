package com.ten.twenty.task.data.source.paging

import androidx.paging.PagingSource

abstract class PagingDataSource<Response : Any> : PagingSource<Int, Response>()