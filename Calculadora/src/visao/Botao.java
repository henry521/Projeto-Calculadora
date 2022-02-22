package visao;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Botao extends JButton {
	
	public Botao(String texto, Color cor) {
		setText(texto);
		setOpaque(true);
		setBackground(cor);
		setFont(new Font("consolas", Font.PLAIN, 20));
		setForeground(Color.WHITE);
		setMargin(new Insets(0, 0, 0, 0));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	

}
