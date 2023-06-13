package online.transporteari.chambea.presentation.feature.requestTransport.infrastructure.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import online.transporteari.chambea.R
import online.transporteari.chambea.databinding.ActivityRequestTransportBinding
import online.transporteari.chambea.presentation.base.BaseChambeaActivity
import online.transporteari.chambea.presentation.feature.authentication.welcome.infrastructure.ui.WelcomeFragment


class RequestTransportActivity : BaseChambeaActivity(){

    private lateinit var binding: ActivityRequestTransportBinding
    //override lateinit var component: RequestTransportComponent
    private var requestTransportFragment: RequestTransportFragment? = null

    private var toolbar: Toolbar? = null
    private var actionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestTransportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeToolbar()
        init(savedInstanceState)
    }

    fun initializeToolbar() {
        /*toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar!!.setNavigationIcon(R.drawable.ic_arrow_back)
        val navigationIcon = toolbar!!.navigationIcon
        //val color = ContextCompat.getColor(this, R.color.app_taxi_grey_very_hard)
        val tintedIcon = DrawableCompat.wrap(navigationIcon!!).mutate()
        //DrawableCompat.setTint(tintedIcon, color)
        toolbar!!.navigationIcon = tintedIcon
        setSupportActionBar(toolbar)

        actionBar = supportActionBar
        actionBar?.setTitle("Taylor Swift")
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        Tools().setCompleteSystemBarLight(this)*/
    }

    fun init(savedInstanceState: Bundle?) {
        if(savedInstanceState == null) {
            requestTransportFragment = RequestTransportFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.contentMain, requestTransportFragment!!)
                .commit()
        }
    }
}