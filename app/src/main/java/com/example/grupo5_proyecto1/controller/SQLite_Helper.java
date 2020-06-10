package com.example.grupo5_proyecto1.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.grupo5_proyecto1.models.Articulo;
import com.example.grupo5_proyecto1.models.Asignacion;
import com.example.grupo5_proyecto1.models.CatalogoArticulo;
import com.example.grupo5_proyecto1.models.CatalogoMotivoAsignacion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SQLite_Helper extends SQLiteOpenHelper {
    private static  final String BASE_DATOS="mantto_libreria.s3db";
    private static final int VERSION=1;
    public SQLite_Helper(Context context) {
        super(context, BASE_DATOS, null, VERSION);
    }

    private String queryAsignacion="create table ASIGNACIONES" +
            "(" +
            "   NODOCUMENTO          INTEGER primary key AUTOINCREMENT," +
            "   CODMOTIVOASGINACION  INTEGER," +
            "   CODIGOARTICULO       varchar(10)," +
            "   DOCENTE              varchar(30)," +
            "   CODARTICULO          varchar(10)," +
            "   DESCRIPCION          varchar(30)," +
            "   FECHAASIGNACION      TEXT" +
            ");";
    private String queryAutores="create table AUTORES" +
            "(" +
            "   CODIGOARTICULO       varchar(10) not null," +
            "   CORLN                REAL not null," +
            "   NOOMBRE              varchar(50)," +
            "   primary key (CODIGOARTICULO, CORLN)" +
            ");";
    private String queryCatalogoArticulo="create table CATALOGOARTICULO" +
            "(" +
            "   CODTIPOARTICULO      varchar(10) not null," +
            "   DESCRIPCION          varchar(10)," +
            "   primary key (CODTIPOARTICULO)" +
            ");";


    //Relacion asignacion - catalogoMotivoAsignacion
    private String relacionCatalogoMotivoAsignacion="create table CATALOGOMOTIVOASIGNACION" +
            "(" +
            "   CODMOTIVOASGINACION  INTEGER primary key AUTOINCREMENT," +
            "   DESCRIPCION          varchar(10)" +
            ");";
    //Relacion asignacion + catalogoArticulo con Articulo
    private String relacionArticulo="create table ARTICULO" +
            "(" +
            "   CODIGOARTICULO       varchar(10) not null," +
            "   CODTIPOARTICULO      varchar(10)," +
            "   FECHAREGISTRO        TEXT," +
            "   ESTADO               INTEGER," +
            "   primary key (CODIGOARTICULO)" +
            ");";

    private static String TABLA_ASIGNACIONES="ASIGNACIONES";
    private static String TABLA_AUTORES="AUTORES";
    private static String TABLA_CATALOGO_ARTICULO="CATALOGOARTICULO";
    //Tabla relacion
    private static String TABLA_CATALOGOMOTASIG="CATALOGOMOTIVOASIGNACION";
    private static String TABLA_ARTICULO="ARTICULO";

    //Columna relacion
    private static String[] columna_cat_mot_asignacion={"CODMOTIVOASGINACION","DESCRIPCION"};
    private static String[] columna_articulo={"CODIGOARTICULO","CODTIPOARTICULO","FECHAREGISTRO","ESTADO"};

    private static String[] columna_asignacion={"NODOCUMENTO","CODMOTIVOASGINACION","CODIGOARTICULO","DOCENTE","CODARTICULO","DESCRIPCION","FECHAASIGNACION"};
    private static String[] columna_autores={"CODIGOARTICULO","CORLN","NOOMBRE"};
    private static String[] columna_catalogo_articul={"CODTIPOARTICULO","DESCRIPCION"};


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(queryAsignacion);
        db.execSQL(queryAutores);
        db.execSQL(queryCatalogoArticulo);
        //relacion con asignacion
        db.execSQL(relacionCatalogoMotivoAsignacion);
        db.execSQL(relacionArticulo);

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
    public String llenarBaseDatos(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String resultado;
        //Articulo
        final String[] VArticuloCodigo={"AA01","BB02","CC03"};
        final String[] VArticuloCodigoTipo={"ART1","ART1","ART2"};
        final String[] VArticuloFecha={formatter.format(calendar.getTime()).toString(),formatter.format(calendar.getTime()).toString(),formatter.format(calendar.getTime()).toString()};
        final int[] VArticuloEstado={1,1,1};

        //private static String[] columna_catalogo_articul={"CODTIPOARTICULO","DESCRIPCION"};
        //Catalogo Articulo
        final String[] VCatalogoArticuloCodigo={"ART1","ART2","ART3"};
        final String[] VACatalogoArticuloDescripcion={"Descripcion Articulo 1","Descripcion Articulo 2","Descripcion Articulo 3"};

        //private static String[] columna_cat_mot_asignacion={"CODMOTIVOASGINACION","DESCRIPCION"};
        //Categoria motivo asignacion
        final String[] VCatMoviAsignacion={"Prestamo","Alquiler","Pedido"};

        this.abrir();
        this.getWritableDatabase().execSQL("DELETE FROM "+TABLA_ARTICULO);
        this.getWritableDatabase().execSQL("DELETE FROM "+TABLA_CATALOGO_ARTICULO);
        this.getWritableDatabase().execSQL("DELETE FROM "+TABLA_CATALOGOMOTASIG);
        Articulo articulo=new Articulo();
        for(int i=0;i<3;i++){
            articulo.setCodigoArticulo(VArticuloCodigo[i]);
            articulo.setCodTipoArticulo(VArticuloCodigoTipo[i]);
            articulo.setFecha(VArticuloFecha[i]);
            articulo.setEstado(VArticuloEstado[i]);
            this.insertar(articulo);
        }
        CatalogoArticulo catalogoArticulo=new CatalogoArticulo();
        for(int i=0;i<3;i++){
            catalogoArticulo.setCodTipoArticulo(VCatalogoArticuloCodigo[i]);
            catalogoArticulo.setDescripcion(VACatalogoArticuloDescripcion[i]);
            this.insertar(catalogoArticulo);
        }
        CatalogoMotivoAsignacion catalogoMotivoAsignacion=new CatalogoMotivoAsignacion();
        for(int i=0;i<3;i++){
            catalogoMotivoAsignacion.setDescripcion(VCatMoviAsignacion[i]);
            this.insertar(catalogoMotivoAsignacion);
        }
        this.cerrar();
        return resultado="Guardado correctamente";
    }
    public String insertar(Articulo articulo){
        String regIngresado="Registro ingresado No.";
        long cont=0;
        try{
            ContentValues contentValues=new ContentValues();
            contentValues.put(columna_articulo[0],articulo.getCodigoArticulo());
            contentValues.put(columna_articulo[1],articulo.getCodTipoArticulo());
            contentValues.put(columna_articulo[2],articulo.getFecha());
            contentValues.put(columna_articulo[3],articulo.getEstado());
            cont=this.getWritableDatabase().insert(TABLA_ARTICULO,null,contentValues);
            if(cont==1 || cont==0){
                regIngresado="Error al insertar registro, Registro duplicado, verificar insersion";
            }else{
                regIngresado+=cont;
            }
        }catch(SQLException e){
            return  "Fallo al insert registro";
        }
        return  regIngresado;
    }

    public String insertar(CatalogoArticulo catalogoArticulo){
        String regIngresado="Registro ingresado No.";
        long cont=0;
        try{
            ContentValues contentValues=new ContentValues();
            contentValues.put(columna_catalogo_articul[0],catalogoArticulo.getCodTipoArticulo());
            contentValues.put(columna_catalogo_articul[1],catalogoArticulo.getDescripcion());
            cont=this.getWritableDatabase().insert(TABLA_CATALOGO_ARTICULO,null,contentValues);
            if(cont==1 || cont==0){
                regIngresado="Error al insertar registro, Registro duplicado, verificar insersion";
            }else{
                regIngresado+=cont;
            }
        }catch(SQLException e){
            return  "Fallo al insert registro";
        }
        return  regIngresado;
    }

    public String insertar(CatalogoMotivoAsignacion catalogoMotivoAsignacion){
        String regIngresado="Registro ingresado No.";
        long cont=0;
        try{
            ContentValues contentValues=new ContentValues();
            contentValues.put(columna_cat_mot_asignacion[1],catalogoMotivoAsignacion.getDescripcion());
            cont=this.getWritableDatabase().insert(TABLA_CATALOGOMOTASIG,null,contentValues);
            if(cont==1 || cont==0){
                regIngresado="Error al insertar registro, Registro duplicado, verificar insersion";
            }else{
                regIngresado+=cont;
            }
        }catch(SQLException e){
            return  "Fallo al insert registro";
        }
        return  regIngresado;
    }

    public List<Articulo> obtenerArticulo(){
        //columna_articulo={"CODIGOARTICULO","CODTIPOARTICULO","FECHAREGISTRO","ESTADO"};
        this.abrir();
        List<Articulo>articulos=new ArrayList<>();
        Cursor cursor=this.getReadableDatabase().query(TABLA_ARTICULO,columna_articulo,null,null,null,null,null);
        while(cursor.moveToNext()){
            Articulo articulo=new Articulo();
            articulo.setCodigoArticulo(cursor.getString(0));
            articulo.setCodTipoArticulo(cursor.getString(1));
            articulo.setEstado(cursor.getInt(2));
            articulo.setFecha(cursor.getString(3));
            articulos.add(articulo);
        }
        this.cerrar();
        return articulos;
    }
    public List<CatalogoMotivoAsignacion> obtenerCatalogoMotivoAsignacion(){
        this.abrir();
        List<CatalogoMotivoAsignacion>catalogoMotivoAsignacions=new ArrayList<>();
        Cursor cursor=this.getReadableDatabase().query(TABLA_CATALOGOMOTASIG,columna_cat_mot_asignacion,null,null,null,null,null);
        while(cursor.moveToNext()){
            CatalogoMotivoAsignacion catalogoMotivoAsignacion=new CatalogoMotivoAsignacion();
            catalogoMotivoAsignacion.setCodMotivoAsignacion(cursor.getInt(0));
            catalogoMotivoAsignacion.setDescripcion(cursor.getString(1));
            catalogoMotivoAsignacions.add(catalogoMotivoAsignacion);
        }
        this.cerrar();
        return catalogoMotivoAsignacions;
    }

    public String insertar(Asignacion asignacion){
        //columna_asignacion={"NODOCUMENTO","CODMOTIVOASGINACION","CODIGOARTICULO","DOCENTE","CODARTICULO","DESCRIPCION","FECHAASIGNACION"};
        String regIngresado="Registro ingresado No.";
        long cont=0;
        try{
            ContentValues contentValues=new ContentValues();
            contentValues.put(columna_asignacion[1],asignacion.getCodMotivoAsignacion());
            contentValues.put(columna_asignacion[2],asignacion.getCodigoArticulo());
            contentValues.put(columna_asignacion[3],asignacion.getDocente());
            contentValues.put(columna_asignacion[4],asignacion.getCodigoArticulo());
            contentValues.put(columna_asignacion[5],asignacion.getDescripcion());
            contentValues.put(columna_asignacion[6],asignacion.getFechaAsignacion());
            cont=this.getWritableDatabase().insert(TABLA_ASIGNACIONES,null,contentValues);
            if(cont==1 || cont==0){
                regIngresado="Error al insertar registro, Registro duplicado, verificar insersion";
            }else{
                regIngresado+=cont;
            }
        }catch(SQLException e){
            return  "Fallo al insert registro";
        }
        return  regIngresado;
    }

    public int obtenerIdCodMotivoAsignacion(String motivo){
        String consulta="SELECT "+columna_cat_mot_asignacion[0]+" FROM "+TABLA_CATALOGOMOTASIG;
        int valor=0;
        try{
            this.abrir();
            Cursor cursor=this.getReadableDatabase().query(TABLA_CATALOGOMOTASIG,columna_cat_mot_asignacion,columna_cat_mot_asignacion[1]+"=?",null,null,null,null);
            if(cursor.moveToFirst()){
                valor=cursor.getInt(1);
            }
            this.close();
        }catch(SQLException e){
            e.printStackTrace(System.out);
            valor=-1;
        }
        return valor;
    }

    public List<Asignacion> MostrarAsignaciones(){
        //columna_asignacion={"NODOCUMENTO","CODMOTIVOASGINACION","CODIGOARTICULO","DOCENTE","CODARTICULO","DESCRIPCION","FECHAASIGNACION"};
        List<Asignacion> listaAsignaciones=new ArrayList<>();
        try{
            this.abrir();
            Cursor cursor=this.getReadableDatabase().query(TABLA_ASIGNACIONES,columna_asignacion,null,null,null,null,null);
            while(cursor.moveToFirst()){
                Asignacion asignacion=new Asignacion();
                asignacion.setCodigoArticulo(cursor.getString(2));
                asignacion.setCodMotivoAsignacion(cursor.getInt(1));
                asignacion.setDocente(cursor.getString(3));
                asignacion.setDescripcion(cursor.getString(5));
                asignacion.setFechaAsignacion(cursor.getString(6));
                listaAsignaciones.add(asignacion);
            }
            return listaAsignaciones;
        }catch(SQLException e){
            e.printStackTrace(System.out);
            return null;
        }
    }


}
