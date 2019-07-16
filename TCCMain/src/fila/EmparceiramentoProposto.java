/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fila;

import java.util.ArrayList;

/**
 *
 * @author neijo *

 */
public class EmparceiramentoProposto {

    private ArrayList<Par> emparceiramentos;
    private boolean inelegivel;
    private float pont;

    public EmparceiramentoProposto(ArrayList<Par> lista) {
        emparceiramentos = lista;
        inelegivel = false;
        pont = 0;
    }

    public EmparceiramentoProposto() {
        emparceiramentos = new ArrayList<>();
        inelegivel = false;
        pont = 0;
    }
    
    public boolean getInelegivel(){
        return inelegivel;
    }
    
    public void adicionar_par(Integer id1, Integer id2) {
        emparceiramentos.add(new Par(id1, id2));
    }

    public ArrayList<Par> obter_emparceiramentos() {
        return new ArrayList<>(emparceiramentos);
    }


    public void inelegivel() {
        inelegivel = true;
    }
    
    public void adicionar_pont(float p) {
        pont = p;
    }
    
    public float getPont() {
        return pont;
    }
    
    public void mostrar_emparceiramentos() {
        emparceiramentos.forEach((p) -> {
            System.out.println(p.toString());
        });
    }
}
