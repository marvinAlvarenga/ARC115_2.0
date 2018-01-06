package configuraciones;

/**
 * Representa las especificaciones de la RAM configuradas por el usuario
 * @author Marvin
 */
public class EspecificacionRam {
    
    private int capacidadMP;
    private int unidadMedidaMP;
    private int nivelDireccionable;
    private int tamañoBloque;
    private int tipoLlenado;
    private int totalNumeroBloques;
    private int maxDireccionable;

    public EspecificacionRam(int capacidadMP, int unidadMedidaMP, int nivelDireccionable, int tamañoBloque, int tipoLlenado) {
        this.capacidadMP = capacidadMP;
        this.unidadMedidaMP = unidadMedidaMP;
        this.nivelDireccionable = nivelDireccionable;
        this.tamañoBloque = tamañoBloque;
        this.tipoLlenado = tipoLlenado;
    }

    public int getCapacidadMP() {
        return capacidadMP;
    }

    public void setCapacidadMP(int capacidadMP) {
        this.capacidadMP = capacidadMP;
    }

    public int getUnidadMedida() {
        return unidadMedidaMP;
    }

    public void setUnidadMedida(int unidadMedidaMP) {
        this.unidadMedidaMP = unidadMedidaMP;
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
    
}
