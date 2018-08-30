package com.igordanilchik.coroutinestest.flows.offers.router

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * @author Igor Danilchik
 */
@StateStrategyType(OneExecutionStateStrategy::class)
interface OffersRouter {
    fun goToOffer(id: Int)
}