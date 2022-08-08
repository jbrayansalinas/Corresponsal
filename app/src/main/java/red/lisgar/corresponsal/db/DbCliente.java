package red.lisgar.corresponsal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

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
        Cursor cursorCorresponsal;

        cursorCorresponsal = db.rawQuery("SELECT * FROM " + TABLE_CLIENTE + " WHERE " + COLUMN_CLIENTE_CEDULA + " = '" + cedula+"'", null);

        if (cursorCorresponsal.moveToFirst()){
            cliente = new Cliente();
            cliente.setCuenta_cliente(cursorCorresponsal.getString(5));
            cliente.setCvv_cliente(cursorCorresponsal.getString(6));
            cliente.setFecha_exp_cliente(cursorCorresponsal.getString(7));
            cliente.setNombre_cliente(cursorCorresponsal.getString(1));
            cliente.setSaldo_cliente(cursorCorresponsal.getString(3));
        }
        cursorCorresponsal.close();
        return cliente;
    }
}
