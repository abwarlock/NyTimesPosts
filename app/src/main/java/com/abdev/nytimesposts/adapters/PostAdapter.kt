package com.abdev.nytimesposts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdev.nytimesposts.R
import com.abdev.nytimesposts.databinding.ItemPostBinding
import com.abdev.nytimesposts.models.Result
import com.abdev.nytimesposts.viewmodels.PostViewModel

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private lateinit var postList: List<Result>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPostBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_post, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (::postList.isInitialized) postList.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    fun updatePostList(postList: List<Result>) {
        this.postList = postList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = PostViewModel()

        fun bind(post: Result) {
            viewModel.bind(post)
            binding.viewModel = viewModel
        }
    }
}