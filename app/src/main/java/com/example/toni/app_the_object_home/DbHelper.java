package com.example.toni.app_the_object_home;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Locale;

/**
 * Created by Toni on 30/11/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="productos.sqlite";
    private static final int DB_SCHEME_VERSION= 1;


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }

    //Método que se ejecuta cuando se crea la base de datos, aquí se crean las tablas
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseManager.CREATE_TABLE);

    }

    //Se encarga de actualizar la versión de la base de datos cuando actualicemos los campos de la tabla
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
