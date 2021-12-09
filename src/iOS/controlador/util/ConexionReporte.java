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
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ConexionReporte<E> {

	public JDialog ventanaReporte = new JDialog(new JFrame(), "Visualizar", true);
	private static final String PREFIX = "/iOS/vista/impresiones/";
	private static final String SUFFIX = ".jasper";

	public void generarReporte(List<E> lista, Map<String, Object> parametros, String nombreReporte) throws JRException {
		// Seleccionamos el logo
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("/img/LOGO_IOS.png"));
			parametros.put("logo", image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// The compiled report design.
		// El diseño compilado del reporte.
		String path_1 = PREFIX + nombreReporte + SUFFIX;
		System.out.println(path_1);
		InputStream path = ConexionReporte.class.getResourceAsStream(PREFIX + nombreReporte + SUFFIX);
		System.out.println(path);
		
		// Populate this list of beans as per your requirements.
		// Rellenar esta lista con información según requerimientos.
//		List<Bean> beans = new ArrayList<>();

		// Wrap the beans in a beans in a JRBeanCollectionDataSource.
		// Envolver la información en un JRBeanCollectionDataSource.
		JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(lista);

		// Fill the report, get the JasperPrint that can be exported to desired format.
		// Rellenar el reporte, obtener el JasperPrint que puede ser exportado en el formato deseado.
		JasperPrint jasperPrint = JasperFillManager.fillReport(path, parametros, datasource);

		// Shows in a window
		// Visualizar en una ventana
		JasperViewer viewer = new JasperViewer(jasperPrint, true);
		ventanaReporte.getContentPane().add(viewer.getContentPane());
		ventanaReporte.setSize(1000, 700);
		ventanaReporte.setLocationRelativeTo(null);
		ventanaReporte.setModal(true);
	}
}
