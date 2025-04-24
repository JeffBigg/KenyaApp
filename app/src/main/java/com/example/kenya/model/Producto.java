package com.example.kenya.model;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class Producto {
    private boolean esSeleccionado;

    private int id;
    private String nombre;
    private String descripcion;
    private int stock;
    @SerializedName("precio_soles")
    private String precioSoles;

    @SerializedName("imagen_url")
    private String imagenUrl;

    private String slug;
    private int categoria_id;

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getPrecioSoles() {
        if (precioSoles == null || precioSoles.isEmpty()) {
            return "0.00"; // Valor por defecto si está vacío
        }
        try {
            double precio = Double.parseDouble(precioSoles);
            return String.format(Locale.US, "%.2f", precio); // Formato a 2 decimales
        } catch (NumberFormatException e) {
            return "0.00"; // Si hay error en el formato
        }
    }
    public void setPrecioSoles(String precioSoles) { this.precioSoles = precioSoles; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public int getCategoria() { return categoria_id; }
    public void setCategoria(int categoria) { this.categoria_id = categoria; }
    public int getStock() {
        return stock;
    }
    public boolean isEsSeleccionado() {
        return esSeleccionado;
    }

    public void setEsSeleccionado(boolean esSeleccionado) {
        this.esSeleccionado = esSeleccionado;
    }

}
