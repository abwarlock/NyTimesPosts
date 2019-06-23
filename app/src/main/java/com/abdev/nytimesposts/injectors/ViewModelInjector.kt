package com.abdev.nytimesposts.injectors

import com.abdev.nytimesposts.networking.NetworkModule
import com.abdev.nytimesposts.viewmodels.PostListViewModels
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(postViewModels: PostListViewModels)

    @Component.Builder
    interface Builder {

        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}