package com.example.viewbindingsext.ui.abtest.variation1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.viewbindingsext.R
import com.example.viewbindingsext.bindView
import com.example.viewbindingsext.ui.abtest.fragments.ABTestFragment

class Step1FragmentV1 : ABTestFragment() {

    private lateinit var buttonLeak: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step1_variant1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // this will leak if we don't set it to null onDestroyView
        buttonLeak = view.findViewById(R.id.button)
        buttonLeak.text = resources.getString(R.string.end_step, stepNumber)
        buttonLeak.setOnClickListener {
            activityViewModel.endedStepNavigateToNext(stepNumber)
        }
    }
}