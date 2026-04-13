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
import pl.projekt1.malin.data.dataSources.BeaconDataSource
import pl.projekt1.malin.ui.theme.MALINTheme
import pl.projekt1.malin.ui.theme.MainViewModel


class MainActivity : ComponentActivity() {
    companion object{
        private const val BEACONS_FILE_NAME =
            "beacons.json"
    }

    private val dataSource = BeaconDataSource(
        inputStreamProvider = {
            assets.open(BEACONS_FILE_NAME)
        } )
    private val viewModel = MainViewModel(dataSource)

    override fun onCreate(
        savedInstanceState: Bundle?
    ){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MALINTheme {
                Scaffold(modifier =
                            Modifier.fillMaxSize()) {innerPadding ->
                    BeaconList(
                        viewModel = viewModel,
                        modifier =
                            Modifier.padding(innerPadding)
                    ) }
            }
        }
        viewModel.loadBeacons()
    }
}

@Composable
fun BeaconList(
    viewModel: MainViewModel,
    modifier: Modifier
){
    val context = LocalContext.current
    val activity = context as Activity
    val state by viewModel.uiState.collectAsState()
    val launcher = rememberLauncherForActivityResult(
        contract = ScanContract()
    ) { result: ScanIntentResult ->
        val qrContent = result.contents
        if (qrContent.isNullOrBlank()) {
            //viewModel.cancelQrScanning()
            Toast.makeText(activity, "Cancelled", Toast.LENGTH_LONG).show();
        } else {
            //viewModel.handleQrContent(qrContent)
            Toast.makeText(activity, "Scanned: $qrContent", Toast.LENGTH_LONG).show();
        }
    }
    Button(
        onClick = {
            launcher.launch(ScanOptions())
        },
        modifier = Modifier,
    ) {
        Text("Scan QR Code")
    }


}





