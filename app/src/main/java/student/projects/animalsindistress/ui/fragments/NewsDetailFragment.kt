package student.projects.animalsindistress.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import student.projects.animalsindistress.R

class NewsDetailFragment : Fragment(R.layout.fragment_news_detail) {

    private val args: NewsDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Get the article slug from navigation arguments
        val slug = args.slug
        
        // For now, using default content - in a real app, would fetch article by slug
        val title = "News Article: $slug"
        val date = "2024"
        val paragraphs = listOf("Full article content for $slug will be displayed here.", "This would typically be fetched from a database or API based on the slug.")

        view.findViewById<TextView>(R.id.news_title)?.text = title
        view.findViewById<TextView>(R.id.news_date)?.text = date

        val container = view.findViewById<LinearLayout>(R.id.news_paragraphs)
        container?.removeAllViews()
        for (p in paragraphs) {
            val tv = TextView(requireContext())
            tv.text = p
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.topMargin = resources.getDimensionPixelSize(R.dimen.spacing_8)
            tv.layoutParams = lp
            container?.addView(tv)
        }
    }
}


