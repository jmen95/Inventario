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
    public boolean register(Session session, Users users,Roleusr roleusr) throws Exception;

    public List<Users> getActives(Session session) throws Exception;

    public List<Users> getAll(Session session) throws Exception;

    public boolean update(Session session, Users users) throws Exception;
    
    public Users getByUsuario(Session session, String usuario) throws Exception;
}