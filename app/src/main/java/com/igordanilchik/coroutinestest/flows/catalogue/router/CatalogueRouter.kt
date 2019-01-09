package com.igordanilchik.coroutinestest.flows.catalogue.router

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * @author Igor Danilchik
 */
@StateStrategyType(OneExecutionStateStrategy::class)
interface CatalogueRouter {
    fun goToCategory(id: Int, name: String)
}