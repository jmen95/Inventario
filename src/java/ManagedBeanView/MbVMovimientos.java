/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeanView;

import Dao.DaoMovimientos;
import HibernateUtil.HibernateUtil;
import Pojo.Movimientos;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author JESÚS MENDOZA
 */
@ManagedBean
@ViewScoped
public class MbVMovimientos implements Serializable {

    /**
     * Creates a new instance of MbVMovimientos
     */
    private List<Movimientos> listMovimientosf;
    private List<Movimientos> listMovimientos;
    private List<String> listAcciones;

    private Session session;
    private Transaction transaction;

    public MbVMovimientos() {
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction=session.beginTransaction();
            DaoMovimientos daoMovimientos = new DaoMovimientos();
            this.listMovimientos = daoMovimientos.getMovimientos(session);
            this.listAcciones = daoMovimientos.getAcciones(session);
        } catch (Exception ex) {
            if(this.transaction!=null){
                this.transaction.rollback();
            }
            Logger.getLogger(MbVMovimientos.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(this.session.isOpen()){
                session.close();
            }
        }
    }

    public List<Movimientos> getListMovimientos() {
        return listMovimientos;
    }

    public void setListMovimientos(List<Movimientos> listMovimientos) {
        this.listMovimientos = listMovimientos;
    }

    public List<Movimientos> getListMovimientosf() {
        return listMovimientosf;
    }

    public void setListMovimientosf(List<Movimientos> listMovimientosf) {
        this.listMovimientosf = listMovimientosf;
    }

    public List<String> getListAcciones() {
        return listAcciones;
    }

    public void setListAcciones(List<String> listAcciones) {
        this.listAcciones = listAcciones;
    }
    
}
