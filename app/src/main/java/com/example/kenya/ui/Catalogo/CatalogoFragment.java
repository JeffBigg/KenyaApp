package com.example.kenya.ui.Catalogo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kenya.R;
import com.example.kenya.model.Producto;
import com.example.kenya.ui.Seleccionado.SeleccionadoViewModel;

import java.util.ArrayList;
import java.util.List;

public class CatalogoFragment extends Fragment {
    private SeleccionadoViewModel seleccionadoViewModel;
    private CatalogoViewModel viewModel;
    private RecyclerView recyclerViewCatalogo;
    private CatalogoAdapter adapter;
    private EditText editTextBuscar;

    private LinearLayout filtroWorkstation, filtroMonitor, filtroPc, filtroAccesorios, filtroConsumibles;
    private ImageButton btnWorkstation, btnMonitor, btnPc, btnAccesorios, btnConsumibles;

    private List<Producto> fullProductoList = new ArrayList<>();
    private int selectedCategoryId = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_catalogo, container, false);

        recyclerViewCatalogo = root.findViewById(R.id.recyclerViewCatalogo);
        recyclerViewCatalogo.setLayoutManager(new GridLayoutManager(getContext(), 2));

        editTextBuscar = root.findViewById(R.id.editTextBuscar);

        filtroWorkstation = root.findViewById(R.id.filtroWorkstation);
        filtroMonitor = root.findViewById(R.id.filtroMonitor);
        filtroPc = root.findViewById(R.id.filtroPc);
        filtroAccesorios = root.findViewById(R.id.filtroAccesorios);
        filtroConsumibles = root.findViewById(R.id.filtroConsumibles);

        btnWorkstation = root.findViewById(R.id.btnWorkstation);
        btnMonitor = root.findViewById(R.id.btnMonitor);
        btnPc = root.findViewById(R.id.btnPc);
        btnAccesorios = root.findViewById(R.id.btnAccesorios);
        btnConsumibles = root.findViewById(R.id.btnConsumibles);

        viewModel = new ViewModelProvider(this).get(CatalogoViewModel.class);
        seleccionadoViewModel = new ViewModelProvider(requireActivity()).get(SeleccionadoViewModel.class);

        viewModel.getProductos().observe(getViewLifecycleOwner(), productos -> {
            if (productos != null && !productos.isEmpty()) {
                fullProductoList = productos;
                setupAdapter(productos);
            }
        });

        seleccionadoViewModel.getProductosSeleccionados().observe(getViewLifecycleOwner(), productosSeleccionados -> {
            for (Producto producto : fullProductoList) {
                producto.setEsSeleccionado(productosSeleccionados.contains(producto));
            }
            if (adapter != null) adapter.notifyDataSetChanged();
        });

        editTextBuscar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { filterProducts(); }
            @Override public void afterTextChanged(Editable s) {}
        });

        setCategoryClickListener(filtroWorkstation, btnWorkstation, 1);
        setCategoryClickListener(filtroMonitor, btnMonitor, 2);
        setCategoryClickListener(filtroPc, btnPc, 3);
        setCategoryClickListener(filtroAccesorios, btnAccesorios, 4);
        setCategoryClickListener(filtroConsumibles, btnConsumibles, 5);

        return root;
    }

    private void setupAdapter(List<Producto> productos) {
        adapter = new CatalogoAdapter(productos);
        adapter.setOnItemClickListener(producto -> {
            Intent intent = new Intent(getContext(), DetalleProductoActivity.class);
            intent.putExtra("nombre", producto.getNombre());
            intent.putExtra("precio", producto.getPrecioSoles());
            intent.putExtra("stock", String.valueOf(producto.getStock()));
            intent.putExtra("descripcion", producto.getDescripcion());
            intent.putExtra("imagen_url", producto.getImagenUrl());
            startActivity(intent);
        });

        adapter.setOnFavoritoClickListener((producto, agregar) -> {
            if (agregar) {
                seleccionadoViewModel.agregarProducto(producto);
            } else {
                seleccionadoViewModel.removerProducto(producto);
            }
        });

        recyclerViewCatalogo.setAdapter(adapter);
    }

    private void setCategoryClickListener(LinearLayout container, ImageButton button, int categoryId) {
        View.OnClickListener listener = v -> {
            if (selectedCategoryId == categoryId) {
                selectedCategoryId = 0;
                button.setSelected(false);
            } else {
                selectedCategoryId = categoryId;
                deselectAllButtons();
                button.setSelected(true);
            }
            filterProducts();
        };
        container.setOnClickListener(listener);
        button.setOnClickListener(listener);
    }

    private void deselectAllButtons() {
        btnWorkstation.setSelected(false);
        btnMonitor.setSelected(false);
        btnPc.setSelected(false);
        btnAccesorios.setSelected(false);
        btnConsumibles.setSelected(false);
    }

    private void filterProducts() {
        String query = editTextBuscar.getText().toString().toLowerCase().trim();
        List<Producto> filteredList = new ArrayList<>();

        for (Producto producto : fullProductoList) {
            boolean matchesName = producto.getNombre().toLowerCase().contains(query);
            boolean matchesCategory = (selectedCategoryId == 0) || (producto.getCategoria() == selectedCategoryId);

            if (matchesName && matchesCategory) {
                filteredList.add(producto);
            }
        }

        setupAdapter(filteredList);
    }
}
