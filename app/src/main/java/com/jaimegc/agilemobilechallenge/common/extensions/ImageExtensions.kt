package com.jaimegc.agilemobilechallenge.common.extensions

import android.widget.ImageView
import com.jaimegc.agilemobilechallenge.R
import com.squareup.picasso.Picasso

fun ImageView.loadUrl(url: String?, imageScaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP, placeHolder: Int = R.drawable.profile_placeholder) {
    url?.let {

        if (url.isNotEmpty()) {
            Picasso.get().load(url).placeholder(R.drawable.profile_placeholder).into(this)
        }
    } ?: Picasso.get().load(placeHolder).into(this)

    scaleType = imageScaleType
}