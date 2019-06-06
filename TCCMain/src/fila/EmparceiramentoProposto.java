/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fila;

import java.util.ArrayList;

/**
 *
 * @author neijo
 */
public class EmparceiramentoProposto {

    private ArrayList<Par> emparceiramentos;
    private Integer num_regras_relativas;
    private boolean inelegivel;

    public EmparceiramentoProposto(ArrayList<Par> lista) {
        emparceiramentos = lista;
        num_regras_relativas = 0;
        inelegivel = false;
    }

    public EmparceiramentoProposto() {
        emparceiramentos = new ArrayList<>();
        num_regras_relativas = 0;
        inelegivel = false;
    }
    
    public boolean getInelegivel(){
        return inelegivel;
    }
    
    public void adicionar_par(Integer id1, Integer id2) {
        emparceiramentos.add(new Par(id1, id2));
    }

    public ArrayList<Par> obter_emparceiramentos() {
        return emparceiramentos;
    }

    public void seguiu_regra() {
        num_regras_relativas++;
    }

    public void inelegivel() {
        inelegivel = false;
    }
}
