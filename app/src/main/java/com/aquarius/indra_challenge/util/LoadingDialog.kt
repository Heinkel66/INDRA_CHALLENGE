package com.aquarius.indra_challenge.util

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.aquarius.indra_challenge.databinding.DialogLoadingBinding

class LoadingDialog(context: Context) {

    private val dialog: Dialog = Dialog(context)

    init {
        val binding = DialogLoadingBinding.inflate(LayoutInflater.from(context))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
    }

    fun show() {
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }
}