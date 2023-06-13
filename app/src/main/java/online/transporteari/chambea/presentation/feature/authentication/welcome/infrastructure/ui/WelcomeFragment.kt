package online.transporteari.chambea.presentation.feature.authentication.welcome.infrastructure.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.PermissionRequest
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import online.transporteari.chambea.R
import online.transporteari.chambea.databinding.FragmentWelcomeBinding
import online.transporteari.chambea.presentation.base.BaseChambeaFragment
import online.transporteari.chambea.presentation.common.utils.NetworkUtil
import online.transporteari.chambea.presentation.common.widget.CustomButton
import online.transporteari.chambea.presentation.feature.authentication.welcome.application.providers.AuthProvider
import online.transporteari.chambea.presentation.feature.dashboard.infrastructure.ui.DashboardActivity
import java.util.*


class WelcomeFragment : BaseChambeaFragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private var callbackManager: CallbackManager? = null
    private val authProvider = AuthProvider()


    private val FIELD_PROFILE = "public_profile"
    private val FIELD_USER_HOMETOWN = "user_hometown"
    private val FIELD_USER_BIRTHDAY = "user_birthday"
    private val FIELD_USER_AGE_RANGE = "user_age_range"
    private val FIELD_USER_GENDER = "user_gender"
    private val FIELD_USER_LINK = "user_link"
    private val FIELD_USER_FRIENDS = "user_friends"
    private val FIELD_USER_LOCATION = "user_location"
    private val FIELD_USER_LIKES = "user_likes"
    private val FIELD_USER_PHOTOS = "user_photos"
    private val FIELD_USER_VIDEOS = "user_videos"
    private val FIELD_USER_POSTS = "user_posts"
    private val FIELD_EMAIL = "email"

    private val tmp_email = "taylor@gmail.com"
    private val tmp_password = "12345678"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLoginFacebook!!.setOnClickListener {
            goToLoginFacebook()
        }
        binding.btnLoginGoogle.setOnClickListener{
            goToLoginGoogle()
        }
        binding.btnLoginApple.setOnClickListener{
            goToLoginApple()
        }
    }

    private fun goToLoginApple() {
        Toast.makeText(context(), "LOGIN CON APPLE", Toast.LENGTH_SHORT).show()
    }

    private fun goToLoginGoogle() {
        Toast.makeText(context(), "LOGIN CON GOOGLE", Toast.LENGTH_SHORT).show()
        authProvider.login(tmp_email, tmp_password).addOnCompleteListener {
            if (it.isSuccessful) {
                goToDashboard()
            }
            else {
                Toast.makeText(context(), "Error iniciando sesion", Toast.LENGTH_SHORT).show()
                Log.d("FIREBASE", "ERROR: ${it.exception.toString()}")
            }
        }
    }

    override fun context(): Context? {
        val activity = activity
        return activity?.applicationContext
    }

    fun goToLoginFacebook() {
        if (NetworkUtil.isThereInternetConnection(context())) {
            callbackManager = CallbackManager.Factory.create()
            //LoginManager.getInstance().logInWithReadPermissions( context(), Arrays.asList(FIELD_PROFILE, FIELD_EMAIL) )

            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(
                FIELD_PROFILE,
                FIELD_USER_HOMETOWN,
                FIELD_USER_BIRTHDAY,
                FIELD_USER_AGE_RANGE,
                FIELD_USER_GENDER,
                FIELD_USER_LINK,
                FIELD_USER_FRIENDS,
                FIELD_USER_LOCATION,
                FIELD_USER_LIKES,
                FIELD_USER_PHOTOS,
                FIELD_USER_VIDEOS,
                FIELD_USER_POSTS,
                FIELD_EMAIL))
            /*LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        println("============ Facebook token: " + loginResult.accessToken.token)
                        //startActivity(Intent(applicationContext, AuthenticatedActivity::class.java))
                    }

                    override fun onCancel() {
                        Log.d("MainActivity", "Facebook onCancel.")

                    }

                    override fun onError(error: FacebookException) {
                        Log.d("MainActivity", "Facebook onError.")

                    }
                })*/


        } else {
            showAlertMessage(
                R.drawable.ic_offline,
                getString(R.string.error_network_title),
                getString(R.string.error_network_message),
                getString(R.string.accept),
                "",
                null,
                null
            )
        }
    }

    fun goToDashboard() {
        val intent = Intent(activity, DashboardActivity::class.java)
        intent.putExtra("name", "")
        startActivity(intent)
        activity?.finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

}


