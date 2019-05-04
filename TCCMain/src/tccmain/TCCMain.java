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
        ArrayList<Jogador> participantes = new ArrayList<>();
        
        for (int i = 1; i <= 22; i++) {
            if(i % i == 0){
                participantes.add(new Jogador(i, "p"));
            }else{
                participantes.add(new Jogador(i, "b"));
            }
        }
    }
}
