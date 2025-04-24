package com.example.kenya.ui.Catalogo;

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

public class CatalogoAdapter extends RecyclerView.Adapter<CatalogoAdapter.ProductoViewHolder> {

    private List<Producto> productoList;
    private OnItemClickListener listener;
    private OnFavoritoClickListener favoritoClickListener;

    public interface OnItemClickListener {
        void onItemClick(Producto producto);
    }

    public interface OnFavoritoClickListener {
        void onFavoritoClick(Producto producto, boolean agregar);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnFavoritoClickListener(OnFavoritoClickListener listener) {
        this.favoritoClickListener = listener;
    }

    public CatalogoAdapter(List<Producto> productoList) {
        this.productoList = productoList;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = productoList.get(position);
        holder.bind(producto);
    }

    @Override
    public int getItemCount() {
        return (productoList != null) ? productoList.size() : 0;
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        private ImageView productoImageView;
        private TextView nombreTextView;
        private TextView precioTextView;
        private TextView verMasButton;
        private ImageButton buttonFavorito;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            productoImageView = itemView.findViewById(R.id.itemProductoImageView);
            nombreTextView = itemView.findViewById(R.id.itemNombreTextView);
            precioTextView = itemView.findViewById(R.id.itemPrecioTextView);
            verMasButton = itemView.findViewById(R.id.buttonVerMas);
            buttonFavorito = itemView.findViewById(R.id.buttonFavorito);

            verMasButton.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (listener != null && pos != RecyclerView.NO_POSITION) {
                    listener.onItemClick(productoList.get(pos));
                }
            });

            buttonFavorito.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (favoritoClickListener != null && pos != RecyclerView.NO_POSITION) {
                    Producto producto = productoList.get(pos);
                    boolean nuevoEstado = !producto.isEsSeleccionado();
                    producto.setEsSeleccionado(nuevoEstado);
                    notifyItemChanged(pos);
                    favoritoClickListener.onFavoritoClick(producto, nuevoEstado);
                }
            });
        }

        public void bind(final Producto producto) {
            nombreTextView.setText(producto.getNombre());

            // Formatear el precio como double con 2 decimales
            precioTextView.setText(String.format("S/ %.2f", producto.getPrecioSoles()));

            Glide.with(itemView.getContext())
                    .load(producto.getImagenUrl())
                    .into(productoImageView);

            if (producto.isEsSeleccionado()) {
                buttonFavorito.setImageResource(R.drawable.corazon_lleno);
            } else {
                buttonFavorito.setImageResource(R.drawable.corazon21);
            }
        }

    }
}
