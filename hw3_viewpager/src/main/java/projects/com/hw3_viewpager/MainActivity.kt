package projects.com.hw3_viewpager

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import projects.com.hw3_viewpager.Fragment.QuestionnaireFragment
import projects.com.hw3_viewpager.Fragment.ViewPagerFragment
import projects.com.hw3_viewpager.Model.Question
import projects.com.hw3_viewpager.Repository.QuestionRepository
import projects.com.hw3_viewpager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ViewPagerFragment())
            .commit()
    }
}