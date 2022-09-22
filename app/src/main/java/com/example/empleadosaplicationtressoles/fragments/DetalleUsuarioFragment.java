package com.example.empleadosaplicationtressoles.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.empleadosaplicationtressoles.Modelos.Daos.UsuarioDTO;
import com.example.empleadosaplicationtressoles.Modelos.Producto;
import com.example.empleadosaplicationtressoles.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalleUsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleUsuarioFragment extends Fragment {

    View root;

    public static DetalleUsuarioFragment newInstance(UsuarioDTO item) {
        DetalleUsuarioFragment fragment = new DetalleUsuarioFragment();
        Bundle args = new Bundle();
        args.putSerializable("usuario",item);
        fragment.setArguments(args);
        return fragment;
    }
    TextView usuario,nombre,apellido,celular,rol;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root=inflater.inflate(R.layout.fragment_detalle_usuario, container, false);

        UsuarioDTO u= (UsuarioDTO) getArguments().getSerializable("usuario");
        usuario=root.findViewById(R.id.tvUsuarioDetalle);
        nombre=root.findViewById(R.id.tvNombreDetalleUsuario);
        apellido=root.findViewById(R.id.tvApellidoDetalle);
        celular=root.findViewById(R.id.tvCelularDetalle);
        rol=root.findViewById(R.id.tvRolDetalle);
        usuario.setText(u.getNombreUsuario());
        nombre.setText(u.getNombre());
        apellido.setText(u.getApellido());
        celular.setText(u.getNumeroContacto());
        rol.setText(u.getRoles().get(0).getNombre());
        return root;
    }
}