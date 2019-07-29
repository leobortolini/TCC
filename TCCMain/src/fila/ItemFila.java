/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fila;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import tccmain.Jogador;

/**
 *
 * @author neijo
 */
public class ItemFila {

    private ArrayList<Par> par;
    private ArrayList<Integer> restantes;
    private ArrayList<Integer> ids_cima;
    private ArrayList<Integer> ids_baixo;

    public ItemFila(ArrayList<Par> pares, ArrayList<Integer> restantes) {
        this.par = pares;
        this.restantes = restantes;
        ids_cima = new ArrayList<>();
        ids_baixo = new ArrayList<>();
    }

    public ItemFila(ArrayList<Integer> ids) {
        par = new ArrayList<>();
        restantes = new ArrayList<>();
        restantes.addAll(ids);
        ids_cima = new ArrayList<>();
        ids_baixo = new ArrayList<>();
    }

    public ItemFila(ArrayList<Integer> ids, ArrayList<Integer> ids_cima, ArrayList<Integer> ids_baixo) {
        par = new ArrayList<>();
        restantes = new ArrayList<>();
        restantes.addAll(ids);
        this.ids_cima = new ArrayList<>();
        this.ids_cima.addAll(ids_cima);
        this.ids_baixo = new ArrayList<>();
        this.ids_baixo.addAll(ids_baixo);
    }

    public ItemFila() {
        par = new ArrayList<>();
        restantes = new ArrayList<>();
        ids_cima = new ArrayList<>();
        ids_baixo = new ArrayList<>();
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

    public boolean terminou() {
        return restantes.isEmpty();
    }

    public String mostrar_emparceiramentos() {
        String resultado = new String();

        for (Par i : par) {
            resultado = resultado.concat(i.toString());
        }

        return resultado;
    }

    public void ordenar(boolean grupo_debaixo) {
        if (grupo_debaixo) {
            Comparator<Integer> por_id_decrescente = (Integer j1, Integer j2)
                    -> j2 - j1;
            Collections.sort(restantes, por_id_decrescente);
            if (ids_baixo.size() > 0) {
                Collections.sort(ids_baixo);
                restantes.addAll(0, ids_baixo);
            }
            if (ids_cima.size() > 0) {
                Collections.sort(ids_cima);
                restantes.addAll(ids_cima);
            }
            return;
        }
        Collections.sort(restantes);
        if (ids_baixo.size() > 0) {
            Collections.sort(ids_baixo);
            restantes.addAll(ids_baixo);
        }
        if (ids_cima.size() > 0) {
            Collections.sort(ids_cima);
            restantes.addAll(0, restantes);
        }
    }

    public ArrayList<ItemFila> combinar(boolean grupo_debaixo) {
        if (this.restantes.isEmpty()) {
            return null;
        }
        ordenar(grupo_debaixo);
        ArrayList<ItemFila> resultado = new ArrayList<>();

        for (int i = 0; i < restantes.size() / 2; i++) {
            ItemFila item = new ItemFila();

            for (Par p : par) {
                Par pNovo = new Par(p);
                item.getPar().add(pNovo);
            }
            for (Integer x : restantes) {
                item.getRestantes().add(x);
            }

            item.getPar().add(new Par(restantes.get(0), restantes.get(restantes.size() / 2 + i)));
            item.getRestantes().remove(restantes.get(0));
            item.getRestantes().remove(restantes.get(restantes.size() / 2 + i));
            resultado.add(item);
        }
        for (int i = 0; i < restantes.size() / 2 - 1; i++) {
            ItemFila item = new ItemFila();

            for (Par p : par) {
                Par pNovo = new Par(p);
                item.getPar().add(pNovo);
            }
            for (Integer x : restantes) {
                item.getRestantes().add(x);
            }
            item.getPar().add(new Par(restantes.get(0), restantes.get(restantes.size() / 2 - i - 1)));
            item.getRestantes().remove(restantes.get(0));
            item.getRestantes().remove(restantes.get(restantes.size() / 2 - i - 1));
            resultado.add(item);
        }
        return resultado;
    }

    @Override
    public String toString() {
        return "ItemFila{" + "par=" + par + ", restantes=" + restantes + '}';
    }
}
