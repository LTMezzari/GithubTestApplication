package mezzari.torres.lucas.github_test_application.flow.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import mezzari.torres.lucas.conductor.source.generic.implementation.BaseFragment
import mezzari.torres.lucas.github_test_application.R
import mezzari.torres.lucas.github_test_application.archive.observe
import mezzari.torres.lucas.github_test_application.conductor.MainConductor
import mezzari.torres.lucas.github_test_application.dagger.DaggerHelper
import mezzari.torres.lucas.github_test_application.databinding.FragmentSearchBinding
import mezzari.torres.lucas.github_test_application.model.User
import javax.inject.Inject

/**
 * @author Lucas T. Mezzari
 * @since 12/08/2020
 */
class SearchFragment: BaseFragment() {

    @Inject
    override lateinit var conductor: MainConductor

    private lateinit var dataBinding: FragmentSearchBinding

    private val viewModel: SearchFragmentViewModel by lazy {
        ViewModelProvider(this)[SearchFragmentViewModel::class.java]
    }

    init {
        DaggerHelper.mainComponent.inject(this)
    }

    var user: User? set(value) {
        viewModel.user.value = value
    } get() = viewModel.user.value

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.viewModel = viewModel

        dataBinding.btnSearch.setOnClickListener {
            viewModel.getUser()
        }

        viewModel.isValid.observe(this) {
            dataBinding.btnSearch.isEnabled = it ?: false
        }

        viewModel.hasError.observe(this) {
            dataBinding.tvErrorMessage.visibility = if (it == true) View.VISIBLE else View.GONE
        }

        viewModel.user.observe(this) {
            it ?: return@observe
            next()
        }
    }
}