/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeanView;

import Dao.DaoRole;
import HibernateUtil.HibernateUtil;
import Pojo.Role;
import java.io.Serializable;
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
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author J-MeN
 */
@ManagedBean
@ViewScoped
public class MbVRole implements Serializable {

    /**
     * Creates a new instance of MbVRole
     */
    Transaction transaction;
    Session session;
    private Role role;
    private String rolname;
    private String roldesc;
    private String rolestado;
    private boolean estado;
    private List<Role> listRole;

    public MbVRole() {
        limpiar();
    }

    public void register() {
        try {
//            this.role=new Role();
            if (estado) {
                this.role.setRolestado("AC");
            } else {
                this.role.setRolestado("IN");
            }

            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            DaoRole daoRole = new DaoRole();
            daoRole.register(session, role);
            transaction.commit();
            limpiar();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Guardado", "Guardado con exito.");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } catch (Exception ex) {
            if(this.transaction!=null)
            {
                this.transaction.rollback();
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error:", "Por favor contacte con su administrador "+ex.getMessage()));
        } finally {
            if (session.isOpen()) {
                session.close();
            }

        }
    }

    public void update(RowEditEvent event) {
        try {
            this.role.setRolid(((Role) event.getObject()).getRolid());
            this.role.setRolname(((Role) event.getObject()).getRolname());
            this.role.setRolestado(((Role) event.getObject()).getRolestado());
            this.role.setRoldesc(((Role) event.getObject()).getRoldesc());
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            DaoRole daoRole = new DaoRole();
            daoRole.update(session, role);
            transaction.commit();
            FacesMessage msg = new FacesMessage("Actualizado", "Actualizado con exito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            if(this.transaction!=null)
            {
                this.transaction.rollback();
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error:", "Por favor contacte con su administrador "+ex.getMessage()));
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    public final void limpiar() {
        this.role = new Role();
        this.estado=true;
        DaoRole daoRole = new DaoRole();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            listRole = daoRole.getAll(session);
        } catch (Exception ex) {
            Logger.getLogger(MbVRole.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<Role> getListRole() {
        return listRole;
    }

    public void setListRole(List<Role> listRole) {
        this.listRole = listRole;
    }

    public String getRolname() {
        return rolname;
    }

    public void setRolname(String rolname) {
        this.rolname = rolname;
    }

    public String getRoldesc() {
        return roldesc;
    }

    public void setRoldesc(String roldesc) {
        this.roldesc = roldesc;
    }

    public String getRolestado() {
        return rolestado;
    }

    public void setRolestado(String rolestado) {
        this.rolestado = rolestado;
    }
    
}
