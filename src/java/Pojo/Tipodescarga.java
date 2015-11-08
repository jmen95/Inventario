package Pojo;
// Generated 2/11/2015 09:58:29 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Tipodescarga generated by hbm2java
 */
public class Tipodescarga  implements java.io.Serializable {


     private int tideCodigo;
     private String tideNombre;
     private String tideDescripcion;
     private Set productos = new HashSet(0);

    public Tipodescarga() {
    }

	
    public Tipodescarga(int tideCodigo, String tideNombre) {
        this.tideCodigo = tideCodigo;
        this.tideNombre = tideNombre;
    }
    public Tipodescarga(int tideCodigo, String tideNombre, String tideDescripcion, Set productos) {
       this.tideCodigo = tideCodigo;
       this.tideNombre = tideNombre;
       this.tideDescripcion = tideDescripcion;
       this.productos = productos;
    }
   
    public int getTideCodigo() {
        return this.tideCodigo;
    }
    
    public void setTideCodigo(int tideCodigo) {
        this.tideCodigo = tideCodigo;
    }
    public String getTideNombre() {
        return this.tideNombre;
    }
    
    public void setTideNombre(String tideNombre) {
        this.tideNombre = tideNombre;
    }
    public String getTideDescripcion() {
        return this.tideDescripcion;
    }
    
    public void setTideDescripcion(String tideDescripcion) {
        this.tideDescripcion = tideDescripcion;
    }
    public Set getProductos() {
        return this.productos;
    }
    
    public void setProductos(Set productos) {
        this.productos = productos;
    }




}

