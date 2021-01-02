package com.example.viewbindingsext

import android.app.Activity
import android.util.Log
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalStateException

class BindViewDelegate<T>(
    private val createView: () -> T,
    private val getLifecycle: () -> Lifecycle
) : Lazy<T>, LifecycleObserver {

    private var view: T? = null

    private val lifecycle: Lifecycle?
        get() = try {
            getLifecycle()
        } catch (e: IllegalStateException) {
            Log.e("BindViewDelegate", e.message)
            null
        }

    override val value: T
        get() {
            if (view == null) {
                lifecycle?.removeObserver(this)
                view = createView()
                lifecycle?.addObserver(this)
            }
            @Suppress("UNCHECKED_CAST")
            return view as T
        }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        reset()
    }

    private fun reset() {
        lifecycle?.removeObserver(this)
        view = null
    }

    override fun isInitialized(): Boolean = view != null
}

fun <T : View> Fragment.bindView(@IdRes resource: Int): Lazy<T> = BindViewDelegate(
    createView = { requireView().findViewById<T>(resource) },
    getLifecycle = { viewLifecycleOwner.lifecycle }
)

fun <T : View> Activity.bindView(@IdRes res: Int): Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(res) }
}

fun <T : View> View.bindView(@IdRes res: Int): Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(res) }
}

fun <T : View> RecyclerView.ViewHolder.bindView(@IdRes res: Int): Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return lazy(LazyThreadSafetyMode.NONE) { itemView.findViewById<T>(res) }
}