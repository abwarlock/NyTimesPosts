package com.abdev.nytimesposts.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.abdev.nytimesposts.R
import com.abdev.nytimesposts.adapters.PostAdapter
import com.abdev.nytimesposts.daos.PostDao
import com.abdev.nytimesposts.models.Posts
import com.abdev.nytimesposts.networking.PostApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostListViewModels(private val postDao: PostDao) : BaseViewModel() {

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
        subscription = Observable.fromCallable { postDao.getAll }
            .concatMap { dbPostList ->
                if (dbPostList.isEmpty()) {
                    val postList: ArrayList<Posts> = ArrayList(0)
                    postApi.getPosts("arts", "IERip91h9sWSwgbyhIdp8webJYLw0wlp").concatMap { postResp ->
                        postList.add(postResp)
                        postDao.InsertAll(*postList.toTypedArray())
                        Observable.just(postList)
                    }
                } else {
                    Observable.just(dbPostList)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result -> onRetrievePostListSuccess(result) }
            )
    }

    private fun loadPosts1() {
        subscription = postApi
            .getPosts("arts", "IERip91h9sWSwgbyhIdp8webJYLw0wlp")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { resp -> onRetrievePostSuccess(resp) },
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