package financeiro.conta;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import financeiro.usuario.Usuario;

public class ContaDAOHibernate implements ContaDAO{

	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public void salvar(Conta conta) {
		this.session.saveOrUpdate(conta);
	}

	public void excluir(Conta conta) {
		this.session.delete(conta);
	}

	public Conta carregar(Integer codigo) {		
		return (Conta) this.session.get(Conta.class, codigo);
	}

	public List<Conta> listar(Usuario usuario) {
		List<Conta> contaList = new ArrayList<Conta>(); 
	    for (Object conta : this.session.createQuery("from Conta").getResultList()) {
	    		contaList.add((Conta) conta);
	    }
	    return contaList;
	}

	public Conta buscarFavorita(Usuario usuario) {
		CriteriaBuilder builder = this.session.getCriteriaBuilder();		
	    CriteriaQuery<Conta> query = builder.createQuery(Conta.class);	    
	    Root<Conta> contas = query.from(Conta.class);

	    //Constructing list of parameters
	    List<Predicate> predicates = new ArrayList<Predicate>();

	    //Adding predicates in case of parameter not being null
	    if (usuario != null) {
	        predicates.add(builder.equal(contas.get("usuario"), usuario));
	    }
	    
        predicates.add(builder.equal(contas.get("favorita"), true));

        //query itself
        query.select(contas).where(predicates.toArray(new Predicate[]{}));
        
	    //execute query and do something with result
	    return this.session.createQuery(query).getSingleResult();
	}
	
}