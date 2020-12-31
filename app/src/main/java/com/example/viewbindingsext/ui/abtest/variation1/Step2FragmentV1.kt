package com.example.viewbindingsext.ui.abtest.variation1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.viewbindingsext.R
import com.example.viewbindingsext.bindView
import com.example.viewbindingsext.ui.abtest.fragments.ABTestFragment

class Step2FragmentV1 : ABTestFragment() {

    // this will also leak and also it won't trigger a second click after you return to this fragment (next step -> back -> next step??)
    private val buttonLeak: Button by lazy {
        requireView().findViewById<Button>(R.id.button)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step2_variant1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonLeak.text = resources.getString(R.string.end_step, stepNumber)
        buttonLeak.setOnClickListener {
            activityViewModel.endedStepNavigateToNext(stepNumber)
        }
    }
}