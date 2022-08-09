package red.lisgar.corresponsal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import red.lisgar.corresponsal.entidades.Cliente;
import red.lisgar.corresponsal.entidades.Corresponsal;

public class DbCliente extends DbHelper{
    Context context;

    public DbCliente(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public boolean validarNombreCliente(String nombre) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorCliente = db.rawQuery("SELECT * FROM " + TABLE_CLIENTE + " WHERE " + COLUMN_CLIENTE_NOMBRE + " =? ",new String[] {nombre});
        if (cursorCliente.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean validarCedulaCliente(String cedula) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorCliente = db.rawQuery("SELECT * FROM " + TABLE_CLIENTE + " WHERE " + COLUMN_CLIENTE_CEDULA + " =? ",new String[] {cedula});
        if (cursorCliente.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean validarCedulaNit(String cedula) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorCliente = db.rawQuery("SELECT * FROM " + TABLE_CORRESPONSAL + " WHERE " + COLUMN_CORRESPONSAL_NIT + " =? ",new String[] {cedula});
        if (cursorCliente.getCount()>0)
            return true;
        else
            return false;
    }

    public long insertarCliente(Cliente cliente) {

        long id= 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();


            ContentValues values = new ContentValues();
            values.put(COLUMN_CLIENTE_NOMBRE, cliente.getNombre_cliente());
            values.put(COLUMN_CLIENTE_CEDULA, cliente.getCedula_cliente());
            values.put(COLUMN_CLIENTE_CUENTA, cliente.getCuenta_cliente());
            values.put(COLUMN_CLIENTE_CVV, cliente.getCvv_cliente());
            values.put(COLUMN_CLIENTE_FECHA_EXP, cliente.getFecha_exp_cliente());
            values.put(COLUMN_CLIENTE_PIN, cliente.getPin_cliente());
            values.put(COLUMN_CLIENTE_SALDO, cliente.getSaldo_cliente());


            id = db.insert(TABLE_CLIENTE, null, values);
        } catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public Cliente mostrarDatosCliente(String cedula){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cliente cliente = null;
        Cursor cursorCliente;

        cursorCliente = db.rawQuery("SELECT * FROM " + TABLE_CLIENTE + " WHERE " + COLUMN_CLIENTE_CEDULA + " = '" + cedula+"'", null);

        if (cursorCliente.moveToFirst()){
            cliente = new Cliente();
            cliente.setCuenta_cliente(cursorCliente.getString(5));
            cliente.setCvv_cliente(cursorCliente.getString(6));
            cliente.setFecha_exp_cliente(cursorCliente.getString(7));
            cliente.setNombre_cliente(cursorCliente.getString(1));
            cliente.setSaldo_cliente(cursorCliente.getString(3));
        }
        cursorCliente.close();
        return cliente;
    }

    public String mostrarSaldo(String cedula){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cliente cliente = null;
        Cursor cursorCliente;

        cursorCliente = db.rawQuery("SELECT * FROM " + TABLE_CLIENTE + " WHERE " + COLUMN_CLIENTE_CEDULA + " = '" + cedula+"'", null);

        if (cursorCliente.moveToFirst()){
            cliente = new Cliente();
            cliente.setSaldo_cliente(cursorCliente.getString(3));
        }
        cursorCliente.close();
        return cliente.getSaldo_cliente();
    }

    public boolean actualizarDatosCliente(String cedula, String nombre, String pin) {

        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_CLIENTE + " SET " +
                    COLUMN_CLIENTE_NOMBRE + " = '"+nombre+"', " +
                    COLUMN_CLIENTE_PIN + " = '"+pin+"' " +
                    " WHERE " + COLUMN_CLIENTE_CEDULA + " = '"+cedula+"'");
            correcto = true;
        } catch (Exception ex){
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public ArrayList<Cliente> listaCliente(){

        ArrayList<Cliente> lista = new ArrayList<>();
        Cursor cursorUsuarios;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cliente cliente ;

            cursorUsuarios = db.rawQuery("select * from "+TABLE_CLIENTE , null);

            if(cursorUsuarios.moveToFirst()){
                do {
                    cliente = new Cliente();
                    cliente.setId_cliente(cursorUsuarios.getInt(0));
                    cliente.setNombre_cliente(cursorUsuarios.getString(1));
                    cliente.setCuenta_cliente(cursorUsuarios.getString(5));
                    cliente.setCedula_cliente(cursorUsuarios.getString(2));
                    cliente.setSaldo_cliente(cursorUsuarios.getString(3));
                    lista.add(cliente);
                }while (cursorUsuarios.moveToNext());
            }else {
                return null;
            }
        } catch (Exception ex){
            ex.toString();
        }
        return lista;
    }
}
