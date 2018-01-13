/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.awt.event.KeyEvent;

/**
 * Contiene métodos que validan los diferentes campos.
 *
 * @author Marvin
 */
public class Validador {

    /**
     * Valida si es un número lo enviado por parámetro
     *
     * @param e El evento de teclado desencadenado al escribir una letra.
     */
    public static void validarNumero(KeyEvent e) {
        char c = e.getKeyChar();
        if (c < '0' || c > '9') {
            e.consume();
        }
    }

    /**
     * Devuelve la potencia de 2 de un numero.
     *
     * @param numCad El numero como cadena
     * @return La potencia de 2. O -1 si el numero no es potencia de dos
     */
    public static int esPotenciaDeDos(String numCad) {
        try {
            int numero = Integer.parseInt(numCad);
            int exponente = 0;
            while (numero % 2 == 0) {
                numero /= 2;
                exponente++;
            }
            if (numero != 1) {
                exponente = -1;
            }
            return exponente;
        }catch(NumberFormatException e){
            long num = Long.parseLong(numCad);
            int expo = 0;
            while(num % 2 == 0){
                num /= 2;
                expo++;
            }
            if(num != 1){
                expo = -1;
            }
            return expo;
        }

    }

}
