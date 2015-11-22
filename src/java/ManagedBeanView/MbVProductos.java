/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeanView;

import Dao.DaoProducto;
import HibernateUtil.HibernateUtil;
import Pojo.Grupo;
import Pojo.Marca;
import Pojo.Producto;
import Pojo.Tipodescarga;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;

/**
 *
 * @author J-MeN
 */
@ManagedBean
@RequestScoped
public class MbVProductos {
 private Producto producto;
    private String codMarca;
    private String codGrupo;
    private String codTipoDescarga;
    private List<Marca> listaMarca;
    private List<Grupo> listaGrupo;
    private List<Tipodescarga> listaTipodescarga;
    Session session;
    Transaction transaction;

    public MbVProductos() {
        try {
            DaoProducto daoProducto = new DaoProducto();
            session = HibernateUtil.getSessionFactory().openSession();
            producto = new Producto();
            listaGrupo=daoProducto.getGrupos(session);
            listaMarca=daoProducto.getMarcas(session);
            listaTipodescarga=daoProducto.getTipodescargas(session);
        }  catch (Exception ex) {
            if (this.transaction != null) {
                this.transaction.rollback();
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error:", "Por favor contacte con su administrador " + ex.getMessage()));
        } finally {
            if (session.isOpen()) {
                session.close();
            }

        }
    }

    public void register() {
        session = null;
        transaction = null;
        try {
            DaoProducto daoProducto = new DaoProducto();
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            if (daoProducto.getByCodigoBarra(session, producto.getProCodigoBarra()) != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El Código de barras ya se encuentra registrado por en el sistema"));
                return;
            }
            Marca marca;
            Grupo grupo;
            Tipodescarga tipodescarga;
            marca = daoProducto.getByCodigoMarca(session, Integer.parseInt(codMarca));
            grupo = daoProducto.getByCodigoGrupo(session, Integer.parseInt(codGrupo));
            tipodescarga = daoProducto.getByCodigoDescarga(session, Integer.parseInt(codTipoDescarga));
            producto.setGrupo(grupo);
            producto.setMarca(marca);
            producto.setTipodescarga(tipodescarga);
            daoProducto.register(session, producto);
            transaction.commit();
            producto = new Producto();
            codGrupo = null;
            codMarca = null;
            codTipoDescarga = null;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto:", "El registro fue realizado correctamente.");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } catch (Exception ex) {
            if (this.transaction != null) {
                this.transaction.rollback();
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error:", "Por favor contacte con su administrador " + ex.getMessage()));
        } finally {
            if (session.isOpen()) {
                session.close();
            }

        }
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getCodMarca() {
        return codMarca;
    }

    public void setCodMarca(String codMarca) {
        this.codMarca = codMarca;
    }

    public String getCodGrupo() {
        return codGrupo;
    }

    public void setCodGrupo(String codGrupo) {
        this.codGrupo = codGrupo;
    }

    public String getCodTipoDescarga() {
        return codTipoDescarga;
    }

    public void setCodTipoDescarga(String codTipoDescarga) {
        this.codTipoDescarga = codTipoDescarga;
    }

    public List<Marca> getListaMarca() {
        return listaMarca;
    }

    public void setListaMarca(List<Marca> listaMarca) {
        this.listaMarca = listaMarca;
    }

    public List<Grupo> getListaGrupo() {
        return listaGrupo;
    }

    public void setListaGrupo(List<Grupo> listaGrupo) {
        this.listaGrupo = listaGrupo;
    }

    public List<Tipodescarga> getListaTipodescarga() {
        return listaTipodescarga;
    }

    public void setListaTipodescarga(List<Tipodescarga> listaTipodescarga) {
        this.listaTipodescarga = listaTipodescarga;
    }
    
    
}
