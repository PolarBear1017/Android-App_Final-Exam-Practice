package ntut.csie.s113590028.finalexam_practice

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MainActivity : AppCompatActivity() {

    // 資料清單
    private val spotList = ArrayList<Spot>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. 初始化資料 (這裡請換成你自己的圖片 ID)
        initData()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewSpot)
        val spinner = findViewById<Spinner>(R.id.spinnerFormat)

        // 2. 設定 Adapter
        // 這裡的 { clickedSpot -> ... } 就是我們傳給 Adapter 的點擊事件
        val adapter = SpotAdapter(spotList) { clickedSpot ->
            // 當項目被點擊時，跳轉到 DetailActivity (開啟另一個視窗)
            // this: 指的是「目前的頁面」
            // .java: 為了要相容 Android SDK 底層的 Java 介面，我們必須透過 .java 把 Kotlin 的類別資訊轉換成 Java 引擎看得懂的類別格式。這是一個轉換動作。
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("KEY_NAME", clickedSpot.name)
            intent.putExtra("KEY_DESC", clickedSpot.description)
            intent.putExtra("KEY_IMG", clickedSpot.imageResId)
            startActivity(intent)   // 跳轉畫面
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
        // 請確保 R.drawable.xxx 是你剛剛加入的圖片名稱
        // 為了測試瀑布流，建議圖片長寬比可以不一樣
        spotList.add(Spot("倫敦塔橋", "UK", R.drawable.uk_bridge))
        spotList.add(Spot("富士山", "Japan", R.drawable.japan_fuji))
        spotList.add(Spot("金門大橋", "USA 舊金山", R.drawable.usa_bridge))
        spotList.add(Spot("自由女神像", "USA", R.drawable.usa_statue))
        spotList.add(Spot("夢蓮湖（Moraine Lake）", "Canada", R.drawable.canada_lake))
        spotList.add(Spot("雪梨歌劇院", "Australia Sydney", R.drawable.australia_sydney))

    }
}