package pl.projekt1.malin.ui.theme

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import android.util.Log

class MainViewModel : ViewModel() {

    companion object {
        private const val TAG = "pw.MainViewModel"
    }

    private val _qrContent = MutableStateFlow<String?>(null)
    val qrContent: StateFlow<String?> = _qrContent

    fun handleQrContent(content: String) {
        Log.d(TAG, "\nQR zeskanowany: $content")
        _qrContent.value = content
    }

    fun clearQrContent() {
        _qrContent.value = null
    }
}