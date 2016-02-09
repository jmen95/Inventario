/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Model.IndCincoMes;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author JESÚS MENDOZA
 */
public interface InterfaceHome {
    
    public List<IndCincoMes> getIndMes(Session session) throws Exception;
    
}
