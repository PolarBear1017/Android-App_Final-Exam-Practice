package ntut.csie.s113590028.finalexam_practice

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    // 宣告變數來記錄讚數
    private var likeCount = 0
    private lateinit var tvLikeCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // 1. 綁定 UI 元件
        val imgDetail: ImageView = findViewById(R.id.imgDetail)
        val tvName: TextView = findViewById(R.id.tvDetailName)
        val tvDesc: TextView = findViewById(R.id.tvDetailDesc)
        val btnLike: Button = findViewById(R.id.btnLike)
        tvLikeCount = findViewById(R.id.tvLikeCount)

        // 2. 接收 Intent 傳過來的資料
        // 注意：Key (字串) 必須跟 MainActivity 傳送時的一模一樣
        val name = intent.getStringExtra("KEY_NAME")
        val desc = intent.getStringExtra("KEY_DESC")
        // 使用 getIntExtra 接收圖片整數 ID，預設值給 0
        val imgId = intent.getIntExtra("KEY_IMG", 0)

        // 3. 顯示資料
        tvName.text = name
        tvDesc.text = desc
        // 如果 ID 不是 0，代表有抓到正確的資源 ID，就把它設定給 ImageView
        if (imgId != 0) {
            imgDetail.setImageResource(imgId)
        }

        // 4. 處理按讚按鈕
        btnLike.setOnClickListener {
            likeCount++
            updateLikeCountText()
        }

        // 5. 檢查是否有儲存的狀態 (如果是旋轉螢幕後重建，這裡不會是 null)
        if (savedInstanceState != null) {
            // 取出之前存的數字
            likeCount = savedInstanceState.getInt("KEY_LIKE_COUNT", 0)
            updateLikeCountText()
        }
    }

    // 輔助函式：更新畫面上的數字
    private fun updateLikeCountText() {
        tvLikeCount.text = likeCount.toString()
    }

    // 6. 考試重點：覆寫 onSaveInstanceState
    // 當 Activity 即將被銷毀（例如旋轉螢幕）時，系統會呼叫這個方法
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // 將目前的變數存入 Bundle
        outState.putInt("KEY_LIKE_COUNT", likeCount)
    }
}