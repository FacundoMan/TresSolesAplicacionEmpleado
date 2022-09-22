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

import com.example.empleadosaplicationtressoles.Modelos.Daos.MensajeDTO;
import com.example.empleadosaplicationtressoles.Modelos.Daos.UsuarioDTO;
import com.example.empleadosaplicationtressoles.Modelos.Rol;
import com.example.empleadosaplicationtressoles.R;
import com.example.empleadosaplicationtressoles.Service.PedidoService;
import com.example.empleadosaplicationtressoles.Service.UsuarioService;
import com.example.empleadosaplicationtressoles.Utilidades.ConexionRetrofit;
import com.google.android.material.button.MaterialButtonToggleGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class CrearEmpleadoFragment extends Fragment {
    View root;
    Button bttnRegistrar;
    Spinner spinerRol;
    EditText nombreUsuario,password,confirmarPaswword,celularContacto,nombre,apellido;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root=inflater.inflate(R.layout.fragment_crear_empleado, container, false);
        spinerRol=root.findViewById(R.id.spRolEmpleado);
        cargarSpinnerRol();
        nombreUsuario=root.findViewById(R.id.etNombreUsuario);
        password=root.findViewById(R.id.etPassword);
        confirmarPaswword=root.findViewById(R.id.etConfirmarPassword);
        celularContacto=root.findViewById(R.id.etNumeroContacto);
        nombre=root.findViewById(R.id.etNombre);
        apellido=root.findViewById(R.id.etApellido);
        bttnRegistrar=root.findViewById(R.id.bttnRegistrar);

        bttnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarEmpleado(crearEmpleado());
            }
        });

        return root;
    }

    private void registrarEmpleado(UsuarioDTO crearEmpleado) {
        if(crearEmpleado==null){
            Toast toast = Toast.makeText(getContext(), "La contraseña deben coincidir o verifique que eligio el rol",Toast.LENGTH_LONG);
            toast.show();
        }else{
            if(crearEmpleado.getRoles().get(0).getNombre().equals("Picker")){
                Retrofit retrofit= ConexionRetrofit.conexion(getContext(),false);
                UsuarioService usuarioService=retrofit.create(UsuarioService.class);
                Call<MensajeDTO> call=usuarioService.registrarPicker(crearEmpleado);
                call.enqueue(new Callback<MensajeDTO>() {
                    @Override
                    public void onResponse(Call<MensajeDTO> call, Response<MensajeDTO> response) {
                        if(response.code()==200){
                            Toast toast = Toast.makeText(getContext(),response.body().getMensaje(),Toast.LENGTH_LONG);
                            toast.show();
                           ListaEmpleadosFragment listaEmpleadosFragment=new ListaEmpleadosFragment();
                            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction=fragmentManager.beginTransaction();
                            //Remplazar el fragment acutal con el de Detalles producto
                            transaction.replace(R.id.idFragmentGerente, listaEmpleadosFragment);
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

            }else{

            }
        }

    }

    private UsuarioDTO crearEmpleado(){
        if(password.getText().toString().equals(confirmarPaswword.getText().toString()) && !spinerRol.getSelectedItem().toString().equals("Elegir Rol")){
            UsuarioDTO ret=new UsuarioDTO();
            ret.setNombre(nombre.getText().toString());
            ret.setApellido(apellido.getText().toString());
            ret.setNombreUsuario(nombreUsuario.getText().toString());
            ret.setNumeroContacto(celularContacto.getText().toString());
            List<Rol> roles=new ArrayList<>();
            Rol r=new Rol();
            r.setNombre(spinerRol.getSelectedItem().toString());
            roles.add(r);
            ret.setRoles(roles);
            ret.setPassword(password.getText().toString());
            return ret;
        }else {
            return null;
        }

    }

    public void cargarSpinnerRol(){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.registroRol, R.layout.spinner_estilo);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinerRol.setAdapter(adapter);
    }
}