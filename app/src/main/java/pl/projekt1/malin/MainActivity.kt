package pl.projekt1.malin

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
    val qrId by viewModel.qrId.collectAsState()
    val location by viewModel.location.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ScanContract()
    ) { result: ScanIntentResult ->
        val qrContent = result.contents

        if (qrContent.isNullOrBlank()) {
            Toast.makeText(activity, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(activity, "QR: $qrContent", Toast.LENGTH_LONG).show()
            viewModel.handleQrContent(qrContent)
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                launcher.launch(ScanOptions())
            }
        ) {
            Text("Scan QR Code")
        }

        Spacer(modifier = Modifier.height(16.dp))

        qrId?.let {
            Text(
                text = "ID: $it",
                style = MaterialTheme.typography.titleMedium
            )
        }

        location?.let { (lat, lon) ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Lat: ${String.format("%.5f", lat)}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Lon: ${String.format("%.5f", lon)}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
