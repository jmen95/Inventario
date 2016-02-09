/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeanView;

import Dao.DaoHome;
import Model.IndCincoMes;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author JESÚS MENDOZA
 */
@ManagedBean
@ViewScoped
public class MbVHome implements Serializable {

    /**
     * Creates a new instance of MbVHome
     */
    private BarChartModel indicadormes;
    private List<IndCincoMes> listaIndicadormes;
    Session session;

    public MbVHome() {
        session = null;
        indicadormes=new BarChartModel();
        try {
            session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
            DaoHome daoHome = new DaoHome();
            listaIndicadormes = daoHome.getIndMes(session);
            int max=0;
            for (IndCincoMes indicador : listaIndicadormes) {
                ChartSeries charSeries = new ChartSeries();
                charSeries.setLabel(indicador.getNombreProducto());
                charSeries.set(indicador.getNombreProducto(), indicador.getCantidadProducto().intValue());
                if(indicador.getCantidadProducto().intValue()>max){
                    max=indicador.getCantidadProducto().intValue();
                }
                indicadormes.addSeries(charSeries);
            }
            indicadormes.setTitle("Productos más utilizados en el mes");
            indicadormes.setAnimate(true);
            indicadormes.setLegendPosition("ne");
            Axis yAxis = indicadormes.getAxis(AxisType.Y);
            yAxis.setMin(0);
            yAxis.setMax(max+5);
        } catch (Exception ex) {
            Logger.getLogger(MbVHome.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    public BarChartModel getIndicadormes() {
        return indicadormes;
    }

    public void setIndicadormes(BarChartModel indicadormes) {
        this.indicadormes = indicadormes;
    }

    public List<IndCincoMes> getListaIndicadormes() {
        return listaIndicadormes;
    }

    public void setListaIndicadormes(List<IndCincoMes> listaIndicadormes) {
        this.listaIndicadormes = listaIndicadormes;
    }

}
