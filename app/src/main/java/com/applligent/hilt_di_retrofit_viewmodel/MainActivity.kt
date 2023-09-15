package com.applligent.hilt_di_retrofit_viewmodel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.applligent.hilt_di_retrofit_viewmodel.LoadingBar.LoadingBarActivity
import com.applligent.hilt_di_retrofit_viewmodel.biometric.BiometricActivity
import com.applligent.hilt_di_retrofit_viewmodel.clickAnimationOrWidget.animation.ClickAnimationActivity
import com.applligent.hilt_di_retrofit_viewmodel.common.Utils.LanguageConfig
import com.applligent.hilt_di_retrofit_viewmodel.common.Utils.SharedPrefsLanguage
import com.applligent.hilt_di_retrofit_viewmodel.dataStoreAndNavigation.DataStoreActivity
import com.applligent.hilt_di_retrofit_viewmodel.databinding.ActivityMainBinding
import com.applligent.hilt_di_retrofit_viewmodel.firebase.activity.FirebaseSignUpActivity
import com.applligent.hilt_di_retrofit_viewmodel.fromTutorial.Adapter.PostAdapter
import com.applligent.hilt_di_retrofit_viewmodel.fromTutorial.MainViewModel
import com.applligent.hilt_di_retrofit_viewmodel.fromTutorial.Model.Post
import com.applligent.hilt_di_retrofit_viewmodel.ourApiGet.Ui.ForGetActivity
import com.applligent.hilt_di_retrofit_viewmodel.ourApiPost.Ui.ForPostActivity
import com.applligent.hilt_di_retrofit_viewmodel.room.RoomActivity
import com.applligent.hilt_di_retrofit_viewmodel.sqlLite.SQLiteActivity
import com.applligent.hilt_di_retrofit_viewmodel.swipeGesture.SwipeGestureActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var postAdapter: PostAdapter
    private val viewModel: MainViewModel by viewModels()
    var prefsLanguage: SharedPrefsLanguage? = null
    var COUNTRY_CODE = "en"

    //splash
    //var contentHasLoaded = false
    //change language

    override fun attachBaseContext(newBase: Context?) {
        prefsLanguage = SharedPrefsLanguage(newBase!!)
        val languageCode: String? = prefsLanguage?.locale
        val context: Context = LanguageConfig.changeLanguage(newBase, languageCode)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*startLoadingContent()
        setupSplashScreen(splashScreen)*/
        setUi()
        viewModel.getPost()
        viewModel.postLiveData.observe(this, Observer {response->
            postAdapter.setData(response as ArrayList<Post>)
        })
        setOnClickListener()
    }

    /*private fun startLoadingContent() {
        // For this example, the Timer delay represents awaiting a response from a network call
        Timer().schedule(3000){
            contentHasLoaded = true
        }
    }*/
   /* private fun setupSplashScreen(splashScreen: SplashScreen) {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (contentHasLoaded) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else false
                }
            }
        )

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideBack = ObjectAnimator.ofFloat(
                splashScreenView.view,
                View.TRANSLATION_X,
                0f,
                -splashScreenView.view.width.toFloat()
            ).apply {
                interpolator = DecelerateInterpolator()
                duration = 800L
                doOnEnd { splashScreenView.remove() }
            }

            slideBack.start()
        }
    }*/

    private fun setOnClickListener() {
        binding.thisToGetActivity.setOnClickListener {
            val i = Intent(this,ForGetActivity::class.java)
            startActivity(i)
        }

        binding.thisToPostActivity.setOnClickListener {
            val i = Intent(this, ForPostActivity::class.java)
            startActivity(i)
        }

        binding.thisToLoadingActivity.setOnClickListener {
            val i = Intent(this, LoadingBarActivity::class.java)
            startActivity(i)
        }

        binding.thisToSqlLiteActivity.setOnClickListener {
            val i = Intent(this, SQLiteActivity::class.java)
            startActivity(i)
        }

        binding.thisToRoomActivity.setOnClickListener {
            val i = Intent(this, RoomActivity::class.java)
            startActivity(i)
        }

        binding.thisToBiometricActivity.setOnClickListener {
            val i = Intent(this, BiometricActivity::class.java)
            startActivity(i)
        }

        binding.thisToFirebaseSignUp.setOnClickListener {
            val i = Intent(this,FirebaseSignUpActivity::class.java)
            startActivity(i)
        }

        binding.thisToDataStore.setOnClickListener {
            val i = Intent(this,DataStoreActivity::class.java)
            startActivity(i)
        }

        binding.thisToAnimationActivity.setOnClickListener {
            val i = Intent(this, ClickAnimationActivity::class.java)
            startActivity(i)
        }

        binding.thisToSwipeGesture.setOnClickListener {
            val i = Intent(this, SwipeGestureActivity::class.java)
            startActivity(i)
        }

        binding.selectHindi.setOnClickListener {
            COUNTRY_CODE = "hi"
            prefsLanguage?.locale = COUNTRY_CODE
            val i = Intent(this, MainActivity::class.java);
            startActivity(i)
        }

        binding.selectEnglish.setOnClickListener {
            COUNTRY_CODE = "en"
            prefsLanguage?.locale = COUNTRY_CODE
            val i = Intent(this, MainActivity::class.java);
            startActivity(i)
        }

    }


    private fun setUi() {
        postAdapter= PostAdapter(this, ArrayList())
        binding.recyclerView.apply {
            setHasFixedSize(true)
            //layoutManager = LinearLayoutManager(this@MainActivity)
            adapter=postAdapter
        }
    }
}