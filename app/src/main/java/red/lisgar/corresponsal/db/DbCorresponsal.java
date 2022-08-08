package red.lisgar.corresponsal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;
import androidx.core.util.PatternsCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import red.lisgar.corresponsal.corresponsal.CorresponsalHome;
import red.lisgar.corresponsal.entidades.Corresponsal;

public class DbCorresponsal extends DbHelper{

    Context context;

    public DbCorresponsal(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public boolean validarCorreoCorresponsal(String correo_electronico, String contrasena) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorCorresponsal = db.rawQuery("SELECT * FROM " + TABLE_CORRESPONSAL + " WHERE " + COLUMN_CORRESPONSAL_CORREO + " =? AND " + COLUMN_CORRESPONSAL_CONTRASENA + " =?",new String[] {correo_electronico,contrasena});
        if (cursorCorresponsal.getCount()>0)
            return true;
        else
            return false;
    }

    public long insertarCorresponsal(Corresponsal corresponsal) {

        long id= 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();


            ContentValues values = new ContentValues();
            values.put(COLUMN_CORRESPONSAL_NOMBRE, corresponsal.getNombre_corresponsal());
            values.put(COLUMN_CORRESPONSAL_NIT, corresponsal.getNit_corresponsal());
            values.put(COLUMN_CORRESPONSAL_CORREO, corresponsal.getCorreo_corresponsal());
            values.put(COLUMN_CORRESPONSAL_CONTRASENA, corresponsal.getContrasena_corresponsal());
            values.put(COLUMN_CORRESPONSAL_SALDO, corresponsal.getSaldo_corresponsal());
            values.put(COLUMN_CORRESPONSAL_CUENTA, corresponsal.getCuenta_corresponsal());
            values.put(COLUMN_CORRESPONSAL_ESTADO, corresponsal.getEstado_corresponsal());


            id = db.insert(TABLE_CORRESPONSAL, null, values);
        } catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public String NunTarjetaRandom(String nit){
        String numero = String.valueOf(Math.round(3 + Math.random()*3));
        numero+= nit;
        int limite = 16-numero.length();
        for (int i = 0; i < limite; i++){
            numero += String.valueOf(Math.round(Math.random()* 9));
        }
        return numero;
    }

    public boolean validarCreado(String nit) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorCorresponsal = db.rawQuery("SELECT * FROM " + TABLE_CORRESPONSAL + " WHERE " + COLUMN_CORRESPONSAL_NIT + " =? ",new String[] {nit});
        if (cursorCorresponsal.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean validarEmail(String correo) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorCorresponsal = db.rawQuery("SELECT * FROM " + TABLE_CORRESPONSAL + " WHERE " + COLUMN_CORRESPONSAL_CORREO + " =? ",new String[] {correo});
        if (cursorCorresponsal.getCount()>0)
            return true;
        else
            return false;
    }
    public boolean validarNombre(String nombre) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorCorresponsal = db.rawQuery("SELECT * FROM " + TABLE_CORRESPONSAL + " WHERE " + COLUMN_CORRESPONSAL_NOMBRE + " =? ",new String[] {nombre});
        if (cursorCorresponsal.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean validarEmailFormato(String correo_electronico){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (PatternsCompat.EMAIL_ADDRESS.matcher(correo_electronico).matches()){
            return true;
        }else{
            return false;
        }
    }

    public boolean actualizarDatos(String nit, String nombre, String correo, String contrasena) {

        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_CORRESPONSAL + " SET " +
                    COLUMN_CORRESPONSAL_NOMBRE + " = '"+nombre+"', " +
                    COLUMN_CORRESPONSAL_CORREO + " = '"+correo+"', " +
                    COLUMN_CORRESPONSAL_CONTRASENA + " = '"+contrasena+"'" +
                    " WHERE " + COLUMN_CORRESPONSAL_NIT + " = '"+nit+"'");
            correcto = true;
        } catch (Exception ex){
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public String mostrarNit(String nit){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Corresponsal corresponsal = null;
        Cursor cursorCorresponsal;

        cursorCorresponsal = db.rawQuery("SELECT * FROM " + TABLE_CORRESPONSAL + " WHERE " + COLUMN_CORRESPONSAL_NIT + " = '" + nit+"'", null);

        if (cursorCorresponsal.moveToFirst()){
            corresponsal = new Corresponsal();
            corresponsal.setSaldo_corresponsal(cursorCorresponsal.getString(6));
        }
        cursorCorresponsal.close();
        return corresponsal.getSaldo_corresponsal();
    }
}
