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

/**  *
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
        //nesse momento, os grupos já estão feitos, os grupos já conseguem escolher os seus emparceiramentos
        //falta agora, coordenar a ordem de emparceiramento dos grupos
        //manipular os flutuantes 
        
        
    }

    public boolean existe_grupo(float pont) {
        for (Grupo g : grupos) {
            if (g.getPontuacao() == pont) {
                return true;
            }
        }

        return false;
    }
}
