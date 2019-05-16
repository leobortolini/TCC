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
    private ArrayList<Jogador> rebaixar;
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
                        //checar pq nao foi emparceirado e emparceirar
                    }
                }
            }
        } else if (quantidade_pref_brancas > quantidade_pref_preto) {
            jogadores.remove(pior_brancas());
        } else if (quantidade_pref_brancas < quantidade_pref_brancas) {
            jogadores.remove(pior_pretas());
        }
    }

    public Jogador pior_pretas() {
        Jogador rebaixado = null;

        for (Jogador e : jogadores) {
            if (e.checar_preferencia() > 0) {
                rebaixado = e;
                break;
            }
        }
        if (rebaixado == null) {
            for (Jogador e : jogadores) {
                if (e.UltimaCor() == 'b') {
                    rebaixado = e;
                    break;
                }
            }
        }

        for (Jogador e : jogadores) {
            if (e.checar_preferencia() > 0 || e.UltimaCor() == 'p') {
                if (e.getId() > rebaixado.getId()) {
                    rebaixado = e;
                }
            }
        }

        return rebaixado;
    }

    public Jogador pior_brancas() {
        Jogador rebaixado = null;

        for (Jogador e : jogadores) {
            if (e.checar_preferencia() < 0) {
                rebaixado = e;
                break;
            }
        }
        if (rebaixado == null) {
            for (Jogador e : jogadores) {
                if (e.UltimaCor() == 'p') {
                    rebaixado = e;
                    break;
                }
            }
        }

        for (Jogador e : jogadores) {
            if (e.checar_preferencia() < 0 || e.UltimaCor() == 'p') {
                if (e.getId() > rebaixado.getId()) {
                    rebaixado = e;
                }
            }
        }

        return rebaixado;
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
