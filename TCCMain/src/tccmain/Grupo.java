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
public class Grupo {
    private ArrayList<Jogador> jogadores;
    private float pontuacao_grupo;
    private ArrayList<Emparceiramento> partidas;
    
    public Grupo(){
    }
    
    public void adiciona_jogador(Jogador j){
        jogadores.add(j);
    }
    
    public void emparceirar_grupo(){
        int quantidade_pref_brancas = preferencia_brancas();
        int quantidade_pref_preto = preferencia_pretas();
        int rebaixar = 0;
        
        if(quantidade_pref_brancas == quantidade_pref_preto){
            
        }else if(quantidade_pref_brancas > quantidade_pref_preto){
            rebaixar = pior_brancas();
        }else if(quantidade_pref_brancas < quantidade_pref_brancas){
            rebaixar = pior_pretas();
        }
        int quantidade_jogos = jogadores.size();
        
        if(rebaixar > 0){
            quantidade_jogos--;
        }
        
        for (int i = 0; i < quantidade_jogos; i++) {
            
        }
    }
    
    public int pior_pretas(){
        int id = 0;
        
        for (Jogador e : jogadores) {
            if(e.checar_preferencia() > 0){
                id = e.getId();
                break;
            }
        }
        
        for (Jogador e : jogadores) {
            if(e.checar_preferencia() > 0){
                if(e.getId() > id){
                    id = e.getId();
                }
            }
        }
        
        return id;
    }
    
    public int pior_brancas(){
        int id = 0;
        
        for (Jogador e : jogadores) {
            if(e.checar_preferencia() < 0){
                id = e.getId();
                break;
            }
        }
        
        for (Jogador e : jogadores) {
            if(e.checar_preferencia() > 0){
                if(e.getId() > id){
                    id = e.getId();
                }
            }
        }
        
        return id;
    }
    
    public int preferencia_pretas(){
        int preferencia = 0;
        
        for (Jogador jogadore : jogadores) {
            if(jogadore.checar_preferencia() > 0){
                preferencia++;
            }
        }
        return preferencia;
    }
    
    public int preferencia_brancas(){
        int preferencia = 0;
        
        for (Jogador jogadore : jogadores) {
            if(jogadore.checar_preferencia() < 0){
                preferencia++;
            }
        }
        return preferencia;
    }
}
