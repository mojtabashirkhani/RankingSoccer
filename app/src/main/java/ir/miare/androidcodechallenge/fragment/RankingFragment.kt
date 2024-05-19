package ir.miare.androidcodechallenge.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import ir.miare.androidcodechallenge.R
import ir.miare.androidcodechallenge.activity.MainActivity
import ir.miare.androidcodechallenge.adapter.RankingRecyclerViewAdapter
import ir.miare.androidcodechallenge.bottomSheet.PlayerInfoBottomSheet
import ir.miare.androidcodechallenge.data.Player
import ir.miare.androidcodechallenge.databinding.FragmentRankingBinding

@AndroidEntryPoint
class RankingFragment() : Fragment(), (Player) -> Unit {



    var binding: FragmentRankingBinding? = null
    private lateinit var rankingRecyclerViewAdapter: RankingRecyclerViewAdapter

    private val mViewModel by lazy {
        ViewModelProvider(this)[RankingFragmentVM::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ranking, container, false)
        binding = FragmentRankingBinding.bind(view)


        /*   val retrofit = Retrofit.Builder()
               .baseUrl("https://test_baseurl.com/v2/")
               .addConverterFactory(JacksonConverterFactory.create())
               .client(
                   OkHttpClient.Builder()
                       .addInterceptor(
                           MockFitInterceptor(
                               bodyFactory = { input -> resources.assets.open(input) },
                               logger = { tag, message -> Log.d(tag, message) },
                               baseUrl = "https://test_baseurl.com/v2/",
                               requestPathToJsonMap = MockFitConfig.REQUEST_TO_JSON, // autogenerated constant, just press build button
                               mockFilesPath = "",
                               mockFitEnable = true,
                               apiEnableMock = true,
                               apiIncludeIntoMock = arrayOf(),
                               apiExcludeFromMock = arrayOf(),
                               apiResponseLatency = 1000L
                           )
                       )
                       .connectTimeout(20, TimeUnit.SECONDS)
                       .writeTimeout(20, TimeUnit.SECONDS)
                       .readTimeout(20, TimeUnit.SECONDS)
                       .build()
               ).build()
   //        binding!!.pbLoading.show()
           thread(name = "NetworkWorker") {
               retrofit.create(Api::class.java).getData().enqueue(object : Callback<List<FakeData>> {
                   override fun onResponse(
                       call: Call<List<FakeData>>,
                       response: Response<List<FakeData>>
                   ) {
                       val data = response.body()!!

                   }

                   override fun onFailure(call: Call<List<FakeData>>, t: Throwable) {
                       t.printStackTrace()
                   }
               })
           }*/
        initRecyclerViewForNoneButtonDefaultChecked()
        initRecyclerViewForTeamAndLeagueRankingButton()
        initRecyclerViewForMostGoalsScoredByAPlayerButton()
        initRecyclerViewForAvgGoalPerMatchInALeagueButton()
        initRecyclerViewForNoneButton()


        return view
    }

    private fun initRecyclerViewForNoneButtonDefaultChecked() {
        if ((requireActivity() as MainActivity).binding?.radioButtonNone?.isChecked == true) {
            binding?.pbLoading?.show()

            mViewModel.noneDataListResponse.observe(viewLifecycleOwner) { noneList ->
                rankingRecyclerViewAdapter =
                    RankingRecyclerViewAdapter(
                        noneList,
                        this
                    )
                binding?.recyclerViewRanking?.adapter = rankingRecyclerViewAdapter

                binding?.pbLoading?.hide()
            }
        }

    }

    private fun initRecyclerViewForTeamAndLeagueRankingButton() {
        (requireActivity() as MainActivity).binding?.radioButtonTeamLeagueRanking?.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                binding?.pbLoading?.show()
                mViewModel.teamAndLeagueRankedListResponse.observe(viewLifecycleOwner) { teamAndLeagueRankedList ->
                    rankingRecyclerViewAdapter =
                        RankingRecyclerViewAdapter(
                            teamAndLeagueRankedList,
                            this
                        )
                    binding?.recyclerViewRanking?.adapter = rankingRecyclerViewAdapter

                    binding?.pbLoading?.hide()
                }

            }
        }

    }

    private fun initRecyclerViewForMostGoalsScoredByAPlayerButton() {
        (requireActivity() as MainActivity).binding?.radioButtonMostGoal?.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                binding?.pbLoading?.show()

                mViewModel.mostGoalPlayerListResponse.observe(viewLifecycleOwner) { mostGoalPlayerList ->
                    rankingRecyclerViewAdapter =
                        RankingRecyclerViewAdapter(
                            mostGoalPlayerList,
                            this
                        )
                    binding?.recyclerViewRanking?.adapter = rankingRecyclerViewAdapter

                    binding?.pbLoading?.hide()
                }

            }
        }
    }

    private fun initRecyclerViewForAvgGoalPerMatchInALeagueButton() {
        (requireActivity() as MainActivity).binding?.radioButtonAverageGoal?.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                binding?.pbLoading?.show()

                mViewModel.avgGoalPerLeagueListResponse.observe(viewLifecycleOwner) { avgGoalPerLeagueList ->
                    rankingRecyclerViewAdapter =
                        RankingRecyclerViewAdapter(
                            avgGoalPerLeagueList,
                            this
                        )
                    binding?.recyclerViewRanking?.adapter = rankingRecyclerViewAdapter

                    binding?.pbLoading?.hide()
                }

            }
        }
    }

    private fun initRecyclerViewForNoneButton() {
        (requireActivity() as MainActivity).binding?.radioButtonNone?.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                binding?.pbLoading?.show()

                mViewModel.noneDataListResponse.observe(viewLifecycleOwner) { noneList ->
                    rankingRecyclerViewAdapter =
                        RankingRecyclerViewAdapter(
                            noneList,
                            this
                        )
                    binding?.recyclerViewRanking?.adapter = rankingRecyclerViewAdapter

                    binding?.pbLoading?.hide()
                }

            }
        }
    }



    override fun invoke(player: Player) {
        PlayerInfoBottomSheet(player).show(requireActivity().supportFragmentManager, "")
    }

}

