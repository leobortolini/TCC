/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emparceirador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import tccmain.Emparceiramento;
import tccmain.Grupo;
import tccmain.Jogador;
import tccmain.Partida;

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

    public Emparceirador(ArrayList<Jogador> jogadores) {
        this.grupos = new ArrayList<>();
        this.jogadores = new ArrayList<>(jogadores);
        emparceiramentos_finais = new ArrayList<>();
        rodada = 0;
        bye = new Jogador();
    }

    /*public Emparceirador(ArrayList<Grupo> grupos) {
        this.grupos = new ArrayList<>(grupos);
        jogadores = new ArrayList<>();
        emparceiramentos_finais = new ArrayList<>();
        rodada = 0;
    }*/
    public void adicionar_jogador(Jogador j) {
        jogadores.add(j);
    }

    public void iniciar_combinacao() {
        rodada = 2; //para testes
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
                -> (int) ((g2.getPontuacao() * 10) - (g1.getPontuacao() * 10));

        Collections.sort(grupos, grupo_por_pontuacao);
        for (Grupo g : grupos) {
            Collections.sort(g.getJogadores(), jogador_por_id);
        }
        ArrayList<Grupo> backup = new ArrayList<>(grupos);
        ArrayList<Grupo> grupos_acima = new ArrayList<>();
        ArrayList<Grupo> grupos_abaixo = new ArrayList<>();
        Grupo grupo_medio = null;

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
            for (int i = grupos_abaixo.size() - 1; i >= 0; i--) {
                bye = grupos_abaixo.get(i).encontra_bye();
                if (!bye.existe()) {
                    System.out.println("bye nao encontrado nesse grupo");
                } else {
                    break;
                }
            }
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
        }
        //tentativa de encontrar bye nos grupos de cima

        Jogador variavel_flut = new Jogador();
        Jogador variavel_flut_aux;
        Jogador variavel_flut_proprio;
        Emparceiramento geral = new Emparceiramento();

        for (int i = 0; i < grupos_acima.size(); i++) {
            if (variavel_flut.existe()) {
                if (grupos_acima.get(i).quantidade_jogadores() == 1) {
                    variavel_flut.zerar_historico();
                    if (i == grupos_acima.size() - 1) {
                        if (grupo_medio == null) {
                            grupos_abaixo.get(0).unir_grupo(grupos_acima.get(i), true);
                            grupos_abaixo.get(0).adiciona_jogador(variavel_flut);
                        } else {
                            grupo_medio.unir_grupo(grupos_acima.get(i), true);
                            grupo_medio.adiciona_jogador(variavel_flut);
                        }
                    } else {
                        grupos_acima.get(i + 1).unir_grupo(grupos_acima.get(i), false);
                        grupos_acima.get(i + 1).adiciona_jogador(variavel_flut);
                    }
                    variavel_flut = new Jogador();
                    break;
                }
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
                            variavel_flut.zerar_historico();
                            if (i == grupos_acima.size() - 1) {
                                if (grupo_medio == null) {
                                    grupos_abaixo.get(0).unir_grupo(grupos_acima.get(i), true);
                                    grupos_abaixo.get(0).adiciona_jogador(variavel_flut);
                                } else {
                                    grupo_medio.unir_grupo(grupos_acima.get(i), true);
                                    grupo_medio.adiciona_jogador(variavel_flut);
                                }
                            } else {
                                grupos_acima.get(i + 1).unir_grupo(grupos_acima.get(i), false);
                                grupos_acima.get(i + 1).adiciona_jogador(variavel_flut);
                            }
                            variavel_flut = new Jogador();
                            break;
                        }
                    }
                } else {
                    boolean conseguiu = false;

                    while (!conseguiu) {
                        variavel_flut_proprio = grupos_acima.get(i).encontra_rebaixado_forcado(true);
                        if (variavel_flut_proprio == null) {
                            variavel_flut.zerar_historico();
                            if (i == grupos_acima.size() - 1) {
                                if (grupo_medio == null) {
                                    grupos_abaixo.get(0).unir_grupo(grupos_acima.get(i), true);
                                    grupos_abaixo.get(0).adiciona_jogador(variavel_flut);
                                } else {
                                    grupo_medio.unir_grupo(grupos_acima.get(i), true);
                                    grupo_medio.adiciona_jogador(variavel_flut);
                                }
                            } else {
                                grupos_acima.get(i + 1).unir_grupo(grupos_acima.get(i), false);
                                grupos_acima.get(i + 1).adiciona_jogador(variavel_flut);
                            }
                            variavel_flut = new Jogador();
                            break;
                        }
                        variavel_flut_proprio.tentou_flutuar();
                        while (true) {
                            if (grupos_acima.get(i).receber_flutuante(true, variavel_flut)) {
                                variavel_flut_aux = grupos_acima.get(i).getFlutuanteAtualizado();
                                Emparceiramento x = grupos_acima.get(i).emparceirar_grupo(false);

                                if (x != null) {
                                    geral.adicionar_partida(x);
                                    variavel_flut = variavel_flut_proprio;
                                    variavel_flut.flutuou();
                                    variavel_flut.atualiza_flut(-1);
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
                if (grupos_acima.get(i).quantidade_jogadores() == 1) {
                    if (i == grupos_acima.size() - 1) {
                        if (grupo_medio == null) {
                            grupos_abaixo.get(0).unir_grupo(grupos_acima.get(i), true);
                        } else {
                            grupo_medio.unir_grupo(grupos_acima.get(i), true);
                        }
                    } else {
                        grupos_acima.get(i + 1).unir_grupo(grupos_acima.get(i), false);
                    }
                    break;
                }
                if (grupos_acima.get(i).quantidade_jogadores() % 2 != 0) {
                    boolean conseguiu = false;

                    while (!conseguiu) {
                        variavel_flut_proprio = grupos_acima.get(i).encontra_rebaixado_forcado(true);
                        if (variavel_flut_proprio == null) {
                            if (i == grupos_acima.size() - 1) {
                                if (grupo_medio == null) {
                                    grupos_abaixo.get(0).unir_grupo(grupos_acima.get(i), false);
                                } else {
                                    grupo_medio.unir_grupo(grupos_acima.get(i), false);
                                }
                            } else {
                                grupos_acima.get(i + 1).unir_grupo(grupos_acima.get(i), false);
                            }
                            break;
                        }
                        variavel_flut_proprio.tentou_flutuar();
                        Emparceiramento x = grupos_acima.get(i).emparceirar_grupo(false);

                        if (x != null) {
                            geral.adicionar_partida(x);
                            variavel_flut = variavel_flut_proprio;
                            variavel_flut.flutuou();
                            variavel_flut.atualiza_flut(-1);
                            variavel_flut_aux = new Jogador();
                            conseguiu = true;
                        } else {
                            grupos_acima.get(i).adiciona_jogador(variavel_flut_proprio);
                            variavel_flut_proprio = new Jogador();
                        }
                    }
                } else {
                    if (grupos_acima.get(i).quantidade_jogadores() == 1) {
                        if (i == grupos_acima.size() - 1) {
                            if (grupo_medio == null) {
                                grupos_abaixo.get(0).unir_grupo(grupos_acima.get(i), true);
                            } else {
                                grupo_medio.unir_grupo(grupos_acima.get(i), true);
                            }
                        } else {
                            grupos_acima.get(i + 1).unir_grupo(grupos_acima.get(i), false);
                        }
                    }
                    Emparceiramento x = grupos_acima.get(i).emparceirar_grupo(false);

                    if (x != null) {
                        geral.adicionar_partida(x);
                        variavel_flut = new Jogador();
                        variavel_flut_aux = new Jogador();
                    } else {
                        if (i == grupos_acima.size() - 1) {
                            if (grupo_medio == null) {
                                grupos_abaixo.get(0).unir_grupo(grupos_acima.get(i), true);
                            } else {
                                grupo_medio.unir_grupo(grupos_acima.get(i), true);
                            }
                        } else {
                            grupos_acima.get(i + 1).unir_grupo(grupos_acima.get(i), false);
                        }
                    }
                }
            }
        }
        Jogador variavel_flut_baixo = new Jogador();

        for (int i = grupos_abaixo.size() - 1; i >= 0; i--) {
            if (variavel_flut_baixo.existe()) {
                if (grupos_abaixo.get(i).quantidade_jogadores() == 1) {
                    variavel_flut_baixo.zerar_historico();
                    if (i == 0) {
                        if (grupo_medio == null) {
                            grupos_abaixo.get(i + 1).unir_grupo(grupos_abaixo.get(i), true);
                            grupos_abaixo.get(i + 1).adiciona_jogador(variavel_flut_baixo);
                            grupos_abaixo.get(i + 1).zerar_emparceiramentos_propostos();
                            grupos_abaixo.remove(i);
                            i++;
                        } else {
                            grupo_medio.unir_grupo(grupos_abaixo.get(i), true);
                            grupo_medio.adiciona_jogador(variavel_flut_baixo);
                        }
                    } else {
                        grupos_abaixo.get(i - 1).unir_grupo(grupos_abaixo.get(i), true);
                        grupos_abaixo.get(i - 1).adiciona_jogador(variavel_flut_baixo);
                    }
                    variavel_flut_baixo = new Jogador();
                    break;
                }
                if (grupos_abaixo.get(i).quantidade_jogadores() % 2 != 0) {
                    while (true) {
                        if (grupos_abaixo.get(i).receber_flutuante(false, variavel_flut_baixo)) {
                            variavel_flut_aux = grupos_abaixo.get(i).getFlutuanteAtualizado();
                            Emparceiramento x = grupos_abaixo.get(i).emparceirar_grupo(true);

                            if (x != null) {
                                grupos_abaixo.get(i).adiciona_jogador(variavel_flut_baixo);
                                variavel_flut_baixo = new Jogador();
                                variavel_flut_aux = new Jogador();
                                break;
                            } else {
                                grupos_abaixo.get(i).reiniciar_emp_flut();
                                variavel_flut_baixo = variavel_flut_aux;
                            }
                        } else {
                            variavel_flut_baixo.zerar_historico();
                            if (i == 0) {
                                if (grupo_medio == null) {
                                    grupos_abaixo.get(i + 1).unir_grupo(grupos_abaixo.get(i), true);
                                    grupos_abaixo.get(i + 1).adiciona_jogador(variavel_flut_baixo);
                                    grupos_abaixo.get(i + 1).zerar_emparceiramentos_propostos();
                                    grupos_abaixo.remove(i);
                                    i++;
                                } else {
                                    grupo_medio.unir_grupo(grupos_abaixo.get(i), true);
                                    grupo_medio.adiciona_jogador(variavel_flut_baixo);
                                }
                            } else {
                                grupos_abaixo.get(i - 1).unir_grupo(grupos_abaixo.get(i), true);
                                grupos_abaixo.get(i - 1).adiciona_jogador(variavel_flut_baixo);
                            }
                            variavel_flut_baixo = new Jogador();
                            break;
                        }
                    }
                } else {
                    boolean conseguiu = false;

                    while (!conseguiu) {
                        variavel_flut_proprio = grupos_abaixo.get(i).encontra_rebaixado_forcado(false);
                        if (variavel_flut_proprio == null) {
                            variavel_flut_baixo.zerar_historico();
                            if (i == 0) {
                                if (grupo_medio == null) {
                                    grupos_abaixo.get(i + 1).unir_grupo(grupos_abaixo.get(i), true);
                                    grupos_abaixo.get(i + 1).adiciona_jogador(variavel_flut_baixo);
                                    grupos_abaixo.get(i + 1).zerar_emparceiramentos_propostos();
                                    grupos_abaixo.remove(i);
                                    i++;
                                } else {
                                    grupo_medio.unir_grupo(grupos_abaixo.get(i), true);
                                    grupo_medio.adiciona_jogador(variavel_flut_baixo);
                                }
                            } else {
                                grupos_abaixo.get(i - 1).unir_grupo(grupos_abaixo.get(i), true);
                                grupos_abaixo.get(i - 1).adiciona_jogador(variavel_flut_baixo);
                            }
                            variavel_flut_baixo = new Jogador();
                            break;
                        }
                        variavel_flut_proprio.tentou_flutuar();
                        while (true) {
                            if (grupos_abaixo.get(i).receber_flutuante(false, variavel_flut_baixo)) {
                                variavel_flut_aux = grupos_abaixo.get(i).getFlutuanteAtualizado();

                                Emparceiramento x = grupos_abaixo.get(i).emparceirar_grupo(true);
                                if (x != null) {
                                    grupos_abaixo.get(i).adiciona_jogador(variavel_flut_baixo);
                                    variavel_flut_baixo = variavel_flut_proprio;
                                    variavel_flut_baixo.flutuou();
                                    variavel_flut_baixo.atualiza_flut(1);
                                    variavel_flut_aux = new Jogador();
                                    conseguiu = true;
                                    break;
                                } else {
                                    grupos_abaixo.get(i).reiniciar_emp_flut();
                                    grupos_abaixo.get(i).adiciona_jogador(variavel_flut_proprio);
                                    variavel_flut_baixo = variavel_flut_aux;
                                    variavel_flut_proprio = new Jogador();
                                    break;
                                }
                            } else {
                                variavel_flut_baixo.zerar_historico();
                                grupos_abaixo.get(i).adiciona_jogador(variavel_flut_proprio);
                                break;
                            }
                        }
                    }
                }
            } else {
                if (grupos_abaixo.get(i).quantidade_jogadores() == 1) {
                    if (i == 0) {
                        if (grupo_medio == null) {
                            grupos_abaixo.get(i + 1).unir_grupo(grupos_abaixo.get(i), true);
                            grupos_abaixo.remove(i);
                            i++;
                        } else {
                            grupo_medio.unir_grupo(grupos_abaixo.get(i), true);
                        }
                    } else {
                        grupos_abaixo.get(i - 1).unir_grupo(grupos_abaixo.get(i), true);
                    }
                    break;
                }
                if (grupos_abaixo.get(i).quantidade_jogadores() % 2 != 0) {
                    boolean conseguiu = false;

                    while (!conseguiu) {
                        variavel_flut_proprio = grupos_abaixo.get(i).encontra_rebaixado_forcado(false);
                        if (variavel_flut_proprio == null) {
                            if (i == 0) {
                                if (grupo_medio == null) {
                                    grupos_abaixo.get(i + 1).unir_grupo(grupos_abaixo.get(i), true);
                                    grupos_abaixo.remove(i);
                                    i++;
                                } else {
                                    grupo_medio.unir_grupo(grupos_abaixo.get(i), true);
                                }
                            } else {
                                grupos_abaixo.get(i - 1).unir_grupo(grupos_abaixo.get(i), true);
                            }
                            break;
                        }
                        variavel_flut_proprio.tentou_flutuar();
                        Emparceiramento x = grupos_abaixo.get(i).emparceirar_grupo(true);

                        if (x != null) {
                            //geral.adicionar_partida(x);
                            variavel_flut_baixo = variavel_flut_proprio;
                            variavel_flut_baixo.flutuou();
                            variavel_flut_baixo.atualiza_flut(1);
                            variavel_flut_aux = new Jogador();
                            conseguiu = true;
                        } else {
                            grupos_abaixo.get(i).adiciona_jogador(variavel_flut_proprio);
                            variavel_flut_proprio = new Jogador();
                        }
                    }
                } else {
                    Emparceiramento x = grupos_abaixo.get(i).emparceirar_grupo(true);

                    if (x != null) {
                        //geral.adicionar_partida(x);
                        variavel_flut_baixo = new Jogador();
                        variavel_flut_aux = new Jogador();
                    } else {
                        if (i == 0) {
                            if (grupo_medio == null) {
                                grupos_abaixo.get(i + 1).unir_grupo(grupos_abaixo.get(i), true);
                                grupos_abaixo.remove(i);
                                i++;
                            } else {
                                grupo_medio.unir_grupo(grupos_abaixo.get(i), true);
                            }
                        } else {
                            grupos_abaixo.get(i - 1).unir_grupo(grupos_abaixo.get(i), true);
                        }
                        break;
                    }
                }
            }
        }

        if (grupo_medio != null) {
            Emparceiramento x = null;

            if (variavel_flut.existe()) {
                if (variavel_flut_baixo.existe()) {
                    grupo_medio.adiciona_jogador(variavel_flut);
                    grupo_medio.adiciona_jogador(variavel_flut_baixo);
                    x = grupo_medio.emparceirar_grupo(false);

                    if (x == null) {
                        grupos_abaixo.get(0).unir_grupo(grupo_medio, true);
                        grupos_abaixo.get(0).zerar_emparceiramentos_propostos();
                        grupos_abaixo.get(0).adiciona_jogador(variavel_flut);
                        grupos_abaixo.get(0).adiciona_jogador(variavel_flut_baixo);
                        x = grupos_abaixo.get(0).emparceirar_grupo(false);
                    }
                } else {
                    if (grupo_medio.receber_flutuante(true, variavel_flut)) {
                        x = grupo_medio.emparceirar_grupo(false);
                        if (x == null) {
                            grupos_abaixo.get(0).unir_grupo(grupo_medio, true);
                            grupos_abaixo.get(0).zerar_emparceiramentos_propostos();
                            grupos_abaixo.get(0).adiciona_jogador(variavel_flut);
                            x = grupos_abaixo.get(0).emparceirar_grupo(false);
                        }
                    } else {
                        grupos_abaixo.get(0).unir_grupo(grupo_medio, true);
                        grupos_abaixo.get(0).zerar_emparceiramentos_propostos();
                        grupos_abaixo.get(0).adiciona_jogador(variavel_flut);
                        x = grupos_abaixo.get(0).emparceirar_grupo(false);
                    }
                }
            } else if (variavel_flut_baixo.existe()) {
                if (grupo_medio.receber_flutuante(false, variavel_flut_baixo)) {
                    x = grupo_medio.emparceirar_grupo(false);
                    if (x == null) {
                        grupos_abaixo.get(0).unir_grupo(grupo_medio, true);
                        grupos_abaixo.get(0).zerar_emparceiramentos_propostos();
                        grupos_abaixo.get(0).adiciona_jogador(variavel_flut);
                        x = grupos_abaixo.get(0).emparceirar_grupo(false);
                    }
                    geral.adicionar_partida(x);
                } else {
                    grupos_abaixo.get(0).unir_grupo(grupo_medio, true);
                    grupos_abaixo.get(0).zerar_emparceiramentos_propostos();
                    grupos_abaixo.get(0).adiciona_jogador(variavel_flut_baixo);
                    x = grupos_abaixo.get(0).emparceirar_grupo(false);
                    geral.adicionar_partida(x);
                }
            } else {
                x = grupo_medio.emparceirar_grupo(false);
                if (x == null) {
                    grupos_abaixo.get(0).unir_grupo(grupo_medio, true);
                    grupos_abaixo.get(0).zerar_emparceiramentos_propostos();
                    grupos_abaixo.get(0).adiciona_jogador(variavel_flut);
                    x = grupos_abaixo.get(0).emparceirar_grupo(false);
                    geral.adicionar_partida(x);
                }
            }
            grupos_abaixo.forEach((g) -> {
                geral.adicionar_partida(g.partidas());
            });
            System.out.println(geral.mostrar_partidas());
            System.out.println("Bye: " + bye.toString());
        }
        Scanner x = new Scanner(System.in);
        ArrayList<Jogador> aux_jogador = new ArrayList<>(jogadores.size());
        
        System.out.println("----------");
        for(Partida p : geral.getEmparceiramentos()) {
            System.out.print(p.toString() + " ");
            p.adicionar_resultado(x.nextInt());
            aux_jogador.add(p.get1());
            aux_jogador.add(p.get2());
        }
        jogadores = new ArrayList<>(aux_jogador); //colocar bye também
        for(Jogador j :jogadores) {
            System.out.println(j.getId() + "(" + j.getPontuacao() + ")" + ": " + j.getCores());
        }
    }

    public boolean existe_grupo(float pont) {
        return grupos.stream().anyMatch((g) -> (g.getPontuacao() == pont));
    }
}
