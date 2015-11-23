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
    public boolean register(Session session, Users users) throws Exception {

        session.save(users);
        return true;
    }

    @Override
    public List<Users> getActives(Session session) throws Exception {
        Criteria criteria = session.createCriteria(Users.class);
        criteria.add(Restrictions.eq("userestado", "AC"));
        return criteria.list();
    }

    @Override
    public List<Users> getAll(Session session) throws Exception {
        Criteria criteria = session.createCriteria(Users.class);
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
        String hql = "from Users where userusu=:usuario";
        Query query = session.createQuery(hql);
        query.setParameter("usuario", usuario);

        Users users = (Users) query.uniqueResult();

        return users;
    }
    
    @Override
    public Users getByDocumento(Session session, String documento) throws Exception {
        String hql = "from Users where userdoc=:documento";
        Query query = session.createQuery(hql);
        query.setParameter("documento", documento);

        Users users = (Users) query.uniqueResult();

        return users;
    }

    @Override
    public List<Roleusr> getRoles(Session session, int codigo) throws Exception {
        Criteria criteria = session.createCriteria(Roleusr.class)
                .add(Restrictions.eq("users.userid", codigo))
                .createCriteria("role", "r")
                .setResultTransformer(Criteria.ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public Roleusr getByRoleusr(Session session, int user, int role) throws Exception {
        Criteria criteria = session.createCriteria(Roleusr.class)
                .add(Restrictions.eq("Users.userid", user))
                .add(Restrictions.eq("Role.rolid", role));
        return (Roleusr) criteria.uniqueResult();
    }

    @Override
    public Users getByCode(Session session, Integer codigo) throws Exception {
        String hql = "from Users where userid=:codigo";
        Query query = session.createQuery(hql);
        query.setParameter("codigo", codigo);
        return (Users) query.uniqueResult();
    }

    @Override
    public boolean registerRole(Session session, Roleusr roleusr) throws Exception {
        session.save(roleusr);
        return true;
    }

}
