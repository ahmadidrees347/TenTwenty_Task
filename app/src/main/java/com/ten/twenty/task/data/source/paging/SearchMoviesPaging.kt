package com.ten.twenty.task.data.source.paging

import androidx.paging.PagingState
import com.ten.twenty.task.data.source.MoviesAPI
import com.ten.twenty.task.data.source.dto.MovieResults

class SearchMoviesPaging(
    private val movieService: MoviesAPI,
    private val query: String
) :
    PagingDataSource<MovieResults>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResults> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = movieService.searchMoviesByQuery(page, query)
            val results = response.body()?.results ?: emptyList()
            LoadResult.Page(
                data = results,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1),
                nextKey = if (results.isEmpty()) null else page.plus(1)
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieResults>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}