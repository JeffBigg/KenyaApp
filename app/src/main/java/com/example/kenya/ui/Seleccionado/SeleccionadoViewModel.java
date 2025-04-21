package com.example.kenya.ui.Seleccionado;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kenya.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class SeleccionadoViewModel extends ViewModel {

    private final MutableLiveData<List<Producto>> productosSeleccionados = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Producto>> getProductosSeleccionados() {
        return productosSeleccionados;
    }

    public void agregarProducto(Producto producto) {
        List<Producto> listaActual = productosSeleccionados.getValue();
        if (listaActual == null) {
            listaActual = new ArrayList<>();
        }

        // Verificamos si ya existe el producto antes de agregar
        boolean yaExiste = false;
        for (Producto p : listaActual) {
            if (p.getNombre().equals(producto.getNombre())) {
                yaExiste = true;
                break;
            }
        }

        if (!yaExiste) {
            listaActual.add(producto);
            productosSeleccionados.setValue(new ArrayList<>(listaActual));
        }
    }

    public void removerProducto(Producto producto) {
        List<Producto> listaActual = productosSeleccionados.getValue();
        if (listaActual != null) {
            List<Producto> nuevaLista = new ArrayList<>();
            for (Producto p : listaActual) {
                if (!p.getNombre().equals(producto.getNombre())) {
                    nuevaLista.add(p);
                }
            }
            productosSeleccionados.setValue(nuevaLista);
        }
    }

    public void quitarProducto(Producto producto) {
        removerProducto(producto); // mismo comportamiento que remover
    }

    public boolean estaSeleccionado(Producto producto) {
        List<Producto> seleccionados = productosSeleccionados.getValue();
        if (seleccionados != null) {
            for (Producto p : seleccionados) {
                if (p.getNombre().equals(producto.getNombre())) {
                    return true;
                }
            }
        }
        return false;
    }
}
