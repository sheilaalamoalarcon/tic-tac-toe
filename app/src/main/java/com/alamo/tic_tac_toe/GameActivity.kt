package com.alamo.tic_tac_toe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.alamo.tic_tac_toe.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityGameBinding
    private var gameModel: GameModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn0.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)

        binding.startGameBtn.setOnClickListener {
            startGame()
        }
        GameData.gameModel.observe(this) {
            gameModel = it
            SetUI()
        }
    }

    fun SetUI() {
        gameModel?.apply {

            binding.btn0.text = fillPos[0]
            binding.btn1.text = fillPos[1]
            binding.btn2.text = fillPos[2]
            binding.btn3.text = fillPos[3]
            binding.btn4.text = fillPos[4]
            binding.btn5.text = fillPos[5]
            binding.btn6.text = fillPos[6]
            binding.btn7.text = fillPos[7]
            binding.btn8.text = fillPos[8]

            binding.startGameBtn.visibility = View.VISIBLE

            binding.gameStatusText.text =
                when (gameStatus) {
                    GameStatus.CREATED -> {
                        binding.startGameBtn.visibility = View.VISIBLE
                        "Game ID"
                    }

                    GameStatus.JOINED -> {
                        "Click on start game"
                    }

                    GameStatus.IN_PROGRESS -> {
                        binding.startGameBtn.visibility = View.VISIBLE
                        currentPlayer + "turn"

                    }

                    GameStatus.FINISHED -> {
                        if (winner.isNotEmpty()) {
                            "Winner is $winner"
                        } else {
                            "TABS"
                        }
                    }

                }
        }
    }

    fun startGame() {
        gameModel?.apply {
            UpdateGameData(
                GameModel(
                    gameID = gameID,
                    gameStatus = GameStatus.IN_PROGRESS
                )
            )
        }
    }

    fun UpdateGameData(model: GameModel) {
        GameData.SaveGameModel(model)
    }

    fun CheckWinner() {
        val winningPos = arrayOf(
            intArrayOf(0, 1, 2),
            intArrayOf(3, 4, 5),
            intArrayOf(6, 7, 8),
            intArrayOf(0, 3, 6),
            intArrayOf(1, 4, 7),
            intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8),
            intArrayOf(2, 5, 8),
            intArrayOf(2, 4, 6),
        )

        gameModel?.apply {
            for (i in winningPos) {
                if (fillPos[i[0]] == fillPos[i[1]] &&
                    fillPos[i[1]] == fillPos[i[2]] &&
                    fillPos[i[0]].isNotEmpty()
                ) {
                    gameStatus = GameStatus.FINISHED
                    winner = fillPos[i[0]]
                }
            }
            if (fillPos.none() { it.isEmpty() }) {
                gameStatus = GameStatus.FINISHED
            }
            UpdateGameData(this)
        }
    }

    override fun onClick(v: View?) {
        gameModel?.apply {
            if (gameStatus != GameStatus.IN_PROGRESS) {
                Toast.makeText(applicationContext, "Game not started", Toast.LENGTH_SHORT).show()
                return
            } //game in progress
            val clickedPos = (v?.tag as String).toInt()
            if (fillPos[clickedPos].isEmpty()) {
                fillPos[clickedPos] = currentPlayer
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                CheckWinner()
                UpdateGameData(this)
            }
        }
    }
}