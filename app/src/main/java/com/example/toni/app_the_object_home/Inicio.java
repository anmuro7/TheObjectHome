package com.example.toni.app_the_object_home;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class Inicio extends Fragment {


    public Inicio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        //instanciamos los contenedores con las imágenes que harán de menú
        ImageView b1=(ImageView)view.findViewById( R.id.banner1 );
        insertaBanner( b1,"banner1" );
        ImageView b2=(ImageView)view.findViewById( R.id.banner2 );
        insertaBanner( b2,"banner2" );
        ImageView b3=(ImageView)view.findViewById( R.id.banner3 );
        insertaBanner( b3,"banner3" );
        ImageView b4=(ImageView)view.findViewById( R.id.banner4 );
        insertaBanner( b4,"banner4" );
        ImageView b5=(ImageView)view.findViewById( R.id.banner5 );
        insertaBanner( b5,"banner5" );

        // Inflate the layout for this fragment
        return view;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        //al hacer clic en cada una de las imágenes, estas nos dirigen hacia la pantalla correspondiente
        ImageView b1=view.findViewById( R.id.banner1 );
        b1.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                menuImagenes( "DECORACION" );
            }
        } );
        ImageView b2=(ImageView)view.findViewById( R.id.banner2 );
        b2.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                menuImagenes( "ILUMINACION" );
            }
        } );
        ImageView b3=(ImageView)view.findViewById( R.id.banner3 );
        b3.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                menuImagenes( "TEXTIL" );
            }
        } );
        ImageView b4=(ImageView)view.findViewById( R.id.banner4 );
        b4.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                menuImagenes( "MOBILIARIO" );
            }
        } );
        ImageView b5=(ImageView)view.findViewById( R.id.banner5 );
        b5.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                menuImagenes( "inicio" );
            }
        } );

    }

    //función para insertar cada imagen en su contenedor
    public void insertaBanner(final ImageView banner, String nameImage){
        BitmapImageViewTarget into = Glide
                .with( getContext( ) )
                .load( Uri.parse( "android.resource://com.example.toni.app_the_object_home/drawable/" + nameImage ) )
                .asBitmap( )
                .error( R.drawable.ic_launcher_foreground )
                .centerCrop( )
                .into( new BitmapImageViewTarget( banner ) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable
                                = RoundedBitmapDrawableFactory.create( getContext( ).getResources( ), resource );
                        drawable.setCircular( false );
                        banner.setImageDrawable( drawable );
                    }
                } );

    }

    //función que realiza las transiciones entre fragments
    public void menuImagenes(String categoriaTransicion){
        if(categoriaTransicion!="inicio"){
            Bundle bundle = new Bundle();
            bundle.putString("categoria", categoriaTransicion);
            FragmentDecoracion fragmentDeco=new FragmentDecoracion();
            fragmentDeco.setArguments( bundle );
            getFragmentManager().beginTransaction().replace(R.id.contenedor,fragmentDeco).addToBackStack(null).commit();
        }else{
            Inicio inicio=new Inicio();
            getFragmentManager().beginTransaction().replace(R.id.contenedor,inicio).addToBackStack(null).commit();
        }


    }



}
