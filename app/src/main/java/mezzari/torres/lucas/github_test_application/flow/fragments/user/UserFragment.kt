package mezzari.torres.lucas.github_test_application.flow.fragments.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import io.noties.markwon.Markwon
import io.noties.markwon.image.glide.GlideImagesPlugin
import mezzari.torres.lucas.conductor.source.generic.implementation.BaseFragment
import mezzari.torres.lucas.conductor.source.path.Path
import mezzari.torres.lucas.github_test_application.R
import mezzari.torres.lucas.github_test_application.archive.bindTo
import mezzari.torres.lucas.github_test_application.archive.observe
import mezzari.torres.lucas.github_test_application.conductor.MainConductor
import mezzari.torres.lucas.github_test_application.dagger.DaggerHelper
import mezzari.torres.lucas.github_test_application.databinding.FragmentUserBinding
import mezzari.torres.lucas.github_test_application.model.User
import javax.inject.Inject

/**
 * @author Lucas T. Mezzari
 * @since 12/08/2020
 */
class UserFragment: BaseFragment() {

    @Inject override lateinit var conductor: MainConductor

    init {
        DaggerHelper.mainComponent.inject(this)
    }

    private val viewModel: UserFragmentViewModel by lazy {
        ViewModelProvider(this)[UserFragmentViewModel::class.java]
    }

    private lateinit var dataBinding: FragmentUserBinding

    var user: User? set(value) {
        viewModel.user = value
    } get() = viewModel.user

    private val markDown: Markwon by lazy {
        Markwon
            .builder(requireContext())
            .usePlugin(GlideImagesPlugin.create(requireContext()))
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.viewModel = viewModel

        dataBinding.apply {
            tvName.bindTo(this@UserFragment.viewModel.name, viewLifecycleOwner)
            tvJoinedDate.bindTo(this@UserFragment.viewModel.enteredAt, viewLifecycleOwner)
            tvCompany.bindTo(this@UserFragment.viewModel.company, viewLifecycleOwner)
            tvLocation.bindTo(this@UserFragment.viewModel.location, viewLifecycleOwner)
            tvUsername.bindTo(this@UserFragment.viewModel.username, viewLifecycleOwner)
            tvBio.bindTo(this@UserFragment.viewModel.bio, viewLifecycleOwner)
            tvFollowers.bindTo(this@UserFragment.viewModel.followers, viewLifecycleOwner)
            tvFollowing.bindTo(this@UserFragment.viewModel.following, viewLifecycleOwner)
        }

        viewModel.profileImage.observe(this) {
            if (!it.isNullOrEmpty()) {
                Glide.with(view.context)
                    .load(it)
                    .circleCrop()
                    .placeholder(R.drawable.ic_baseline_account_circle_24)
                    .into(dataBinding.ivProfile)
            }
        }
        
        viewModel.overview.observe(this) {
            markDown.setMarkdown(dataBinding.tvMarkdown, it ?: "")
        }

        dataBinding.btnRepositories.setOnClickListener {
            //Move to repositories
            next(UserFragmentPaths.REPOSITORIES)
        }

        dataBinding.btnProfile.setOnClickListener {
            //Open profile
            next(UserFragmentPaths.PROFILE)
        }

        dataBinding.tvSearch.setOnClickListener {
            //Go back to search
            next(UserFragmentPaths.SEARCH)
        }

        viewModel.loadOverview()
    }

    enum class UserFragmentPaths: Path {
        SEARCH,
        PROFILE,
        REPOSITORIES
    }
}