package iOS.vista.componentes;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;

public class ReporteGenerico2 extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8399071329792240075L;
	private JTabbedPane tabbed;

	public ReporteGenerico2() {
		getContentPane().setBackground(new Color(255, 255, 255));
		setFont(new Font("Tahoma", Font.BOLD, 12));
		setBackground(new Color(51, 51, 51));
		setBounds(100, 100, 1280, 640);
		setLocationRelativeTo(this);
		setResizable(false);
		setModal(true);
		setTitle("Reportes");
		getContentPane().setLayout(null);
		
		tabbed = new JTabbedPane(JTabbedPane.TOP);
		tabbed.setBounds(10, 10, 1254, 590);
		getContentPane().add(tabbed);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JTabbedPane getTabbed() {
		return tabbed;
	}

	public void setTabbed(JTabbedPane tabbed) {
		this.tabbed = tabbed;
	}
	
	
}
