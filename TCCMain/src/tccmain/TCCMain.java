/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tccmain;

import fila.EmparceiramentoProposto;
import fila.Fila;
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

        lista.add(new Jogador(7, "ppbb"));
        lista.add(new Jogador(6, "pbpb"));
        lista.add(new Jogador(5, "bppb"));
        lista.add(new Jogador(4, "ppbb"));
        lista.add(new Jogador(3, "bpbp"));
        lista.add(new Jogador(2, "pbbp"));
        lista.add(new Jogador(1, "pbpb"));
        Jogador j = new Jogador(8, "ppbb");

        Grupo g = new Grupo(lista, (float) 0.5);

        System.out.println("------------------------------");
        g.receber_flutuante(true, j);
        g.emparceirar_grupo(false);
        System.out.println("------------------------------");
    }
}
