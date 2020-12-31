package com.example.viewbindingsext.ui.abtest.variation1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.viewbindingsext.R
import com.example.viewbindingsext.bindView
import com.example.viewbindingsext.ui.abtest.fragments.ABTestFragment
import leakcanary.LeakCanary

class Step4FragmentV1 : ABTestFragment() {

    //this won't leak
    private val button: Button by bindView(R.id.button)
    private val buttonLeaksCheck: Button by bindView(R.id.button_leaks)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step4_variant1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.text = resources.getString(R.string.end_step, stepNumber)
        button.setOnClickListener {
            activityViewModel.endedStepNavigateToNext(stepNumber)
        }
        buttonLeaksCheck.setOnClickListener {
            LeakCanary.dumpHeap()
        }
    }
}