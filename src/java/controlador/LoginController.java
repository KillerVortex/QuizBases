/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;


import com.sun.xml.internal.ws.spi.db.BindingContextFactory;
import dao.UsuarioDAO;
import java.io.IOException;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import modelo.Usuario;
import controlador.LoginController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;

@Named(value="loginController")
@SessionScoped
public class LoginController {

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public void setUsuarioAutenticado(Usuario usuarioAutenticado) {
        this.usuarioAutenticado = usuarioAutenticado;
    }

    public UsuarioDAO getEjbDAO() {
        return ejbDAO;
    }

    public void setEjbDAO(UsuarioDAO ejbDAO) {
        this.ejbDAO = ejbDAO;
    }
private Usuario usuario;
private Usuario usuarioAutenticado;
private final static Logger LOGGER = Logger.getLogger("controlador.LoginController");
private List<Usuario>listado;
@EJB
private UsuarioDAO ejbDAO;


public LoginController()
{
    usuario= new Usuario();
    
}
public void login() throws IOException
{
    FacesContext ctx= FacesContext.getCurrentInstance();
   Object res = ejbDAO.encontrarUsuarioPorLogin(usuario.getCorreo(), usuario.getClave());
    
    if(res!=null)
        
    {     LOGGER.log(Level.INFO, "Bienvenido"); 
         
          ctx.getExternalContext().redirect("home");
    }
    else
    {      LOGGER.log(Level.SEVERE, "NO ENCONTRADO"); 
    FacesMessage mensaje = new FacesMessage("Credenciales incorrectas");
         mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
         ctx.addMessage(null, mensaje);
                 
          ctx.getExternalContext().redirect("index");
    }
    
   
    
  
}
public List<Usuario> getListado()

{
    listado = ejbDAO.listar();
    return listado;
}
  
    
}
