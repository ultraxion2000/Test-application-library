package com.example.library.presentation.adapters

import android.content.Context
import android.content.Intent
import android.media.CamcorderProfile.get
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.library.repository.BookModal
import com.example.library.R
import com.example.library.presentation.activity.BookDetailsActivity
import com.example.library.databinding.BookItemBinding
import com.example.library.presentation.fragment.BookDetailsFragment
import com.squareup.picasso.Picasso

class BookAdapter(
    private var bookList: ArrayList<BookModal>,
    private var ctx: Context
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val binding = BookItemBinding.bind(itemView)

        val bookTitleTV: TextView = binding.idTVBookName
        val bookPagesTV: TextView = binding.idTVBookPages
        val bookIV: ImageView = binding.idIVBook
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.book_item,
            parent, false
        )
        return BookViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {

        val bookInfo = bookList.get(position)
        Picasso.get().load(bookInfo.thumbnail).into(holder.bookIV)
        holder.bookTitleTV.text = bookInfo.title
        holder.bookPagesTV.text = "Pages : " + bookInfo.pageCount


        holder.itemView.setOnClickListener {
            val i = Intent(ctx, BookDetailsActivity::class.java)
            i.putExtra("title", bookInfo.title)
            i.putExtra("subtitle", bookInfo.subtitle)
            i.putExtra("authors", bookInfo.authors)
            i.putExtra("publisher", bookInfo.publisher)
            i.putExtra("publishedDate", bookInfo.publishedDate)
            i.putExtra("description", bookInfo.description)
            i.putExtra("pageCount", bookInfo.pageCount)
            i.putExtra("smallThumbnail", bookInfo.thumbnail)
            i.putExtra("previewLink", bookInfo.previewLink)
            i.putExtra("infoLink", bookInfo.infoLink)
            i.putExtra("buyLink", bookInfo.buyLink)

            ctx.startActivity(i)
        }

    }

    override fun getItemCount(): Int {
        return bookList.size
    }

}