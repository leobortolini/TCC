/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fila;

import java.util.ArrayList;

/**
 *
 * @author neijo
 */
public class Fila {

    private ArrayList<ItemFila> itens;

    public Fila(ArrayList<Integer> ids) {
        itens = new ArrayList<>();
        itens.add(new ItemFila(ids));
    }

    public ArrayList<ItemFila> getItens() {
        return itens;
    }

    public ArrayList<EmparceiramentoProposto> resolver_fila() {
        ArrayList<EmparceiramentoProposto> pares = new ArrayList<>();

        while (!itens.get(0).terminou()) {
            ArrayList<ItemFila> resultados = itens.get(0).combinar();
           
            itens.addAll(resultados);
            itens.remove(0);
        }
        for (ItemFila e : itens) {
            pares.add(new EmparceiramentoProposto(e.getPar()));
        }

        return pares;
    }

    public void mostrar_resultados() {
        String result = new String();

        for (ItemFila e : itens) {
            result = result.concat(e.mostrar_emparceiramentos());
            result = result.concat("-------------\n");
        }
        System.out.println(result);
    }
    
    public Integer tamanho_lista(){
        return itens.size();
    }
}
