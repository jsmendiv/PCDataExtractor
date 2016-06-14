/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informacion;

import conex.ConexBD;
import datos.informacionCod;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author martha rivera
 */
@WebService(serviceName = "infoWS")
public class infoWS {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "nombreEquipo")
    public String nombreEquipo(@WebParam(name = "nombEquipo") String nombEquipo) {
        //Manda a llamar a la clase InformacionCod
        informacionCod infos = new informacionCod();

        String resp = "";//damos valor String "" a la respuesta que obtendremos de obtener nombre del equipo

        try {
            resp = infos.getNombreEquipo(); //Hacemos llamado del metodo de informacionCod para obtener el nombre del equipo
        } catch (UnknownHostException ex) {
            Logger.getLogger(infoWS.class.getName()).log(Level.SEVERE, null, ex);
        }

        writefile("Nombre del Equipo : " + resp); //la respuesta de obtener nombre del equipo se escribira en un txt
        return resp; //nos devuellve la respuesta obtenida del cliente

    }


    public void writefile(String Write) {
//Metodo para guardar en un txt toda la informacion recibida del cliente
        
        try {
            BufferedWriter Fescribe = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("C://Users//martha1//Desktop//ok//datos.txt", true), "utf-8"));//ruta para guardar la informacion
            //Escribe en el fichero la cadena que recibe la función 
            //el string "\r\n" significa salto de linea
            Fescribe.write(Write + "\r\n"); //se escriben todos los datos recibidos del cliente para poder guardar

            //Cierra el flujo de escritura  
            Fescribe.close();
        } catch (Exception ex) {
            //Captura un posible error le imprime en pantalla   
            System.out.println(ex.getMessage());
        }
    }

    @WebMethod(operationName = "SistemaOperativo")

    /**
     * Web service operation
     *
     * @param So
     * @return
     */
    public String SistemaOperativo(@WebParam(name = "So") String So) {
        informacionCod info = new informacionCod();
        String respuesta = info.getSo();
        info.getSo();
        writefile("Sistema Operativo :" + respuesta); //se guarda en el txt 

        return respuesta;

    }

    /**
     * Web service operation
     *
     * @param ip
     * @return
     */
    @WebMethod(operationName = "ip")
    public String ip(@WebParam(name = "ip") String ip) {
        informacionCod info = new informacionCod();
        String resp = "";
        try {
            resp = info.getIp();
        } catch (UnknownHostException ex) {
            Logger.getLogger(infoWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        writefile("Direccion Ip : " + resp);

        return resp;

    }

    /**
     * Web service operation
     *
     * @param direFisica
     * @return
     */
    @WebMethod(operationName = "DireccionFisica")
    public String DireccionFisica(@WebParam(name = "direFisica") String direFisica
    ) {
        informacionCod info = new informacionCod();
        String respuest = "";
        try {
            respuest = info.getobtenerDireccionFisica();
        } catch (IOException ex) {
            Logger.getLogger(infoWS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(infoWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        writefile("Direccion Fisica :" + respuest);

        return respuest;

    }

    /**
     * Web service operation
     *
     * @param numSerie
     * @return
     */
    @WebMethod(operationName = "numeroSerie")
    public String numeroSerie(@WebParam(name = "numSerie") String numSerie
    ) {
        informacionCod info = new informacionCod();
        String r = info.getNumeroSerie();

        writefile("Numero de Serie :" + r);
        return r;

    }
//public String serieMB(@WebParam(name="serieMB")String serieMB){
//    informacionCod info = new informacionCod();
//    String mb= info.getSerieMotherBoard();
//    writefile(mb);
//    return mb;
//}
   
    /**
     * Web service operation
     */
    @WebMethod(operationName = "horayfecha")
    public String horayfecha(@WebParam(name = "fecha") String fecha) {
        Date fecha1 = new Date(); //metodo para obtener la fecha y hora del servidor

        fecha = fecha1.toLocaleString();//retorna una cadena que contiene la hora y fecha del equipo
        writefile("*" + fecha); //guarda la hora y fecha en el txt

        return fecha;
    }

    private void conexion(String conex) {
        //Conexion a la base de datos para que muestre informacion recibida del cliente a la base de datos
       ConexBD cone= new ConexBD();//mandamos allamar la conexion
       String ip="";
       String nombEquipo="";
       String So="";
       String direFisica="";
       String numSerie="";
 
        try {
            //el parametro cone de la ConexionBD se guardaran en la base de datos
            cone.guardarInfo2(ip, nombEquipo, So, direFisica, numSerie);//Mandamos a llamar al metodo guardarInfo2 de la conexion
        } catch (SQLException ex) {
            Logger.getLogger(infoWS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/* 
    Se hace un metodo con todos los datos para poder mandarlo a llamar en la conexion de la base de datos, esto se hace
    para que no exita un conflicto a la hora de imprimir lo que el cliente nos envia al servidor. Si no se hace lanza un error.
    */
   public String guardarBD(
           @WebParam(name = "ip") String ip,
           @WebParam(name = "nombreEquipo") String nombEquipo, 
           @WebParam(name = "SistemaOperativo") String So, 
           @WebParam(name = "DireccionFisica") String direFisica,
           @WebParam(name = "numSerie") String numSerie, 
           @WebParam(name = "serieMB") String serieMB) {
        informacionCod info = new informacionCod();
        try {
            //mandamos a llamar los metodos de informacionCod para llenar campos en la base de datos
            String dato = info.getIp();
            String dato1 = info.getNombreEquipo();
            String dato2 = info.getSo();
            String dato3 = info.getobtenerDireccionFisica();
            String dato4 = info.getNumeroSerie();
            
            conexion(dato+dato1+dato2+dato3+dato4);//Guarda informacion en la bd
        } catch (UnknownHostException ex) {
            Logger.getLogger(infoWS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(infoWS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(infoWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        ;
        return null;

    }
}
