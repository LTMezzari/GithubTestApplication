package mezzari.torres.lucas.github_test_application.flow.fragments.repository

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import io.noties.markwon.Markwon
import io.noties.markwon.image.glide.GlideImagesPlugin
import mezzari.torres.lucas.conductor.source.generic.implementation.BaseFragment
import mezzari.torres.lucas.github_test_application.R
import mezzari.torres.lucas.github_test_application.archive.bindTo
import mezzari.torres.lucas.github_test_application.archive.observe
import mezzari.torres.lucas.github_test_application.conductor.MainConductor
import mezzari.torres.lucas.github_test_application.dagger.DaggerHelper
import mezzari.torres.lucas.github_test_application.databinding.FragmentRepositoryBinding
import mezzari.torres.lucas.github_test_application.model.Repository
import mezzari.torres.lucas.github_test_application.model.User
import javax.inject.Inject

/**
 * @author Lucas T. Mezzari
 * @since 13/08/2020
 */
class RepositoryFragment: BaseFragment() {

    @Inject override lateinit var conductor: MainConductor

    init {
        DaggerHelper.mainComponent.inject(this)
    }

    private val viewModel: RepositoryFragmentViewModel by lazy {
        ViewModelProvider(this)[RepositoryFragmentViewModel::class.java]
    }

    private lateinit var dataBinding: FragmentRepositoryBinding

    private val markDown: Markwon by lazy {
        Markwon
            .builder(requireContext())
            .usePlugin(GlideImagesPlugin.create(requireContext()))
            .build()
    }

    var user: User? set(value) {
        viewModel.user = value
    } get() = viewModel.user

    var repository: Repository? set(value) {
        viewModel.repository = value
    } get() = viewModel.repository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_repository, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.viewModel = viewModel

        dataBinding.apply {
            tvFullName.bindTo(this@RepositoryFragment.viewModel.fullName, viewLifecycleOwner)
            tvDescription.bindTo(this@RepositoryFragment.viewModel.description, viewLifecycleOwner)
            tvCreatedDate.bindTo(this@RepositoryFragment.viewModel.createdDate, viewLifecycleOwner)
            tvUpdatedDate.bindTo(this@RepositoryFragment.viewModel.updatedDate, viewLifecycleOwner)
            tvStars.bindTo(this@RepositoryFragment.viewModel.stars, viewLifecycleOwner)
            tvLanguage.bindTo(this@RepositoryFragment.viewModel.language, viewLifecycleOwner)

            btnRepository.setOnClickListener {
                next()
            }
        }

        viewModel.overview.observe(viewLifecycleOwner) {
            markDown.setMarkdown(dataBinding.tvMarkdown, it ?: "")
        }

        viewModel.getOverview()
    }
}