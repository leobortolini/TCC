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

        for (int i = 1; i < 7; i++) {
            lista.add(new Jogador(i));
        }

        Grupo g = new Grupo(lista, (float) 0.5);
        System.out.println("------------------------------");
        g.emparceirar_grupo();
        System.out.println("------------------------------");
    }
}
