/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuraciones;

import cache.UtilCache;
import java.util.Objects;
import utilidades.UnidadMedida;
import utilidades.Validador;

/**
 * Representa las especificaciones de la CACHE configuradas por el usuario
 *
 * @author Marvin
 */
public class EspecificacionCache {

    private int capacidadCache;
    private int unidadMedidaCache;
    private int funcionCorrespondencia;
    private int algoReemplazo;
    private EspecificacionRam ram;
    private long numTotalLineas;
    private int cantidadLineasPorConjunto; //Para la asociativa por conjunto
    private long cantidadDeConjuntos;//Para la asociativa por conjunto

    private int formatPalabra;
    private int formatLinea;
    private int formatEtiqueta;
    private int formatConjunto;

    public EspecificacionCache(int capacidadCache, int unidadMedidaCache, int funcionCorrespondencia, int algoReemplazo, EspecificacionRam ram) {
        this.capacidadCache = capacidadCache;
        this.unidadMedidaCache = unidadMedidaCache;
        this.funcionCorrespondencia = funcionCorrespondencia;
        this.algoReemplazo = algoReemplazo;
        this.ram = ram;
    }

    public void realizarCalculos() {
        // calcular el numero total de lineas
        long totalBytes = 0;
        switch (unidadMedidaCache) {
            case UnidadMedida.KILO_BYTE:
                totalBytes = capacidadCache * (int) Math.pow(2, 10);
                break;
            case UnidadMedida.MEGA_BYTE:
                totalBytes = capacidadCache * (int) Math.pow(2, 20);
                break;
            case UnidadMedida.GIGA_BYTE:
                totalBytes = capacidadCache * (int) Math.pow(2, 30);
        }

        long totalLineas = 0;
        switch (ram.getNivelDireccionable()) {
            case UnidadMedida.BYTE:
                if (ram.getTamañoBloque() != 0) {
                    totalLineas = totalBytes / ram.getTamañoBloque();
                }
                break;
            case UnidadMedida.PALABRA:
                if (ram.getTamañoBloque() != 0 && ram.getTamañoPalabra() != 0) {
                    totalLineas = totalBytes / (ram.getTamañoBloque() * ram.getTamañoPalabra());
                }
        }
        this.numTotalLineas = totalLineas;

        long totalConjuntos = 0;
        if (funcionCorrespondencia == UtilCache.POR_CONJUNTO) {
            if (cantidadLineasPorConjunto != 0) {
                totalConjuntos = numTotalLineas / cantidadLineasPorConjunto;
                this.cantidadDeConjuntos = totalConjuntos;
            }
        }
        establecerFormato(); //Establecer la cantidad de bits para el formato
    }

    private void establecerFormato() {
        //Establecer la cantidad de bits para la palabra
        int tamBloque = ram.getTamañoBloque();
        if (tamBloque != 0) {
            formatPalabra = Validador.esPotenciaDeDos(String.valueOf(tamBloque));
        }
        switch (funcionCorrespondencia) {
            case UtilCache.DIRECTA:
                if (numTotalLineas != 0) {
                    formatLinea = Validador.esPotenciaDeDos(String.valueOf(numTotalLineas));
                    formatEtiqueta = ram.getMaxDireccionable() - formatLinea - formatPalabra;
                }
                break;
            case UtilCache.ASOCIATIVA:
                formatEtiqueta = ram.getMaxDireccionable() - formatPalabra;
                break;
            case UtilCache.POR_CONJUNTO:
                if (cantidadDeConjuntos != 0) {
                    formatConjunto = Validador.esPotenciaDeDos(String.valueOf(cantidadDeConjuntos));
                    formatEtiqueta = ram.getMaxDireccionable() - formatConjunto - formatPalabra;
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

    public long getNumTotalLineas() {
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

    public long getCantidadDeConjuntos() {
        return cantidadDeConjuntos;
    }

    public void setCantidadDeConjuntos(int cantidadDeConjuntos) {
        this.cantidadDeConjuntos = cantidadDeConjuntos;
    }

    public int getFormatPalabra() {
        return formatPalabra;
    }

    public void setFormatPalabra(int formatPalabra) {
        this.formatPalabra = formatPalabra;
    }

    public int getFormatLinea() {
        return formatLinea;
    }

    public void setFormatLinea(int formatLinea) {
        this.formatLinea = formatLinea;
    }

    public int getFormatEtiqueta() {
        return formatEtiqueta;
    }

    public void setFormatEtiqueta(int formatEtiqueta) {
        this.formatEtiqueta = formatEtiqueta;
    }

    public int getFormatConjunto() {
        return formatConjunto;
    }

    public void setFormatConjunto(int formatConjunto) {
        this.formatConjunto = formatConjunto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.capacidadCache;
        hash = 89 * hash + this.unidadMedidaCache;
        hash = 89 * hash + this.funcionCorrespondencia;
        hash = 89 * hash + this.algoReemplazo;
        hash = 89 * hash + Objects.hashCode(this.ram);
        hash = (int) (89 * hash + this.numTotalLineas);
        hash = 89 * hash + this.cantidadLineasPorConjunto;
        hash = (int) (89 * hash + this.cantidadDeConjuntos);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EspecificacionCache other = (EspecificacionCache) obj;
        if (this.capacidadCache != other.capacidadCache) {
            return false;
        }
        if (this.unidadMedidaCache != other.unidadMedidaCache) {
            return false;
        }
        if (this.funcionCorrespondencia != other.funcionCorrespondencia) {
            return false;
        }
        if (this.algoReemplazo != other.algoReemplazo) {
            return false;
        }
        if (this.numTotalLineas != other.numTotalLineas) {
            return false;
        }
        if (this.cantidadLineasPorConjunto != other.cantidadLineasPorConjunto) {
            return false;
        }
        if (this.cantidadDeConjuntos != other.cantidadDeConjuntos) {
            return false;
        }
        if (!Objects.equals(this.ram, other.ram)) {
            return false;
        }
        return true;
    }

}
