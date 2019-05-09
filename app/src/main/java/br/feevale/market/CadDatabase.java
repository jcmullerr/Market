package br.feevale.market;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class CadDatabase {
    private Context ctx;
    public static final String DATABASE_NAME = "market.db";
    public static final Integer DATABASE_VERSION = 4;
    private SQLiteDatabase db;
    private CadDatabaseHelper dbHelper;

    public void Delete(long id,String tabela){
        String sql = "DELETE FROM "+tabela+
                " WHERE _ID = " + id;
        db.execSQL(sql);
    }

    public void UpdateClienteScript(Cliente obj){
        Cliente c = (Cliente) obj;
        String sql;
        sql = "update "+ClienteTable.TABLE_NAME+
                " set "+ClienteTable.COLUMN_NOME+" = '"+c.getNome()+"',"+
                ClienteTable.COLUMN_EMAIL+" = '"+c.getEmail()+"',"+
                ClienteTable.COLUMN_ENDERECO+" = '"+c.getEndereco()+"',"+
                ClienteTable.COLUMN_TIPO+" = '"+c.getTipo()+
                "' where "+ClienteTable._ID +" = "+String.valueOf(c.getId());
        db.execSQL(sql);
    }

    public void UpdateComidaScript(Object obj){
        Comida c = (Comida) obj;
        String sql;
        sql = "update "+ComidaTable.TABLE_NAME+
                "set  "+ComidaTable.COLUMN_NOME+" = '"+c.getNome()+"',"+
                ComidaTable.COLUMN_CALORIAS+" = "+String.valueOf(c.getCalorias())+","+
                ComidaTable.COLUMN_VALOR+" = "+String.valueOf(c.getValor())+","+
                ComidaTable.COLUMN_VALIDADE+" = '"+c.getValidade()+"',"+
                ComidaTable.COLUMN_TIPO+" = '"+c.getTipo()+"'"+
                " where "+ComidaTable._ID +" = "+String.valueOf(c.getId());
        db.execSQL(sql);
    }

    public void UpdateBebidaScript(Object obj){
        Bebida b = (Bebida) obj;
        String sql;
        sql =  sql = "update "+BebidaTable.TABLE_NAME+
                " set "+BebidaTable.COLUMN_NOME+" = '"+b.getNome()+"',"+
                BebidaTable.COLUMN_VOLUME+" = "+b.getVolume()+","+
                BebidaTable.COLUMN_VOLUME+" = "+b.getValor()+","+
                BebidaTable.COLUMN_ALCOLICA+" = '"+b.getAlcolica()+"'"+
                " where "+BebidaTable._ID +" = "+String.valueOf(b.getId());
        db.execSQL(sql);
    }
    public CadDatabase(Context ctx){
        this.ctx = ctx;
        dbHelper = new CadDatabaseHelper();
        db = dbHelper.getWritableDatabase();

    }

    public double getSoma(long id){
        double valor = 0;
        String sql = "SELECT SUM("+ CadDatabase.VendaTable.COLUMN_VALOR +")"+
                "FROM "+ CadDatabase.VendaTable.TABLE_NAME+" WHERE "+ CadDatabase.VendaTable.COLUMN_CLIENTE+
                " = "+id;
        Cursor cr = db.rawQuery(sql,null);
        if(cr != null){
            try{
                if(cr.moveToFirst()){
                    valor = cr.getDouble(0);
                }
            }finally {
                cr.close();
            }
        }
        return valor;
    }

    public static class ClienteTable implements BaseColumns {
        public static final String TABLE_NAME = "cliente";
        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_ENDERECO = "endereco";
        public static final String COLUMN_TIPO = "tipo";
        public static final String COLUMN_EMAIL = "email";

        public static String getSQL(){
            String sql = "CREATE TABLE "+TABLE_NAME+" ("+
                    _ID +               " INTEGER PRIMARY KEY,"+
                    COLUMN_NOME +       " TEXT, "+
                    COLUMN_EMAIL+       " TEXT, " +
                    COLUMN_ENDERECO+    " TEXT,"+
                    COLUMN_TIPO+        " TEXT"+
                    ")";
            return sql;

        }
    }

    public static class BebidaTable implements BaseColumns {
        public static final String TABLE_NAME = "bebida";
        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_VOLUME = "volume";
        public static final String COLUMN_ALCOLICA = "alcolica";
        public static final String COLUMN_VALOR = "valor";


        public static String getSQL(){
            String sql = "CREATE TABLE "+TABLE_NAME+" ("+
                    _ID +               " INTEGER PRIMARY KEY,"+
                    COLUMN_NOME +       " TEXT, "+
                    COLUMN_VOLUME+      " TEXT, " +
                    COLUMN_ALCOLICA+    " TEXT,"+
                    COLUMN_VALOR+       " FLOAT"+
                    ")";
            return sql;

        }
    }

    public static class ComidaTable implements BaseColumns {
        public static final String TABLE_NAME = "comida";
        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_CALORIAS = "calorias";
        public static final String COLUMN_TIPO = "tipo";
        public static final String COLUMN_VALIDADE = "validade";
        public static final String COLUMN_VALOR = "valor";

        public static String getSQL(){
            String sql = "CREATE TABLE "+TABLE_NAME+" ("+
                    _ID +               " INTEGER PRIMARY KEY, "+
                    COLUMN_NOME +       " TEXT, "+
                    COLUMN_VALIDADE+    " text, " +
                    COLUMN_CALORIAS+    " INTEGER, "+
                    COLUMN_TIPO+        " TEXT,"+
                    COLUMN_VALOR+       " FLOAT"+
                    ")";
            return sql;

        }
    }

    public static class VendaTable implements BaseColumns {
        public static final String TABLE_NAME = "venda";
        public static final String COLUMN_CLIENTE = "cliente";
        public static final String COLUMN_BEBIDA = "bebida";
        public static final String COLUMN_COMIDA = "comida";
        public static final String COLUMN_VALOR = "valor";


        public static String getSQL(){
            String sql = "CREATE TABLE "+TABLE_NAME+" ("+
                    _ID +               " INTEGER PRIMARY KEY,"+
                    COLUMN_CLIENTE +    " INTEGER, "+
                    COLUMN_BEBIDA+     " INTEGER, "+
                    COLUMN_COMIDA+     " INTEGER, "+
                    COLUMN_VALOR+       " FLOAT,"+
                    "FOREIGN KEY (" +   COLUMN_CLIENTE+")"+
                    "REFERENCES "+      ClienteTable.TABLE_NAME+"("+ClienteTable._ID+"),"+
                    "FOREIGN KEY (" +   COLUMN_BEBIDA+")"+
                    "REFERENCES "+      BebidaTable.TABLE_NAME+"("+ClienteTable._ID+"),"+
                    "FOREIGN KEY (" +   COLUMN_COMIDA+")"+
                    "REFERENCES "+      ComidaTable.TABLE_NAME+"("+ClienteTable._ID+")"+
                    ")";
            return sql;
        }
    }

    public Long addVenda(Venda c){
        ContentValues values = new ContentValues();
        if (c.getComida() == null) {
            values.put(VendaTable.COLUMN_BEBIDA, c.getBebida().getId());
        }else {
            values.put(VendaTable.COLUMN_COMIDA, c.getComida().getId());
        }
        values.put(VendaTable.COLUMN_CLIENTE, c.getCliente().getId());
        values.put(VendaTable.COLUMN_VALOR, c.getValor());


        return db.insert(VendaTable.TABLE_NAME, null, values);
    }

    public List<Venda> getVendas(long codigo) throws Exception{
        String args[] = {String.valueOf(codigo)};
        String cols[] = {VendaTable._ID, VendaTable.COLUMN_COMIDA,VendaTable.COLUMN_BEBIDA,VendaTable.COLUMN_CLIENTE, VendaTable.COLUMN_VALOR};
        Cursor cursor = db.query(VendaTable.TABLE_NAME, cols, VendaTable.COLUMN_CLIENTE +" = ?", args, null, null, VendaTable._ID);
        List<Venda> Vendas = new ArrayList<>();
        Venda ven;

        while(cursor.moveToNext()){
            ven = new Venda();
            ven.setId(cursor.getLong(cursor.getColumnIndex(VendaTable._ID)));
            ven.setBebida(getBebida(cursor.getLong(cursor.getColumnIndex(VendaTable.COLUMN_BEBIDA))));
            ven.setCliente(getCliente(cursor.getLong(cursor.getColumnIndex(VendaTable.COLUMN_CLIENTE))));
            ven.setComida(getComida(cursor.getLong(cursor.getColumnIndex(VendaTable.COLUMN_COMIDA))));
            ven.setValor(cursor.getLong(cursor.getColumnIndex(VendaTable.COLUMN_VALOR)));
            Vendas.add(ven);
        }
        return Vendas;
    }


    public Long addCliente(Cliente c){
        ContentValues values = new ContentValues();
        values.put(ClienteTable.COLUMN_NOME, c.getNome());
        values.put(ClienteTable.COLUMN_EMAIL, c.getEmail());
        values.put(ClienteTable.COLUMN_ENDERECO, c.getEndereco());
        values.put(ClienteTable.COLUMN_TIPO, c.getTipo());


        return db.insert(ClienteTable.TABLE_NAME, null, values);
    }

    public Cliente getCliente(Long id){
        String cols[] = {ClienteTable._ID, ClienteTable.COLUMN_NOME,ClienteTable.COLUMN_TIPO,ClienteTable.COLUMN_ENDERECO, ClienteTable.COLUMN_EMAIL};
        String args[] = {id.toString()};
        Cursor cursor = db.query(ClienteTable.TABLE_NAME, cols, ClienteTable._ID+"=?", args, null, null, ClienteTable._ID);

        if(cursor.getCount() != 0){
            return null;
        }

        cursor.moveToNext();
        Cliente cli = new Cliente();
        cli.setId(cursor.getLong(cursor.getColumnIndex(ClienteTable._ID)));
        cli.setNome(cursor.getString(cursor.getColumnIndex(ClienteTable.COLUMN_NOME)));
        cli.setEmail(cursor.getString(cursor.getColumnIndex(ClienteTable.COLUMN_EMAIL)));
        cli.setTipo(cursor.getString(cursor.getColumnIndex(ClienteTable.COLUMN_TIPO)));
        cli.setEndereco(cursor.getString(cursor.getColumnIndex(ClienteTable.COLUMN_ENDERECO)));

        return cli;
    }

    public List<Cliente> getClientes(){
        String cols[] = {ClienteTable.COLUMN_NOME,ClienteTable.COLUMN_ENDERECO,ClienteTable.COLUMN_EMAIL,ClienteTable.COLUMN_TIPO,ClienteTable._ID};
        Cursor cursor = db.query(ClienteTable.TABLE_NAME, cols, null, null, null, null, ClienteTable._ID);
        List<Cliente> Clientes = new ArrayList<>();
        Cliente cli;

        while(cursor.moveToNext()){
            cli = new Cliente();
            cli.setId(cursor.getLong(cursor.getColumnIndex(ClienteTable._ID)));
            cli.setNome(cursor.getString(cursor.getColumnIndex(ClienteTable.COLUMN_NOME)));
            cli.setEmail(cursor.getString(cursor.getColumnIndex(ClienteTable.COLUMN_EMAIL)));
            cli.setTipo(cursor.getString(cursor.getColumnIndex(ClienteTable.COLUMN_TIPO)));
            cli.setEndereco(cursor.getString(cursor.getColumnIndex(ClienteTable.COLUMN_ENDERECO)));
            Clientes.add(cli);
            System.out.println(cli.getId()+" "+cli.getNome());
        }

        return Clientes;
    }

    public Long addComida(Comida c){
        ContentValues values = new ContentValues();
        values.put(ComidaTable.COLUMN_NOME, c.getNome());
        values.put(ComidaTable.COLUMN_VALIDADE, c.getValidade());
        values.put(ComidaTable.COLUMN_CALORIAS, c.getCalorias());
        values.put(ComidaTable.COLUMN_TIPO, c.getTipo());
        values.put(ComidaTable.COLUMN_VALOR, c.getValor());

        System.out.println(values);
        return db.insert(ComidaTable.TABLE_NAME, null, values);

    }

    public Comida getComida(Long id) throws Exception{
        String cols[] = {ComidaTable._ID, ComidaTable.COLUMN_NOME,ComidaTable.COLUMN_TIPO,ComidaTable.COLUMN_CALORIAS, ComidaTable.COLUMN_VALOR,ComidaTable.COLUMN_VALOR};
        String args[] = {id.toString()};
        Cursor cursor = db.query(ComidaTable.TABLE_NAME, cols, ComidaTable._ID+"=?", args, null, null, ComidaTable._ID);

        if(cursor.getCount() != 0){
            return null;
        }

        cursor.moveToNext();
        Comida cli = new Comida();
        cli.setId(cursor.getLong(cursor.getColumnIndex(ComidaTable._ID)));
        cli.setNome(cursor.getString(cursor.getColumnIndex(ComidaTable.COLUMN_NOME)));
        cli.setCalorias(cursor.getInt(cursor.getColumnIndex(ComidaTable.COLUMN_CALORIAS)));
        cli.setTipo(cursor.getString(cursor.getColumnIndex(ComidaTable.COLUMN_TIPO)));
        cli.setValor(cursor.getDouble(cursor.getColumnIndex(ComidaTable.COLUMN_VALOR)));
        cli.setValidade(cursor.getString(cursor.getColumnIndex(ComidaTable.COLUMN_VALIDADE)));

        return cli;
    }

    public List<Comida> getComidas() throws Exception{
        String cols[] = {ComidaTable._ID, ComidaTable.COLUMN_NOME,ComidaTable.COLUMN_TIPO,ComidaTable.COLUMN_CALORIAS, ComidaTable.COLUMN_VALOR,ComidaTable.COLUMN_VALIDADE};
        Cursor cursor = db.query(ComidaTable.TABLE_NAME, cols, null, null, null, null, ClienteTable._ID);
        List<Comida> Comidas = new ArrayList<>();
        Comida cli;

        while(cursor.moveToNext()){
            cli = new Comida();
            cli.setId(cursor.getLong(cursor.getColumnIndex(ComidaTable._ID)));
            cli.setNome(cursor.getString(cursor.getColumnIndex(ComidaTable.COLUMN_NOME)));
            cli.setTipo(cursor.getString(cursor.getColumnIndex(ComidaTable.COLUMN_TIPO)));
            cli.setCalorias(cursor.getInt(cursor.getColumnIndex(ComidaTable.COLUMN_CALORIAS)));
            cli.setValor(cursor.getDouble(cursor.getColumnIndex(ComidaTable.COLUMN_VALOR)));
            cli.setValidade(cursor.getString(cursor.getColumnIndex(ComidaTable.COLUMN_VALIDADE)));
            Comidas.add(cli);
        }

        return Comidas;
    }

    public Long addBebida(Bebida b){
        ContentValues values = new ContentValues();
        values.put(BebidaTable.COLUMN_NOME, b.getNome());
        values.put(BebidaTable.COLUMN_ALCOLICA, b.getAlcolica());
        values.put(BebidaTable.COLUMN_VALOR, b.getValor());
        values.put(BebidaTable.COLUMN_VOLUME, b.getVolume());


        return db.insert(BebidaTable.TABLE_NAME, null, values);
    }

    public Bebida getBebida(Long id){
        String cols[] = {BebidaTable._ID, BebidaTable.COLUMN_NOME,BebidaTable.COLUMN_ALCOLICA,BebidaTable.COLUMN_VALOR, BebidaTable.COLUMN_VOLUME};
        String args[] = {id.toString()};
        Cursor cursor = db.query(BebidaTable.TABLE_NAME, cols, BebidaTable._ID+"=?", args, null, null, BebidaTable._ID);

        if(cursor.getCount() != 0){
            return null;
        }

        cursor.moveToNext();
        Bebida beb = new Bebida();
        beb.setId(cursor.getLong(cursor.getColumnIndex(BebidaTable._ID)));
        beb.setNome(cursor.getString(cursor.getColumnIndex(BebidaTable.COLUMN_NOME)));
        beb.setAlcolica(cursor.getString(cursor.getColumnIndex(BebidaTable.COLUMN_ALCOLICA)));
        beb.setValor(cursor.getDouble(cursor.getColumnIndex(BebidaTable.COLUMN_VALOR)));
        beb.setVolume(cursor.getInt(cursor.getColumnIndex(BebidaTable.COLUMN_VOLUME)));

        return beb;
    }

    public List<Bebida> getBebidas(){
        String cols[] = {BebidaTable._ID, BebidaTable.COLUMN_NOME,BebidaTable.COLUMN_ALCOLICA,BebidaTable.COLUMN_VALOR, BebidaTable.COLUMN_VOLUME};
        Cursor cursor = db.query(BebidaTable.TABLE_NAME, cols, null, null, null, null, BebidaTable._ID);
        List<Bebida> Bebidas = new ArrayList<>();
        Bebida beb;

        while(cursor.moveToNext()){
            beb = new Bebida();
            beb.setId(cursor.getLong(cursor.getColumnIndex(BebidaTable._ID)));
            beb.setNome(cursor.getString(cursor.getColumnIndex(BebidaTable.COLUMN_NOME)));
            beb.setAlcolica(cursor.getString(cursor.getColumnIndex(BebidaTable.COLUMN_ALCOLICA)));
            beb.setValor(cursor.getDouble(cursor.getColumnIndex(BebidaTable.COLUMN_VALOR)));
            beb.setVolume(cursor.getInt(cursor.getColumnIndex(BebidaTable.COLUMN_VOLUME)));
            Bebidas.add(beb);
        }

        return Bebidas;
    }
    private class CadDatabaseHelper extends SQLiteOpenHelper {
        CadDatabaseHelper(){
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(ClienteTable.getSQL());
            db.execSQL(BebidaTable.getSQL());
            db.execSQL(ComidaTable.getSQL());
            db.execSQL(VendaTable.getSQL());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + ClienteTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + BebidaTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + ComidaTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + VendaTable.TABLE_NAME);
            onCreate(db);
            System.out.println(ClienteTable.getSQL());
            System.out.println(BebidaTable.getSQL());
            System.out.println(ComidaTable.getSQL());
            System.out.println(VendaTable.getSQL());
        }

        @Override
        public void onConfigure(SQLiteDatabase db) {
            super.onConfigure(db);
            db.setForeignKeyConstraintsEnabled(true);
        }
    }
}
