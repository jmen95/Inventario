/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Interface.InterfaceRole;
import Pojo.Role;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author J-MeN
 */
public class DaoRole implements InterfaceRole{

    @Override
    public boolean register(Session session, Role role) throws Exception {
        session.save(role);
        return true;
    }

    @Override
    public boolean update(Session session, Role role) throws Exception {
        session.update(role);
        return true;
    }

    @Override
    public List<Role> getAll(Session session) throws Exception {
        Criteria criteria=session.createCriteria(Role.class);
        criteria.addOrder(Order.asc("rolid"));
        return criteria.list();
    }

    @Override
    public List<Role> getActives(Session session) throws Exception {
        Criteria criteria=session.createCriteria(Role.class);
        criteria.add(Restrictions.eq("rolestado", "AC"));
        return criteria.list();
    }

    @Override
    public Role getByCode(Session session, Integer codigo) throws Exception {
        String hql="from Role where Rolid=:codigo";
        Query query=session.createQuery(hql);
        query.setParameter("codigo", codigo);        
        return (Role) query.uniqueResult();
    }
    
}
