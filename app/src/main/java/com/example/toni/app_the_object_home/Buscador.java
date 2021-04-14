package com.example.toni.app_the_object_home;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;


public class Buscador extends Fragment {

    private Spinner ordenarProductos;
    private DataBaseManager manager;
    private Cursor cursor;
    private GridView lista;
    private AdaptadorPrincipal adapter;
    private Button btnOrdenar;

    public Buscador() {
        // Required empty public constructor
    }
    public static Buscador newInstance(){
        return new Buscador();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate( R.layout.fragment_buscador, container, false );
        //Creamos una instancia para la iniciación de la base de datos
        manager=new DataBaseManager(getContext());
        //insertamos los datos en la BD
        manager.CargaDatosDB();

        //buscamos el Gridview para insertar los resultados de la búsqueda
        lista=(GridView)view.findViewById( R.id.listaBuscador );
        //Extraemos el valor pasado por el navigation drawer y asi saber que elementos mostrar
        final String myValue = this.getArguments().getString("busqueda");
        //consulta a la DB
        cursor=manager.cargaCursorBuscador( myValue );
        //localizamos el textview donde indicaremos al usuario los resultados de la búsqueda
        TextView titulo=(TextView)view.findViewById( R.id.nItemsLista );
        //buscamos el spinner y su título
        Spinner spinerOpciones=(Spinner)view.findViewById( R.id.spinnerOrden );
        TextView titleSpinner=(TextView)view.findViewById( R.id.titleSpinner );

        //si la búsqueda no arroja resultados
        if(cursor.getCount()==0){
            //le indicamos al usuario de que no hy registros para su búsqueda
            titulo.setText( "No hay productos para su búsqueda" );
            spinerOpciones.setVisibility( View.INVISIBLE );
            titleSpinner.setVisibility( View.INVISIBLE );

        }
        //si la búsqueda devuelve resultados
        else{

            titleSpinner.setVisibility( View.VISIBLE );
           spinerOpciones.setVisibility( View.VISIBLE );
            //Creamos el String que contiene las dos opciones
            String[] opciones={"Mejor coincidencia","Menor precio","Mayor precio"};
            //Utilizamos un adaptador para colocar las opciones dentro del Spinner
            ArrayAdapter adapterSpinner = new ArrayAdapter<String>(getContext(),R.layout.item_spinner, opciones);
            spinerOpciones.setAdapter( adapterSpinner );

            //creamos un nuevo adaptador, reutilizamos el que usamos cuando el usuario viene del menú
            adapter= new AdaptadorPrincipal( getContext(),cursor );
            //insertamos los datos en nuestra lista
            lista.setAdapter( adapter );

            //según el número de registros variamos ligeramente el mensaje
            if(cursor.getCount()==1){
                titulo.setText( cursor.getCount()+" ARTÍCULO");

            }else{
                titulo.setText(  cursor.getCount()+" ARTÍCULOS");
            }


            spinerOpciones.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener( ) {
                @Override
                public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {
                    if(position==0){
                        cursor=manager.cargaCursorBuscador( myValue );
                        adapter.changeCursor( cursor );
                    }else if (position==1){
                        cursor = manager.cargaCursorPrecioAscendente(myValue);
                        adapter.changeCursor( cursor );
                    }else if(position==2){
                        cursor = manager.cargaCursorPrecioDescendente(myValue);
                        adapter.changeCursor( cursor );
                    }
                }

                @Override
                public void onNothingSelected(AdapterView <?> parent) {

                }
            } );

        }


        //le indicamos que item de la lista pulsamos y pasamos a su ficha correspondiente
        lista.setOnItemClickListener (new  AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick (AdapterView <?> parent, View view, int position, long id) {

                //Se busca la referencia del TextView en la vista.
                TextView textView = (TextView) view.findViewById(R.id.nombreProducto);
                //Obtiene el texto dentro del TextView de la celda.
                String textItemList  = textView.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("nombre", textItemList);
                Ficha ficha =new Ficha();
                ficha.setArguments( bundle );
                getFragmentManager().beginTransaction().replace( R.id.contenedor,ficha ).addToBackStack( null ).commit();
                //getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,fragmentDeco).addToBackStack(null).commit();

            }
        });

        return view;



    }





}
