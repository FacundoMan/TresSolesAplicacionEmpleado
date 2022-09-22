package com.example.empleadosaplicationtressoles.fragments;

import android.content.Context;
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
import android.widget.EditText;

import com.example.empleadosaplicationtressoles.Adapters.AdapterRecyclerViewListaEmpelado;
import com.example.empleadosaplicationtressoles.Adapters.AdapterRecylcerViewPedidos;
import com.example.empleadosaplicationtressoles.Modelos.Daos.UsuarioDTO;
import com.example.empleadosaplicationtressoles.Modelos.Pedido;
import com.example.empleadosaplicationtressoles.Modelos.Producto;
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


public class ListaEmpleadosFragment extends Fragment {

    View root;
    Context context;
    AdapterRecyclerViewListaEmpelado empleadosAdapter;
    List<UsuarioDTO> empleados;
    EditText buscarEmpleado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root=inflater.inflate(R.layout.fragment_lista_empleados, container, false);
        context=getContext();
        cargarListaEmpleados();
        buscarEmpleado=root.findViewById(R.id.etBuscarEmpleado);

        buscarEmpleado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    filtrarEmpleado(s.toString());
            }
        });
        return root;
    }

    private void cargarListaEmpleados() {
        Retrofit retrofit= ConexionRetrofit.conexion(context,false);
        UsuarioService usuarioService=retrofit.create(UsuarioService.class);
        //
        Call<List<UsuarioDTO>> call=usuarioService.getEmpleados();
        call.enqueue(new Callback<List<UsuarioDTO>>() {
            @Override
            public void onResponse(Call<List<UsuarioDTO>> call, Response<List<UsuarioDTO>> response) {
                if(response.code()==200){
                   empleados =response.body();
                    empleadosAdapter=new AdapterRecyclerViewListaEmpelado(empleados, context, new AdapterRecyclerViewListaEmpelado.OnItemClickListener() {
                        @Override
                        public void onItemClick(UsuarioDTO item) {
                            moveToDetalles(item);
                        }
                    });
                    RecyclerView r1=root.findViewById(R.id.idRecyclerViewListEmpleado);
                    r1.setHasFixedSize(true);
                    r1.setLayoutManager(new LinearLayoutManager(context));
                    r1.setAdapter(empleadosAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<UsuarioDTO>> call, Throwable t) {

            }
        });

    }
    public void moveToDetalles(UsuarioDTO item){
        DetalleUsuarioFragment detalleUsuarioFragment=new DetalleUsuarioFragment();
        //Creando el bundle para pasarle el producto con los datos
        Bundle bundle=new Bundle();
        bundle.putSerializable("usuario",item);
        detalleUsuarioFragment.setArguments(bundle);
        //Creando el fragment y las transaciones
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //Remplazar el fragment acutal con el de Detalles producto
        transaction.replace(R.id.idFragmentGerente, detalleUsuarioFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void filtrarEmpleado(String s){
        ArrayList<UsuarioDTO> filtrarLista=new ArrayList<>();
        if(empleados!=null){
            for(UsuarioDTO u: empleados){
                if((u.getNombre().toLowerCase()+" "+u.getApellido().toLowerCase()).contains(s.toLowerCase())){
                    filtrarLista.add(u);
                }
            }
            empleadosAdapter.filtrar(filtrarLista);
        }
    }
}