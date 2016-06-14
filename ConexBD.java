/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conex;

import datos.informacionCod;
import informacion.infoWS;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author martha rivera
 */
public class ConexBD {

    String servidor = "localhost"; //nombre de la instancia de mi servidor sql
    String puerto = "1433";//puerto que utiliza sql para comunicar
    String user = "sa";//usuario creado en la base de datos
    String password = "telmex123";//contraseña de usuario de la bd
    String BaseDatos = "infoWs";//nombre de mi base de datos
    String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";//driver
    Statement stm = null;
    ResultSet rs = null;
    Connection con = null;

    public void ConexBD() {

        //instanciar la conexion a la base de datos de SQL SERVER
        try {
            Class.forName(driver); //Cargar el driver
            //Conexion URL
            String url = "jdbc:sqlserver://" + servidor + ":"
                    + puerto + ";" + "databaseName="
                    + BaseDatos + ";user="
                    + user + ";" + ";password="
                    + password + ";";
            con = DriverManager.getConnection(url);
            stm = con.createStatement();

        } catch (SQLException e) {
            System.out.println("SQL Exception:" + e.toString());

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                }
            }
        }

    }

    public void cierraconexion() {

        try {

            if (con != null) {
                con.close();
            }
            con = null;

        } catch (Exception ex) {

        }
    }
//Guarda la informacion recibida del cliente y hace conexion a nuestra base de datos
    public void guardarInfo2(String ip, String nombEquipo, String So, String direFisica, String numSerie) throws SQLException {
        try {
            Class.forName(driver);

            String url = "jdbc:sqlserver://" + servidor + ":"
                    + puerto + ";" + "databaseName="
                    + BaseDatos + ";user="
                    + user + ";" + ";password="
                    + password + ";";
            con = DriverManager.getConnection(url);
            stm = con.createStatement();
            //Mandamos a llamar la clase informacionCod para poder darle valores a los campos de nuestra bd
            informacionCod info = new informacionCod();
            String i = info.getIp();
            ip = i;
            String ne = info.getNombreEquipo();
            nombEquipo = ne;
            String s = info.getSo();
            So = s;
            String df = info.getobtenerDireccionFisica();
            direFisica = df;
            String ns = info.getNumeroSerie();
            numSerie = ns;
            //se agrega el query de insertar informacion a la bd con los campos vacios para asignarles 
            PreparedStatement pst = con.prepareStatement("INSERT INTO informacion(ip,nombre,sistema_operativo,"
                    + "direccion_fisica,numero_serie)"
                    + " VALUES(?,?,?,?,?)");

            pst.setString(1, ip);
            pst.setString(2, nombEquipo);
            pst.setString(3, So);
            pst.setString(4, direFisica);
            pst.setString(5, numSerie);

            pst.executeUpdate();
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(ConexBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ConexBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConexBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ConexBD.class.getName()).log(Level.SEVERE, null, ex);

        }

    }
}
