package com.igordanilchik.coroutinestest.flows.catalogue.presenter

import com.arellomobile.mvp.InjectViewState
import com.igordanilchik.coroutinestest.common.mvp.presenter.AppBasePresenter
import com.igordanilchik.coroutinestest.data.Categories
import com.igordanilchik.coroutinestest.flows.catalogue.model.ICatalogueModel
import com.igordanilchik.coroutinestest.flows.catalogue.view.CatalogueView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch
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

    private fun loadData() = launch(UI) {
        viewState.showProgress()

        Timber.d("request categories UI")
        launch(UI) {
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