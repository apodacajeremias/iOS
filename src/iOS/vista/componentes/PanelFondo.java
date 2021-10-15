package iOS.vista.componentes;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelFondo extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4406720808176850746L;
	private Image img;

	public PanelFondo(String nombreFondo) {
		try {
			URL url = PanelFondo.class.getResource("/img/"+nombreFondo);
			img = new ImageIcon(url).getImage();
		} catch (Exception e) {
		}
		//hacemos transparente el color de fondo
		setOpaque(false);
	}

	@Override
	public void paint(Graphics g) {
		//recuperamos el ancho del panel
		int ancho = this.getWidth();
		//recuperamos el alto del panel
		int alto = this.getHeight();

		if(img != null){
			//dibujamos el fonodo en el panel
			g.drawImage(img,0,0,ancho,alto,null);
		}

		super.paint(g);
	}

}
