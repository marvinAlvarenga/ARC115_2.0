package configuraciones;

import utilidades.UnidadMedida;
import utilidades.Validador;

/**
 * Representa las especificaciones de la RAM configuradas por el usuario
 * @author Marvin
 */
public class EspecificacionRam {
    
    private boolean especificacionRamAplicada;
    private int capacidadMP;
    private int unidadMedidaMP;
    private int nivelDireccionable;
    private int tamañoBloque;
    private int tipoLlenado;
    private int tamañoPalabra; //Numero de Bytes para la palabra o -1 si no es direccionable por Palabra.
    private int maxDireccionable; // numero de bits de la direccion
    private int totalNumeroBloques;

    public EspecificacionRam(int capacidadMP, int unidadMedidaMP, int nivelDireccionable, int tamañoBloque, int tipoLlenado) {
        this.capacidadMP = capacidadMP;
        this.unidadMedidaMP = unidadMedidaMP;
        this.nivelDireccionable = nivelDireccionable;
        this.tamañoBloque = tamañoBloque;
        this.tipoLlenado = tipoLlenado;
        this.especificacionRamAplicada = false;
    }
    
    public void realizarCalculos(){
        //calcular el maximo direccionable y el numero total de bloques de memoria
        int totalBytes = 0;
        switch(unidadMedidaMP){
            case UnidadMedida.KILO_BYTE:
                totalBytes = capacidadMP * (int)Math.pow(2, 10);
            break;
            case UnidadMedida.MEGA_BYTE:
                totalBytes = capacidadMP * (int)Math.pow(2, 20);
            break;
            case UnidadMedida.GIGA_BYTE:
                totalBytes = capacidadMP * (int)Math.pow(2, 30);
        }
        this.maxDireccionable = Validador.esPotenciaDeDos(String.valueOf(totalBytes));
        
        int totalBloques = 0;
        switch(nivelDireccionable){
            case UnidadMedida.BYTE:
                if(tamañoBloque!=0)
                    totalBloques = totalBytes / tamañoBloque;
            break;
            case UnidadMedida.PALABRA:
                if(tamañoPalabra!=0 && tamañoBloque!=0)
                    totalBloques = (totalBytes/tamañoPalabra) / tamañoBloque;
        }
        this.totalNumeroBloques = totalBloques;
    }

    public int getTamañoPalabra() {
        return tamañoPalabra;
    }

    public void setTamañoPalabra(int tamañoPalabra) {
        this.tamañoPalabra = tamañoPalabra;
    }

    public int getCapacidadMP() {
        return capacidadMP;
    }

    public void setCapacidadMP(int capacidadMP) {
        this.capacidadMP = capacidadMP;
    }

    public int getNivelDireccionable() {
        return nivelDireccionable;
    }

    public void setNivelDireccionable(int nivelDireccionable) {
        this.nivelDireccionable = nivelDireccionable;
    }

    public int getTamañoBloque() {
        return tamañoBloque;
    }

    public void setTamañoBloque(int tamañoBloque) {
        this.tamañoBloque = tamañoBloque;
    }

    public int getTipoLlenado() {
        return tipoLlenado;
    }

    public void setTipoLlenado(int tipoLlenado) {
        this.tipoLlenado = tipoLlenado;
    }

    public int getTotalNumeroBloques() {
        return totalNumeroBloques;
    }

    public void setTotalNumeroBloques(int totalNumeroBloques) {
        this.totalNumeroBloques = totalNumeroBloques;
    }

    public int getMaxDireccionable() {
        return maxDireccionable;
    }

    public void setMaxDireccionable(int maxDireccionable) {
        this.maxDireccionable = maxDireccionable;
    }

    public boolean isEspecificacionRamAplicada() {
        return especificacionRamAplicada;
    }

    public void setEspecificacionRamAplicada(boolean especificacionRamAplicada) {
        this.especificacionRamAplicada = especificacionRamAplicada;
    }

    public int getUnidadMedidaMP() {
        return unidadMedidaMP;
    }

    public void setUnidadMedidaMP(int unidadMedidaMP) {
        this.unidadMedidaMP = unidadMedidaMP;
    }
    
}
