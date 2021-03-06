package Pojo;
// Generated 21/11/2015 08:46:55 PM by Hibernate Tools 4.3.1

import java.util.Date;




/**
 * Movimientos generated by hbm2java
 */
public class Movimientos  implements java.io.Serializable {


     private int movCodigo;
     private Producto producto;
     private Users users;
     private String movAccion;
     private String movCantidad;
     private Date movFecha;

    public Movimientos() {
    }

	
    public Movimientos(int movCodigo) {
        this.movCodigo = movCodigo;
    }
    public Movimientos(int movCodigo, Producto producto, Users users, String movAccion, String movCantidad, Date movFecha) {
       this.movCodigo = movCodigo;
       this.producto = producto;
       this.users = users;
       this.movAccion = movAccion;
       this.movCantidad = movCantidad;
       this.movFecha = movFecha;
    }
   
    public int getMovCodigo() {
        return this.movCodigo;
    }
    
    public void setMovCodigo(int movCodigo) {
        this.movCodigo = movCodigo;
    }
    public Producto getProducto() {
        return this.producto;
    }
    
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    public String getMovAccion() {
        return this.movAccion;
    }
    
    public void setMovAccion(String movAccion) {
        this.movAccion = movAccion;
    }
    public String getMovCantidad() {
        return this.movCantidad;
    }
    
    public void setMovCantidad(String movCantidad) {
        this.movCantidad = movCantidad;
    }

    public Date getMovFecha() {
        return movFecha;
    }

    public void setMovFecha(Date movFecha) {
        this.movFecha = movFecha;
    }




}


