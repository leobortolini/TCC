/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emparceirador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import tccmain.Emparceiramento;
import tccmain.Grupo;
import tccmain.Jogador;

/**
 * *
 * @author neijo
 */
public class Emparceirador {

    private ArrayList<Jogador> jogadores;
    private ArrayList<Grupo> grupos;
    private int rodada;
    private ArrayList<Emparceiramento> emparceiramentos_finais;
    private Jogador bye;

    public Emparceirador() {
        grupos = new ArrayList<>();
        jogadores = new ArrayList<>();
        emparceiramentos_finais = new ArrayList<>();
        rodada = 0;
    }

    public Emparceirador(ArrayList<Grupo> grupos) {
        this.grupos = new ArrayList<>(grupos);
        jogadores = new ArrayList<>();
        emparceiramentos_finais = new ArrayList<>();
        rodada = 0;
    }

    public void adicionar_jogador(Jogador j) {
        jogadores.add(j);
    }

    public void iniciar_combinacao() {
        rodada++;
        for (Jogador j : jogadores) {
            if (existe_grupo(j.getPontuacao())) {
                for (Grupo g : grupos) {
                    if (g.getPontuacao() == j.getPontuacao()) {
                        g.adiciona_jogador(j);
                        break;
                    }
                }
            } else {
                grupos.add(new Grupo(j, j.getPontuacao()));
            }
        }
        Comparator<Jogador> jogador_por_id = (Jogador j1, Jogador j2)
                -> j1.getId() - j2.getId();
        Comparator<Grupo> grupo_por_pontuacao = (Grupo g1, Grupo g2)
                -> (int) ((g1.getPontuacao() * 10) - (g2.getPontuacao() * 10));

        Collections.sort(grupos, grupo_por_pontuacao);
        for (Grupo g : grupos) {
            Collections.sort(g.getJogadores(), jogador_por_id);
        }
        ArrayList<Grupo> backup = new ArrayList<>(grupos);
        //nesse momento, os grupos já estão feitos, os grupos já conseguem escolher os seus emparceiramentos
        //falta agora, coordenar a ordem de emparceiramento dos grupos
        ArrayList<Grupo> grupos_acima = new ArrayList<>();
        ArrayList<Grupo> grupos_abaixo = new ArrayList<>();
        Grupo grupo_medio;

        for (Grupo g : grupos) {
            if (g.getPontuacao() < (rodada - 1) / 2) {
                grupos_abaixo.add(g);
            } else if (g.getPontuacao() > (rodada - 1) / 2) {
                grupos_acima.add(g);
            } else {
                grupo_medio = g;
            }
        }
        //tentativa de encontrar bye nos grupos abaixo do g medio
        if (jogadores.size() % 2 != 0) {
            for (int i = grupos_abaixo.size() - 1; i > 0; i--) {
                bye = grupos_abaixo.get(i).encontra_bye();
                if (!bye.existe()) {
                    System.out.println("bye nao encontrado nesse grupo");
                } else {
                    break;
                }
            }
        }
        //tentativa de encontrar bye nos grupos de cima
        if (!bye.existe()) {
            for (Grupo g : grupos_acima) {
                bye = g.encontra_bye();
                if (!bye.existe()) {
                    System.out.println("bye nao encontrado grupo acima");
                } else {
                    break;
                }
            }
        }
        //o bye nao foi procurado no grupo médio ainda
        if (!bye.existe()) {
            System.out.println("nenhum jogador elegivel para bye");
        }
        Jogador variavel_flut = new Jogador();
        Jogador variavel_flut_aux;
        Jogador variavel_flut_proprio;
        Emparceiramento geral = new Emparceiramento();

        for (int i = 0; i < grupos_acima.size(); i++) {
            if (variavel_flut.existe()) {
                if (grupos_acima.get(i).quantidade_jogadores() % 2 != 0) {
                    while (true) {
                        if (grupos_acima.get(i).receber_flutuante(true, variavel_flut)) {
                            variavel_flut_aux = grupos_acima.get(i).getFlutuanteAtualizado();

                            Emparceiramento x = grupos_acima.get(i).emparceirar_grupo(false);
                            if (x != null) {
                                geral.adicionar_partida(x);
                                variavel_flut = new Jogador();
                                variavel_flut_aux = new Jogador();
                                break;
                            } else {
                                grupos_acima.get(i).reiniciar_emp_flut();
                                variavel_flut = variavel_flut_aux;
                            }
                        } else {
                            grupos_acima.get(i + 1).unir_grupo(grupos_acima.get(i), false);
                            variavel_flut.zerar_historico();
                            grupos_acima.get(i + 1).adiciona_jogador(variavel_flut);
                            break;
                        }
                    }
                } else {
                    boolean conseguiu = false;

                    while (!conseguiu) {
                        variavel_flut_proprio = grupos_acima.get(i).encontra_rebaixado_forcado(true);
                        if (variavel_flut_proprio == null) {
                            grupos_acima.get(i + 1).unir_grupo(grupos_acima.get(i), false);
                            variavel_flut.zerar_historico();
                            grupos_acima.get(i + 1).adiciona_jogador(variavel_flut);
                            break;
                        }
                        variavel_flut_proprio.tentou_flutuar();
                        while (true) {
                            if (grupos_acima.get(i).receber_flutuante(true, variavel_flut)) {
                                variavel_flut_aux = grupos_acima.get(i).getFlutuanteAtualizado();

                                Emparceiramento x = grupos_acima.get(i).emparceirar_grupo(false);
                                if (x != null) {
                                    geral.adicionar_partida(x);
                                    variavel_flut = new Jogador();
                                    variavel_flut_aux = new Jogador();
                                    conseguiu = true;
                                    break;
                                } else {
                                    grupos_acima.get(i).reiniciar_emp_flut();
                                    grupos_acima.get(i).adiciona_jogador(variavel_flut_proprio);
                                    variavel_flut = variavel_flut_aux;
                                    variavel_flut_proprio = new Jogador();
                                    break;
                                }
                            } else {
                                variavel_flut.zerar_historico();
                                grupos_acima.get(i).adiciona_jogador(variavel_flut_proprio);
                                break;
                            }
                        }
                    }
                }
            } else {
                if (grupos_acima.get(i).quantidade_jogadores() % 2 != 0) {
                    boolean conseguiu = false;

                    while (!conseguiu) {
                        variavel_flut_proprio = grupos_acima.get(i).encontra_rebaixado_forcado(true);
                        if (variavel_flut_proprio == null) {
                            grupos_acima.get(i + 1).unir_grupo(grupos_acima.get(i), false);
                            break;
                        }
                        variavel_flut_proprio.tentou_flutuar();
                        Emparceiramento x = grupos_acima.get(i).emparceirar_grupo(false);

                        if (x != null) {
                            geral.adicionar_partida(x);
                            variavel_flut = new Jogador();
                            variavel_flut_aux = new Jogador();
                            conseguiu = true;
                        } else {
                            grupos_acima.get(i).adiciona_jogador(variavel_flut_proprio);
                            variavel_flut_proprio = new Jogador();
                        }
                    }
                } else {
                    Emparceiramento x = grupos_acima.get(i).emparceirar_grupo(false);

                    if (x != null) {
                        geral.adicionar_partida(x);
                        variavel_flut = new Jogador();
                        variavel_flut_aux = new Jogador();
                    } else {
                        grupos_acima.get(i + 1).unir_grupo(grupos_acima.get(i), false);
                    }
                }
            }
        }
    }

    public boolean existe_grupo(float pont) {
        return grupos.stream().anyMatch((g) -> (g.getPontuacao() == pont));
    }
}
