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

            for (Par p : pares) {
                if (jogadores.get(p.getId1()).jogou_com(p.getId2())
                        || jogadores.get(p.getId1()).preferencia_forte_brancas()
                        == jogadores.get(p.getId2()).preferencia_forte_brancas()
                        || jogadores.get(p.getId1()).preferencia_forte_pretas()
                        == jogadores.get(p.getId2()).preferencia_forte_pretas()) {
                    x.inelegivel();
                    propostas.add(x);
                    break;
                }
                if (jogadores.get(p.getId1()).rodadas_pares()) {
                    int pref_1 = jogadores.get(p.getId1()).checar_preferencia();
                    int pref_2 = jogadores.get(p.getId2()).checar_preferencia();
                    
                    if (pref_1 > 0 && pref_2 < 0) {
                        pontuacao_emparceiramento += 5;
                        break;
                    } else if (pref_2 > 0 && pref_1 < 0) {
                        pares.set(pares.indexOf(p), p.inverter_cores());
                        pontuacao_emparceiramento += 5;
                        break;
                    } else {
                        if ((jogadores.get(p.getId1()).preferencia_forte_brancas() 
                                || jogadores.get(p.getId1()).checar_preferencia() > 0)  
                                &&  !jogadores.get(p.getId2()).preferencia_forte_pretas() ) {
                            break;
                        } else if (jogadores.get(p.getId1()).preferencia_forte_pretas()
                                || jogadores.get(p.getId1()).checar_preferencia() < 0){
                            pares.set(pares.indexOf(p), p.inverter_cores());
                            break;
                        }
                    }
                }
            }
        }
//        for (Par p : e.obter_emparceiramentos()) {
//            if (jogadores.get(p.getId1()).jogou_com(p.getId2())) {
//                e.inelegivel();
//                break;
//            } else {
//                Integer pont = 0;
//                Integer pont_invert = 0;
//
//                if (jogadores.get(p.getId1()).rodadas_pares()) {
//                    if (jogadores.get(p.getId1()).UltimaCor() == 'p'
//                            && jogadores.get(p.getId2()).UltimaCor() == 'b') {
//                        pont += 2;
//                    } else if (jogadores.get(p.getId1()).UltimaCor() == 'p') {
//                        pont += 1;
//                    }
//                    if (jogadores.get(p.getId2()).UltimaCor() == 'p'
//                            && jogadores.get(p.getId1()).UltimaCor() == 'b') {
//                        pont_invert += 2;
//                    } else if (jogadores.get(p.getId2()).UltimaCor() == 'p') {
//                        pont_invert += 1;
//                    }
//                } else {
//                    if (jogadores.get(p.getId1()).sequencia_cores_futura('b') == 0
//                            && jogadores.get(p.getId2()).sequencia_cores_futura('p') == 0) {
//                        pont += 2;
//                    } else if (jogadores.get(p.getId1()).sequencia_cores_futura('b') == 0) {
//                        pont += 1;
//                    }
//                    if (jogadores.get(p.getId2()).sequencia_cores_futura('b') == 0
//                            && jogadores.get(p.getId1()).sequencia_cores_futura('p') == 0) {
//                        pont_invert += 2;
//                    } else if (jogadores.get(p.getId2()).sequencia_cores_futura('b') == 0) {
//                        pont_invert += 1;
//                    }
//                }
//                if (pont_invert > pont) {
//                    p.inverter_cores();
//                }
//            }
//        }
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
