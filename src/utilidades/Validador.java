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

    /**
     * Se encarga de validar que sea hexadecimal las entradas de campo y que correspondan la longitud al MaxDireccionable
     * @param evt El evento de las teclado
     * @param longiTextoActual Longitud de lo que hay escrito en el TextField
     * @param maxDireccionable Numero de bits direccionables
     * @return Si la entrada es valida o no lo es
     */
    public static boolean validarHexaYDirsAdmitidas(KeyEvent evt, int longiTextoActual, int maxDireccionable){
        if(!isHexadecimal(evt.getKeyChar())){
            evt.consume();
            return false;
        }
        int caracPermitidos = maxDireccionable / 4;
        int residuo = maxDireccionable % 4;
        if(residuo != 0)
            caracPermitidos++;
        if(longiTextoActual >= caracPermitidos){
            evt.consume();
            return false;
        }
        return true;
    }
    
    /**
     * Verifica que los datos a escribir en Ram correspondan al tamaño de celda y que sea hexadecimal
     * @param evt
     * @param longiTextoActual
     * @param numBytes
     * @return 
     */
    public static boolean validarHexaYDatosAdmitidos(KeyEvent evt, int longiTextoActual, int numBytes){
        if(!isHexadecimal(evt.getKeyChar())){
            evt.consume();
            return false;
        }
        int carPermitidos = numBytes * 2;
        if(longiTextoActual >= carPermitidos){
            evt.consume();
            return false;
        }
        return true;
    }
    
    /**
     * Verifica que un numero sea hexadecimal o no
     * @param num El numero a verificar
     * @return El resultado de la verificacion
     */
    private static boolean isHexadecimal(char c){
        return !((c<'a'||c>'f') && (c<'A'||c>'F') && (c<'0' || c>'9'));
    }
}
