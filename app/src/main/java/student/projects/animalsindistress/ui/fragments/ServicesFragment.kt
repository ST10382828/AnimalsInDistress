package student.projects.animalsindistress.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import student.projects.animalsindistress.R

class ServicesFragment : Fragment(R.layout.fragment_services) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val requestAid: Button? = view.findViewById(R.id.btn_request_medical_aid)
        val requestMobile: Button? = view.findViewById(R.id.btn_request_mobile_visit)

        val showDialog: () -> Unit = {
            val dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_request_medical_aid, null, false)
            val dialog = MaterialAlertDialogBuilder(requireContext())
                .setView(dialogView)
                .create()

            dialogView.findViewById<Button>(R.id.btn_cancel)?.setOnClickListener { dialog.dismiss() }
            dialogView.findViewById<Button>(R.id.btn_submit)?.setOnClickListener { dialog.dismiss() }

            dialog.show()
        }

        view.findViewById<View>(R.id.btn_request_medical_aid).setOnClickListener {
            showDialog()
        }

        view.findViewById<View>(R.id.btn_request_mobile_visit)?.setOnClickListener {
            showDialog()
        }
    }
}


