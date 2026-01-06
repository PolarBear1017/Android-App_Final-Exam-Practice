package ntut.csie.s113590028.finalexam_practice

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    private var likeCount = 0
    private var position = -1   // 記錄這是列表的第幾筆資料
    private lateinit var tvLikeCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // 1. 綁定 UI 元件
        val imgDetail: ImageView = findViewById(R.id.imgDetail)
        val tvName: TextView = findViewById(R.id.tvDetailName)
        val tvDesc: TextView = findViewById(R.id.tvDetailDesc)
        val btnLike: Button = findViewById(R.id.btnLike)
        val btnBack: Button = findViewById(R.id.btnBack)
        tvLikeCount = findViewById(R.id.tvLikeCount)

        // 2. 接收 Intent 傳過來的資料
        val name = intent.getStringExtra("KEY_NAME")
        val desc = intent.getStringExtra("KEY_DESC")
        val imgId = intent.getIntExtra("KEY_IMG", 0)
        likeCount = intent.getIntExtra("KEY_LIKE_COUNT", 0)
        position = intent.getIntExtra("KEY_POSITION", -1)

        // 3. 顯示資料
        tvName.text = name
        tvDesc.text = desc
        if (imgId != 0) {
            imgDetail.setImageResource(imgId)
        }
        updateLikeCountText()

        // 4. 處理按讚按鈕
        btnLike.setOnClickListener {
            likeCount++
            updateLikeCountText()
        }

        // 5. 實作「返回按鈕」邏輯
        btnBack.setOnClickListener {
            finishWithResult()
        }

        // 6. 處理「手機實體返回鍵」 (現代化做法，取代 onBackPressed)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishWithResult()
            }
        })

        // 7. 檢查是否有儲存的狀態 (螢幕旋轉)
        if (savedInstanceState != null) {
            likeCount = savedInstanceState.getInt("KEY_LIKE_COUNT", 0)
            updateLikeCountText()
        }
    }

    // 封裝回傳資料並關閉 Activity 的邏輯
    private fun finishWithResult() {
        val resultIntent = Intent()
        resultIntent.putExtra("KEY_LIKE_COUNT", likeCount)
        resultIntent.putExtra("KEY_POSITION", position)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun updateLikeCountText() {
        tvLikeCount.text = likeCount.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("KEY_LIKE_COUNT", likeCount)
    }
}