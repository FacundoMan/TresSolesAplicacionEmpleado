package com.example.empleadosaplicationtressoles.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.empleadosaplicationtressoles.Modelos.Daos.MensajeDTO;
import com.example.empleadosaplicationtressoles.Modelos.Producto;
import com.example.empleadosaplicationtressoles.R;
import com.example.empleadosaplicationtressoles.Service.ProductoService;
import com.example.empleadosaplicationtressoles.Utilidades.ConexionRetrofit;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class DetalleProductoFragment extends Fragment {


    public DetalleProductoFragment() {
        // Required empty public constructor
    }

    public static DetalleProductoFragment newInstance(Producto item) {
        DetalleProductoFragment fragment = new DetalleProductoFragment();
        Bundle args = new Bundle();
        args.putSerializable("producto",item);
        fragment.setArguments(args);
        return fragment;
    }
    View root;
    EditText idProducto,nombre,categoria,precio,oferta,urlImagen,descripcion;
    Button bttnModificarProducto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.fragment_detalle_producto, container, false);
        idProducto=root.findViewById(R.id.etIdProductoDetalle);
        nombre=root.findViewById(R.id.etNombreDetalleProducto);
        categoria=root.findViewById(R.id.etCategoriaDetalleProducto);
        precio=root.findViewById(R.id.etPrecioDetalleProducto);
        oferta=root.findViewById(R.id.etOfertaDetalleProducto);
        urlImagen=root.findViewById(R.id.etUrlImagenDetalleProducto);
        descripcion=root.findViewById(R.id.etDescripcionDetalleProducto);
        bttnModificarProducto=root.findViewById(R.id.bttnModificarProducto);

        Producto p= (Producto) getArguments().getSerializable("producto");
        idProducto.setText(p.getId().toString());
        nombre.setText(p.getNombre());
       categoria.setText(p.getCategorias().get(0).getNombre());
       precio.setText(""+p.getPrecio());
       oferta.setText(""+p.getOferta());
       urlImagen.setText(p.getUrlImagen());
       descripcion.setText(""+p.getDescripcion());


        bttnModificarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarProducto(crearModificacionesProducto());
            }
        });
        return root;
    }

    private void modificarProducto(Producto crearModificacionesProducto) {
        Retrofit retrofit= ConexionRetrofit.conexion(getContext(),false);
        ProductoService productoService=retrofit.create(ProductoService.class);
        Call<MensajeDTO> call=productoService.modificarProducto(crearModificacionesProducto,Long.parseLong(idProducto.getText().toString()));
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

    private Producto crearModificacionesProducto() {
        Producto p=new Producto();
        p.setPrecio(Double.parseDouble(precio.getText().toString()));
        p.setOferta(Integer.parseInt(oferta.getText().toString()));
        return p;
    }


}