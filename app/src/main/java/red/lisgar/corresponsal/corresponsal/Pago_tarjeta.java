package red.lisgar.corresponsal.corresponsal;

import static android.graphics.Color.parseColor;

import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import red.lisgar.corresponsal.R;
import red.lisgar.corresponsal.all.CrearCuenta;
import red.lisgar.corresponsal.banco.ConsultarCliente;
import red.lisgar.corresponsal.db.DbCliente;
import red.lisgar.corresponsal.db.DbCorresponsal;
import red.lisgar.corresponsal.entidades.Cliente;
import red.lisgar.corresponsal.entidades.Corresponsal;
import red.lisgar.corresponsal.entidades.Transacciones;

public class Pago_tarjeta extends AppCompatActivity {

    //Layout Tarjeta
    Button btnconfirmarTarjeta;
    Button btncancelarTarjeta;
    EditText numeroTarjeta;
    EditText mmTarjeta;
    EditText aaaaTarjeta;
    EditText cvvTarjeta;
    EditText nombreClienteTarjeta;
    EditText valorTarjeta;
    Spinner spinnerTarjeta;
    ImageView atras_tresTarjeta;
    TextView nombreCorresponsalTarjeta;
    TextView saldoCorresponsalTarjeta;
    TextView cuentaCorresponsalTarjeta;

    //Layout Confirmacion tarjeta
    TextView nombreConfirmacionTarjeta;
    TextView pagoConfirmacionTarjeta;
    TextView numeroCuotasConfirmacion;
    TextView numeroTarjetaConfirmacion;
    TextView nombreTarjetaConfirmacion;
    Button btnconfirmarTarjetaConfirmacion;
    Button btncancelarTarjetaConfirmacion;

    //Layout Uno
    ImageView atras_Uno;
    TextView tituloUno;
    EditText primerCampoUno;
    Button btnconfirmarUno;
    Button btncancelarUno;
    View lineaUno;

    //Layout Mensaje
    TextView tituloMensaje;
    ImageView imgMensaje;
    Button btnsalirMensaje;

    //Cosas de la clase
    String correoCorresponsal;
    DbCorresponsal dbCorresponsal;
    Corresponsal corresponsal;
    Transacciones transacciones;
    DbCliente dbCliente;
    Cliente cliente;
    String numtarjeta;
    String cvv;
    String mm;
    String aaaa;
    String nomCliente;
    int cuotas;
    String monto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pago_tarjeta);
        Bundle extras = getIntent().getExtras();
        correoCorresponsal = extras.getString("correo");
        layoutTarjeta();
        toolbarTarjeta();
        spinner();

        btnconfirmarTarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarObligatoriedad()) {
                    if (validarTarjeta()) {
                        if (validarcvv()) {
                            if (validarfecha()) {
                                if (validarNombre()){
                                    if (validarSaldoTarjeta()){
                                            setContentView(R.layout.confirmacion_tarjeta);
                                            layoutConfirmacionTarjeta();
                                            btnconfirmarTarjetaConfirmacion.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                        setContentView(R.layout.uno_txt);
                                                        layoutPin();
                                                        btnconfirmarUno.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                if (validarPin()) {
                                                                    restarComision();
                                                                    sumarComision();
                                                                    guardarTransaccion();
                                                                    mensajeOk();
                                                                }else{Toast.makeText(Pago_tarjeta.this, "Pin incorrecto", Toast.LENGTH_LONG).show();}
                                                            }
                                                        });
                                                        btncancelarUno.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                mensajeSalir();
                                                            }
                                                        });
                                                }
                                            });
                                            btncancelarTarjetaConfirmacion.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mensajeSalir();
                                    }
                                });
                                    }else{Toast.makeText(Pago_tarjeta.this, "Saldo inválido", Toast.LENGTH_LONG).show();}
                                }else{Toast.makeText(Pago_tarjeta.this, "Nombre incorrecto", Toast.LENGTH_LONG).show();}
                            }else{Toast.makeText(Pago_tarjeta.this, "Fecha incorrecta", Toast.LENGTH_LONG).show();}
                        }else{Toast.makeText(Pago_tarjeta.this, "Cvv incorrecto", Toast.LENGTH_LONG).show();}
                    }else{Toast.makeText(Pago_tarjeta.this, "La tarjeta no existe", Toast.LENGTH_LONG).show();}
                }else{Toast.makeText(Pago_tarjeta.this, "Rellene todos los campos.", Toast.LENGTH_LONG).show();}
            }
        });
        btncancelarTarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mensajeSalir();
            }
        });
    }

    private void layoutTarjeta(){
        numeroTarjeta = findViewById(R.id.numeroTarjeta);
        cvvTarjeta = findViewById(R.id.cvvTarjeta);
        mmTarjeta = findViewById(R.id.mmTarjeta);
        aaaaTarjeta = findViewById(R.id.aaaaTarjeta);
        nombreClienteTarjeta = findViewById(R.id.nombreClienteTarjeta);
        valorTarjeta = findViewById(R.id.valorTarjeta);
        btncancelarTarjeta = findViewById(R.id.btncancelarTarjeta);
        btnconfirmarTarjeta = findViewById(R.id.btnconfirmarTarjeta);
        atras_tresTarjeta = findViewById(R.id.atras_tresTarjeta);
        nombreCorresponsalTarjeta = findViewById(R.id.nombreCorresponsalTarjeta);
        saldoCorresponsalTarjeta = findViewById(R.id.saldoCorresponsalTarjeta);
        cuentaCorresponsalTarjeta = findViewById(R.id.cuentaCorresponsalTarjeta);

        atras_tresTarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });

    }
    private void layoutConfirmacionTarjeta(){
        nombreConfirmacionTarjeta = findViewById(R.id.nombreConfirmacionTarjeta);
        pagoConfirmacionTarjeta = findViewById(R.id.pagoConfirmacionTarjeta);
        numeroCuotasConfirmacion = findViewById(R.id.numeroCuotasConfirmacion);
        nombreTarjetaConfirmacion = findViewById(R.id.nombreTarjetaConfirmacion);
        numeroTarjetaConfirmacion = findViewById(R.id.numeroTarjetaConfirmacion);
        btnconfirmarTarjetaConfirmacion = findViewById(R.id.btnconfirmarTarjetaConfirmacion);
        btncancelarTarjetaConfirmacion = findViewById(R.id.btncancelarTarjetaConfirmacion);

        recibeDatos();
        spinnerselected();
        cliente = dbCliente.mostrarDatosClienteCuenta(numtarjeta);
        nombreConfirmacionTarjeta.setText(cliente.getNombre_cliente());
        pagoConfirmacionTarjeta.setText(monto);
        numeroCuotasConfirmacion.setText(String.valueOf(cuotas));
        numeroTarjetaConfirmacion.setText(cliente.getCuenta_cliente());
        nombreTarjeta();
    }
    private void layoutPin(){
        atras_Uno = findViewById(R.id.atras_Uno);
        lineaUno = findViewById(R.id.lineaUno);
        tituloUno = findViewById(R.id.tituloUno);
        primerCampoUno = findViewById(R.id.primerCampoUno);
        btncancelarUno = findViewById(R.id.btncancelarUno);
        btnconfirmarUno = findViewById(R.id.btnconfirmarUno);

        primerCampoUno.setHint("PIN");
        tituloUno.setText("Confirmar PIN");
    }
    private void toolbarTarjeta(){
        dbCorresponsal = new DbCorresponsal(this);
        corresponsal = new Corresponsal();
        corresponsal = dbCorresponsal.mostrarDatosCorresponsalHome(correoCorresponsal);
        nombreCorresponsalTarjeta.setText(corresponsal.getNombre_corresponsal());
        saldoCorresponsalTarjeta.setText(corresponsal.getSaldo_corresponsal());
        cuentaCorresponsalTarjeta.setText(corresponsal.getCuenta_corresponsal());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            saldoCorresponsalTarjeta.setCompoundDrawableTintList(ColorStateList.valueOf(parseColor("#3399FF")));
            cuentaCorresponsalTarjeta.setCompoundDrawableTintList(ColorStateList.valueOf(parseColor("#3399FF")));
        }
    }
    private void recibeDatos(){
        numtarjeta = numeroTarjeta.getText().toString().trim();
        cvv = cvvTarjeta.getText().toString().trim();
        mm = mmTarjeta.getText().toString().trim();
        aaaa = aaaaTarjeta.getText().toString().trim();
        nomCliente = nombreClienteTarjeta.getText().toString().trim().toUpperCase();
        monto = valorTarjeta.getText().toString().trim();
        dbCliente = new DbCliente(this);
        cliente = new Cliente();
    }
    private boolean validarObligatoriedad(){
        recibeDatos();
        spinnerselected();
        if (!TextUtils.isEmpty(numtarjeta) && !TextUtils.isEmpty(cvv) && !TextUtils.isEmpty(mm) && !TextUtils.isEmpty(aaaa) && !TextUtils.isEmpty(nomCliente) && !TextUtils.isEmpty(monto) && !TextUtils.isEmpty(String.valueOf(cuotas))){
            return true;
        }else{
            return false;
        }
    }
    private void spinnerselected(){
    String seleccion = spinnerTarjeta.getSelectedItem().toString();
    switch(seleccion){
        case "1":
            cuotas = 1;
            break;
        case "2":
            cuotas = 2;
            break;
        case "3":
            cuotas = 3;
            break;
        case "4":
            cuotas = 4;
            break;
        case "5":
            cuotas = 5;
            break;
        case "6":
            cuotas = 6;
            break;
        case "7":
            cuotas = 7;
            break;
        case "8":
            cuotas = 8;
            break;
        case "9":
            cuotas = 9;
            break;
        case "10":
            cuotas = 10;
            break;
        case "11":
            cuotas = 11;
            break;
        case "12":
            cuotas = 12;
            break;
        default:
            cuotas = 1;
            break;
    }
}
    private void spinner(){
        spinnerTarjeta = findViewById(R.id.spinnerTarjeta);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.opciones, R.layout.frontend_item_spinner);
        adapter.setDropDownViewResource(R.layout.background_item_spinner);
        spinnerTarjeta.setAdapter(adapter);


    }
    private boolean validarPin(){
        recibeDatos();
        cliente = dbCliente.mostrarDatosClienteCuenta(numtarjeta);
        String pinCleinte = cliente.getPin_cliente();
        String pinLayout = primerCampoUno.getText().toString().trim();
        if (pinCleinte.equals(pinLayout)){
            return true;
        }else return false;
    }
    private boolean validarTarjeta(){
        recibeDatos();
        dbCliente = new DbCliente(this);
        return dbCliente.validarTarjeta(numtarjeta);
    }
    private boolean validarcvv(){
        recibeDatos();
        dbCliente = new DbCliente(this);
        return dbCliente.validarcvv(numtarjeta, cvv);
    }
    private boolean validarNombre(){
        recibeDatos();
        dbCliente = new DbCliente(this);
        return dbCliente.validarNombre(numtarjeta, nomCliente);
    }
    private boolean validarSaldoTarjeta(){
        recibeDatos();
        cliente = dbCliente.mostrarDatosClienteCuenta(numtarjeta);
        int saldoCliente = parseInt(cliente.getSaldo_cliente());
        if (saldoCliente>=parseInt(monto)){
            if (parseInt(monto) >= 10000 && 1000000 >= parseInt(monto)){
                    return true;
            }else return false;
        }else return false;
    }
    private void nombreTarjeta(){
        recibeDatos();
        String nombreTar = numtarjeta.substring(0,1);
        switch (nombreTar){
            case "3":
                nombreTarjetaConfirmacion.setText("AMERICAN EXPRESS");
                break;
            case "4":
                nombreTarjetaConfirmacion.setText("VISA");
                break;
            case "5":
                nombreTarjetaConfirmacion.setText("MASTERCARD");
                break;
            case "6":
                nombreTarjetaConfirmacion.setText("UNIONPAY");
                break;
            default:
                Toast.makeText(Pago_tarjeta.this, "número de tarjeta no valido", Toast.LENGTH_LONG).show();
        }
    }
    private boolean validarfecha(){
        recibeDatos();
        cliente = dbCliente.mostrarDatosClienteCuenta(numtarjeta);
        String fechaMes = cliente.getFecha_exp_cliente();
        String mes = fechaMes.substring(5,7);
        String year = fechaMes.substring(0,4);
        if (year.equals(aaaa)){
            if (mes.equals(mm)){
                return true;
            } else return false;
        } else return false;
    }
    private void restarComision(){
        recibeDatos();
        cliente = dbCliente.mostrarDatosClienteCuenta(numtarjeta);
        dbCliente = new DbCliente(this);
        dbCliente.restarTransferenciaCliente(cliente.getCedula_cliente(), monto);
    }
    private void sumarComision(){
        recibeDatos();
        dbCliente = new DbCliente(this);
        dbCliente.sumarTransferenciaCorresponsal(correoCorresponsal, Integer.parseInt(monto));
    }
    private void guardarTransaccion(){
        recibeDatos();
        dbCliente = new DbCliente(this);
        cliente = dbCliente.mostrarDatosClienteCuenta(numtarjeta);
        transacciones = new Transacciones();
        transacciones.setId_cliente(Integer.parseInt(cliente.getCedula_cliente()));
        transacciones.setMonto_transaccion(monto);
        transacciones.setId_corresponsal(correoCorresponsal);
        transacciones.setFecha_transaccion(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        transacciones.setTipo_transaccion("PAGO CON TARJETA");


        dbCliente.insertarTransaccion(transacciones);

    }
    private void salir(){
        Intent intent = new Intent(Pago_tarjeta.this, CorresponsalHome.class);
        intent.putExtra("cuenta", correoCorresponsal);
        startActivity(intent);
    }
    private void mensaje(){
        setContentView(R.layout.mensaje);
        tituloMensaje = findViewById(R.id.tituloMensaje);
        imgMensaje = findViewById(R.id.imgMensaje);
        btnsalirMensaje = findViewById(R.id.btnsalirMensaje);
    }
    private void mensajeSalir(){
        mensaje();
        tituloMensaje.setText("Se canceló el pago");
        imgMensaje.setImageResource(R.drawable.error);
        btnsalirMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
    }
    private void mensajeOk(){
        mensaje();
        tituloMensaje.setText("Se realizó el pago");
        imgMensaje.setImageResource(R.drawable.bien);
        btnsalirMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
    }
}