package red.lisgar.corresponsal.banco;

import static android.graphics.Color.parseColor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

import red.lisgar.corresponsal.R;
import red.lisgar.corresponsal.adaptadores.ListaHomeAdapter;
import red.lisgar.corresponsal.all.ActualizarCorresponsal;
import red.lisgar.corresponsal.all.CrearCuenta;
import red.lisgar.corresponsal.all.MainActivity;
import red.lisgar.corresponsal.entidades.Home;

public class Banco extends AppCompatActivity {

    TextView nombreCorresponsal;
    ImageView menuCorresponsal;
    RecyclerView recyclerCorresponsal;
    ListaHomeAdapter lista;
    PopupMenu popupMenu;

    //Layout mensajeAlert
    TextView textoAlert;
    Button btncancelarAlert;
    Button btnconfirmarAlert;

    ArrayList<Home> listaOp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corresponsal);
        layoutBanco();
        toolbar();
        opciones();

        menuCorresponsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu = new PopupMenu(Banco.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.items_menu_banco, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.actualizarCorresponsal:
                                actualizarCorresponsal();
                                break;
                            case R.id.actualizarClientes:
                                actualizarClientes();
                                break;
                            case R.id.cerrarSesion:
                                cerrarSesion();
                                break;
                            default:
                                return Banco.super.onOptionsItemSelected(menuItem);
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void layoutBanco(){
        nombreCorresponsal = findViewById(R.id.nombreCorresponsal);
        menuCorresponsal = findViewById(R.id.menuCorresponsal);
        recyclerCorresponsal = findViewById(R.id.recyclerCorresponsal);
    }
    private void toolbar(){

        findViewById(R.id.bolitas_azulesCorresponsal).setVisibility(View.INVISIBLE);
        nombreCorresponsal.setText("Mi Banco");
        nombreCorresponsal.setTextColor(getResources().getColor(R.color.bolita_roja));
    }
    // Opciones Array
    public void opciones(){

       listaOp = new ArrayList<>();

        listaOp.add(new Home(R.drawable.crear_cliente, "Crear Cliente", CrearCuenta.class, "crearCuenta", ""));
        listaOp.add(new Home(R.drawable.registrar_corresponsal, "Registrar Corresponsal", RegistrarCorresponsal.class, "", ""));
        listaOp.add(new Home(R.drawable.consultar_cliente, "Consultar Clinete", BuscadorConsultarCliente.class, "", ""));
        listaOp.add(new Home(R.drawable.consultar_corresponsal, "Consultar Corresponsal", ConsultarCorresponsal.class, "", ""));
        listaOp.add(new Home(R.drawable.listado_clientes, "Listado Clientes", ListadoClientes.class, "", ""));
        listaOp.add(new Home(R.drawable.listado_corresponsales, "Listados Corresponsales", ListadoCorresponsal.class, "", ""));

        lista = new ListaHomeAdapter(listaOp, Banco.this, "rojo", "visible");
        recyclerCorresponsal.setAdapter(lista);
        recyclerCorresponsal.setLayoutManager(new GridLayoutManager(Banco.this, 2));

    }
    private void layoutMensajeAlert(){
        textoAlert = findViewById(R.id.textoAlert);
        btncancelarAlert = findViewById(R.id.btncancelarAlert);
        btnconfirmarAlert = findViewById(R.id.btnconfirmarAlert);
    }
    private void toolbaarMensajeAlert(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            menuCorresponsal.setEnabled(false);
            recyclerCorresponsal.setClickable(false);
            recyclerCorresponsal.setEnabled(false);
            /*ArrayList<Home> listaOp = new ArrayList<>();
            listaOp.add(new Home(R.drawable.crear_cliente, "Crear Cliente", Banco.class, "crearCuenta", ""));
            listaOp.add(new Home(R.drawable.registrar_corresponsal, "Registrar Corresponsal", Banco.class, "", ""));
            listaOp.add(new Home(R.drawable.consultar_cliente, "Consultar Clinete", Banco.class, "", ""));
            listaOp.add(new Home(R.drawable.consultar_corresponsal, "Consultar Corresponsal", Banco.class, "", ""));
            listaOp.add(new Home(R.drawable.listado_clientes, "Listado Clientes", Banco.class, "", ""));
            listaOp.add(new Home(R.drawable.listado_corresponsales, "Listados Corresponsales", Banco.class, "", ""));*/
            lista = new ListaHomeAdapter(listaOp, Banco.this, "rojo", "invisible");
            recyclerCorresponsal.setAdapter(lista);
            recyclerCorresponsal.setLayoutManager(new GridLayoutManager(Banco.this, 2));
        }
        btnconfirmarAlert.setBackground(getDrawable(R.drawable.btn_rojo_bordes));
        btncancelarAlert.setBackground(getDrawable(R.drawable.btn_transparente_bordes_rojo));
        btncancelarAlert.setTextColor(parseColor("#ff5f58"));

        textoAlert.setText("¿Desea salir?");
    }
    private void actualizarCorresponsal(){
        Intent intent = new Intent(this, ActualizarCorresponsal.class);
        intent.putExtra("vista", "banco");
        startActivity(intent);
    }
    private void actualizarClientes(){
        Intent intent2 = new Intent(this, ActualizarCliente.class);
        startActivity(intent2);
    }
    private void cerrarSesion(){
        findViewById(R.id.alertaPrincipal).setVisibility(View.VISIBLE);
        layoutMensajeAlert();
        toolbaarMensajeAlert();
        btnconfirmarAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Banco.this, MainActivity.class);
                startActivity(intent3);
            }
        });
        btncancelarAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuCorresponsal.setEnabled(true);
                Intent intent2 = new Intent(Banco.this, Banco.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(intent2, 0);
                overridePendingTransition(0,0);
            }
        });
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}