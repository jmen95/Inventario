/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeanView;

import Dao.DaoEgreso;
import HibernateUtil.HibernateUtil;
import Pojo.Grupo;
import Pojo.Marca;
import Pojo.Producto;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;

/**
 *
 * @author jhonny.villa
 */
@ManagedBean
@ViewScoped
public class MbVEgreso {

    /**
     * Creates a new instance of MbVEgreso
     */
    /**
     * Creates a new instance of MbRVenta
     */
    Session session;
    Transaction transaction;

    private Producto producto;
    private List<Producto> listaProducto;
    private List<Producto> listaProductof;
    private List<Producto> listaVentaDetalle;
    private List<Marca> listaMarcas;
    private List<Grupo> listaGrupos;
    private int cantidad;

    private String valorCodigoBarras;

    public MbVEgreso() {
        this.producto = new Producto();
        this.listaVentaDetalle = new ArrayList<>();
        this.listaProducto = getAllProducto();
        this.listaMarcas = getAllMarcas();
        this.listaGrupos = getAllGrupos();
    }

    private List<Producto> getAllProducto() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            DaoEgreso daoEgreso = new DaoEgreso();

            return daoEgreso.getAll(this.session);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));

            return null;
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }
    private List<Marca> getAllMarcas() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            DaoEgreso daoEgreso = new DaoEgreso();

            return daoEgreso.getAllMarcas(this.session);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));

            return null;
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }
    private List<Grupo> getAllGrupos() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            DaoEgreso daoEgreso = new DaoEgreso();

            return daoEgreso.getAllGrupos(this.session);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));

            return null;
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    public void agregarListaVentaDetalle(Producto productosel) {
        this.session = null;
        this.transaction = null;
        if (valorCodigoBarras == null) {
            try {
                if (cantidad < 1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cantidad debe tener un valor"));
                    RequestContext.getCurrentInstance().update("frmRealizarVentas:mensajeGeneral");
                    return;
                }
                if (listaVentaDetalle.contains(productosel)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El producto ya fue agregado"));
                    RequestContext.getCurrentInstance().update("frmRealizarVentas:mensajeGeneral");
                    return;
                } else {
                    if ((productosel.getProStockBodega() - cantidad) < 0) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cantidad no debe superar el stock en bodega"));
                        RequestContext.getCurrentInstance().update("frmRealizarVentas:mensajeGeneral");
                        return;
                    }
                    productosel.setProStockBodega(productosel.getProStockBodega() - cantidad);
                    this.listaVentaDetalle.add(productosel);
                }

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Producto agregado"));

                RequestContext.getCurrentInstance().update("frmRealizarVentas:tablaListaProductosVenta");
                RequestContext.getCurrentInstance().update("frmRealizarVentas:mensajeGeneral");
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
            }
        }
    }

    public void agregarListaVentaDetallePorCodigoBarras() {
        this.session = null;
        this.transaction = null;

        try {
            if (this.valorCodigoBarras.equals("")) {
                return;
            }
            if (cantidad < 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cantidad debe tener un valor"));
                RequestContext.getCurrentInstance().update("frmRealizarVentas:mensajeGeneral");
                return;
            }

            this.session = HibernateUtil.getSessionFactory().openSession();

            DaoEgreso daoEgreso = new DaoEgreso();

            this.producto = daoEgreso.getByCodigoBarras(this.session, this.valorCodigoBarras);

            if (this.producto != null) {
                boolean existe=false;
                for (Producto next : listaVentaDetalle) {
                    if(next.getProCodigoBarra().equals(producto.getProCodigoBarra())){
                        existe=true;
                    }
                }
                if (existe) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El producto ya fue agregado"));
                    RequestContext.getCurrentInstance().update("frmRealizarVentas:mensajeGeneral");
                    return;
                } else {
                    if ((producto.getProStockBodega() - cantidad) < 0) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cantidad no debe superar el stock en bodega"));
                        RequestContext.getCurrentInstance().update("frmRealizarVentas:mensajeGeneral");
                        return;
                    }
                    producto.setProStockBodega(producto.getProStockBodega() - cantidad);
                    this.listaVentaDetalle.add(producto);
                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Producto agregado"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Código de barras invalido", "Producto no encontrado"));
            }

            this.valorCodigoBarras = null;

            RequestContext.getCurrentInstance().update("frmRealizarVentas:tablaListaProductosVenta");
            RequestContext.getCurrentInstance().update("frmRealizarVentas:mensajeGeneral");
            RequestContext.getCurrentInstance().update("frmRealizarVentas:txtAgregarPorCodigoBarras");
        } catch (Exception ex) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
            RequestContext.getCurrentInstance().update("frmRealizarVentas:mensajeGeneral");
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    public void retirarListaVentaDetalle(Producto productosel) {
        try {
            this.listaVentaDetalle.remove(productosel);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Correcto", "Producto retirado de la lista de venta"));

            RequestContext.getCurrentInstance().update("frmRealizarVentas:tablaListaProductosVenta");
            RequestContext.getCurrentInstance().update("frmRealizarVentas:mensajeGeneral");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        }
    }

    public void realizarVenta() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            DaoEgreso daoEgreso = new DaoEgreso();

            this.transaction = this.session.beginTransaction();

            for (Producto item : this.listaVentaDetalle) {
                daoEgreso.update(session, item);
            }

            this.transaction.commit();

            this.listaVentaDetalle = new ArrayList<>();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Descargue realizado correctamente"));
        } catch (Exception ex) {
            if (this.transaction != null) {
                transaction.rollback();
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<Producto> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public List<Producto> getListaVentaDetalle() {
        return listaVentaDetalle;
    }

    public void setListaVentaDetalle(List<Producto> listaVentaDetalle) {
        this.listaVentaDetalle = listaVentaDetalle;
    }

    public String getValorCodigoBarras() {
        return valorCodigoBarras;
    }

    public void setValorCodigoBarras(String valorCodigoBarras) {
        this.valorCodigoBarras = valorCodigoBarras;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public List<Marca> getListaMarcas() {
        return listaMarcas;
    }

    public void setListaMarcas(List<Marca> listaMarcas) {
        this.listaMarcas = listaMarcas;
    }

    public List<Grupo> getListaGrupos() {
        return listaGrupos;
    }

    public void setListaGrupos(List<Grupo> listaGrupos) {
        this.listaGrupos = listaGrupos;
    }

    public List<Producto> getListaProductof() {
        return listaProductof;
    }

    public void setListaProductof(List<Producto> listaProductof) {
        this.listaProductof = listaProductof;
    }
    
}
