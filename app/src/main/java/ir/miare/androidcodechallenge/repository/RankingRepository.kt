package ir.miare.androidcodechallenge.repository

import ir.miare.androidcodechallenge.api.Api
import ir.miare.androidcodechallenge.data.FakeData
import retrofit2.Call
import javax.inject.Inject

class RankingRepository @Inject constructor(private val api: Api) {
     fun getData(): Call<List<FakeData>> {
        return api.getData()
    }

}