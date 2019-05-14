/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tccmain;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author neijo
 */
public class Emparceiramento {
    private HashMap<Integer, ArrayList<Partida>> emparceiramentos;
    
    public Emparceiramento(Integer num_rodada, ArrayList<Partida> parts){
        emparceiramentos.put(num_rodada, parts);
    }
    
    void adicionar_partida(Integer numero_rodada, Partida p){
        emparceiramentos.get(numero_rodada).add(p);
    }
    
    public boolean foi_emparceirado(int numero_rodada, int id_jogador){
        boolean foi = false;

        for (int i = 0; i < emparceiramentos.get(numero_rodada).size(); i++) {
            if(emparceiramentos.get(numero_rodada).get(i).esta_emparceirado(i)){
                foi = true;
                break;
            }
        }
        return foi;
    }
}
