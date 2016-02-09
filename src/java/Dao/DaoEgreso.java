/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Pojo.Grupo;
import Pojo.Marca;
import Pojo.Producto;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author jhonny.villa
 */
public class DaoEgreso implements Interface.InterfaceEgreso{

    @Override
    public boolean update(Session session, Producto producto) throws Exception {
        session.update(producto);
        return true;
    }

    @Override
    public List<Producto> getAll(Session session) throws Exception {
        Criteria criteria=session.createCriteria(Producto.class)
        .createAlias("grupo","g",CriteriaSpecification.LEFT_JOIN)
        .createAlias("marca","m",CriteriaSpecification.LEFT_JOIN)
        .createAlias("tipodescarga","t",CriteriaSpecification.LEFT_JOIN)
        .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//        criteria.add(Restrictions.eq("proEstado", "AC"));
        return criteria.list();
    }

    @Override
    public Producto getByCodigoBarras(Session session, String codigo) throws Exception {
        String hql = "from Producto where proCodigoBarra=:codigo";
        Query query = session.createQuery(hql);
        query.setParameter("codigo", codigo);
        Producto producto = (Producto) query.uniqueResult();
        return producto;
    }

    @Override
    public List<Marca> getAllMarcas(Session session) throws Exception {
        Criteria criteria=session.createCriteria(Marca.class);
        criteria.add(Restrictions.eq("marEstado", "AC"));
        return criteria.list();
    }

    @Override
    public List<Grupo> getAllGrupos(Session session) throws Exception {
        Criteria criteria=session.createCriteria(Grupo.class);
        criteria.add(Restrictions.eq("gruEstado", "AC"));
        return criteria.list();
    }

    @Override
    public List<Producto> getActives(Session session) throws Exception {
        Criteria criteria=session.createCriteria(Producto.class)
        .createAlias("grupo","g",CriteriaSpecification.LEFT_JOIN)
        .createAlias("marca","m",CriteriaSpecification.LEFT_JOIN)
        .createAlias("tipodescarga","t",CriteriaSpecification.LEFT_JOIN)
        .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("proEstado", "AC"));
        return criteria.list();
    }


}
