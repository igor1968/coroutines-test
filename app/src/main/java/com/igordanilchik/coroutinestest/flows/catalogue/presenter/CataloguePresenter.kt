package com.igordanilchik.coroutinestest.flows.catalogue.presenter

import com.arellomobile.mvp.InjectViewState
import com.igordanilchik.coroutinestest.common.mvp.presenter.AppBasePresenter
import com.igordanilchik.coroutinestest.data.Categories
import com.igordanilchik.coroutinestest.flows.catalogue.model.ICatalogueModel
import com.igordanilchik.coroutinestest.flows.catalogue.view.CatalogueView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import timber.log.Timber


/**
 * @author Igor Danilchik
 */

@InjectViewState
class CataloguePresenter(
        val model: ICatalogueModel
) : AppBasePresenter<CatalogueView>(), ICataloguePresenter {


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.showEmptyState()
        loadData()
    }

    private fun loadData() = GlobalScope.launch(Dispatchers.Main) {
        viewState.showProgress()

        Timber.d("request categories UI")
        GlobalScope.launch(Dispatchers.Main) {
            try {
                model.categories().consumeEach {
                    Timber.d("update categories UI")
                    viewState.hideEmptyState()
                    viewState.showCategories(it)
                }
            } catch (e: Throwable) {
                viewState.showError(e)
            } finally {
                viewState.hideProgress()
            }
        }
    }

    override fun onRefresh() {
        loadData()
    }

    override fun onCategoryClicked(category: Categories.Category) = viewState.goToCategory(category.id)

}