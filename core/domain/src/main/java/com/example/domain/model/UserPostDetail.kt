package com.example.domain.model

import com.example.data.model.Comment


data class UserPostDetail(
    val userPost: UserPost,
    val author: PostAuthor?,
    val comments: List<PostComment>,
    val favorite: Boolean
) {
    companion object {
       fun mock(userId: Int, postId: Int, favorite: Boolean) : UserPostDetail =
           UserPostDetail(
               userPost = UserPost(
                   userId = userId,
                   id = postId,
                   title = "",
                   body = "",
                   favorite = favorite
               ),
               author = PostAuthor(userId, "","",
                   AuthorAddress("","","", geo = AuthorGeoLocation(lat = 0.0, lng=0.0), zipcode = ""),
                   "", "", AuthorCompany(name = "", "", "")
               ),
               comments = listOf(PostComment(postId, postId, "","","")),
               favorite = favorite
           )
    }
}