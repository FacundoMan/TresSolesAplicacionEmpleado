package com.example.empleadosaplicationtressoles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.empleadosaplicationtressoles.Modelos.Daos.MensajeDTO;
import com.example.empleadosaplicationtressoles.Modelos.LineaDePedido;
import com.example.empleadosaplicationtressoles.Modelos.Pedido;
import com.example.empleadosaplicationtressoles.Service.PedidoService;
import com.example.empleadosaplicationtressoles.Service.UsuarioService;
import com.example.empleadosaplicationtressoles.Utilidades.ConexionRetrofit;
import com.example.empleadosaplicationtressoles.Utilidades.Utilidades;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetallePedidoActivity extends AppCompatActivity {
    TextView idPedido,fecha,nombre,apellido,total,
            direccion,descripcion,contacto,cambio,formaPago,retiroOEnvio,estadoDetalle,totalDescuento,tvTotalDescuento;
    ListView productos;
    Spinner spinnerCambiarEstado;
    Button bttnCambiarEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);
        idPedido=findViewById(R.id.tvIdPedidoDetalle);
        fecha=findViewById(R.id.tvFechaPedidoDetalle);
        nombre=findViewById(R.id.tvNombrePedidoDetalle);
        apellido=findViewById(R.id.tvApellidoPedidoDetalle);
        direccion=findViewById(R.id.tvDireccionPedidoDetalle);
        descripcion=findViewById(R.id.tvDescripcionCasaPedidoDetalle);
        contacto=findViewById(R.id.tvContanctoPedidoDetalle);
        cambio =findViewById(R.id.tvCambioPedidoDetalle);
        formaPago=findViewById(R.id.tvFormaPagoPedidoDetalle);
        retiroOEnvio=findViewById(R.id.tvRetiroEnvioPedidoDetalle);
        estadoDetalle=findViewById(R.id.tvEstadoPedidoDetalle);
        productos=findViewById(R.id.idListaProductoDetalle);
        total=findViewById(R.id.tvTotalDetallePedido);
        totalDescuento=findViewById(R.id.tvTotalDescuentoDetallePedido);
        tvTotalDescuento=findViewById(R.id.txtViewDescuentoDetallePedido);
        spinnerCambiarEstado=findViewById(R.id.spinnerDetalleEstado);
        bttnCambiarEstado=findViewById(R.id.bttnCambiarEstado);
        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();

        Pedido p= (Pedido) extra.getSerializable("pedido");


        idPedido.setText(""+p.getId());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date datofecha1 =p.getFecha();
        String datotexto1 = formatter.format(datofecha1);
        fecha.setText(datotexto1);
        nombre.setText(p.getNombre());
        apellido.setText(p.getApellido());
        if(p.getDireccion()==null){
            direccion.setText("");
        }else{
            direccion.setText(p.getDireccion());
        }
        if(p.getDescripcionCasa()==null){
            descripcion.setText("");
        }else{
            descripcion.setText(p.getDescripcionCasa());
        }
        contacto.setText(p.getContacto());
        cambio.setText(""+p.getCambio());

        if(p.getFormaDePago()==null){
            formaPago.setText("");
        }else{
            formaPago.setText(p.getFormaDePago());
        }

        retiroOEnvio.setText(p.getEnvioORetiro());
        estadoDetalle.setText(p.getEstado().getNombre());

        ArrayList<String> listPro=new ArrayList<String>();


        if(!p.getLineasPedido().isEmpty()){
            for (LineaDePedido l:p.getLineasPedido()) {
                String nombreProducto=l.getProducto().getNombre();
                if(nombreProducto.length()>25){
                    nombreProducto=nombreProducto.substring(0,24)+"...";
                }

                listPro.add(nombreProducto+" - Cant: "+l.getCantidad()+" - $"+(l.calcularTotalLinea()));
            }
        }

        total.setText("$"+ Utilidades.df.format(p.calcularTotal()));

        if(p.getTotal()!=p.calcularTotal()){
            totalDescuento.setVisibility(View.VISIBLE);
            tvTotalDescuento.setVisibility(View.VISIBLE);
            totalDescuento.setText("$"+Utilidades.df.format(p.getTotal()));
        }else{
            tvTotalDescuento.setVisibility(View.GONE);
            totalDescuento.setVisibility(View.GONE);
        }

        ViewGroup.LayoutParams params = productos.getLayoutParams();
        params.height = 160*(listPro.size());
        productos.setLayoutParams(params);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, listPro);
        productos.setAdapter(adapter);

        cargarSpinnerEstadoDetalle();


        bttnCambiarEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarEstado();
            }
        });
    }

    private void cambiarEstado() {
        Retrofit retrofit = ConexionRetrofit.conexion(getBaseContext(),false);
        PedidoService pedidoService=retrofit.create(PedidoService.class);
        long pedido=Long.parseLong(idPedido.getText().toString());
        long estado=spinnerCambiarEstado.getSelectedItemId()+1;
        Call<MensajeDTO> call=pedidoService.cambiarEstado(pedido,estado);
        call.enqueue(new Callback<MensajeDTO>() {
            @Override
            public void onResponse(Call<MensajeDTO> call, Response<MensajeDTO> response) {
                if(response.code()==200){

                    Toast toast = Toast.makeText(getBaseContext(), response.body().getMensaje(),Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast toast = Toast.makeText(getBaseContext(), jObjError.getJSONObject("error").getString("message"),Toast.LENGTH_LONG);
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

    private void cargarSpinnerEstadoDetalle() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getBaseContext(),
                R.array.cambiarEstadoDetalle, R.layout.spinner_estilo);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerCambiarEstado.setAdapter(adapter);
    }
}