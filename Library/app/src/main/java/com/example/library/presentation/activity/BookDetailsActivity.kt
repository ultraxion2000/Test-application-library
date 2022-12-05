package com.example.library.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.library.R
import com.example.library.presentation.fragment.BookDetailsFragment
import com.example.library.presentation.fragment.MainFragment

class BookDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeHolder2, BookDetailsFragment.newInstance())
            .commit()
    }
}