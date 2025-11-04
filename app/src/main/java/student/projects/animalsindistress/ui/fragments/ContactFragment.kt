package student.projects.animalsindistress.ui.fragments

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import student.projects.animalsindistress.R
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.EditText
import java.net.NetworkInterface
import java.util.Collections

class ContactFragment : Fragment(R.layout.fragment_contact) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Phone section - dial when clicked
        view.findViewById<View>(R.id.phone_section)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0114660261"))
            startActivity(intent)
        }
        
        // Email section - send email when clicked
        view.findViewById<View>(R.id.email_section)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:animals@animalsindistress.org.za"))
            startActivity(intent)
        }
        
        // Social media buttons
        view.findViewById<View>(R.id.btn_facebook)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/tsfaid"))
            startActivity(intent)
        }
        
        view.findViewById<View>(R.id.btn_twitter)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/SaidFundraiser"))
            startActivity(intent)
        }
        
        view.findViewById<View>(R.id.btn_instagram)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/thesocietyforanimalsindistress/"))
            startActivity(intent)
        }
        
        // Submit contact form (send email + basic rate limit)
        view.findViewById<View>(R.id.btn_submit_contact)?.setOnClickListener {
            val fn = view.findViewById<EditText>(R.id.etFirstName)?.text?.toString()?.trim().orEmpty()
            val ln = view.findViewById<EditText>(R.id.etLastName)?.text?.toString()?.trim().orEmpty()
            val email = view.findViewById<EditText>(R.id.etEmail)?.text?.toString()?.trim().orEmpty()
            val phone = view.findViewById<EditText>(R.id.etPhone)?.text?.toString()?.trim().orEmpty()
            val subject = view.findViewById<EditText>(R.id.etSubject)?.text?.toString()?.trim().orEmpty()
            val message = view.findViewById<EditText>(R.id.etMessage)?.text?.toString()?.trim().orEmpty()

            if (fn.isEmpty() || ln.isEmpty() || email.isEmpty() || subject.isEmpty() || message.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Rate-limit: min 60s between sends per device
            val prefs = requireContext().getSharedPreferences("contact_prefs", android.content.Context.MODE_PRIVATE)
            val last = prefs.getLong("last_send_ts", 0L)
            val now = System.currentTimeMillis()
            val ip = getDeviceIpAddress() ?: "unknown"
            val lastIp = prefs.getLong("ip_${ip}_last_send", 0L)
            val minIntervalMs = 5 * 60_000L // 5 minutes
            if (now - last < minIntervalMs || now - lastIp < minIntervalMs) {
                val waitSec = ((minIntervalMs - (now - last)) / 1000).toInt()
                Toast.makeText(requireContext(), "Please wait ${waitSec}s before sending again", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Build email body (do not include device IP)
            val body = buildString {
                appendLine("New contact form submission")
                appendLine("Name: $fn $ln")
                appendLine("Email: $email")
                appendLine("Phone: $phone")
                appendLine("Subject: $subject")
                appendLine()
                appendLine(message)
                appendLine()
                appendLine("Timestamp: ${java.util.Date()}")
            }

            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.contact_email)))
                putExtra(Intent.EXTRA_SUBJECT, "[Contact] $subject")
                putExtra(Intent.EXTRA_TEXT, body)
            }
            try {
                startActivity(intent)
                // Persist both global and per-IP rate limit timestamps
                prefs.edit().putLong("last_send_ts", now).apply()
                val ipForStore = getDeviceIpAddress() ?: "unknown"
                prefs.edit().putLong("ip_${ipForStore}_last_send", now).apply()
            } catch (_: Exception) {
                Toast.makeText(requireContext(), "No email app found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getDeviceIpAddress(): String? {
        return try {
            val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
            interfaces.flatMap { iface -> Collections.list(iface.inetAddresses) }
                .firstOrNull { addr -> !addr.isLoopbackAddress && addr.hostAddress.indexOf(':') < 0 }
                ?.hostAddress
        } catch (_: Exception) { null }
    }
}


