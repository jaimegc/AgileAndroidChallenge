package com.jaimegc.agilemobilechallenge.ui.model.viewmodel

import com.jaimegc.agilemobilechallenge.ui.adapter.holder.factory.ViewItemsFactory

abstract class ItemViewModel {
    abstract operator fun invoke(viewItemsFactory: ViewItemsFactory): Int
}