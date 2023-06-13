package online.transporteari.chambea.presentation.feature.payment.infrastructure.ui

import android.os.Bundle
import android.view.View
import online.transporteari.chambea.R
import online.transporteari.chambea.databinding.ActivityPaymentMethodBinding
import online.transporteari.chambea.presentation.base.BaseChambeaActivity

class PaymentMethodActivity : BaseChambeaActivity() {
    lateinit var binding: ActivityPaymentMethodBinding
    private var paymentMethodFragment: PaymentMethodFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentMethodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeToolbar()
        init(savedInstanceState)
    }
    private fun initializeToolbar() {
        //toolbar.setTitle(getString(R.string.choose_payment_method))
        //toolbar.setNavigationOnClickListener(View.OnClickListener { v: View? -> onBackPressed() })
    }
    fun init(savedInstanceState: Bundle?) {
        if(savedInstanceState == null) {
            paymentMethodFragment = PaymentMethodFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.contentMain, paymentMethodFragment!!)
                .commit()
        }
    }
}