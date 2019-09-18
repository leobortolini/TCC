/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tccmain;

import fila.Par;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author neijo
 */
public class Jogador {

    private Integer id;
    private String cores;
    private ArrayList<Integer> adversarios;
    private float pontuacao;
    private boolean bye;
    private int flutuacao;
    private boolean exist = true;
    private ArrayList<Integer> historico_tentativas_emp;
    private boolean opcao_para_flutuar;
    //checar se foi rebaixado ou promovido

    public Jogador(int id, String cor) {
        this.id = id;
        this.cores = cor;
        pontuacao = 0;
        adversarios = new ArrayList<>();
        bye = false;
        flutuacao = 0;
        historico_tentativas_emp = new ArrayList<>();
        opcao_para_flutuar = false;
    }

    public Jogador() {
        adversarios = new ArrayList<>();
        exist = false;
        historico_tentativas_emp = new ArrayList<>();
        opcao_para_flutuar = false;
    }

    public Jogador(int id, String cor, Integer... adv) {
        this.id = id;
        this.cores = cor;
        pontuacao = 0;
        bye = false;
        flutuacao = 0;
        adversarios = new ArrayList<>();
        adversarios.addAll(Arrays.asList(adv));
        historico_tentativas_emp = new ArrayList<>();
        opcao_para_flutuar = false;
    }
    
    public Jogador(int id, String cor, ArrayList<Integer> adv) {
        this.id = id;
        this.cores = cor;
        pontuacao = 0;
        adversarios = new ArrayList<>(adv);
        bye = false;
        flutuacao = 0;
        historico_tentativas_emp = new ArrayList<>();
        opcao_para_flutuar = false;
    }

    public Jogador(int id) {
        this.id = id;
        adversarios = new ArrayList<>();
        bye = false;
        flutuacao = 0;
        historico_tentativas_emp = new ArrayList<>();
        opcao_para_flutuar = false;
    }

    public Jogador(Jogador j) {
        this.id = j.id;
        this.adversarios = j.adversarios;
        this.bye = j.bye;
        this.cores = j.cores;
        this.flutuacao = j.flutuacao;
        this.pontuacao = j.pontuacao;
        this.exist = true;
        historico_tentativas_emp = new ArrayList<>();
        opcao_para_flutuar = false;
    }

    public void tentou_flutuar(){
        opcao_para_flutuar = true;
    }
    
    public boolean getTentativaFlutuar() {
        return opcao_para_flutuar;
    }
    
    public boolean adicionar_no_historico(Integer i) {
        if(historico_tentativas_emp.size() > 0 && historico_tentativas_emp.get(0) == i){
            historico_tentativas_emp = new ArrayList<>();
            
            return false;
        }
        historico_tentativas_emp.add(i);
        
        return true;
    }
    
    public void adicionar_no_historico(Par p) {
        if(id == p.getId1()) {
            historico_tentativas_emp.add(p.getId2());
        } else {
            historico_tentativas_emp.add(p.getId1());
        }
    }
    
    public boolean esta_no_historico(int id) {
        return historico_tentativas_emp.contains(id);
    }
    
    public boolean existe(){
        return exist;
    }
    
    public char UltimaCor() {
        if(cores.isEmpty()) return ' ';
        return cores.charAt(cores.length() - 1);
    }
    
    public boolean UltimaCor(char c) {
        if(cores.isEmpty()) return true; //ver se é pra retornar true ou false, qual é melhor
        return cores.charAt(cores.length() - 1) == c;
    }

    public int getId() {
        return id;
    }

    public float getPontuacao() {
        return pontuacao;
    }
    
    public int getFlutuacao() {
        return flutuacao;
    }

    public boolean ultimas_tres_cores(char c) { //nunca pode acontecer isso
        return cores.charAt(cores.length() - 3) == cores.charAt(cores.length() - 2)
                && cores.charAt(cores.length() - 2) == cores.charAt(cores.length() - 1)
                && cores.charAt(cores.length() - 1) == c;
    }

    public boolean ultimas_duas_cores(char c) {
        if(cores.length() < 2) return false;
        return cores.charAt(cores.length() - 2) == cores.charAt(cores.length() - 1)
                && cores.charAt(cores.length() - 1) == c;
    }

    public boolean rodadas_pares() {
        return adversarios.size() % 2 == 0;
    }

    public boolean preferencia_forte_pretas() {
        return checar_preferencia() == -2
                || ultimas_duas_cores('b');
    }

    public boolean tem_preferencia_forte() {
        return checar_preferencia() == -2
                || checar_preferencia() == 2
                || ultimas_duas_cores('b')
                || ultimas_duas_cores('p');
    }

    public boolean preferencia_forte_brancas() {
        return checar_preferencia() == 2 || ultimas_duas_cores('p');
    }

    public int checar_preferencia() {
        if (ultimas_duas_cores('p')) {
            return 2;
        } else if (ultimas_duas_cores('b')) {
            return -2;
        }
        int pref = 0;

        for (int i = 0; i < cores.length(); i++) {
            if ('p' == cores.charAt(i)) {
                pref++;
            } else if ('b' == cores.charAt(i)) {
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
        return adversarios.contains(id_adversario);
    }

    public boolean foi_bye() {
        return bye;
    }
    
    public void bye() {
        bye = true;
    }
    
    public void atualiza_flut(int i){
        flutuacao += i;
    }
}
