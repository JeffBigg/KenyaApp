package com.example.kenya.ui.Seleccionado;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kenya.R;
import com.example.kenya.model.Producto;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SeleccionadoFragment extends Fragment {

    private RecyclerView recyclerViewSeleccionado;
    private SeleccionadoAdapter seleccionadoAdapter;
    private SeleccionadoViewModel viewModel;
    private TextView textTotal;
    private Button btnEnviarWhatsApp;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_seleccionado, container, false);

        recyclerViewSeleccionado = root.findViewById(R.id.recyclerViewSeleccionado);
        recyclerViewSeleccionado.setLayoutManager(new LinearLayoutManager(getContext()));

        textTotal = root.findViewById(R.id.textTotal);
        btnEnviarWhatsApp = root.findViewById(R.id.btnEnviarWhatsApp);

        viewModel = new ViewModelProvider(requireActivity()).get(SeleccionadoViewModel.class);

        seleccionadoAdapter = new SeleccionadoAdapter(new ArrayList<>(), producto -> {
            viewModel.quitarProducto(producto);
        });

        recyclerViewSeleccionado.setAdapter(seleccionadoAdapter);

        viewModel.getProductosSeleccionados().observe(getViewLifecycleOwner(), productos -> {
            seleccionadoAdapter.setProductos(productos);
            actualizarTotalYWhatsApp(productos);
        });

        return root;
    }

    private void actualizarTotalYWhatsApp(List<Producto> productos) {
        double total = 0.0;
        DecimalFormat df = new DecimalFormat("#0.00");
        StringBuilder mensaje = new StringBuilder("🛒 *Lista de productos seleccionados:*\n\n");

        for (Producto producto : productos) {
            double precio = producto.getPrecioSoles(); // Ahora double
            total += precio;
            mensaje.append("• ").append(producto.getNombre())
                    .append(" - S/ ").append(df.format(precio))
                    .append("\n");
        }

        mensaje.append("\n*Total: S/ ").append(df.format(total)).append("*");
        textTotal.setText("Total: S/ " + df.format(total));

        btnEnviarWhatsApp.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String url = "https://wa.me/?text=" + Uri.encode(mensaje.toString());
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
    }
}
