package com.ten.twenty.task.presentation

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.ten.twenty.task.presentation.viewmodel.MoviesViewModel
import com.ten.twenty.task.presentation.viewmodel.MySharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseActivity<V : ViewBinding> : AppCompatActivity() {

    //Binding
    protected lateinit var binding: V
    protected abstract val bindingInflater: (LayoutInflater) -> V

    protected val moviesViewModel by viewModel<MoviesViewModel>()
    protected val mySharedViewModel by viewModel<MySharedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)

        handleBackPressed()
    }

    protected fun hideKeyboard() {
        try {
            val imm: InputMethodManager? =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
            imm?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        } catch (ignored: java.lang.Exception) {
        }
    }

    protected open fun onBackPress() {
        finish()
    }

    private fun handleBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ) {
                onBackPress()
            }
        } else {
            onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPress()
                }
            })
        }
    }
}