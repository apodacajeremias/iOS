package iOS.vista.componentes;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class LoadingPanel extends JPanel {
	public LoadingPanel() {
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1764524811815523241L;
	URL url = getClass().getResource("/img/logo.png");
	Image image = new ImageIcon(url).getImage();

	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		setOpaque(false);
		super.paintComponent(g);
	}
}
