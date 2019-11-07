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
import java.util.Collections;
import java.util.Comparator;

public class Grupo {

    private ArrayList<Jogador> jogadores;
    private float pontuacao_grupo;
    private Emparceiramento partidas;
    private ArrayList<Par> pares_flutuantes;
    private ArrayList<Grupo> grupos_colapsados_cima;
    private ArrayList<Grupo> grupos_colapsados_baixo;
    private ArrayList<Jogador> jogadores_cima;
    private ArrayList<Jogador> jogadores_baixo;
    private ArrayList<Jogador> jogares_emparceirados_flut;
    private Jogador flutuante;

    public Grupo(ArrayList<Jogador> j, float pont) {
        jogadores = j;
        pontuacao_grupo = pont;
        pares_flutuantes = new ArrayList<>();
        grupos_colapsados_cima = new ArrayList<>();
        grupos_colapsados_baixo = new ArrayList<>();
        jogadores_baixo = new ArrayList<>();
        jogadores_cima = new ArrayList<>();
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
            //System.out.println("Par flutuante: " + p.toString());

            if (jogador.jogou_com(p.getId2())) {
                //System.out.println("jogaram entre si flutuante");
                continue;
            } else if ((jogador.preferencia_forte_brancas() == true
                    && encontra_jogador(p.getId2()).preferencia_forte_brancas() == true)) {
                //System.out.println("pref forte branca flutuante");
                continue;
            } else if ((jogador.preferencia_forte_pretas() == true
                    && encontra_jogador(p.getId2()).preferencia_forte_pretas() == true)) {
                //System.out.println("pref forte preta flutuante");
                continue;
            } else if (jogador.esta_no_historico(p.getId2())) {
                continue; //aqui checa o historico usado pro Emparceirador
            }
            int pref_1 = jogador.checar_preferencia();
            int pref_2 = encontra_jogador(p.getId2()).checar_preferencia();
            //armazenar antes o valor de cada um, cuidar com as inversões que já foram feitas!
            if (pref_1 == 2) {
                if (encontra_jogador(p.getId2()).quer_pretas()) {
                    pontuacao_par_atual += 1000;
                    pontuacao_par_atual += calcula_valor(!de_cima, 100, p.getId2());
                    //System.out.println("1");
                } else if (encontra_jogador(p.getId2()).quer_brancas()) {
                    pontuacao_par_atual += 1000;
                    //System.out.println("2");
                }
                if (pontuacao_par_atual > pontuacao_par_melhor) {
                    par_melhor = new Par(p);
                    conseguiu = true;
                    pontuacao_par_melhor = pontuacao_par_atual;
                }
                continue;
            } else if (pref_1 == -2) {
                p = p.inverter_cores();
                if (encontra_jogador(p.getId1()).quer_brancas()) {
                    pontuacao_par_atual += calcula_valor(!de_cima, 100, p.getId1());
                    pontuacao_par_atual += 1000;
                    //System.out.println("3");
                } else if (encontra_jogador(p.getId1()).quer_pretas()) {
                    pontuacao_par_atual += 1000;
                    //System.out.println("4");
                }
                if (pontuacao_par_atual > pontuacao_par_melhor) {
                    conseguiu = true;
                    par_melhor = new Par(p);
                    pontuacao_par_melhor = pontuacao_par_atual;
                }
                continue;
            } else if (pref_2 == 2) {
                p = p.inverter_cores();
                if (jogador.quer_pretas()) {
                    pontuacao_par_atual += 1000;
                    pontuacao_par_atual += calcula_valor(!de_cima, 100, p.getId1());
                    //System.out.println("5");
                } else if (jogador.quer_brancas()) {
                    pontuacao_par_atual += calcula_valor(!de_cima, 100, p.getId1());
                    //System.out.println("6");
                }
                if (pontuacao_par_atual > pontuacao_par_melhor) {
                    par_melhor = new Par(p);
                    pontuacao_par_melhor = pontuacao_par_atual;
                    conseguiu = true;
                }
                continue;
            } else if (pref_2 == -2) {
                if (jogador.quer_brancas()) {
                    pontuacao_par_atual += 1000;
                    pontuacao_par_atual += calcula_valor(!de_cima, 100, p.getId2());
                    //System.out.println("7");
                } else if (jogador.quer_pretas()) {
                    pontuacao_par_atual += 0;
                    //System.out.println("8");
                }
                if (pontuacao_par_atual > pontuacao_par_melhor) {
                    par_melhor = new Par(p);
                    pontuacao_par_melhor = pontuacao_par_atual;
                    conseguiu = true;
                }
                continue;
            }
            //acima são tratados os casos de preferencia forte        
            if (jogador.quer_brancas() && encontra_jogador(p.getId2()).quer_pretas()) {
                pontuacao_par_atual += 1000;
                pontuacao_par_atual += calcula_valor(!de_cima, 100, p.getId2());
                //System.out.println("9");
            } else if (jogador.quer_pretas() && encontra_jogador(p.getId2()).quer_brancas()) {
                p = p.inverter_cores();
                pontuacao_par_atual += 1000;
                pontuacao_par_atual += calcula_valor(!de_cima, 100, p.getId1());
                //System.out.println("10");
            } else {
                if (jogador.quer_brancas() && encontra_jogador(p.getId2()).quer_brancas()) {
                    pontuacao_par_atual += 1000;
                    //System.out.println("11");
                } else if (jogador.quer_pretas() && encontra_jogador(p.getId2()).quer_pretas()) {
                    p = p.inverter_cores();
                    pontuacao_par_atual += 1000;
                    //System.out.println("12");
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
        flutuante = new Jogador(jogador);
        flutuante.adicionar_no_historico(par_melhor);
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

    public void reiniciar_emp_flut() {
        jogadores.addAll(jogares_emparceirados_flut);
        jogares_emparceirados_flut.clear();
        pares_flutuantes.clear();
    }

    public Jogador getFlutuanteAtualizado() {
        return flutuante;
    }

    public Jogador encontra_jogador(int id) {
        for (Jogador j : jogadores) {
            if (j.getId() == id) {
                return j;
            }
        }
        for (Jogador j : jogares_emparceirados_flut) {
            if (j.getId() == id) {
                return j;
            }
        }
        for (Jogador j : jogadores_cima) {
            if (j.getId() == id) {
                return j;
            }
        }
        for (Jogador j : jogadores_baixo) {
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
        Fila fila = new Fila(lista_ids, grupo_debaixo);
        int maximo = calcula_max_pont();

        partidas = new Emparceiramento();
        EmparceiramentoProposto x = new EmparceiramentoProposto();
        ArrayList<EmparceiramentoProposto> propostas = new ArrayList<>();
        int contador = 0;
        while ((x = fila.obter_proximo_emparceiramento()) != null) {
            contador++;
            ArrayList<Par> pares = x.obter_emparceiramentos();
            float pontuacao_emparceiramento = 0;
            int qtde = x.obter_emparceiramentos().size() * 2;

            for (Par p : pares) {
                if (encontra_jogador(p.getId1()).jogou_com(p.getId2())) {
                    x.inelegivel();
                    propostas.add(x);
                    fila.adicionar_restricao(p);
                    //System.out.println("inelegivel 1");
                    break;
                } else if ((encontra_jogador(p.getId1()).preferencia_forte_brancas() == true
                        && encontra_jogador(p.getId2()).preferencia_forte_brancas() == true)) {
                    x.inelegivel();
                    propostas.add(x);
                    fila.adicionar_restricao(p);
                    //System.out.println("inelegivel 2");
                    break;
                } else if ((encontra_jogador(p.getId1()).preferencia_forte_pretas() == true
                        && encontra_jogador(p.getId2()).preferencia_forte_pretas() == true)) {
                    x.inelegivel();
                    propostas.add(x);
                    fila.adicionar_restricao(p);
                    //System.out.println("inelegivel 3");
                    break;
                }
                int pref_1 = encontra_jogador(p.getId1()).checar_preferencia();
                int pref_2 = encontra_jogador(p.getId2()).checar_preferencia();
                //armazenar antes o valor de cada um, cuidar com as inversões que já foram feitas!
                if (pref_1 == 2) {
                    if (pref_2 < 0 || pref_2 == 0 && encontra_jogador(p.getId2()).UltimaCor('b')) {
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId1());
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId2());
                        //System.out.println("1");
                    } else if (pref_2 > 0 || encontra_jogador(p.getId2()).UltimaCor('p')) {
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId1());
                        //System.out.println("2");
                    }
                    continue;
                } else if (pref_1 == -2) {
                    pares.set(pares.indexOf(p), p.inverter_cores());
                    if (pref_2 > 0 || pref_2 == 0 && encontra_jogador(p.getId1()).UltimaCor() == 'p') {
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId1());
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId2());
                        //System.out.println("3");
                    } else if (pref_2 < 0 || encontra_jogador(p.getId1()).UltimaCor() == 'b') {
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId2());
                        //System.out.println("4");
                    }
                    continue;
                } else if (pref_2 == 2) {
                    pares.set(pares.indexOf(p), p.inverter_cores());
                    if (pref_1 < 0 || pref_1 == 0 && encontra_jogador(p.getId2()).UltimaCor('b')) {
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId1());
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId2());
                        //System.out.println("5");
                    } else if (pref_1 > 0 || encontra_jogador(p.getId2()).UltimaCor('p')) {
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId1());
                        //System.out.println("6");
                    }
                    continue;
                } else if (pref_2 == -2) {
                    if (pref_1 > 0 || pref_1 == 0 && encontra_jogador(p.getId1()).UltimaCor('p')) {
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId1());
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId2());
                        //System.out.println("7");
                    } else if (pref_1 < 0 || encontra_jogador(p.getId1()).UltimaCor('b')) {
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId2());
                        //  System.out.println("8");
                    }
                    continue;
                }
                //acima são tratados os casos de preferencia forte        
                if (pref_1 > 0 && pref_2 < 0
                        || pref_1 > 0 && encontra_jogador(p.getId2()).UltimaCor('b')
                        || encontra_jogador(p.getId1()).UltimaCor('p')
                        && encontra_jogador(p.getId2()).UltimaCor('b')) {
                    pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId1());
                    pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId2());
                    //System.out.println("9");
                } else if (pref_2 > 0 && pref_1 < 0
                        || pref_1 < 0 && encontra_jogador(p.getId2()).UltimaCor('p')
                        || encontra_jogador(p.getId1()).UltimaCor('b')
                        && encontra_jogador(p.getId2()).UltimaCor('p')) {
                    pares.set(pares.indexOf(p), p.inverter_cores());
                    pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId1());
                    pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId2());
                    //System.out.println("10");
                } else {
                    if (pref_1 > 0 && encontra_jogador(p.getId2()).UltimaCor('p')) {
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId1());
                        //System.out.println("11");
                    } else if (pref_1 < 0 && encontra_jogador(p.getId2()).UltimaCor('b')) {
                        pares.set(pares.indexOf(p), p.inverter_cores());
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId2());
                        //System.out.println("12");
                    } else if (encontra_jogador(p.getId1()).UltimaCor('p')
                            && encontra_jogador(p.getId2()).UltimaCor('p')) {
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId1());
                        //System.out.println("13");
                    } else if (encontra_jogador(p.getId1()).UltimaCor('b')
                            && encontra_jogador(p.getId2()).UltimaCor('b')) {
                        pares.set(pares.indexOf(p), p.inverter_cores());
                        pontuacao_emparceiramento += calcula_valor(grupo_debaixo, qtde, p.getId2());
                        //System.out.println("14");
                    }
                }
            }
            x.adicionar_pont(pontuacao_emparceiramento); //ver qual é a maior pontuaçao possivel
            propostas.add(x);
            if (pontuacao_emparceiramento == maximo) {
                break;
            }
        }
        EmparceiramentoProposto melhor = new EmparceiramentoProposto();

        for (EmparceiramentoProposto e : propostas) {
            if (melhor.getPont() < e.getPont() && !e.getInelegivel()) {
                melhor = e;
            }
        }
        if (melhor.getPont() == 0) {
            return null;
        }
        melhor.adicionar_par(pares_flutuantes);
        melhor.mostrar_emparceiramentos();
        ArrayList<Partida> partidas_finais = new ArrayList<>();

        for (Par p : melhor.obter_emparceiramentos()) {
            partidas_finais.add(transforma_partida(p));
        }
        partidas = new Emparceiramento(partidas_finais);
        System.out.println(contador);
        return partidas;
    }

    public void zerar_jogadores() {
        jogadores.clear();
    }

    public void zerar_emparceiramentos_propostos() {
        partidas = new Emparceiramento();
    }

    public Emparceiramento partidas() {
        return partidas;
    }

    public int calcula_max_pont() {
        int valor = 0;

        for (int i = jogadores.size(); i > 0; i--) {
            valor += i;
        }

        return valor;
    }

    public void unir_grupo(Grupo g, boolean de_baixo) {
        g.getJogadores().forEach((j) -> {
            jogadores.add(new Jogador(j));
        });
        if (de_baixo) {
            grupos_colapsados_baixo.add(g);
        }
        grupos_colapsados_cima.add(g);
    }

    public int calcula_valor(boolean grupo_debaixo, int qtde, int id) {
        //multiplicar a pontuaçao pelos pontos obtidos do jogador
        if (grupo_debaixo) {
            return jogadores.indexOf(encontra_jogador(id)) + 1;
        }
        return (jogadores.size() - (jogadores.indexOf(encontra_jogador(id))));
    }

    public Partida transforma_partida(Par p) {
        return new Partida(encontra_jogador(p.getId1()), encontra_jogador(p.getId2()));
    }

    public Jogador pior_pretas(boolean vai_descer) {
        Jogador rebaixado = null;
        if (vai_descer) {
            for (int i = jogadores.size() - 1; i >= 0; i--) {
                Jogador e = jogadores.get(i);
                if (e.checar_preferencia() < 0 && e.getFlutuacao() >= 0) {
                    rebaixado = e;
                    return rebaixado;
                }
            }
            if (rebaixado == null) {
                for (int i = jogadores.size() - 1; i >= 0; i--) {
                Jogador e = jogadores.get(i);
                
                    if (e.UltimaCor() == 'b' && e.getFlutuacao() >= 0) {
                        rebaixado = e;
                        return rebaixado;
                    }
                }
            }
            for (int i = jogadores.size() - 1; i >= 0; i--) {
                Jogador e = jogadores.get(i);
            
                if (e.checar_preferencia() < 0 || e.UltimaCor() == 'p' && e.getFlutuacao() >= 0) {
                    if (e.getId() > rebaixado.getId()) {
                        rebaixado = e;
                        return rebaixado;
                    }
                }
            }
        } else {
           for (Jogador e : jogadores) {

                if (e.checar_preferencia() < 0 && e.getFlutuacao() <= 0) {
                    rebaixado = e;
                    return rebaixado;
                }
            }
            if (rebaixado == null) {
                for (Jogador e : jogadores) {

                    if (e.UltimaCor() == 'b' && e.getFlutuacao() <= 0) {
                        rebaixado = e;
                        return rebaixado;
                    }
                }
            }
            for (Jogador e : jogadores) {

                if (e.checar_preferencia() < 0 || e.UltimaCor() == 'p' && e.getFlutuacao() <= 0) {
                    if (e.getId() > rebaixado.getId()) {
                        rebaixado = e;
                        return rebaixado;
                    }
                }
            }
        }

        return rebaixado;
    }

    public Jogador pior_brancas(boolean vai_descer) {
        Jogador rebaixado = null;

        if (vai_descer) {
            for (int i = jogadores.size() - 1; i >= 0; i--) {
                Jogador e = jogadores.get(i);

                if (e.checar_preferencia() > 0 && e.getFlutuacao() >= 0 && !e.getTentativaFlutuar()) {
                    rebaixado = e;
                    return rebaixado;
                }
            }
            if (rebaixado == null) {
                for (int i = jogadores.size() - 1; i >= 0; i--) {
                    Jogador e = jogadores.get(i);
                    if (e.UltimaCor() == 'p' && e.getFlutuacao() >= 0 && !e.getTentativaFlutuar()) {
                        rebaixado = e;
                        return rebaixado;
                    }
                }
            }
            for (int i = jogadores.size() - 1; i >= 0; i--) {
                Jogador e = jogadores.get(i);

                if (e.checar_preferencia() > 0 || e.UltimaCor() == 'p' && e.getFlutuacao() >= 0
                        && !e.getTentativaFlutuar()) {
                    if (e.getId() > rebaixado.getId()) {
                        return rebaixado;
                    }
                }
            }
        } else {
            for (Jogador e : jogadores) {

                if (e.checar_preferencia() > 0 && e.getFlutuacao() <= 0 && !e.getTentativaFlutuar()) {
                    rebaixado = e;
                    return rebaixado;
                }
            }
            if (rebaixado == null) {
               for (Jogador e : jogadores) {

                    if (e.UltimaCor() == 'p' && e.getFlutuacao() <= 0 && !e.getTentativaFlutuar()) {
                        rebaixado = e;
                        return rebaixado;
                    }
                }
            }

            for (Jogador e : jogadores) {

                if ((e.checar_preferencia() > 0 || e.UltimaCor() == 'p') && e.getFlutuacao() <= 0
                        && !e.getTentativaFlutuar()) {
                    if (e.getId() > rebaixado.getId()) {
                        rebaixado = e;
                        return rebaixado;
                    }
                }
            }
        }

        return rebaixado;
    }

    public int preferencia_pretas() {
        int preferencia = 0;

        for (Jogador jogadore : jogadores) {
            if (jogadore.checar_preferencia() < 0) {
                preferencia++;
            } else if (jogadore.checar_preferencia() == 0 && jogadore.UltimaCor() == 'b') {
                preferencia++;
            }
        }
        return preferencia;
    }

    public int preferencia_brancas() {
        int preferencia = 0;

        for (Jogador jogadore : jogadores) {
            if (jogadore.checar_preferencia() > 0) {
                preferencia++;
            } else if (jogadore.checar_preferencia() == 0 && jogadore.UltimaCor('p')) {
                preferencia++;
            }
        }
        return preferencia;
    }

    public int quantidade_jogadores() {
        return jogadores.size();
    }

    public Jogador encontra_rebaixado(boolean vai_descer) {
        if (quantidade_jogadores() % 2 == 0) {
            return null;
        }
        if (preferencia_brancas() > preferencia_pretas()) {
            Jogador j = pior_brancas(vai_descer);
            if (j == null) {
                return null;
            }
            jogadores.remove(j);
            return j;
        }
        Jogador j = pior_pretas(vai_descer);
        if (j == null) {
            return null;
        }
        jogadores.remove(j);

        return j;
    }

    public Jogador encontra_rebaixado_forcado(boolean vai_descer) {
        Jogador j = null;
        int pref_br = preferencia_brancas(); //excluir depois que depurar
        int pref_pr = preferencia_pretas();

        if (preferencia_brancas() > preferencia_pretas()) {
            j = pior_brancas(vai_descer);
            if (j == null) {
                j = pior_pretas(vai_descer);
                if (j == null) {
                    if (vai_descer) {
                        j = jogadores.get(jogadores.size() - 1);
                    } else {
                        j = jogadores.get(0);
                    }
                }
            }
            jogadores.remove(j);

            return j;
        }
        j = pior_pretas(vai_descer);
        if (j == null) {
            j = pior_brancas(vai_descer);
            if (j == null) {
                if (vai_descer) {
                    j = jogadores.get(jogadores.size() - 1);
                } else {
                    j = jogadores.get(0);
                }
            }
        }
        jogadores.remove(j);

        return j;
    }

    public Jogador encontra_bye() {
        Jogador j = new Jogador();

        for (int i = jogadores.size() - 1; i > 0; i--) {
            if (!jogadores.get(i).foi_bye()) {
                j = jogadores.get(i);
                jogadores.remove(j);
                break;
            }
        }
        return j;
    }
}
