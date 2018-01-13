package configuraciones;

import utilidades.UnidadMedida;
import utilidades.Validador;

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
    private int tamañoPalabra; //Numero de Bytes para la palabra o -1 si no es direccionable por Palabra.
    private int maxDireccionable; // numero de bits de la direccion
    private long totalNumeroBloques;

    public EspecificacionRam(int capacidadMP, int unidadMedidaMP, int nivelDireccionable, int tamañoBloque, int tipoLlenado) {
        this.capacidadMP = capacidadMP;
        this.unidadMedidaMP = unidadMedidaMP;
        this.nivelDireccionable = nivelDireccionable;
        this.tamañoBloque = tamañoBloque;
        this.tipoLlenado = tipoLlenado;
    }
    
    public void realizarCalculos(){
        //calcular el maximo direccionable y el numero total de bloques de memoria
        long totalBytes = 0;
        switch(unidadMedidaMP){
            case UnidadMedida.KILO_BYTE:
                totalBytes = capacidadMP * (long)Math.pow(2, 10);
            break;
            case UnidadMedida.MEGA_BYTE:
                totalBytes = capacidadMP * (long)Math.pow(2, 20);
            break;
            case UnidadMedida.GIGA_BYTE:
                totalBytes = capacidadMP * (long)Math.pow(2, 30);
        }
        this.maxDireccionable = Validador.esPotenciaDeDos(String.valueOf(totalBytes));
        
        long totalBloques = 0;
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

    public long getTotalNumeroBloques() {
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

    public int getUnidadMedidaMP() {
        return unidadMedidaMP;
    }

    public void setUnidadMedidaMP(int unidadMedidaMP) {
        this.unidadMedidaMP = unidadMedidaMP;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.capacidadMP;
        hash = 97 * hash + this.unidadMedidaMP;
        hash = 97 * hash + this.nivelDireccionable;
        hash = 97 * hash + this.tamañoBloque;
        hash = 97 * hash + this.tipoLlenado;
        hash = 97 * hash + this.tamañoPalabra;
        hash = 97 * hash + this.maxDireccionable;
        hash = (int) (97 * hash + this.totalNumeroBloques);
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
        final EspecificacionRam other = (EspecificacionRam) obj;
        if (this.capacidadMP != other.capacidadMP) {
            return false;
        }
        if (this.unidadMedidaMP != other.unidadMedidaMP) {
            return false;
        }
        if (this.nivelDireccionable != other.nivelDireccionable) {
            return false;
        }
        if (this.tamañoBloque != other.tamañoBloque) {
            return false;
        }
        if (this.tipoLlenado != other.tipoLlenado) {
            return false;
        }
        if (this.tamañoPalabra != other.tamañoPalabra) {
            return false;
        }
        if (this.maxDireccionable != other.maxDireccionable) {
            return false;
        }
        if (this.totalNumeroBloques != other.totalNumeroBloques) {
            return false;
        }
        return true;
    }
    
}
