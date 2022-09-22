package com.example.empleadosaplicationtressoles.Utilidades;

import java.util.ArrayList;
import java.util.List;

public class RolesPermitidos {
    public static boolean comprobar(String rol){
        //Permitido true no permitido false
        List<String>  r=new ArrayList<>();
        r.add("ROLE_PICKER");
        r.add("ROLE_REPARTIDOR");
        r.add("ROLE_GERENTE");
        if(r.contains(rol)){
            return true;
        }
        return  false;
    }

    public static boolean hayRolIlegal(List<String> listaDeRoles){
        //Si hay 1 rol o la lista es 0 eso significa q es un rol ilegal y mandaria true
        //Si no hay un rol ilegal mandaria false
        if(listaDeRoles.size()==0){
            return true;
        }else{
            for (String s:listaDeRoles) {
                if(!RolesPermitidos.comprobar(s)){
                    return true;
                }
            }
        }
        return false;
    }
}
