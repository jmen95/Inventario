/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Pojo.Roleusr;
import Pojo.Users;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author J-MeN
 */
public interface InterfaceUsers {
    
    public boolean register(Session session, Users users) throws Exception;
    
    public boolean registerRole(Session session,Roleusr roleusr) throws Exception;

    public List<Users> getActives(Session session) throws Exception;

    public List<Users> getAll(Session session) throws Exception;
    
    public List<Roleusr> getRoles(Session session,int codigo) throws Exception;

    public boolean update(Session session, Users users) throws Exception;
    
    public Users getByUsuario(Session session, String usuario) throws Exception;
    
    public Users getByDocumento(Session session, String documento) throws Exception;
    
    public Roleusr getByRoleusr(Session session, int user,int role) throws Exception;
    
    public Users getByCode(Session session,Integer codigo) throws Exception;
}
