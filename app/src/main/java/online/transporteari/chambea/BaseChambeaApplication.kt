package online.transporteari.chambea

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.FirebaseApp


class BaseChambeaApplication : Application() {
    //private var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        AppEventsLogger.activateApp(this)
        initializeInjector()
        //initializeTimber()
        initializeFirebase()
        initializeFacebook()
        //initializeMarketingCloud()
    }

    private fun initializeInjector() {
        /*appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()*/
    }

    /*fun getAppComponent(): AppComponent? {
        return appComponent
    }*/

    private fun initializeFirebase() {
        FirebaseApp.initializeApp(this)
    }

    private fun initializeFacebook() {
        //FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);
    }

    /*override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }*/


    companion object {
        private var instance: BaseChambeaApplication? = null
        //private var appComponent: AppComponent? = null
        fun getInstance(): BaseChambeaApplication? {
            return instance
        }
    }
}