package controllers;

import java.util.Date;
import java.util.List;


import models.Cliente;
import models.Conta;
import models.Recarga;
import play.mvc.Controller;

public class Contas extends Controller {

	public static void formConta(Long idConta) {
		Conta conta = Conta.findById(idConta);
		Cliente cliente = Cliente.findById(idConta);
		
		render(conta, cliente);
	}
	
	public static void CompraRefeicao(Long idConta) {
		Conta conta = Conta.findById(idConta);
		if(conta.saldo > 0) {
			conta.saldo -= 1;
			conta.save();
			flash.success("Compra realizada com sucesso!");
		}
		else {
			flash.error("Saldo insuficiente faça uma recarga!");
		}
        Operadores.operador();
	}
	
	public static void salvar(Conta conta) {
				
		conta.saldo += conta.recarga;
		conta.save();
		
		Recarga recarga = new Recarga();
		recarga.recarga = conta.recarga;
		recarga.pagamento = conta.pagamento;
		recarga.conta = conta;
		recarga.data = new Date();
		recarga.save();

		flash.success("recarga salva com sucesso!");

		renderTemplate("Recargas/detalhesRecarga.html", recarga);
	}
	
	
	public static void detalhesConta(Conta conta) {
		long id = new Long(session.get("idClienteLogado"));
		Cliente cliente = Cliente.findById(id);
		
		conta = cliente.conta;
		List<Recarga> recargas = Recarga.find("conta_id = ?", conta.id).fetch();
		render(conta,cliente,recargas);
	}
	
	public static void contaListar() {
		List<Conta> contas = Conta.findAll();
		render(contas);
	}
	
	public static void verificarNumeroConta(Integer numeroConta){
		Conta conta = Conta.find("numeroConta = ?", numeroConta).first();
		
		if(conta == null){
			flash.error("Erro!!! Informe um número de conta válida!");
			Operadores.operador();
		}else{
			Long idConta = conta.id;
			formConta(idConta); 
		}
		
	}
	
	public static void CompraVerificarNumeroConta(Integer numeroConta){
		Conta conta = Conta.find("numeroConta = ?", numeroConta).first();
		
		if(conta == null){
			flash.error("Erro!!! Informe um número de conta válida!");
			Operadores.operador();
		}else{
			Long idConta = conta.id;
			CompraRefeicao(idConta); 
		}
		
	}
	 
	public static void editar(Long id) {
		Conta conta = Conta.findById(id);
		renderTemplate("Contas/formConta.html", conta);
	}
	
	public static void remover(Long id) {
		Conta conta = Conta.findById(id);
		conta.delete();
		flash.success("Conta removida com sucesso!");
		contaListar();
	}
}
