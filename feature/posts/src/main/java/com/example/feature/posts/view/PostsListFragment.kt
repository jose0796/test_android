package com.example.feature.posts.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.domain.model.UserPost
import com.example.feature.posts.viewmodel.PostsUiState
import com.example.feature.posts.viewmodel.PostsViewModel
import com.example.feature.posts.databinding.FragmentPostsListBinding
import com.example.feature.posts.view.adapter.PostsAdapter
import com.example.feature.posts.view.adapter.PostsListener
import com.test.core.common.extensions.invisible
import com.test.core.common.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostsListFragment : Fragment() {

    private var _binding: FragmentPostsListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<PostsViewModel>()

    private var adapter: PostsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostsListBinding.inflate(inflater, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.postsUiState.collect(uiStateCollector)
            }
        }
    }

    private val postsListener : PostsListener = object : PostsListener {
        override fun onClick(postId: Int, position: Int) {
            findNavController().navigate(
                PostsListFragmentDirections.actionPostsListFragmentToPostDetailFragment(postId)
            )
        }

        override fun onDeletePost(postId: Int, position: Int) {
            viewModel.deletePost(postId)
        }

        override fun onToggleFavorite(post: UserPost, position: Int) {
            viewModel.toggleFavorite(post.id, !post.favorite)
        }

    }

    private val uiStateCollector : FlowCollector<PostsUiState> = FlowCollector {
        when(it) {
            is PostsUiState.Success -> {
                binding.progressBar.invisible()
                if (binding.postList.adapter == null) {
                    adapter = PostsAdapter(it.posts, postsListener)
                    binding.postList.adapter = adapter
                }
                else {
                    adapter?.update(it.posts)
                }

                binding.postList.visible()
            }

            is PostsUiState.Loading -> {
                binding.progressBar.visible()
                binding.postList.invisible()
            }

            is PostsUiState.Error -> {
                binding.progressBar.visible()
                binding.progressBar.invisible()
            }
            else -> {}
        }
    }

}