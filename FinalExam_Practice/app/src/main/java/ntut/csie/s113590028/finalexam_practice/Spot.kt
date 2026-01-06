package ntut.csie.s113590028.finalexam_practice

// 這是我們用來傳遞資料的物件，包含名稱、說明、圖片ID
data class Spot(
    val name: String,
    val description: String,
    val imageResId: Int,
    var likeCount: Int = 0
)