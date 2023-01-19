package com.example.feature.posts.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.UserPost
import com.example.feature.posts.R
import com.example.feature.posts.databinding.PostsLayoutItemBinding

class PostsAdapter(
    var posts: List<UserPost>,
    val listener: PostsListener? = null
) : RecyclerView.Adapter<PostsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder =
        PostsViewHolder(PostsLayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = posts.size
    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) = holder.bind(posts[position], listener)

    fun update(newPosts: List<UserPost>) {
        this.posts = newPosts
        notifyDataSetChanged()
    }
}

interface PostsListener {
    fun onClick(postId: Int, position: Int)
    fun onDeletePost(postId: Int, position: Int)
    fun onToggleFavorite(post : UserPost, position: Int)
}

class PostsViewHolder(
    val binding: PostsLayoutItemBinding,
    val listener: PostsListener? = null
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: UserPost, listener: PostsListener?) {
        binding.root.setOnClickListener { listener?.onClick(post.id, adapterPosition) }
        binding.title.text = post.title

        binding.favorite.setImageResource(
            if(post.favorite) R.drawable.ic_star
            else R.drawable.ic_star_outlined
        )

        binding.delete.setOnClickListener {
            listener?.onDeletePost(postId = post.id, adapterPosition)
        }

        binding.favorite.setOnClickListener {
            listener?.onToggleFavorite(post, adapterPosition)
        }
    }
}