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
public class EmparceiramentosPropostos {
    private ArrayList<Par> emparceiramentos;

    public EmparceiramentosPropostos() {
        emparceiramentos = new ArrayList<>();
    }
    
    public void adicionar_par(Integer id1, Integer id2){
        emparceiramentos.add(new Par(id1, id2));
    }
    
    public ArrayList<Par> obter_emparceiramentos(){
        return emparceiramentos;
    }
}
