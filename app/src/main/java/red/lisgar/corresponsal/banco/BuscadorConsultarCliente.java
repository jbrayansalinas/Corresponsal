package red.lisgar.corresponsal.banco;

import static android.graphics.Color.parseColor;

import static red.lisgar.corresponsal.R.color.bolita_roja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import red.lisgar.corresponsal.R;
import red.lisgar.corresponsal.all.CrearCuenta;
import red.lisgar.corresponsal.db.DbCliente;

public class BuscadorConsultarCliente extends AppCompatActivity {

    //Layout Uno
    ImageView atras_Uno;
    TextView tituloUno;
    EditText primerCampoUno;
    Button btnconfirmarUno;
    Button btncancelarUno;
    View lineaUno;
    DbCliente dbCliente;

    //Layout Mensaje
    TextView tituloMensaje;
    ImageView imgMensaje;
    Button btnsalirMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uno_txt);
        layoutPin();
        toolbarRojoUno();
        barraRoja();

        btnconfirmarUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarObligatoriedad()) {
                    if (validarCedulaCliente()) {
                        irConsultarCliente();
                    }else{Toast.makeText(BuscadorConsultarCliente.this, "No existe", Toast.LENGTH_LONG).show();}
                }else{Toast.makeText(BuscadorConsultarCliente.this, "Rellene todos los campos.", Toast.LENGTH_LONG).show();}
            }
        });

        btncancelarUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensajeSalir();
            }
        });
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
    private void layoutPin(){
        atras_Uno = findViewById(R.id.atras_Uno);
        lineaUno = findViewById(R.id.lineaUno);
        tituloUno = findViewById(R.id.tituloUno);
        primerCampoUno = findViewById(R.id.primerCampoUno);
        btncancelarUno = findViewById(R.id.btncancelarUno);
        btnconfirmarUno = findViewById(R.id.btnconfirmarUno);

        primerCampoUno.setHint("Número de cédula a consultar");
        tituloUno.setText("Consultar Cliente");

    }
    private void irConsultarCliente(){
        Intent intent = new Intent(BuscadorConsultarCliente.this, ConsultarCliente.class);
        intent.putExtra("cedula", primerCampoUno.getText().toString().trim());
        startActivity(intent);
    }
    private boolean validarObligatoriedad(){
        if (!TextUtils.isEmpty(primerCampoUno.getText().toString().trim())){
            return true;
        }else{
            return false;
        }
    }
    private boolean validarCedulaCliente(){
        dbCliente = new DbCliente(this);
        return dbCliente.validarCedulaCliente(primerCampoUno.getText().toString().trim());
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
                Intent intent = new Intent(BuscadorConsultarCliente.this, Banco.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
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