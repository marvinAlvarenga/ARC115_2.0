package cache;

import cpu.UtilDireccionamiento;

/**
 * Utilidades de constantes
 * @author Marvin
 */
public class UtilCache {
    
    public static final int DIRECTA = 0;
    public static final int ASOCIATIVA = 1;
    public static final int POR_CONJUNTO = 2;
    public static final int LRU = 0;
    public static final int FIFO = 1;
    public static final int ALEATORIO = 2;
    public static final int MANUAL = 0;
    public static final int LLENADO_ALEAT = 1;
    
    /**
     * Devuelve la etiqueta de la direccion
     * @param direccion
     * @param maxDireccionable
     * @param bitsEtiqueta
     * @return 
     */
    public static String generarEtiqueta(String direccion, int maxDireccionable,int bitsEtiqueta){
        String binario = UtilDireccionamiento.direccionEnBinario(direccion, maxDireccionable);
        String etiquetaBin = binario.substring(0, bitsEtiqueta);
        return Long.toHexString(Long.parseLong(etiquetaBin, 2));
    }
    
    public static long generarBloqueMP(String direccion, int maxDireccionable, int bitsPalabra){
        long bloque;
        String binario = UtilDireccionamiento.direccionEnBinario(direccion, maxDireccionable);
        String bloqueString = binario.substring(0, maxDireccionable - bitsPalabra);
        bloque = Long.parseLong(bloqueString, 2);
        return bloque;
    }
    
    public static int generarPalabra(String direccion, int maxDireccionable, int bitsPalabra){
        int palabra;
        String binario = UtilDireccionamiento.direccionEnBinario(direccion, maxDireccionable);
        String palaString = binario.substring(maxDireccionable - bitsPalabra);
        if(bitsPalabra != 0)
            palabra = Integer.parseInt(palaString, 2);
        else
            palabra = 0;
        return palabra;
    }
    
}
