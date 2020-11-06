package com.lmy.helloweather.viewmodle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lmy.helloweather.base.BaseViewModel
import com.lmy.helloweather.logic.repository.Repository
import com.lmy.helloweather.logic.repository.PlaceRepository
import com.lmy.helloweather.model.Place
import kotlinx.coroutines.launch

/**
 * @author Lmy
 * @功能:
 * @Creat 2020/5/21 10:41
 * @Compony 永远相信美好的事物即将发生
 */
class PlaceViewModle : BaseViewModel() {
    val placeList = ArrayList<Place>()
    private val _uiState = MutableLiveData<PlaceModel>()
    val uiState: LiveData<PlaceModel>
        get() = _uiState

    fun getaddressData(name: String) {
        viewModelScope.launch {
            val data = MutableLiveData<List<Place>>()
            checkResult(PlaceRepository.getSerchPlace(name), {
                data.value = it
                updateUiState(showSuccess = true, isShowProgress = false, placeResponse = data)
            }) {
                updateUiState(showSuccess = false, isShowProgress = false, showErrorText = it)
            }
        }
    }

    fun savePlace(place: Place) = Repository.savePlace(place)

    fun getSavedPlace() = Repository.getSavedPlace()

    fun isPlaceSaved() = Repository.isPlaceSaved()


    private fun updateUiState(
        showSuccess: Boolean = false,
        isRefresh: Boolean = false,
        isShowProgress: Boolean = false,
        showErrorText: String? = null,
        placeResponse: MutableLiveData<List<Place>>? = null
    ) {
        val uiModel = PlaceModel(
            showSuccess,
            isRefresh,
            isShowProgress,
            showErrorText,
            placeResponse
        )
        _uiState.value = uiModel
    }
}

data class PlaceModel(
    val showSuccess: Boolean,
    val isRefresh: Boolean,
    val isShowProgress: Boolean,
    var showErrorText: String?,
    val placeResponse: MutableLiveData<List<Place>>?
)
