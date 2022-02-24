package visao;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import modelo.Memoria;
import modelo.MemoriaObservador;

@SuppressWarnings("serial")
public class Display extends JPanel implements MemoriaObservador  {
	
	private final JLabel label;
	
	
	 
	public Display() {
		Memoria.getInstancia().adicionarObservador(this);
		
		
		setBackground(new Color(46, 49, 50));
		label = new JLabel(Memoria.getInstancia().getTextoAtual());
		add(label);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("consolas", Font.PLAIN, 30));
		
		// criando o gerenciador de layout para colocar o texto
		// no lugar correto
		setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 25));
		
	}



	@Override
	public void valorAlterado(String novoValor) {
		label.setText(novoValor);		
	}

}
