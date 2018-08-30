package com.igordanilchik.coroutinestest.flows.catalogue.presenter

import com.igordanilchik.coroutinestest.data.Categories

/**
 * @author Igor Danilchik
 */
interface ICataloguePresenter {
    fun onRefresh()
    fun onCategoryClicked(category: Categories.Category)
}