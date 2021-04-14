package com.example.toni.app_the_object_home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;


public class CarroCompra extends Fragment {

private ListView vistaListaCarro;
private AdaptadorCarrito adapter;

    public CarroCompra() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate( R.layout.fragment_carro_compra, container, false );
        vistaListaCarro=(ListView)view.findViewById( R.id.listaPantallaCarro );
        //Añadimos header y footer al listview
        LayoutInflater inflador = getLayoutInflater();
        ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.footer_carro, vistaListaCarro, false);
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header_carro, vistaListaCarro, false);
        vistaListaCarro.addHeaderView( header,null,false );
        vistaListaCarro.addFooterView(footer, null, false);
        ArrayList <ProductoCarro> listaCarro=((MainActivity)getActivity()).productosCarro;


        //instanciamos el adaptador
        adapter=new AdaptadorCarrito(  getContext(),listaCarro);
        //le añadimos los datos al listview
        vistaListaCarro.setAdapter( adapter );

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
       PVPPedido.setText( Double.toString( totalPedido )+" €");

        vistaListaCarro.setOnItemClickListener (new  AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick (AdapterView <?> parent, View view, int position, long id) {
                Toast.makeText( getContext(),"posicio"+position,Toast.LENGTH_SHORT ).show();
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
        return view;
    }





}
