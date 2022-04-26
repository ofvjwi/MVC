package com.example.androidmvc.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

object Utils {

    fun customDialog(
        context: Context?,
        title: String,
        message: String,
        dialogListener: DialogListener
    ) {
        context?.let { it ->
            val builder = AlertDialog.Builder(it)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setCancelable(true)
            builder.setPositiveButton("OK") { dialogInterface: DialogInterface?, _: Int ->
                dialogListener.onPositiveClick()
                dialogInterface?.dismiss()
            }
            builder.setNegativeButton("NO")
            { dialogInterface: DialogInterface?, _: Int ->
                dialogListener.onNegativeClick()
                dialogInterface?.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

    interface DialogListener {
        fun onPositiveClick()
        fun onNegativeClick()
    }
}

