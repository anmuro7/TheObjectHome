package com.example.toni.app_the_object_home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;


public class Ficha extends Fragment {
    private DataBaseManager manager;
    private Cursor cursor;
    //declaramos las variables que usaremos para almacenar los datos de consulta
    private String name;
    private String image;
    private String price;
    private String description;


    private View view;
    public Ficha() {
        // Required empty public constructor
    }
    public static Ficha newInstance(){
        return new Ficha();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
         view= inflater.inflate( R.layout.fragment_ficha, container, false );
        //Creamos una instancia para la iniciación de la base de datos
        manager=new DataBaseManager(getContext());
        //extraemos el dato enviado desde el anterior fragment
        String myValue = this.getArguments().getString("nombre");
        //cargamos el cursor donde almacenamos la consulta a la BD
        cursor= manager.cargaCursorPorNombre(myValue);
        //extraemos los datos del cursor
        if(cursor.getCount() >=1){
            while(cursor.moveToNext()){
                image=cursor.getString( cursor.getColumnIndex( DataBaseManager.CN_IMAGE ));
                name=cursor.getString( cursor.getColumnIndex( DataBaseManager.CN_NAME ));
                price=cursor.getString( cursor.getColumnIndex( DataBaseManager.CN_PRICE ));
                description=cursor. getString( cursor.getColumnIndex( DataBaseManager.CN_DESCRIPTION ) );
            }

        }


    //Localizamos los contenedores para añadir los datos y dar funcionalidades
        final ImageView fotoFicha=(ImageView)view.findViewById( R.id.fotoFicha );
        final TextView nombreFicha=(TextView)view.findViewById( R.id.productoEnFicha );
        TextView precioFicha=(TextView)view.findViewById( R.id.precioEnFicha );
        TextView descripcionFicha=(TextView)view.findViewById( R.id.descripcionEnFicha );
        TextView cantidadCarro=(TextView)view.findViewById( R.id.cantidadCarrito );


     //Añadimos los valores a los contenedores
        Glide
                .with(getContext())
                .load(Uri.parse("android.resource://com.example.toni.app_the_object_home/drawable/"+image ))
                .asBitmap()
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(new BitmapImageViewTarget(fotoFicha) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable
                                = RoundedBitmapDrawableFactory.create(getContext().getResources(), resource);
                        drawable.setCircular(false);
                        fotoFicha.setImageDrawable(drawable);
                    }
                });

        //A continuación insertamos los textos dentro de los campos correspondientes
        nombreFicha.setText( name );
        precioFicha.setText( price+" €");
        descripcionFicha.setText( description );

    //Localizamos los botones
        Button btnMas=(Button)view.findViewById( R.id.buttonMas );
        //le aplicamos la funcionalidad
        btnMas.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Extraemos el valor del contenedor con la cantidad
                EditText cantidadElegida=view.findViewById( R.id.cantidadFicha );
                String cantidadText=cantidadElegida.getText().toString();
                cantidadElegida.setText( sumaNumero( cantidadText ) );

            }
        } );
        Button btnMenos=(Button)view.findViewById( R.id.buttonMenos );
        btnMenos.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Extraemos el valor de la cantidad actual seleccionada
                EditText cantidadElegida=view.findViewById( R.id.cantidadFicha );
                String cantidadText=cantidadElegida.getText().toString();
                cantidadElegida.setText( restaNumero( cantidadText ) );

            }
        } );

        //A continuación asignamos las funciones al botón de compra
        Button btnCompra=(Button)view.findViewById( R.id.buttonCompra );
        //le asignamos la funcionalidad
        btnCompra.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Extraemos el valor de la cantidad actual seleccionada
                EditText cantidadElegida=view.findViewById( R.id.cantidadFicha );
                String cantidadText=cantidadElegida.getText().toString();
                //llamamos al metodo de la activity que inserta los datos en el arrayList
                ((MainActivity)getActivity()).insertaCarro(name,cantidadText,price,image);
                //Creames el Alert Dialog y le pasamos los valores para añadir al carro
                createLoginDialogo();

            }
        } );

        // Inflate the layout for this fragment
        return view;


    }
    //mètodo que suma cantidades
    public String sumaNumero(String cantidadActual){
        int nuevaCantidad=Integer.parseInt( cantidadActual )+1;
        return Integer.toString( nuevaCantidad );
    }
    //metodo que resta cantidades
    public String restaNumero(String cantidadActual){
        int nuevaCantidad=Integer.parseInt( cantidadActual )-1;
        if(nuevaCantidad<1){
            Toast.makeText( getContext(),"El número mínimo de productos es 1",Toast.LENGTH_SHORT ).show();
            return cantidadActual;
        }else{
            return Integer.toString( nuevaCantidad );
        }
    }
    public AlertDialog createLoginDialogo() {

        final AlertDialog alertDialog;



        //añadimos el objeto producto al arraylist, al pulsar el botón el usuario ya h decidido añadirlo
        //posteriormente según la decisión que tome lo pasamos o esperamos
        //.add( productos );


        //Le indicamos que tiene que mostrar nuestro Alert Dialog personalizado
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_compra, null);
        builder.setView(v);

        //Permitimos que el ususario pueda cerrar el alertdialog al hacer clic fuera de él
        builder.setCancelable( true );

        //localizamos los botones de nuestro alertdialog para crear su funcionalidad
        Button goCarro = (Button) v.findViewById(R.id.irCarro);
        Button continueBuy = (Button) v.findViewById(R.id.continuarCompra);
        //creamos y mostramos el alertdialog
        alertDialog= builder.create();
        alertDialog.show();

        //cuando el usuario quiera finalizar su compra e ir al carro...
        goCarro.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       Carrodecompra carro=new Carrodecompra();
                        getFragmentManager().beginTransaction().replace( R.id.contenedor,carro ).addToBackStack( null ).commit();
                        alertDialog.dismiss();

                    }
                }
        );
        //cuando el usuario quiera continuar con su compra...
        continueBuy.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();
                    }
                }

        );
        //devolvemos el alertdialog
    return alertDialog;

    }

}
