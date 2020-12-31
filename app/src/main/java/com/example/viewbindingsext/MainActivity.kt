package com.example.viewbindingsext

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.viewbindingsext.ui.abtest.ABTestActivity
import com.example.viewbindingsext.ui.abtest.ABTestVariation
import leakcanary.LeakCanary

class MainActivity : AppCompatActivity() {

    private val buttonBaseline: Button by bindView(R.id.button_go_to_baseline)
    private val buttonVariant1: Button by bindView(R.id.button_go_to_variant1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonBaseline.setOnClickListener {
            goToABTest(ABTestVariation.BASELINE)
        }
        buttonVariant1.setOnClickListener {
            goToABTest(ABTestVariation.VARIATION1)
        }
    }

    private fun goToABTest(variation: ABTestVariation) {
        ABTestActivity.startInABTestVariation(this, variation)
    }

}
