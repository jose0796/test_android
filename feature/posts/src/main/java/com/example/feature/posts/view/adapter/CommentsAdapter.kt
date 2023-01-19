package com.example.feature.posts.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.PostComment
import com.example.feature.posts.databinding.CommentsLayoutItemBinding

class CommentsAdapter(
    var comments: List<PostComment>
) : RecyclerView.Adapter<CommentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder =
        CommentsViewHolder(CommentsLayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = comments.size
    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) = holder.bind(comments[position])

    fun update(newComments: List<PostComment>) {
        this.comments = newComments
        notifyDataSetChanged()
    }
}

class CommentsViewHolder(
    val binding: CommentsLayoutItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(comments: PostComment) {

        binding.authorEmail.text = comments.email
        binding.title.text = comments.name
        binding.body.text = comments.body
    }
}
