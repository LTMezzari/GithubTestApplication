package mezzari.torres.lucas.github_test_application.flow.fragments.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mezzari.torres.lucas.conductor.source.generic.implementation.BaseFragment
import mezzari.torres.lucas.github_test_application.R
import mezzari.torres.lucas.github_test_application.adapter.RepositoriesAdapter
import mezzari.torres.lucas.github_test_application.archive.observe
import mezzari.torres.lucas.github_test_application.conductor.MainConductor
import mezzari.torres.lucas.github_test_application.dagger.DaggerHelper
import mezzari.torres.lucas.github_test_application.databinding.FragmentRepositoriesBinding
import mezzari.torres.lucas.github_test_application.model.Repository
import mezzari.torres.lucas.github_test_application.model.User
import javax.inject.Inject

/**
 * @author Lucas T. Mezzari
 * @since 13/08/2020
 */
class RepositoriesFragment: BaseFragment() {
    @Inject override lateinit var conductor: MainConductor

    private val viewModel: RepositoriesFragmentViewModel by lazy {
        ViewModelProvider(this)[RepositoriesFragmentViewModel::class.java]
    }

    init {
        DaggerHelper.mainComponent.inject(this)
    }

    private lateinit var dataBinding: FragmentRepositoriesBinding

    private val adapter: RepositoriesAdapter by lazy {
        RepositoriesAdapter(requireContext()).apply {
            onRepositoryClick = {
                this@RepositoriesFragment.repository = it
                next()
            }
        }
    }

    var user: User? set(value) {
        viewModel.user = value
    } get() = viewModel.user

    var repository: Repository? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_repositories, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.rvRepositories.apply {
            adapter = this@RepositoriesFragment.adapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        viewModel.repositories.observe(viewLifecycleOwner) {
            adapter.items = it ?: arrayListOf()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            adapter.isLoading = it ?: false
        }

        viewModel.getRepositories()
    }
}