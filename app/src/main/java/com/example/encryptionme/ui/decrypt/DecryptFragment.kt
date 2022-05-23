package com.example.encryptionme.ui.decrypt

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.encryptionme.R
import com.example.encryptionme.databinding.FragmentDecryptBinding
import com.example.encryptionme.ui.MethodWithId

class DecryptFragment : Fragment() {

    private var _binding: FragmentDecryptBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val methodList = listOf(
        MethodWithId(0, "Cesar Method", false),
        MethodWithId(1, "Grid Method", false),
        MethodWithId(2, "Railway Fence Method", false),
        MethodWithId(3, "DES", false),
        MethodWithId(4, "AES", false),
        MethodWithId(5, "BlowfishKnowledge", false),
        MethodWithId(6, "RSA", false)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel =
            ViewModelProvider(this).get(DecryptViewModel::class.java)

        _binding = FragmentDecryptBinding.inflate(inflater, container, false)
        val root: View = binding.root
        with(binding) {
            this.methodsList.setText(getString(R.string.choose_encryption_algorithm), false)
            this.methodsList.setOnItemClickListener { adapterView, view, i, l ->
                val adapter = adapterView.adapter
                (adapter.getItem(i) as? MethodWithId)?.let {
                    if (!it.isSelected) {
                        for (itemId in 0 until adapter.count) {
                            (adapter.getItem(itemId) as MethodWithId).isSelected = false
                        }
                        (adapter.getItem(i) as MethodWithId).isSelected = true
                        viewModel.changeMethod(it.id)
                    }
                }
            }
            this.methodsList.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.list_item_method,
                    methodList)
            )
            this.encryptButtom.setOnClickListener {
                val text = this.text.text.toString().trim()
                val key = this.keyText.text.toString().trim()
                if (text.isEmpty()) {
                    this.textInput.error = getString(R.string.field_is_required)
                    return@setOnClickListener
                }
                if (key.isEmpty()) {
                    this.keyInput.error = getString(R.string.field_is_required)
                    return@setOnClickListener
                }
                val startTime = System.currentTimeMillis()
                val encryptedText = viewModel.decryptText(text, Integer.parseInt(key))
                val endTime = System.currentTimeMillis()
                this.resultText.setText(encryptedText)
                Toast.makeText(requireContext(), "Decryption time: ${endTime - startTime} ms", Toast.LENGTH_SHORT).show()
            }

            this.copy.setOnClickListener {
                copyText()
                Toast.makeText(requireContext(), getString(R.string.copy), Toast.LENGTH_SHORT).show()
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        binding.methodsList.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.list_item_method,
                methodList)
        )
    }

    protected fun copyText() {
        (requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(
            ClipData.newPlainText("Decrypted text", binding.resultText.text.toString()))
    }
}