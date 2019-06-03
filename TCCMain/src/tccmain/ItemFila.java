/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tccmain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author neijo
 */
public class ItemFila {

    private ArrayList<Par> par;
    private ArrayList<Integer> restantes;

    public ItemFila(ArrayList<Par> pares, ArrayList<Integer> restantes) {
        this.par = pares;
        this.restantes = restantes;
    }

    public ItemFila(ArrayList<Integer> ids) {
        par = new ArrayList<>();
        restantes = new ArrayList<>();
        restantes.addAll(ids);
    }

    public ItemFila() {
        par = new ArrayList<>();
        restantes = new ArrayList<>();
    }

    public ArrayList<Par> getPar() {
        return par;
    }

    public void setPar(ArrayList<Par> par) {
        this.par = par;
    }

    public ArrayList<Integer> getRestantes() {
        return restantes;
    }

    public void setRestantes(ArrayList<Integer> restantes) {
        this.restantes = restantes;
    }

    public boolean terminou(){
        return restantes.isEmpty();
    }
            
    public String mostrar_emparceiramentos() {
        String resultado = new String();

        for (Par i : par) {
            resultado.concat(i.toString());
        }

        return resultado;
    }

    public void ordenar() {
        Collections.sort(restantes);
    }

    public ArrayList<ItemFila> combinar() {
        if (this.restantes.isEmpty()) {
            return null;
        }
        ordenar();
        ArrayList<ItemFila> resultado = new ArrayList<>();

        for (int i = 0; i < restantes.size() / 2; i++) {
            ItemFila item = new ItemFila(this.par, this.restantes);

            item.getPar().add(new Par(restantes.get(0), restantes.get(restantes.size() / 2 + i)));
            item.getRestantes().remove(restantes.get(0));
            item.getRestantes().remove(restantes.get(restantes.size() / 2 + i));
            resultado.add(item);
        }
        for (int i = 0; i < restantes.size() / 2 - 1; i++) {
            ItemFila item = new ItemFila(this.par, this.restantes);

            item.getPar().add(new Par(restantes.get(0), restantes.get(restantes.size() / 2 - i)));
            item.getRestantes().remove(restantes.get(0));
            item.getRestantes().remove(restantes.get(restantes.size() / 2 - i));
            resultado.add(item);
        }
        return resultado;
    }

}
