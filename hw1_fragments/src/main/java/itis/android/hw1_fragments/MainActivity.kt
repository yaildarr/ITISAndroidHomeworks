package itis.android.hw1_fragments

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import itis.android.hw1_fragments.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var viewBinding: ActivityMainBinding


    private val mainContainerId : Int = R.id.FragmentContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        enableEdgeToEdge()
        supportFragmentManager.beginTransaction()
            .add(mainContainerId, FirstFragment())
            .commit()
    }
    fun navigate(navClass: NavigateClasses, bundle: Bundle? = null){
        if (navClass.equals(NavigateClasses.SECOND)) {
            supportFragmentManager.findFragmentById(mainContainerId)?.let {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    .detach(it)
                    .setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    .add(
                        mainContainerId,
                        bundle?.let { it1 -> SecondFragment.getInstance(it1) } ?: SecondFragment())
                    .addToBackStack(null)
                    .commit()
            }
        } else if (navClass.equals(NavigateClasses.FRSECONTDOTHIRD)){
            supportFragmentManager.findFragmentById(mainContainerId)?.let {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    .replace(mainContainerId, bundle?.let { it1 -> ThirdFragment.getInstance(it1) } ?: ThirdFragment() )
                    .addToBackStack(null)
                    .commit()
            }
        } else if (navClass.equals(NavigateClasses.THIRD)) {
            supportFragmentManager.findFragmentById(mainContainerId)?.let {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    .replace(
                        mainContainerId,
                        bundle?.let {SecondFragment.getInstance(it) } ?: SecondFragment())
                    .addToBackStack(null)
                    .commit()
            }
            supportFragmentManager.findFragmentById(mainContainerId)?.let {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    .replace(mainContainerId, bundle?.let { ThirdFragment.getInstance(it) } ?: ThirdFragment() )
                    .addToBackStack(null)
                    .commit()
            }
        }
    }



    companion object{
        val secondFragment: SecondFragment = SecondFragment()
    }
}