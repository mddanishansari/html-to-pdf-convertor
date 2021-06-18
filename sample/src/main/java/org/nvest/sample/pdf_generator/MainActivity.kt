package org.nvest.sample.pdf_generator

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import org.nvest.html_to_pdf.HtmlToPdfConvertor
import org.nvest.sample.pdf_generator.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var htmlToPdfConvertor: HtmlToPdfConvertor
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize HtmlToPdfConvertor
        htmlToPdfConvertor = HtmlToPdfConvertor(this)


        binding.btnConvert.setOnClickListener {
            if (shouldAskPermission()) {
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    STORAGE_PERMISSION_REQUEST
                )
            } else {
                convertHtmlToPdf()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                convertHtmlToPdf()
            } else {
                Toast.makeText(
                    this,
                    "Storage permission denied, enabled it from settings",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun convertHtmlToPdf() {
        // start loading
        binding.loader.isVisible = true

        // define output file location and html content
        val pdfLocation = File(getPdfFilePath(), "${System.currentTimeMillis()}.pdf")
        val htmlString = htmlContent()

        // start conversion
        htmlToPdfConvertor.convert(
            pdfLocation = pdfLocation,
            htmlString = htmlString,
            onPdfGenerationFailed = { exception ->
                // something went wrong, stop loading and log the exception
                binding.loader.isVisible = false
                exception.printStackTrace()
                Toast.makeText(this, "Check logs for error", Toast.LENGTH_SHORT).show()
            },
            onPdfGenerated = { pdfFile ->
                // pdf was generated, stop the loading and open it
                binding.loader.isVisible = false
                openPdf(pdfFile)
            })
    }

    private fun shouldAskPermission(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
    }

    private fun openPdf(pdfFilePath: File) {
        try {
            val path = FileProvider.getUriForFile(
                this,
                "$packageName.fileprovider",
                pdfFilePath
            )
            val pdfIntent = Intent(Intent.ACTION_VIEW)
            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            pdfIntent.setDataAndType(path, "application/pdf")
            startActivity(pdfIntent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            Toast.makeText(this, "No application found to open pdf file", Toast.LENGTH_SHORT).show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Toast.makeText(
                this,
                "Unable to open ${pdfFilePath.name}, please try again later.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getPdfFilePath(): File? {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            }
            else -> {
                File(Environment.getExternalStorageDirectory().toString() + "/Documents/")
            }
        }
    }

    private fun htmlContent() =
        """
        <!DOCTYPE html>
        <html>
        
        <body>
        
            <h1>My First Heading</h1>
        
            <p>My first paragraph.</p>
        
        </body>
        
        </html>
        """.trimIndent()

    companion object {
        const val STORAGE_PERMISSION_REQUEST = 1
    }
}