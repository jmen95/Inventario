/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author XILENE
 */
public class Imagen {

    public static void copyFile(String fileName, InputStream in,String carpeta) {
        String ruta;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String relativeWebPath = "/resources/"+carpeta+"/";
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        ruta = servletContext.getRealPath(relativeWebPath);
        try {

            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(ruta + "\\" + fileName));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();
            ruta += "\\" + fileName;
            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
