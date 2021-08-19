package com.danbamitale.cognizanttest.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.DifferCallback
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.danbamitale.cognizanttest.databinding.AlbumListItemBinding
import com.danbamitale.cognizanttest.models.Album
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class AlbumAdapter @Inject constructor(@ActivityContext val context: Context) : PagedListAdapter<Album, AlbumAdapter.AlbumViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = getItem(position)
        album?.let(holder::bind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = AlbumListItemBinding.inflate(LayoutInflater.from(context),parent, false)
        return AlbumViewHolder(binding)
    }

    class AlbumViewHolder(private val binding: AlbumListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.titleView.text = album.title
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Album>() {
            override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
                return oldItem == newItem
            }
        }
    }
}