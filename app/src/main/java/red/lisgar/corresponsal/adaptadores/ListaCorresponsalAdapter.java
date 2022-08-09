package red.lisgar.corresponsal.adaptadores;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import red.lisgar.corresponsal.R;
import red.lisgar.corresponsal.entidades.Cliente;
import red.lisgar.corresponsal.entidades.Corresponsal;

public class ListaCorresponsalAdapter extends RecyclerView.Adapter<ListaCorresponsalAdapter.CorresponsalViewHolder> {

    ArrayList<Corresponsal> listaCorresponsal;
    ArrayList<Corresponsal> listaOriginal;

    public ListaCorresponsalAdapter(ArrayList<Corresponsal> listaCorresponsal) {
        this.listaCorresponsal = listaCorresponsal;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaCorresponsal);
    }

    @NonNull
    @Override
    public ListaCorresponsalAdapter.CorresponsalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consultas_rojo, null, false);
        return new CorresponsalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaCorresponsalAdapter.CorresponsalViewHolder holder, int position) {
        holder.campoUnoRojo.setText(listaCorresponsal.get(position).getNombre_corresponsal());
        holder.campoDosRojo.setText(listaCorresponsal.get(position).getCuenta_corresponsal());
        holder.campoTresRojo.setText(listaCorresponsal.get(position).getNit_corresponsal());
        holder.campoCuatroRojo.setText(listaCorresponsal.get(position).getEstado_corresponsal());
        holder.campoOtroRojo.setText(listaCorresponsal.get(position).getSaldo_corresponsal());
    }

    @Override
    public int getItemCount() {
        return listaCorresponsal.size();
    }

    public void filtrado(String txtBuscar){
        int longitud = txtBuscar.length();
        if(longitud == 0){
            listaCorresponsal.clear();
            listaCorresponsal.addAll(listaOriginal);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                List<Corresponsal> collection = listaCorresponsal.stream().filter(i -> i.getNit_corresponsal().toLowerCase().contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
                listaCorresponsal.clear();
                listaCorresponsal.addAll(collection);
            }else{
                for (Corresponsal l: listaOriginal){
                    if (l.getNit_corresponsal().toLowerCase().contains(txtBuscar.toLowerCase())){
                        listaCorresponsal.add(l);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public class CorresponsalViewHolder extends RecyclerView.ViewHolder {

        TextView campoUnoRojo;
        TextView campoDosRojo;
        TextView campoTresRojo;
        TextView campoCuatroRojo;
        TextView campoOtroRojo;

        public CorresponsalViewHolder(@NonNull View itemView) {
            super(itemView);
            campoUnoRojo  = itemView.findViewById(R.id.campoUnoRojo);
            campoDosRojo  = itemView.findViewById(R.id.campoDosRojo);
            campoTresRojo  = itemView.findViewById(R.id.campoTresRojo);
            campoCuatroRojo  = itemView.findViewById(R.id.campoCuatroRojo);
            campoOtroRojo  = itemView.findViewById(R.id.campoOtroRojo);

        }
    }
}
