package com.ten.twenty.task.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.ten.twenty.task.R
import com.ten.twenty.task.databinding.ActivityMainBinding
import com.ten.twenty.task.extension.beGone
import com.ten.twenty.task.extension.beVisible

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = {
            ActivityMainBinding.inflate(it)
        }

    companion object {
        val moviesFragment = MoviesFragment()
        val searchFragment = SearchFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        transactFragment(moviesFragment)
    }

    private fun hideAllFragment() {
        supportFragmentManager.fragments.forEach {
            supportFragmentManager.beginTransaction()
                .hide(it)
                .commit()
        }
    }

    private fun transactFragment(fragment: Fragment) {
        if (fragment.isVisible)
            return
        hideAllFragment()
        if (!fragment.isAdded) {
            supportFragmentManager.beginTransaction()
                .add(R.id.navHostContainer, fragment, fragment.tag)
                .commit()
        }
        supportFragmentManager.beginTransaction()
            .show(fragment)
            .commit()
    }

    private fun initViews() {
        hideKeyboard()
        with(binding) {
            edtSearchBar.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (s.toString().isNotEmpty())
                        mySharedViewModel.searchMovies(s.toString())
                }
            })

            imgSearchMovies.setOnClickListener {
                transactFragment(searchFragment)
                searchLayout.beVisible()
            }
            imgCancel.setOnClickListener {
                hideKeyboard()
                transactFragment(moviesFragment)
                searchLayout.beGone()
            }
        }
    }

}