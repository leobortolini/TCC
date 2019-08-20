/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tccmain;

import fila.Par;
import java.util.ArrayList;
import java.util.Arrays;

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

        lista.add(new Jogador(1, "ppbbp", 2, 4, 5));     
        lista.add(new Jogador(2, "pbbpp", 3, 5, 7));
        lista.add(new Jogador(3, "bpbbp", 4, 6, 8));
        lista.add(new Jogador(4, "bbppb", 5, 7, 1)); 
        lista.add(new Jogador(5, "bbppb", 1, 8, 2));
        lista.add(new Jogador(6, "bbppb", 7, 3));//log na base 2 do numero de jogadores
        lista.add(new Jogador(7, "ppbbp", 8, 2, 4));
        Jogador j = new Jogador(8, "bppbp", 1, 3, 5); 
        Grupo g = new Grupo(lista, (float) 0.5);

        System.out.println("------------------------------");
        System.out.println(g.receber_flutuante(true, j));
        System.out.println(g.emparceirar_grupo(false));
        System.out.println("------------------------------");
    }
}
