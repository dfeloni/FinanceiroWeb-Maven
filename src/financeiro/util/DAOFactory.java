package financeiro.util;

import financeiro.conta.ContaDAO;
import financeiro.conta.ContaDAOHibernate;
import financeiro.usuario.UsuarioDAO;
import financeiro.usuario.UsuarioDAOHibernate;

public class DAOFactory {
	
	public static UsuarioDAO criarUsuarioDAO() {	
		UsuarioDAOHibernate usuarioDAO = new UsuarioDAOHibernate();
		usuarioDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return usuarioDAO;
	}
	
	public static ContaDAO criarContaDAO() {
		ContaDAOHibernate contaDAO = new ContaDAOHibernate();
		contaDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return contaDAO;
	}
}