/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author JESÚS MENDOZA
 */
public class IndCincoMes {
    
    private String nombreProducto;
    private BigInteger cantidadProducto;

    public IndCincoMes() {
    }

    public IndCincoMes(String nombreProducto, BigInteger cantidadProducto) {
        this.nombreProducto = nombreProducto;
        this.cantidadProducto = cantidadProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public BigInteger getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(BigInteger cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }
    
    
    
}
