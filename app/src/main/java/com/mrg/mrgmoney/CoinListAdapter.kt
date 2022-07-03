import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrg.mrgmoney.DataBase.Coin
import com.mrg.mrgmoney.R

class CoinListAdapter(private var itemsList: List<Coin>) :  RecyclerView.Adapter<CoinListAdapter.CoinViewHolder>() {
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CoinViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recyclerview_item, viewGroup, false)

        return CoinViewHolder(view)
    }
    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: CoinViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.amount.text = itemsList[position].amount.toString()
        viewHolder.date.text = itemsList[position].amount.toString()
    }

    class CoinViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Define click listener for the ViewHolder's View.
        val amount: TextView = view.findViewById(R.id.amount_item)
        val date: TextView = view.findViewById(R.id.date_item)

    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = itemsList.size

}