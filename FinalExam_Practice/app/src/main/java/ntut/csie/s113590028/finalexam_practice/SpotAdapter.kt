package ntut.csie.s113590028.finalexam_practice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// 宣告 Adapter，並接收兩個參數：
// 1. spotList: 資料清單
// 2. onItemClick: 點擊時要執行的函式，增加傳出 position
class SpotAdapter(
    private val spotList: List<Spot>,
    // Lambda 改成接收兩個參數：Spot 和 Int (position)
    private val onItemClick: (Spot, Int) -> Unit
) : RecyclerView.Adapter<SpotAdapter.SpotViewHolder>() {

    // 1. 定義 ViewHolder：負責暫存每一格畫面上的元件 (ImageView, TextView)
    inner class SpotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgSpot: ImageView = itemView.findViewById(R.id.imgSpot)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvLikes: TextView = itemView.findViewById(R.id.tvLikes)

        fun bind(spot: Spot, position: Int) {
            tvName.text = spot.name
            imgSpot.setImageResource(spot.imageResId)
            tvLikes.text = "按讚數: ${spot.likeCount}"

            // 關鍵：設定點擊事件，傳出 spot 與 position
            itemView.setOnClickListener {
                onItemClick(spot, position) 
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
        holder.bind(spot, position)
    }

    // 4. 回傳資料總數
    override fun getItemCount(): Int {
        return spotList.size
    }
}