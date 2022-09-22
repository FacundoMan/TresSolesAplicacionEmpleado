package com.example.empleadosaplicationtressoles.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.empleadosaplicationtressoles.Modelos.Daos.UsuarioDTO;
import com.example.empleadosaplicationtressoles.Modelos.Producto;
import com.example.empleadosaplicationtressoles.Modelos.Rol;
import com.example.empleadosaplicationtressoles.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerViewListaProductos extends RecyclerView.Adapter<AdapterRecyclerViewListaProductos.ViewHolder> {
    private List<Producto> productosDatos;
    private LayoutInflater mInflater;
    private Context context;
    final AdapterRecyclerViewListaProductos.OnItemClickListener listener;
    //Se agrega para poder hacer click en el reclicler view
    public interface OnItemClickListener{
        void onItemClick(Producto item);
    }

    public AdapterRecyclerViewListaProductos(List<Producto> itemList, Context context, AdapterRecyclerViewListaProductos.OnItemClickListener listener){
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.productosDatos=itemList;
        this.listener=listener;
    }

    @Override
    public int getItemCount(){
        return productosDatos.size();
    }

    @Override
    public AdapterRecyclerViewListaProductos.ViewHolder onCreateViewHolder(ViewGroup parents, int viewType){
        View view=LayoutInflater.from(parents.getContext()).inflate(R.layout.activity_elemento_lista_productos_pequenio,null);
        return new AdapterRecyclerViewListaProductos.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecyclerViewListaProductos.ViewHolder holder, final int position){
        holder.bindData(productosDatos.get(position));
    }

    public void setItems(List<Producto> items){productosDatos=items;}

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombre,oferta,precio;


        ViewHolder(View itemView){
            super(itemView);
            nombre=itemView.findViewById(R.id.tvNombreProductoCard);
            oferta=itemView.findViewById(R.id.tvOfertaProductoCard);
            precio=itemView.findViewById(R.id.tvPrecioProductoCard);
        }
        void bindData(Producto item){
            nombre.setText(item.getNombre());
            oferta.setText(""+item.getOferta());
            precio.setText(""+item.getPrecio());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }



    public void filtrar(ArrayList<Producto> filtroProductos){
        this.productosDatos=filtroProductos;
        notifyDataSetChanged();
    }

}


