/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ManagedBeanView;


import Dao.DaoProducto;
import HibernateUtil.HibernateUtil;
import Pojo.Producto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;

/**
 *
 * @author KevinArnold
 */
@Named(value = "mbVVenta")
@ViewScoped
public class MbVVenta implements Serializable{

    /**
     * Creates a new instance of MbRVenta
     */
    
    Session session;
    Transaction transaction;
    
    private Producto producto;
    private List<Producto> listaProducto;
//    private Tventa venta;
//    private List<Tventadetalle> listaVentaDetalle;
    
    private String valorCodigoBarras;
    
    public MbVVenta() 
    {
        this.venta=new Tventa();
        this.listaVentaDetalle=new ArrayList<>();
    }
    
    public List<Producto> getAllProducto()
    {
        this.session=null;
        this.transaction=null;
        
        try
        {
            this.session=HibernateUtil.getSessionFactory().openSession();
            
            DaoProducto daoProducto=new DaoProducto();
            
            this.transaction=this.session.beginTransaction();
            
            this.listaProducto=daoProducto.getAll(this.session);
            
            this.transaction.commit();
            
            return this.listaProducto;
        }
        catch(Exception ex)
        {
            if(this.transaction!=null)
            {
                transaction.rollback();
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
            
            return null;
        }
        finally
        {
            if(this.session!=null)
            {
                this.session.close();
            }
        }
    }
    
    public void agregarListaVentaDetalle(Integer idProducto)
    {
        this.session=null;
        this.transaction=null;
        
        try
        {
            this.session=HibernateUtil.getSessionFactory().openSession();
            
            DaoTProducto daoTProducto=new DaoTProducto();
            
            this.transaction=this.session.beginTransaction();
            
            this.producto=daoTProducto.getByIdProducto(this.session, idProducto);
            
            this.listaVentaDetalle.add(new Tventadetalle(null, null, this.producto.getCodigoBarras(), this.producto.getNombre(), this.producto.getPrecioVentaUnitario(), 0, new BigDecimal("0")));
            
            this.transaction.commit();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Producto agregado"));
            
            RequestContext.getCurrentInstance().update("frmRealizarVentas:tablaListaProductosVenta");
            RequestContext.getCurrentInstance().update("frmRealizarVentas:mensajeGeneral");
        }
        catch(Exception ex)
        {
            if(this.transaction!=null)
            {
                transaction.rollback();
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        }
        finally
        {
            if(this.session!=null)
            {
                this.session.close();
            }
        }
    }
    
    public void agregarListaVentaDetallePorCodigoBarras()
    {
        this.session=null;
        this.transaction=null;
        
        try
        {
            if(this.valorCodigoBarras.equals("")){return;}
            
            this.session=HibernateUtil.getSessionFactory().openSession();
            
            DaoTProducto daoTProducto=new DaoTProducto();
            
            this.transaction=this.session.beginTransaction();
            
            this.producto=daoTProducto.getByCodigoBarras(this.session, this.valorCodigoBarras);
            
            if(this.producto!=null)
            {
                this.listaVentaDetalle.add(new Tventadetalle(null, null, this.producto.getCodigoBarras(), this.producto.getNombre(), this.producto.getPrecioVentaUnitario(), 0, new BigDecimal("0")));

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Producto agregado"));
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Código de barras invalido", "Producto no encontrado"));
            }
            
            this.valorCodigoBarras="";
            
            this.transaction.commit();
            
            RequestContext.getCurrentInstance().update("frmRealizarVentas:tablaListaProductosVenta");
            RequestContext.getCurrentInstance().update("frmRealizarVentas:mensajeGeneral");
            RequestContext.getCurrentInstance().update("frmRealizarVentas:txtAgregarPorCodigoBarras");
        }
        catch(Exception ex)
        {
            if(this.transaction!=null)
            {
                transaction.rollback();
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        }
        finally
        {
            if(this.session!=null)
            {
                this.session.close();
            }
        }
    }
    
    public void retirarListaVentaDetalle(String codigoBarras)
    {        
        try
        {
            int i=0;
            
            for(Tventadetalle item : this.listaVentaDetalle)
            {
                if(item.getCodigoBarrasProducto().equals(codigoBarras))
                {
                    this.listaVentaDetalle.remove(i);
                    
                    break;
                }
                
                i++;
            }
            
            BigDecimal totalVenta=new BigDecimal("0");
            
            for(Tventadetalle item : this.listaVentaDetalle)
            {
                BigDecimal totalVentaPorProducto=item.getPrecioVentaUnitarioProducto().multiply(new BigDecimal(item.getCantidad()));
                
                item.setTotalPrecioVenta(totalVentaPorProducto);
                
                totalVenta=totalVenta.add(totalVentaPorProducto);
            }
            
            this.venta.setPrecioVentaTotal(totalVenta);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Correcto", "Producto retirado de la lista de venta"));
            
            RequestContext.getCurrentInstance().update("frmRealizarVentas:tablaListaProductosVenta");
            RequestContext.getCurrentInstance().update("frmRealizarVentas:panelFinalVenta");
            RequestContext.getCurrentInstance().update("frmRealizarVentas:mensajeGeneral");
        }
        catch(Exception ex)
        {            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        }
    }
    
    public void calcularCostos()
    {
        try
        {   
            BigDecimal totalVenta=new BigDecimal("0");
            
            for(Tventadetalle item : this.listaVentaDetalle)
            {
                BigDecimal totalVentaPorProducto=item.getPrecioVentaUnitarioProducto().multiply(new BigDecimal(item.getCantidad()));
                
                item.setTotalPrecioVenta(totalVentaPorProducto);
                
                totalVenta=totalVenta.add(totalVentaPorProducto);
            }
            
            this.venta.setPrecioVentaTotal(totalVenta);
            
            RequestContext.getCurrentInstance().update("frmRealizarVentas:tablaListaProductosVenta");
            RequestContext.getCurrentInstance().update("frmRealizarVentas:panelFinalVenta");
        }
        catch(Exception ex)
        {            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        }
    }
    
    public void realizarVenta()
    {
        this.session=null;
        this.transaction=null;
        
        try
        {
            this.session=HibernateUtil.getSessionFactory().openSession();
            
            DaoTProducto daoTProducto=new DaoTProducto();
            DaoTVenta daoTVenta=new DaoTVenta();
            DaoTVentaDetalle daoTVentaDetalle=new DaoTVentaDetalle();
            
            this.transaction=this.session.beginTransaction();
            
            daoTVenta.insert(this.session, this.venta);
            this.venta=daoTVenta.getUltimoRegistro(this.session);
            
            for(Tventadetalle item : this.listaVentaDetalle)
            {
                this.producto=daoTProducto.getByCodigoBarras(this.session, item.getCodigoBarrasProducto());
                item.setTventa(this.venta);
                item.setTproducto(this.producto);
                
                daoTVentaDetalle.insert(this.session, item);
            }
            
            this.transaction.commit();
            
            this.listaVentaDetalle=new ArrayList<>();
            this.venta=new Tventa();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Venta realizada correctamente"));
        }
        catch(Exception ex)
        {
            if(this.transaction!=null)
            {
                transaction.rollback();
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        }
        finally
        {
            if(this.session!=null)
            {
                this.session.close();
            }
        }
    }
    
    public Tproducto getProducto() {
        return producto;
    }

    public void setProducto(Tproducto producto) {
        this.producto = producto;
    }

    public List<Tproducto> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<Tproducto> listaProducto) {
        this.listaProducto = listaProducto;
    }
    
    public Tventa getVenta() {
        return venta;
    }

    public void setVenta(Tventa venta) {
        this.venta = venta;
    }
    
    public List<Tventadetalle> getListaVentaDetalle() {
        return listaVentaDetalle;
    }

    public void setListaVentaDetalle(List<Tventadetalle> listaVentaDetalle) {
        this.listaVentaDetalle = listaVentaDetalle;
    }
    
    public String getValorCodigoBarras() {
        return valorCodigoBarras;
    }

    public void setValorCodigoBarras(String valorCodigoBarras) {
        this.valorCodigoBarras = valorCodigoBarras;
    }
    
}
