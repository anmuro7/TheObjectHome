package com.example.toni.app_the_object_home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

public class PagoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pago );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );


        String[] lista = (String[]) getIntent().getSerializableExtra("datos");

        //llamamos al método que inserta los datos
        insertaDatos( lista );


        ImageView salirApp=(ImageView)findViewById( R.id.imgClose );
        salirApp.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                //cerramos la aplicación
                finishAffinity();
            }
        } );

        ImageView volverInicio=(ImageView)findViewById( R.id.imgReturn );
        volverInicio.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                //volvemos a iniciar el MainActivity
                Intent intent;
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        } );

    }
    //método que inserta los datos en su lugar
    public void insertaDatos(String [] lista){
        //Buscamos los contenedores para insertar el resumen de la compra
        TextView nombreResumen=(TextView)findViewById( R.id.nombreResumen );
        nombreResumen.setText( "Nombre: "+lista[0].toString() );
        TextView totalResumen=(TextView)findViewById( R.id.totalResumen );
        totalResumen.setText( "Total pedido: "+lista[6].toString() );
        TextView ciudadResumen=(TextView)findViewById( R.id.direccionResumen );
        ciudadResumen.setText( "Dirección entrega: "+lista[3].toString()+ " ,"+ lista[4].toString()+" ,"+lista[5].toString() );
        TextView telefonoResumen=(TextView)findViewById( R.id.telefonoResumen );
        telefonoResumen.setText( "Teléfono: "+lista[1].toString() );
        TextView emailResumen=(TextView)findViewById( R.id.emailResumen );
        emailResumen.setText( "E-mail: "+lista[2].toString() );
    }

}
