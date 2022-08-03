package red.lisgar.corresponsal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NOMBRE = "Corresponsal.db";

    //COLUMNAS DE LA TABLA CLIENTE
    public static final String TABLE_CLIENTE = "Tabla_cliente";
    public static final String COLUMN_CLIENTE_ID = "Id_cliente";
    public static final String COLUMN_CLIENTE_NOMBRE = "nombre_cliente";
    public static final String COLUMN_CLIENTE_CEDULA = "cedula_cliente";
    public static final String COLUMN_CLIENTE_SALDO = "saldo_cliente";
    public static final String COLUMN_CLIENTE_PIN = "pin_cliente";
    public static final String COLUMN_CLIENTE_CUENTA = "cuenta_cliente";
    public static final String COLUMN_CLIENTE_CVV = "cvv_cliente";
    public static final String COLUMN_CLIENTE_FECHA_EXP = "fecha_exp_cliente";

    //COLUMNAS DE LA TABLA TRANSACCIONES
    public static final String TABLE_TRANSACCIONES = "Tabla_transacciones";
    public static final String COLUMN_TRANSACCIONES_ID = "Id_transacciones";
    public static final String COLUMN_TRANSACCIONES_ID_CLIENTE = "id_cliente_transacciones";
    public static final String COLUMN_TRANSACCIONES_ID_CORRESPONSAL = "id_corresponsal_transacciones";
    public static final String COLUMN_TRANSACCIONES_TIPO = "tipo_transacciones";
    public static final String COLUMN_TRANSACCIONES_FECHA = "fecha_transacciones";
    public static final String COLUMN_TRANSACCIONES_MONTO = "monto_transacciones";
    public static final String COLUMN_TRANSACCIONES_ID_EMISOR = "id_emisor_transacciones";

    //COLUMNAS DE LA TABLA CORRESPONSAL
    public static final String TABLE_CORRESPONSAL = "Tabla_corresponsal";
    public static final String COLUMN_CORRESPONSAL_ID = "Id_corresponsal";
    public static final String COLUMN_CORRESPONSAL_NOMBRE = "nombre_corresponsal";
    public static final String COLUMN_CORRESPONSAL_NIT = "nit_corresponsal";
    public static final String COLUMN_CORRESPONSAL_CORREO = "correo_corresponsal";
    public static final String COLUMN_CORRESPONSAL_CONTRASENA = "contrasena_corresponsal";
    public static final String COLUMN_CORRESPONSAL_ESTADO = "estado_corresponsal";
    public static final String COLUMN_CORRESPONSAL_SALDO = "saldo_corresponsal";
    public static final String COLUMN_CORRESPONSAL_CUENTA = "cuenta_corresponsal";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //CREACION DE LA TABLA CLIENTE
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CLIENTE + " ("+
                COLUMN_CLIENTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CLIENTE_NOMBRE + " TEXT NOT NULL, " +
                COLUMN_CLIENTE_CEDULA + " TEXT NOT NULL, " +
                COLUMN_CLIENTE_SALDO + " TEXT NOT NULL, " +
                COLUMN_CLIENTE_PIN + " TEXT NOT NULL, " +
                COLUMN_CLIENTE_CUENTA + " TEXT NOT NULL, " +
                COLUMN_CLIENTE_CVV + " TEXT NOT NULL, " +
                COLUMN_CLIENTE_FECHA_EXP + " TEXT NOT NULL) ");

        //CREACION DE LA TABLA TRANSACCIONES
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_TRANSACCIONES + " ("+
                COLUMN_TRANSACCIONES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TRANSACCIONES_ID_CLIENTE + " TEXT NOT NULL, " +
                COLUMN_TRANSACCIONES_ID_CORRESPONSAL + " TEXT NOT NULL, " +
                COLUMN_TRANSACCIONES_TIPO + " TEXT NOT NULL, " +
                COLUMN_TRANSACCIONES_FECHA + " TEXT NOT NULL, " +
                COLUMN_TRANSACCIONES_MONTO + " TEXT NOT NULL, " +
                COLUMN_TRANSACCIONES_ID_EMISOR + " TEXT) ");

        //CREACION DE LA TABLA CORRESPONSAL
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CORRESPONSAL + " ("+
                COLUMN_CORRESPONSAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CORRESPONSAL_NOMBRE + " TEXT NOT NULL, " +
                COLUMN_CORRESPONSAL_NIT + " TEXT NOT NULL, " +
                COLUMN_CORRESPONSAL_CORREO + " TEXT NOT NULL, " +
                COLUMN_CORRESPONSAL_CONTRASENA + " TEXT NOT NULL, " +
                COLUMN_CORRESPONSAL_ESTADO + " TEXT NOT NULL, " +
                COLUMN_CORRESPONSAL_SALDO + " TEXT NOT NULL, " +
                COLUMN_CORRESPONSAL_CUENTA + " TEXT NOT NULL) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_CLIENTE);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_TRANSACCIONES);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_CORRESPONSAL);
        onCreate(sqLiteDatabase);
    }
}
