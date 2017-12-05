package financeiro.web;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

import financeiro.conta.Conta;
import financeiro.conta.ContaRN;
import financeiro.usuario.Usuario;
import financeiro.usuario.UsuarioRN;
import java.io.Serializable;

@Named
@SessionScoped
public class ContextoBean implements Serializable {
	
	private static final long serialVersionUID = 383636868757100744L;
	
	private Usuario usuarioLogado = null;
	private Conta contaAtiva = null;
	
	public Usuario getUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String login = external.getRemoteUser();
		
		if (this.usuarioLogado == null || !login.equals(this.usuarioLogado.getLogin())) {
			if (login != null) {
				UsuarioRN usuarioRN = new UsuarioRN();
				this.usuarioLogado = usuarioRN.buscarPorLogin(login);
				this.contaAtiva = null;
			}
		}
		
		return usuarioLogado;
	}
	
	public void setUsuarioLogado(Usuario usuario) {
		this.usuarioLogado = usuario;
	}
	
	public Conta getContaAtiva() {
		if (this.contaAtiva == null) {
			Usuario usuario = this.getUsuarioLogado();
			ContaRN contaRN = new ContaRN();
			this.contaAtiva = contaRN.buscarFavorita(usuario);
			
			if (this.contaAtiva == null) {
				List<Conta> contas = contaRN.listar(usuario);
				
				if (contas != null) {
					for (Conta conta : contas) {
						this.contaAtiva = conta;
						break;
					}
				}
			}
		}
		
		return this.contaAtiva;
	}
	
	public void setContaAtiva(ValueChangeEvent event) {
		Integer conta = (Integer) event.getNewValue();
		ContaRN contaRN = new ContaRN();
		this.contaAtiva = contaRN.carregar(conta);
	}
	
}