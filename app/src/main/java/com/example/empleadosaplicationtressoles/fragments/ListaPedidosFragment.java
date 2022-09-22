package com.example.empleadosaplicationtressoles.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.empleadosaplicationtressoles.Adapters.AdapterRecyclerViewListaEmpelado;
import com.example.empleadosaplicationtressoles.Adapters.AdapterRecylcerViewPedidos;
import com.example.empleadosaplicationtressoles.Adapters.AdapterRecylcerViewPedidosPequenio;
import com.example.empleadosaplicationtressoles.DetallePedidoActivity;
import com.example.empleadosaplicationtressoles.Modelos.Daos.UsuarioDTO;
import com.example.empleadosaplicationtressoles.Modelos.Pedido;
import com.example.empleadosaplicationtressoles.R;
import com.example.empleadosaplicationtressoles.Service.PedidoService;
import com.example.empleadosaplicationtressoles.Service.UsuarioService;
import com.example.empleadosaplicationtressoles.Utilidades.ConexionRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ListaPedidosFragment extends Fragment {

   View root;
   Context context;
   AdapterRecylcerViewPedidosPequenio adapterRecylcerViewPedidosPequenio;
   List<Pedido> pedidos;
   Spinner spinnerEstados;
   EditText buscador;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root=inflater.inflate(R.layout.fragment_lista_pedidos, container, false);
        context=getContext();
        spinnerEstados=root.findViewById(R.id.spinnerEstadoGerente);
        cargarListaPedidos();
        cargarSpinner();
        String [] idSpinerGerente=getResources().getStringArray(R.array.idEstadosGerente);
        buscador=root.findViewById(R.id.etBuscadorGerente);
        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filtrar(s.toString(),Long.parseLong(idSpinerGerente[spinnerEstados.getSelectedItemPosition()]));
            }
        });
        spinnerEstados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filtrar(buscador.getText().toString(),Long.parseLong(idSpinerGerente[spinnerEstados.getSelectedItemPosition()]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return root;
    }

    private void cargarSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.estadosGerente, R.layout.spinner_estilo);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerEstados.setAdapter(adapter);
    }

    private void cargarListaPedidos() {
        Retrofit retrofit= ConexionRetrofit.conexion(context,false);
        PedidoService pedidoService=retrofit.create(PedidoService.class);
        //
        Call<List<Pedido>> call=pedidoService.getTodosLosPedidos();
        call.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                if(response.code()==200){
                    pedidos=response.body();
                    adapterRecylcerViewPedidosPequenio=new AdapterRecylcerViewPedidosPequenio(pedidos, context, new AdapterRecylcerViewPedidosPequenio.OnItemClickListener() {
                        @Override
                        public void onItemClick(Pedido item) {
                            moveToDetalles(item);
                        }
                    });
                    RecyclerView r1=root.findViewById(R.id.idRecyclerPedidosGerente);
                    r1.setHasFixedSize(true);
                    r1.setLayoutManager(new LinearLayoutManager(context));
                    r1.setAdapter(adapterRecylcerViewPedidosPequenio);
                }else{
                    pedidos= new ArrayList<>();
                }
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {

            }
        });
    }
    public void moveToDetalles(Pedido item){
        Intent intent = new Intent(getContext(), DetallePedidoActivity.class);
        intent.putExtra("pedido", item);
        startActivity(intent);

    }

    public void filtrar(String f, long spinnerEleccion){
        ArrayList<Pedido> filtrarLista=new ArrayList<>();
            if(pedidos!=null){
                for(Pedido p: pedidos){
                    if(spinnerEleccion==0){
                        if((p.getNombre()+" "+p.getApellido()).toLowerCase().contains(f.toLowerCase())){
                            filtrarLista.add(p);
                        }
                    }else{
                        if(p.getEstado()!=null){
                            if((p.getNombre()+" "+p.getApellido()).toLowerCase().contains(f.toLowerCase()) && p.getEstado().getId().equals(spinnerEleccion)){
                                filtrarLista.add(p);
                            }
                        }

                    }

                }
                adapterRecylcerViewPedidosPequenio.filtrar(filtrarLista);

            }

    }
}