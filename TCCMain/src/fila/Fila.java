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

    public EmparceiramentosPropostos resolver_fila() {
        EmparceiramentosPropostos pares = new EmparceiramentosPropostos();
        
        while (!itens.get(0).terminou()) {
            ArrayList<ItemFila> resultados = itens.get(0).combinar();
            if (resultados == null) {
                break;
            }
            itens.addAll(resultados);
            itens.remove(0);
        }
        for(ItemFila e : itens) {
            for (int i = 0; i < e.getPar().size(); i++) {
                pares.adicionar_par(e.getPar().get(i).getId1(), e.getPar().get(i).getId2());
            }
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
}
