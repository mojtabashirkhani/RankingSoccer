package ir.miare.androidcodechallenge.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.data.FakeData
import ir.miare.androidcodechallenge.repository.RankingRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RankingFragmentVM @Inject constructor(rankingRepository: RankingRepository): ViewModel() {
    val noneDataListResponse = MutableLiveData<MutableList<Any>>()
    val mostGoalPlayerListResponse = MutableLiveData<MutableList<Any>>()
    val teamAndLeagueRankedListResponse = MutableLiveData<MutableList<Any>>()
    val avgGoalPerLeagueListResponse = MutableLiveData<MutableList<Any>>()

    init {
        rankingRepository.getData().enqueue(object : Callback<List<FakeData>> {
            override fun onResponse(
                call: Call<List<FakeData>>,
                response: Response<List<FakeData>>
            ) {
                val data = response.body() ?: emptyList()

                // most goal
                val mostGoalPlayerList: MutableList<Any> = mutableListOf()

                mostGoalPlayerList.addAll(data.flatMap { it.players }.sortedBy { it.totalGoal }
                    .reversed())

                mostGoalPlayerListResponse.value = mostGoalPlayerList

                // none
                val noneList: MutableList<Any> = mutableListOf()

                data.forEach {
                    noneList.add(it.league)
                    it.players.forEach { player ->
                        noneList.add(player)
                    }
                }

                noneDataListResponse.value = noneList

                // ranking league
                val teamAndLeagueRanked: MutableList<Any> = mutableListOf()

                val sortedLeague = data.sortedBy { it.league.rank }

                sortedLeague.forEach {
                    teamAndLeagueRanked.add(it.league)
                    it.players.sortedBy { player -> player.team.rank }.forEach { player ->
                        teamAndLeagueRanked.add(player)
                    }
                }

                teamAndLeagueRankedListResponse.value = teamAndLeagueRanked

                // average goals per total match of the leagues
                val avgGoalPerLeagueList: MutableList<Any> = mutableListOf()
                var totalGoalAllOfPlayers = 0f

                data.forEach {
                    it.players.forEach { player ->
                        totalGoalAllOfPlayers += player.totalGoal
                    }
                    it.avgPerLeague = totalGoalAllOfPlayers / it.league.totalMatches
                    totalGoalAllOfPlayers = 0f
                }

                data.sortedBy { it.avgPerLeague }.reversed().forEach {
                    avgGoalPerLeagueList.add(it.league)
                    it.players.forEach { player ->
                        avgGoalPerLeagueList.add(player)
                    }
                }

                avgGoalPerLeagueListResponse.value = avgGoalPerLeagueList
            }

            override fun onFailure(call: Call<List<FakeData>>, t: Throwable) {
                t.printStackTrace()
            }

        })

    }
}