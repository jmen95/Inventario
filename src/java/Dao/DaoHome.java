/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Interface.InterfaceHome;
import Model.IndCincoMes;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author JESÚS MENDOZA
 */
public class DaoHome implements InterfaceHome {

    @Override
    public List<IndCincoMes> getIndMes(Session session) throws Exception {
        String sql = "SELECT \n"
                + "  proNombre,\n"
                + "  COUNT(1) \n"
                + "FROM\n"
                + "  movimientos \n"
                + "  INNER JOIN producto \n"
                + "    ON movCodProducto = proCodigoBarra \n"
                + "WHERE movAccion = 'Egreso' \n"
                + "  AND MONTH(movFecha) = MONTH(CURDATE()) \n"
                + "  AND YEAR(movFecha) = YEAR(CURDATE()) \n"
                + "GROUP BY proNombre \n"
                + "ORDER BY movFecha DESC \n"
                + "LIMIT 5";
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        List<IndCincoMes> lista=new ArrayList<>();
        for (Iterator it = sqlQuery.list().iterator(); it.hasNext();) {
            Object[] lista1 = (Object[]) it.next();
            lista.add(new IndCincoMes(lista1[0].toString(),(BigInteger)lista1[1]));
        }
        return lista;
    }

}
