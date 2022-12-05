package com.example.library.presentation.activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.library.R
import com.example.library.presentation.fragment.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeHolder, MainFragment.newInstance())
            .commit()
    }
}