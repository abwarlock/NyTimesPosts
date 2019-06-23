package com.abdev.nytimesposts

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdev.nytimesposts.databinding.ActivityMainBinding
import com.abdev.nytimesposts.utils.PrefManager
import com.abdev.nytimesposts.viewmodels.PostListViewModels
import com.abdev.nytimesposts.viewmodels.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: PostListViewModels
    private var errorSnackbar: Snackbar? = null
    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (PrefManager.getValue(this).isEmpty()) {
            PrefManager.setValue(this, resources.getStringArray(R.array.sectionList)[0])
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.layoutManager = LinearLayoutManager(this)
        binding.dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(PostListViewModels::class.java)
        title = "${getString(R.string.showing_post_for)} ${PrefManager.getValue(this)}"

        binding.viewModel = viewModel
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.onRetrievePost.invoke(true)
            binding.swipeRefresh.isRefreshing = false
        }
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) {
                showError(errorMessage)
            } else {
                hideError()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.commen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.section_menu -> {
                showSectionList()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSectionList() {
        val sectionArray = resources.getStringArray(R.array.sectionList)
        val selectedIndex = sectionArray.indexOf(PrefManager.getValue(this))
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Section")
        builder.setSingleChoiceItems(sectionArray, selectedIndex) { _, which ->
            PrefManager.setValue(this, sectionArray[which])
            title = "${getString(R.string.showing_post_for)} ${PrefManager.getValue(this)}"
            viewModel.onRetrievePost.invoke(false)
            if (::alertDialog.isInitialized) {
                alertDialog.dismiss()
            }
        }
        builder.setCancelable(true)
        alertDialog = builder.show()
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
