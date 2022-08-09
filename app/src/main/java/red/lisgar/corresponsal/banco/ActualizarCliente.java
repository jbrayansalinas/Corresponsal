package red.lisgar.corresponsal.banco;

import static android.graphics.Color.parseColor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import red.lisgar.corresponsal.R;
import red.lisgar.corresponsal.all.ActualizarCorresponsal;
import red.lisgar.corresponsal.db.DbCliente;
import red.lisgar.corresponsal.db.DbCorresponsal;
import red.lisgar.corresponsal.entidades.Cliente;

public class ActualizarCliente extends AppCompatActivity {

    //Layout Cuatro
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

    //Layout datos_Cliente
    TextView tituloDtCliente;
    View lineaDtCliente;
    TextView primerCampoDtCliente;
    TextView segundoCampoDtCliente;
    TextView tercerCampoDtCliente;
    Button btncancelarDtCliente;
    Button btnconfirmarDtCliente;
    RelativeLayout relativeDatosCliente;

    //Layout Mensaje
    TextView tituloMensaje;
    ImageView imgMensaje;
    Button btnsalirMensaje;

    //Cosas de la clase
    DbCliente dbCliente;
    Cliente cliente;
    String nombreCliente;
    String cedula;
    String pin;
    String pin2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuatro_txt);
        layoutActualizarCliente();
        toolbar();
        btnconfirmarCuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarObligatoriedad()){
                    if (validarCedula()){
                        if (confirmarPin()){
                            setContentView(R.layout.datos_cliente);
                            layoutDtCLiente();
                            toolbarRojoDtCLiente();
                            btnconfirmarDtCliente.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (actualizarDatosCliente()) {
                                        mensajeOk();
                                    } else {
                                        Toast.makeText(ActualizarCliente.this, "Error al Actualizar", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            btncancelarDtCliente.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mensajeSalir();
                                }
                            });
                        }else{Toast.makeText(ActualizarCliente.this, "El PIN debe ser el mismo", Toast.LENGTH_LONG).show();}
                    }else{Toast.makeText(ActualizarCliente.this, "No existe la cédula", Toast.LENGTH_LONG).show();}
                }else{Toast.makeText(ActualizarCliente.this, "Rellene todos los campos.", Toast.LENGTH_LONG).show();}
            }
        });
        btncancelarCuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensajeSalir();
            }
        });
    }

    private void layoutActualizarCliente(){
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

        primerCampoCuatro2.setHint("Nombre Cliente");
        segundoCampoCuatro2.setHint("Número de Cédula");
        tercerCampoCuatro2.setHint("PIN Nuevo");
        cuartoCampoCuarto2.setHint("Confirmar PIN nuevo");
    }
    private void toolbar(){

        findViewById(R.id.bolitas_azulesCuatro).setVisibility(View.INVISIBLE);
        lineaCuatro.setBackgroundColor(parseColor("#ff5f58"));
        tituloCuatro.setText("Actualizar Cliente");

        primerCampoCuatro.setBackground(getDrawable(R.drawable.txt_login_rojo));
        primerCampoCuatro2.setHintTextColor(ColorStateList.valueOf(parseColor("#ff5f58")));
        primerCampoCuatro2.setBoxStrokeColor(parseColor("#ff5f58"));

        segundoCampoCuatro.setBackground(getDrawable(R.drawable.txt_login_rojo));
        segundoCampoCuatro2.setHintTextColor(ColorStateList.valueOf(parseColor("#ff5f58")));
        segundoCampoCuatro2.setBoxStrokeColor(parseColor("#ff5f58"));

        tercerCampoCuatro.setBackground(getDrawable(R.drawable.txt_login_rojo));
        tercerCampoCuatro2.setHintTextColor(ColorStateList.valueOf(parseColor("#ff5f58")));
        tercerCampoCuatro2.setBoxStrokeColor(parseColor("#ff5f58"));
        tercerCampoCuatro2.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        tercerCampoCuatro2.setEndIconTintList(ColorStateList.valueOf(parseColor("#ff5f58")));

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
                Intent intent = new Intent(ActualizarCliente.this, Banco.class);
                startActivity(intent);
            }
        });
        tercerCampoCuatro.setInputType(InputType.TYPE_CLASS_NUMBER);
        cuartoCampoCuarto.setInputType(InputType.TYPE_CLASS_NUMBER);
    }
    private void recibeDatos(){
        dbCliente = new DbCliente(this);
        nombreCliente = primerCampoCuatro.getText().toString().trim();
        cedula = segundoCampoCuatro.getText().toString().trim();
        pin = tercerCampoCuatro.getText().toString().trim();
        pin2 = cuartoCampoCuarto.getText().toString().trim();
    }
    private boolean validarObligatoriedad(){
        recibeDatos();
        if (!TextUtils.isEmpty(nombreCliente) && !TextUtils.isEmpty(cedula) && !TextUtils.isEmpty(pin) && !TextUtils.isEmpty(pin2)){
            return true;
        }else{
            return false;
        }
    }
    private boolean validarCedula(){
        recibeDatos();
        dbCliente = new DbCliente(this);
        return dbCliente.validarCedulaCliente(segundoCampoCuatro.getText().toString().trim());
    }
    private boolean actualizarDatosCliente(){
        recibeDatos();
        return dbCliente.actualizarDatosCliente(cedula, nombreCliente, pin);
    }
    private boolean confirmarPin(){
        recibeDatos();
        if (pin.equals(pin2)){
            return true;
        } else return false;
    }
    private void mensajeOk(){
        mensaje();
        tituloMensaje.setText("Se Actualizó el Cliente");
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
        tituloMensaje.setText("Actualización del Cliente cancelado");
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
        Intent intent = new Intent(ActualizarCliente.this, Banco.class);
        startActivity(intent);
    }
    private void layoutDtCLiente(){
        recibeDatos();
        tituloDtCliente = findViewById(R.id.tituloDtCliente);
        lineaDtCliente = findViewById(R.id.lineaDtCliente);
        primerCampoDtCliente = findViewById(R.id.primerCampoDtCliente);
        segundoCampoDtCliente = findViewById(R.id.segundoCampoDtCliente);
        tercerCampoDtCliente = findViewById(R.id.tercerCampoDtCliente);
        btncancelarDtCliente = findViewById(R.id.btncancelarDtCliente);
        btnconfirmarDtCliente = findViewById(R.id.btnconfirmarDtCliente);
        relativeDatosCliente = findViewById(R.id.relativeDatosCliente);

        tituloDtCliente.setText("Confirmar datos Cliente");
        primerCampoDtCliente.setText(nombreCliente);
        segundoCampoDtCliente.setText(cedula);
        tercerCampoDtCliente.setText(dbCliente.mostrarSaldo(cedula));
    }
    private void toolbarRojoDtCLiente(){

        findViewById(R.id.bolitas_azulesDtCliente).setVisibility(View.INVISIBLE);

        lineaDtCliente.setBackgroundColor(parseColor("#ff5f58"));
        relativeDatosCliente.setBackground(getDrawable(R.drawable.txt_login_rojo));
        btnconfirmarDtCliente.setBackground(getDrawable(R.drawable.btn_rojo));
        btncancelarDtCliente.setBackground(getDrawable(R.drawable.btn_transparente_roja));
        btncancelarDtCliente.setTextColor(parseColor("#ff5f58"));
    }
}