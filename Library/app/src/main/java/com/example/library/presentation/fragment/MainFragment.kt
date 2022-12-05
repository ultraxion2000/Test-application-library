package com.example.library.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.library.R
import com.example.library.presentation.adapters.BookAdapter
import com.example.library.repository.BookModal
import com.example.library.databinding.FragmentMainBinding
import com.example.library.domain.MainViewModal
import kotlinx.coroutines.NonDisposableHandle.parent


class MainFragment : Fragment() {

    lateinit var mRequestQueue: RequestQueue
    lateinit var booksList: ArrayList<BookModal>
    lateinit var loadingPB: ProgressBar
    lateinit var searchEdt: EditText
    lateinit var searchBtn: ImageButton

    private lateinit var adapter: BookAdapter

    private lateinit var binding: FragmentMainBinding

    private val model: MainViewModal by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingPB = binding.idLoadingPB
        searchEdt = binding.idEdtSearchBooks
        searchBtn = binding.idBtnSearch

        val searchEdt2 = "Kotlin"
        getBooksData(searchEdt2)

        searchBtn.setOnClickListener {
            loadingPB.visibility = View.VISIBLE
            if (searchEdt.text.toString().isNullOrEmpty()) {
                searchEdt.setError("Please enter search query")
            }
            getBooksData(searchEdt.getText().toString())
        }


    }
    private fun getBooksData(searchQuery: String){

        booksList = ArrayList()

        mRequestQueue = Volley.newRequestQueue(activity)

        mRequestQueue.cache.clear()

        val url = "https://www.googleapis.com/books/v1/volumes?q=$searchQuery"

        val queue = Volley.newRequestQueue(activity)

        val request = JsonObjectRequest(
            Request.Method.GET,
            url, null,
            { response ->
                loadingPB.setVisibility(View.GONE)
                try {
                    val itemsArray = response.getJSONArray("items")
                    for (i in 0 until itemsArray.length()) {
                        val itemsObj = itemsArray.getJSONObject(i)
                        val volumeObj = itemsObj.getJSONObject("volumeInfo")
                        val title = volumeObj.optString("title")
                        val subtitle = volumeObj.optString("subtitle")
                        val authorsArray = volumeObj.getJSONArray("authors")
                        val publisher = volumeObj.optString("publisher")
                        val publishedDate = volumeObj.optString("publishedDate")
                        val description = volumeObj.optString("description")
                        val pageCount = volumeObj.optInt("pageCount")
                        val imageLinks = volumeObj.optJSONObject("imageLinks")
                        val thumbnail = imageLinks.optString("smallThumbnail")
                        val previewLink = volumeObj.optString("previewLink")
                        val infoLink = volumeObj.optString("infoLink")
                        val saleInfoObj = itemsObj.optJSONObject("saleInfo")
                        val buyLink = saleInfoObj.optString("buyLink")
                        val authorsArrayList: ArrayList<String> = ArrayList()


                        if (authorsArray.length() != 0) {
                            for (j in 0 until authorsArray.length()) {
                                authorsArrayList.add(authorsArray.optString(i))
                            }
                        }
                        val bookInfo = BookModal(
                            title,
                            subtitle,
                            authorsArrayList,
                            publisher,
                            publishedDate,
                            description,
                            pageCount,
                            thumbnail,
                            previewLink,
                            infoLink,
                            buyLink
                        )
                        booksList.add(bookInfo)

                        model.liveDataCurrent.value = bookInfo
                    }
                    val adapter = context?.let { BookAdapter(booksList, it) }
                    binding.idRVBooks.layoutManager = GridLayoutManager(activity, 3)
                    binding.idRVBooks.adapter = adapter

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }, { error ->
                Toast.makeText(activity, "No books found..", Toast.LENGTH_SHORT)
                    .show()
            })
        queue.add(request)
    }


    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
