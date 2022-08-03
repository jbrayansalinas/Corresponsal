package red.lisgar.corresponsal.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

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
}
