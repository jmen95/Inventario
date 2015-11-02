/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeanView;

import Pojo.Role;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author J-MeN
 */
@ManagedBean
@ViewScoped
public class MbVRole {

    /**
     * Creates a new instance of MbVRole
     */ 
    Transaction transaction;
    Session session;
    Role role;
    boolean estado;
    public MbVRole() {
        role=new Role();
        limpiar();
    }
    public String register() {
        try {
            if (estado) {
                this.role.setRolestado("AC");
            } else {
                this.role.setRolestado("IN");
            }
            

            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            DaoPqrRoles daoPqrRoles = new DaoPqrRoles();
            daoPqrRoles.register(session, pqrRoles);
            transaction.commit();
            limpiar();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Guardado", "Guardado con exito.");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } catch (Exception ex) {
            Logger.getLogger(MbVPqrRoles.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (session.isOpen()) {
                session.close();
            }

        }
        return null;
    }

    public void update(RowEditEvent event) {
        try {
            this.pqrRoles.setRolid(((PqrRoles) event.getObject()).getRolid());
            this.pqrRoles.setRolname(((PqrRoles) event.getObject()).getRolname());
            this.pqrRoles.setState(((PqrRoles) event.getObject()).getState());
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            DaoPqrRoles daoPqrRoles = new DaoPqrRoles();
            daoPqrRoles.update(session, pqrRoles);
            transaction.commit();
            FacesMessage msg = new FacesMessage("Actualizado", "Actualizado con exito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            Logger.getLogger(MbVPqrRoles.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    public final void limpiar() {
        this.state = true;
        this.rolename = null;
        DaoPqrRoles daoPqrRoles = new DaoPqrRoles();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            listPqrRoles = daoPqrRoles.getAll(session);
        } catch (Exception ex) {
            Logger.getLogger(MbVPqrRoles.class.getName()).log(Level.SEVERE, null, ex);
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
    
}
