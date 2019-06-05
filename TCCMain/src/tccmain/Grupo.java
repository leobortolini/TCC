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
    private Emparceiramento partidas;
    public static ArrayList<Jogador> movidos;

    public Grupo(ArrayList<Jogador> j, float pont) {
        jogadores = j;
        pontuacao_grupo = pont;
    }

    public Grupo(Jogador j, float pont) {
        jogadores = new ArrayList<>();
        jogadores.add(j);
        pontuacao_grupo = pont;
    }

    public void adiciona_jogador(Jogador j) {
        jogadores.add(j);
    }

    public float getPontuacao() {
        return pontuacao_grupo;
    }

    
    public void emparceirar_grupo() {
        partidas = new Emparceiramento();

        int quantidade_pref_brancas = preferencia_brancas();
        int quantidade_pref_preto = preferencia_pretas();
        boolean terminou = false;

        while (!terminou) {
            if (quantidade_pref_brancas == quantidade_pref_preto) {
                for (int i = 0; i < quantidade_pref_brancas; i++) {
                    for (int j = quantidade_pref_brancas; j < quantidade_pref_brancas * 2; j++) {
                        if (jogadores.get(i).checar_preferencia() > jogadores.get(j).checar_preferencia()
                                && !jogadores.get(i).jogou_com(jogadores.get(j).getId())
                                && jogadores.get(i).checar_preferencia() > -2
                                && jogadores.get(j).checar_preferencia() < 2
                                && !jogadores.get(i).ultimas_tres_cores('b')
                                && !jogadores.get(j).ultimas_tres_cores('p')) {
                            partidas.adicionar_partida(new Partida(jogadores.get(i), jogadores.get(j)));
                            break;
                        } else if (jogadores.get(i).checar_preferencia() < jogadores.get(j).checar_preferencia()
                                && !jogadores.get(i).jogou_com(jogadores.get(j).getId())
                                && jogadores.get(i).checar_preferencia() < -2
                                && jogadores.get(j).checar_preferencia() > 2
                                && !jogadores.get(i).ultimas_tres_cores('p')
                                && !jogadores.get(j).ultimas_tres_cores('b')) {
                            partidas.adicionar_partida(new Partida(jogadores.get(j), jogadores.get(i)));
                            break;
                        }
                    }
                    if (!partidas.foi_emparceirado(jogadores.get(i).getId())) {
                        for (int j = i; j < quantidade_pref_brancas; j--) {
                            if (jogadores.get(i).checar_preferencia() > jogadores.get(j).checar_preferencia()
                                    && !jogadores.get(i).jogou_com(jogadores.get(j).getId())
                                    && jogadores.get(i).checar_preferencia() > -2
                                    && jogadores.get(j).checar_preferencia() < 2
                                    && !jogadores.get(i).ultimas_tres_cores('b')
                                    && !jogadores.get(j).ultimas_tres_cores('p')) {
                                partidas.adicionar_partida(new Partida(jogadores.get(i), jogadores.get(j)));
                                break;
                            } else if (jogadores.get(i).checar_preferencia() < jogadores.get(j).checar_preferencia()
                                    && !jogadores.get(i).jogou_com(jogadores.get(j).getId())
                                    && jogadores.get(i).checar_preferencia() < -2
                                    && jogadores.get(j).checar_preferencia() > 2
                                    && !jogadores.get(i).ultimas_tres_cores('p')
                                    && !jogadores.get(j).ultimas_tres_cores('b')) {
                                partidas.adicionar_partida(new Partida(jogadores.get(j), jogadores.get(i)));
                                break;
                            }
                        }
                    } else if (!partidas.foi_emparceirado(jogadores.get(i).getId())) {
                        //inverte a cor? e se nao der?
                    } else if (quantidade_pref_brancas > quantidade_pref_preto) {
                        Jogador rebaixado = pior_brancas();
                        jogadores.remove(rebaixado);
                        movidos.add(rebaixado);
                        quantidade_pref_brancas--;
                    } else if (quantidade_pref_brancas < quantidade_pref_preto) {
                        Jogador rebaixado = pior_pretas();
                        jogadores.remove(rebaixado);
                        movidos.add(rebaixado);
                        quantidade_pref_preto--;
                    }
                }
            }
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
