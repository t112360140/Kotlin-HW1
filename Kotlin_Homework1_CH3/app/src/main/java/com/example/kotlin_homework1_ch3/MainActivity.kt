package com.example.kotlin_homework1_ch3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView

enum class MORA {
    SCISSOR,
    STONE,
    PAPER
}
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val nameInput = findViewById<EditText>(R.id.name_in)
        val resultText = findViewById<TextView>(R.id.tv_text)
        val nameLabel = findViewById<TextView>(R.id.tv_name)
        val winnerLabel = findViewById<TextView>(R.id.tv_winner)
        val playerMoveLabel = findViewById<TextView>(R.id.tv_mmora)
        val computerMoveLabel = findViewById<TextView>(R.id.tv_cmora)
        val scissorButton = findViewById<RadioButton>(R.id.btn_scissor)
        val stoneButton = findViewById<RadioButton>(R.id.btn_stone)
        val paperButton = findViewById<RadioButton>(R.id.btn_paper)
        val playButton = findViewById<Button>(R.id.btn_mora)

        playButton.setOnClickListener {
            val playerName = nameInput.text.toString()

            if (playerName.isBlank()) {
                resultText.text = "請輸入玩家姓名"
                return@setOnClickListener // 提早結束
            }

            // 使用when替代if
            nameLabel.text = "姓名:\n$playerName"

            val playerMove = when {
                scissorButton.isChecked -> MORA.SCISSOR // 剪刀
                stoneButton.isChecked -> MORA.STONE   // 石頭
                paperButton.isChecked -> MORA.PAPER   // 布
                else -> null // 沒有選擇
            }

            if (playerMove == null) {
                resultText.text = "請先選擇出拳！"
                return@setOnClickListener
            }

            val playerMoveText = when (playerMove) {
                MORA.SCISSOR -> "剪刀"
                MORA.STONE -> "石頭"
                MORA.PAPER -> "布"
                else -> "神奇的情況出現了！！！"
            }
            playerMoveLabel.text = "我方出拳:\n$playerMoveText"

            val computerMove = MORA.values().random() // 使用.random()
            val computerMoveText = when (computerMove) {
                MORA.SCISSOR -> "剪刀"
                MORA.STONE -> "石頭"
                MORA.PAPER -> "布"
                else -> "神奇的情況出現了！！！"
            }
            computerMoveLabel.text = "電腦出拳:\n$computerMoveText"

            when {
                playerMove == computerMove -> {
                    winnerLabel.text = "勝利者\n平手"
                    resultText.text = "平局，請再試一次!"
                }
                ((playerMove == MORA.STONE && computerMove == MORA.SCISSOR) ||
                (playerMove == MORA.SCISSOR && computerMove == MORA.PAPER) ||
                (playerMove == MORA.PAPER && computerMove == MORA.STONE)) -> {
                    winnerLabel.text = "勝利者\n$playerName"
                    resultText.text = "恭喜您獲勝!"
                }
                else -> {
                    winnerLabel.text = "勝利者\n電腦"
                    resultText.text = "可惜，電腦獲勝!"
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
