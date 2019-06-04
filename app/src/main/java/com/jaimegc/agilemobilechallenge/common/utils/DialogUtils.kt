package com.jaimegc.agilemobilechallenge.common.utils

import android.app.AlertDialog
import android.content.Context
import com.jaimegc.agilemobilechallenge.R
import com.jaimegc.agilemobilechallenge.common.extensions.resToText

class DialogUtils {

    companion object {
        fun showUserNotFound(ctx: Context?) {
            ctx?.run {
                showAlertDialog(ctx, ctx.resToText(R.string.user_not_found))
            }
        }

        fun showUserUnknown(ctx: Context?) {
            ctx?.run {
                showAlertDialog(ctx, ctx.resToText(R.string.user_unknown))
            }
        }

        fun showError(ctx: Context?) {
            ctx?.run {
                showAlertDialog(ctx, ctx.resToText(R.string.generic_error))
            }
        }

        fun showReposNotFound(ctx: Context?) {
            ctx?.run {
                showAlertDialog(ctx, ctx.resToText(R.string.repos_not_found_error))
            }
        }

        private fun showAlertDialog(
            ctx: Context,
            description: String
        ) {
            val dialog = AlertDialog.Builder(ctx)
                .setMessage(description)
                .setPositiveButton(ctx.resToText(R.string.ok)) { _, _ -> }
                .create()

            dialog.show()
        }
    }
}