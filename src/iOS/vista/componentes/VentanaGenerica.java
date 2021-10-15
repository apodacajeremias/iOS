package iOS.vista.componentes;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
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
		setBackground(new Color(51, 51, 51));
		getContentPane().setBackground(new Color(255, 255, 255));
		setResizable(false);
		setType(Type.NORMAL);
		setBounds(100, 100, 500, 500);
		//centrad
		setLocationRelativeTo(this);
		//Evita que la ventana pierda el foco hasta que se cierre
		setModal(true);
		getContentPane().setLayout(null);
		
		panelFormulario = new JPanel();
		panelFormulario.setBackground(new Color(255, 255, 255));
		panelFormulario.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(240, 248, 255)));
		panelFormulario.setBounds(10, 10, 474, 370);
		getContentPane().add(panelFormulario);
		panelFormulario.setLayout(null);
				
		miToolBar = new MiToolbar();
		miToolBar.getbtSalir().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		miToolBar.setBounds(10, 420, 474, 40);
		getContentPane().add(miToolBar);
		
		lMensaje = new LabelPersonalizado(10);
		lMensaje.setBounds(10, 390, 474, 20);
		getContentPane().add(lMensaje);
		lMensaje.setHorizontalAlignment(SwingConstants.RIGHT);
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
