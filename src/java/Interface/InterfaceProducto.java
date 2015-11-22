/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Pojo.Grupo;
import Pojo.Marca;
import Pojo.Producto;
import Pojo.Tipodescarga;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author J-MeN
 */
public interface InterfaceProducto {
    public boolean register(Session session, Producto producto) throws Exception;
    
    public List<Marca> getMarcas(Session session) throws Exception;
    
    public List<Grupo> getGrupos(Session session) throws Exception;
    
    public List<Tipodescarga> getTipodescargas(Session session) throws Exception;
    
    public Producto getByCodigoBarra(Session session, String codigo) throws Exception;
    
    public Marca getByCodigoMarca(Session session, int codigo) throws Exception;
    
    public Grupo getByCodigoGrupo(Session session, int codigo) throws Exception;
    
    public Tipodescarga getByCodigoDescarga(Session session, int codigo) throws Exception;
}
