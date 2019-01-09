package com.igordanilchik.coroutinestest.flows.catalogue.view

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import com.igordanilchik.coroutinestest.R
import com.igordanilchik.coroutinestest.common.mvp.view.BaseFragment
import com.igordanilchik.coroutinestest.data.Categories
import com.igordanilchik.coroutinestest.flows.catalogue.builder.CatalogueModule
import com.igordanilchik.coroutinestest.flows.catalogue.presenter.CataloguePresenter
import com.igordanilchik.coroutinestest.ui.adapter.CategoriesAdapter

/**
 * @author Igor Danilchik
 */
class CatalogueFragment: BaseFragment(), CatalogueView, CategoriesAdapter.CategoriesCallback {

    @BindView(R.id.catalogue_recycler_view)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.swipe_container)
    lateinit var swipeContainer: SwipeRefreshLayout
    @BindView(R.id.empty_state_container)
    lateinit var emptyStateContainer: LinearLayout


    @InjectPresenter
    lateinit var presenter: CataloguePresenter

    override val layoutResID = R.layout.fragment_catalogue

    override val baseTitle = R.string.farfor_title

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeContainer.setOnRefreshListener(presenter::onRefresh)
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onDestroyView() {
        recyclerView.adapter = null

        super.onDestroyView()
    }

    override fun onCategoryClicked(category: Categories.Category) =
            presenter.onCategoryClicked(category)


    override fun showCategories(categories: Categories) {
        (recyclerView.adapter as? CategoriesAdapter)?.apply {
            appendOrUpdate(categories.categories)
        } ?: run {
            recyclerView.adapter = CategoriesAdapter(categories, this)
        }
    }

    override fun showError(throwable: Throwable) {
        Snackbar.make(recyclerView, "Error: " + throwable.message, Snackbar.LENGTH_LONG)
                .show()
    }

    override fun showProgress() {
        swipeContainer.post { swipeContainer.isRefreshing = true }
    }

    override fun hideProgress() {
        swipeContainer.post { swipeContainer.isRefreshing = false }
    }

    override fun showEmptyState() {
        emptyStateContainer.visibility = View.VISIBLE
    }

    override fun hideEmptyState() {
        emptyStateContainer.visibility = View.GONE
    }

    override fun goToCategory(id: Int, name: String) {
        val directions = CatalogueFragmentDirections.toOffersFragment(name)
            .setCategoryId(id)
        view?.findNavController()?.navigate(directions)
    }

    @ProvidePresenter
    fun providePresenter(): CataloguePresenter {
        return appComponent().plusCatalogueComponent(CatalogueModule()).presenter()
    }

}