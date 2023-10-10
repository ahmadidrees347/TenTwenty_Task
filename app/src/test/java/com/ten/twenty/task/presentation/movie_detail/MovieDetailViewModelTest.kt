package com.ten.twenty.task.presentation.movie_detail

import androidx.paging.PagingData
import app.cash.turbine.test
import com.ten.twenty.task.data.network.ApiResponseRequest
import com.ten.twenty.task.data.network.ResponseState
import com.ten.twenty.task.data.repository.MoviesRepoImpl
import com.ten.twenty.task.data.source.MoviesAPI
import com.ten.twenty.task.data.source.dto.MovieDetailModel
import com.ten.twenty.task.data.source.dto.MovieResults
import com.ten.twenty.task.domain.usecase.MovieDetailUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailViewModelTest {

    @Mock
    private lateinit var apiHelper: MoviesAPI

    @Mock
    lateinit var movieDetailUseCase: MovieDetailUseCase

    @Mock
    lateinit var movieDetailViewModel: MovieDetailViewModel

    @Mock
    lateinit var repository: MoviesRepoImpl

    @Mock
    lateinit var apiResponseRequest: ApiResponseRequest

    private val testDispatcher = StandardTestDispatcher()

    private val movieID = 678512

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        repository = MoviesRepoImpl(apiHelper)
        movieDetailUseCase = MovieDetailUseCase(repository)
        movieDetailViewModel = MovieDetailViewModel(movieDetailUseCase)
    }

    @Test
    fun getAllMovies() = runTest {

        val flow = flow {
            try {
                emit(ResponseState.Loading())
                emit(ResponseState.Success(repository.getMovieDetailById(movieID)))
            } catch (e: HttpException) {
                emit(ResponseState.Error(e.localizedMessage ?: "An Unexpected Error"))
            } catch (e: IOException) {
                emit(ResponseState.Error("Internet Error"))
            }
        }
        val movieItem = flow.toList().first().data
        Mockito.`when`(movieDetailUseCase(movieID)).thenReturn(flow)

//        movieDetailViewModel.getMovieDetail(1)

        Assert.assertEquals(MovieDetailModel(movieID, "", ""), movieItem)
    }

    @Test
    fun `test for get movie by id`() = runBlocking {
        val retroResponse = getDummyMovieValue()
        val response = Response.success(retroResponse)
        val channel = Channel<Response<MovieDetailModel>>()
        val flow = channel.consumeAsFlow()
        Mockito.`when`(repository.getMovieDetailById(movieID))
            .thenReturn(flow.toList().first().body())

        launch {
            channel.send(response)
        }
        movieDetailViewModel.getMovieDetail(678512)
        Assert.assertEquals(1, movieDetailViewModel.moviesDetailData.value.movieModel)
    }

    private fun getDummyMovieValue() = MovieDetailModel(
        id = 678512, title = "Sound of Freedom",
        overview = "The story of Tim Ballard, a former US government agent, who quits his job in order to devote his life to rescuing children from global sex traffickers.",
        releaseDate = "2023-07-03",
        posterPath = "/kSf9svfL2WrKeuK8W08xeR5lTn8.jpg",
        backdropPath = "/eMPxmNvJjxVZIQWI2t1VmNC5IuR.jpg"
    )

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() = runTest {

        doReturn(flowOf(MoviesAPI)).`when`(apiHelper).getMovieDetailById(movieID)
        movieDetailViewModel.moviesDetailData.test {
            assertEquals(ResponseState.Success(emptyList<List<MoviesAPI>>()), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        verify(apiHelper).getMovieDetailById(movieID)
    }

    @Test
    fun test_beginEnroll_Success() = runTest {
        Mockito.`when`(repository.getMovieDetailById(movieID)).thenReturn(getDummyMovieValue())

        val result = repository.getMovieDetailById(movieID)

        Assert.assertEquals(getDummyMovieValue(), result)

//        doReturn(flowOf(ResponseState.Success(data = emptyList<MovieDetailModel>()))).`when`(repository).getMovieDetailById(movieID)
//        movieDetailViewModel.moviesDetailData.test{
//            assertEquals(ResponseState.Success(emptyList<List<MoviesAPI>>()), awaitItem())
//            cancelAndIgnoreRemainingEvents()
//        }
//        verify(repository).getMovieDetailById(movieID)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}