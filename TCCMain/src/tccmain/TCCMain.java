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
     * 
     * 
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Jogador> lista = new ArrayList<>();

        lista.add(new Jogador(1, "", 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16));     
        lista.add(new Jogador(2, "", 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16));
        lista.add(new Jogador(3, "", 1, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16));
        lista.add(new Jogador(4, "", 1, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)); 
        lista.add(new Jogador(5, "", 1, 2, 3, 4, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16));
        lista.add(new Jogador(6, "", 1, 2, 3, 4, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16));//log na base 2 do numero de jogadores
        lista.add(new Jogador(7, "", 1, 2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14, 15, 16));
        lista.add(new Jogador(8, "", 1, 2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14, 15, 16)); 
//        lista.add(new Jogador(9, "bbbppp", 1, 2, 3, 4, 5, 6, 7, 8, 11, 12, 13, 14, 15, 16));     
//        lista.add(new Jogador(10, "pppbbb", 1, 2, 3, 4, 5, 6, 7, 8, 11, 12, 13, 14, 15, 16));
//        lista.add(new Jogador(11, "bbbppp", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 13, 14, 15, 16));
//        lista.add(new Jogador(12, "pppbbb", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 13, 14, 15, 16)); 
//        lista.add(new Jogador(13, "bbbppp", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 15, 16));
//        lista.add(new Jogador(14, "pppbbb", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 15, 16));//log na base 2 do numero de jogadores
//        lista.add(new Jogador(15, "bbbppp", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14));
//        lista.add(new Jogador(16, "pppbbb", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14));
        Grupo g = new Grupo(lista, (float) 0.5);

        System.out.println("------------------------------");
        System.out.println(g.emparceirar_grupo(false));
        System.out.println("------------------------------");
    }
}
