import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mrg.mrgmoney.DataBase.Coin
import com.mrg.mrgmoney.R
import com.mrg.mrgmoney.ViewModel.CoinViewModel

class CoinListAdapter(private val mList: ArrayList<Coin>,val deleteInterface:DeleteInterface) :  RecyclerView.Adapter<CoinListAdapter.CoinViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)

        return CoinViewHolder(view)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.amount.text = ItemsViewModel.amount.toString()
        holder.date.text = ItemsViewModel.date.toString()
        holder.removeBtn.setOnClickListener {
            deleteInterface.onDelete(mList.get(position))
        }
    }


    class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val amount: TextView = itemView.findViewById(R.id.amount_item)
        val date: TextView = itemView.findViewById(R.id.date_item)
        val removeBtn : ImageView = itemView.findViewById(R.id.rmv_btn)

    }
    fun updateList(newList: List<Coin>) {
        // on below line we are clearing
        // our notes array list
        mList.clear()
        // on below line we are adding a
        // new list to our all notes list.
        mList.addAll(newList)
        // on below line we are calling notify data
        // change method to notify our adapter.
        notifyDataSetChanged()
    }
    interface DeleteInterface{
        fun onDelete(coin: Coin)
    }
}