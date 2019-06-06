/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fila;

/**
 *
 * @author neijo
 */
public class Par {
    private Integer id1;
    private Integer id2;
    
    public Par(Integer id1, Integer id2){
        this.id1 = id1;
        this.id2 = id2;
    }
    
    public Par(Par p) {
        this.id1 = p.id1;
        this.id2 = p.id2;
    }

    public Integer getId1() {
        return id1;
    }

    public void setId1(Integer id1) {
        this.id1 = id1;
    }

    public Integer getId2() {
        return id2;
    }

    public void setId2(Integer id2) {
        this.id2 = id2;
    }
    
    public void inverter_cores(){
        Integer id_temp = id1;
        id1 = id2;
        id2 = id_temp;
    }
    
    @Override
    public String toString(){
        return id1 + "x" + id2 + "\n";
    }
}
