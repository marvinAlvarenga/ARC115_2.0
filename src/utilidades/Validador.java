/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.awt.event.KeyEvent;

/**
 * Contiene métodos que validan los diferentes campos.
 * @author Marvin
 */
public class Validador {
    
    /**
     * Valida si es un número lo enviado por parámetro
     * @param e El evento de teclado desencadenado al escribir una letra.
     */
    public static void validarNumero(KeyEvent e){
        char c = e.getKeyChar();
        if(c<'0' || c>'9'){
            e.consume();
        }
    }
    
}
