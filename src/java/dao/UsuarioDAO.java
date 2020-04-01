/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Usuario;


@Stateless
public class UsuarioDAO {
    @PersistenceContext(unitName="SirvePU")
    private EntityManager em;
    private EntityManagerFactory factory;
    private final static Logger LOGGER = Logger.getLogger("dao.UsuarioDAO");
    public void crear(Usuario entity){
        em.persist(entity);
        
    }
    public void editar(Usuario entity){
        em.merge(entity);
        
    }
    public void eliminar(Usuario entity){
        em.remove(em.merge(entity));
    }
    public Usuario encontrarUsuario(String correo){
        return em.find(Usuario.class, correo);
    }
    public Usuario encontrarUsuarioPorLogin(String correo,String clave){
        
        crearConexion();
        Query q= em.createQuery("SELECT u FROM Usuario u WHERE u.correo= :mail AND u.clave= :pass ",Usuario.class);
        q.setParameter("mail", correo);
        q.setParameter("pass", clave);
        try{
        return(Usuario) q.getSingleResult();
            
                }
        catch(NoResultException e)
        {   LOGGER.severe("ERROR AL CONSULTAR");
            return null;
        }finally
        {   LOGGER.info("CONEXIÓN CERRADA");
            cerrarConexion();
        }
                
    }
    public void crearConexion()
    {
        factory = Persistence.createEntityManagerFactory("SirvePU");
        em = factory.createEntityManager();
    }
    public void cerrarConexion()
    {
        em.close();
    }
    public List<Usuario> listar()
            
    {
        Query q= em.createQuery("SELECT FROM u FROM Usuario u");
        LOGGER.log(Level.INFO, "Listar" +q.getResultList());
            return q.getResultList();
    }

   
}
