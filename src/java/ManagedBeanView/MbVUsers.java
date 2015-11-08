/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeanView;

import Clases.Encrypt;
import Dao.DaoRole;
import Dao.DaoUsers;
import HibernateUtil.HibernateUtil;
import Pojo.Role;
import Pojo.Roleusr;
import Pojo.Users;
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
public class MbVUsers implements Serializable {

    /**
     * Creates a new instance of MbVUsers
     */
    private Users users;
    private String Contrasenia2;
    private int codRole;
    private String roleid;
    private Role role;
    private boolean estado;
    private Roleusr roleusr;
    private List<Role> listaRoles;
    Transaction transaction;
    Session session;

    public MbVUsers() {
        try {
            codRole = 0;
            session = HibernateUtil.getSessionFactory().openSession();
            users = new Users();
            role = new Role();
            roleusr = new Roleusr();
            this.estado = true;
            DaoRole daoRole = new DaoRole();
            listaRoles = daoRole.getActives(session);
        } catch (Exception ex) {
            Logger.getLogger(MbVUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void register() {
        this.session = null;
        this.transaction = null;
        try {
            if(!this.users.getUserpass().equals(this.Contrasenia2))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Las contraseñas no coenciden"));

                return;
            }
            
            DaoUsers daoUsers = new DaoUsers();
            
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            if(daoUsers.getByUsuario(this.session, this.users.getUserusu())!=null)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El usuario ya se encuentra registrado en el sistema"));

                return;
            }
            this.users.setUserpass(Encrypt.sha512(this.users.getUserpass()));
            if (estado) {
                this.users.setUserestado("AC");
            } else {
                this.users.setUserestado("IN");
            }
            codRole = Integer.parseInt(roleid);
            DaoRole daoRole = new DaoRole();
            role = daoRole.getByCode(session, codRole);
            roleusr.setRole(role);
            roleusr.setUsers(users);
            daoUsers.register(session, users, roleusr);
            transaction.commit();
            limpiar();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto:", "El registro fue realizado correctamente.");
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
//            DaoRole daoRole=new DaoRole();
            this.users.setUsercorreo(((Users) event.getObject()).getUsercorreo());
            this.users.setUserdir(((Users) event.getObject()).getUserdir());
            this.users.setUserdoc(((Users) event.getObject()).getUserdoc());
            this.users.setUserestado(((Users) event.getObject()).getUserestado());
            this.users.setUsername(((Users) event.getObject()).getUsername());
            this.users.setUserpass(Encrypt.sha512(((Users) event.getObject()).getUserpass()));
            this.users.setUserid(((Users) event.getObject()).getUserid());
            this.users.setUsertele(((Users) event.getObject()).getUsertele());
            this.users.setUserusu(((Users) event.getObject()).getUserusu());
            ((Users) event.getObject()).setUserpass(Encrypt.sha512(((Users) event.getObject()).getUserpass()));
            session = HibernateUtil.getSessionFactory().openSession();

            transaction = session.beginTransaction();
            DaoUsers daoUsers = new DaoUsers();
            daoUsers.update(session, users);
            transaction.commit();
            FacesMessage msg = new FacesMessage("Actualizado", "Actualizado con exito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            Logger.getLogger(MbVUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    public void limpiar() {
        this.users = new Users();
        this.role = new Role();
        this.estado = true;
        this.Contrasenia2 = null;
        this.codRole = 0;

    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getContrasenia2() {
        return Contrasenia2;
    }

    public void setContrasenia2(String Contrasenia2) {
        this.Contrasenia2 = Contrasenia2;
    }

    public int getCodRole() {
        return codRole;
    }

    public void setCodRole(int codRole) {
        this.codRole = codRole;
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

    public List<Role> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(List<Role> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

}