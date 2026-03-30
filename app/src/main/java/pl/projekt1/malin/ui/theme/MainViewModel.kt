package pl.projekt1.malin.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

import pl.projekt1.malin.data.dataSources.BeaconDataSource
import pl.projekt1.malin.data.mappers.toDomain
import pl.projekt1.malin.domain.model.Beacon


class MainViewModel(
    private val beaconDataSource: BeaconDataSource
) : ViewModel() {

    companion object{
        private const val TAG = "pw.MainViewModel"
    }

    private val _beacons = MutableStateFlow<List<Beacon>>(emptyList())
    val beacons: StateFlow<List<Beacon>> = _beacons

    fun loadBeacons(){
        viewModelScope.launch{
            val result = beaconDataSource.loadBeacons()
            result.onSuccess { beaconDto ->
                _beacons.value = beaconDto.map { it.toDomain() }
            }.onFailure { error ->
                Log.e(TAG, "Błąd wczytywania beaconów", error)
            }
        }
    }
}