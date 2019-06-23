package com.abdev.nytimesposts.viewmodels

import androidx.lifecycle.MutableLiveData
import com.abdev.nytimesposts.models.Result

class PostViewModel : BaseViewModel() {
    private val postTitle = MutableLiveData<String>()
    private val postBody = MutableLiveData<String>()

    fun bind(postResult: Result) {
        postTitle.value = postResult.title
        postBody.value = postResult.abstract
    }

    fun getPostTitle(): MutableLiveData<String> {
        return postTitle
    }

    fun getPostBody(): MutableLiveData<String> {
        return postBody
    }
}