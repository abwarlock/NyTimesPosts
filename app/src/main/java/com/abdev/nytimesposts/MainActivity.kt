package com.abdev.nytimesposts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdev.nytimesposts.databinding.ActivityMainBinding
import com.abdev.nytimesposts.viewmodels.PostListViewModels
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: PostListViewModels
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.layoutManager = LinearLayoutManager(this)
        binding.dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        viewModel = ViewModelProviders.of(this).get(PostListViewModels::class.java)
        binding.viewModel = viewModel
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })
    }

    private fun showError(errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }
}
