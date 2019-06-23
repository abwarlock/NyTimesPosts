package com.abdev.nytimesposts.viewmodels

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.abdev.nytimesposts.R
import com.abdev.nytimesposts.adapters.PostAdapter
import com.abdev.nytimesposts.daos.PostDao
import com.abdev.nytimesposts.models.Posts
import com.abdev.nytimesposts.networking.PostApi
import com.abdev.nytimesposts.utils.PrefManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostListViewModels(private val postDao: PostDao, private val context: Context) : BaseViewModel() {

    @Inject
    lateinit var postApi: PostApi
    private lateinit var subscription: Disposable
    private var retrieveNewPost = false
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }
    val onRetrievePost = { retrieveNewPost: Boolean -> loadWithNewPosts(retrieveNewPost) }

    val postListAdapter: PostAdapter = PostAdapter()

    init {
        loadPosts()
    }

    private fun loadWithNewPosts(retrieveNewPost: Boolean) {
        this.retrieveNewPost = retrieveNewPost
        loadPosts()
    }
    private fun loadPosts() {
        val value = PrefManager.getValue(context)
        subscription = Observable.fromCallable {
            if (retrieveNewPost) {
                postDao.deleteAll(value)
            }
            postDao.getAll(value)
        }.concatMap { dbPostList ->
                if (dbPostList.isEmpty()) {
                    val postList: ArrayList<Posts> = ArrayList(0)
                    postApi.getPosts(value, "IERip91h9sWSwgbyhIdp8webJYLw0wlp").concatMap { postResp ->
                        postList.add(postResp)
                        postDao.InsertAll(*postList.toTypedArray())
                        Observable.just(postList)
                    }
                } else {
                    Observable.just(dbPostList)
                }
            }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result -> onRetrievePostListSuccess(result) },
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

    private fun onRetrievePostListSuccess(respList: List<Posts>?) {
        if (respList != null && respList.isNotEmpty()) {
            postListAdapter.updatePostList(respList[0].results)
        } else {
            postListAdapter.updatePostList(ArrayList(0))
        }
    }

    private fun onRetrievePostSuccess(resp: Posts) {
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