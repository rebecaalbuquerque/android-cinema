package com.albuquerque.favorites.presentation

import androidx.core.view.isVisible
import com.albuquerque.designsystem.extension.bindSafely

internal fun FavoritesFragment.showEmptyView() = binding.bindSafely {
    containerFeedback.apply {
        isVisible = true
        feedbackContainer {
            title = "Lista vazia"
            illustrationRes = com.albuquerque.designsystem.R.drawable.ds_illu_empty
        }
    }
}

internal fun FavoritesFragment.showErrorView() = binding.bindSafely {
    containerFeedback.apply {
        isVisible = true
        feedbackContainer {
            title = "Erro"
            illustrationRes = com.albuquerque.designsystem.R.drawable.ds_illu_generic_error
        }
    }
}