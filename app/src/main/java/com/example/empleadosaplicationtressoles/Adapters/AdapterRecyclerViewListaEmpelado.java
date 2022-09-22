package com.example.empleadosaplicationtressoles.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.empleadosaplicationtressoles.Modelos.Daos.UsuarioDTO;

import com.example.empleadosaplicationtressoles.Modelos.Rol;
import com.example.empleadosaplicationtressoles.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterRecyclerViewListaEmpelado extends RecyclerView.Adapter<AdapterRecyclerViewListaEmpelado.ViewHolder> {
    private List<UsuarioDTO> usuarioDatos;
    private LayoutInflater mInflater;
    private Context context;
    final AdapterRecyclerViewListaEmpelado.OnItemClickListener listener;
    //Se agrega para poder hacer click en el reclicler view
    public interface OnItemClickListener{
        void onItemClick(UsuarioDTO item);
    }

    public AdapterRecyclerViewListaEmpelado(List<UsuarioDTO> itemList, Context context,AdapterRecyclerViewListaEmpelado.OnItemClickListener listener){
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.usuarioDatos=itemList;
        this.listener=listener;
    }

    @Override
    public int getItemCount(){
        return usuarioDatos.size();
    }

    @Override
    public AdapterRecyclerViewListaEmpelado.ViewHolder onCreateViewHolder(ViewGroup parents, int viewType){
        View view=LayoutInflater.from(parents.getContext()).inflate(R.layout.activity_elemento_empleados,null);
        return new AdapterRecyclerViewListaEmpelado.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecyclerViewListaEmpelado.ViewHolder holder, final int position){
        holder.bindData(usuarioDatos.get(position));
    }

    public void setItems(List<UsuarioDTO> items){usuarioDatos=items;}

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombre,usuario,rol;


        ViewHolder(View itemView){
            super(itemView);
            nombre=itemView.findViewById(R.id.tvNombreEmpleado);
            usuario=itemView.findViewById(R.id.tvUsuarioEmpleado);
            rol=itemView.findViewById(R.id.tvRolEmpleado);
        }
        void bindData(UsuarioDTO item){
            nombre.setText(item.getNombre()+" "+item.getApellido());
            usuario.setText(item.getNombreUsuario());
            Rol r=item.getRoles().get(0);
            rol.setText(r.getNombre());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }



    public void filtrar(ArrayList<UsuarioDTO> filtroProductos){
        this.usuarioDatos=filtroProductos;
        notifyDataSetChanged();
    }

}


