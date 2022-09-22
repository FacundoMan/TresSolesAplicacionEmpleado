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
import com.example.empleadosaplicationtressoles.Service.UsuarioService;
import com.example.empleadosaplicationtressoles.Utilidades.ConexionRetrofit;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CambiarCelularActivity extends AppCompatActivity {
    EditText celular;
    Button aceptarModificarCel,cancelarModificarCel;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_celular);
        context=getBaseContext();
        celular=findViewById(R.id.etCelularModificar);
        aceptarModificarCel=findViewById(R.id.bttnAceptarModificarCelular);
        cancelarModificarCel=findViewById(R.id.bttnCancelarModificarCelular);

        aceptarModificarCel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(celular.getText().toString().length()>0){
                    modificarCelular(celular.getText().toString());
                }

            }
        });

        cancelarModificarCel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivityTabs();
            }
        });

    }

    private void abrirActivityTabs() {
        Intent menu = new Intent(context, AdministradorTabs.class);
        startActivity(menu);
    }

    private void modificarCelular(String celular){
        Retrofit retrofit= ConexionRetrofit.conexion(context,false);
        UsuarioService usuarioService=retrofit.create(UsuarioService.class);
        Call<MensajeDTO> call=usuarioService.modificarCelular(celular);
        call.enqueue(new Callback<MensajeDTO>() {
            @Override
            public void onResponse(Call<MensajeDTO> call, Response<MensajeDTO> response) {
                if (response.code() == 200) {
                    Toast toast = Toast.makeText(context, response.body().getMensaje(), Toast.LENGTH_LONG);
                    toast.show();
                    abrirActivityTabs();
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

            }
        });
    }
}