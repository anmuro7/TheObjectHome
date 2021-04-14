package com.example.toni.app_the_object_home;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * Created by Toni on 30/11/2017.
 */

public class AdaptadorPrincipal extends CursorAdapter {
    public AdaptadorPrincipal(Context context, Cursor c) {
        super( context, c,0 );
    }

    //<<<<<<<<<<<MÉTODO EN EL QUE INDICAMOS LA PLANTILLA QUE UTILIZAREMOS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.listado_productos, parent, false);
        return view;
    }
//<<<<<<<<<<<<<<<<<MÉTODO EN EL QUE INSERTAREMOS LOS VALORES DE LA BD EN EL LISTADO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

//Definimos los contenedores donde insertaremos la información
        final ImageView contImagen = (ImageView)view.findViewById( R.id.fotoProducto );
        TextView contNombre= (TextView)view.findViewById( R.id.nombreProducto );
        TextView contPrecio=(TextView)view.findViewById( R.id.precioProducto );

//Esxtraemos de la BD los datos a insertar en el contenedor
        String image=cursor.getString( cursor.getColumnIndex( DataBaseManager.CN_IMAGE ));
        String name=cursor.getString( cursor.getColumnIndex( DataBaseManager.CN_NAME ));
        String price=cursor.getString( cursor.getColumnIndex( DataBaseManager.CN_PRICE ));

//Insertamos los valores extraidos dentro de los contenedores definidos
        //imagen de producto
        Glide
                .with(context)
                .load(Uri.parse("android.resource://com.example.toni.app_the_object_home/drawable/"+image ))
                .asBitmap()
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(new BitmapImageViewTarget(contImagen) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable
                                = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        drawable.setCircular(false);
                        contImagen.setImageDrawable(drawable);
                    }
                });
        //A continuación insertamos los textos dentro de los campos correspondientes
        contNombre.setText( name );
        contPrecio.setText( price+" €");





    }
}
