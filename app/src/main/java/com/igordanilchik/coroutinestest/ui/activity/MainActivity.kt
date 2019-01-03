package com.igordanilchik.coroutinestest.ui.activity

import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.navigation.NavigationView
import com.igordanilchik.coroutinestest.R
import com.igordanilchik.coroutinestest.app.DaggerApplication
import com.igordanilchik.coroutinestest.common.di.ApplicationComponent
import com.igordanilchik.coroutinestest.common.factory.FragmentFactory
import com.igordanilchik.coroutinestest.extensions.replaceFragment
import com.igordanilchik.coroutinestest.ui.ViewContract

class MainActivity : AppCompatActivity(), ViewContract {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.drawer_layout)
    lateinit var drawer: DrawerLayout
    @BindView(R.id.nav_view)
    lateinit var navigationView: NavigationView

    private var onlyMapIsDisplayed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent().inject(this)

        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
        }

        val toggle = ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener { item ->
            selectDrawerItem(item)
            true
        }

        savedInstanceState?.let {
            onlyMapIsDisplayed = it.getBoolean(KEY_ONLY_MAP)
        } ?: run {
            val item = navigationView.menu.findItem(R.id.nav_catalogue)
            selectDrawerItem(item)
        }
    }

    private fun appComponent(): ApplicationComponent =
            DaggerApplication[applicationContext].appComponent

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_ONLY_MAP, onlyMapIsDisplayed)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        return when (id) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            if (onlyMapIsDisplayed) {
                finish()
            }
            super.onBackPressed()
        }
    }

    private fun selectDrawerItem(menuItem: MenuItem) {
        val fragment: Fragment

        when (menuItem.itemId) {
            R.id.nav_catalogue -> {
                fragment = FragmentFactory.categories()
                onlyMapIsDisplayed = false
            }
            R.id.nav_location -> {
                fragment = FragmentFactory.location()
                onlyMapIsDisplayed = true
            }
            else -> {
                fragment = FragmentFactory.categories()
                onlyMapIsDisplayed = false
            }
        }

        supportFragmentManager.popBackStackImmediate()
        replaceFragment(R.id.frame_content, fragment, false)

        menuItem.isChecked = true
        title = menuItem.title

        //drawer.closeDrawers();
        drawer.closeDrawer(GravityCompat.START)
    }

    override fun goToCategory(bundle: Bundle) {
        val fragment = FragmentFactory.offers(bundle)
        replaceFragment(R.id.frame_content, fragment, true)
    }

    override fun goToOffer(bundle: Bundle) {
        val fragment = FragmentFactory.offer(bundle)
        replaceFragment(R.id.frame_content, fragment, true)
    }

    companion object {

        const val KEY_ONLY_MAP = "KEY_ONLY_MAP"
        const val ARG_CATEGORY_ID = "ARG_CATEGORY_ID"
        const val ARG_OFFER_ID = "ARG_OFFER_ID"
    }
}
