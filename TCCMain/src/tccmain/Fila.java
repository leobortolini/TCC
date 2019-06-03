/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tccmain;

import java.util.ArrayList;

/**
 *
 * @author neijo
 */
public class Fila {
    ArrayList<ItemFila> itens;
    
    public Fila(ArrayList<Integer> ids){
        itens = new ArrayList<>();
        itens.add(new ItemFila(ids));
    }   
    
    public void resolver_fila(){
        while(!itens.get(0).terminou()){
            ArrayList<ItemFila> resultados = itens.get(0).combinar();
            itens.addAll(resultados);
            itens.remove(0);
        }
    }
    
    public String mostrar_resultados(){
        String result = new String();
        
        for(ItemFila e : itens){
            result.concat(e.mostrar_emparceiramentos());
        }
        result.concat("-------------\n");
        
        return result;
    }
}
