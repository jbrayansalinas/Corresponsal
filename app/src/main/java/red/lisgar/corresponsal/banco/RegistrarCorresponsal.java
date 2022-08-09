package red.lisgar.corresponsal.banco;

import static android.graphics.Color.parseColor;
import static red.lisgar.corresponsal.R.color.bolita_roja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

import red.lisgar.corresponsal.R;
import red.lisgar.corresponsal.all.ActualizarCorresponsal;
import red.lisgar.corresponsal.all.MainActivity;
import red.lisgar.corresponsal.all.SharePreference;
import red.lisgar.corresponsal.corresponsal.CorresponsalHome;
import red.lisgar.corresponsal.db.DbCliente;
import red.lisgar.corresponsal.db.DbCorresponsal;
import red.lisgar.corresponsal.entidades.Cliente;
import red.lisgar.corresponsal.entidades.Corresponsal;

public class RegistrarCorresponsal extends AppCompatActivity {

    //Layout Cuatro
    TextView nombreCorresponsalCuatro;
    TextView tituloCuatro;
    TextInputEditText primerCampoCuatro;
    TextInputLayout primerCampoCuatro2;
    TextInputEditText segundoCampoCuatro;
    TextInputLayout segundoCampoCuatro2;
    TextInputEditText tercerCampoCuatro;
    TextInputLayout tercerCampoCuatro2;
    TextInputEditText cuartoCampoCuarto;
    TextInputLayout cuartoCampoCuarto2;
    Button btnconfirmarCuatro;
    Button btncancelarCuatro;
    ImageView atras_Cuatro;
    View lineaCuatro;

    //Layout datos_corresponsal
    TextView tituloDtCorresponsal;
    View lineaDtCorresponsal;
    TextView primerCampoDtCorresponsal;
    TextView segundoCampoDtCorresponsal;
    TextView tercerCampoDtCorresponsal;
    TextView cuartoCampoDtCorresponsal;
    Button btnconfirmarDtCorresponsal;
    Button btncancelarDtCorresponsal;
    Button btnHabilitarDtCorresponsal;

    //Layout Mensaje
    TextView tituloMensaje;
    ImageView imgMensaje;
    Button btnsalirMensaje;

    //Cosas de la clase
    Corresponsal corresponsal;
    DbCorresponsal dbCorresponsal;
    Cliente cliente;
    DbCliente dbCliente;
    String nombre;
    String nit;
    String correo;
    String contrasena;
    String saldo;
    String estado;
    String cuenta;
    SharePreference sharePreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuatro_txt);
        layoutRegistrarCorresponsal();
        toolbar();

        btnconfirmarCuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarObligatoriedad()) {
                    if (!validarNombre()) {
                        if (!validarNit()) {
                            if (!validarcedulaCliente()) {
                                if (validarEmailFormato()) {
                                    if (!validarEmail()) {
                                        setContentView(R.layout.datos_corresponsal);
                                        layoutDtCorresponsal();
                                        btnconfirmarDtCorresponsal.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                setContentView(R.layout.mensaje);
                                                if (enviarDatos()) {
                                                    sharePreference = new SharePreference(RegistrarCorresponsal.this);
                                                    sharePreference.setSharedPreferences(nit);
                                                    mensajeOk();
                                                } else {
                                                    Toast.makeText(RegistrarCorresponsal.this, "Error al registrarse", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                        btncancelarDtCorresponsal.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mensajeSalir();
                                            }
                                        });
                                    } else {
                                        Toast.makeText(RegistrarCorresponsal.this, "Correo no disponible", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(RegistrarCorresponsal.this, "Formato de correo incorrrecto", Toast.LENGTH_LONG).show();
                                }
                            }else {Toast.makeText(RegistrarCorresponsal.this, "Nit no disponible", Toast.LENGTH_LONG).show();}
                        } else {Toast.makeText(RegistrarCorresponsal.this, "Nit no disponible", Toast.LENGTH_LONG).show();}
                    }else {Toast.makeText(RegistrarCorresponsal.this, "Nombre no disponible", Toast.LENGTH_LONG).show();}
                } else{Toast.makeText(RegistrarCorresponsal.this, "Rellene todos los campos.", Toast.LENGTH_LONG).show();}
            }
        });
        escribirCorreo();
        btncancelarCuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensajeSalir();
            }
        });
    }

    private void toolbar(){

        findViewById(R.id.bolitas_azulesCuatro).setVisibility(View.INVISIBLE);
        nombreCorresponsalCuatro.setText("Mi Banco");
        nombreCorresponsalCuatro.setTextColor(getResources().getColor(bolita_roja));
        lineaCuatro.setBackgroundColor(parseColor("#ff5f58"));
        tituloCuatro.setText("Registrar Corresponsal");

        primerCampoCuatro.setBackground(getDrawable(R.drawable.txt_login_rojo));
        primerCampoCuatro2.setHintTextColor(ColorStateList.valueOf(parseColor("#ff5f58")));
        primerCampoCuatro2.setBoxStrokeColor(parseColor("#ff5f58"));

        segundoCampoCuatro.setBackground(getDrawable(R.drawable.txt_login_rojo));
        segundoCampoCuatro2.setHintTextColor(ColorStateList.valueOf(parseColor("#ff5f58")));
        segundoCampoCuatro2.setBoxStrokeColor(parseColor("#ff5f58"));

        tercerCampoCuatro.setBackground(getDrawable(R.drawable.txt_login_rojo));
        tercerCampoCuatro2.setHintTextColor(ColorStateList.valueOf(parseColor("#ff5f58")));
        tercerCampoCuatro2.setBoxStrokeColor(parseColor("#ff5f58"));

        cuartoCampoCuarto.setBackground(getDrawable(R.drawable.txt_login_rojo));
        cuartoCampoCuarto2.setHintTextColor(ColorStateList.valueOf(parseColor("#ff5f58")));
        cuartoCampoCuarto2.setBoxStrokeColor(parseColor("#ff5f58"));
        cuartoCampoCuarto2.setStartIconTintList(ColorStateList.valueOf(parseColor("#ff5f58")));
        cuartoCampoCuarto2.setEndIconTintList(ColorStateList.valueOf(parseColor("#ff5f58")));

        btnconfirmarCuatro.setBackground(getDrawable(R.drawable.btn_rojo));
        btncancelarCuatro.setBackground(getDrawable(R.drawable.btn_transparente_roja));
        btncancelarCuatro.setTextColor(parseColor("#ff5f58"));


        atras_Cuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrarCorresponsal.this, Banco.class);
                startActivity(intent);
            }
        });
        tercerCampoCuatro.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
    }
    private void layoutRegistrarCorresponsal(){
        nombreCorresponsalCuatro = findViewById(R.id.nombreCorresponsalCuatro);
        atras_Cuatro = findViewById(R.id.atras_Cuatro);
        lineaCuatro = findViewById(R.id.lineaCuatro);
        tituloCuatro = findViewById(R.id.tituloCuatro);
        primerCampoCuatro = findViewById(R.id.primerCampoCuatro);
        primerCampoCuatro2 = findViewById(R.id.primerCampoCuatro2);
        segundoCampoCuatro = findViewById(R.id.segundoCampoCuatro);
        segundoCampoCuatro2 = findViewById(R.id.segundoCampoCuatro2);
        tercerCampoCuatro = findViewById(R.id.tercerCampoCuatro);
        tercerCampoCuatro2 = findViewById(R.id.tercerCampoCuatro2);
        cuartoCampoCuarto = findViewById(R.id.cuartoCampoCuarto);
        cuartoCampoCuarto2 = findViewById(R.id.cuartoCampoCuarto2);
        btnconfirmarCuatro = findViewById(R.id.btnconfirmarCuatro);
        btncancelarCuatro = findViewById(R.id.btncancelarCuatro);

        primerCampoCuatro2.setHint("Nombre Corresponsal");
        segundoCampoCuatro2.setHint("NIT de Corresponsal");
        tercerCampoCuatro2.setHint("Correo Corresponsal");
        cuartoCampoCuarto2.setHint("Contraseña Corresponsal");
    }
    private void layoutDtCorresponsal(){
        recibeDatos();
        tituloDtCorresponsal = findViewById(R.id.tituloDtCorresponsal);
        lineaDtCorresponsal = findViewById(R.id.lineaDtCorresponsal);
        primerCampoDtCorresponsal = findViewById(R.id.primerCampoDtCorresponsal);
        segundoCampoDtCorresponsal = findViewById(R.id.segundoCampoDtCorresponsal);
        tercerCampoDtCorresponsal = findViewById(R.id.tercerCampoDtCorresponsal);
        cuartoCampoDtCorresponsal = findViewById(R.id.cuartoCampoDtCorresponsal);
        btnconfirmarDtCorresponsal = findViewById(R.id.btnconfirmarDtCorresponsal);
        btncancelarDtCorresponsal = findViewById(R.id.btncancelarDtCorresponsal);
        btnHabilitarDtCorresponsal = findViewById(R.id.btnHabilitarDtCorresponsal);

        tituloDtCorresponsal.setText("Confirmar datos Correponsal");
        primerCampoDtCorresponsal.setText(nombre);
        segundoCampoDtCorresponsal.setText(nit);
        tercerCampoDtCorresponsal.setText(saldo);
        cuartoCampoDtCorresponsal.setText(correo);
        btnHabilitarDtCorresponsal.setVisibility(View.INVISIBLE);
    }
    private void recibeDatos(){
        dbCorresponsal = new DbCorresponsal(this);
        nombre = primerCampoCuatro.getText().toString().trim();
        nit = segundoCampoCuatro.getText().toString().trim();
        correo = tercerCampoCuatro.getText().toString().trim();
        contrasena = cuartoCampoCuarto.getText().toString().trim();
        saldo = "1000000";
        estado = "HABILITADO";
        cuenta = dbCorresponsal.NunTarjetaRandom(nit);
    }
    private boolean validarObligatoriedad(){
        recibeDatos();
        if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(nit) && !TextUtils.isEmpty(correo) && !TextUtils.isEmpty(contrasena)){
            return true;
        }else{
            return false;
        }
    }
    private boolean enviarDatos(){
        recibeDatos();
        corresponsal = new Corresponsal();
        corresponsal.setNombre_corresponsal(nombre);
        corresponsal.setNit_corresponsal(nit);
        corresponsal.setCorreo_corresponsal(correo);
        corresponsal.setContrasena_corresponsal(contrasena);
        corresponsal.setSaldo_corresponsal(saldo);
        corresponsal.setCuenta_corresponsal(cuenta);
        corresponsal.setEstado_corresponsal(estado);
        long id = dbCorresponsal.insertarCorresponsal(corresponsal);
        if(id > 0) {
            return true;
        }else{
            return false;
        }
    }
    private boolean validarNit(){
        recibeDatos();
        dbCorresponsal = new DbCorresponsal(this);
        return dbCorresponsal.validarCreado(nit);
    }
    private boolean validarEmailFormato(){
        recibeDatos();
        dbCorresponsal = new DbCorresponsal(this);
        if (correo.length()>10){
            if (correo.substring(correo.length()-10).equals("@wposs.com")){
                return dbCorresponsal.validarEmailFormato(correo);
            }else return false;
        }else return false;
    }
    private boolean validarEmail(){
        recibeDatos();
        dbCorresponsal = new DbCorresponsal(this);
        return dbCorresponsal.validarEmail(correo);
    }
    private boolean validarNombre(){
        recibeDatos();
        dbCorresponsal = new DbCorresponsal(this);
        return dbCorresponsal.validarNombre(nombre);
    }
    private boolean validarcedulaCliente(){
        recibeDatos();
        dbCliente = new DbCliente(this);
        return dbCliente.validarCedulaCliente(nit);
    }
    private void escribirCorreo(){
        primerCampoCuatro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                recibeDatos();
                tercerCampoCuatro.setText(nombre.toLowerCase() + "@wposs.com");
            }
        });
    }
    private void mensajeOk(){
        mensaje();
        tituloMensaje.setText("Se registró el Corresponsal");
        imgMensaje.setImageResource(R.drawable.bien);
        btnsalirMensaje.setBackgroundResource(R.drawable.btn_rojo);
        btnsalirMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
    }
    private void mensaje(){
        setContentView(R.layout.mensaje);
        findViewById(R.id.bolitas_azulesMensaje).setVisibility(View.INVISIBLE);
        tituloMensaje = findViewById(R.id.tituloMensaje);
        imgMensaje = findViewById(R.id.imgMensaje);
        btnsalirMensaje = findViewById(R.id.btnsalirMensaje);
    }
    private void mensajeSalir(){
        mensaje();
        tituloMensaje.setText("Registro del Corresponsal cancelado");
        imgMensaje.setImageResource(R.drawable.error);
        btnsalirMensaje.setBackgroundResource(R.drawable.btn_rojo);
        btnsalirMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
    }
    private void salir(){
        Intent intent = new Intent(RegistrarCorresponsal.this, Banco.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}