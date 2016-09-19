package com.padulles.pablo.woocar_android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pablo on 17/03/16.
 */

public class PollSQLiteHelper extends SQLiteOpenHelper {


    //SENTENCIAS SQL de CREACION DB
    String sqlPosiciones = "CREATE TABLE posiciones (latitude TEXT, longitude TEXT, " +
            "speed TEXT)";

    //CONSTRUCTOR
    public  PollSQLiteHelper(Context contexto, String nombre,
                             SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }


    // Se ejecuta si la db no existe
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlPosiciones);
    }

    public void clearPosiciones(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS posiciones");

        db.execSQL(sqlPosiciones);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
