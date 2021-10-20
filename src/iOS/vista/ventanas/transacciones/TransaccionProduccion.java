package iOS.vista.ventanas.transacciones;

import java.awt.Color;

import javax.swing.JDialog;

public class TransaccionProduccion extends JDialog {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7235985227042616775L;

	public TransaccionProduccion() {
		getContentPane().setBackground(Color.WHITE);
		setBounds(100, 100, 900, 600);
		setLocationRelativeTo(this);
		setModal(true);
		getContentPane().setLayout(null);
	}

}
