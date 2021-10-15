package iOS.vista.componentes;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class LabelPersonalizado extends JLabel {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1425270651759066380L;

	public LabelPersonalizado(int tam) {
		super();
		setBackground(Color.WHITE);
		setForeground(Color.BLACK);
		setHorizontalAlignment(SwingConstants.LEFT);
		
		if (tam == 0) {
			tam = 12;
			setFont(new Font("Tahoma", Font.BOLD, tam));
		} else {
			setFont(new Font("Tahoma", Font.BOLD, tam));
		}
	}
	
}
