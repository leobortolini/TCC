/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tccmain;

import emparceirador.Emparceirador;
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

        lista.add(new Jogador(1, "bbp", (float) 2, 2));;
        lista.add(new Jogador(2, "ppb", (float) 2, 1));
        lista.add(new Jogador(3, "ppb", 2));
        lista.add(new Jogador(4, "bpb", (float) 1.5));
        lista.add(new Jogador(5, "pbp", (float) 1.5));
        lista.add(new Jogador(6, "pbp", (float) 1.5));
        lista.add(new Jogador(7, "bpp", 1));
        lista.add(new Jogador(8, "bpb", 1));//log na base 2 do numero de jogadores
        lista.add(new Jogador(9, "bpb", 1));//log na base 2 do numero de jogadores
        lista.add(new Jogador(10, "ppb", (float) 0.5));
        lista.add(new Jogador(11, "ppb", (float) 0.5));
        lista.add(new Jogador(12, "bbp", (float) 0.5));
        lista.add(new Jogador(13, "bbp", (float) 0.5));
        lista.add(new Jogador(14, "bbp", (float) 0));
        lista.add(new Jogador(15, "bbp", (float) 0));
        lista.add(new Jogador(16, "bbp", (float) 0));
//        lista.add(new Jogador(9, "bbbppp", 1, 2, 3, 4, 5, 6, 7, 8, 11, 12, 13, 14, 15, 16));     
//        lista.add(new Jogador(10, "pppbbb", 1, 2, 3, 4, 5, 6, 7, 8, 11, 12, 13, 14, 15, 16));
//        lista.add(new Jogador(11, "bbbppp", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 13, 14, 15, 16));
//        lista.add(new Jogador(12, "pppbbb", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 13, 14, 15, 16)); 
//        lista.add(new Jogador(13, "bbbppp", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 15, 16));
//        lista.add(new Jogador(14, "pppbbb", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 15, 16));//log na base 2 do numero de jogadores
//        lista.add(new Jogador(15, "bbbppp", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14));
//        lista.add(new Jogador(16, "pppbbb", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14));
        Grupo g = new Grupo(lista, (float) 0.5);
        Emparceirador e = new Emparceirador(lista);
        System.out.println("------------------------------");
        e.iniciar_combinacao();
        System.out.println("------------------------------");
    }
}
