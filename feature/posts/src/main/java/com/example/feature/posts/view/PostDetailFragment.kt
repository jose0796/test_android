package com.example.feature.posts.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.domain.model.UserPostDetail
import com.example.feature.posts.viewmodel.PostDetailUiState
import com.example.feature.posts.viewmodel.PostDetailViewModel
import com.example.feature.posts.databinding.FragmentPostDetailBinding
import com.example.feature.posts.view.adapter.CommentsAdapter
import com.test.core.common.extensions.invisible
import com.test.core.common.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostDetailFragment : Fragment() {

    private var _binding : FragmentPostDetailBinding?  = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PostDetailViewModel>()
    private val args by navArgs<PostDetailFragmentArgs>()

    private lateinit var commentsAdapter : CommentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.postDetailUiState.collect(uiStateCollector)
            }
        }

        viewModel.getDetail(args.postId)
    }

    private val uiStateCollector : FlowCollector<PostDetailUiState> = FlowCollector {
        when(it) {
            is PostDetailUiState.Success -> {
                binding.progressBar.invisible()
                loadPostDetails(it.post)
                binding.mainView.visible()
            }

            is PostDetailUiState.Loading -> {
                binding.progressBar.visible()
            }

            is PostDetailUiState.Error -> {
                binding.error.root.visible()
                binding.progressBar.invisible()
            }
            else -> {}
        }
    }

    private fun loadPostDetails(postDetail: UserPostDetail) {

        binding.title.text = postDetail.userPost.title
        binding.description.text = postDetail.userPost.body

        binding.authorName.text = postDetail.author?.name
        binding.authorEmail.text = postDetail.author?.email
        binding.authorAddress.text = postDetail.author?.completeAddress
        binding.authorWebsite.text = postDetail.author?.website
        binding.authorPhone.text = postDetail.author?.phone

        commentsAdapter = CommentsAdapter(postDetail.comments)
        binding.commentsList.adapter = commentsAdapter

    }
}