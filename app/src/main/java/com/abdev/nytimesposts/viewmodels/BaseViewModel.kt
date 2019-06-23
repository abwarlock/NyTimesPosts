package com.abdev.nytimesposts.viewmodels

import androidx.lifecycle.ViewModel
import com.abdev.nytimesposts.injectors.DaggerViewModelInjector
import com.abdev.nytimesposts.networking.NetworkModule

abstract class BaseViewModel : ViewModel() {
    val injector = DaggerViewModelInjector.builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is PostListViewModels -> injector.inject(this)
        }
    }

}