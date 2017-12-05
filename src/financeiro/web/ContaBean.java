package financeiro.web;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import financeiro.conta.Conta;
import financeiro.conta.ContaRN;
import financeiro.web.util.ContextoUtil;

@Named
@RequestScoped
public class ContaBean {

	private Conta selecionada = new Conta();
	private List<Conta> lista = null;
	
	public void salvar() {
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		this.selecionada.setUsuario(contextoBean.getUsuarioLogado());
		ContaRN contaRN = new ContaRN();
		contaRN.salvar(this.selecionada);
		this.selecionada = new Conta();
		this.lista = null;
	}
	
	public void excluir() {
		ContaRN contaRN = new ContaRN();
		contaRN.excluir(this.selecionada);
		this.selecionada = new Conta();
		this.lista = null;
	}
	
	public void tornarFavorita() {
		ContaRN contaRN = new ContaRN();
		contaRN.tornarFavorita(this.selecionada);
		this.selecionada = new Conta();
	}

	public Conta getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(Conta selecionada) {
		this.selecionada = selecionada;
	}

	public List<Conta> getLista() {
		if (this.lista == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			
			ContaRN contaRN = new ContaRN();
			this.lista = contaRN.listar(contextoBean.getUsuarioLogado());
		}
		
		return this.lista;
	}
	
}