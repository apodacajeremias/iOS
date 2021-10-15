package iOS.controlador.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ConexionReporte<E> {

	public JDialog ventanaReporte = new JDialog(new JFrame(), "Visualizar",
			true);

	public void generarReporte(List<E> lista, Map<String, Object> parametros,
			String nombreReporte) throws JRException {
		ventanaReporte.setSize(1000, 700);
		ventanaReporte.setLocationRelativeTo(null);
		ventanaReporte.setModal(true);
//		InputStream logo = ConexionReporte.class.getResourceAsStream("/img/LOGO_IOS.png");
//		parametros.put("logo", logo);
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("/img/LOGO_IOS.png"));
		    parametros.put("logo", image);
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		
		InputStream stream = ConexionReporte.class.getResourceAsStream("/iOS/vista/impresiones/"+ nombreReporte + ".jrxml");
		JasperReport report = JasperCompileManager.compileReport(stream);
		JasperPrint print = JasperFillManager.fillReport(report, parametros, new JRBeanCollectionDataSource(lista));
		JasperViewer viewer = new JasperViewer(print, true);
		ventanaReporte.getContentPane().add(viewer.getContentPane());
	}

}
