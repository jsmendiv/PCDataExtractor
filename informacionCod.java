/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author martha rivera
 */
public class informacionCod {
    //Funciones para obtener direccion fisica, nombre de la maquina, ip, numero de serie, sistema operativo
    public static final String getobtenerDireccionFisica() throws IOException, InterruptedException {
     
        boolean isWin = System.getProperty("os.name").toLowerCase().indexOf("win") != -1;
               Process aProc = Runtime.getRuntime().exec(
                isWin ? "ipconfig /all" : "/sbin/ifconfig -a"); //Comando para obtener la direccion fisica en cmd
        BufferedReader br = new BufferedReader(
                new InputStreamReader(new DataInputStream(aProc.getInputStream())));
        Pattern macAddressPattern = Pattern.compile(
                "((\\p{XDigit}\\p{XDigit}"
                + (isWin ? "-" : ":")
                + "){5}\\p{XDigit}\\p{XDigit})"); //compila la expresion
        for (String outputLine = ""; outputLine != null; outputLine = br.readLine()) {
            Matcher macAddressMatcher = macAddressPattern.matcher(outputLine);
            if (macAddressMatcher.find()) {
                return macAddressMatcher.group(0);
            }
        }
        aProc.destroy();
        aProc.waitFor();
        return null;
    }

    private static String sn = null;
//Obtener el numero de serie de la maquina

    public static final String getNumeroSerie() {

        if (sn != null) {
            return sn;
        }
//Representa el flujo de entrada y salida
        OutputStream os = null;
        InputStream is = null;
//metodo que devuelve el objeto de tiempo de ejecucion con la aplicacion
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        try {
            //Comando que se realiza en windows (CMD) para obtener numero de serie
            process = runtime.exec(new String[]{"wmic", "bios", "get", "serialnumber"});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        os = process.getOutputStream();
        is = process.getInputStream();

        try {
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //cuando se hace la el flujo de entrada se hace lectura de datos
        //para poder asi encontrar el SERIAL NUMBER con el equials 
        // y no envie otra informacion, para eso se hace el .equals (next) se pueda
        //leer lo que se pidio anteriormente con la serie de comandos 
        Scanner sc = new Scanner(is);
        try {
            while (sc.hasNext()) {
                String next = sc.next();
                
                if ("SerialNumber".equals(next)) {
                    sn = sc.next().trim();
                    break;
                }
            }
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (sn == null) {
            throw new RuntimeException("Cannot find computer SN");
        }

        return sn;
    }

     
//Obtener el numero de serie de la mother board
/*
    public static final String getSerieMotherBoard() {
        String result = "";
        try {
            
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_BaseBoard\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.SerialNumber \n"
                    + "    exit for  ' do the first cpu only! \n"
                    + "Next \n"; // comando para encontrar por CMD serie de la motherboard
            
            fw.flush();
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
        }
        return result.trim();
    }*/
//captura la entrada de los datos en consola de linux y añade
    //funcionalidades a otra entrada
    /* 
    cuando se crea el buffereadinputStream se crea una matriz intermedia interna
    */
    private static String linuxRunIfConfigCommand() throws IOException {
        Process p = Runtime.getRuntime().exec("ifconfig");
        InputStream stdoutStream = new BufferedInputStream(p.getInputStream());

        StringBuffer buffer = new StringBuffer();
        for (;;) {
            int c = stdoutStream.read();
            if (c == -1) {
                break;
            }
            buffer.append((char) c);
        }
        String outputText = buffer.toString();

        stdoutStream.close();

        return outputText;
    }

    /*Captura la entrada de los datos en consola de Windows y añade
   funcionalidades a otra entrada
    cuando se crea el buffereadinputStream se crea una matriz intermedia interna
    */
    private static String windowsRunIpConfigCommand() throws IOException {
        Process p = Runtime.getRuntime().exec("ipconfig /all");
        InputStream stdoutStream = new BufferedInputStream(p.getInputStream());

        StringBuffer buffer = new StringBuffer();
        for (;;) {
            int c = stdoutStream.read();
            if (c == -1) {
                break;
            }
            buffer.append((char) c);
        }
        String outputText = buffer.toString();

        stdoutStream.close();

        return outputText;
    }
//obtener el nombre del equipo

    public static String getNombreEquipo() throws UnknownHostException {
        /*
        Devuelve el servidor local para obtener el nombre del equipo
        */
        InetAddress addr = InetAddress.getLocalHost();
        String NombEquipo = addr.getHostName();
        return NombEquipo;

    }

//Obtener el sistema operativo
    public static String getSo() {
        //System.getProperty("os.name"); es para obtener el Sistema Operativo
        String so = System.getProperty("os.name");
        return so;

    }
//obtener la ip

    public static final String getIp() throws UnknownHostException {
//Obtiene la direccion de la ip
        String ip = InetAddress.getLocalHost().getHostAddress();
        return ip;

    }
}
