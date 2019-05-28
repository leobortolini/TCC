/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tccmain;

import java.util.ArrayList;

/**
 *
 * @author neijo
 */
public class TCCMain {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        ArrayList<Grupo> grupos = new ArrayList<>();
        ArrayList<Jogador> jogadores = new ArrayList<>();
        
        for (Jogador e : jogadores) {
            boolean existe_grupo = false;
            for (Grupo g : grupos) {
                if(e.getPontuacao() == g.getPontuacao()){
                    existe_grupo = true;
                    g.adiciona_jogador(e);
                }
            }
            if(!existe_grupo){
                grupos.add(new Grupo(e, e.getPontuacao()));
            }
        }
        
        //começar o emparceiramento e puxar os movidos do grupo anterior ou posterior
    }
}
