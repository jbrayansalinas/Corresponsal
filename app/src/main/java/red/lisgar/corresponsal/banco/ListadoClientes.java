package red.lisgar.corresponsal.banco;

import static android.graphics.Color.parseColor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import red.lisgar.corresponsal.R;
import red.lisgar.corresponsal.adaptadores.ListaClientesAdapter;
import red.lisgar.corresponsal.db.DbCliente;

public class ListadoClientes extends AppCompatActivity {

    //layout Listado
    TextView titulolistado;
    View linealistado;
    EditText buscadorlistado;
    ImageView atras_listado;
    RecyclerView recyclerlistado;
    ListaClientesAdapter adapter;
    DbCliente dbCliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado);
        layoutListado();
        mostrarDatos();
    }

    private void layoutListado(){
        titulolistado = findViewById(R.id.titulolistado);
        linealistado = findViewById(R.id.linealistado);
        buscadorlistado = findViewById(R.id.buscadorlistado);
        atras_listado = findViewById(R.id.atras_listado);
        recyclerlistado = findViewById(R.id.recyclerlistado);

        titulolistado.setText("Listado Clientes");
        buscadorlistado.setHint("Cédula cliente");
        linealistado.setBackgroundColor(parseColor("#ff5f58"));
        buscadorlistado.setBackground(getDrawable(R.drawable.buscador_rojo));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            buscadorlistado.setCompoundDrawableTintList(ColorStateList.valueOf(parseColor("#ff5f58")));
        }
        findViewById(R.id.bolitas_azulesListado).setVisibility(View.INVISIBLE);
        atras_listado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListadoClientes.this, Banco.class);
                startActivity(intent);
            }
        });
    }
    private void mostrarDatos(){
        dbCliente = new DbCliente(this);
        if (dbCliente.listaCliente()!=null){
            recyclerlistado.setLayoutManager(new LinearLayoutManager(this));
            adapter = new ListaClientesAdapter(dbCliente.listaCliente());
            recyclerlistado.setAdapter(adapter);

            buscadorlistado.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    adapter.filtrado(s.toString());
                    recyclerlistado.setAdapter(adapter);
                }
            });
        }
    }
}