package com.abdev.nytimesposts.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.abdev.nytimesposts.R
import com.abdev.nytimesposts.adapters.PostAdapter
import com.abdev.nytimesposts.models.Posts
import com.abdev.nytimesposts.networking.PostApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostListViewModels : BaseViewModel() {

    @Inject
    lateinit var postApi: PostApi
    private lateinit var subscription: Disposable

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }

    val postListAdapter: PostAdapter = PostAdapter()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        subscription = postApi
            .getPosts("arts", "IERip91h9sWSwgbyhIdp8webJYLw0wlp")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { resp -> onRetrievePostListSuccess(resp) },
                { onRetrievePostListError() }
            )
    }

    private fun onRetrievePostListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostListSuccess(resp: Posts) {
        postListAdapter.updatePostList(resp.results)
    }

    private fun onRetrievePostListError() {
        errorMessage.value = R.string.post_error
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }


}