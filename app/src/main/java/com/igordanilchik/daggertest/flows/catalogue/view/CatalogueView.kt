package com.igordanilchik.daggertest.flows.catalogue.view

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.igordanilchik.daggertest.common.mvp.view.AppBaseView
import com.igordanilchik.daggertest.data.Categories
import com.igordanilchik.daggertest.flows.catalogue.router.CatalogueRouter

/**
 * @author Igor Danilchik
 */
@StateStrategyType(OneExecutionStateStrategy::class)
interface CatalogueView: AppBaseView, CatalogueRouter {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showCategories(categories: Categories)
    fun showProgress()
    fun hideProgress()
    fun showEmptyState()
    fun hideEmptyState()
    fun showError(throwable: Throwable)
}