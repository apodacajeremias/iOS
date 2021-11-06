package iOS.controlador.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import iOS.modelo.dao.CajaDao;
import iOS.modelo.entidades.CajaMovimiento;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.singleton.Sesion;
import net.sf.jasperreports.engine.JRException;

public class Impresiones {
	
	private static Impresiones impresion;
	
	private Impresiones() {
	}
	
	public synchronized static Impresiones getInstance() {
		if (impresion == null) {
			impresion = new Impresiones();
		}
		return impresion;
	}
	
	public void imprimirPedidoConfeccionIndividual(Pedido pedido, JDialog ventana) {
		if (pedido == null) {
			return;
		}		
		int opcion = JOptionPane.showConfirmDialog(null,"¿Desea imprimir?",
				"Impresion", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if (opcion == JOptionPane.OK_OPTION) {
			HashMap<String, Object> parametros = new HashMap<>();
			parametros.put("nombreEmpresa", "iOS Comunicacion Visual");
			parametros.put("esPresupuesto", esPresupuesto(pedido));
			parametros.put("entrega", verificarValidezPago(pedido));

			List<Pedido> pedidos = new ArrayList<Pedido>();
			pedidos.add(pedido);

			ConexionReporte<Pedido> conexionReporte = new ConexionReporte<Pedido>();
			try {
				conexionReporte.generarReporte(pedidos, parametros, "PedidoImpreso3");
				conexionReporte.ventanaReporte.setLocationRelativeTo(ventana);
				ventana.dispose();
				conexionReporte.ventanaReporte.setVisible(true);
			} catch (JRException e) {
				e.printStackTrace();
			}	
		} else {
			return;
		}	
	}
	
	public void imprimirPedidoCarteleriaIndividual(Pedido pedido, JDialog ventana) {
		if (pedido == null) {
			return;
		}		
		int opcion = JOptionPane.showConfirmDialog(null,"¿Desea imprimir?",
				"Impresion", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if (opcion == JOptionPane.OK_OPTION) {
			HashMap<String, Object> parametros = new HashMap<>();
			parametros.put("nombreEmpresa", "iOS Comunicacion Visual");
			parametros.put("esPresupuesto", esPresupuesto(pedido));
			parametros.put("entrega", verificarValidezPago(pedido));

			List<Pedido> pedidos = new ArrayList<Pedido>();
			pedidos.add(pedido);

			ConexionReporte<Pedido> conexionReporte = new ConexionReporte<Pedido>();
			try {
				conexionReporte.generarReporte(pedidos, parametros, "PedidoImpreso4");
				conexionReporte.ventanaReporte.setLocationRelativeTo(ventana);
				ventana.dispose();
				conexionReporte.ventanaReporte.setVisible(true);
			} catch (JRException e) {
				e.printStackTrace();
			}	
		} else {
			return;
		}	
	}
	
	public void imprimirReportePedido(List<Pedido> lista, String tipoReporte, String claseReporte, JDialog reporte) {		
		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("solicitante", Sesion.getInstance().getColaborador().toString());
		parametros.put("tipoReporte", tipoReporte);
		parametros.put("claseReporte", claseReporte);


		// Creando reportes
		ConexionReporte<Pedido> conexionReporte = new ConexionReporte<Pedido>();
		try {
			conexionReporte.generarReporte(lista, parametros, "ReportePedido3");
			conexionReporte.ventanaReporte.setLocationRelativeTo(reporte);
			conexionReporte.ventanaReporte.setVisible(true);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private String esPresupuesto(Pedido pedido) {
		if (pedido.isEsPresupuesto()) {
			return "PRESUPUESTO";
		} else {
			return "PEDIDO";
		}
	}	
	
	private double verificarValidezPago(Pedido pedido) {
		List<CajaMovimiento> pagos = new ArrayList<CajaMovimiento>();
		CajaDao cajaDao = new CajaDao();
		pagos = cajaDao.recuperarEntregaPedido(pedido.getId());
		for (int i = 0; i < pagos.size(); i++) {
			if (!pagos.get(i).isEsAnulado()) {
				return pedido.getPagosPedido().get(i).getValorGS();
			}
		}
		return 0;
	}
	
	

}
