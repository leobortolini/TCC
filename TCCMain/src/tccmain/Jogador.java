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
public class Jogador {

    private Integer id;
    private String cores;
    private ArrayList<Integer> adversarios;
    private float pontuacao;
    //checar se foi rebaixado ou promovido

    public Jogador(int id, String cor) {
        this.id = id;
        this.cores = cor;
        pontuacao = 0;
    }

    public Jogador(int id) {
        this.id = id;
    }

    public char UltimaCor() {
        return cores.charAt(cores.length() - 1);
    }

    public int getId() {
        return id;
    }

    public float getPontuacao() {
        return pontuacao;
    }

    public boolean ultimas_tres_cores(char c) {
        return cores.charAt(cores.length() - 2) == cores.charAt(cores.length() - 1)
                && cores.charAt(cores.length() - 1) == c;
    }

    public boolean rodadas_pares() {
        return adversarios.size() % 2 == 0;
    }

    public boolean preferencia_forte_pretas() {
        return checar_preferencia() == -2;
    }
    
    public boolean tem_preferencia_forte() {
        return checar_preferencia() == -2 && checar_preferencia() == 2; 
    }
    
    public boolean preferencia_forte_brancas() {
        return checar_preferencia() == 2;
    }

    public int checar_preferencia() {
        int pref = 0;

        for (int i = 0; i < cores.length(); i++) {
            if ("p".equals(cores.charAt(i))) {
                pref++;
            } else if ("b".equals(cores.charAt(i))) {
                pref--;
            }
        }
        return pref;
    }

    public int sequencia_cores_futura(char c) {
        int pref = checar_preferencia();
        if (c == 'b') {
            pref--;
        } else {
            pref++;
        }
        return pref;
    }

    public void adicionar_cor(String cor) {
        cores += cor;
    }

    public boolean jogou_com(Integer id_adversario) {
        return adversarios.contains(id);
    }
}
