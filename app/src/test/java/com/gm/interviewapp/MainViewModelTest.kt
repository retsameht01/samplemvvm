package com.gm.interviewapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.gm.interviewapp.data.ItunesApiClient
import com.gm.interviewapp.data.ItunesRepository
import com.gm.interviewapp.data.Song
import com.gm.interviewapp.data.SongsResponse
import com.gm.interviewapp.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.io.*


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()


    @Mock
    private lateinit var apiClient: ItunesApiClient

    @Mock
    private lateinit var apiObserver: Observer<Resource<List<Song>>>

    private lateinit var repository: ItunesRepository

    private lateinit var mainViewModel: MainViewModel

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<Resource<List<Song>>>

    @Before
    fun setup() {
        repository = ItunesRepository(apiClient)
        mainViewModel = MainViewModel(repository)
    }

    @Test
    fun shouldReturnGetSongsForArtistSuccess() {
        val sampleResponse = getSampleResponse()
        val sampleArtist = "Some artist"
        testCoroutineRule.runBlockingTest {
            doReturn(sampleResponse)
                .`when`(apiClient)
                .getSongsForArtist(sampleArtist)

            mainViewModel.getSongs().observeForever(apiObserver)
            mainViewModel.lookupSongsForArtist(sampleArtist)
            verify(apiClient).getSongsForArtist(sampleArtist)
            verify(apiObserver, times(2)).onChanged(argumentCaptor.capture())
            val capturedData = argumentCaptor.value
            //Verify that the data is valid
            Assert.assertTrue(capturedData != null)
            Assert.assertEquals(4, capturedData.data?.size)
            mainViewModel.getSongs().removeObserver(apiObserver)
        }
    }

    private fun getSampleResponse(): SongsResponse {
        val file = File("src/test/resources/sample_song_json.json")
        val inputStream: InputStream = FileInputStream(file)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val sb = StringBuffer()
        var str: String?
        while (reader.readLine().also { str = it } != null) {
            sb.append(str)
        }
        val sampleResponse = sb.toString()
        return Gson().fromJson(sampleResponse, SongsResponse::class.java)
    }

}