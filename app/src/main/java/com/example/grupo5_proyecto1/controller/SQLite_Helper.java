package com.example.grupo5_proyecto1.controller;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLite_Helper extends SQLiteOpenHelper {
    public SQLite_Helper(Context context,String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private String queryAsignacion="create table ASIGNACIONES" +
            "(" +
            "   NODOCUMENTO          REAL not null," +
            "   CODMOTIVOASGINACION  REAL," +
            "   CODIGOARTICULO       varchar(10)," +
            "   DOCENTE              varchar(30)," +
            "   CODARTICULO          varchar(10)," +
            "   DESCRIPCION          varchar(30)," +
            "   FECHAASIGNACION      TEXT," +
            "   primary key (NODOCUMENTO)" +
            ");";
    private String queryAutores="create table AUTORES" +
            "(" +
            "   CODIGOARTICULO       varchar(10) not null," +
            "   CORLN                numeric(8,0) not null," +
            "   NOOMBRE              varchar(50)," +
            "   primary key (CODIGOARTICULO, CORLN)" +
            ");";
    private String queryCatalogoArticulo="create table CATALOGOARTICULO" +
            "(" +
            "   CODTIPOARTICULO      REAL not null," +
            "   DESCRIPCION          varchar(10)," +
            "   primary key (CODTIPOARTICULO)" +
            ");";
    private static String TABLA_ASIGNACIONES="ASIGNACIONES";
    private static String TABLA_AUTORES="AUTORES";
    private static String TABLA_CATALOGO_ARTICULO="CATALOGOARTICULO";

    private static String[] columna_asignacion={"NODOCUMENTO","CODMOTIVOASGINACION","CODIGOARTICULO","DOCENTE","CODARTICULO","DESCRIPCION","FECHAASIGNACION"};
    private static String[] columna_autores={"CODIGOARTICULO","CORLN","NOOMBRE"};
    private static String[] columna_catalogo_articul={"CODTIPOARTICULO","DESCRIPCION"};


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(queryAsignacion);
        db.execSQL(queryAutores);
        db.execSQL(queryCatalogoArticulo);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void abrir(){
        try{
            this.getWritableDatabase();
        }catch(SQLException e){
            e.printStackTrace(System.out);
        }
    }
    public void cerrar(){
        try{
            this.close();
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }
    }
}
