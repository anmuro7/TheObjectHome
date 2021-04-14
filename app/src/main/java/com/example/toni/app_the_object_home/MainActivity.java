package com.example.toni.app_the_object_home;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static MainActivity newInstance(){
        return new MainActivity();
    }



    //DEFINIMOS LAS INSTANCIAS A LOS OBJETOS NECESARIOS PARA LA CREACIÓN DE LA BD
    private ImageView carrito;
    private ImageView buscar;
    //creamos la variable ArrayList que contendra los objetos del carro
    public ArrayList<ProductoCarro> productosCarro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //iniciamos el Array para que se ejecute en cuanto iniciamos la app y podamos ir añadiendo productos
        productosCarro=new ArrayList <ProductoCarro>(  );

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );

        //Creamos una instancia del fragmento que contendrá la página principal de la aplicación para que se
        //muestre al cargarse la app
        Inicio fragmentPrincipal=new Inicio();
        //Insertamos el fragmento para que se sobreponga al layout actual
        getSupportFragmentManager().beginTransaction().add(R.id.contenedor,fragmentPrincipal).commit();

        //Buscamos el icono del carro compra
        carrito=(ImageView)findViewById( R.id.carro );
        buscar=(ImageView)findViewById( R.id.botonBuscar );

        //le asignamos un evento para que escuche el clic
        carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Carrodecompra carroCompra=new Carrodecompra();
                //sustituimos el fragmento actual por el fragmento del carro de compra
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,carroCompra).addToBackStack(null).commit();
            }
        });
        //cuando el usuario haga clic sobre el buscador, le aparece el alertDialog para introducir su búsqueda
        buscar.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                dialogBuscador();
            }
        } );


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //Creamos las transiciones de fragments del navigation drawer.
        int id = item.getItemId();

        if (id == R.id.dir_inicio) {
            String categoria="inicio";
            Inicio inicio=new Inicio();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,inicio).addToBackStack(null).commit();

        }
        //para las categorias que definamos, le pasamos un String que posteriormente nos permitirá mostrar los datos correspondientes
        else if (id == R.id.dir_decoracion) {
            String categoria="DECORACION";

            Bundle bundle = new Bundle();
            bundle.putString("categoria", categoria);
            FragmentDecoracion fragmentDeco=new FragmentDecoracion();
            fragmentDeco.setArguments( bundle );
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,fragmentDeco).addToBackStack(null).commit();

        } else if (id == R.id.dir_muebles) {
            String categoria="MOBILIARIO";

            Bundle bundle = new Bundle();
            bundle.putString("categoria", categoria);
            FragmentDecoracion fragmentDeco=new FragmentDecoracion();
            fragmentDeco.setArguments( bundle );
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,fragmentDeco).addToBackStack(null).commit();

        } else if (id == R.id.dir_iluminacion) {
            String categoria="ILUMINACION";
            Bundle bundle = new Bundle();
            bundle.putString("categoria", categoria);
            FragmentDecoracion fragmentDeco=new FragmentDecoracion();
            fragmentDeco.setArguments( bundle );
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,fragmentDeco).addToBackStack(null).commit();
        } else if (id == R.id.dir_textil) {
            String categoria="TEXTIL";
            Bundle bundle = new Bundle();
            bundle.putString("categoria", categoria);
            FragmentDecoracion fragmentDeco=new FragmentDecoracion();
            fragmentDeco.setArguments( bundle );
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,fragmentDeco).addToBackStack(null).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    //método que inserta los productos en el carro
    public void insertaCarro(String nombre,String cantidad, String precio, String imagen){
        //comprobamos que el producto no está en el arralist
        Boolean productoEnCarro=preparaCarro( nombre );
        if(productoEnCarro){
            //Actualizamos la cantidad de producto pasandole la actual
            //variable con cantidad actualizada
            int cantidadNueva=Integer.parseInt( cantidad );
            //buscamos en que posicion se encuentra el elemento repetido
                for(int i=0;i<productosCarro.size();i++){
                    //condicion para parar
                    if(productosCarro.get( i ).getNombre().equals( nombre )){
                        //extraemos la cantidad almacenada en esa posicón
                        int cantidadActual=Integer.parseInt(productosCarro.get( i ).getCantidad());
                        //sumamos la nueva cantidad
                        cantidadNueva=cantidadNueva+cantidadActual;
                        //introducimos la nueva cantidad en esta posicion
                        productosCarro.get( i ).setCantidad( Integer.toString( cantidadNueva ) );
                    }
                }

            //
        }else
        {
            //Creamos un objeto ProductoCarro
            ProductoCarro productoCarro=new ProductoCarro( nombre,cantidad,precio,imagen);
            productosCarro.add( productoCarro );
        }
        TextView cantidadCarro=(TextView)findViewById( R.id.cantidadCarrito );
        cantidadCarro.setText(Integer.toString( productosCarro.size() ) );
        }

    //método que usamos para modificar la cantidad de un producto concreto en el carro
    public void modificaCantidadCarro(String nombre,String cantidad){
        //comprobamos que el producto no está en el arralist
        Boolean productoEnCarro=preparaCarro( nombre );
        if(productoEnCarro){
            //Actualizamos la cantidad de producto pasandole la actual
            //buscamos en que posicion se encuentra el elemento que queremos modificar
            for(int i=0;i<productosCarro.size();i++){
                //condicion para parar
                if(productosCarro.get( i ).getNombre().equals( nombre )){
                    //extraemos la cantidad almacenada en esa posicón
                    int cantidadActual=Integer.parseInt(productosCarro.get( i ).getCantidad());
                    //sustituimos la cantidad
                    productosCarro.get( i ).setCantidad( cantidad );
                }
            }
        }
    }

    //función utilizada para eliminar un producto concreto del carro
    public void eliminaProductoCarro(String nombre){
        //comprobamos que el producto no está en el arraylist
        Boolean productoEnCarro=preparaCarro( nombre );
        if(productoEnCarro){
            //Actualizamos la cantidad de producto pasandole la actual
            //buscamos en que posicion se encuentra el elemento repetido
            for(int i=0;i<productosCarro.size();i++){
                //condicion para parar
                if(productosCarro.get( i ).getNombre().equals( nombre )){
                  productosCarro.remove( i );
                }
            }
            //comprobamos que si queda algún producto en el carro
            TextView cantidadCarro=(TextView)findViewById( R.id.cantidadCarrito );
            if(productosCarro.size()==0){
                //dejamos vació el TextView que indica al usuario el numero de referencias en el carro
                cantidadCarro.setText(" " );
            }else{
                //actualizamos el número de referencias en el carro
                cantidadCarro.setText( Integer.toString( productosCarro.size() ) );
            }
        }
    }



        //Creamos otro método que recorre el ArrayList cuando pasemos al carro de la compra
    public Boolean preparaCarro(String nombre){
        Boolean repetido=false;
        //pasamos el nombre que queremos saber si está en el arrrayList
        //recorremos el array para comprobar elementos repetidos
        for (int i =0;i<productosCarro.size();i++){
          if(nombre.equals( productosCarro.get( i ).getNombre() )){
              repetido=true;
          }
        }
        return repetido;
    }

    //método que mostrará el alertDialog donde el usuario introduce su búsqueda
    public AlertDialog dialogBuscador() {
        final AlertDialog alertDialog;

        //Le indicamos que tiene que mostrar nuestro Alert Dialog personalizado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_buscador, null);
        builder.setView(v);

        //Permitimos que el ususario pueda cerrar el alertdialog al hacer clic fuera de él
        builder.setCancelable( true );

        //localizamos los botones de nuestro alertdialog para crear su funcionalidad
        Button buscarProducto = (Button) v.findViewById(R.id.botonBuscaProducto);
        //localizamos el boton para cerrar el dialog
        ImageView btnClose=(ImageView)v.findViewById( R.id.closeBuscador );
        //Localizamos el Edittext donde se introduce la búsqueda
        final EditText productoaBuscar=(EditText)v.findViewById( R.id.productoABuscar );
        alertDialog= builder.create();
        alertDialog.show();

        btnClose.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        } );
        //cuando el usuario pulse el boton...
        buscarProducto.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("busqueda", productoaBuscar.getText().toString());
                Buscador buscador=new Buscador();
                buscador.setArguments( bundle );
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,buscador).addToBackStack(null).commit();
                alertDialog.dismiss();
            }
        } );

        //devolvemos el alertdialog
        return alertDialog;

    }



}







