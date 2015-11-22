/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Interface.InterfaceProducto;
import Pojo.Grupo;
import Pojo.Marca;
import Pojo.Producto;
import Pojo.Tipodescarga;
import Pojo.Users;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author J-MeN
 */
public class DaoProducto implements InterfaceProducto{

    @Override
    public boolean register(Session session, Producto producto) throws Exception {
        session.save(producto);
        return true;
    }

    @Override
    public List<Marca> getMarcas(Session session) throws Exception {
        Criteria criteria=session.createCriteria(Marca.class);
        criteria.add(Restrictions.eq("marEstado", "AC"));
        return criteria.list();
    }

    @Override
    public List<Grupo> getGrupos(Session session) throws Exception {
        Criteria criteria=session.createCriteria(Grupo.class);
        criteria.add(Restrictions.eq("gruEstado", "AC"));
        return criteria.list();
    }

    @Override
    public Producto getByCodigoBarra(Session session, String codigo) throws Exception {
        String hql = "from Producto where proCodigoBarra=:codigo";
        Query query = session.createQuery(hql);
        query.setParameter("codigo", codigo);
        Producto producto = (Producto) query.uniqueResult();
        return producto;
    }

    @Override
    public Marca getByCodigoMarca(Session session, int codigo) throws Exception {
        String hql = "from Marca where marCodigo=:codigo";
        Query query = session.createQuery(hql);
        query.setParameter("codigo", codigo);
        Marca marca = (Marca) query.uniqueResult();
        return marca;
    }

    @Override
    public Grupo getByCodigoGrupo(Session session, int codigo) throws Exception {
        String hql = "from Grupo where gruCodigo=:codigo";
        Query query = session.createQuery(hql);
        query.setParameter("codigo", codigo);
        Grupo grupo = (Grupo) query.uniqueResult();
        return grupo;
    }

    @Override
    public Tipodescarga getByCodigoDescarga(Session session, int codigo) throws Exception {
         String hql = "from Tipodescarga where tideCodigo=:codigo";
        Query query = session.createQuery(hql);
        query.setParameter("codigo", codigo);
        Tipodescarga tipodescarga = (Tipodescarga) query.uniqueResult();
        return tipodescarga;
    }

    @Override
    public List<Tipodescarga> getTipodescargas(Session session) throws Exception {
        Criteria criteria=session.createCriteria(Tipodescarga.class);
        criteria.add(Restrictions.eq("tideEstado", "AC"));
        return criteria.list();
    }
    
}
