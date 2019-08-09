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
        ArrayList<Jogador> lista = new ArrayList<>();

        lista.add(new Jogador(1, "pbpb"));
        lista.add(new Jogador(2, "bbbp"));
        lista.add(new Jogador(3, "bbbp"));
        lista.add(new Jogador(4, "bbbp"));
        lista.add(new Jogador(5, "bbbp"));
        lista.add(new Jogador(6, "bbbp"));
        lista.add(new Jogador(7, "bbbp"));
        Jogador j = new Jogador(8, "bbbp");

        Grupo g = new Grupo(lista, (float) 0.5);

        System.out.println("------------------------------");
        System.out.println(g.receber_flutuante(true, j));
        System.out.println(g.emparceirar_grupo(false));
        System.out.println("------------------------------");
    }
}
