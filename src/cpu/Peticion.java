/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu;

/**
 * Una peticion del CPU
 * @author Marvin
 */
public class Peticion {
    private String campoDireccion;
    private String campoRegistro;
    private String campoDatoEscribir;
    private int metodoDireccion;
    private int tipoPeticion; //Lectura o escritura

    public String getCampoDireccion() {
        return campoDireccion;
    }

    public void setCampoDireccion(String campoDireccion) {
        this.campoDireccion = campoDireccion;
    }

    public String getCampoRegistro() {
        return campoRegistro;
    }

    public void setCampoRegistro(String campoRegistro) {
        this.campoRegistro = campoRegistro;
    }

    public String getCampoDatoEscribir() {
        return campoDatoEscribir;
    }

    public void setCampoDatoEscribir(String campoDatoEscribir) {
        this.campoDatoEscribir = campoDatoEscribir;
    }

    public int getMetodoDireccion() {
        return metodoDireccion;
    }

    public void setMetodoDireccion(int metodoDireccion) {
        this.metodoDireccion = metodoDireccion;
    }

    public int getTipoPeticion() {
        return tipoPeticion;
    }

    public void setTipoPeticion(int tipoPeticion) {
        this.tipoPeticion = tipoPeticion;
    }
    
    
}
