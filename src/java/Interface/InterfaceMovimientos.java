/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Pojo.Movimientos;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author JESÚS MENDOZA
 */
public interface InterfaceMovimientos {
    public List<Movimientos> getMovimientos(Session session) throws Exception;
    public List<String> getAcciones(Session session) throws Exception;
}
