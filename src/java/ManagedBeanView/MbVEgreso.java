/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeanView;

import Clases.Imagen;
import Dao.DaoEgreso;
import Dao.DaoProducto;
import HibernateUtil.HibernateUtil;
import Pojo.Grupo;
import Pojo.Marca;
import Pojo.Producto;
import Pojo.Tipodescarga;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author jhonny.villa
 */
@ManagedBean
@ViewScoped
public class MbVEgreso implements Serializable{

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
    private Producto productoSeleccionado;
    private Producto productoSeleccionado2;
    private UploadedFile file;
    private List<Tipodescarga> listaTipodescarga;

    private String valorCodigoBarras;

    public MbVEgreso() {
        try {
            this.producto = new Producto();
            this.listaVentaDetalle = new ArrayList<>();
            this.productoSeleccionado = new Producto();
            this.listaProducto = getAllProducto();
            this.listaMarcas = getAllMarcas();
            this.listaGrupos = getAllGrupos();
            DaoProducto daoProducto = new DaoProducto();
            session = HibernateUtil.getSessionFactory().openSession();
            listaTipodescarga = daoProducto.getTipodescargas(session);
            this.cantidad = 1;
        } catch (Exception ex) {
            Logger.getLogger(MbVEgreso.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            if (cantidad < 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cantidad debe tener un valor"));
                RequestContext.getCurrentInstance().update("frmRealizarVentas:mensajeGeneral");
                return;
            }
            boolean existe = false;
            for (Producto next : listaVentaDetalle) {
                if (next.getProCodigoBarra().equals(productosel.getProCodigoBarra())) {
                    existe = true;
                }
            }
            if (existe) {
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
                productosel.setProStockMaximo(cantidad);
                this.listaVentaDetalle.add(productosel);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Producto agregado"));

            RequestContext.getCurrentInstance().update("frmRealizarVentas:tablaListaProductosVenta");
            RequestContext.getCurrentInstance().update("frmRealizarVentas:mensajeGeneral");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
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
                boolean existe = false;
                for (Producto next : listaVentaDetalle) {
                    if (next.getProCodigoBarra().equals(producto.getProCodigoBarra())) {
                        existe = true;
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
                    producto.setProStockMaximo(cantidad);
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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Producto retirado de la lista de venta"));

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

    public void actualizarImagen() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            DaoEgreso daoEgreso = new DaoEgreso();

            this.transaction = this.session.beginTransaction();

            productoSeleccionado.setProImagen(productoSeleccionado.getProCodigoBarra() + "." + file.getContentType().split("/")[1]);
            daoEgreso.update(session, productoSeleccionado);
            if (!(this.file.getFileName().toLowerCase().endsWith(".png") || this.file.getFileName().toLowerCase().endsWith(".gif") || this.file.getFileName().toLowerCase().endsWith(".jpg") || this.file.getFileName().toLowerCase().endsWith(".jpeg"))) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Tipo de imagen no admitida"));
                return;
            }

            if (this.file.getSize() > 0) {
                Imagen.copyFile(productoSeleccionado.getProCodigoBarra() + "." + file.getContentType().split("/")[1], file.getInputstream(), "imgproductos");
            }

            this.transaction.commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Imagen actualizada con exito"));
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

    public void validarCantidad(CellEditEvent event) {
        try {
            Object oldValue = event.getOldValue();
            Object newValue = event.getNewValue();
            productoSeleccionado2 = listaVentaDetalle.get(event.getRowIndex());
            if (productoSeleccionado2.getProStockBodega() - (Integer) newValue < 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "La cantidad no debe superar el stock en bodega"));
                listaVentaDetalle.get(event.getRowIndex()).setProStockMaximo((Integer) oldValue);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Cantidad actualizada"));
                listaVentaDetalle.get(event.getRowIndex()).setProStockBodega(listaVentaDetalle.get(event.getRowIndex()).getProStockBodega() - (Integer) newValue + (Integer) oldValue);
            }
            RequestContext.getCurrentInstance().update("frmRealizarVentas:mensajeGeneral");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        }
    }

    public void actualizarProducto() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            DaoEgreso daoEgreso = new DaoEgreso();

            this.transaction = this.session.beginTransaction();

            Producto productoEditado = productoSeleccionado;
            daoEgreso.update(session, productoEditado);

            this.transaction.commit();
            
//            RequestContext.getCurrentInstance().update("frmVerProductos:tblProductos");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Producto editado correctamente"));
        } catch (Exception ex) {
            if (this.transaction != null) {
                transaction.rollback();
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        } finally {
            if (this.session != null) {
                this.session.close();
            }
            this.listaProducto = getAllProducto();
        }
    }

    public void cargarUsuarioEditar() {

        try {

            RequestContext.getCurrentInstance().update("frmProducto");
            RequestContext.getCurrentInstance().execute("PF('proDialogEdit').show()");

        } catch (Exception ex) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador " + ex.getMessage()));
        }
    }

    public Producto getPro(Producto pro) {
        productoSeleccionado = pro;
        return pro;
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

    public Producto getProductoSeleccionado() {
        if(productoSeleccionado.getGrupo()==null){
            Grupo grupo=new Grupo();
            productoSeleccionado.setGrupo(grupo);
        }
        if(productoSeleccionado.getMarca()==null){
            Marca marca=new Marca();
            productoSeleccionado.setMarca(marca);
        }
        if(productoSeleccionado.getTipodescarga()==null){
            Tipodescarga tipodescarga=new Tipodescarga();
            productoSeleccionado.setTipodescarga(tipodescarga);
        }
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(Producto productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<Tipodescarga> getListaTipodescarga() {
        return listaTipodescarga;
    }

    public void setListaTipodescarga(List<Tipodescarga> listaTipodescarga) {
        this.listaTipodescarga = listaTipodescarga;
    }

}
