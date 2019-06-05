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

    ArrayList<ItemFila> itens;

    public Fila(ArrayList<Integer> ids) {
        itens = new ArrayList<>();
        itens.add(new ItemFila(ids));
    }

    public ArrayList<ItemFila> getItens() {
        return itens;
    }

    public void resolver_fila() {
        while (!itens.get(0).terminou()) {
            ArrayList<ItemFila> resultados = itens.get(0).combinar();
            if (resultados == null) {
                break;
            }
            itens.addAll(resultados);
            itens.remove(0);
        }
    }

    public void mostrar_resultados() {
        String result = new String();
        
        for (ItemFila e : itens) {
            result = result.concat(e.mostrar_emparceiramentos());
            result = result.concat("-------------\n");
        }

        System.out.println(result);
    }
}
