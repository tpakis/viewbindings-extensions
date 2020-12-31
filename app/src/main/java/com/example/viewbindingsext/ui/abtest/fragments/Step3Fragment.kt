package com.example.viewbindingsext.ui.abtest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.viewbindingsext.R
import com.example.viewbindingsext.bindView

class Step3Fragment : ABTestFragment() {

    private val button: Button by bindView(R.id.button)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step3_baseline, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.text = resources.getString(R.string.end_step, stepNumber)
        button.setOnClickListener {
            activityViewModel.endedStepNavigateToNext(stepNumber)
        }
    }
}