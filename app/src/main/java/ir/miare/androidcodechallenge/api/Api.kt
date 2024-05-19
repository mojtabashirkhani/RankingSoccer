package ir.miare.androidcodechallenge.api

import ir.logicbase.mockfit.Mock
import ir.miare.androidcodechallenge.data.FakeData
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @Mock("data.json")
    @GET("list")
    fun getData(): Call<List<FakeData>>
}