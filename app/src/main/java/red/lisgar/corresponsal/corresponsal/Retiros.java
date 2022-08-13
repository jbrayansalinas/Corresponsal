package red.lisgar.corresponsal.corresponsal;

import static android.graphics.Color.parseColor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import red.lisgar.corresponsal.R;
import red.lisgar.corresponsal.all.CrearCuenta;
import red.lisgar.corresponsal.db.DbCliente;
import red.lisgar.corresponsal.db.DbCorresponsal;
import red.lisgar.corresponsal.entidades.Cliente;
import red.lisgar.corresponsal.entidades.Corresponsal;
import red.lisgar.corresponsal.entidades.Transacciones;

public class Retiros extends AppCompatActivity {

    //Layout Dos
    TextView tituloDos;
    TextView nombreCorresponsalDos;
    TextView saldoCorresponsalDos;
    TextView cuentaCorresponsalDos;
    ImageView atras_Dos;
    TextInputEditText primerCampoDos;
    TextInputLayout primerCampoDos2;
    TextInputEditText segundoCampoDos;
    TextInputLayout segundoCampoDos2;
    Button btnconfirmarDos;
    Button btncancelarDos;

    //Cosas de la clase
    DbCorresponsal dbCorresponsal;
    Corresponsal corresponsal;
    Transacciones transacciones;
    DbCliente dbCliente;
    Cliente cliente;
    String correoCorresponsal;
    String cedula;
    String monto;
    int monto2;

    //Layout mensajeAlert
    TextView textoAlert;
    Button btncancelarAlert;
    Button btnconfirmarAlert;

    //Layout Cofirmacion retiro
    TextView nombreConfirmacionRetiro;
    TextView cuentaConfirmacionRetiro;
    TextView pagoRetiroConfirmacion;
    Button btnconfirmarRetiroConfirmacion;
    Button btncancelarRetiroConfirmacion;

    //Layout Mensaje
    TextView tituloMensaje;
    ImageView imgMensaje;
    Button btnsalirMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dos_txt);
        Bundle extras = getIntent().getExtras();
        correoCorresponsal = extras.getString("correo");
        notificacioninvisible();
        layoutDos();
        toolbarDos();
        btnconfirmarDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarObligatoriedad()){
                    if (validarCedulaCliente()) {
                        if (validarSaldoSuficiente()){
                        findViewById(R.id.alertaDos).setVisibility(View.VISIBLE);
                        layoutMensajeAlert();
                        toolbaarMensajeAlert();
                        btnconfirmarAlert.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setContentView(R.layout.confirmacion_retiro);
                                layoutConfirRetiro();
                                mostrarDatos();
                                btnconfirmarRetiroConfirmacion.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        realizaRetiro();
                                        guardarTransaccion();
                                        mensajeOk();
                                    }
                                });
                                btncancelarRetiroConfirmacion.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mensajeSalir();
                                    }
                                });
                            }
                        });
                        btncancelarAlert.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mensajeSalir();
                            }
                        });
                        }else{Toast.makeText(Retiros.this, "Saldo insuficiente", Toast.LENGTH_LONG).show();}
                    }else{Toast.makeText(Retiros.this, "No existe", Toast.LENGTH_LONG).show();}
                }else{Toast.makeText(Retiros.this, "Rellene todos los campos.", Toast.LENGTH_LONG).show();}
            }
        });
        btncancelarDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensajeSalir();
            }
        });
    }

    private void layoutDos(){
        tituloDos = findViewById(R.id.tituloDos);
        nombreCorresponsalDos = findViewById(R.id.nombreCorresponsalDos);
        saldoCorresponsalDos = findViewById(R.id.saldoCorresponsalDos);
        cuentaCorresponsalDos = findViewById(R.id.cuentaCorresponsalDos);
        primerCampoDos = findViewById(R.id.primerCampoDos);
        primerCampoDos2 = findViewById(R.id.primerCampoDos2);
        segundoCampoDos = findViewById(R.id.segundoCampoDos);
        segundoCampoDos2 = findViewById(R.id.segundoCampoDos2);
        atras_Dos = findViewById(R.id.atras_Dos);
        btnconfirmarDos = findViewById(R.id.btnconfirmarDos);
        btncancelarDos = findViewById(R.id.btncancelarDos);
    }
    private void layoutConfirRetiro(){
        nombreConfirmacionRetiro = findViewById(R.id.nombreConfirmacionRetiro);
        cuentaConfirmacionRetiro = findViewById(R.id.cuentaConfirmacionRetiro);
        pagoRetiroConfirmacion = findViewById(R.id.pagoRetiroConfirmacion);
        btnconfirmarRetiroConfirmacion = findViewById(R.id.btnconfirmarRetiroConfirmacion);
        btncancelarRetiroConfirmacion = findViewById(R.id.btncancelarRetiroConfirmacion);

    }
    private void layoutMensajeAlert(){
        textoAlert = findViewById(R.id.textoAlert);
        btncancelarAlert = findViewById(R.id.btncancelarAlert);
        btnconfirmarAlert = findViewById(R.id.btnconfirmarAlert);
    }
    private void toolbarDos() {
        dbCorresponsal = new DbCorresponsal(this);
        corresponsal = new Corresponsal();
        corresponsal = dbCorresponsal.mostrarDatosCorresponsalHome(correoCorresponsal);
        nombreCorresponsalDos.setText(corresponsal.getNombre_corresponsal());
        saldoCorresponsalDos.setText(corresponsal.getSaldo_corresponsal());
        cuentaCorresponsalDos.setText(corresponsal.getCuenta_corresponsal());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            saldoCorresponsalDos.setCompoundDrawableTintList(ColorStateList.valueOf(parseColor("#3399FF")));
            cuentaCorresponsalDos.setCompoundDrawableTintList(ColorStateList.valueOf(parseColor("#3399FF")));
        }
        tituloDos.setText("Retiros");
        atras_Dos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
        primerCampoDos2.setHint("Número de Cédula");
        segundoCampoDos2.setHint("Monto a retirar");
    }
    private void toolbaarMensajeAlert(){
        layoutDos();
        primerCampoDos.setEnabled(false);
        segundoCampoDos.setEnabled(false);
        primerCampoDos2.setEnabled(false);
        segundoCampoDos2.setEnabled(false);
        btnconfirmarDos.setEnabled(false);
        btncancelarDos.setEnabled(false);
        atras_Dos.setEnabled(false);

        textoAlert.setText("Retiro tiene un costo de 2.000 pesos que se descontarán directamente de su cuenta WPOSS. ¿Desea continuar?");
    }
    private void recibeDatos(){
        cedula = primerCampoDos.getText().toString().trim();
        monto = segundoCampoDos.getText().toString().trim();
    }
    private void mostrarDatos(){
        recibeDatos();
        cliente = dbCliente.mostrarDatosCliente(cedula);

        nombreConfirmacionRetiro.setText(cliente.getNombre_cliente());
        cuentaConfirmacionRetiro.setText(cliente.getCuenta_cliente());
        pagoRetiroConfirmacion.setText(monto);
    }
    private boolean realizaRetiro(){
        recibeDatos();
        dbCliente = new DbCliente(this);
        monto2 = Integer.parseInt(monto);
        int monto3 = monto2 + 2000;
        if (dbCliente.sumarTransferenciaCorresponsal(correoCorresponsal, 2000)){
            return dbCliente.restarTransferenciaCliente(cedula, String.valueOf(monto3));
        }else return false;
    }
    private void guardarTransaccion(){
        recibeDatos();
        dbCliente = new DbCliente(this);
        transacciones = new Transacciones();
        transacciones.setId_cliente(Integer.parseInt(cedula));
        transacciones.setMonto_transaccion(monto);
        transacciones.setId_corresponsal(correoCorresponsal);
        transacciones.setFecha_transaccion(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        transacciones.setTipo_transaccion("RETIRO");

        dbCliente.insertarTransaccion(transacciones);

    }
    private boolean validarCedulaCliente(){
        recibeDatos();
        dbCliente = new DbCliente(this);
        return dbCliente.validarCedulaCliente(cedula);
    }
    private boolean validarSaldoSuficiente(){
        recibeDatos();
        dbCliente = new DbCliente(this);
        cliente = new Cliente();
        cliente = dbCliente.mostrarDatosCliente(cedula);
        if(Integer.parseInt(cliente.getSaldo_cliente()) >= Integer.parseInt(monto)){
            return true;
        }else return false;
    }
    private boolean validarObligatoriedad(){
        recibeDatos();
        if (!TextUtils.isEmpty(cedula) && !TextUtils.isEmpty(monto)){
            return true;
        }else{
            return false;
        }
    }
    private void notificacioninvisible(){
        findViewById(R.id.alertaDos).setVisibility(View.INVISIBLE);
    }
    private void salir(){
        Intent intent = new Intent(Retiros.this, CorresponsalHome.class);
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
        tituloMensaje.setText("Se canceló el retiro");
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
        tituloMensaje.setText("Se realizó el retiro");
        imgMensaje.setImageResource(R.drawable.bien);
        btnsalirMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}