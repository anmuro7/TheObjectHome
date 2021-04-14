package com.example.toni.app_the_object_home;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Toni on 30/11/2017.
 */

public class DataBaseManager {
    //nombre tabla
    public static final String TABLE_NAME="productos";

    //nombre de los campos
    public static final String CN_ID="_id";
    public static final String CN_REFERENCE="referencia";
    public static final String CN_NAME="nombre";
    public static final String CN_DESCRIPTION="descripcion";
    public static final String CN_IMAGE="imagen";
    public static final String CN_PRICE="precio";
    public static final String CN_STOCK="stock";
    public static final String CN_CATEGORY="categoria";

    //definimos la sentencia que creará la tabla con los productos
    public static final String CREATE_TABLE = " create table " +TABLE_NAME+ " ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_REFERENCE + " text not null unique,"
            + CN_NAME +  " text not null unique,"
            + CN_DESCRIPTION + " text not null,"
            + CN_IMAGE + " text not null unique,"
            + CN_PRICE + " text not null ,"
            + CN_STOCK + " text not null,"
            + CN_CATEGORY + " text not null);";

    private DbHelper helper;
    private SQLiteDatabase db;

    //método constructor de la clase
    public DataBaseManager(Context context) {
        //creamos una instancia de DbHelper
        helper = new DbHelper(context);
        db= helper.getWritableDatabase();
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<MÉTODO QUE INSERTA LOS VALORES QUE LE PASEMOS EN LA BASE DE DATOS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //Creamos el método para la inserción de los datos en la base de datos. pasandole los campos que insertaremos
    public  void insertar(String referencia, String nombre, String descripcion, String imagen, String precio, String stock, String categoria){
        db.insert(TABLE_NAME,null,generarContentValues( referencia,nombre,descripcion,imagen,precio,stock,categoria ));

    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<MÉTODO AUXLIAR PARA LA INSERCIÓN EN LA BASE DE DATOS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public ContentValues generarContentValues(String referencia, String nombre, String descripcion, String imagen, String precio, String stock, String categoria){
        ContentValues valores = new ContentValues(  );
        valores.put( CN_REFERENCE,referencia );
        valores.put( CN_NAME,nombre );
        valores.put( CN_DESCRIPTION,descripcion );
        valores.put( CN_IMAGE,imagen );
        valores.put( CN_PRICE,precio );
        valores.put( CN_STOCK,stock );
        valores.put( CN_CATEGORY,categoria );
        return valores;
    }

    //<<<<<<<<<<<<<<<<<<CARGA DEPARAMETROS DE LA BASE DE DATOS EN UN CURSOR>>>>>>>>>>>>>>>>>>>>>>>>

    //creamos el cursor que nos devuelve los registros para la ficha del producto
    public  Cursor cargaCursorCatalogo(String categoria){
        String query= "SELECT * FROM " + TABLE_NAME + " WHERE categoria = '" + categoria + "'";
        Cursor c = db.rawQuery(query,null);
        return c;
    }
    //creamos los cursores que se encargaran de ordenar los productos en función de la selección del spinner
    public  Cursor ordenarCatalogoAscendente(String categoria){
        String query= "SELECT * FROM " + TABLE_NAME  + " WHERE categoria = '"+categoria+ "' ORDER BY CAST(precio AS DECIMAL(20,2)) ASC";
        Cursor c = db.rawQuery(query,null);
        return c;
    }
    //creamos los cursores que se encargaran de ordenar los productos en función de la selección del spinner
    public  Cursor ordenarCatalogoDescendente(String categoria){
        String query= "SELECT * FROM " + TABLE_NAME + " WHERE categoria = '" + categoria + "'ORDER BY CAST(precio AS DECIMAL(20,2)) DESC";
        Cursor c = db.rawQuery(query,null);
        return c;
    }
    public Cursor cargaCursorBuscador(String busqueda){
        String query= "SELECT * FROM " + TABLE_NAME  + " WHERE nombre LIKE '%"+busqueda+"%'";
        Cursor c = db.rawQuery(query,null);
        return c;
    }
    //creamos el cursor que nos devuelve los registros para la ficha del producto
    public  Cursor cargaCursorPorNombre(String name){
        String query= "SELECT * FROM " + TABLE_NAME + " WHERE nombre = '" + name + "'";
        Cursor c = db.rawQuery(query,null);
        return c;
    }
    //creamos el cursor para ordenar datos por orden descendente
    public  Cursor cargaCursorPrecioAscendente(String busqueda){
        String query="SELECT * FROM " + TABLE_NAME  + " WHERE nombre LIKE '%"+busqueda+"%' ORDER BY CAST(precio AS DECIMAL(20,2)) ASC";
        // String query= "SELECT * FROM productos  ORDER BY CAST(precio AS DECIMAL(20,2)) DESC";
        Cursor c = db.rawQuery(query,null);
        return c;
    }

    //creamos el cursor para ordenar datos por orden descendente
    public  Cursor cargaCursorPrecioDescendente(String busqueda){
        String query="SELECT * FROM " + TABLE_NAME  + " WHERE nombre LIKE '%"+busqueda+"%' ORDER BY CAST(precio AS DECIMAL(20,2)) DESC";
        // String query= "SELECT * FROM productos  ORDER BY CAST(precio AS DECIMAL(20,2)) DESC";
        Cursor c = db.rawQuery(query,null);
        return c;
    }
    //<<<<<<<<<<<<<<<<<<<<<CREAMOS UN MÉTODO QUE SE ENCARGA DE LA CARDA DE DATOS EN LA BD>>>>>>>>>>>>>>>>>>>>>>>>>>
    public void CargaDatosDB (){
        insertar( "d0001", "Cuadro LEAH","El cuadro LEAH, es un cuadro de pared en el cual la estructura es de madera y el diseño está realizado sobre tejido.","d0001","91.95 ","SI","DECORACION" );
        insertar( "d0002", "Cuadro LETO","El cuadro LETO, es un cuadro de pared en el cual la estructura es de madera y el diseño está realizado sobre tejido.","d0002","60.95 ","SI","DECORACION" );
        insertar( "d0003", "Espejo LIVANA","El espejo LIVANA  es un espejo de pared con luz y unos acabados de metal envejecido que le dan una estética muy original. El espejo LIVANA recuerda a los típicos espejos que tienen las estrellas del espectáculo en sus camerinos.","d0003","129.95 ","SI","DECORACION" );
        insertar( "d0004", "Mural LARIA","El mural de pared LARIA, es una pieza decorativa con marcado estilo industrial. Se diseño está formado por el símbolo & metálico acabado con un color rojo con detalles envejecidos que le aportan esa estética industrial.","d0004","109.95 ","SI","DECORACION" );
        insertar( "d0005", "Organizador LARISSA","El organizador LARISSA, es un organizador de pared de estilo industrial. Dale un toque diferente a tu recibidor con este accesorio que combina la calidez de la madera natural y la personalidad del metal envejecido","d0005","60.95 ","SI","DECORACION" );
        insertar( "d0006", "Tarro LEANDRA","El tarro LEANDRA, es un bonito tarro de cristal transparente reciclado con un original corcho que tapa la parte superior del tarro. En su diseño incluye también un gravado que le aporta un toque artesanal a la pieza.","d0006","42.00 ","SI","DECORACION" );
        insertar( "d0007", "Jarrón KAIA","El jarrón KAIA, es un bonito jarrón de cristal con un degradado bitono que va de transparente en la parte superior a turquesa en la parte inferior. Su diseño en forma de botella con una base amplia y un estrechamiento en la parte del cuello, hacen del jarrón KAIA una pieza perfecta para decorar cualquier estancia de tu hogar. ","d0007","28.00 ","SI","DECORACION" );
        insertar( "i2000", "Lámpara  MAGGIE","La lámpara MAGGIE, es una lámpara de techo con un acabado en metal envejecido en un tono óxido.","i2000","103.90 ","SI","ILUMINACION" );
        insertar( "i2001", "Lámpara  MAIA","La lámpara MAIA, es una lámpara de techo formada por tres pantallas iguales de cristal emplomado transparente y con una estructura en metal galvanizado en color negro.","i2001","125.95 ","SI","ILUMINACION" );
        insertar( "i2002", "Lámpara  MAEVE","La lámpara MAEVE, es una lámpara de techo formada por tres pantalla con forma de tarro de cristal grabados y con detalles metálicos, el cable de suspensión es en color rojo.","i2002","122.00 ","SI","ILUMINACION" );
        insertar( "i2003", "Lámpara MELBA blanca","La lámpara MELBA, es una lámpara de mesa con un pie en madera de forma curvada y una pantalla en algodón de color blanco.","i2003","60.90 ","SI","ILUMINACION" );
        insertar( "i2004", "Lámpara MELBA negra","La lámpara MELBA, es una lámpara de mesa con un pie en madera de forma curvada y una pantalla en algodón de color negro.","i2004","60.90 ","SI","ILUMINACION" );
        insertar( "i2005", "Lámpara  MACARIA","La lámpara MACARIA, es una lámpara de techo de cristal emplomado transparente y una estructura de metal galvanizado en color negro.","i2005","105.65 ","SI","ILUMINACION" );
        insertar( "i2006", "Lámpara  MADGE","La lámpara MADGE, es una lámpara de techo de marcado estilo industrial. Combina una pantalla totalmente metálica con un cable de estilo industrial que combina un gancho metálico con una cuerda haciendo el enganche entre la pantalla y el cable, en conjunto se trata de una lámpara ideal para decoraciones industriales.","i2006","102.00 ","SI","ILUMINACION" );
        insertar( "i2007", "Lámpara  LYSANDER","La lámpara LYSANDER, es un conjunto de 5 pantallas de cristal emplomado transparente y una estructura de metal galvanizado en color cobre. Cada una de las pantallas de la lámpara LYSANDER está formada por una estructura metálica y la propia pantalla de la lámpara que es de cristal transparente, todo ello forma una forma de pirámide inacabada que le aporta un toque muy distintivo.","i2007","145.55 ","SI","ILUMINACION" );
        insertar( "i2008", "Lámpara  MYLES","La lámpara MYLES, es una lámpara de pie con un pie metálico en forma de trípode y una pantalla también metálica con varillas de metal en color latón.","i2008","148.55 ","SI","ILUMINACION" );
        insertar( "m0001", "Mesa  OLYMPIA","La mesita auxiliar OLYMPIA, es una mesa de madera maciza de mango que combina una estructura pintada en color azul envejecido con un sobre en madera reciclada.","m0001","265.95 ","SI","MOBILIARIO" );
        insertar( "m1001", "Mesas  OMEGA","Las mesas nido OMEGA, son un juego de mesitas nido de madera de mango con un marco de hierro y un grafismo estampado en ambas.","m1001","253.95 ","SI","MOBILIARIO" );
        insertar( "m1002", "Contenedor OPHIRA","El contenedor OPHIRA, es un contenedor en madera de mango natural que combina una estética envejecida con unas ruedas metálicas decorativas que le dan un aire industrial perfecto para decorar ambientes con este estilo decorativo tan de moda actualmente.","m1002","99.95 ","SI","MOBILIARIO" );
        insertar( "m1004", "Estanteria NICIA","La estantería NICIA, es una estantería inclinada de pared donde los estantes están fabricados en madera de abeto envejecido y la estructura en metal. La combinación de madera envejecida y metal, hacen de la estantería NICIA la opción perfecta si lo que estás buscando es una estantería de estética industrial","m1004","450.95 ","SI","MOBILIARIO" );
        insertar( "m1005", "Estanteria NICOLE","La estantería NICOLE, es una estantería inclinada de pared con 2 estantes y una cajonera de 3 cajones fabricados en madera de abeto envejecido y una estructura metálica","m1005","495.95 ","SI","MOBILIARIO" );
        insertar( "m1006", "Mesa NEOMA","La mesita auxiliar NEOMA, es una mesita metálica con un acabado de pintura por pulverización electroestática de color blanco, su pie metálico recuerda a la forma de las persianas de los comercios.","m1006","62.95 ","SI","MOBILIARIO" );
        insertar( "m1007", "Mesa NERIN ","La mesita auxiliar NERIN, es una mesita que combina un sobre en madera de color blanca con un pie de metal acabado con pintura por pulverización electrostática de color negro.","m1007","253.95","SI","MOBILIARIO" );
        insertar( "t3000","Cojín ELDORIS antracita", "El cojín ELDORIS, es un cojín desenfundable fabricado 100% en poliéster que incluye el relleno de fibra. Se trata de un cojín ligero y fácil de combinar gracias a la gran variedad de colores de los que dispones para elegir.","t3000","21.50 ","SI","TEXTIL" );
        insertar( "t3001","Cojín ELDORIS gris", "El cojín ELDORIS, es un cojín desenfundable fabricado 100% en poliéster que incluye el relleno de fibra. Se trata de un cojín ligero y fácil de combinar gracias a la gran variedad de colores de los que dispones para elegir.","t3001","21.50 ","SI","TEXTIL" );
        insertar( "t3002","Cojín ELDORIS verde", "El cojín ELDORIS, es un cojín desenfundable fabricado 100% en poliéster que incluye el relleno de fibra. Se trata de un cojín ligero y fácil de combinar gracias a la gran variedad de colores de los que dispones para elegir.","t3002","21.50 ","SI","TEXTIL" );
        insertar( "t3003","Cojín ELDORIS beige", "El cojín ELDORIS, es un cojín desenfundable fabricado 100% en poliéster que incluye el relleno de fibra. Se trata de un cojín ligero y fácil de combinar gracias a la gran variedad de colores de los que dispones para elegir.","t3003","21.50 ","SI","TEXTIL" );
        insertar( "t3004","Cojín ELDORIS azul 1", "El cojín ELDORIS, es un cojín desenfundable fabricado 100% en poliéster que incluye el relleno de fibra. Se trata de un cojín ligero y fácil de combinar gracias a la gran variedad de colores de los que dispones para elegir.","t3004","21.50 ","SI","TEXTIL" );
        insertar( "t3005","Cojín ELDORIS azul 2", "El cojín ELDORIS, es un cojín desenfundable fabricado 100% en poliéster que incluye el relleno de fibra. Se trata de un cojín ligero y fácil de combinar gracias a la gran variedad de colores de los que dispones para elegir.","t3005","21.50 ","SI","TEXTIL" );
        insertar( "t3006","Cojín ELDORIS mostaza", "El cojín ELDORIS, es un cojín desenfundable fabricado 100% en poliéster que incluye el relleno de fibra. Se trata de un cojín ligero y fácil de combinar gracias a la gran variedad de colores de los que dispones para elegir.","t3006","21.50 ","SI","TEXTIL" );
        insertar( "t3007","Cojín CYRYL", "El cojín CYRIL, es un cojín rectangular con dibujo bordado 100% poliéster. Presenta un diseño con figuras geométricas en colores muy vivos perfectos para darle ese toque final a la decoración de tu hogar.","t3007","23.95 ","SI","TEXTIL" );

    }

}
