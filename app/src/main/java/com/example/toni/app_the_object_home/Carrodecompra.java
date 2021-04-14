package com.example.toni.app_the_object_home;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;


public class Carrodecompra extends Fragment {

ListView listaProductosCarro;
AdaptadorCarrito adapter;
    public Carrodecompra() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate( R.layout.fragment_carrodecompra, container, false );

        final ArrayList<ProductoCarro> listaCarro=((MainActivity)getActivity()).productosCarro;
        TextView carroVacio=(TextView)view.findViewById( R.id.mensajeVacio);

        if(listaCarro.size()==0){
            carroVacio.setText( "Su carrito está vacio" );
        }else{
            listaProductosCarro=(ListView)view.findViewById( R.id.listviewCarro );
            //Añadimos header y footer al listview
            LayoutInflater inflador = getLayoutInflater();
            ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.footer_carro, listaProductosCarro, false);
            ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header_carro, listaProductosCarro, false);
            listaProductosCarro.addHeaderView( header,null,false );
            listaProductosCarro.addFooterView(footer, null, false);
            //instanciamos el adaptador
            adapter=new AdaptadorCarrito(  getContext(),listaCarro);
            //le añadimos los datos al listview
            listaProductosCarro.setAdapter( adapter );
            //mostramos el mensaje con las indicaciones para editar el carro
            Button btnHelp=(Button)view.findViewById( R.id.btnInformacon );
            btnHelp.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {
                    Toast.makeText( getContext(),"Pulsa sobre los productos para realizar modificaciones en tu carrito",Toast.LENGTH_SHORT ).show();

                }
            } );



            // Calculamos total pedido
            //Creamos la variable que sumara el total de importes de la lista
            double totalPedido=0.0;

            //Creamos un bucle que recorre la lista sumando los totales
            for(int i = 0; i<listaCarro.size();i++){
                //calculamos el precio total de producto en esa fila
                String totalFilaActual=adapter.getItem( i ).precioTotalCalculado( adapter.getItem( i ).getPrecio(),adapter.getItem( i ).cantidad );
                double totalFila2=Double.parseDouble( totalFilaActual );
                //En un double vamos acumulando los valores
                totalPedido=totalPedido+totalFila2;
            }

            //añadimos el importe en el contenedor correspondiente
            final TextView PVPPedido=(TextView)view.findViewById( R.id.total );
            String totalfiltrado=Double.toString(totalPedido);
            PVPPedido.setText(  dosDecimales( totalfiltrado )+" €");

            listaProductosCarro.setOnItemClickListener (new  AdapterView.OnItemClickListener () {
                @Override
                public void onItemClick (AdapterView <?> parent, View view, int position, long id) {
                    String nombreProduct=adapter.getItem( position-1 ).getNombre().toString();
                    String imageProduct=adapter.getItem( position-1 ).getImagen().toString();
                    String cantidadProduct=adapter.getItem( position-1 ).getCantidad().toString();
                    String precioProduct=adapter.getItem( position-1 ).getPrecio().toString();
                    dialogModifica( nombreProduct,cantidadProduct,imageProduct, precioProduct );
                }
            });
            //adapter.notifyDataSetChanged();
            Button btnDatos=(Button)view.findViewById( R.id.btnContinuarPago );
            btnDatos.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {
                    //extraemos el valor
                    Bundle bundle= new Bundle(  );
                    bundle.putString("total pedido", PVPPedido.getText().toString());
                    DatosCompra rellenaDatos=new DatosCompra();
                    rellenaDatos.setArguments( bundle );
                    getFragmentManager().beginTransaction().replace( R.id.contenedor,rellenaDatos ).addToBackStack( null ).commit();
                }
            } );
        }


        return view;
    }

    //funcion utilizada para pasar el total del pedido antes de insertarlo para que muestre solo dos decimales
    public String dosDecimales (String totalPedido){
        //pasamos el String a double
        double auxiliar=0.0;
        //pasamos la cantidad total a double
        auxiliar=Double.parseDouble( totalPedido );
        //limitamos el número de decimales de salida a 2
        BigDecimal bd= new BigDecimal( auxiliar );
        bd=bd.setScale( 2, RoundingMode.HALF_UP );
        totalPedido=Double.toString( bd.doubleValue() );
        return totalPedido;
    }


    public AlertDialog dialogModifica (final String nombre, final String cantidad, final String imagen, final String precio) {

        final AlertDialog alertDialog;
        //Le indicamos que tiene que mostrar nuestro Alert Dialog personalizado
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_modificar_carro, null);
        builder.setView(v);

        //Permitimos que el ususario pueda cerrar el alertdialog al hacer clic fuera de él
        builder.setCancelable( true );


        //creamos y mostramos el alertdialog
        alertDialog= builder.create();
        alertDialog.show();

        TextView nombreDialog=(TextView)v.findViewById( R.id.nombreModificaProducto );
        final ImageView imagenDialog=(ImageView)v.findViewById( R.id.imageModificaCarro );
        final EditText cantidadActualDialog=(EditText)v.findViewById( R.id.cantidadDialogModifica );
        //botón +
        Button sumaCantidad=(Button) v.findViewById( R.id.btnMasModifica );
        //botón -
        Button restaCantidad=(Button) v.findViewById( R.id.btnMenosModifica );

        //botón guardar cambios
        Button modificarCantidad=(Button) v.findViewById( R.id.dialogGuardar );
        //elimina producto del carro
        ImageView borraProducto=(ImageView)v.findViewById( R.id.dialogEliminaProducto ) ;



        Glide
                .with(getContext())
                .load(Uri.parse("android.resource://com.example.toni.app_the_object_home/drawable/"+imagen ))
                .asBitmap()
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(new BitmapImageViewTarget(imagenDialog) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable
                                = RoundedBitmapDrawableFactory.create(getContext().getResources(), resource);
                        drawable.setCircular(false);
                        imagenDialog.setImageDrawable(drawable);
                    }
                });

        cantidadActualDialog.setText( cantidad );
        nombreDialog.setText( nombre );

        //funcionalidad para el botón +
        sumaCantidad.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                //declaramos variable para guardar la nueva cantidad
                int newCantidad=0;
                //le sumamos una unidad al valor actual en el edittext
                newCantidad=Integer.parseInt(cantidadActualDialog.getText().toString())+1;
                //le asignamos el nuevo valor al editText
                cantidadActualDialog.setText( Integer.toString( newCantidad ) );
            }
        } );
        restaCantidad.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                //declaramos variable para guardar la nueva cantidad
                int newCantidad=0;
                //restamos una unidad al valor actual en el edittext
                newCantidad=Integer.parseInt(cantidadActualDialog.getText().toString())-1;
                //si el nuevo valor es menor que 1
                if(newCantidad<1){
                    Toast.makeText( getContext(),"La cantidad mínima es 1",Toast.LENGTH_SHORT ).show();
                }else{
                    //le asignamos el nuevo valor al editText
                    cantidadActualDialog.setText( Integer.toString( newCantidad ) );
                }
            }
        } );

        //Si pulsamos sobre el elemento guardar
        modificarCantidad.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                //extraemos el valor del contenedor de texto
                String cantidadModificada=cantidadActualDialog.getText().toString();
                //buscamos el producto dentro del ArrayList
                //Creamos un bucle que recorre la lista buscando el producto
                //llamamos al metodo de la activity que inserta los datos en el arrayList
                ((MainActivity)getActivity()).modificaCantidadCarro(nombre,cantidadModificada);
                Carrodecompra carro=new Carrodecompra();
                getFragmentManager().beginTransaction().replace( R.id.contenedor,carro ).addToBackStack( null ).commit();
                Toast.makeText( getContext(),"Producto "+nombre +" modificado correctamente",Toast.LENGTH_SHORT ).show();

                alertDialog.dismiss();

            }
        } );
        borraProducto.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                //eliminamos el producto de la lista que coincide con el nombre
                ((MainActivity)getActivity()).eliminaProductoCarro(nombre);
                Carrodecompra carro=new Carrodecompra();
                getFragmentManager().beginTransaction().replace( R.id.contenedor,carro ).addToBackStack( null ).commit();
                Toast.makeText( getContext(),"Producto "+nombre+" eliminado correctamente",Toast.LENGTH_SHORT ).show();
                alertDialog.dismiss();
            }
        } );


        //devolvemos el alertdialog
        return alertDialog;

    }



}