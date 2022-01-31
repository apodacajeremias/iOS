package iOS.vista.componentes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class VentanaGenerica extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2162327890190805660L;
	private JPanel panelFormulario;
	private MiToolbar miToolBar;
	private LabelPersonalizado lMensaje;

	/**
	 * Create the dialog.
	 */
	public VentanaGenerica() {
		getContentPane().setBackground(new Color(255, 255, 255));
		setBounds(100, 100, 500, 500);
		setResizable(false);
		setType(Type.NORMAL);
		setLocationRelativeTo(this);
		setModalityType(ModalityType.APPLICATION_MODAL);
		getContentPane().setLayout(null);

		panelFormulario = new JPanel();
		panelFormulario.setBackground(new Color(255, 255, 255));
		panelFormulario.setBorder(new MatteBorder(2, 2, 2, 2, new Color(240, 248, 255)));
		panelFormulario.setBounds(10, 10, 474, 370);
		getContentPane().add(panelFormulario);
		panelFormulario.setLayout(null);

		miToolBar = new MiToolbar();
		miToolBar.getbtSalir().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		miToolBar.setBounds(10, 420, 474, 40);
		getContentPane().add(miToolBar);

		lMensaje = new LabelPersonalizado(10);
		lMensaje.setText("REGISTRO ACTUALIZADO CON \u00C9XITO");
		lMensaje.setFont(new Font("Tahoma", Font.BOLD, 18));
		lMensaje.setBounds(20, 390, 450, 20);
		getContentPane().add(lMensaje);
		lMensaje.setForeground(Color.BLACK);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JPanel getPanelFormulario() {
		return panelFormulario;
	}

	public MiToolbar getMiToolBar() {
		return miToolBar;
	}

	public LabelPersonalizado getlMensaje() {
		return lMensaje;
	}

}
