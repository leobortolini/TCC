/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tccmain;

import fila.EmparceiramentoProposto;
import fila.Fila;
import fila.Par;
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
        ArrayList<Integer> lista_ids = new ArrayList<>();

        jogadores.forEach((j) -> {
            lista_ids.add(j.getId());
        });
        Fila fila = new Fila(lista_ids);

        partidas = new Emparceiramento();
        EmparceiramentoProposto x = new EmparceiramentoProposto();
        ArrayList<EmparceiramentoProposto> propostas = new ArrayList<>();

        while ((x = fila.obter_proximo_emparceiramento()) != null) { //checar se depois que adicionar o x na array list nao vai mduar
            ArrayList<Par> pares = x.obter_emparceiramentos();
            float pontuacao_emparceiramento = 0;
            int qtde = x.obter_emparceiramentos().size() * 2;

            for (Par p : pares) {
                if (jogadores.get(p.getId1() - 1).jogou_com(p.getId2())) {
                    x.inelegivel();
                    propostas.add(x);
                    System.out.println("inelegivel 1");
                    break;
                } else if ((jogadores.get(p.getId1() - 1).preferencia_forte_brancas() == true
                        && jogadores.get(p.getId2() - 1).preferencia_forte_brancas() == true)) {
                    x.inelegivel();
                    propostas.add(x);
                    System.out.println("inelegivel 2");
                    break;
                } else if ((jogadores.get(p.getId1() - 1).preferencia_forte_pretas() == true
                        && jogadores.get(p.getId2() - 1).preferencia_forte_pretas() == true)) {
                    x.inelegivel();
                    propostas.add(x);
                    System.out.println("inelegivel 3");
                    break;
                }
                int pref_1 = jogadores.get(p.getId1() - 1).checar_preferencia();
                int pref_2 = jogadores.get(p.getId2() - 1).checar_preferencia();

                if (pref_1 == 2) {
                    if (pref_2 < 0 || pref_2 == 0 && jogadores.get(p.getId2() - 1).UltimaCor() == 'b') {
                        pontuacao_emparceiramento += (qtde - p.getId1());
                        pontuacao_emparceiramento += (qtde - p.getId2());
                        System.out.println("1");
                    } else if (pref_2 > 0 || jogadores.get(p.getId2() - 1).UltimaCor() == 'p') {
                        pontuacao_emparceiramento += (qtde - p.getId1());
                        System.out.println("2");
                    }
                    continue;
                } else if (pref_1 == -2) {
                    pares.set(pares.indexOf(p), p.inverter_cores());
                    if (pref_2 > 0 || pref_2 == 0 && jogadores.get(p.getId1() - 1).UltimaCor() == 'p') {
                        pontuacao_emparceiramento += (qtde - p.getId1());
                        pontuacao_emparceiramento += (qtde - p.getId2());
                        System.out.println("3");
                    } else if (pref_2 < 0 || jogadores.get(p.getId1() - 1).UltimaCor() == 'b') {
                        pontuacao_emparceiramento += (qtde - p.getId2());
                        System.out.println("4");
                    }
                    continue;
                } else if (pref_2 == 2) {
                    pares.set(pares.indexOf(p), p.inverter_cores());
                    if (pref_1 < 0 || pref_1 == 0 && jogadores.get(p.getId2() - 1).UltimaCor() == 'b') {
                        pontuacao_emparceiramento += (qtde - p.getId1());
                        pontuacao_emparceiramento += (qtde - p.getId2());
                        System.out.println("5");
                    } else if (pref_1 > 0 || jogadores.get(p.getId2() - 1).UltimaCor() == 'p') {
                        pontuacao_emparceiramento += (qtde - p.getId1());
                        System.out.println("6");
                    }
                    continue;
                } else if (pref_2 == -2) {
                    if (pref_1 > 0 || pref_1 == 0 && jogadores.get(p.getId1() - 1).UltimaCor() == 'p') {
                        pontuacao_emparceiramento += (qtde - p.getId1());
                        pontuacao_emparceiramento += (qtde - p.getId2());
                        System.out.println("7");
                    } else if (pref_1 < 0 || jogadores.get(p.getId1() - 1).UltimaCor() == 'b') {
                        pontuacao_emparceiramento += (qtde - p.getId2());
                        System.out.println("8");
                    }
                    continue;
                }
                //acima são tratados os casos de preferencia forte        
                if (pref_1 > 0 && pref_2 < 0
                        || pref_1 > 0 && jogadores.get(p.getId2() - 1).UltimaCor() == 'b'
                        || jogadores.get(p.getId1() - 1).UltimaCor() == 'p'
                        && jogadores.get(p.getId2() - 1).UltimaCor() == 'b') {
                    pontuacao_emparceiramento += (qtde - p.getId1());
                    pontuacao_emparceiramento += (qtde - p.getId2());
                    System.out.println("9");
                } else if (pref_2 > 0 && pref_1 < 0
                        || pref_1 < 0 && jogadores.get(p.getId2()).UltimaCor() == 'p'
                        || jogadores.get(p.getId1() - 1).UltimaCor() == 'b'
                        && jogadores.get(p.getId2() - 1).UltimaCor() == 'p') {
                    pares.set(pares.indexOf(p), p.inverter_cores());
                    pontuacao_emparceiramento += (qtde - p.getId1());
                    pontuacao_emparceiramento += (qtde - p.getId2());
                    System.out.println("10");
                } else {
                    if (pref_1 > 0 && jogadores.get(p.getId2() - 1).UltimaCor() == 'p') {
                        pontuacao_emparceiramento += (qtde - p.getId1());
                        System.out.println("11");
                    } else if (pref_1 < 0 && jogadores.get(p.getId2() - 1).UltimaCor() == 'b') {
                        pares.set(pares.indexOf(p), p.inverter_cores());
                        pontuacao_emparceiramento += (qtde - p.getId2());
                        System.out.println("12");
                    } else if (jogadores.get(p.getId1() - 1).UltimaCor() == 'p'
                            && jogadores.get(p.getId2() - 1).UltimaCor() == 'p') {
                        pontuacao_emparceiramento += (qtde - p.getId1());
                        System.out.println("13");
                    } else if (jogadores.get(p.getId1() - 1).UltimaCor() == 'b'
                            && jogadores.get(p.getId2() - 1).UltimaCor() == 'b') {
                        pares.set(pares.indexOf(p), p.inverter_cores());
                        pontuacao_emparceiramento += (qtde - p.getId2());
                        System.out.println("14");
                    }
                }
            }
            x.adicionar_pont(pontuacao_emparceiramento);
            propostas.add(x);
        }
        //abaixo só para testes
        EmparceiramentoProposto melhor = new EmparceiramentoProposto();

        for (EmparceiramentoProposto e : propostas) {
            if (melhor.getPont() < e.getPont() && !e.getInelegivel()) {
                melhor = e;
            }
            System.out.println("--------------");
            e.mostrar_emparceiramentos();
            System.out.println(e.getPont());
            System.out.println("--------------");
        }
//        System.out.println("---------------");
//        melhor.mostrar_emparceiramentos();
//        System.out.println(melhor.getPont());
//        System.out.println("---------------");
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
