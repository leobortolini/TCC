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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Grupo {

    private ArrayList<Jogador> jogadores;
    private float pontuacao_grupo;
    private Emparceiramento partidas;
    public static ArrayList<Jogador> movidos;
    private ArrayList<Par> pares_flutuantes;
    private ArrayList<Grupo> grupos_colapsados_cima;
    private ArrayList<Grupo> grupos_colapsados_baixo;
    private ArrayList<Jogador> jogadores_cima;
    private ArrayList<Jogador> jogadores_baixo;
    private ArrayList<Jogador> jogares_emparceirados_flut;
    private ArrayList<Par> lista_restricao;

    public Grupo(ArrayList<Jogador> j, float pont) {
        jogadores = j;
        pontuacao_grupo = pont;
        pares_flutuantes = new ArrayList<>();
        grupos_colapsados_cima = new ArrayList<>();
        grupos_colapsados_baixo = new ArrayList<>();
        jogadores_baixo = new ArrayList<>();
        jogadores_cima = new ArrayList<>();
        lista_restricao = new ArrayList<>();
        jogares_emparceirados_flut = new ArrayList<>();
    }

    public Grupo(Jogador j, float pont) {
        jogadores = new ArrayList<>();
        jogadores.add(j);
        pontuacao_grupo = pont;
        pares_flutuantes = new ArrayList<>();
        grupos_colapsados_cima = new ArrayList<>();
        grupos_colapsados_baixo = new ArrayList<>();
        jogadores_baixo = new ArrayList<>();
        jogadores_cima = new ArrayList<>();
        lista_restricao = new ArrayList<>();
        jogares_emparceirados_flut = new ArrayList<>();
    }

    public void adiciona_jogador(Jogador j) {
        jogadores.add(j);
    }

    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    public float getPontuacao() {
        return pontuacao_grupo;
    }

    public boolean receber_flutuante(boolean de_cima, Jogador jogador) {
        boolean conseguiu = false;
        ArrayList<Integer> lista_ids = new ArrayList<>();

        jogadores.forEach((j) -> {
            lista_ids.add(j.getId());
        });
        Par par_melhor = new Par();
        float pontuacao_par_melhor = 0;

        if (!de_cima) {
            jogadores_baixo.add(new Jogador(jogador));
            Comparator<Integer> por_id_decrescente = (Integer j1, Integer j2)
                    -> j2 - j1;
            Collections.sort(lista_ids, por_id_decrescente);
        } else {
            jogadores_cima.add(new Jogador(jogador));
            Collections.sort(lista_ids);
        }
        for (Integer i : lista_ids) {
            Par p = new Par(jogador.getId(), i);
            float pontuacao_par_atual = 0;
            System.out.println("Par flutuante: " + p.toString());

            if (jogador.jogou_com(p.getId2())) {
                System.out.println("jogaram entre si flutuante");
                continue;
            } else if ((jogador.preferencia_forte_brancas() == true
                    && encontra_jogador(p.getId2()).preferencia_forte_brancas() == true)) {
                System.out.println("pref forte branca flutuante");
                continue;
            } else if ((jogador.preferencia_forte_pretas() == true
                    && encontra_jogador(p.getId2()).preferencia_forte_pretas() == true)) {
                System.out.println("pref forte preta flutuante");
                continue;
            }
            int pref_1 = jogador.checar_preferencia();
            int pref_2 = encontra_jogador(p.getId2()).checar_preferencia();
            //armazenar antes o valor de cada um, cuidar com as inversões que já foram feitas!
            if (pref_1 == 2) {
                if (pref_2 < 0 || pref_2 == 0 && encontra_jogador(p.getId2()).UltimaCor() == 'b') {
                    pontuacao_par_atual += 1000;
                    pontuacao_par_atual += calcula_valor(!de_cima, 100, p.getId2());
                    System.out.println("1");
                } else if (pref_2 > 0 || encontra_jogador(p.getId2()).UltimaCor() == 'p') {
                    pontuacao_par_atual += 1000;
                    System.out.println("2");
                }
                if (pontuacao_par_atual > pontuacao_par_melhor) {
                    par_melhor = new Par(p);
                    conseguiu = true;
                    pontuacao_par_melhor = pontuacao_par_atual;
                }
                continue;
            } else if (pref_1 == -2) {
                p = p.inverter_cores();
                if (pref_2 > 0 || pref_2 == 0 && encontra_jogador(p.getId1()).UltimaCor() == 'p') {
                    pontuacao_par_atual += calcula_valor(!de_cima, 100, p.getId1());
                    pontuacao_par_atual += 1000;
                    System.out.println("3");
                } else if (pref_2 < 0 || encontra_jogador(p.getId1()).UltimaCor() == 'b') {
                    pontuacao_par_atual += 1000;
                    System.out.println("4");
                }
                if (pontuacao_par_atual > pontuacao_par_melhor) {
                    conseguiu = true;
                    par_melhor = new Par(p);
                    pontuacao_par_melhor = pontuacao_par_atual;
                }
                continue;
            } else if (pref_2 == 2) {
                p = p.inverter_cores();
                if (pref_1 < 0 || pref_1 == 0 && jogador.UltimaCor() == 'b') {
                    pontuacao_par_atual += 1000;
                    pontuacao_par_atual += calcula_valor(!de_cima, 100, p.getId2());
                    System.out.println("5");
                } else if (pref_1 > 0 || jogador.UltimaCor() == 'p') {
                    pontuacao_par_atual += 0;
                    System.out.println("6");
                }
                if (pontuacao_par_atual > pontuacao_par_melhor) {
                    par_melhor = new Par(p);
                    pontuacao_par_melhor = pontuacao_par_atual;
                    conseguiu = true;
                }
                continue;
            } else if (pref_2 == -2) {
                if (pref_1 > 0 || pref_1 == 0 && jogador.UltimaCor() == 'p') {
                    pontuacao_par_atual += 1000;
                    pontuacao_par_atual += calcula_valor(!de_cima, 100, p.getId2());
                    System.out.println("7");
                } else if (pref_1 < 0 || jogador.UltimaCor() == 'b') {
                    pontuacao_par_atual += 0;
                    System.out.println("8");
                }
                if (pontuacao_par_atual > pontuacao_par_melhor) {
                    par_melhor = new Par(p);
                    pontuacao_par_melhor = pontuacao_par_atual;
                    conseguiu = true;
                }
                continue;
            }
            //acima são tratados os casos de preferencia forte        
            if (pref_1 > 0 && pref_2 < 0
                    || pref_1 > 0 && encontra_jogador(p.getId2()).UltimaCor() == 'b'
                    || jogador.UltimaCor() == 'p'
                    && encontra_jogador(p.getId2()).UltimaCor() == 'b') {
                pontuacao_par_atual += 1000;
                pontuacao_par_atual += calcula_valor(!de_cima, 100, p.getId2());
                System.out.println("9");
            } else if (pref_2 > 0 && pref_1 < 0
                    || pref_1 < 0 && encontra_jogador(p.getId2()).UltimaCor() == 'p'
                    || jogador.UltimaCor() == 'b'
                    && encontra_jogador(p.getId2()).UltimaCor() == 'p') {
                p = p.inverter_cores();
                pontuacao_par_atual += 1000;
                pontuacao_par_atual += calcula_valor(!de_cima, 100, p.getId1());
                System.out.println("10");
            } else {
                if (pref_1 > 0 && encontra_jogador(p.getId2()).UltimaCor() == 'p') {
                    pontuacao_par_atual += 1000;
                    System.out.println("11");
                } else if (pref_1 < 0 && encontra_jogador(p.getId2()).UltimaCor() == 'b') {
                    p = p.inverter_cores();
                    pontuacao_par_atual += 1000;
                    System.out.println("12");
                } else if (jogador.UltimaCor() == 'p'
                        && encontra_jogador(p.getId2()).UltimaCor() == 'p') {
                    pontuacao_par_atual += 1000;
                    System.out.println("13");
                } else if (jogador.UltimaCor() == 'b'
                        && encontra_jogador(p.getId2()).UltimaCor() == 'b') {
                    p = p.inverter_cores();
                    pontuacao_par_atual += 1000;
                    System.out.println("14");
                }
            }
            if (pontuacao_par_atual > pontuacao_par_melhor) {
                par_melhor = new Par(p);
                conseguiu = true;
                pontuacao_par_melhor = pontuacao_par_atual;
            }
        }
        if (conseguiu == false) {
            if (!de_cima) {
                jogadores_baixo.remove(jogador);
            } else {
                jogadores_cima.remove(jogador);
            }
            return false;
        }
        pares_flutuantes.add(par_melhor);
        for (Jogador j : jogadores) {
            if (j.getId() == par_melhor.getId1()) {
                jogares_emparceirados_flut.add(j);
                jogadores.remove(j);
                break;
            } else if (j.getId() == par_melhor.getId2()) {
                jogares_emparceirados_flut.add(j);
                jogadores.remove(j);
                break;
            }
        }
        return conseguiu;
    }

    public Jogador encontra_jogador(int id) {
        for (Jogador j : jogadores) {
            if (j.getId() == id) {
                return j;
            }
        }

        return null;
    }

    public Emparceiramento emparceirar_grupo(boolean grupo_debaixo) {
        if (jogadores.size() < 2) {
            return null;
        }
        ArrayList<Integer> lista_ids = new ArrayList<>();
        //tem que tratar como flutuante os colapsados
        jogadores.forEach((j) -> {
            lista_ids.add(j.getId());
        });
        Fila fila = new Fila(lista_ids, grupo_debaixo); //tratar cada jogador de grupos colapsados como jogadores flutuantes.

        partidas = new Emparceiramento();
        EmparceiramentoProposto x = new EmparceiramentoProposto();
        ArrayList<EmparceiramentoProposto> propostas = new ArrayList<>();

        while ((x = fila.obter_proximo_emparceiramento()) != null) { //checar se depois que adicionar o x na array list nao vai mduar
            ArrayList<Par> pares = x.obter_emparceiramentos();
            float pontuacao_emparceiramento = 0;
            int qtde = x.obter_emparceiramentos().size() * 2;

            for (Par p : pares) {
                if (encontra_jogador(p.getId1()).jogou_com(p.getId2())) {
                    x.inelegivel();
                    if (!lista_restricao.contains(p)) {
                        lista_restricao.add(new Par(p));
                    }
                    propostas.add(x);
                    System.out.println("inelegivel 1");
                    break;
                } else if ((encontra_jogador(p.getId1()).preferencia_forte_brancas() == true
                        && encontra_jogador(p.getId2()).preferencia_forte_brancas() == true)) {
                    x.inelegivel();
                    if (!lista_restricao.contains(p)) {
                        lista_restricao.add(new Par(p));
                    }
                    propostas.add(x);
                    System.out.println("inelegivel 2");
                    break;
                } else if ((encontra_jogador(p.getId1()).preferencia_forte_pretas() == true
                        && encontra_jogador(p.getId2()).preferencia_forte_pretas() == true)) {
                    x.inelegivel();
                    if (!lista_restricao.contains(p)) {
                        lista_restricao.add(new Par(p));
                    }
                    propostas.add(x);
                    System.out.println("inelegivel 3");
                    break;
                }
                int pref_1 = encontra_jogador(p.getId1()).checar_preferencia();
                int pref_2 = encontra_jogador(p.getId2()).checar_preferencia();
                //armazenar antes o valor de cada um, cuidar com as inversões que já foram feitas!
                if (pref_1 == 2) {
                    if (pref_2 < 0 || pref_2 == 0 && encontra_jogador(p.getId2()).UltimaCor() == 'b') {
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId1());
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId2());
                        System.out.println("1");
                    } else if (pref_2 > 0 || encontra_jogador(p.getId2()).UltimaCor() == 'p') {
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId1());
                        System.out.println("2");
                    }
                    continue;
                } else if (pref_1 == -2) {
                    pares.set(pares.indexOf(p), p.inverter_cores());
                    if (pref_2 > 0 || pref_2 == 0 && encontra_jogador(p.getId1()).UltimaCor() == 'p') {
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId1());
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId2());
                        System.out.println("3");
                    } else if (pref_2 < 0 || encontra_jogador(p.getId1()).UltimaCor() == 'b') {
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId2());
                        System.out.println("4");
                    }
                    continue;
                } else if (pref_2 == 2) {
                    pares.set(pares.indexOf(p), p.inverter_cores());
                    if (pref_1 < 0 || pref_1 == 0 && encontra_jogador(p.getId2()).UltimaCor() == 'b') {
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId1());
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId2());
                        System.out.println("5");
                    } else if (pref_1 > 0 || encontra_jogador(p.getId2()).UltimaCor() == 'p') {
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId1());
                        System.out.println("6");
                    }
                    continue;
                } else if (pref_2 == -2) {
                    if (pref_1 > 0 || pref_1 == 0 && encontra_jogador(p.getId1()).UltimaCor() == 'p') {
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId1());
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId2());
                        System.out.println("7");
                    } else if (pref_1 < 0 || encontra_jogador(p.getId1()).UltimaCor() == 'b') {
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId2());
                        System.out.println("8");
                    }
                    continue;
                }
                //acima são tratados os casos de preferencia forte        
                if (pref_1 > 0 && pref_2 < 0
                        || pref_1 > 0 && encontra_jogador(p.getId2()).UltimaCor() == 'b'
                        || encontra_jogador(p.getId1()).UltimaCor() == 'p'
                        && encontra_jogador(p.getId2()).UltimaCor() == 'b') {
                    pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId1());
                    pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId2());
                    System.out.println("9");
                } else if (pref_2 > 0 && pref_1 < 0
                        || pref_1 < 0 && encontra_jogador(p.getId2()).UltimaCor() == 'p'
                        || encontra_jogador(p.getId1()).UltimaCor() == 'b'
                        && encontra_jogador(p.getId2()).UltimaCor() == 'p') {
                    pares.set(pares.indexOf(p), p.inverter_cores());
                    pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId1());
                    pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId2());
                    System.out.println("10");
                } else {
                    if (pref_1 > 0 && encontra_jogador(p.getId2()).UltimaCor() == 'p') {
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId1());
                        System.out.println("11");
                    } else if (pref_1 < 0 && encontra_jogador(p.getId2()).UltimaCor() == 'b') {
                        pares.set(pares.indexOf(p), p.inverter_cores());
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId2());
                        System.out.println("12");
                    } else if (encontra_jogador(p.getId1()).UltimaCor() == 'p'
                            && encontra_jogador(p.getId2()).UltimaCor() == 'p') {
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId1());
                        System.out.println("13");
                    } else if (encontra_jogador(p.getId1()).UltimaCor() == 'b'
                            && encontra_jogador(p.getId2()).UltimaCor() == 'b') {
                        pares.set(pares.indexOf(p), p.inverter_cores());
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId2());
                        System.out.println("14");
                    }
                }
            }
            x.adicionar_pont(pontuacao_emparceiramento); //ver qual é a maior pontuaçao possivel
            propostas.add(x);
        }
        EmparceiramentoProposto melhor = new EmparceiramentoProposto();

        for (EmparceiramentoProposto e : propostas) {
            if (melhor.getPont() < e.getPont() && !e.getInelegivel()) {
                melhor = e;
            }
        }
        if (melhor.getPont() == 0) {
            return null; //checar no retorno a lista de restriçao
        }
        melhor.adicionar_par(pares_flutuantes);
        melhor.mostrar_emparceiramentos();
        ArrayList<Partida> partidas_finais = new ArrayList<>();
        
        for (Par p : melhor.obter_emparceiramentos()) {
            partidas_finais.add(transforma_partida(p));
        }
        partidas = new Emparceiramento(partidas_finais, pontuacao_grupo);

        return partidas;
    }

    public ArrayList<Integer> encontra_grupo_rebaixados() {
        if (jogadores.size() < 3) {
            return null;
        }
        HashMap<Integer, Integer> contador = new HashMap<>();

        lista_restricao.forEach((p) -> {
            if (!contador.containsKey(p.getId1())) {
                contador.put(p.getId1(), 1);
            } else {
                int valor_novo = contador.get(p.getId1()) + 1;
                contador.replace(p.getId1(), valor_novo);
            }
            if (!contador.containsKey(p.getId2())) {
                contador.put(p.getId2(), 1);
            } else {
                int valor_novo = contador.get(p.getId2());
                valor_novo++;
                contador.replace(p.getId2(), valor_novo);
            }
        });

        HashMap<Integer, Integer> lista_ord = contador.entrySet().stream().
                sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2)
                        -> e1, LinkedHashMap::new));
        ArrayList<Integer> ids_rebaixar = new ArrayList<>();

        lista_ord.forEach((key, value) -> ids_rebaixar.add(key));
        return (ArrayList<Integer>) ids_rebaixar.subList(0, 2);
    }

    public void unir_grupo(Grupo g, boolean de_baixo) {
        if (de_baixo) {
            grupos_colapsados_baixo.add(g);
        }
        grupos_colapsados_cima.add(g);
    }

    public int calcula_valor(boolean grupo_debaixo, int qtde, int id) {
        if (grupo_debaixo) {
            return jogadores.indexOf(encontra_jogador(id)) + 1;
        }
        return (qtde - (jogadores.indexOf(encontra_jogador(id)) + 1));
    }

    public Partida transforma_partida(Par p) {
        return new Partida(p.getId1(), p.getId2());
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

    public int quantidade_jogadores() {
        return jogadores.size();
    }

    public Jogador encontra_rebaixado() {
        if (quantidade_jogadores() % 2 == 0) {
            return null;
        }
        if (preferencia_brancas() > preferencia_pretas()) {
            Jogador j = pior_brancas();
            jogadores.remove(j);
            return j;
        }
        Jogador j = pior_brancas();
        jogadores.remove(j);
        return j;
    }
}
