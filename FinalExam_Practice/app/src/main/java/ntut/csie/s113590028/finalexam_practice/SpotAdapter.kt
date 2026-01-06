package ntut.csie.s113590028.finalexam_practice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// 宣告 Adapter，並接收兩個參數：
// 1. spotList: 資料清單
// 2. onItemClick: 點擊時要執行的函式 (這是 Kotlin 的 Lambda 寫法，非常方便)
class SpotAdapter(
    private val spotList: List<Spot>,
    private val onItemClick: (Spot) -> Unit
) : RecyclerView.Adapter<SpotAdapter.SpotViewHolder>() {

    // 1. 定義 ViewHolder：負責暫存每一格畫面上的元件 (ImageView, TextView)
    inner class SpotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgSpot: ImageView = itemView.findViewById(R.id.imgSpot)
        val tvName: TextView = itemView.findViewById(R.id.tvName)

        fun bind(spot: Spot) {
            tvName.text = spot.name
            imgSpot.setImageResource(spot.imageResId)

            // 關鍵：設定點擊事件
            itemView.setOnClickListener {
                onItemClick(spot) // 當被點擊時，執行傳進來的函式，並把目前的 spot 資料傳出去
            }
        }
    }

    // 2. 建立 ViewHolder：載入 layout XML
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_spot, parent, false)
        return SpotViewHolder(view)
    }

    // 3. 綁定資料：把 spotList 裡的資料填入 ViewHolder
    override fun onBindViewHolder(holder: SpotViewHolder, position: Int) {
        val spot = spotList[position]
        holder.bind(spot)
    }

    // 4. 回傳資料總數
    override fun getItemCount(): Int {
        return spotList.size
    }
}