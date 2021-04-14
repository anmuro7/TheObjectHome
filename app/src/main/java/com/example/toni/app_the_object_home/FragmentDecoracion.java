package com.example.toni.app_the_object_home;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

public class FragmentDecoracion extends Fragment {


    private DataBaseManager manager;
    private Cursor cursor;
    private GridView lista;
    private AdaptadorPrincipal adapter;

    public FragmentDecoracion() {
        // Required empty public constructor
    }
    public static FragmentDecoracion newInstance(){
        return new FragmentDecoracion();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Creamos una instancia para la iniciación de la base de datos
        manager=new DataBaseManager(getContext());
        //insertamos los datos en la BD
        manager.CargaDatosDB();


        View view = inflater.inflate(R.layout.fragment_fragment_decoracion, container, false);
        //Extraemos el valor pasado por el navigation drawer y asi saber que elementos mostrar
        final String myValue = this.getArguments().getString("categoria");
        TextView Titulo= view.findViewById( R.id.titleBusqueda );
        //insertamos el titulo de la categoria
        Titulo.setText( myValue );

        TextView titleSpinnerCatalogo=(TextView)view.findViewById( R.id.titleSpnCatalogo );
        Spinner spnCatalogo=(Spinner)view.findViewById( R.id.spnCatalog );
        TextView numeroProductos=(TextView)view.findViewById( R.id.productosCategoria );

        //Creamos el String que contiene las dos opciones
        String[] opciones={"Mejor coincidencia","Menor precio","Mayor precio"};
        //Utilizamos un adaptador para colocar las opciones dentro del Spinner
        ArrayAdapter adapterSpinner = new ArrayAdapter<String>(getContext(),R.layout.item_spinner, opciones);
        spnCatalogo.setAdapter( adapterSpinner );

        //INSERTAMOS EN EL GRIDVIEW LOS PRODUCTOS DE LA CATEGORIA CORRESPONDIENTE
        if (myValue=="DECORACION"){

            //localizamos la lista donde usaremos el adapter
            lista=(GridView)view.findViewById(R.id.listaDecoración);
            //cargamos el cursor donde almacenamos la consulta a la BD
            cursor=manager.cargaCursorCatalogo( myValue );
            //Creamos una instancia a nuestro Adaptador y le pasamos el cursor con los datos almacenados para que haga
            //el tratamiento
            //añadimos el número de productos disponibles en la categoria donde nos encontremos
            numeroProductos.setText( cursor.getCount()+" ARTÍCULOS" );
            adapter= new AdaptadorPrincipal( getContext(),cursor );
            //insertamos los datos en nuestra lista
            lista.setAdapter( adapter );

        }else if(myValue=="MOBILIARIO"){

            //localizamos la lista donde usaremos el adapter
            lista=(GridView)view.findViewById(R.id.listaDecoración);
            //cargamos el cursor donde almacenamos la consulta a la BD
            cursor=manager.cargaCursorCatalogo( myValue );
            //añadimos el número de productos disponibles en la categoria donde nos encontremos
            numeroProductos.setText( cursor.getCount()+" ARTÍCULOS" );
            //Creamos una instancia a nuestro Adaptador y le pasamos el cursor con los datos almacenados para que haga
            //el tratamiento
            adapter= new AdaptadorPrincipal( getContext(),cursor );
            //insertamos los datos en nuestra lista
            lista.setAdapter( adapter );

        }else if(myValue=="ILUMINACION"){
            //localizamos la lista donde usaremos el adapter
            lista=(GridView)view.findViewById(R.id.listaDecoración);
            //cargamos el cursor donde almacenamos la consulta a la BD
            cursor=manager.cargaCursorCatalogo( myValue );
            //añadimos el número de productos disponibles en la categoria donde nos encontremos
            numeroProductos.setText( cursor.getCount()+" ARTÍCULOS" );
            //Creamos una instancia a nuestro Adaptador y le pasamos el cursor con los datos almacenados para que haga
            //el tratamiento
            adapter= new AdaptadorPrincipal( getContext(),cursor );
            //insertamos los datos en nuestra lista
            lista.setAdapter( adapter );
        }else if(myValue=="TEXTIL"){
            //localizamos la lista donde usaremos el adapter
            lista=(GridView)view.findViewById(R.id.listaDecoración);
            //cargamos el cursor donde almacenamos la consulta a la BD
            cursor=manager.cargaCursorCatalogo( myValue );
            //añadimos el número de productos disponibles en la categoria donde nos encontremos
            numeroProductos.setText( cursor.getCount()+" ARTÍCULOS" );
            //Creamos una instancia a nuestro Adaptador y le pasamos el cursor con los datos almacenados para que haga
            //el tratamiento
            adapter= new AdaptadorPrincipal( getContext(),cursor );
            //insertamos los datos en nuestra lista
            lista.setAdapter( adapter );
        }
        //listener para los clic en el GridView
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
        //listener para definir las acciones del spinner
        spnCatalogo.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener( ) {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {
                if(position==0){
                    cursor=manager.cargaCursorCatalogo( myValue );
                    adapter.changeCursor( cursor );
                }else if (position==1){
                    cursor = manager.ordenarCatalogoAscendente(myValue);
                    adapter.changeCursor( cursor );
                }else if(position==2){
                    cursor = manager.ordenarCatalogoDescendente(myValue);
                    adapter.changeCursor( cursor );
                }
            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        } );




        return view;
    }





}
