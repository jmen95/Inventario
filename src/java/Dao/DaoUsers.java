/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Interface.InterfaceUsers;
import Pojo.Roleusr;
import Pojo.Users;
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
public class DaoUsers implements InterfaceUsers {

    @Override
    public boolean register(Session session, Users users,Roleusr roleusr) throws Exception {
        session.save(users);
        session.save(roleusr);
        return true;
    }

    @Override
    public List<Users> getActives(Session session) throws Exception {
        Criteria criteria=session.createCriteria(Users.class);
        criteria.add(Restrictions.eq("userestado", "AC"));
        return criteria.list();
    }

    @Override
    public List<Users> getAll(Session session) throws Exception {
        Criteria criteria=session.createCriteria(Users.class);
        criteria.addOrder(Order.asc("userid"));
        return criteria.list();
    }

    @Override
    public boolean update(Session session, Users users) throws Exception {
        session.update(users);
        return true;
    }

    @Override
    public Users getByUsuario(Session session, String usuario) throws Exception {
        String hql="from Tusuario where correoElectronico=:usuario";
        Query query=session.createQuery(hql);
        query.setParameter("usuario", usuario);
        
        Users users=(Users) query.uniqueResult();
        
        return users;
    }

}
