package red.lisgar.corresponsal.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import red.lisgar.corresponsal.R;
import red.lisgar.corresponsal.all.CrearCuenta;
import red.lisgar.corresponsal.entidades.Home;

public class ListaHomeAdapter extends RecyclerView.Adapter<ListaHomeAdapter.HomeViewHOlder> {

    ArrayList<Home> home;
    Context context;
    String fondo;

    public ListaHomeAdapter(ArrayList<Home> home, Context context, String fondo) {
        this.home = home;
        this.context = context;
        this.fondo = fondo;
    }

    @NonNull
    @Override
    public ListaHomeAdapter.HomeViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new HomeViewHOlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaHomeAdapter.HomeViewHOlder holder, int position) {
        final Home item = home.get(position);
        Glide.with(context)
                .load(item.getImgHome())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgItemHome);
        holder.tituloItemHome.setText(item.getTituloHome());
        holder.clase = item.getClase();
        holder.direccion = item.getDireccion();
        holder.correo = item.getCorreo();
        if (fondo.equals("azul")){
            holder.fondo_itemHome.setCardBackgroundColor(Color.rgb(119, 183, 247));
        }else{
            holder.fondo_itemHome.setCardBackgroundColor(Color.rgb(225, 138, 133));
        }
    }

    @Override
    public int getItemCount() {
        return home.size();
    }

    public class HomeViewHOlder extends RecyclerView.ViewHolder {

        ImageView imgItemHome;
        TextView tituloItemHome;
        Object clase;
        CardView fondo_itemHome;
        String direccion;
        String correo;

        public HomeViewHOlder(@NonNull View itemView) {
            super(itemView);
            imgItemHome = itemView.findViewById(R.id.imgItemHome);
            tituloItemHome = itemView.findViewById(R.id.tituloItemHome);
            fondo_itemHome = itemView.findViewById(R.id.fondo_itemHome);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent;
                    switch (direccion) {
                        case "crearCuenta":
                            Intent intent2 = new Intent(context, CrearCuenta.class);
                            intent2.putExtra("vista", "banco");
                            context.startActivity(intent2);
                        break;
                        case "":
                            intent = new Intent(context, (Class<?>) clase);
                            intent.putExtra("ID", home.get(getAdapterPosition()).getId());
                            context.startActivity(intent);
                        break;
                        case "correo":
                            intent = new Intent(context, (Class<?>) clase);
                            intent.putExtra("correo", correo);
                            context.startActivity(intent);
                            break;
                    }
                }
            });
        }
    }
}
