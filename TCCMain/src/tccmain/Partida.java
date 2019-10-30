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
    private int resultado;
    
    public Partida(Jogador jo1, Jogador jo2){
        this.j1 = jo1;
        this.j2 = jo2;
        resultado = 2;
    }
    
    public Partida(Integer id1, Integer id2) {
        j1 = new Jogador(id1);
        j2 = new Jogador(id2);
        resultado = 2;
    }
    
    public Jogador get1() {
        return j1;
    }
    
    public Jogador get2(){
        return j2;
    }
    
    public int getResultado() {
        return resultado;
    }
    
    public void adicionar_resultado(int res){
        resultado = res;
        j1.atualizar_hist(this);
        j2.atualizar_hist(this);
        switch (res) {
            case 1:
                j1.adicionar_pont(1);
                break;
            case -1:
                j2.adicionar_pont(1);
                break;
            case 0:
                j1.adicionar_pont((float) 0.5);
                j2.adicionar_pont((float) 0.5);
                break;
            default:
                System.out.println("NUMERO DE RESULTADO INVALIDO");
                break;
        }
    }
    
    public boolean esta_emparceirado(Integer id){
        return j1.getId() == id || j2.getId() == id;
    }
    
    @Override
    public String toString() {
        return j1.getId() + " x " + j2.getId() + "\n";
    }
}
