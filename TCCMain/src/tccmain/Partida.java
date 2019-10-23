/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tccmain;

/**
 *
 * @author neijo
 */
public class Partida {
    private Jogador j1;
    private Jogador j2;
    
    public Partida(Jogador jo1, Jogador jo2){
        this.j1 = jo1;
        this.j2 = jo2;
    }
    
    public Partida(Integer id1, Integer id2) {
        j1 = new Jogador(id1);
        j2 = new Jogador(id2);
    }
    
    public Jogador get1() {
        return j1;
    }
    
    public Jogador get2(){
        return j2;
    }
    
    public boolean esta_emparceirado(Integer id){
        return j1.getId() == id || j2.getId() == id;
    }
    
    @Override
    public String toString() {
        return j1.getId() + " x " + j2.getId() + "\n";
    }
}
