package online.transporteari.chambea.presentation.feature.payment.infrastructure.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.globant.inkafarma.domain.entity.PaymentMethod
import online.transporteari.chambea.databinding.FragmentPaymentMethodBinding
import online.transporteari.chambea.presentation.base.BaseChambeaFragment


class PaymentMethodFragment : BaseChambeaFragment() {
    private lateinit var binding: FragmentPaymentMethodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentMethodBinding.inflate(layoutInflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        val bundle = arguments
        if (bundle != null) {
            println("bundle ===>  " + bundle)
            binding.rvPaymentMethods.setVisibility(View.VISIBLE)
        }
    }

    override fun context(): Context? {
        val activity = activity
        return activity?.applicationContext
    }

}