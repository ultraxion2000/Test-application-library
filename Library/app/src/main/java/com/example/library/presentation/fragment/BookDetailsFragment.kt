package com.example.library.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.library.databinding.FragmentBookDetailsBinding
import com.example.library.domain.MainViewModal
import com.example.library.presentation.adapters.BookAdapter
import com.example.library.repository.BookModal
import com.squareup.picasso.Picasso

class BookDetailsFragment : Fragment() {

    lateinit var titleTV: TextView
    lateinit var subtitleTV: TextView
    lateinit var publisherTV: TextView
    lateinit var descTV: TextView
    lateinit var pageTV: TextView
    lateinit var publisherDateTV: TextView
    lateinit var previewBtn: Button
    lateinit var buyBtn: Button
    lateinit var bookIV: ImageView

    private lateinit var binding: FragmentBookDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBookInfo()
    }


    private fun getBookInfo() {
        titleTV = binding.idTVTitle
        subtitleTV = binding.idTVSubTitle
        publisherTV = binding.idTVpublisher
        descTV = binding.idTVDescription
        pageTV = binding.idTVNoOfPages
        publisherDateTV = binding.idTVPublishDate
        previewBtn = binding.idBtnPreview
        buyBtn = binding.idBtnBuy
        bookIV = binding.idIVbook

        val title = getActivity()?.getIntent()?.getStringExtra("title")
        val subtitle = getActivity()?.getIntent()?.getStringExtra("subtitle")
        val publisher = getActivity()?.getIntent()?.getStringExtra("publisher")
        val publishedDate = getActivity()?.getIntent()?.getStringExtra("publishedDate")
        val description = getActivity()?.getIntent()?.getStringExtra("description")
        val pageCount = getActivity()?.getIntent()?.getStringExtra("pageCount")
        val thumbnail = getActivity()?.getIntent()?.getStringExtra("smallThumbnail")
        val previewLink = getActivity()?.getIntent()?.getStringExtra("previewLink")
        val infoLink = getActivity()?.getIntent()?.getStringExtra("infoLink")
        val buyLink = getActivity()?.getIntent()?.getStringExtra("buyLink")

        titleTV.setText(title)
        subtitleTV.setText(subtitle)
        publisherTV.setText(publisher)
        publisherDateTV.setText("Published On : " + publishedDate)
        descTV.setText(description)
        pageTV.setText("No Of Pages : " + pageCount)
        Picasso.get().load(thumbnail).into(bookIV)


        previewBtn.setOnClickListener {
            if (previewLink.isNullOrEmpty()) {
                Toast.makeText(
                    activity,
                    "No preview Link present",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            val uri: Uri = Uri.parse(previewLink)
            val i = Intent(Intent.ACTION_VIEW, uri)
            startActivity(i)
        }


        buyBtn.setOnClickListener {
            if (buyLink.isNullOrEmpty()) {
                Toast.makeText(
                    activity,
                    "No buy page present for this book",
                    Toast.LENGTH_SHORT
                ).show()
            }

            val uri = Uri.parse(buyLink)
            val i = Intent(Intent.ACTION_VIEW, uri)
            startActivity(i)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = BookDetailsFragment()
    }
}
