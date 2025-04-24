package com.example.kenya.ui.Seleccionado;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kenya.R;
import com.example.kenya.model.Producto;

import java.util.List;

public class SeleccionadoAdapter extends RecyclerView.Adapter<SeleccionadoAdapter.ProductoViewHolder> {

    private List<Producto> productos;
    private OnEliminarFavoritoListener eliminarFavoritoListener;

    public interface OnEliminarFavoritoListener {
        void onEliminarFavorito(Producto producto);
    }

    public SeleccionadoAdapter(List<Producto> productos, OnEliminarFavoritoListener listener) {
        this.productos = productos;
        this.eliminarFavoritoListener = listener;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto_seleccionado, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        holder.bind(productos.get(position));
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    class ProductoViewHolder extends RecyclerView.ViewHolder {
        private final TextView nombreTextView, precioTextView, stockTextView, descripcionTextView;
        private final ImageView imagenProducto;
        private final ImageButton buttonFavorito;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombreProducto);
            precioTextView = itemView.findViewById(R.id.precioProducto);
            stockTextView = itemView.findViewById(R.id.stockProducto);
            descripcionTextView = itemView.findViewById(R.id.descripcionProducto);
            imagenProducto = itemView.findViewById(R.id.imagenProducto);
            buttonFavorito = itemView.findViewById(R.id.buttonFavoritoSeleccionado);

            buttonFavorito.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (eliminarFavoritoListener != null && pos != RecyclerView.NO_POSITION) {
                    eliminarFavoritoListener.onEliminarFavorito(productos.get(pos));
                }
            });
        }

        public void bind(Producto producto) {
            nombreTextView.setText(producto.getNombre());

            // Formato de precio con 2 decimales
            precioTextView.setText(String.format("S/ %.2f", producto.getPrecioSoles()));

            stockTextView.setText("Stock: " + producto.getStock());
            descripcionTextView.setText(producto.getDescripcion());

            Glide.with(itemView.getContext())
                    .load(producto.getImagenUrl())
                    .into(imagenProducto);

            buttonFavorito.setImageResource(R.drawable.corazon_lleno);
        }
    }
}
