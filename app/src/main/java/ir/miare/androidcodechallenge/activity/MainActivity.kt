package ir.miare.androidcodechallenge.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ir.miare.androidcodechallenge.R
import ir.miare.androidcodechallenge.fragment.RankingFragment
import ir.miare.androidcodechallenge.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, RankingFragment())
            .commit()

    }
}
