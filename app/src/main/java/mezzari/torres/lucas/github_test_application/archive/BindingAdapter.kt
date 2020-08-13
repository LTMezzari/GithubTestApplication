package mezzari.torres.lucas.github_test_application.archive

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import mezzari.torres.lucas.github_test_application.R

/**
 * @author Lucas T. Mezzari
 * @since 13/08/2020
 */
@BindingAdapter("imageFromUrl")
fun bindImageFromUrl (view: ImageView, imageUrl: String?) {
    if(!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .into(view)
    }
}