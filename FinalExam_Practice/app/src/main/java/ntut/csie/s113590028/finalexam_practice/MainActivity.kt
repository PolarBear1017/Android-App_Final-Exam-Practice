package ntut.csie.s113590028.finalexam_practice

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MainActivity : AppCompatActivity() {

    // 資料清單
    private val spotList = ArrayList<Spot>()
    private lateinit var adapter: SpotAdapter // 提昇 adapter 變數層級以便之後存取

    // 1. 定義「結果接收器」
    // 當從 DetailActivity 回來時，這段程式碼會被執行
    private val detailLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            // 接收回傳的資料
            val position = data?.getIntExtra("KEY_POSITION", -1) ?: -1
            val newLikeCount = data?.getIntExtra("KEY_LIKE_COUNT", 0) ?: 0

            // 更新列表資料
            if (position != -1) {
                spotList[position].likeCount = newLikeCount // 更新資料源
                adapter.notifyItemChanged(position) // 通知 Adapter 更新那一格畫面
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. 初始化資料
        initData()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewSpot)
        val spinner = findViewById<Spinner>(R.id.spinnerFormat)

        // 2. 設定 Adapter
        adapter = SpotAdapter(spotList) { clickedSpot, position ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("KEY_NAME", clickedSpot.name)
            intent.putExtra("KEY_DESC", clickedSpot.description)
            intent.putExtra("KEY_IMG", clickedSpot.imageResId)

            // 關鍵：把「現在的讚數」和「第幾格 (position)」傳過去
            intent.putExtra("KEY_LIKE_COUNT", clickedSpot.likeCount)
            intent.putExtra("KEY_POSITION", position)

            // 關鍵：改用 launcher 啟動，而不是直接 startActivity
            detailLauncher.launch(intent)
        }
        recyclerView.adapter = adapter

        // 3. 設定 Spinner 選項
        val layoutOptions = listOf("Linear Vertical", "Linear Horizontal", "Grid (2 columns)", "Staggered Grid")
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, layoutOptions)

        // 4. 設定 Spinner 監聽器，切換 LayoutManager
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                recyclerView.layoutManager = when (position) {
                    0 -> LinearLayoutManager(this@MainActivity) // 直向
                    1 -> LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false) // 橫向
                    2 -> GridLayoutManager(this@MainActivity, 2) // Grid
                    3 -> StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) // 瀑布流
                    else -> LinearLayoutManager(this@MainActivity)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun initData() {
        // 建立資料
        spotList.add(Spot("倫敦塔橋", "UK", R.drawable.uk_bridge))
        spotList.add(Spot("富士山", "Japan", R.drawable.japan_fuji))
        spotList.add(Spot("金門大橋", "USA 舊金山", R.drawable.usa_bridge))
        spotList.add(Spot("自由女神像", "USA", R.drawable.usa_statue))
        spotList.add(Spot("夢蓮湖（Moraine Lake）", "Canada", R.drawable.canada_lake))
        spotList.add(Spot("雪梨歌劇院", "Australia Sydney", R.drawable.australia_sydney))
    }
}