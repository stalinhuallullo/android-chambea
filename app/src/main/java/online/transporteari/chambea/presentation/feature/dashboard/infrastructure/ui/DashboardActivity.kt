package online.transporteari.chambea.presentation.feature.dashboard.infrastructure.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import online.transporteari.chambea.R
import online.transporteari.chambea.databinding.ActivityDashboardBinding
import online.transporteari.chambea.presentation.base.BaseChambeaActivity
import online.transporteari.chambea.presentation.common.utils.Tools

class DashboardActivity : BaseChambeaActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private var dashboardFragment: DashboardFragment? = null

    private var toolbar: Toolbar? = null
    private var actionBar: ActionBar? = null
    private var navigationView: NavigationView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeToolbar()
        initDrawerMenu()
        init(savedInstanceState)
    }

    fun initializeToolbar() {
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar!!.setNavigationIcon(R.drawable.ic_menu)
        val navigationIcon = toolbar!!.navigationIcon
        val color = ContextCompat.getColor(this, R.color.app_taxi_grey_very_hard)
        val tintedIcon = DrawableCompat.wrap(navigationIcon!!).mutate()
        DrawableCompat.setTint(tintedIcon, color)
        toolbar!!.navigationIcon = tintedIcon
        setSupportActionBar(toolbar)

        actionBar = supportActionBar
        actionBar?.setTitle(null)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        Tools().setCompleteSystemBarLight(this)
    }

    private fun initDrawerMenu() {
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            onNavigationItemClick(menuItem)
            drawer.closeDrawers()
            true
        }
    }

    fun init(savedInstanceState: Bundle?) {
        if(savedInstanceState == null) {
            dashboardFragment = DashboardFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.contentMain, dashboardFragment!!)
                .commit()
        }
    }

    private fun onNavigationItemClick(menuItem: MenuItem) {
        val id = menuItem.itemId
        if (id == R.id.nav_booking) {
            //startActivity(Intent(this, ActivityBooking::class.java))
            println("id ===> " + id)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        Tools().changeMenuIconColor(menu, resources.getColor(R.color.app_taxi_grey_very_hard))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_location -> {
                dashboardFragment?.onClickMoveCurrentLocation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}