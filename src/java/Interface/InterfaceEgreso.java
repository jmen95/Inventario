/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Pojo.Grupo;
import Pojo.Marca;
import Pojo.Producto;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author jhonny.villa
 */
public interface InterfaceEgreso {

    public boolean update(Session session, Producto producto) throws Exception;

    public List<Producto> getAll(Session session) throws Exception;
    
    public List<Marca> getAllMarcas(Session session) throws Exception;
    
    public List<Grupo> getAllGrupos(Session session) throws Exception;

    public Producto getByCodigoBarras(Session session, String codigo) throws Exception;
    
    public List<Producto> getActives(Session session) throws Exception;
}