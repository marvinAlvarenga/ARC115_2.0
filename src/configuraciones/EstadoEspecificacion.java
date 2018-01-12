/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuraciones;

/**
 * Los estados de las especificaciones
 * @author Marvin
 */
public class EstadoEspecificacion {
    
    private static boolean especiRamEnHome = false;
    private static boolean especiRamEnCompo = false;
    private static boolean especiCacheEnHome = false;
    private static boolean especiCacheEnCompo = false;

    public static boolean isEspeciRamEnHome() {
        return especiRamEnHome;
    }

    public static void setEspeciRamEnHome(boolean especiRamEnHome) {
        EstadoEspecificacion.especiRamEnHome = especiRamEnHome;
    }

    public static boolean isEspeciRamEnCompo() {
        return especiRamEnCompo;
    }

    public static void setEspeciRamEnCompo(boolean especiRamEnCompo) {
        EstadoEspecificacion.especiRamEnCompo = especiRamEnCompo;
    }

    public static boolean isEspeciCacheEnHome() {
        return especiCacheEnHome;
    }

    public static void setEspeciCacheEnHome(boolean especiCacheEnHome) {
        EstadoEspecificacion.especiCacheEnHome = especiCacheEnHome;
    }

    public static boolean isEspeciCacheEnCompo() {
        return especiCacheEnCompo;
    }

    public static void setEspeciCacheEnCompo(boolean especiCacheEnCompo) {
        EstadoEspecificacion.especiCacheEnCompo = especiCacheEnCompo;
    }
    
    
}
