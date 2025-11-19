package student.projects.animalsindistress.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import student.projects.animalsindistress.R

class FAQFragment : Fragment(R.layout.fragment_faq) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Setup expand/collapse functionality for all 15 questions
        for (i in 1..15) {
            val questionId = resources.getIdentifier("faq_question_$i", "id", requireContext().packageName)
            val answerId = resources.getIdentifier("faq_answer_$i", "id", requireContext().packageName)
            val arrowId = resources.getIdentifier("faq_arrow_$i", "id", requireContext().packageName)
            
            val questionView = view.findViewById<View>(questionId)
            val answerView = view.findViewById<LinearLayout>(answerId)
            val arrowView = view.findViewById<TextView>(arrowId)
            
            questionView?.setOnClickListener {
                if (answerView?.visibility == View.VISIBLE) {
                    // Collapse
                    answerView.visibility = View.GONE
                    arrowView?.text = "▶"
                } else {
                    // Expand
                    answerView?.visibility = View.VISIBLE
                    arrowView?.text = "▼"
                }
            }
        }
    }
}


