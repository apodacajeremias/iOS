package iOS.vista.componentes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class CampoTextoPersonalizado extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3170736208686794999L;

	public CampoTextoPersonalizado() {
		super();
		setFont(new Font("Tahoma", Font.PLAIN, 15));
		setDisabledTextColor(Color.DARK_GRAY);
		mayusculas();
	}
		
		

	public void soloNumerosEnteros() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Character.isDigit(e.getKeyChar()))
					e.consume();
			}
		});

	}

	public void soloLetras() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (Character.isDigit(e.getKeyChar()))
					e.consume();
			}
		});

	}

	public void mayusculas() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Character c = evt.getKeyChar();
				if (Character.isLetter(c)) {
					evt.setKeyChar(Character.toUpperCase(c));
				}
			}
		});
	}

	public void limite(final int lim) {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				if (getText().length() == lim) {
					evt.consume();
				}
			}
		});
	}

	public void avisar(){
		setBorder(BorderFactory.createLineBorder(Color.red, 2));
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				setBorder(BorderFactory.createLineBorder(null));
				requestFocus();
			}
		});
	}
	
//	public void error() {
//		this.setBorder(BorderFactory.createLineBorder(Color.RED));
//		new Timer().schedule(new TimerTask() {
//			 
//			public void run() {
//				CampoTextoPersonalizado.this.setBorder(border);
//			}
//		}, 300);
//	}


}