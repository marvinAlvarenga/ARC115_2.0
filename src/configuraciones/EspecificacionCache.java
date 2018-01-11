/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuraciones;

import utilidades.UnidadMedida;

/**
 * Representa las especificaciones de la CACHE configuradas por el usuario
 * @author Marvin
 */
public class EspecificacionCache {
    
    private boolean especificacionCacheAplicada;
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
        this.especificacionCacheAplicada = false;
    }
    
    public void realizarCalculos(){
        // calcular el numero total de lineas
        int totalBytes = 0;
        switch(unidadMedidaCache){
            case UnidadMedida.KILO_BYTE:
                totalBytes = capacidadCache * (int)Math.pow(2, 10);
            break;
            case UnidadMedida.MEGA_BYTE:
                totalBytes = capacidadCache * (int)Math.pow(2, 20);
            break;
            case UnidadMedida.GIGA_BYTE:
                totalBytes = capacidadCache * (int)Math.pow(2, 30);
        }
        
        int totalLineas = 0;
        switch(ram.getNivelDireccionable()){
            case UnidadMedida.BYTE:
                if(ram.getTamañoBloque()!=0)
                    totalLineas = totalBytes / ram.getTamañoBloque();
            break;
            case UnidadMedida.PALABRA:
                if(ram.getTamañoBloque()!=0 && ram.getTamañoPalabra()!=0)
                    totalLineas = totalBytes / (ram.getTamañoBloque()*ram.getTamañoPalabra());
        }
        this.numTotalLineas = totalLineas;
    }

    public boolean isEspecificacionCacheAplicada() {
        return especificacionCacheAplicada;
    }

    public void setEspecificacionCacheAplicada(boolean especificacionCacheAplicada) {
        this.especificacionCacheAplicada = especificacionCacheAplicada;
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
