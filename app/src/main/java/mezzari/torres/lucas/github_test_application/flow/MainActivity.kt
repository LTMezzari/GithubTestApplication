package mezzari.torres.lucas.github_test_application.flow

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import mezzari.torres.lucas.conductor.source.generic.implementation.BaseActivity
import mezzari.torres.lucas.github_test_application.R
import mezzari.torres.lucas.github_test_application.dagger.DaggerHelper
import mezzari.torres.lucas.github_test_application.conductor.MainConductor
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    override lateinit var conductor: MainConductor

    init {
        DaggerHelper.mainComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}