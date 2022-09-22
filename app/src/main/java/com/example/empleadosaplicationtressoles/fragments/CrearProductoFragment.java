package com.example.empleadosaplicationtressoles.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.empleadosaplicationtressoles.Modelos.Categoria;
import com.example.empleadosaplicationtressoles.Modelos.Daos.MensajeDTO;
import com.example.empleadosaplicationtressoles.Modelos.Producto;
import com.example.empleadosaplicationtressoles.R;
import com.example.empleadosaplicationtressoles.Service.ProductoService;
import com.example.empleadosaplicationtressoles.Service.UsuarioService;
import com.example.empleadosaplicationtressoles.Utilidades.ConexionRetrofit;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class CrearProductoFragment extends Fragment {

    View root;
    EditText nombreProducto,precioProducto,ofertaProducto,urlImagen,descripcionProducto;
    Spinner categoriaProducto;
    Button bttnCrearProducto;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root=inflater.inflate(R.layout.fragment_crear_producto, container, false);
        nombreProducto=root.findViewById(R.id.etNombreProducto);
        precioProducto=root.findViewById(R.id.etPrecioProducto);
        ofertaProducto=root.findViewById(R.id.etOfertaDelProducto);
        urlImagen=root.findViewById(R.id.etUrlImagen);
        descripcionProducto=root.findViewById(R.id.etDescripcionProducto);
        categoriaProducto=root.findViewById(R.id.spCategoriaProducto);
        bttnCrearProducto=root.findViewById(R.id.bttnCrearProducto);
        cargarSpinnerCategoria();
        bttnCrearProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(categoriaProducto.getSelectedItem().toString().equals("Elegir categoria")){
                    Toast toast = Toast.makeText(getContext(), "Eliga una categoria porfavor",Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    registrarProducto(crearProducto());
                }

            }
        });
        return root;
    }

    private void cargarSpinnerCategoria() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.categoriasProducto, R.layout.spinner_estilo);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        categoriaProducto.setAdapter(adapter);
    }

    private void registrarProducto(Producto crearProducto) {
        Retrofit retrofit= ConexionRetrofit.conexion(getContext(),false);
        ProductoService productoService=retrofit.create(ProductoService.class);

        Call<MensajeDTO> call=productoService.addProducto(crearProducto);
        call.enqueue(new Callback<MensajeDTO>() {
            @Override
            public void onResponse(Call<MensajeDTO> call, Response<MensajeDTO> response) {
                if(response.code()==200){
                    Toast toast = Toast.makeText(getContext(),response.body().getMensaje(),Toast.LENGTH_LONG);
                    toast.show();

                    ListaProductosFragment listaProductosFragment=new ListaProductosFragment();
                    FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction=fragmentManager.beginTransaction();
                    //Remplazar el fragment acutal con el de Detalles producto
                    transaction.replace(R.id.idFragmentGerente, listaProductosFragment);
                    transaction.commit();
                }else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast toast = Toast.makeText(getContext(), jObjError.getJSONObject("error").getString("message"),Toast.LENGTH_LONG);
                        toast.show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<MensajeDTO> call, Throwable t) {

            }
        });
    }

    private Producto crearProducto() {
       Producto p=new Producto();
       p.setNombre(nombreProducto.getText().toString());
       p.setPrecio(Double.parseDouble(precioProducto.getText().toString()));
       p.setOferta(Integer.parseInt(ofertaProducto.getText().toString()));
       p.setUrlImagen(urlImagen.getText().toString());
       p.setDescripcion(descripcionProducto.getText().toString());
       List<Categoria> c=new ArrayList<>();
       Categoria cat=new Categoria();
       cat.setNombre(categoriaProducto.getSelectedItem().toString());
       c.add(cat);
       p.setCategorias(c);
       return p;
    }
}