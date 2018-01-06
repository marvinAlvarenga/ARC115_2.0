/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuraciones;

/**
 * Representa las especificaciones de la CACHE configuradas por el usuario
 * @author Marvin
 */
public class EspecificacionCache {
    
    private int capacidadCache;
    private int unidadMedidaCache;
    private int funcionCorrespondencia;
    private int algoReemplazo;
    private EspecificacionRam ram;
    private int numTotalLineas;

    public EspecificacionCache(int capacidadCache, int unidadMedidaCache, int funcionCorrespondencia, int algoReemplazo, EspecificacionRam ram) {
        this.capacidadCache = capacidadCache;
        this.unidadMedidaCache = unidadMedidaCache;
        this.funcionCorrespondencia = funcionCorrespondencia;
        this.algoReemplazo = algoReemplazo;
        this.ram = ram;
    }

    public int getCapacidadCache() {
        return capacidadCache;
    }

    public void setCapacidadCache(int capacidadCache) {
        this.capacidadCache = capacidadCache;
    }

    public int getUnidadMedidaCache() {
        return unidadMedidaCache;
    }

    public void setUnidadMedidaCache(int unidadMedidaCache) {
        this.unidadMedidaCache = unidadMedidaCache;
    }

    public int getFuncionCorrespondencia() {
        return funcionCorrespondencia;
    }

    public void setFuncionCorrespondencia(int funcionCorrespondencia) {
        this.funcionCorrespondencia = funcionCorrespondencia;
    }

    public int getAlgoReemplazo() {
        return algoReemplazo;
    }

    public void setAlgoReemplazo(int algoReemplazo) {
        this.algoReemplazo = algoReemplazo;
    }

    public EspecificacionRam getRam() {
        return ram;
    }

    public void setRam(EspecificacionRam ram) {
        this.ram = ram;
    }

    public int getNumTotalLineas() {
        return numTotalLineas;
    }

    public void setNumTotalLineas(int numTotalLineas) {
        this.numTotalLineas = numTotalLineas;
    }
    
}
