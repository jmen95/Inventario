/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Interface.InterfaceMovimientos;
import Pojo.Movimientos;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;

/**
 *
 * @author JESÚS MENDOZA
 */
public class DaoMovimientos implements InterfaceMovimientos{

    @Override
    public List<Movimientos> getMovimientos(Session session) throws Exception {
        Criteria criteria=session.createCriteria(Movimientos.class);
        criteria.createAlias("producto", "p", JoinType.LEFT_OUTER_JOIN)
        .createAlias("users", "u", JoinType.LEFT_OUTER_JOIN).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.addOrder(Order.desc("movFecha"));
        return criteria.list();
    }

    @Override
    public List<String> getAcciones(Session session) throws Exception {
        String sql="SELECT DISTINCT(movAccion) FROM movimientos";
        SQLQuery sqlQuery=session.createSQLQuery(sql);
        return sqlQuery.list();
    }
    
}
