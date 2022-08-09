package red.lisgar.corresponsal.banco;

import static android.graphics.Color.parseColor;
import static red.lisgar.corresponsal.R.color.bolita_roja;
import static red.lisgar.corresponsal.R.drawable.btn_rojo;
import static red.lisgar.corresponsal.R.drawable.btn_transparente_bordes_muy_rojo;
import static red.lisgar.corresponsal.R.drawable.btn_transparente_bordes_verde;
import static red.lisgar.corresponsal.R.drawable.btn_transparente_roja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import red.lisgar.corresponsal.R;
import red.lisgar.corresponsal.db.DbCliente;
import red.lisgar.corresponsal.db.DbCorresponsal;
import red.lisgar.corresponsal.entidades.Cliente;
import red.lisgar.corresponsal.entidades.Corresponsal;

public class ConsultarCorresponsal extends AppCompatActivity {

    //Layout Consultar Cliente
    TextView primerCampoDtCorresponsal;
    TextView tituloDtCorresponsal;
    TextView segundoCampoDtCorresponsal;
    TextView tercerCampoDtCorresponsal;
    TextView cuartoCampoDtCorresponsal;
    Button btnconfirmarDtCorresponsal;
    Button btncancelarDtCorresponsal;
    Button btnHabilitarDtCorresponsal;
    ImageView atras_DtCorresponsal;
    String estado;

    DbCorresponsal dbCorresponsal;
    Corresponsal corresponsal;

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

    //Layout habilitar Corresponsal
    RelativeLayout relativeHbCorresponsal;
    TextView txtEstadoHbCorresponsal;
    Button btnsalirHbCorresponsal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uno_txt);
        barraRoja();
        layoutUno();
        toolbarRojoUno();
        btnconfirmarUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarObligatoriedad()){
                    if (validarNitCorresponsal()){
                        setContentView(R.layout.datos_corresponsal);
                        layoutConsultarCliente();
                    }else {Toast.makeText(ConsultarCorresponsal.this, "No existe", Toast.LENGTH_LONG).show();}
                } else {Toast.makeText(ConsultarCorresponsal.this, "Escriba su Nit", Toast.LENGTH_LONG).show();}
            }
        });
        btncancelarUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensajeSalir();
            }
        });
    }
    private void layoutHbCorresponsal(){
        relativeHbCorresponsal = findViewById(R.id.relativeHbCorresponsal);
        txtEstadoHbCorresponsal = findViewById(R.id.txtEstadoHbCorresponsal);
        btnsalirHbCorresponsal = findViewById(R.id.btnsalirHbCorresponsal);

    }
    private void layoutConsultarCliente(){
        primerCampoDtCorresponsal = findViewById(R.id.primerCampoDtCorresponsal);
        tituloDtCorresponsal = findViewById(R.id.tituloDtCorresponsal);
        segundoCampoDtCorresponsal = findViewById(R.id.segundoCampoDtCorresponsal);
        tercerCampoDtCorresponsal = findViewById(R.id.tercerCampoDtCorresponsal);
        cuartoCampoDtCorresponsal = findViewById(R.id.cuartoCampoDtCorresponsal);
        btnconfirmarDtCorresponsal = findViewById(R.id.btnconfirmarDtCorresponsal);
        btncancelarDtCorresponsal = findViewById(R.id.btncancelarDtCorresponsal);
        btnHabilitarDtCorresponsal = findViewById(R.id.btnHabilitarDtCorresponsal);
        atras_DtCorresponsal = findViewById(R.id.atras_DtCorresponsal);
        tituloDtCorresponsal.setText(R.string.consultarCorresponsal);

        atras_DtCorresponsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });

        dbCorresponsal = new DbCorresponsal(this);
        corresponsal = new Corresponsal();
        corresponsal = dbCorresponsal.mostrarDatosCorresponsal(primerCampoUno.getText().toString().trim());
        primerCampoDtCorresponsal.setText(corresponsal.getNombre_corresponsal());
        segundoCampoDtCorresponsal.setText(corresponsal.getNit_corresponsal());
        tercerCampoDtCorresponsal.setText(corresponsal.getSaldo_corresponsal());
        cuartoCampoDtCorresponsal.setText(corresponsal.getCorreo_corresponsal());
        btnconfirmarDtCorresponsal.setVisibility(View.INVISIBLE);
        btncancelarDtCorresponsal.setVisibility(View.INVISIBLE);

        estado = corresponsal.getEstado_corresponsal();
        if (estado.equals("HABILITADO")){
            btnHabilitarDtCorresponsal.setText("DEHSABILITAR");
            btnHabilitarDtCorresponsal.setBackground(getDrawable(btn_transparente_roja));
            btnHabilitarDtCorresponsal.setTextColor(Color.parseColor("#ff5f58"));
            btnHabilitarDtCorresponsal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbCorresponsal.estadoCorresponsal("DESHABILITADO", corresponsal.getNit_corresponsal());
                    setContentView(R.layout.habilitar_corresponsal);
                    layoutHbCorresponsal();
                    relativeHbCorresponsal.setBackground(getDrawable(btn_transparente_bordes_muy_rojo));
                    txtEstadoHbCorresponsal.setText("CORRESPONSAL DESHABILITADO");
                    txtEstadoHbCorresponsal.setTextColor(Color.parseColor("#E62E26"));
                    btnsalirHbCorresponsal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            salir();
                        }
                    });
                }
            });
        } else if(estado.equals("DESHABILITADO")){
            btnHabilitarDtCorresponsal.setText("HABILITAR");
            btnHabilitarDtCorresponsal.setBackground(getDrawable(btn_rojo));
            btnHabilitarDtCorresponsal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbCorresponsal.estadoCorresponsal("HABILITADO", corresponsal.getNit_corresponsal());
                    setContentView(R.layout.habilitar_corresponsal);
                    layoutHbCorresponsal();
                    relativeHbCorresponsal.setBackground(getDrawable(btn_transparente_bordes_verde));
                    txtEstadoHbCorresponsal.setText("CORRESPONSAL HABILITADO");
                    txtEstadoHbCorresponsal.setTextColor(Color.parseColor("#58C167"));
                    btnsalirHbCorresponsal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            salir();
                        }
                    });
                }
            });
        }
    }
    private void toolbarRojoUno(){

        findViewById(R.id.bolitas_azulesUno).setVisibility(View.INVISIBLE);

        lineaUno.setBackgroundColor(parseColor("#ff5f58"));
        primerCampoUno.setBackground(getDrawable(R.drawable.txt_login_rojo));
        atras_Uno.setVisibility(View.INVISIBLE);
        btnconfirmarUno.setBackground(getDrawable(R.drawable.btn_rojo));
        btncancelarUno.setBackground(getDrawable(R.drawable.btn_transparente_roja));
        btncancelarUno.setTextColor(parseColor("#ff5f58"));

    }
    private void salir(){
        Intent intent = new Intent(ConsultarCorresponsal.this, Banco.class);
        startActivity(intent);
    }
    private boolean validarObligatoriedad(){
        if (!TextUtils.isEmpty(primerCampoUno.getText().toString().trim())){
            return true;
        }else{
            return false;
        }
    }
    private boolean validarNitCorresponsal(){
        dbCorresponsal = new DbCorresponsal(this);
        return dbCorresponsal.validarCreado(primerCampoUno.getText().toString().trim());
    }
    private void mensajeSalir(){
        setContentView(R.layout.mensaje);
        findViewById(R.id.bolitas_azulesMensaje).setVisibility(View.INVISIBLE);
        tituloMensaje = findViewById(R.id.tituloMensaje);
        imgMensaje = findViewById(R.id.imgMensaje);
        btnsalirMensaje = findViewById(R.id.btnsalirMensaje);
        tituloMensaje.setText("Búsqueda cancelada");
        imgMensaje.setImageResource(R.drawable.error);
        btnsalirMensaje.setBackgroundResource(R.drawable.btn_rojo);
        btnsalirMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
    }
    private void layoutUno(){
        atras_Uno = findViewById(R.id.atras_Uno);
        lineaUno = findViewById(R.id.lineaUno);
        tituloUno = findViewById(R.id.tituloUno);
        primerCampoUno = findViewById(R.id.primerCampoUno);
        btncancelarUno = findViewById(R.id.btncancelarUno);
        btnconfirmarUno = findViewById(R.id.btnconfirmarUno);

        tituloUno.setText(R.string.consultarCorresponsal);
        primerCampoUno.setHint("Número de NIT a consultar");
    }
    private void barraRoja(){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(bolita_roja));
        }
    }
}