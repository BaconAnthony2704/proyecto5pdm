package com.example.grupo5_proyecto1.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.grupo5_proyecto1.models.Articulo;
import com.example.grupo5_proyecto1.models.Asignacion;
import com.example.grupo5_proyecto1.models.Autores;
import com.example.grupo5_proyecto1.models.CatalogoArticulo;
import com.example.grupo5_proyecto1.models.CatalogoMotivoAsignacion;
import com.example.grupo5_proyecto1.models.CatalogoLibros;
import com.example.grupo5_proyecto1.models.Libros;
import com.example.grupo5_proyecto1.models.Prestamos;

//Alfredo
import com.example.grupo5_proyecto1.models.CatalogoTipoLibro;
import com.example.grupo5_proyecto1.models.DetalleLibro;
//////////
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
            "   NODOCUMENTO          INTEGER primary key AUTOINCREMENT not null," +
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

    //Alfredo
    private String querycatalogoTipoLibro ="create table CATALOGOTIPOLIBRO" +
            "(" +
            "   CODIGOTIPOLIBRO                  INTEGER not null," +
                        "   DESCRIPCION          varchar(50)," +
            "   primary key (CODIGOTIPOLIBRO)" +
            ");";
    //////////
    //Relacion asignacion - catalogoMotivoAsignacion
    private String relacionCatalogoMotivoAsignacion="create table CATALOGOMOTIVOASIGNACION" +
            "(" +
            "   CODMOTIVOASGINACION  INTEGER primary key AUTOINCREMENT not null," +
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

    //ALfredo
    //Relacion DETALLELIBRO + catalogoTipoLibro con Libros
    private String relacionDetalleLibro="create table DETALLELIBRO" +
            "(" +
            "   CODIGOARTICULO      VARCAHR(10) not null," +
            "   ISBN                TEXT," +
            "   IDIOMA              TEXT," +
            "   CODTIPOLIBRO        INTEGER," +
            "   TITULO              TEXT," +

            "   primary key (CODIGOARTICULO)" +
            ");";

    private String crearTablaPrestamos = "create table PRESTAMOS" +
            "(" +
            "   NODOCUMENTO          numeric(8,0) not null," +
            "   CODIGOARTICULO       varchar(10)," +
            "   MATERIA              varchar(6)," +
            "   DOCENTE              varchar(30)," +
            "   ACTIVIDAD            varchar(15)," +
            "   DURACION             varchar(10)," +
            "   primary key (NODOCUMENTO)" +
            ");";

    private String crearTablaUnidadesAdmin = "create table UNIDADESADMIN" +
            "(" +
            "   IDUNADMIN            numeric(8,0) not null," +
            "   DESCRIPCION          varchar(20)," +
            "   UBICACION            varchar(20)," +
            "   primary key (IDUNADMIN)" +
            ");";

    private String crearTablaCatalogoEquipo = "create table CATALOGOEQUIPO" +
            "(" +
            "   CODTIPOEQUIPO        numeric(8,0) not null," +
            "   DESCRIPCION          varchar(10)," +
            "   primary key (CODTIPOEQUIPO)" +
            ");";


    private static String TABLA_ASIGNACIONES="ASIGNACIONES";
    private static String TABLA_AUTORES="AUTORES";
    private static String TABLA_CATALOGO_ARTICULO="CATALOGOARTICULO";
    //Alfredo
    private static String TABLA_CATALOGO_TIPO_LIBRO="CATALOGOTIPOLIBRO";
    //////////
    //Tabla relacion
    private static String TABLA_CATALOGOMOTASIG="CATALOGOMOTIVOASIGNACION";
    private static String TABLA_ARTICULO="ARTICULO";
    //Alfredo
    private static String TABLA_DETALLELIBRO="DETALLELIBRO";
    //////////
    //Columna relacion
    private static String[] columna_cat_mot_asignacion={"CODMOTIVOASGINACION","DESCRIPCION"};
    private static String[] columna_articulo={"CODIGOARTICULO","CODTIPOARTICULO","FECHAREGISTRO","ESTADO"};

    private static String[] columna_asignacion={"NODOCUMENTO","CODMOTIVOASGINACION","CODIGOARTICULO","DOCENTE","CODARTICULO","DESCRIPCION","FECHAASIGNACION"};
    private static String[] columna_autores={"CODIGOARTICULO","CORLN","NOOMBRE"};
    private static String[] columna_catalogo_articul={"CODTIPOARTICULO","DESCRIPCION"};

    //Alfredo
    private static String[] columna_detalle_libro={"CODIGOARTICULO", "ISBN","IDIOMA", "CODTIPOLIBRO", "TITULO"};
    private static String[] columna_catalogo_tipo_libro={"CODTIPOLIBRO","DESCRIPCION"};
    //////////
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(queryAsignacion);
        db.execSQL(queryAutores);
        db.execSQL(queryCatalogoArticulo);
        //relacion con asignacion
        db.execSQL(relacionCatalogoMotivoAsignacion);
        db.execSQL(relacionArticulo);

        //Alfredo


        db.execSQL(crearTablaPrestamos);
        db.execSQL(crearTablaUnidadesAdmin);
        db.execSQL(crearTablaCatalogoEquipo);

        db.execSQL(querycatalogoTipoLibro);
        db.execSQL(relacionDetalleLibro);
        //////////
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
    public String llenarBaseDatos() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String resultado;
        //Articulo
        final String[] VArticuloCodigo = {"AA01", "BB02", "CC03"};
        final String[] VArticuloCodigoTipo = {"ART1", "ART1", "ART2"};
        final String[] VArticuloFecha = {formatter.format(calendar.getTime()).toString(), formatter.format(calendar.getTime()).toString(), formatter.format(calendar.getTime()).toString()};
        final int[] VArticuloEstado = {1, 1, 1};

        //private static String[] columna_catalogo_articul={"CODTIPOARTICULO","DESCRIPCION"};
        //Catalogo Articulo
        final String[] VCatalogoArticuloCodigo = {"ART1", "ART2", "ART3"};
        final String[] VACatalogoArticuloDescripcion = {"Descripcion Articulo 1", "Descripcion Articulo 2", "Descripcion Articulo 3"};

        //private static String[] columna_cat_mot_asignacion={"CODMOTIVOASGINACION","DESCRIPCION"};
        //Categoria motivo asignacion
        final String[] VCatMoviAsignacion = {"Prestamo", "Alquiler", "Pedido"};
        //Autores
        //columna_autores={"CODIGOARTICULO","CORLN","NOOMBRE"};
        final String[] VAutoresCodigoArticulo = {"AA01", "AA01", "BB02", "BB02", "CC03"};
        final double[] VAutoresCorlin = {300, 250, 450, 230, 500};
        final String[] VAutoresNombre = {"Antonio Salarrue", "Manlio Argueta", "Alfredo Espino", "Garcia Lorca", "Julio Verne"};


        //Alfredo


        //DetalleLibro
        //private static String[] columna_detalle_libro={"CODIGOARTICULO", "ISBN","IDIOMA", "CODTIPOLIBRO", "TITULO"};
        final String [] VDetalleLibrocodigoArticulo ={"ZZ29","XX30","YY31"};
        final String [] VDetalleLibroISBN = {"9788425223280", "9788445432754", "9788494404900"};
        final String[] VDetalleLibroIdioma = {"Español", "Español", "Español",};
        final int [] VDetalleLibrocodTipoLibro ={1,2,1};
        final String[] VDetalleLibroTitulo = {"DESARROLLO SEGURO EN INGENIERÍA DEL SOFTWARE.", "TECNOLOGÍA Y ESTRUCTURA DE COMPUTADORES", "ACTIC 3", "LEAN UX", "MINERÍA DE DATOS"};

        //catalogo libros
        //private static String[] columna_catalogo_libros={"ISBN","TITULO","DESCRIPCION"};
        final int [] VCatalogoTipoLibrocodigoTipoLibro = {1,2,1};
        //final String[] VCatalogoLibrosTitulo = {"DESARROLLO SEGURO EN INGENIERÍA DEL SOFTWARE.", "TECNOLOGÍA Y ESTRUCTURA DE COMPUTADORES", "ACTIC 3", "LEAN UX", "MINERÍA DE DATOS"};
        final String[] VCatalogoTipoLibroDescripcion = {"Descripcion Libro 1", "Descripcion Libro 2", "Descripcion Libro 3", "Descripcion Libro 4", "Descripcion Libro 5"};
        //////////

        this.abrir();
        this.getWritableDatabase().execSQL("DELETE FROM " + TABLA_ARTICULO);
        this.getWritableDatabase().execSQL("DELETE FROM " + TABLA_CATALOGO_ARTICULO);
        this.getWritableDatabase().execSQL("DELETE FROM " + TABLA_CATALOGOMOTASIG);
        this.getWritableDatabase().execSQL("DELETE FROM " + TABLA_ASIGNACIONES);
        this.getWritableDatabase().execSQL("DELETE FROM " + TABLA_AUTORES);

        //Alfredo
        this.getWritableDatabase().execSQL("DELETE FROM " + TABLA_DETALLELIBRO);
        this.getWritableDatabase().execSQL("DELETE FROM " + TABLA_CATALOGO_TIPO_LIBRO);
        //////////
        Articulo articulo = new Articulo();
        for (int i = 0; i < 3; i++) {
            articulo.setCodigoArticulo(VArticuloCodigo[i]);
            articulo.setCodTipoArticulo(VArticuloCodigoTipo[i]);
            articulo.setFecha(VArticuloFecha[i]);
            articulo.setEstado(VArticuloEstado[i]);
            this.insertar(articulo);
        }
        CatalogoArticulo catalogoArticulo = new CatalogoArticulo();
        for (int i = 0; i < 3; i++) {
            catalogoArticulo.setCodTipoArticulo(VCatalogoArticuloCodigo[i]);
            catalogoArticulo.setDescripcion(VACatalogoArticuloDescripcion[i]);
            this.insertar(catalogoArticulo);
        }
        CatalogoMotivoAsignacion catalogoMotivoAsignacion = new CatalogoMotivoAsignacion();
        for (int i = 0; i < 3; i++) {
            catalogoMotivoAsignacion.setDescripcion(VCatMoviAsignacion[i]);
            this.insertar(catalogoMotivoAsignacion);
        }
        Autores autores = new Autores();
        for (int i = 0; i < 5; i++) {
            autores.setCodigoArticulo(VAutoresCodigoArticulo[i]);
            autores.setCorlin(VAutoresCorlin[i]);
            autores.setNombre(VAutoresNombre[i]);
            this.insertar(autores);
        }

        //Alfredo
        //private static String[] columna_detalle_libro={"CODIGOARTICULO", "ISBN","IDIOMA", "CODTIPOLIBRO", "TITULO"};
        DetalleLibro detalleLibro = new DetalleLibro();
        for (int i = 0; i < 3; i++) {
            detalleLibro.setCodigoArticulo(VDetalleLibrocodigoArticulo[i]);
            detalleLibro.setIsbn(VDetalleLibroISBN[i]);
            detalleLibro.setIdioma(VDetalleLibroIdioma[i]);
            detalleLibro.setCodTipoLibro(VDetalleLibrocodTipoLibro[i]);
            detalleLibro.setTitulo(VDetalleLibroTitulo[i]);

            this.insertar(detalleLibro);
        }
            CatalogoTipoLibro catalogolibros = new CatalogoTipoLibro();
            for (int i = 0; i < 3; i++) {
                catalogolibros.setCodTipoLibro(VCatalogoTipoLibrocodigoTipoLibro[i]);
                catalogolibros.setDescripcion(VCatalogoTipoLibroDescripcion[i]);
                this.insertar(catalogolibros);
            }
                this.cerrar();
                return resultado = "Guardado correctamente";
        //////////
        }



    public String insertar(Autores autores){
        String regIngresado="Registro ingresado No.";
        long cont=0;
        try{
            ContentValues contentValues=new ContentValues();
            contentValues.put(columna_autores[0],autores.getCodigoArticulo());
            contentValues.put(columna_autores[1],autores.getCorlin());
            contentValues.put(columna_autores[2],autores.getNombre());
            cont=this.getWritableDatabase().insert(TABLA_AUTORES,null,contentValues);
            if(cont==-1 || cont==0){
                regIngresado="Error al insertar registro, Registro duplicado, verificar insersion";
            }else{
                regIngresado+=cont;
            }
        }catch (SQLException e){
            return "Fallto al insertar registro";
        }
        return regIngresado;
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
            if(cont==-1 || cont==0){
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
            if(cont==-1 || cont==0){
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
            if(cont==-1 || cont==0){
                regIngresado="Error al insertar registro, Registro duplicado, verificar insersion";
            }else{
                regIngresado+=cont;
            }
        }catch(SQLException e){
            return  "Fallo al insert registro";
        }
        return  regIngresado;
    }

        //Alfredo
        //private static String[] columna_detalle_libro={"CODIGOARTICULO", "ISBN","IDIOMA", "CODTIPOLIBRO", "TITULO"};
            public String insertar(DetalleLibro detalleLibro){
                String regIngresado="Registro ingresado No.";
                long cont=0;
                try{
                    ContentValues contentValues=new ContentValues();
                    contentValues.put(columna_detalle_libro[0], detalleLibro.getCodigoArticulo());
                    contentValues.put(columna_detalle_libro[1], detalleLibro.getIsbn());
                    contentValues.put(columna_detalle_libro[2], detalleLibro.getIdioma());
                    contentValues.put(columna_detalle_libro[3], detalleLibro.getCodTipoLibro());
                    contentValues.put(columna_detalle_libro[4], detalleLibro.getTitulo());

                    cont=this.getWritableDatabase().insert(TABLA_DETALLELIBRO,null,contentValues);
                    if(cont==-1 || cont==0){
                        regIngresado="Error al insertar registro, Registro duplicado, verificar insersion";
                    }else{
                        regIngresado+=cont;
                    }
                }catch(SQLException e){
                    return  "Fallo al insert registro";
                }
                return  regIngresado;
            }

            public String insertar(CatalogoTipoLibro catalogoTipoLibro){
                String regIngresado="Registro ingresado No.";
                long cont=0;
                try{
                    ContentValues contentValues=new ContentValues();
                    contentValues.put(columna_catalogo_tipo_libro[0],catalogoTipoLibro.getCodTipoLibro());
                    contentValues.put(columna_catalogo_tipo_libro[1],catalogoTipoLibro.getDescripcion());

                    cont=this.getWritableDatabase().insert(TABLA_CATALOGO_TIPO_LIBRO,null,contentValues);
                    if(cont==-1 || cont==0){
                        regIngresado="Error al insertar registro, Registro duplicado, verificar insersion";
                    }else{
                        regIngresado+=cont;
                    }
                }catch(SQLException e){
                    return  "Fallo al insert registro";
                }
                return  regIngresado;
            }
            //////////

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

    /**
     * Metodo para obtener todos los articulos en base a un estado
     * @param estado
     * @return
     */
    public List<Articulo> obtenerArticuloEstado(int estado){
        //columna_articulo={"CODIGOARTICULO","CODTIPOARTICULO","FECHAREGISTRO","ESTADO"};
        this.abrir();
        List<Articulo>articulos=new ArrayList<>();
        Cursor cursor=this.getReadableDatabase().query(TABLA_ARTICULO,columna_articulo,"ESTADO = " + estado, null,null,null,null);
        while(cursor.moveToNext()){
            Articulo articulo=new Articulo();
            articulo.setCodigoArticulo(cursor.getString(0));
            articulo.setCodTipoArticulo(cursor.getString(1));
            articulo.setEstado(cursor.getInt(2));
            articulo.setFecha(cursor.getString(3));
            articulos.add(articulo);
            System.out.println(articulo.getCodigoArticulo());
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

    /**
     * Metodo para insertar un nuevo prestamo
     * @param prestamo
     * @return
     */
    public String insertar (Prestamos prestamo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("MATERIA", prestamo.getMateria());
        contentValues.put("DOCENTE", prestamo.getDocente());
        contentValues.put("ACTIVIDAD", prestamo.getActividad());
        contentValues.put("DURACION", prestamo.getDuracion());
        contentValues.put("CODIGOARTICULO", prestamo.getCodigoArticulo());

        try {
            long cont = this.getWritableDatabase().insert("PRESTAMOS",null,contentValues);
            if(cont==1 || cont==0){
                return "Error al insertar registro, Registro duplicado, verificar insersion";
            } else {
                return "Registro ingresado No." + cont;
            }
        } catch (SQLException e) {
            return "Ha ocurrido un error al insertar el registro";
        }
    }

    public String updateEstatusArticulo(String codarticulo, int estado) {


            String[] id = {codarticulo};
            ContentValues cv = new ContentValues();
            cv.put("ESTADO", estado);

            this.getWritableDatabase().update("ARTICULO", cv, "CODARTICULO = ?", id);
            return "Registro Actualizado Correctamente";

    }



    public int obtenerIdCodMotivoAsignacion(String motivo){
        String consulta="SELECT "+columna_cat_mot_asignacion[0]+" FROM "+TABLA_CATALOGOMOTASIG;
        String[] id={motivo};
        int valor;
        try{
            this.abrir();
            Cursor cursor=this.getReadableDatabase().query(TABLA_CATALOGOMOTASIG,columna_cat_mot_asignacion,columna_cat_mot_asignacion[1]+"=?",id,null,null,null);
            if(cursor.moveToFirst()){
                valor=cursor.getInt(0);
            }else{
                valor=0;
            }
            this.close();
        }catch(SQLException e){
            e.printStackTrace(System.out);
            valor=-1;
        }
        return valor;
    }
    public String obtenerMotivoAsignacion(int id){
        String[] idmotivo={String.valueOf(id)};
        String valor;
        try{
            this.abrir();
            Cursor cursor=this.getReadableDatabase().query(TABLA_CATALOGOMOTASIG,columna_cat_mot_asignacion,columna_cat_mot_asignacion[0]+"=?",idmotivo,null,null,null,null);
            if(cursor.moveToFirst()){
                valor=cursor.getString(1);
            }else{
                valor="No hay motivo";
            }

            this.close();
        }catch(SQLException e){
            e.printStackTrace(System.out);
            valor="No se puede obtener el motivo";

        }
        return valor;
    }

    public List<Asignacion> MostrarAsignaciones(){
        //columna_asignacion={"NODOCUMENTO","CODMOTIVOASGINACION","CODIGOARTICULO","DOCENTE","CODARTICULO","DESCRIPCION","FECHAASIGNACION"};
        List<Asignacion> listaAsignaciones=new ArrayList<>();
        try{
            this.abrir();
            Cursor cursor=this.getReadableDatabase().query(TABLA_ASIGNACIONES,columna_asignacion,null,null,null,null,null);
            while(cursor.moveToNext()){
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
    public Articulo obtenerArticulo(String idarticulo){
        //columna_articulo={"CODIGOARTICULO","CODTIPOARTICULO","FECHAREGISTRO","ESTADO"};
        String[]id={idarticulo};
        //Articulo articulo;
        try{
            this.abrir();
            Cursor cursor=this.getReadableDatabase().query(TABLA_ARTICULO,columna_articulo,columna_articulo[0]+"=?",id,null,null,null);
            if(cursor.moveToFirst()){
                Articulo articulo=new Articulo();
                articulo.setCodigoArticulo(cursor.getString(0));
                articulo.setCodTipoArticulo(cursor.getString(1));
                articulo.setFecha(cursor.getString(2));
                articulo.setEstado(cursor.getInt(3));
                //this.cerrar();
                return articulo;

            }else{
                this.cerrar();
                return null;
            }

        }catch (SQLException e){
            e.printStackTrace(System.out);
            this.cerrar();
            return null;

        }
    }

    public CatalogoArticulo obtenerCatalogo(String idCatalogoArticulo){
        //columna_catalogo_articul={"CODTIPOARTICULO","DESCRIPCION"};
        String[] id={idCatalogoArticulo};
        CatalogoArticulo catalogoArticulo;
        try{
            this.abrir();
            Cursor cursor=this.getReadableDatabase().query(TABLA_CATALOGO_ARTICULO,columna_catalogo_articul,columna_catalogo_articul[0]+"=?",id,null,null,null);
            if(cursor.moveToFirst()){
                catalogoArticulo=new CatalogoArticulo();
                catalogoArticulo.setCodTipoArticulo(cursor.getString(0));
                catalogoArticulo.setDescripcion(cursor.getString(1));
                this.cerrar();
                return catalogoArticulo;
            }else{
                this.cerrar();
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
            this.cerrar();
            return null;
        }
    }

    public List<Autores> obtenerAutores(){
        List<Autores> autores=new ArrayList<>();
        try{
            this.abrir();
            Cursor cursor=this.getReadableDatabase().query(TABLA_AUTORES,columna_autores,null,null,null,null,null);
            while(cursor.moveToNext()){
                Autores autores1=new Autores();
                autores1.setCodigoArticulo(cursor.getString(0));
                autores1.setCorlin(cursor.getDouble(1));
                autores1.setNombre(cursor.getString(2));
                autores.add(autores1);
            }
            return  autores;
        }catch(SQLException e){
            e.printStackTrace(System.out);
            return null;
        }
    }


}

