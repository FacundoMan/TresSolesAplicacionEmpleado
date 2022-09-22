package com.example.empleadosaplicationtressoles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.empleadosaplicationtressoles.Modelos.Daos.MensajeDTO;
import com.example.empleadosaplicationtressoles.Modelos.Daos.UsuarioDTO;
import com.example.empleadosaplicationtressoles.R;
import com.example.empleadosaplicationtressoles.Service.UsuarioService;
import com.example.empleadosaplicationtressoles.Utilidades.ConexionRetrofit;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CambiarPasswordActivity extends AppCompatActivity {
    EditText password,confirmarPassword;
    Button aceptarModificarPass,cancelarModificarPass;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_password);
        context=getBaseContext();
        password=findViewById(R.id.etPasswordCambio);
        confirmarPassword=findViewById(R.id.etConfirmarPasswordCambio);

        aceptarModificarPass=findViewById(R.id.bttnAceptarModificarPassword);
        cancelarModificarPass=findViewById(R.id.bttnCancelarModificarPassword);

        aceptarModificarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals(confirmarPassword.getText().toString()) && password.getText().toString().length()>0 ){
                    modificarPassowrd(crearModificacionUsuarioPassword());
                }else{
                    Toast toast = Toast.makeText(context, "Las contraseñas deben de coincidir", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });
        cancelarModificarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               abrirTabs();
            }
        });


    }
    private void abrirTabs(){
        Intent menu = new Intent(context, AdministradorTabs.class);
        startActivity(menu);
    }
    private UsuarioDTO crearModificacionUsuarioPassword(){
        UsuarioDTO u=new UsuarioDTO();
        u.setPassword(password.getText().toString());
        return  u;
    }
    private void modificarPassowrd(UsuarioDTO crearModificacionUsuarioPassword) {
        Retrofit retrofit= ConexionRetrofit.conexion(context,false);
        UsuarioService usuarioService=retrofit.create(UsuarioService.class);
        Call<MensajeDTO> call=usuarioService.modificarPassword(crearModificacionUsuarioPassword);
        call.enqueue(new Callback<MensajeDTO>() {
            @Override
            public void onResponse(Call<MensajeDTO> call, Response<MensajeDTO> response) {
                if (response.code() == 200) {
                    Toast toast = Toast.makeText(context, response.body().getMensaje(), Toast.LENGTH_LONG);
                    toast.show();
                    abrirTabs();
                }else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast toast = Toast.makeText(context, jObjError.getJSONObject("error").getString("message"),Toast.LENGTH_LONG);
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
                Toast toast = Toast.makeText(context, t.getMessage(),Toast.LENGTH_LONG);
                toast.show();
            }
        });

    }
}