package pl.projekt1.malin.ui.theme

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.projekt1.malin.data.network.NetworkClient

class MainViewModel : ViewModel() {

    private val _qrContent = MutableStateFlow<String?>(null)
    val qrContent: StateFlow<String?> = _qrContent
    
    private val _qrId = MutableStateFlow<String?>(null)
    val qrId: StateFlow<String?> = _qrId
    
    private val _location = MutableStateFlow<Pair<Double, Double>?>(null)
    val location: StateFlow<Pair<Double, Double>?> = _location

    fun handleQrContent(content: String) {
        _qrContent.value = content
        val uri = Uri.parse(content)
        val finalId = uri.getQueryParameter("qrText") ?: uri.lastPathSegment ?: content
        _qrId.value = finalId
        fetchLocationSimple(finalId)
    }

    fun fetchLocationSimple(qrId: String) {
        viewModelScope.launch {
            try {
                val response = NetworkClient.arcgisService.fetchLocation("qr_text='$qrId'")
                response.features.firstOrNull()?.geometry?.let {
                    _location.value = it.y to it.x
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Fetch failed: ${e.message}")
            }
        }
    }

    fun clearQrContent() {
        _qrContent.value = null
        _qrId.value = null
    }
}
