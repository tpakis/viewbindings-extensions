package com.example.viewbindingsext.ui.abtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viewbindingsext.OneTimeEvent

class ActivityViewModel(private val variation: ABTestVariation) : ViewModel() {

    private val _navEvent = MutableLiveData<OneTimeEvent<Int>>()
    val navEvent: LiveData<OneTimeEvent<Int>> = _navEvent

    fun endedStepNavigateToNext(currentStep: Int) {
        _navEvent.value = OneTimeEvent(currentStep.plus(1))
    }

    @Suppress("UNCHECKED_CAST")
    class ActivityViewModelFactory(
        private val variation: ABTestVariation
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            (ActivityViewModel(variation) as T)
    }
}