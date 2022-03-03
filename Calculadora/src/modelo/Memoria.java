package modelo;

import java.util.List;
import java.util.ArrayList;

public class Memoria {

	private enum TipoComando {
		ZERAR, NUMERO, DIV, MULT, SUB, SOMA, IGUAL, VIRGULA;
	}

	private static final Memoria instancia = new Memoria();

	private final List<MemoriaObservador> observadores = new ArrayList<>();

	private TipoComando ultimaOperacao = null;
	private boolean substituir = false;
	private String textoAtual = "";
	private String textoBuffer = "";

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

	public void processarComando(String texto) {

		TipoComando tipoComando = detectarTipoComando(texto);
		System.out.println(tipoComando);

		if(tipoComando == null) {
			return;
		} else if (tipoComando == TipoComando.ZERAR) {
			textoAtual = "";
			textoBuffer = "";
			substituir = false;
			ultimaOperacao = null;
			// Se digitou numero ou virgula e o substituir for 
			// verdadeiro, ele vai substituir o texto pelo texto
			// atual. Caso contrário, vai pegar o texto atual + 
			// o texto e substituir pelo atual
		} else if (tipoComando == TipoComando.NUMERO
				||	 tipoComando == TipoComando.VIRGULA) {
			
			textoAtual = substituir ? texto : textoAtual + texto;
			substituir = false;
		} else {
			substituir = true;
			textoAtual = obterResultadoOperacao();
			textoBuffer = textoAtual;
			ultimaOperacao = tipoComando;
		}

		observadores.forEach(o -> o.valorAlterado(getTextoAtual()));
	}

	private String obterResultadoOperacao() {
		if(ultimaOperacao == null || ultimaOperacao == TipoComando.IGUAL) {
			return textoAtual;
		}
		
		double numeroBuffer = 
				Double.parseDouble(textoBuffer.replace("," ,"."));
		
		double numeroAtual = 
				Double.parseDouble(textoAtual.replace("," ,"."));
		
		double resultado = 0;
		
		if(ultimaOperacao == TipoComando.SOMA) {
			resultado = numeroBuffer + numeroAtual;
		} else if (ultimaOperacao == TipoComando.SUB) {
			resultado = numeroBuffer - numeroAtual;
		} else if (ultimaOperacao == TipoComando.MULT) {
			resultado = numeroBuffer * numeroAtual;
		}else if (ultimaOperacao == TipoComando.DIV) {
			resultado = numeroBuffer / numeroAtual;
		}
		
		String resultadoString = 
				Double.toString(resultado).replace(".", ",");
		
		boolean inteiro = resultadoString.endsWith(",0");
		return inteiro ? resultadoString.replace(",0", "") 
				: resultadoString;
		
		
	}

	private TipoComando detectarTipoComando(String texto) {

		if (textoAtual.isEmpty() && texto == "0") {
			return null;
		}

		// verificar se o texto digitado pode ou não
		// ser convertido para um valor inteiro

		try {
			Integer.parseInt(texto);
			return TipoComando.NUMERO;
		} catch (NumberFormatException e) {
			// Quando não for número:

			if ("AC".equals(texto)) {
				return TipoComando.ZERAR;
			} else if ("/".equals(texto)) {
				return TipoComando.DIV;
			} else if ("*".equals(texto)) {
				return TipoComando.MULT;
			} else if ("+".equals(texto)) {
				return TipoComando.SOMA;
			} else if ("-".equals(texto)) {
				return TipoComando.SUB;
			} else if ("=".equals(texto)) {
				return TipoComando.IGUAL;
			} else if (",".equals(texto)
					//Só coloca virgula se não tiver 
					// nenhuma virgula já inserida
					&& !textoAtual.contains(",")) {
				return TipoComando.VIRGULA;
			} 

		}

		return null;
	}

}
