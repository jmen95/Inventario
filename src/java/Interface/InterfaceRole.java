/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Pojo.Role;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author J-MeN
 */
public interface InterfaceRole {
    public boolean register(Session session,Role role) throws Exception;
    public boolean update(Session session,Role role) throws Exception;
    public List<Role> getAll(Session session) throws Exception;
    public List<Role> getActives(Session session) throws Exception;
    public Role getByCode(Session session,Integer codigo) throws Exception;
}
