package com.example.viewbindingsext.ui.abtest

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.viewbindingsext.FIRST_STEP
import com.example.viewbindingsext.R
import com.example.viewbindingsext.STEP_BUNDLE_KEY
import leakcanary.LeakCanary

enum class ABTestVariation(val navGraphRes: Int) {
    BASELINE(R.navigation.baseline_navigation),
    VARIATION1(R.navigation.variant1_navigation),
}

class ABTestActivity : AppCompatActivity() {

    private val activityViewModel by viewModels<ActivityViewModel> {
        ActivityViewModel.ActivityViewModelFactory(
            abTestVariation
        )
    }

    private val abTestVariation: ABTestVariation by lazy {
        intent?.getSerializableExtra(KEY) as? ABTestVariation ?: ABTestVariation.BASELINE
    }

    private val navController: NavController
        get() = findNavController(R.id.nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abtest)
        setupNavigation(abTestVariation.navGraphRes)

        activityViewModel.navEvent.observe(this, Observer {
            handleNavigationEvent(it.getContentIfNotHandled())
        })
    }

    private fun setupNavigation(navGraphId: Int) {
        val graph = navController.navInflater.inflate(navGraphId)
        navController.setGraph(
            graph,
            bundleOf(STEP_BUNDLE_KEY to FIRST_STEP)
        )
    }

    private fun handleNavigationEvent(nextStep: Int?) {
        if (nextStep != null) {
            try {
                navController.navigate(R.id.next, bundleOf(STEP_BUNDLE_KEY to nextStep))
                // if there is no next step then finish
            } catch (e: IllegalArgumentException) {
                finish()
            }
        }
    }

    companion object {
        const val KEY = "TestVariation"

        fun startInABTestVariation(context: AppCompatActivity, variation: ABTestVariation) {
            val intent = Intent(context, ABTestActivity::class.java)
            intent.putExtra(KEY, variation)
            context.startActivity(intent)
        }
    }
}