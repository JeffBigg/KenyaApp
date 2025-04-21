package com.example.kenya.ui.Catalogo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kenya.model.ApiResponseList;
import com.example.kenya.model.Producto;
import com.example.kenya.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatalogoViewModel extends ViewModel {
    private MutableLiveData<List<Producto>> productosLiveData;
    private MutableLiveData<String> errorLiveData;

    public CatalogoViewModel() {
        productosLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
        obtenerProductos();
    }

    public LiveData<List<Producto>> getProductos() {
        return productosLiveData;
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }

    private void obtenerProductos() {
        RetrofitClient.getApiService().getProductos().enqueue(new Callback<ApiResponseList>() {
            @Override
            public void onResponse(Call<ApiResponseList> call, Response<ApiResponseList> response) {
                if(response.isSuccessful() && response.body() != null) {
                    if(response.body().isSuccess()) {
                        productosLiveData.postValue(response.body().getData());
                    } else {
                        errorLiveData.postValue("Error: la respuesta no fue exitosa.");
                    }
                } else {
                    errorLiveData.postValue("Error en la respuesta del servidor.");
                }
            }

            @Override
            public void onFailure(Call<ApiResponseList> call, Throwable t) {
                errorLiveData.postValue("Fallo al conectar: " + t.getMessage());
            }
        });
    }
}
