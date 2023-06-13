package online.transporteari.chambea.presentation.feature.proofPayment.infrastructure.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import online.transporteari.chambea.databinding.ActivityProofPaymentBinding

class ProofPaymentActivity : AppCompatActivity() {
    lateinit var binding: ActivityProofPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProofPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}