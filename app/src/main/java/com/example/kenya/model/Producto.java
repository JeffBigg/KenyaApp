package com.example.kenya.model;

import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;

public class Producto {
    private boolean esSeleccionado;

    private int id;
    private String nombre;
    private String descripcion;
    private int stock;
    @SerializedName("precio_soles")
    private double precioSoles;

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

    public double getPrecioSoles() {
        return precioSoles;
    }

    public void setPrecioSoles(double precioSoles) {
        this.precioSoles = precioSoles;
    }

    public String getPrecioFormateado() {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(precioSoles);
    }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public int getCategoria() { return categoria_id; }
    public void setCategoria(int categoria) { this.categoria_id = categoria; }

    public int getStock() { return stock; }

    public boolean isEsSeleccionado() { return esSeleccionado; }
    public void setEsSeleccionado(boolean esSeleccionado) { this.esSeleccionado = esSeleccionado; }
}
