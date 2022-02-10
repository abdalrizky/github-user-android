package com.abdalrizky.githubuser.ui.about

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.abdalrizky.githubuser.R

class AboutDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle(R.string.app_name)
                .setMessage(R.string.dialog_about_message)
                .setPositiveButton(R.string.dialog_ok, null)
            builder.create()
        }
    }
}