package iOS.modelo.singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import iOS.controlador.util.ConexionReporte;
import iOS.modelo.dao.CajaDao;
import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.Caja;
import iOS.modelo.entidades.CajaMovimiento;
import iOS.modelo.entidades.Cliente;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.entidades.Produccion;
import net.sf.jasperreports.engine.JRException;

public class Metodos {
	private static Metodos metodos;

	private Metodos() {
		// TODO Auto-generated constructor stub
	}

	public synchronized static Metodos getInstance() {
		if (metodos == null) {
			metodos = new Metodos();
		}
		return metodos;
	}

	public void anularRegistro(Object registro) {
		if (registro instanceof CajaMovimiento) {
			int aceptaAnular = JOptionPane.showConfirmDialog(null, "�Confirma la cancelaci�n de este pago?", "ATENCION",
					JOptionPane.YES_NO_OPTION);
			if (aceptaAnular == JOptionPane.YES_OPTION) {
				CajaMovimiento anular = (CajaMovimiento) registro;
//				if (anular.getCaja().isCajaCerrada()) {
//					JOptionPane.showMessageDialog(null, "El caja de este pago ya se encuentra cerrada.");
//					return;
//				}
				if (anular.isEsAnulado()) {
					JOptionPane.showMessageDialog(null, "Este pago ya se encuentra anulado, cancelando operacion...");
					return;
				}
				CajaDao dao = new CajaDao();
				anular.setEsAnulado(true);
				anular.setEstado(false);
				try {
					dao.modificar(anular.getCaja());
					dao.commit();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					dao.rollBack();
				}
			}
		}
		if (registro instanceof Pedido) {
			double pagos = 0;
			int aceptaAnular = JOptionPane.showConfirmDialog(null, "�Confirma la anulaci�n de este pedido?", "ATENCION",
					JOptionPane.YES_NO_OPTION);
			if (aceptaAnular == JOptionPane.YES_OPTION) {
				Pedido anular = (Pedido) registro;
				pagos = anular.getPagosPedido().stream()
						.filter(o -> o.isEsAnulado() == false && o.isEsRetiro() == false && o.isEsVale() == false)
						.mapToDouble(o -> o.getValorGS()).sum();
				if (pagos > 0) {
					JOptionPane.showMessageDialog(null,
							"Este pedido posee pagos vigente por lo que no es posible anular el pedido. Verifique con la administracion.");
					return;
				}

				PedidoDao dao = new PedidoDao();
				anular.setGeneraDeuda(false);
				anular.setEstado(false);
				try {
					dao.modificar(anular);
					dao.commit();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					dao.rollBack();
				}
			}
		}
		return;
	}

	public void generaDeuda(Object registro, boolean genera) {
		// TODO Auto-generated method stub
		if (registro instanceof Pedido) {
			Pedido r = (Pedido) registro;
			r.setGeneraDeuda(genera);
			r.setDeudaPaga(!genera);
			PedidoDao dao = new PedidoDao();
			try {
				dao.modificar(r);
				dao.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				dao.rollBack();
			}
		}
	}

	public void imprimirPedidoConfeccionIndividual(Pedido pedido) {
		if (pedido == null) {
			return;
		}
		int opcion = JOptionPane.showConfirmDialog(null, "�Desea imprimir?", "Impresion", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if (opcion == JOptionPane.OK_OPTION) {
			double sumaPagos = pedido.getSumaPagos();
			double precioPagar = pedido.getPrecioPagar();
			double valorPendiente = precioPagar - sumaPagos;

			HashMap<String, Object> parametros = new HashMap<>();
			parametros.put("nombreEmpresa", "iOS Comunicacion Visual");
			parametros.put("esPresupuesto", pedido.isEsPresupuesto() ? "PRESUPUESTO" : "PEDIDO");
			parametros.put("entrega", sumaPagos);
			parametros.put("valorPendiente", valorPendiente);

			List<Pedido> pedidos = new ArrayList<Pedido>();
			pedidos.add(pedido);

			ConexionReporte<Pedido> conexionReporte = new ConexionReporte<Pedido>();
			try {
				conexionReporte.generarReporte(pedidos, parametros, "PedidoImpreso3");
				conexionReporte.ventanaReporte.setLocationRelativeTo(null);
				conexionReporte.ventanaReporte.setVisible(true);
			} catch (JRException e) {
				e.printStackTrace();
			}
		}
	}

	public void imprimirPedidoCarteleriaIndividual(Pedido pedido) {
		if (pedido == null) {
			return;
		}
		int opcion = JOptionPane.showConfirmDialog(null, "�Desea imprimir?", "Impresion", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if (opcion == JOptionPane.OK_OPTION) {
			double sumaPagos = pedido.getSumaPagos();
			double precioPagar = pedido.getPrecioPagar();
			double valorPendiente = precioPagar - sumaPagos;
			HashMap<String, Object> parametros = new HashMap<>();
			parametros.put("nombreEmpresa", "iOS Comunicacion Visual");
			parametros.put("esPresupuesto", pedido.isEsPresupuesto() ? "PRESUPUESTO" : "PEDIDO");
			parametros.put("entrega", sumaPagos);
			parametros.put("valorPendiente", valorPendiente);

			List<Pedido> pedidos = new ArrayList<Pedido>();
			pedidos.add(pedido);

			ConexionReporte<Pedido> conexionReporte = new ConexionReporte<Pedido>();
			try {
				conexionReporte.generarReporte(pedidos, parametros, "PedidoImpreso4");
				conexionReporte.ventanaReporte.setLocationRelativeTo(null);
				conexionReporte.ventanaReporte.setVisible(true);
			} catch (JRException e) {
				e.printStackTrace();
			}
		}
	}

	public void imprimirReportePedido(List<Pedido> lista, String tipoReporte, String claseReporte) {
		if (lista.size() <= 0) {
			JOptionPane.showMessageDialog(null, "No hay registros para realizar la impresi�n.");
			return;
		}
		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("SOLICITANTE", Sesion.getInstance().getColaborador().toString());

		// Diario Mensual
		parametros.put("TIPO_REPORTE", tipoReporte);

		// Carteleria, costura o ambos
		parametros.put("CLASE_REPORTE", claseReporte);

		// Creando reportes
		ConexionReporte<Pedido> conexionReporte = new ConexionReporte<Pedido>();
		try {
			conexionReporte.generarReporte(lista, parametros, "ReportePedido3");
			conexionReporte.ventanaReporte.setLocationRelativeTo(null);
			conexionReporte.ventanaReporte.setVisible(true);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void imprimirReporteCaja(List<Caja> lista, String tipoReporte, String claseReporte) {
		if (lista.size() <= 0) {
			JOptionPane.showMessageDialog(null, "No hay registros para realizar la impresi�n.");
			return;
		}
		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("SOLICITANTE", Sesion.getInstance().getColaborador().toString());

		// Diario Mensual
		parametros.put("TIPO_REPORTE", tipoReporte);

		// Carteleria, costura o ambos
		parametros.put("CLASE_REPORTE", claseReporte);

		// TRUE para imprimir solamente vales, false para imprimir todo
		parametros.put("SOLO_VALE", false);
		parametros.put("TODOS_MOVIMIENTOS", true);

		// Creando reportes
		ConexionReporte<Caja> conexionReporte = new ConexionReporte<Caja>();
		try {
			conexionReporte.generarReporte(lista, parametros, "ReporteCaja3");
			conexionReporte.ventanaReporte.setLocationRelativeTo(null);
			conexionReporte.ventanaReporte.setVisible(true);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void imprimirReporteVale(List<CajaMovimiento> lista, String tipoReporte, String claseReporte) {
		if (lista.size() <= 0) {
			JOptionPane.showMessageDialog(null, "No hay registros para realizar la impresi�n.");
			return;
		}
		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("SOLICITANTE", Sesion.getInstance().getColaborador().toString());

		// Diario, Mensual, Anual
		parametros.put("TIPO_REPORTE", tipoReporte);

		// Carteleria, costura o ambos
		parametros.put("CLASE_REPORTE", claseReporte);

		// TRUE para imprimir solamente vales, false para imprimir todo
		parametros.put("SOLO_VALE", true);
		parametros.put("TODOS_MOVIMIENTOS", false);

		// Creando reportes
		ConexionReporte<CajaMovimiento> conexionReporte = new ConexionReporte<CajaMovimiento>();
		try {
			conexionReporte.generarReporte(lista, parametros, "ReporteCaja4Vales");
			conexionReporte.ventanaReporte.setLocationRelativeTo(null);
			conexionReporte.ventanaReporte.setVisible(true);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void imprimirReporteCliente(List<Cliente> lista, String tipoReporte, String claseReporte) {
		if (lista.size() <= 0) {
			JOptionPane.showMessageDialog(null, "No hay registros para realizar la impresi�n.");
			return;
		}
		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("SOLICITANTE", Sesion.getInstance().getColaborador().toString());

		// Deuda mayor o menos a 0
		parametros.put("TIPO_REPORTE", tipoReporte);

		// GENERAL o segun filtros
		parametros.put("CLASE_REPORTE", claseReporte);

		// Creando reportes
		ConexionReporte<Cliente> conexionReporte = new ConexionReporte<Cliente>();
		try {
			conexionReporte.generarReporte(lista, parametros, "Clientes");
			conexionReporte.ventanaReporte.setLocationRelativeTo(null);
			conexionReporte.ventanaReporte.setVisible(true);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void imprimirReporteProduccion(List<Produccion> lista, String tipoReporte, String claseReporte) {
		if (lista.size() <= 0) {
			JOptionPane.showMessageDialog(null, "No hay registros para realizar la impresi�n.");
			return;
		}
		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("SOLICITANTE", Sesion.getInstance().getColaborador().toString());

		// Diario Mensual
		parametros.put("TIPO_REPORTE", tipoReporte);

		// Carteleria, costura o ambos
		parametros.put("CLASE_REPORTE", claseReporte);

		// Creando reportes
		ConexionReporte<Produccion> conexionReporte = new ConexionReporte<Produccion>();
		try {
			conexionReporte.generarReporte(lista, parametros, "ReporteProduccion");
			conexionReporte.ventanaReporte.setLocationRelativeTo(null);
			conexionReporte.ventanaReporte.setVisible(true);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String aleatorio() {
		// Random instance
		Random random = new Random();
		int number = random.nextInt();
		// number stores the random integer in decimal form
		String hexadecimal = Integer.toHexString(number);
		return hexadecimal;
	}

}
