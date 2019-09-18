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
public class Emparceiramento {
    private ArrayList<Partida> emparceiramentos;
    
    public Emparceiramento(){
        emparceiramentos = new ArrayList<>();
    }
    
    public Emparceiramento(ArrayList<Partida> p){
        emparceiramentos = new ArrayList<>(p);
    }
    
    public int tamanho(){
        return emparceiramentos.size();
    }
    
    public void adicionar_partida(Partida p){
        emparceiramentos.add(p);
    }
    
    public void adicionar_partida(ArrayList<Partida> p){
        emparceiramentos.addAll(p);
    }
    
    public void adicionar_partida(Emparceiramento p) {
        emparceiramentos.addAll(p.getEmparceiramentos());
    }
    
    public ArrayList<Partida> getEmparceiramentos() {
        return emparceiramentos;
    }
    
    public boolean foi_emparceirado(int id_jogador){
        boolean achou = false;
        
        for (int i = 0; i < 10; i++) {
            if(emparceiramentos.get(i).esta_emparceirado(id_jogador)){
                achou = true;
            }
        }
        return achou;
    }
}
