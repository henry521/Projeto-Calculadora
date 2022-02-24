package modelo;

import java.util.List;
import java.util.ArrayList;



public class Memoria {
	
	private static final Memoria instancia = new Memoria();
	
	private final List<MemoriaObservador> observadores = 
			new ArrayList<>();
	
	private String textoAtual = "";
	
	private Memoria() {
		
	}

	public static Memoria getInstancia() {
		return instancia;
	}
	
	public void adicionarObservador(MemoriaObservador observador) {
		observadores.add(observador);
	}
	
	public String getTextoAtual() { 
		
		// Se o texto atual for vazio, retorna 0
		// Caso contrario, retorna texto atual.
		return textoAtual.isEmpty() ? "0" : textoAtual;
	}
	
	public void processarComando(String valor) { 
		
		if("AC".equals(valor)) {
			textoAtual = "0";
		} else {
			textoAtual += valor;
		}
		
		textoAtual += valor;
		
		observadores.forEach(o -> o.valorAlterado(getTextoAtual()));
	}
	
	

}
