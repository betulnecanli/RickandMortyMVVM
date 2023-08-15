package com.betulnecanli.rickandmortymvvm.utils

import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.betulnecanli.rickandmortymvvm.ui.MainActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

fun ChipGroup.getTextChipChecked(): String {
    val selectedId: Int = this.checkedChipId
    return if (selectedId > -1) findViewById<Chip>(selectedId).text.toString() else ""
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.click(click: () -> Unit) {
    setOnClickListener { click() }
}

fun Fragment.showProgress(show: Boolean) {
    if (requireActivity() !is MainActivity) return
    (requireActivity() as MainActivity).let {
        if (show) {
            it.showProgress()
            return@let
        }
        it.hideProgress()
    }
}

