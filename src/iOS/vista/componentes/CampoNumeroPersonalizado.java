package iOS.vista.componentes;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class CampoNumeroPersonalizado extends JTextField implements KeyListener {

	private static final long serialVersionUID = -8791804016399840179L;
	private DecimalFormat formatter = new DecimalFormat("#,###.##");
	private DecimalFormat formato = new DecimalFormat("#,###");
	private String decimalSeparator;
	private String groupingSeparator;
	private Border border;

	public CampoNumeroPersonalizado() {
		this.addKeyListener(this);
		this.groupingSeparator = formatter.getDecimalFormatSymbols().getGroupingSeparator()+"";
		this.decimalSeparator = formatter.getDecimalFormatSymbols().getDecimalSeparator()+"";
		this.groupingSeparator = formato.getDecimalFormatSymbols().getGroupingSeparator()+"";
		this.border = this.getBorder(); 
		setHorizontalAlignment(SwingConstants.LEFT);
	}

	@Override
	public void setText(String str) {

		int selstart = super.getSelectionStart();
		int lentoend = super.getText().length() - selstart;

		try {
			super.setText(formatter.format(Double.parseDouble(str)));
		} catch (Exception e) {
			super.setText("0");
		}

		int newselstart = super.getText().length() - lentoend;
		super.select(newselstart, 0);
	}

	public void setValue(Double d) {
		if (d == null)
			this.setText("0");
		else
			this.setText(d + "");
	}

	public Double getValue() {
		if (this.getText().isEmpty()) {
			return 0d;
		}
		return Double.parseDouble(this.getText());
	}

	public void error() {
		this.setBorder(BorderFactory.createLineBorder(Color.RED));
		new Timer().schedule(new TimerTask() {
			 
			@Override
			public void run() {
				CampoNumeroPersonalizado.this.setBorder(border);
			}
		}, 300);
	}

	 
	@Override
	public String getText() {
		String str = super.getText().replace(groupingSeparator, "");
		str = str.replace(decimalSeparator, ".");
		return str;
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {
		char c = keyEvent.getKeyChar();
		if ((!(Character.isDigit(c)) && c != ',') || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
			keyEvent.consume();
			if (c != KeyEvent.VK_BACK_SPACE)
				error();
		}
	}

	 
	@Override
	public void keyReleased(KeyEvent keyEvent) {
		char c = keyEvent.getKeyChar();
		if (Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE || c == KeyEvent.VK_LEFT
				|| c == KeyEvent.VK_RIGHT) {
			this.setText(this.getText());
		}
	}

	 
	@Override
	public void keyPressed(KeyEvent e) {
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
}
