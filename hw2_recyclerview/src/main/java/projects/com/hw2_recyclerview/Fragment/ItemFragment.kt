package projects.com.hw2_recyclerview.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import projects.com.hw2_recyclerview.R
import projects.com.hw2_recyclerview.databinding.FragmentItemBinding


class ItemFragment : Fragment(R.layout.fragment_item) {

    private val binding: FragmentItemBinding by viewBinding(FragmentItemBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = requireArguments().getString(ARG_TITLE)
        val description = requireArguments().getString(ARG_DESCRIPTION)
        val image_url = requireArguments().getString(ARG_IMAGE_URL)

        with(binding){
            movieTitle.text = title
            movieDescription.text = description
            Glide.with(this@ItemFragment).load(image_url).into(movieImage)
        }
    }


    companion object{
        private const val ARG_TITLE = "ARG_TITLE"
        private const val ARG_DESCRIPTION = "ARG_DESCRIPTION"
        private const val ARG_IMAGE_URL = "ARG_IMAGE_URL"

        fun newInstance(title: String, imageUrl: String, description: String): ItemFragment {
            return ItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_IMAGE_URL, imageUrl)
                    putString(ARG_DESCRIPTION, description)
                }
            }
        }
    }

}