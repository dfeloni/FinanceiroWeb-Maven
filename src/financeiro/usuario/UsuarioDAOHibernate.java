package financeiro.usuario;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

public class UsuarioDAOHibernate implements UsuarioDAO {
	
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;		
	}

	public void salvar(Usuario usuario) {
		this.session.save(usuario);
	}

	public void atualizar(Usuario usuario) {
		if (usuario.getPermissao() == null || usuario.getPermissao().size() == 0) {
			Usuario usuarioPermissao = this.carregar(usuario.getCodigo());
			usuario.setPermissao(usuarioPermissao.getPermissao());
			this.session.evict(usuarioPermissao);
		}
		this.session.update(usuario);
	}

	public void excluir(Usuario usuario) {
		this.session.delete(usuario);
	}

	public Usuario carregar(Integer codigo) {
		return (Usuario) this.session.get(Usuario.class, codigo);
	}

	public Usuario buscarPorLogin(String login) {
		String hql = "select u from Usuario u where u.login = :login";
		return (Usuario) this.session.createQuery(hql).setParameter("login", login).uniqueResult();
	}

	public List<Usuario> listar() {		
		List<Usuario> usuarioList = new ArrayList<Usuario>(); 
	    for (Object usuario : this.session.createQuery("from Usuario").getResultList()) {
	    		usuarioList.add((Usuario) usuario);
	    }
	    return usuarioList;
	}
}