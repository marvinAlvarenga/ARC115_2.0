package cpu;

/**
 * Constantes para identificar los diferentes metodos de direccionamiento
 * @author Marvin
 */
public class UtilDireccionamiento {
    
    public static final int DIRECTO = 0;
    public static final int INDIRECTO_REGISTRO = 1;
    public static final int DESPLAZAMIENTO_RELATIVO = 2;
    public static final int REGISTRO_BASE = 3;
    public static final int INDEXADO = 4;
    public static final int LECTURA = 0;
    public static final int ESCRITURA = 1;
    
    /**
     * Devuelve un string con la direccion fisica en hexadecimal
     * @param p La peticion a procesar
     * @return La direccion fisica calculada
     */
    public static String generarDireccionFisica(Peticion p){
        String direccion = null;
        Long dirBase;
        Long desplaza;
        
        switch(p.getMetodoDireccion()){
            case DIRECTO:
                direccion = p.getCampoDireccion();
                break;
            case INDIRECTO_REGISTRO:
                direccion = p.getCampoRegistro();
                break;
            case DESPLAZAMIENTO_RELATIVO: case REGISTRO_BASE:
                dirBase = Long.parseLong(p.getCampoRegistro(), 16);
                desplaza = Long.parseLong(p.getCampoDireccion(), 16);
                direccion = Long.toHexString(dirBase + desplaza);
                break;
            case INDEXADO:
                dirBase = Long.parseLong(p.getCampoDireccion(), 16);
                desplaza = Long.parseLong(p.getCampoRegistro(), 16);
                direccion = Long.toHexString(dirBase + desplaza);
                
        }
        
        return direccion;
    }
    
    /**
     * Convertir a binario una direccion
     * @param dirHexa La direccion en hexadecimal
     * @param maxDireccionable El maximo direccionable
     * @return La direccion en binario
     */
    public static String direccionEnBinario(String dirHexa, int maxDireccionable){
        long decimal = Long.parseLong(dirHexa, 16);
        String binario = Long.toBinaryString(decimal);
        int relleno = maxDireccionable - binario.length();
        for(int i=0;i<relleno;i++){
            binario = "0" + binario;
        }
        return binario;
    }
}
