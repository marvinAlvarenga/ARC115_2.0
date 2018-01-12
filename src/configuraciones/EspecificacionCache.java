/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuraciones;

import cache.UtilCache;
import utilidades.UnidadMedida;

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
    private int cantidadLineasPorConjunto; //Para la asociativa por conjunto
    private int cantidadDeConjuntos;//Para la asociativa por conjunto

    public EspecificacionCache(int capacidadCache, int unidadMedidaCache, int funcionCorrespondencia, int algoReemplazo, EspecificacionRam ram) {
        this.capacidadCache = capacidadCache;
        this.unidadMedidaCache = unidadMedidaCache;
        this.funcionCorrespondencia = funcionCorrespondencia;
        this.algoReemplazo = algoReemplazo;
        this.ram = ram;
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
        
        int totalConjuntos = 0;
        if(funcionCorrespondencia == UtilCache.POR_CONJUNTO){
            if(cantidadLineasPorConjunto!=0){
                totalConjuntos = numTotalLineas / cantidadLineasPorConjunto;
                this.cantidadDeConjuntos = totalConjuntos;
            }
        }
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

    public int getCantidadLineasPorConjunto() {
        return cantidadLineasPorConjunto;
    }

    public void setCantidadLineasPorConjunto(int cantidadLineasPorConjunto) {
        this.cantidadLineasPorConjunto = cantidadLineasPorConjunto;
    }

    public int getCantidadDeConjuntos() {
        return cantidadDeConjuntos;
    }

    public void setCantidadDeConjuntos(int cantidadDeConjuntos) {
        this.cantidadDeConjuntos = cantidadDeConjuntos;
    }
    
}
