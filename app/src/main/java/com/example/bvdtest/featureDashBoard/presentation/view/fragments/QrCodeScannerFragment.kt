package com.example.bvdtest.featureDashBoard.presentation.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bvdtest.databinding.FragmentQrCodeScannerBinding
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


class QrCodeScannerFragment : Fragment() {

    private lateinit var binding: FragmentQrCodeScannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentQrCodeScannerBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btScanning.setOnClickListener {
//            val intentIntegrator = IntentIntegrator(getActivity())
//                .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
//                .setPrompt("Scan a QR Code")
//                .setCameraId(0)  // Use a specific camera of the device
//                .setBeepEnabled(true)
//                .setBarcodeImageEnabled(true)
//                .initiateScan();
            launchQrCodeScanner()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult? =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                binding.tvScannedResult.text = "No Results Found..."
            } else {
                // Handle successful scan
                binding.tvScannedResult.text = result.contents
                // Process the scanned data
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun launchQrCodeScanner() {
        scannerLauncher.launch(
            ScanOptions().setPrompt("Scan QR Code")
                .setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                .setBeepEnabled(true)
        )
    }

    private val scannerLauncher =
        registerForActivityResult<ScanOptions, ScanIntentResult>(ScanContract()) { result ->
            if (result.contents == null) {
                Toast.makeText(requireActivity(), "scan Cancelled", Toast.LENGTH_LONG).show()
                binding.tvScannedResult.text = "No Results Found..."
            } else {
                Toast.makeText(requireActivity(), "scan " + result.contents, Toast.LENGTH_LONG)
                    .show()
                binding.tvScannedResult.text = result.contents

            }

        }
}