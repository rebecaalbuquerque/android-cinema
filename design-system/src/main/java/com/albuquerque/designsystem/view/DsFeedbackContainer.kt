package com.albuquerque.designsystem.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.albuquerque.designsystem.databinding.DsCustomViewFeedbackContainerBinding
import com.albuquerque.designsystem.extension.setImageResource
import com.albuquerque.designsystem.extension.setText

class DsFeedbackContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = DsCustomViewFeedbackContainerBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun feedbackContainer(block: Data.() -> Unit) {
        val feedback = Data("", 0, 0).apply(block)

        binding.illustration.setImageResource(feedback.illustrationRes)
        binding.message.setText(text = feedback.title)
    }

    data class Data(
        var title: String? = null,
        @StringRes var titleRes: Int? = null,
        @DrawableRes var illustrationRes: Int? = null
    )
}