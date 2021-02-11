package com.example.marubatuapp

import android.app.Activity
import android.os.Bundle
import com.example.marubatuapp.databinding.ActivityMainBinding

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    /** 正解数 */
    private var correctCount: Int = 0

    /** 現在何問目か */
    private var currentQuestionNum = 1

    private enum class Questions(val id: Int, val question: Int, val answer: Boolean) {
        Q1(1, R.string.question1, false),
        Q2(2, R.string.question2, true),
        Q3(3, R.string.question3, true),
        Q4(4, R.string.question4, true);

        companion object {
            fun getQuestionFromId(id: Int): Questions {
                return values().first { it.id == id }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        println(Questions.Q1.id)
        println(Questions.getQuestionFromId(1).id)
        println(Questions.values().size)
        println("aaa")

        showQuestion()
    }

    /** 問題を表示する */
    private fun showQuestion() {
        if (currentQuestionNum > Questions.values().size) { return }
        binding.questionText.setText(Questions.getQuestionFromId(currentQuestionNum).question)
    }

}
