package com.example.marubatuapp

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import com.example.marubatuapp.databinding.ActivityMainBinding

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    /** 正解数 */
    private var correctCount: Int = 0

    /** 現在何問目か */
    private var currentQuestionNum = 1

    /** ヒントを見ることができるか */
    private var canShowHint: Boolean = true

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
        // バインディング
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // ボタンタップ
        binding.hintButton.setOnClickListener { tappedHintButton() }
        binding.maruButton.setOnClickListener { tappedAnswerButton(true) }
        binding.batuButton.setOnClickListener { tappedAnswerButton(false) }

        showQuestion()
    }

    /** 問題を表示する */
    private fun showQuestion() {
        if (currentQuestionNum > Questions.values().size) { return }
        binding.questionText.setText(Questions.getQuestionFromId(currentQuestionNum).question)
    }

    /** 回答ボタンタップ */
    private fun tappedAnswerButton(tappedButton: Boolean) {
        var alertMessage: String
        val nextQuestionNum = currentQuestionNum + 1

        val result = judgeAnswer(tappedButton)
        alertMessage = if (result) getString(R.string.feedback_correct) else getString(R.string.feedback_mistake)

        // 次の問題があるか
        if (nextQuestionNum <= Questions.values().size) {
            // 次の問題がある
            currentQuestionNum = nextQuestionNum
        } else {
            // 次の問題がない
            alertMessage += getString(R.string.result_text, Questions.values().size, correctCount)
            clear()
        }
        showAlert(alertMessage)
        showQuestion()
    }

    /** 答え合わせ */
    private fun judgeAnswer(tappedButton: Boolean): Boolean {
        var result: Boolean = (tappedButton == Questions.getQuestionFromId(currentQuestionNum).answer)
        if (result) correctCount += 1
        return result
    }

    /** ヒントボタンタップ */
    private fun tappedHintButton() {
        var alertMessage: String

        if (canShowHint) {
            // ヒントを見ることができる場合
            canShowHint = false
            alertMessage = if (Questions.getQuestionFromId(currentQuestionNum).answer) getString(R.string.hint_maru) else getString(R.string.hint_batu)
        } else {
            // ヒントを見ることができない場合
            alertMessage = getString(R.string.hint_cannot_show)
        }

        showAlert(alertMessage)
    }

    /** アラート表示 */
    private fun showAlert(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    /** 初期化 */
    private fun clear() {
        canShowHint = true
        currentQuestionNum = 1
        correctCount = 0
    }

}
