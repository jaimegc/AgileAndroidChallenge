package com.jaimegc.agilemobilechallenge.ui.model.viewmodel

import com.jaimegc.agilemobilechallenge.ui.adapter.holder.factory.ViewItemsFactory

interface ItemViewModel {
    val id: String
    operator fun invoke(viewItemsFactory: ViewItemsFactory): Int
}