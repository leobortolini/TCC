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
    private EmparceiramentoProposto emparceiramento_atual;
    private int limite;
    private int repeticoes;

    public Fila(ArrayList<Integer> ids) {
        itens = new ArrayList<>();
        itens.add(new ItemFila(ids));
        emparceiramento_atual = new EmparceiramentoProposto();
        limite = calcular_limite(ids.size());
        repeticoes = 0;
    }

    public ArrayList<ItemFila> getItens() {
        return itens;
    }
    
    public EmparceiramentoProposto getEmparceiramento_atual(){
        return emparceiramento_atual;
    }

    public EmparceiramentoProposto obter_proximo_emparceiramento(){
        if (repeticoes == limite) { 
            System.out.println("atingiu o limite de repeticoes da fila");
            return null;
        }
        while (!itens.get(0).terminou()) {
            ArrayList<ItemFila> resultados = itens.get(0).combinar();
           
            itens.remove(0);
            for(int i = resultados.size() - 1; i >= 0; i--) { 
                itens.add(0, resultados.get(i));
            }
        }
        emparceiramento_atual = new EmparceiramentoProposto(itens.get(0).getPar());
        itens.remove(0);
        repeticoes++;
        return getEmparceiramento_atual();
    }
    
    
    /**
     * @deprecated 
     * @return 
     */
    
    public ArrayList<EmparceiramentoProposto> resolver_fila() {
        ArrayList<EmparceiramentoProposto> pares = new ArrayList<>();

        while (!itens.get(0).terminou()) {
            ArrayList<ItemFila> resultados = itens.get(0).combinar();
           
            itens.addAll(resultados);
            itens.remove(0);
            
            for (ItemFila i : resultados) {
                resultados.add(0, i);
            }
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

    private int calcular_limite(int tam) {
        int valor = 1;
        
        for (int i = 1; i < tam; i += 2) {
            valor *= i;
        }
        
        return valor;
    }
}
