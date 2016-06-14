/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

/**
 *
 * @author martha rivera
 */
public class informacion {
    //atributos
    private String nombEquipo="";
        private String ip="";
        private String fecha="";
        private String so="";
        private String numSerie="";
        private String serieMB="";
        private String direFisica="";

    /**
     * @return the nombEquipo
     */
        //Se encapsulan los atributos
    public String getNombEquipo() {
        return nombEquipo;
    }

    /**
     * @param nombEquipo the nombEquipo to set
     */
    public void setNombEquipo(String nombEquipo) {
        this.nombEquipo = nombEquipo;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the so
     */
    public String getSo() {
        return so;
    }

    /**
     * @param so the so to set
     */
    public void setSo(String so) {
        this.so = so;
    }

    /**
     * @return the numSerie
     */
    public String getNumSerie() {
        return numSerie;
    }

    /**
     * @param numSerie the numSerie to set
     */
    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    /**
     * @return the serieMB
     */
    public String getSerieMB() {
        return serieMB;
    }

    /**
     * @param serieMB the serieMB to set
     */
    public void setSerieMB(String serieMB) {
        this.serieMB = serieMB;
    }

    /**
     * @return the direFisica
     */
    public String getDireFisica() {
        return direFisica;
    }

    /**
     * @param direFisica the direFisica to set
     */
    public void setDireFisica(String direFisica) {
        this.direFisica = direFisica;
    }
}
