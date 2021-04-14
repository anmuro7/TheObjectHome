package com.example.toni.app_the_object_home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class DatosCompra extends Fragment {

    public DatosCompra() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate( R.layout.fragment_datos_compras, container, false );

       //Extraemos el valore pasado por el Bundle del fragmento carro compra
        String value=this.getArguments().getString( "total pedido" );
        final TextView muestraTotal=(TextView)view.findViewById( R.id.totalPedidoDatos );
        muestraTotal.setText( value );


        //seleccionamos el botón para asignar el evento
      Button btnFormulario=(Button)view.findViewById( R.id.btnForm );

       //seleccionamos todos los contenedores de datos
        final EditText campoNombre=(EditText) view.findViewById( R.id.nombreForm );
        final EditText campoEmail=(EditText)view.findViewById( R.id.emailForm ) ;
        final EditText campoTelefono=(EditText)view.findViewById( R.id.telefonoForm );
        final EditText campoDireccion=(EditText)view.findViewById( R.id.direccionForm );
        final EditText campoCp=(EditText)view.findViewById( R.id.cpForm );
        final EditText campoCiudad=(EditText)view.findViewById( R.id.ciudadForm );

        //mensajes de error para la validación del formulario
        final String errorNombre="Introduce un nombre ";
        final String errorEmail="Introduce un email válido: ejemplo@ejemplo.com";
        final String errorTelefono= "Introduce un fotmato de telefono válido XXXXXXXXX";
        final String errorDireccion="Error, introduce tu dirección de entrega";
        final String errorCp="Formato CP erroneo";
        final String errorCiudad="introduce tu ciudad";

      //asignamos el evento para pasar al siguiente fragment
      btnFormulario.setOnClickListener( new View.OnClickListener( ) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                //iniciamos variable para verificar si todos los datos son correctos
                boolean revisado=false;

                    //validamos el nombre
                    if(campoNombre.getText().toString().equals( errorNombre ) || campoNombre.getText().toString().equals( "" )){
                        campoNombre.setText(  errorNombre);
                        revisado=false;
                    }

                    //validamos el teléfono
                    else if(campoTelefono.getText().toString().equals( errorTelefono ) || campoTelefono.getText().toString().equals( "" ) || campoTelefono.getText().toString().length()<9 ){
                        campoTelefono.setText( errorTelefono);
                        revisado=false;
                    }

                    //validamos el e-mail
                    else if(campoEmail.getText().toString().equals( "" ) || !isEmailValid( campoEmail.getText().toString() ) || campoEmail.getText().toString().equals( errorEmail )){
                        campoEmail.setText(errorEmail);
                        revisado=false;
                    }
                    //validamos la dirección introducida
                    else if(campoDireccion.getText().toString().equals( "" ) || campoDireccion.getText().toString().equals( errorDireccion) ){
                        campoDireccion.setText(  errorDireccion);
                        revisado=false;
                    }

                    //Validamos el CP
                    else if(campoCp.getText().toString().equals( "" ) || campoCp.getText().toString().equals( errorCp) || campoCp.getText().toString().length()!=5 ){
                        campoCp.setText(  errorCp);
                        revisado=false;
                    }

                    //Validamos el campo ciudad
                    else if(campoCiudad.getText().toString().equals( "" ) || campoCiudad.getText().toString().equals( errorCiudad)  ){
                        campoCiudad.setText(  errorCiudad);
                        revisado=false;
                    }else{
                        revisado=true;
                    }

                    //si se pasan todas las validaciones...
                    if (revisado){
                        //guardamos todos los datos sobre el cliente
                        String[] datos={campoNombre.getText().toString(),campoTelefono.getText().toString(),
                        campoEmail.getText().toString(),campoDireccion.getText().toString(),campoCp.getText().toString(),
                        campoCiudad.getText().toString(),muestraTotal.getText().toString()};
                        Intent intent = new Intent(getContext(), PagoActivity.class);
                        intent.putExtra("datos", datos);
                        startActivity(intent);
                    }

            }
        } );
        // Inflate the layout for this fragment
        return view;
    }


    //método que nos indica si un email cumple las condiciones de formato requeridas
    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

}
