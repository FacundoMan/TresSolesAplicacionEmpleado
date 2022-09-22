package com.example.empleadosaplicationtressoles;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.empleadosaplicationtressoles.Adapters.AdapterRecylcerViewPedidos;
import com.example.empleadosaplicationtressoles.Modelos.Daos.MensajeDTO;
import com.example.empleadosaplicationtressoles.Modelos.Pedido;
import com.example.empleadosaplicationtressoles.Service.PedidoService;
import com.example.empleadosaplicationtressoles.Service.UsuarioService;
import com.example.empleadosaplicationtressoles.Utilidades.ConexionRetrofit;
import com.example.empleadosaplicationtressoles.Utilidades.RolesPermitidos;
import com.example.empleadosaplicationtressoles.fragments.CrearEmpleadoFragment;
import com.example.empleadosaplicationtressoles.fragments.CrearProductoFragment;
import com.example.empleadosaplicationtressoles.fragments.EnContruccionFragment;
import com.example.empleadosaplicationtressoles.fragments.ListaEmpleadosFragment;
import com.example.empleadosaplicationtressoles.fragments.ListaPedidosFragment;
import com.example.empleadosaplicationtressoles.fragments.ListaProductosFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdministradorTabs extends AppCompatActivity {
    TabHost tabHost;
    List<String> listaRolUsuario;
    List<Pedido> listadoPedidosPicker,listadoPedidosRepartidor;
    //Componentes de picker
    AdapterRecylcerViewPedidos pedidosAdapterPicker,pedidosAdapterRepartidor ;
    Context context;
    EditText buscadorPicker,buscadorRepartidor;
    Spinner spinnerPicker, spinnerRepartidor;
    ListaEmpleadosFragment listaEmpleadosFragment;
    ListaPedidosFragment listaPedidosFragment;
    CrearEmpleadoFragment crearEmpleadoFragment;
    CrearProductoFragment crearProductoFragment;
    ListaProductosFragment listaProductoFragment;

    ImageButton bttnEmpleados,bttnAgregarEmpleado,bttnProductos,bttnCrearProducto,bttnGestion,bttnPedidos;

    Button bttnCerrarSesion,bttnModificarCelular,bttnModificarPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_tab);

        context=getBaseContext();
        bttnAgregarEmpleado=findViewById(R.id.ibEmpleadosAgregar);
        bttnEmpleados=findViewById(R.id.ibEmpleados);
        bttnProductos=findViewById(R.id.ibProductos);
        bttnCrearProducto=findViewById(R.id.ibAgregarProducto);
        bttnGestion=findViewById(R.id.ibGestion);
        bttnPedidos=findViewById(R.id.ibPedidos);
        bttnCerrarSesion=findViewById(R.id.bttnCerrarSesion);
        bttnModificarCelular=findViewById(R.id.bttnModificarCelular);
        bttnModificarPassword=findViewById(R.id.bttnModificarPassword);


        spinnerPicker  = (Spinner) findViewById(R.id.spinnerPicker);
        buscadorPicker=findViewById(R.id.etBuscadorPicker);
        buscadorRepartidor=findViewById(R.id.etBuscadorRepartidor);
        spinnerRepartidor  = (Spinner) findViewById(R.id.spinnerRepartidor);

        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("tab3");
        TabHost.TabSpec tab4 = tabHost.newTabSpec("tab4");
        TabHost.TabSpec tab5 = tabHost.newTabSpec("tab5");

        tab1.setIndicator("Inicio");//0
        tab1.setContent(R.id.tab1);

        tab2.setIndicator("Picker");//1
        tab2.setContent(R.id.tab2);

        tab3.setIndicator("Repartidor");//2
        tab3.setContent(R.id.tab3);

        tab4.setIndicator("Gerente");//3
        tab4.setContent(R.id.tab4);

        tab5.setIndicator("Opciones");//4
        tab5.setContent(R.id.tab5);

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
        tabHost.addTab(tab4);
        tabHost.addTab(tab5);

        //Cargar La lista de roles, en este caso solo se esta manejando 1 rol, pero igual por si se implementa que un usuario
        // tenga mas de 1 rol
        cargarListaRoles();
        if(RolesPermitidos.hayRolIlegal(listaRolUsuario)) {
            tabHost.getTabWidget().getChildAt(1).setVisibility(View.GONE);
            tabHost.getTabWidget().getChildAt(2).setVisibility(View.GONE);
            tabHost.getTabWidget().getChildAt(3).setVisibility(View.GONE);
            tabHost.getTabWidget().getChildAt(4).setVisibility(View.GONE);

        }else if(listaRolUsuario.contains("ROLE_GERENTE")){
            cargarListaEmpleados();
            cargarSpinnerEstadoRepartidor();
            cargarPedidosRepartidor();
            cargarSpinnerEstadoPicker();
            cargarPedidosPicker();
        }
        else if (listaRolUsuario.contains("ROLE_PICKER") && listaRolUsuario.size() == 1) {
            tabHost.getTabWidget().getChildAt(2).setVisibility(View.GONE);
            tabHost.getTabWidget().getChildAt(3).setVisibility(View.GONE);
            cargarSpinnerEstadoPicker();
            cargarPedidosPicker();

        }else if (listaRolUsuario.contains("ROLE_REPARTIDOR") && listaRolUsuario.size() == 1) {
            tabHost.getTabWidget().getChildAt(1).setVisibility(View.GONE);
            tabHost.getTabWidget().getChildAt(3).setVisibility(View.GONE);
            cargarSpinnerEstadoRepartidor();
            cargarPedidosRepartidor();
        }else if(listaRolUsuario.contains("ROLE_REPARTIDOR")&& listaRolUsuario.contains("ROLE_PICKER")){
            tabHost.getTabWidget().getChildAt(3).setVisibility(View.GONE);

            cargarSpinnerEstadoRepartidor();
            cargarPedidosRepartidor();
            cargarSpinnerEstadoPicker();
            cargarPedidosPicker();

        }

        //picker filtro
        String [] idSpinerPicker=getResources().getStringArray(R.array.idEstadoPicker);
        buscadorPicker.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    filtrar(s.toString(),Long.parseLong(idSpinerPicker[spinnerPicker.getSelectedItemPosition()]),listadoPedidosPicker,pedidosAdapterPicker);
            }
        });
        spinnerPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filtrar(buscadorPicker.getText().toString(),Long.parseLong(idSpinerPicker[spinnerPicker.getSelectedItemPosition()]),listadoPedidosPicker,pedidosAdapterPicker);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ///fin picker filtro
        //Inicio repartidor filtro

        String [] idSpinerRepartidor=getResources().getStringArray(R.array.idEstadoRepartidor);
        buscadorRepartidor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filtrar(s.toString(),Long.parseLong(idSpinerRepartidor[spinnerRepartidor.getSelectedItemPosition()]),listadoPedidosRepartidor,pedidosAdapterRepartidor);
            }
        });
        spinnerRepartidor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filtrar(buscadorRepartidor.getText().toString(),Long.parseLong(idSpinerRepartidor[spinnerRepartidor.getSelectedItemPosition()]),listadoPedidosRepartidor,pedidosAdapterRepartidor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bttnEmpleados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarListaEmpleados();
            }
        });
        bttnAgregarEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarFragmentAgregarempleado();
            }
        });

        bttnPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarFragmentPedidos();
            }
        });
        bttnCrearProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarFragmentCrearProducto();
            }
        });
        bttnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarFragmentListaProductos();
            }
        });

        bttnGestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarFragmentEnContrucion();
            }
        });
        bttnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent opc = new Intent(context, LoginActivity.class);
                startActivity(opc);
            }
        });

        bttnModificarCelular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivityCelular();
            }
        });

        bttnModificarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivityPassword();
            }
        });

    }



    private void abrirActivityPassword(){
        Intent opc = new Intent(context, CambiarPasswordActivity.class);
        startActivity(opc);
    }



    private void abrirActivityCelular(){
        Intent opc = new Intent(context, CambiarCelularActivity.class);
        startActivity(opc);
    }

    private void cargarFragmentListaProductos() {
        listaProductoFragment=new ListaProductosFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //Remplazar el fragment acutal con el de Detalles producto
        transaction.replace(R.id.idFragmentGerente, listaProductoFragment);
        transaction.commit();
    }

    private void cargarFragmentCrearProducto() {
        crearProductoFragment=new CrearProductoFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //Remplazar el fragment acutal con el de Detalles producto
        transaction.replace(R.id.idFragmentGerente, crearProductoFragment);
        transaction.commit();
    }

    private void cargarFragmentAgregarempleado() {
        crearEmpleadoFragment=new CrearEmpleadoFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //Remplazar el fragment acutal con el de Detalles producto
        transaction.replace(R.id.idFragmentGerente, crearEmpleadoFragment);
        transaction.commit();
    }

    private void cargarFragmentPedidos(){
        listaPedidosFragment=new ListaPedidosFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //Remplazar el fragment acutal con el de Detalles producto
        transaction.replace(R.id.idFragmentGerente, listaPedidosFragment);
        transaction.commit();
    }
    private void cargarFragmentEnContrucion() {
        EnContruccionFragment enContruccionFragment=new EnContruccionFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //Remplazar el fragment acutal con el de Detalles producto
        transaction.replace(R.id.idFragmentGerente, enContruccionFragment);
        transaction.commit();
    }

    private void cargarListaEmpleados() {
        listaEmpleadosFragment=new ListaEmpleadosFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //Remplazar el fragment acutal con el de Detalles producto
        transaction.replace(R.id.idFragmentGerente, listaEmpleadosFragment);
        transaction.commit();
    }

    private void cargarSpinnerEstadoRepartidor() {

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.estadoRepartidor, R.layout.spinner_estilo);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerRepartidor.setAdapter(adapter);
    }

    private void cargarPedidosRepartidor() {
        Retrofit retrofit= ConexionRetrofit.conexion(context,false);
        PedidoService pedidoService=retrofit.create(PedidoService.class);

        Call<List<Pedido>> call=pedidoService.getPedidosRepartidor();
        call.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                if(response.code()==200){
                    listadoPedidosRepartidor=response.body();
                    pedidosAdapterRepartidor=new AdapterRecylcerViewPedidos(listadoPedidosRepartidor, context, new AdapterRecylcerViewPedidos.OnItemClickListener() {
                        @Override
                        public void onItemClick(Pedido item) {
                            moveToDetalles(item);
                        }
                    });
                    RecyclerView r1=findViewById(R.id.idRecyclerRepartidor);
                    r1.setHasFixedSize(true);
                    r1.setLayoutManager(new LinearLayoutManager(context));
                    r1.setAdapter(pedidosAdapterRepartidor);
                }

            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {

            }
        });

    }

    public void cargarSpinnerEstadoPicker(){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.estadoPicker, R.layout.spinner_estilo);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerPicker.setAdapter(adapter);
    }

    private void cargarPedidosPicker(){

        Retrofit retrofit= ConexionRetrofit.conexion(context,false);
        PedidoService pedidoService=retrofit.create(PedidoService.class);
        //
        Call<List<Pedido>> call=pedidoService.getPedidoPicker();
        call.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                if(response.code()==200){
                    listadoPedidosPicker=response.body();
                    pedidosAdapterPicker=new AdapterRecylcerViewPedidos(listadoPedidosPicker, context, new AdapterRecylcerViewPedidos.OnItemClickListener() {
                        @Override
                        public void onItemClick(Pedido item) {
                            moveToDetalles(item);
                        }
                    });
                    RecyclerView r1=findViewById(R.id.idRecyclerPicker);
                    r1.setHasFixedSize(true);
                    r1.setLayoutManager(new LinearLayoutManager(context));
                    r1.setAdapter(pedidosAdapterPicker);
                }

            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {

            }
        });


    }
    public void moveToDetalles(Pedido item){
        Intent intent = new Intent(this, DetallePedidoActivity.class);
        intent.putExtra("pedido", item);
        startActivity(intent);

    }


    private void cargarListaRoles() {
        SharedPreferences p=getSharedPreferences("token_usuario", Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String listRolJson=p.getString("roles_usuario",null);
        Type type= new TypeToken<ArrayList<String>>(){}.getType();
        listaRolUsuario=gson.fromJson(listRolJson,type);
        if(listaRolUsuario==null){
            listaRolUsuario=new ArrayList<>();
        }
    }

    public void filtrar(String f, long spinnerEleccion,List<Pedido> l,AdapterRecylcerViewPedidos a){
        ArrayList<Pedido>filtrarLista=new ArrayList<>();
        for(Pedido p: l){
            if(spinnerEleccion==0){
                if((p.getNombre()+" "+p.getApellido()).toLowerCase().contains(f.toLowerCase())){
                    filtrarLista.add(p);
                }
            }else{
                if((p.getNombre()+" "+p.getApellido()).toLowerCase().contains(f.toLowerCase()) && p.getEstado().getId().equals(spinnerEleccion)){
                    filtrarLista.add(p);
                }
            }

        }
        a.filtrar(filtrarLista);
    }

}