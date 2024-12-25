package com.alamo.tic_tac_toe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alamo.tic_tac_toe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playOfflineBtn.setOnClickListener {
            createOfflineGame()
        }
    }

    fun createOfflineGame() {
        GameData.SaveGameModel(GameModel(
            gameStatus = GameStatus.JOINED
        ))
        startGame()
    }

    fun startGame() {
        startActivity(Intent(this, GameActivity::class.java))
    }
}