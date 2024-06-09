package com.example.bvdtest.featureDashBoard.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bvdtest.R
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


class QrCodeScannerFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_qr_code_scanner, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scannerLauncher.launch(
            ScanOptions().setPrompt("Scan QR Code")
                .setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        )
    }

    private val scannerLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(ScanContract()){result ->
        if (result.contents == null){
            Toast.makeText(requireActivity(),"scan Cancelled",Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(requireActivity(),"scan "+result.contents,Toast.LENGTH_LONG).show()
        }

    }
}