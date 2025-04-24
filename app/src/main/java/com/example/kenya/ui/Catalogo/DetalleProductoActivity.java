package com.example.kenya.ui.Catalogo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.kenya.R;
import com.example.kenya.model.Producto;
import com.example.kenya.ui.Seleccionado.SeleccionadoViewModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class DetalleProductoActivity extends AppCompatActivity {

    private boolean esFavorito = false;
    private Producto producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        // Vistas
        ImageView imagenProducto = findViewById(R.id.detalleImagenProducto);
        TextView nombreTextView = findViewById(R.id.detalleNombre);
        TextView precioTextView = findViewById(R.id.detallePrecio);
        TextView stockTextView = findViewById(R.id.detalleStock);
        TextView descripcionTextView = findViewById(R.id.detalleDescripcion);
        TextView tablaEspecificaciones = findViewById(R.id.tablaEspecificaciones);

        ImageView btnAtras = findViewById(R.id.btnAtras);
        ImageView btnCompartir = findViewById(R.id.btnCompartir);

        // ViewModel para favoritos
        SeleccionadoViewModel seleccionadoViewModel = new ViewModelProvider(this).get(SeleccionadoViewModel.class);

        // Obtener datos del intent
        String nombreStr = getIntent().getStringExtra("nombre");
        double precioSoles = getIntent().getDoubleExtra("precioSoles", 0.0); // Ahora double
        String stockStr = getIntent().getStringExtra("stock");
        String descripcionStr = getIntent().getStringExtra("descripcion");
        String imagenUrl = getIntent().getStringExtra("imagen_url");

        // Crear objeto producto
        producto = new Producto();
        producto.setNombre(nombreStr);
        producto.setPrecioSoles(precioSoles); // Ahora como double
        producto.setDescripcion(descripcionStr);
        producto.setImagenUrl(imagenUrl);

        // Formatear precio con 2 decimales
        NumberFormat formatoPrecio = NumberFormat.getNumberInstance(Locale.US);
        ((DecimalFormat) formatoPrecio).applyPattern("#0.00");
        String precioFormateado = formatoPrecio.format(precioSoles);

        // Mostrar info
        nombreTextView.setText(nombreStr);
        precioTextView.setText(String.format("S/ %s", precioFormateado));
        stockTextView.setText(stockStr + " UND.");
        descripcionTextView.setText(descripcionStr);
        Glide.with(this).load(imagenUrl).into(imagenProducto);
        tablaEspecificaciones.setText("Procesador: Intel Core i5\nMemoria RAM: 16GB\n...");

        // Botón regresar
        btnAtras.setOnClickListener(v -> finish());

        // Botón compartir
        btnCompartir.setOnClickListener(v -> {
            String mensaje = "¡Mira este producto en Kenya App!\n\n" +
                    nombreStr + "\nPrecio: S/ " + precioFormateado +
                    "\nStock: " + stockStr + " UND.";
            Intent compartirIntent = new Intent(Intent.ACTION_SEND);
            compartirIntent.setType("text/plain");
            compartirIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
            startActivity(Intent.createChooser(compartirIntent, "Compartir vía"));
        });
    }
}
