import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mrg.mrgmoney.DataBase.Coin
import com.mrg.mrgmoney.R
import com.mrg.mrgmoney.ViewModel.CoinViewModel

class CoinListAdapter(private val context: Context?, private val listener: (Coin, Int) -> Unit) :  RecyclerView.Adapter<CoinViewHolder>() {
    private var coin = listOf<Coin>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recyclerview_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = coin.size

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        if (context != null) {
            holder.bindItem(context, coin[position], listener)
        }
    }

    fun setCoins(coin: List<Coin>) {
        this.coin = coin
        notifyDataSetChanged()
    }
}

class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val amount: TextView = itemView.findViewById(R.id.amount_item)
    val date: TextView = itemView.findViewById(R.id.date_item)
    fun bindItem(context: Context, coin: Coin, listener: (Coin, Int) -> Unit) {
        amount.text = coin.amount.toString()
        date.text = coin.amount.toString()
        itemView.setOnClickListener {
            listener(coin, layoutPosition)
        }
    }


    }