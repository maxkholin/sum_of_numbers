package com.example.sumofnumbers.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.sumofnumbers.R
import com.example.sumofnumbers.domain.entity.GameResult

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("score")
fun bindScore(textView: TextView, score: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        score
    )
}

@BindingAdapter("requiredPercentage")
fun bindRequiredPercentage(textView: TextView, percentage: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        percentage
    )
}

@BindingAdapter("scorePercentage")
fun bindScorePercentage(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        getPercentageOfRightAnswers(gameResult)
    )
}

private fun getPercentageOfRightAnswers(gameResult: GameResult): Int {
    return if (gameResult.countOfQuestion == 0) {
        0
    } else {
        (gameResult.countOfRightAnswers / gameResult.countOfQuestion.toDouble() * 100).toInt()
    }
}

@BindingAdapter("resultEmoji")
fun bindResultEmoji(imageView: ImageView, win: Boolean) {
    imageView.setImageResource(getSmileResId(win))

}

private fun getSmileResId(win: Boolean): Int {
    return if (win) {
        R.drawable.ic_smile
    } else {
        R.drawable.ic_sad
    }
}

@BindingAdapter("enoughCount")
fun bindEnoughCount(textView: TextView, enough: Boolean) {
    textView.setTextColor(getColorByState(textView.context, enough))
}

@BindingAdapter("enoughPercentage")
fun bindEnoughPercentage(progressBar: ProgressBar, enough: Boolean) {
    progressBar.progressTintList =
        ColorStateList.valueOf(getColorByState(progressBar.context, enough))
}

private fun getColorByState(context: Context, state: Boolean): Int {
    val colorResId = if (state) {
        R.color.green
    } else {
        R.color.red
    }
    return ContextCompat.getColor(context, colorResId)
}

@BindingAdapter("numberAsText")
fun bindNumberAsText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, clickListener: (Int) -> Unit) {
    textView.setOnClickListener {
        clickListener(textView.text.toString().toInt())
    }
}
