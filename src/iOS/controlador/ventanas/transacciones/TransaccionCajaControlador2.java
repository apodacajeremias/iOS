package iOS.controlador.ventanas.transacciones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.CajaDao;
import iOS.modelo.entidades.Caja;
import iOS.modelo.entidades.CajaMovimiento;
import iOS.modelo.interfaces.CajaInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaCajaMovimiento;
import iOS.vista.ventanas.VentanaCajaApertura;
import iOS.vista.ventanas.VentanaCajaCierre;
import iOS.vista.ventanas.VentanaCajaMovimiento;
import iOS.vista.ventanas.transacciones.TransaccionCaja2;

public class TransaccionCajaControlador2
		implements ActionListener, MouseListener, KeyListener, PropertyChangeListener, CajaInterface {
	private TransaccionCaja2 transaccion;
	private ModeloTablaCajaMovimiento mtCajaMovimiento;
	private CajaDao dao;
	private List<CajaMovimiento> movimientos = new ArrayList<CajaMovimiento>();
	private CajaMovimiento cajaMovimiento;
	private Caja caja;

	public TransaccionCajaControlador2(TransaccionCaja2 transaccion) {
		this.transaccion = transaccion;
		this.mtCajaMovimiento = new ModeloTablaCajaMovimiento();
		transaccion.getTableMovimientos().setModel(mtCajaMovimiento);
		tableMenu(transaccion.getTableMovimientos());

		dao = new CajaDao();
		setUpEvents();
		cajaAbierta();

	}

	private boolean cajaAbierta() {
		caja = dao.encontrarCajaHoy(Sesion.getInstance().getColaborador().getId());
		transaccion.getlColaborador().setText(Sesion.getInstance().getColaborador().toString());

		if (caja == null) {
			transaccion.getlEstadoCaja().setText("SIN CAJA");
			transaccion.getBtnEstadoCaja().setText("ABRIR CAJA");
			estadoInicial(false);
			return false;
		}
		if (caja.isCajaCerrada()) {
			transaccion.getlEstadoCaja().setText("CERRADA");
			transaccion.getBtnEstadoCaja().setText("ABRIR CAJA NUEVA");
			setCaja(caja);
			estadoInicial(false);
			return false;
		} else {
			transaccion.getlEstadoCaja().setText("ABIERTA");
			transaccion.getBtnEstadoCaja().setText("CERRAR CAJA");
			setCaja(caja);
			estadoInicial(true);
			return true;
		}
	}

	private void estadoInicial(boolean b) {
		EventosUtil.estadosBotones(transaccion.getBtnIngresarEntrega(), b);
		EventosUtil.estadosBotones(transaccion.getBtnIngresarPago(), b);
		EventosUtil.estadosBotones(transaccion.getBtnRetirarGasto(), b);
		EventosUtil.estadosBotones(transaccion.getBtnRetirarVale(), b);
	}

	private void estadoCaja() {
		if (caja == null) {
			abrirVentanaCajaApertura();
			return;
		}
		if (!caja.isCajaCerrada()) {
			abrirVentanaCajaCierre();
		}
	}

	private void setUpEvents() {
		this.transaccion.getTableMovimientos().addMouseListener(this);
		this.transaccion.getTableMovimientos().addPropertyChangeListener(this);
		this.transaccion.getBtnEstadoCaja().addActionListener(this);
		this.transaccion.getBtnIngresarEntrega().addActionListener(this);
		this.transaccion.getBtnIngresarPago().addActionListener(this);
		this.transaccion.getBtnRetirarGasto().addActionListener(this);
		this.transaccion.getBtnRetirarVale().addActionListener(this);

	}

	private void tableMenu(final JTable table) {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int r = table.rowAtPoint(e.getPoint());
				if (r >= 0 && r < table.getRowCount()) {
					table.setRowSelectionInterval(r, r);
				} else {
					table.clearSelection();
				}

				int rowindex = table.getSelectedRow();
				if (rowindex < 0) {
					return;
				}
				if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
					JPopupMenu popup = tablePopup(table, rowindex);
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	private JPopupMenu tablePopup(final JTable table, final int row) {
		JPopupMenu popup = new JPopupMenu("Popup");
		JMenuItem imprimirItem = new JMenuItem("Anular");
		imprimirItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				anular(row);

			}
		});
		popup.add(imprimirItem);
		return popup;
	}

	private void visualizarDetalles(int posicion) {
		if (posicion < 0) {
			return;
		}

		cajaMovimiento = movimientos.get(posicion);
	}

	private ArrayList<Double> ingresos(List<CajaMovimiento> movimientos) {
		ArrayList<Double> ingresos = new ArrayList<>();
		double gs = 0;
		double rs = 0;
		double us = 0;

		try {
			for (int i = 0; i < movimientos.size(); i++) {
				if (!movimientos.get(i).isEsRetiro() && !movimientos.get(i).isEsAnulado()) {
					gs = +gs + movimientos.get(i).getValorGS();
					rs = +rs + movimientos.get(i).getValorRS();
					us = +us + movimientos.get(i).getValorUS();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ingresos.add(gs);
		ingresos.add(rs);
		ingresos.add(us);

		transaccion.getlSaldoIngresoGS().setText(EventosUtil.separadorMiles(gs) + " Gs.");
		transaccion.getlSaldoIngresoRS().setText(EventosUtil.separadorDecimales(rs) + " Rs.");
		transaccion.getlSaldoIngresoUS().setText(EventosUtil.separadorDecimales(us) + " Us.");

		return ingresos;
	}

	private ArrayList<Double> retiros(List<CajaMovimiento> movimientos) {
		ArrayList<Double> retiros = new ArrayList<>();
		double gs = 0;
		double rs = 0;
		double us = 0;
		try {
			for (int i = 0; i < movimientos.size(); i++) {
				if (movimientos.get(i).isEsRetiro() && !movimientos.get(i).isEsAnulado()) {
					gs = +gs + movimientos.get(i).getValorGS();
					rs = +rs + movimientos.get(i).getValorRS();
					us = +us + movimientos.get(i).getValorUS();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		retiros.add(gs);
		retiros.add(rs);
		retiros.add(us);

		transaccion.getlSaldoRetiroGS().setText(EventosUtil.separadorMiles(gs) + " Gs.");
		transaccion.getlSaldoRetiroRS().setText(EventosUtil.separadorDecimales(rs) + " Rs.");
		transaccion.getlSaldoRetiroUS().setText(EventosUtil.separadorDecimales(us) + " Us.");

		return retiros;

	}

	private void anular(int row) {
		if (row < 0) {
			return;
		}
		int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea anular este pago?", "ATENCION",
				JOptionPane.YES_NO_OPTION);

		if (respuesta == JOptionPane.YES_OPTION) {
			cajaMovimiento = movimientos.get(row);

			cajaMovimiento.setEsAnulado(true);
			caja.setCajaMovimientos(movimientos);
			try {
				dao = new CajaDao();
				dao.modificar(caja);
				dao.commit();
				mtCajaMovimiento.fireTableDataChanged();
			} catch (Exception e) {
				dao.rollBack();
				EventosUtil.formatException(e);
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() == transaccion.getTableMovimientos()) {
			// ingresos();
			// retiros();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == transaccion.getTableMovimientos() && e.getClickCount() == 1) {
			visualizarDetalles(transaccion.getTableMovimientos().getSelectedRow());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "IngresarPago":
			abrirVentanaCajaMovimiento(true, "Pago");
			break;
		case "IngresarEntrega":
			abrirVentanaCajaMovimiento(true, "Entrega");
			break;
		case "RetirarVale":
			abrirVentanaCajaMovimiento(false, "Vale");
			break;
		case "RetirarGasto":
			abrirVentanaCajaMovimiento(false, "Gasto");
			break;
		case "EstadoCaja":
			estadoCaja();
			break;
		default:
			break;
		}

	}

	@Override
	public void setCaja(Caja caja) {
		this.caja = caja;
		gestionarCaja();
	}

	private void gestionarCaja() {
		if (caja == null) {
			return;
		}
		transaccion.getlSaldoInicialGS().setText(EventosUtil.separadorMiles(caja.getSaldoInicialGS()) + " Gs.");
		transaccion.getlSaldoInicialRS().setText(EventosUtil.separadorDecimales(caja.getSaldoInicialRS()) + " Rs.");
		transaccion.getlSaldoInicialUS().setText(EventosUtil.separadorDecimales(caja.getSaldoInicialUS()) + " Us.");

		try {
			movimientos = dao.ordenarMovimientosPorID(caja.getId());
			ingresos(movimientos);
			retiros(movimientos);
			mtCajaMovimiento.setMovimiento(movimientos);
			mtCajaMovimiento.fireTableDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void abrirVentanaCajaApertura() {
		VentanaCajaApertura ventana = new VentanaCajaApertura();
		if (EventosUtil.liberarAcceso(Sesion.getInstance().getColaborador(), ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
			transaccion.dispose();
			ventana.setVisible(true);
		}
	}

	private void abrirVentanaCajaMovimiento(boolean esIngreso, String operacion) {
		VentanaCajaMovimiento ventana = new VentanaCajaMovimiento();
		if (esIngreso == true) {
			if (EventosUtil.liberarAcceso(Sesion.getInstance().getColaborador(), ventana.modulo, "INGRESAR")) {
				ventana.setUpControlador(esIngreso, operacion);
				ventana.getControlador().setInterfaz(this);
				ventana.getControlador().setCaja(caja);

				if (operacion.equalsIgnoreCase("ENTREGA")) {
					if (!ventana.getControlador().entrega()) {
						return;
					}
				}

				ventana.setVisible(true);
				return;
			}
		}
		if (esIngreso == false) {
			if (EventosUtil.liberarAcceso(Sesion.getInstance().getColaborador(), ventana.modulo, "RETIRAR")) {
				ventana.setUpControlador(esIngreso, operacion);
				ventana.getControlador().setInterfaz(this);
				ventana.getControlador().setCaja(caja);
				ventana.setVisible(true);
				return;
			}
		}
	}

	private void abrirVentanaCajaCierre() {
		VentanaCajaCierre ventana = new VentanaCajaCierre();
		ventana.setUpControlador();
		ventana.getControlador().setCaja(caja);
		transaccion.dispose();
		ventana.setVisible(true);
	}

}
