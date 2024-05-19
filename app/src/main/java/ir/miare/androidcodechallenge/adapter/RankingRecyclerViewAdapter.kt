package ir.miare.androidcodechallenge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ir.miare.androidcodechallenge.R
import ir.miare.androidcodechallenge.data.League
import ir.miare.androidcodechallenge.data.Player
import ir.miare.androidcodechallenge.databinding.ItemLeagueBinding
import ir.miare.androidcodechallenge.databinding.ItemPlayerBinding

class RankingRecyclerViewAdapter(
    private val data: MutableList<Any>,
    private val onItemClickListener: (Player) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        when (viewType) {
            RankingViewType.PLAYER.ordinal -> {
                val binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_player, parent, false
                ) as ItemPlayerBinding
                return PlayerViewHolder(binding.root, binding)
            }

            RankingViewType.LEAGUE.ordinal -> {
                val binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_league, parent, false
                ) as ItemLeagueBinding
                return LeagueViewHolder(binding.root, binding)
            }

            else -> {
                val binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_player, parent, false
                ) as ItemPlayerBinding
                return PlayerViewHolder(binding.root, binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == RankingViewType.PLAYER.ordinal) {
            (holder as PlayerViewHolder).bind(data[position] as Player, onItemClickListener)
            holder.binding.executePendingBindings()

        } else if (getItemViewType(position) == RankingViewType.LEAGUE.ordinal) {
            (holder as LeagueViewHolder).bind(data[position] as League)
            holder.binding.executePendingBindings()

        }


    }

    override fun getItemCount(): Int {
        return data.size

    }

    inner class PlayerViewHolder(private val itemView: View, val binding: ItemPlayerBinding) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(player: Player, onItemClickListener: (Player) -> Unit) {
            binding.playerName.text = player.name
            binding.teamName.text = player.team.name
            binding.rank.text = player.team.rank.toString()

            binding.root.setOnClickListener {
                onItemClickListener(player)
            }
        }
    }


    inner class LeagueViewHolder(private val itemView: View, val binding: ItemLeagueBinding) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(league: League) {
            binding.leagueName.text = league.name
            binding.leagueCountry.text = league.country
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (data[position] is League) {
            RankingViewType.LEAGUE.ordinal
        } else if (data[position] is Player) {
            RankingViewType.PLAYER.ordinal
        } else {
            RankingViewType.PLAYER.ordinal
        }
    }

    enum class RankingViewType {
        PLAYER, LEAGUE
    }
}
