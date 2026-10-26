package com.example.myshop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myshop.databinding.FragmentCartBinding
import com.example.myshop.databinding.FragmentCheckoutBinding

class CheckoutFragment : Fragment() {
    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){

            // untuk menerima data yang dikirim dari fragment address
            findNavController().currentBackStackEntry
                ?.savedStateHandle?.let { handle ->
                    handle.getLiveData<String>("address")
                        .observe(viewLifecycleOwner) { res ->
                            edtAddress.setText(res)
                        }
                }

            btnDone.setOnClickListener {
                findNavController().navigateUp()
            }

            val args: CheckoutFragmentArgs by navArgs()
            txtProductName.setText(args.productName)
            edtAddress.setOnClickListener {
                val action =
                    CheckoutFragmentDirections.actionCartFragmentToAddressFragment()
                findNavController().navigate(action)
            }
        }

    }
}