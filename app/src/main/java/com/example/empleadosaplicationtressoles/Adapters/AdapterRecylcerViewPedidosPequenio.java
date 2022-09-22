package com.example.empleadosaplicationtressoles.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.empleadosaplicationtressoles.Modelos.Pedido;
import com.example.empleadosaplicationtressoles.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterRecylcerViewPedidosPequenio extends RecyclerView.Adapter<AdapterRecylcerViewPedidosPequenio.ViewHolder> {
    private List<Pedido> pedidosDatos;
    private LayoutInflater mInflater;
    private Context context;
    final AdapterRecylcerViewPedidosPequenio.OnItemClickListener listener;
    //Se agrega para poder hacer click en el reclicler view
    public interface OnItemClickListener{
        void onItemClick(Pedido item);
    }

    public AdapterRecylcerViewPedidosPequenio(List<Pedido> itemList, Context context, AdapterRecylcerViewPedidosPequenio.OnItemClickListener listener){
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.pedidosDatos=itemList;
        this.listener=listener;
    }

    @Override
    public int getItemCount(){
        return pedidosDatos.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parents, int viewType){
        View view=LayoutInflater.from(parents.getContext()).inflate(R.layout.activity_elemento_pedidos_pequenio,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        holder.bindData(pedidosDatos.get(position));
    }

    public void setItems(List<Pedido> items){pedidosDatos=items;}

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombre,fecha,estado;


        ViewHolder(View itemView){
            super(itemView);
            nombre=itemView.findViewById(R.id.tvNombrePedidoElementoPequenio);
            fecha=itemView.findViewById(R.id.tvFechaPedidoElementoPequenio);
            estado=itemView.findViewById(R.id.tvEstadoPedidoElementoPequenio);
        }
        void bindData(Pedido item){
            nombre.setText(item.getNombre()+" "+item.getApellido());
            if(item.getFecha()!=null){
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date datofecha1 =item.getFecha();
                String datotexto1 = formatter.format(datofecha1);
                fecha.setText(""+datotexto1);
            }else{
                fecha.setText("Sin Fecha");
            }

            if(item.getEstado()==null){
                estado.setText("Sin estado");
            }else {
                String e = item.getEstado().getNombre();

                if (e.equals("Cancelado")) {
                    estado.setTextColor(Color.RED);
                } else if (e.equals("Entregado")) {
                    estado.setTextColor(Color.GREEN);
                } else {
                    estado.setTextColor(Color.parseColor("#ff7f50"));
                }
                estado.setText(e);
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }



    public void filtrar(ArrayList<Pedido> filtroProductos){
        this.pedidosDatos=filtroProductos;
        notifyDataSetChanged();
    }

}
