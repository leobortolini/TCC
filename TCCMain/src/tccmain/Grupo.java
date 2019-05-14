/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tccmain;

import java.util.ArrayList;

public class Grupo {

    private ArrayList<Jogador> jogadores;
    private float pontuacao_grupo;
    private ArrayList<Emparceiramento> partidas;

    public Grupo() {
    }

    public void adiciona_jogador(Jogador j) {
        jogadores.add(j);
    }

    public void emparceirar_grupo() {
        Integer numero_rodada = partidas.size() + 1;
        
        partidas.add(new Emparceiramento (numero_rodada, new ArrayList<>()));
        int quantidade_pref_brancas = preferencia_brancas();
        int quantidade_pref_preto = preferencia_pretas();
        int rebaixar = 0;

        if (quantidade_pref_brancas == quantidade_pref_preto) {
            for (int i = 0; i < quantidade_pref_brancas; i++) {
                if(!jogadores.get(i).jogou_com(i + quantidade_pref_brancas)){
                    for (int j = quantidade_pref_brancas; j < quantidade_pref_brancas * 2; j++) {
                        if(jogadores.get(i).checar_preferencia() > jogadores.get(j).checar_preferencia()){
                            partidas.get(numero_rodada).adicionar_partida(numero_rodada, new Partida(jogadores.get(i), jogadores.get(j)));
                        }else if (jogadores.get(i).checar_preferencia() < jogadores.get(j).checar_preferencia()){
                            partidas.get(numero_rodada).adicionar_partida(numero_rodada, new Partida(jogadores.get(j), jogadores.get(i)));
                        }
                    }
                    if(!partidas.get(i).foi_emparceirado(numero_rodada, i)){
                        //ver pq nao foi emparceirado e emparceirar 
                    }
                }
            }
        } else if (quantidade_pref_brancas > quantidade_pref_preto) {
            rebaixar = pior_brancas();
        } else if (quantidade_pref_brancas < quantidade_pref_brancas) {
            rebaixar = pior_pretas();
        }
    }

    public int pior_pretas() {
        int id = 0;

        for (Jogador e : jogadores) {
            if (e.checar_preferencia() > 0) {
                id = e.getId();
                break;
            }
        }
        if (id == 0) {
            for (Jogador e : jogadores) {
                if (e.UltimaCor() == 'b') {
                    id = e.getId();
                    break;
                }
            }
        }

        for (Jogador e : jogadores) {
            if (e.checar_preferencia() > 0 || e.UltimaCor() == 'p') {
                if (e.getId() > id) {
                    id = e.getId();
                }
            }
        }

        return id;
    }

    public int pior_brancas() {
        int id = 0;
        //pior com preferencia brancas
        for (Jogador e : jogadores) {
            if (e.checar_preferencia() < 0) {
                id = e.getId();
                break;
            }
        }
        if (id == 0) {
            for (Jogador e : jogadores) {
                if (e.UltimaCor() == 'p') {
                    id = e.getId();
                    break;
                }
            }
        }
        for (Jogador e : jogadores) {
            if (e.checar_preferencia() < 0 || e.UltimaCor() == 'p') {
                if (e.getId() > id) {
                    id = e.getId();
                }
            }
        }

        return id;
    }

    public int preferencia_pretas() {
        int preferencia = 0;

        for (Jogador jogadore : jogadores) {
            if (jogadore.checar_preferencia() > 0 || jogadore.UltimaCor() == 'b') {
                preferencia++;
            }
        }
        return preferencia;
    }

    public int preferencia_brancas() {
        int preferencia = 0;

        for (Jogador jogadore : jogadores) {
            if (jogadore.checar_preferencia() < 0 || jogadore.UltimaCor() == 'p') {
                preferencia++;
            }
        }
        return preferencia;
    }
}
