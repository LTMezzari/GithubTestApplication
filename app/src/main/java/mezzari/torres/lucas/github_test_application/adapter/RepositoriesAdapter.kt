package mezzari.torres.lucas.github_test_application.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_empty.view.*
import mezzari.torres.lucas.github_test_application.R
import mezzari.torres.lucas.github_test_application.databinding.RowRepositoryBinding
import mezzari.torres.lucas.github_test_application.model.Repository

/**
 * @author Lucas T. Mezzari
 * @since 13/08/2020
 */
class RepositoriesAdapter(context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var _items: ArrayList<Repository> = ArrayList()
    var items: List<Repository> set(value) {
        _items = ArrayList(value)
        notifyDataSetChanged()
    } get() = _items

    var isLoading: Boolean = true
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onRepositoryClick: ((Repository) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_ITEM -> {
                RepositoriesViewHolder(DataBindingUtil.inflate(inflater, R.layout.row_repository, parent, false))
            }

            VIEW_TYPE_EMPTY -> {
                DefaultViewHolder(inflater.inflate(R.layout.row_empty, parent, false))
            }

            else -> {
                DefaultViewHolder(inflater.inflate(R.layout.row_loading, parent, false))
            }
        }
    }

    override fun getItemCount(): Int {
        return if (isLoading || _items.isEmpty()) {
            1
        } else {
            _items.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            isLoading -> {
                VIEW_TYPE_LOADING
            }
            _items.isEmpty() -> {
                VIEW_TYPE_EMPTY
            }
            else -> {
                VIEW_TYPE_ITEM
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_ITEM -> {
                (holder as RepositoriesViewHolder).also {
                    val repository = _items[position]
                    it.bind(repository)
                    it.itemView.setOnClickListener {
                        onRepositoryClick?.invoke(repository)
                    }
                }
            }

            VIEW_TYPE_EMPTY -> {
                (holder as DefaultViewHolder).itemView.tvEmptyMessage.setText(R.string.message_empty_repositories)
            }
        }
    }

    class RepositoriesViewHolder(private val dataBinding: RowRepositoryBinding): RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(repository: Repository) {
            dataBinding.repository = repository
        }
    }

    class DefaultViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    companion object {
        private const val VIEW_TYPE_LOADING = 0
        private const val VIEW_TYPE_EMPTY = 1
        private const val VIEW_TYPE_ITEM = 2
    }
}