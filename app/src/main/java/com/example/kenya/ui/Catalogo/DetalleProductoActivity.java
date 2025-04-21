package com.example.kenya.ui.Catalogo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.kenya.R;
import com.example.kenya.model.Producto;
import com.example.kenya.ui.Seleccionado.SeleccionadoViewModel;

import java.util.List;

public class DetalleProductoActivity extends AppCompatActivity {

    private boolean esFavorito = false;
    private Producto producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        // Vistas
        ImageView imagenProducto = findViewById(R.id.detalleImagenProducto);
        TextView nombre = findViewById(R.id.detalleNombre);
        TextView precio = findViewById(R.id.detallePrecio);
        TextView stock = findViewById(R.id.detalleStock);
        TextView descripcion = findViewById(R.id.detalleDescripcion);
        TextView tablaEspecificaciones = findViewById(R.id.tablaEspecificaciones);

        ImageView btnAtras = findViewById(R.id.btnAtras);

        ImageView btnCompartir = findViewById(R.id.btnCompartir);

        // ViewModel para favoritos
        SeleccionadoViewModel seleccionadoViewModel = new ViewModelProvider(this).get(SeleccionadoViewModel.class);

        // Obtener datos del intent
        String nombreStr = getIntent().getStringExtra("nombre");
        String precioStr = getIntent().getStringExtra("precio");
        String stockStr = getIntent().getStringExtra("stock");
        String descripcionStr = getIntent().getStringExtra("descripcion");
        String imagenUrl = getIntent().getStringExtra("imagen_url");

        // Crear objeto producto
        producto = new Producto();
        producto.setNombre(nombreStr);
        producto.setPrecioSoles(precioStr);
        producto.setDescripcion(descripcionStr);
        producto.setImagenUrl(imagenUrl);

        // Verificar si ya es favorito


        // Mostrar info
        nombre.setText(nombreStr);
        precio.setText("S/ " + precioStr);
        stock.setText(stockStr + " UND.");
        descripcion.setText(descripcionStr);
        Glide.with(this).load(imagenUrl).into(imagenProducto);
        tablaEspecificaciones.setText("Procesador: Intel Core i5\nMemoria RAM: 16GB\n..."); // Ejemplo

        // Botón regresar
        btnAtras.setOnClickListener(v -> finish());

        // Botón compartir
        btnCompartir.setOnClickListener(v -> {
            String mensaje = "¡Mira este producto en Kenya App!\n\n" +
                    nombreStr + "\nPrecio: S/ " + precioStr + "\nStock: " + stockStr + " UND.";
            Intent compartirIntent = new Intent(Intent.ACTION_SEND);
            compartirIntent.setType("text/plain");
            compartirIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
            startActivity(Intent.createChooser(compartirIntent, "Compartir vía"));
        });


    }
}
