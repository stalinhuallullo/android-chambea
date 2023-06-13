package online.transporteari.chambea.presentation.feature.authentication.welcome.infrastructure.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import online.transporteari.chambea.R
import online.transporteari.chambea.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private var welcomeFragment: WelcomeFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            welcomeFragment = WelcomeFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.contentMain, welcomeFragment!!)
                .commit()
        }
    }
}