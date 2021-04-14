package com.example.toni.app_the_object_home;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Toni on 12/12/2017.
 */

public class TestSumaProducto  {

    @Test
    public void sumaCorrecta() throws Exception {
        String cantidadActual="1";
        String nuevaCantidad="2";
        assertEquals( nuevaCantidad, Ficha.newInstance().sumaNumero( cantidadActual ));
    }
    @Test
    public void sumaCorrecta2() throws Exception{
        String cantidadActual="10";
        String nuevaCantidad="11";
        assertEquals( nuevaCantidad,Ficha.newInstance().sumaNumero( cantidadActual ) );
    }
    @Test
    public void restaCorrecta() throws Exception{
        String cantidadActual="5";
        String nuevaCantidad="4";
        assertEquals( nuevaCantidad,Ficha.newInstance().restaNumero( cantidadActual ) );
    }
    @Test
    public void restaCorrecta2() throws Exception{
        String cantidadActual="1";
        //nos va a dar error porque hay un toast de aviso al usuario
        assertEquals(cantidadActual,Ficha.newInstance().restaNumero( cantidadActual ) );
    }
    @Test
    public void sumaErronea() throws Exception{
        String cantidadActual="10";
        String nuevaCantidad="15";
        assertEquals( nuevaCantidad,Ficha.newInstance().sumaNumero( cantidadActual ) );
    }
    @Test
    public void restaErronea() throws Exception{
        String cantidadActual="5";
        String nuevaCantidad="2";
        assertEquals(nuevaCantidad,Ficha.newInstance().restaNumero( cantidadActual ) );
    }


}
