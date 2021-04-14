package com.example.toni.app_the_object_home;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Toni on 07/12/2017.
 */

public class ProductoCarro {
    String nombre;
    String cantidad;
    String precio;
    String imagen;
    double precioTotal;
    public ProductoCarro() {
    }

    public ProductoCarro(String nombre, String cantidad, String precio,String imagen) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.imagen=imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String precioTotalCalculado (String precio, String Cantidad){
        String PVPtotal;
        //Calculamos el precio del producto según su cantidad
        //variable numerica para los calculos
        double auxiliar=0.0;
        //multiplicamos cantidad x precio
        auxiliar=(Double.parseDouble( precio))*(Double.parseDouble( cantidad ));
        //limitamos el número de decimales de salida a 2
        BigDecimal bd= new BigDecimal( auxiliar );
        bd=bd.setScale( 2, RoundingMode.HALF_UP );
        PVPtotal=Double.toString( bd.doubleValue() );
        return PVPtotal;
    }
}
