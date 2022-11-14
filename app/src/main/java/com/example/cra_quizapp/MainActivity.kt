package com.example.cra_quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.cra_quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var rightAnswer: String? = null
    private var rightAnswerCount = 0
    private var quizCount = 1
    private val QUIZ_COUNT = 5

    private val quizData = mutableListOf(
        // country, capital, choice1, choice2, choice3
        mutableListOf("China", "Beijing", "Jakarta", "Manila", "Stockholm"),
        mutableListOf("India", "New Delhi", "Beijing", "Bangkok", "Seoul"),
        mutableListOf("Indonesia", "Jakarta", "Manila", "New Delhi", "Kuala Lumpur"),
        mutableListOf("Japan", "Tokyo", "Bangkok", "Taipei", "Jakarta"),
        mutableListOf("Thailand", "Bangkok", "Berlin", "Havana", "Kingston"),
        mutableListOf("Brazil", "Brasilia", "Havana", "Bangkok", "Copenhagen"),
        mutableListOf("Canada", "Ottawa", "Bern", "Copenhagen", "Jakarta"),
        mutableListOf("Cuba", "Havana", "Bern", "London", "Mexico City"),
        mutableListOf("Mexico", "Mexico City", "Ottawa", "Berlin", "Santiago"),
        mutableListOf("United States", "Washington D.C.", "San Jose", "Buenos Aires", "Kuala Lumpur"),
        mutableListOf("France", "Paris", "Ottawa", "Copenhagen", "Tokyo"),
        mutableListOf("Germany", "Berlin", "Copenhagen", "Bangkok", "Santiago"),
        mutableListOf("Italy", "Rome", "London", "Paris", "Athens"),
        mutableListOf("Spain", "Madrid", "Mexico City", "Jakarta", "Havana"),
        mutableListOf("United Kingdom", "London", "Rome", "Paris", "Singapore")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // shuffle quiz
        quizData.shuffle()

        showNextQuiz()
    }

    fun showNextQuiz() {
        // update count label
        binding.countLabel.text = getString(R.string.count_label, quizCount)

        // pick one quiz set
        val quiz = quizData[0]

        // set question and right answer
        binding.questionLabel.text = quiz[0]
        rightAnswer = quiz[1]

        // remove country from quiz
        quiz.removeAt(0)

        // shuffle answer and choices
        quiz.shuffle()

        // set choices
        binding.answerBtn1.text = quiz[0]
        binding.answerBtn2.text = quiz[1]
        binding.answerBtn3.text = quiz[2]
        binding.answerBtn4.text = quiz[3]

        // remove this quiz from quiz data
        quizData.removeAt(0)
    }

    fun checkAnswer(view: View) {
        // get pushed button
        val answerBtn: Button = findViewById(view.id)
        val btnText = answerBtn.text.toString()

        val alertTitle: String
        if (btnText == rightAnswer) {
            // correct
            alertTitle = "Correct!"
            rightAnswerCount++
        } else {
            // wrong
            alertTitle = "Incorrect answer..."
        }

        // create dialog
        AlertDialog.Builder(this)
            .setTitle(alertTitle)
            .setMessage("Answer: $rightAnswer")
            .setPositiveButton("NEXT") {dialogFragment, i ->
                checkQuizCount()
            }
            .setCancelable(false)
            .show()
    }

    fun checkQuizCount() {
        if (quizCount == QUIZ_COUNT) {
            // show result
            val intent = Intent(this@MainActivity, ResultActivity::class.java)
            intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount)
            startActivity(intent)

        } else {
            quizCount++
            showNextQuiz()
        }
    }
}