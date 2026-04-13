package pl.projekt1.malin

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import pl.projekt1.malin.ui.theme.MALINTheme
import pl.projekt1.malin.ui.theme.MainViewModel


class MainActivity : ComponentActivity() {

    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MALINTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    QrScannerScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun QrScannerScreen(
    viewModel: MainViewModel,
    modifier: Modifier
){
    val context = LocalContext.current
    val activity = context as Activity

    val qr by viewModel.qrContent.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ScanContract()
    ) { result: ScanIntentResult ->
        val qrContent = result.contents
        if (qrContent.isNullOrBlank()) {
            Toast.makeText(activity, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            viewModel.handleQrContent(qrContent)
        }
    }

    Button(
        onClick = {
            launcher.launch(ScanOptions())
        },
        modifier = modifier,
    ) {
        Text("Scan QR Code")
    }

    qr?.let {
        Text("Scanned: $it")
    }
}




