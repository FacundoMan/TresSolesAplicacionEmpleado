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
import com.example.empleadosaplicationtressoles.Adapters.AdapterRecyclerViewListaProductos;
import com.example.empleadosaplicationtressoles.Modelos.Daos.UsuarioDTO;
import com.example.empleadosaplicationtressoles.Modelos.Producto;
import com.example.empleadosaplicationtressoles.R;
import com.example.empleadosaplicationtressoles.Service.ProductoService;
import com.example.empleadosaplicationtressoles.Service.UsuarioService;
import com.example.empleadosaplicationtressoles.Utilidades.ConexionRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ListaProductosFragment extends Fragment {
    View root;
    List<Producto> productos;
    AdapterRecyclerViewListaProductos productosAdapter;
    EditText buscadorProducto;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root=inflater.inflate(R.layout.fragment_lista_productos, container, false);
        context=getContext();
        cargarListaProductos();
        buscadorProducto=root.findViewById(R.id.etBuscadorProducto);

        buscadorProducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filtrarProductos(s.toString());
            }
        });
        return root;
    }

    private void cargarListaProductos() {
        Retrofit retrofit= ConexionRetrofit.conexion(context,false);
        ProductoService productoService=retrofit.create(ProductoService.class);
        Call<List<Producto>> call=productoService.getProductos();
        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if(response.code()==200){
                    productos =response.body();
                    productosAdapter=new AdapterRecyclerViewListaProductos(productos, context, new AdapterRecyclerViewListaProductos.OnItemClickListener() {
                        @Override
                        public void onItemClick(Producto item) {
                            moveToDescription(item);
                        }
                    });
                    RecyclerView r1=root.findViewById(R.id.idRecylcerViewListaProducto);
                    r1.setHasFixedSize(true);
                    r1.setLayoutManager(new LinearLayoutManager(context));
                    r1.setAdapter(productosAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {

            }
        });
        //
    }
    public void moveToDescription(Producto item){
       DetalleProductoFragment detallesProductoFragment=new DetalleProductoFragment();
        //Creando el bundle para pasarle el producto con los datos
        Bundle bundle=new Bundle();
        bundle.putSerializable("producto",item);
        detallesProductoFragment.setArguments(bundle);
        //Creando el fragment y las transaciones
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //Remplazar el fragment acutal con el de Detalles producto
        transaction.replace(R.id.idFragmentGerente, detallesProductoFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void filtrarProductos(String s){
        ArrayList<Producto> filtrarLista=new ArrayList<>();
        if(productos!=null){
            for(Producto p: productos){
                if(p.getNombre().toLowerCase().contains(s.toLowerCase())){
                    filtrarLista.add(p);
                }
            }
            productosAdapter.filtrar(filtrarLista);
        }
        }

}