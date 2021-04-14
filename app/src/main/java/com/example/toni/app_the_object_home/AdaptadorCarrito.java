package com.example.toni.app_the_object_home;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Toni on 09/12/2017.
 */

public class AdaptadorCarrito extends ArrayAdapter<ProductoCarro> {
    public AdaptadorCarrito(Context context, ArrayList<ProductoCarro> productosCarrito) {
       // Toast.makeText( getContext(),"total de elementos pasados en el array:"+productosCarrito.size(),Toast.LENGTH_SHORT ).show();
        super(context, 0, productosCarrito);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Extraemos el producto actual
        final ProductoCarro productoActual = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.plantilla_lista_carro, parent, false);
        }
        // Lookup view for data population
        TextView nombreProductoCarro = (TextView) convertView.findViewById(R.id.nombrePantallaCarro);
        TextView precioProductoCarro = (TextView) convertView.findViewById(R.id.precioPantallaCarro);
        //final EditText cantidadProductoCarro=(EditText) convertView.findViewById( R.id.cantidadPantallaCarro );
        final ImageView imagenProductoCarro=(ImageView) convertView.findViewById( R.id.imagenPantallaCarro );
        final TextView cantidadCarro=(TextView)convertView.findViewById( R.id.cantidadProductCarro );
        final TextView precioTotalProducto=(TextView)convertView.findViewById( R.id.precioFinalProductoPantallaCarro );
        final TextView precioFinal=(TextView)convertView.findViewById( R.id.total );


        // Insertamos los valores correspondientes a cada celda
        nombreProductoCarro.setText(productoActual.getNombre());
        precioProductoCarro.setText( productoActual.getPrecio()+" €" );
        cantidadCarro.setText( productoActual.getCantidad() );




        Glide
                .with(getContext())
                .load( Uri.parse("android.resource://com.example.toni.app_the_object_home/drawable/"+productoActual.getImagen() ))
                .asBitmap()
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(new BitmapImageViewTarget(imagenProductoCarro) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable
                                = RoundedBitmapDrawableFactory.create(getContext().getResources(), resource);
                        drawable.setCircular(false);
                        imagenProductoCarro.setImageDrawable(drawable);
                    }
                });

        //Calculamos el precio total de cada producto en función de las cantidades seleccionadas
        String PVPtotal=modificaTotalProducto( productoActual.getPrecio(),productoActual.getCantidad() );
        precioTotalProducto.setText( PVPtotal+" €" );

        // Return the completed view to render on screen
        return convertView;


    }

    public String modificaTotalProducto (String precio, String cantidad) {
        String PVPtotal;
        //Calculamos el precio del producto según su cantidad
        //variable numerica para los calculos
        double auxiliar = 0.0;
        //multiplicamos cantidad x precio
        auxiliar = ( Double.parseDouble( precio ) ) * ( Double.parseDouble( cantidad ) );
        //limitamos el número de decimales de salida a 2
        BigDecimal bd = new BigDecimal( auxiliar );
        bd = bd.setScale( 2, RoundingMode.HALF_UP );
        PVPtotal = Double.toString( bd.doubleValue( ) );
        return PVPtotal;

    }
    public String totalPedido () {
        ArrayList <ProductoCarro> listaCarro=((MainActivity)getContext()).productosCarro;

        // Calculamos total pedido
        //Creamos la variable que sumara el total de importes de la lista
        double totalPedido = 0.0;

        //Creamos un bucle que recorre la lista sumando los totales
        for (int i = 0; i < listaCarro.size(); i++) {
            //calculamos el precio total de producto en esa fila
            String totalFilaActual = this.getItem(i).precioTotalCalculado(this.getItem(i).getPrecio(), this.getItem(i).cantidad);
            double totalFila2 = Double.parseDouble(totalFilaActual);
            //En un double vamos acumulando los valores
            totalPedido = totalPedido + totalFila2;
        }
        //añadimos el importe en el contenedor correspondiente

        return Double.toString(totalPedido) + " €";
    }

}