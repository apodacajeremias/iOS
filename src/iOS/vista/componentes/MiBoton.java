package iOS.vista.componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import iOS.modelo.entidades.Proceso;
import iOS.modelo.entidades.Sector;

public class MiBoton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4442593098919633372L;

	public MiBoton(String text) {
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(100, 30));
		setMaximumSize(new Dimension(100, 30));
		setHorizontalAlignment(CENTER);
		setHorizontalTextPosition(TRAILING);
		setVerticalAlignment(CENTER);
		setVerticalTextPosition(CENTER);
		setFont(new Font("Helvetica", Font.BOLD, 10));
		setFocusPainted(true);
		setContentAreaFilled(true);
		setBorderPainted(true);
		setOpaque(true);
		super.setText(text);
		setIcono(text);
	}

	public MiBoton(Object entidad) {
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(80, 80));
		setMaximumSize(new Dimension(100, 30));
		setHorizontalAlignment(CENTER);
		setHorizontalTextPosition(TRAILING);
		setVerticalAlignment(CENTER);
		setVerticalTextPosition(CENTER);
		setFont(new Font("Helvetica", Font.BOLD, 10));
		setFocusPainted(true);
		setContentAreaFilled(true);
		setBorderPainted(true);
		setOpaque(true);
		setBorder(new RoundedBorder(90));
		if (entidad instanceof Proceso) {
			Proceso proceso = (Proceso) entidad;
			try {
				setIcon(new ImageIcon(proceso.getIcon()));
			} catch (Exception e) {
				setIcono("NoDisponible");
				e.printStackTrace();
			}
			super.setText(proceso.toString());
		} else if (entidad instanceof Sector) {
			Sector sector = (Sector) entidad;
			setIcono("NoDisponible");
			super.setText(sector.toString());
		}
	}

	public void setIcono(String nombreIcono) {
		try {
			// Se recupera la posible ubicacion del icono
			URL url = MiBoton.class.getResource("/img/" + nombreIcono + ".png");
			// Asigna el icono al boton
			setIcon(new ImageIcon(url));

		} catch (Exception e) {
		}
	}

	public void callToAction() {
		setBackground(Color.GREEN);
	}

}
