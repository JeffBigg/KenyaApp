package com.example.kenya.ui.Seleccionado;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kenya.R;
import com.example.kenya.model.Producto;

import java.util.ArrayList;

public class SeleccionadoFragment extends Fragment {

    private RecyclerView recyclerViewSeleccionado;
    private SeleccionadoAdapter seleccionadoAdapter;
    private SeleccionadoViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_seleccionado, container, false);

        recyclerViewSeleccionado = root.findViewById(R.id.recyclerViewSeleccionado);
        recyclerViewSeleccionado.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = new ViewModelProvider(requireActivity()).get(SeleccionadoViewModel.class);
        seleccionadoAdapter = new SeleccionadoAdapter(new ArrayList<>(), producto -> {
            viewModel.quitarProducto(producto);  // Eliminar del ViewModel
        });

        recyclerViewSeleccionado.setAdapter(seleccionadoAdapter);

        viewModel.getProductosSeleccionados().observe(getViewLifecycleOwner(), productos -> {
            seleccionadoAdapter.setProductos(productos);
        });

        return root;
    }
}
