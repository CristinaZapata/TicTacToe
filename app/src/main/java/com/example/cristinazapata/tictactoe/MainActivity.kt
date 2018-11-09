package com.example.cristinazapata.tictactoe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        buttonSelected(v as Button)
    }

    private var cellsMapping = mutableMapOf<Int, String>()
    private var xTurn = true
    private var winner = false
    private val totalCells = 9
    private lateinit var textInTurn:TextView
    private lateinit var textPlayer1Points:TextView
    private lateinit var textPlayer2Points:TextView
    private lateinit var textDrawPoints:TextView
    private val x = "X"
    private val o = "O"
    private val player1 = "Player 1"
    private val player2 = "Player 2"
    private var playerInTurn = player1
    private var player1Points = 0
    private var player2Points = 0
    private var drawPoints = 0
    private lateinit var ref: DatabaseReference
    private var buttons = arrayOfNulls<Button>(totalCells)
    private val winnerCombinations:Array<IntArray> = arrayOf(
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textInTurn = findViewById(R.id.textViewTurn)
        textInTurn.text = playerInTurn
        textPlayer1Points = findViewById(R.id.textP1Wins)
        textPlayer2Points = findViewById(R.id.textP2Wins)
        textDrawPoints = findViewById(R.id.textDraws)

        for(i in 1..totalCells){
            var button = findViewById<Button>(resources.getIdentifier("button$i", "id", packageName))
            button.setOnClickListener(this)
            buttons[i-1] = button
        }

        ref = FirebaseDatabase.getInstance().getReference()

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if(p0!!.exists()){
                    player1Points = (p0.child("players").child("player1").value as Long).toInt()
                    player2Points = (p0.child("players").child("player2").value as Long).toInt()
                    drawPoints = (p0.child("players").child("draw").value as Long).toInt()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        textPlayer1Points.text = resources.getString(R.string.player1Points, player1Points)
        textPlayer2Points.text = resources.getString(R.string.player2Points, player2Points)
        textDrawPoints.text = resources.getString(R.string.drawPoints, drawPoints)
    }
    
    private fun buttonSelected(button: Button){ //usar HashMap
        var index = 0

        when(button.id){
            R.id.button1 -> index = 0
            R.id.button2 -> index = 1
            R.id.button3 -> index = 2
            R.id.button4 -> index = 3
            R.id.button5 -> index = 4
            R.id.button6 -> index = 5
            R.id.button7 -> index = 6
            R.id.button8 -> index = 7
            R.id.button9 -> index = 8
        }

        playGame(index, button)
        checkWinner()
        update()
    }
    
    private fun playGame(index:Int, button:Button){
        
        when{
            xTurn -> cellsMapping[index] = x
            else -> cellsMapping[index] = o
        }
        button.text = cellsMapping[index]
        button.isEnabled = false

        if(xTurn){
            playerInTurn = player1
            textInTurn.text = player2
        }
        else{
            playerInTurn = player2
            textInTurn.text = player1
        }

        xTurn = !xTurn //changing to next turn
    }

    private fun checkWinner(){
        if(cellsMapping.isNotEmpty()){
            for(combination in winnerCombinations){
                var(a, b, c) = combination

                if(cellsMapping[a] != null && cellsMapping[a] == cellsMapping[b] && cellsMapping[a] == cellsMapping[c]){
                    winner = true
                }
            }
        }
    }

    private fun update(){
        when{
            winner -> {
                textInTurn.text = playerInTurn
                Toast.makeText(this, resources.getString(R.string.winner, playerInTurn), Toast.LENGTH_LONG).show()

                if(!xTurn){
                    player1Points++
                    ref.child("players").child("player1").setValue(player1Points)
                    textPlayer1Points.text = resources.getString(R.string.player1Points, player1Points)
                }else{
                    player2Points++
                    ref.child("players").child("player2").setValue(player2Points)
                    textPlayer2Points.text = resources.getString(R.string.player2Points, player2Points)
                }
            }

            cellsMapping.size == totalCells -> {
                Toast.makeText(this, "Draw", Toast.LENGTH_LONG).show()
                drawPoints++
                ref.child("players").child("draw").setValue(drawPoints)
                textDrawPoints.text = resources.getString(R.string.drawPoints, drawPoints)
            }
        }
    }

    fun reset(view: View){
        cellsMapping = mutableMapOf()
        xTurn = true
        winner = false
        textInTurn.text = player1

        for(i in 1..totalCells){
            var button = buttons[i-1]
            button?.text = ""
            button?.isEnabled = true
        }
    }
}
