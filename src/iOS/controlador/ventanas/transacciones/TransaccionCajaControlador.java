package iOS.controlador.ventanas.transacciones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.CajaDao;
import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.Caja;
import iOS.modelo.entidades.CajaMovimiento;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.interfaces.CajaInterface;
import iOS.modelo.interfaces.ColaboradorInterface;
import iOS.vista.modelotabla.ModeloTablaCajaMovimiento;
import iOS.vista.ventanas.VentanaCajaCierre;
import iOS.vista.ventanas.VentanaCajaMovimiento;
import iOS.vista.ventanas.transacciones.TransaccionCaja;

public class TransaccionCajaControlador implements ActionListener, MouseListener, KeyListener, PropertyChangeListener, CajaInterface, ColaboradorInterface{
	private TransaccionCaja transaccion;
	private ModeloTablaCajaMovimiento mtCajaMovimiento;
	private CajaDao dao;
	private List<CajaMovimiento> movimientos = new ArrayList<CajaMovimiento>();
	private CajaMovimiento cajaMovimiento;
	private Caja caja;
	private Colaborador colaborador;
	private ArrayList<Double> ingresos = new ArrayList<>();
	private ArrayList<Double> retiros = new ArrayList<>();
	private CajaDao cajaDao; 

	public TransaccionCajaControlador(TransaccionCaja transaccion) {
		this.transaccion = transaccion;
		this.mtCajaMovimiento = new ModeloTablaCajaMovimiento();
		transaccion.getTableMovimientos().setModel(mtCajaMovimiento);
		tableMenu(transaccion.getTableMovimientos());

		dao = new CajaDao();
		cajaDao = new CajaDao();

		setUpEvents();
	}


	private void setUpEvents() {
		this.transaccion.getTableMovimientos().addMouseListener(this);
		this.transaccion.getTableMovimientos().addPropertyChangeListener(this);
		this.transaccion.getBtnCerrarCaja().addActionListener(this);
		this.transaccion.getBtnEntrega().addActionListener(this);
		this.transaccion.getBtnIngresar().addActionListener(this);
		this.transaccion.getBtnRetirar().addActionListener(this);
		
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
		cajaMovimiento = movimientos.get(row);
		JPopupMenu popup = new JPopupMenu("Popup");		
		JMenuItem imprimirItem = new JMenuItem("Anular");
		imprimirItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				anular(cajaMovimiento);

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

	public ArrayList<Double> ingresos() {
		double gs = 0;
		double rs = 0;
		double us = 0;

		for (int i = 0; i < caja.getCajaMovimientos().size(); i++) {
			if (!caja.getCajaMovimientos().get(i).isEsRetiro() && !caja.getCajaMovimientos().get(i).isEsAnulado()) {
				gs =+ gs + caja.getCajaMovimientos().get(i).getValorGS();
				rs =+ rs + caja.getCajaMovimientos().get(i).getValorRS();
				us =+ us + caja.getCajaMovimientos().get(i).getValorUS();
			}

		}
		ingresos.add(gs);
		ingresos.add(rs);
		ingresos.add(us);

		transaccion.getlSaldoIngresoGS().setText(EventosUtil.separadorMiles(gs)+" Gs.");
		transaccion.getlSaldoIngresoRS().setText(EventosUtil.separadorDecimales(rs)+" Rs.");
		transaccion.getlSaldoIngresoUS().setText(EventosUtil.separadorDecimales(us)+" Us.");

		return ingresos;

	}

	public ArrayList<Double> retiros() {
		double gs = 0;
		double rs = 0;
		double us = 0;
		for (int i = 0; i < caja.getCajaMovimientos().size(); i++) {
			if (caja.getCajaMovimientos().get(i).isEsRetiro() && !caja.getCajaMovimientos().get(i).isEsAnulado()) {
				gs =+ gs + caja.getCajaMovimientos().get(i).getValorGS();
				rs =+ rs + caja.getCajaMovimientos().get(i).getValorRS();
				us =+ us + caja.getCajaMovimientos().get(i).getValorUS();
			}
		}		
		retiros.add(gs);
		retiros.add(rs);
		retiros.add(us);

		transaccion.getlSaldoRetiroGS().setText(EventosUtil.separadorMiles(gs)+" Gs.");
		transaccion.getlSaldoRetiroRS().setText(EventosUtil.separadorDecimales(rs)+" Rs.");
		transaccion.getlSaldoRetiroUS().setText(EventosUtil.separadorDecimales(us)+" Us.");

		return retiros;

	}

	private void anular(CajaMovimiento cm) {
		if (cm == null) {
			return;
		}
		int respuesta = JOptionPane
				.showConfirmDialog(null,
						"¿Desea anular este pago?",
						"ATENCION", JOptionPane.YES_NO_OPTION);

		if (respuesta == JOptionPane.YES_OPTION) {
			cm.setEsAnulado(true);
			movimientos.add(cm);
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
	
	public Caja cajaAbierta() {
		Date date = new Date();
		cajaDao = new CajaDao();
		Caja caja = cajaDao.encontrarCajaHoy(date, colaborador.getId());
		return caja;
	}
	
	private void entrega() {
		if (cajaAbierta() == null) {
			JOptionPane.showMessageDialog(transaccion, "Debe abrir el caja para realizar el pago de la entrega");
			return;
		}
		
		if (!(cajaAbierta() == null)) {
			
			String pedidoID = JOptionPane.showInputDialog("Introduzca la referencia del pedido");
			
			PedidoDao pedidoDao = new PedidoDao();
			
			Pedido pedido = pedidoDao.recuperarPorId(Integer.parseInt(pedidoID));
			if (pedido == null) {
				JOptionPane.showMessageDialog(transaccion, "No se ha encontrado el pedido indicado.");
				return;
			}
			
			if (verificarValidezPago(pedido) <= 0) {
				abrirVentanaCajaMovimiento(true, cajaAbierta(), pedido);
			}else {
				String mensaje = "El cliente ya ha realizado una entrega de "+EventosUtil.separadorMiles(verificarValidezPago(pedido));
				JOptionPane.showMessageDialog(transaccion, mensaje);
			}
			
		}

	}
	
	private double verificarValidezPago(Pedido pedido) {
		List<CajaMovimiento> pagos = cajaDao.recuperarPagoValido(pedido.getId());
		for (int i = 0; i < pagos.size(); i++) {
			if (!pagos.get(i).isEsAnulado()) {
				return pagos.get(i).getValorGS();
			}
		}
		return 0;
	}
	
	private void abrirVentanaCajaMovimiento(boolean esIngreso, Caja c, Pedido p) {
		VentanaCajaMovimiento ventana = new VentanaCajaMovimiento();
		if (esIngreso == true) {
			if (EventosUtil.liberarAcceso(colaborador, ventana.modulo, "INGRESAR")) {
				ventana.setUpControlador(esIngreso);
				ventana.getControlador().setCaja(c);
				ventana.getControlador().setColaborador(colaborador);
				ventana.getControlador().setCliente(p.getCliente());
				ventana.getControlador().setPedido(p);
				ventana.getTxtObservacion().setText("ENTREGA PEDIDO "+p.getId());
				ventana.getControlador().setInterfaz(this);
				ventana.setVisible(true);
				return;
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() == transaccion.getTableMovimientos()) {
			ingresos();
			retiros();
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
		if (e.getSource() == transaccion.getTableMovimientos() && e.getClickCount() == 2) {
			if (cajaMovimiento.getTipoValor().equalsIgnoreCase("INGRESO")) {
				abrirVentanaCajaMovimiento(true);
			} else {
				abrirVentanaCajaMovimiento(false);
			}
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
		case "IngresarValor":
			abrirVentanaCajaMovimiento(true);
			break;
		case "RetirarValor":
			abrirVentanaCajaMovimiento(false);
			break;
		case "CerrarCaja":
			abrirVentanaCajaCierre();
			break;
		case "Entrega":
			entrega();
			break;
		default:
			break;
		}

	}

	@Override
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;

		gestionarColaborador();
	}

	public void gestionarColaborador() {
		if (colaborador == null) {
			EventosUtil.estadosCampoPersonalizado(transaccion.getContentPane(), false);
			return;
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
		
		if (caja.isCajaCerrada()) {
			EventosUtil.estadosBotones(transaccion.getBtnCerrarCaja(), false);
			EventosUtil.estadosBotones(transaccion.getBtnIngresar(), false);
			EventosUtil.estadosBotones(transaccion.getBtnRetirar(), false);
		}
		transaccion.getlFecha().setText(EventosUtil.formatoFecha(caja.getFechaRegistro()));		
		transaccion.getlSaldoInicialGS().setText(EventosUtil.separadorMiles(caja.getSaldoInicialGS())+" Gs.");
		transaccion.getlSaldoInicialRS().setText(EventosUtil.separadorDecimales(caja.getSaldoInicialRS())+" Rs.");
		transaccion.getlSaldoInicialUS().setText(EventosUtil.separadorDecimales(caja.getSaldoInicialUS())+" Us.");

		movimientos = caja.getCajaMovimientos();
		movimientos.sort(Comparator.comparing(CajaMovimiento::getId));
		mtCajaMovimiento.setMovimiento(movimientos);
		mtCajaMovimiento.fireTableDataChanged();
	}

	private void abrirVentanaCajaMovimiento(boolean esIngreso) {
		VentanaCajaMovimiento ventana = new VentanaCajaMovimiento();
		if (esIngreso == false) {
			if (EventosUtil.liberarAcceso(colaborador, ventana.modulo, "RETIRAR")) {
				ventana.setUpControlador(esIngreso);
				ventana.getControlador().setInterfaz(this);
				ventana.getControlador().setCaja(caja);
				ventana.getControlador().setColaborador(colaborador);
				ventana.setVisible(true);
				return;
			}
		}
		
		if (esIngreso == true) {
			if (EventosUtil.liberarAcceso(colaborador, ventana.modulo, "INGRESAR")) {
				ventana.setUpControlador(esIngreso);
				ventana.getControlador().setInterfaz(this);
				ventana.getControlador().setCaja(caja);
				ventana.getControlador().setColaborador(colaborador);
				ventana.setVisible(true);
				return;
			}
		}

		ventana.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				ingresos();
				retiros();
				mtCajaMovimiento.fireTableDataChanged();
			}
		});

	}

	private void abrirVentanaCajaCierre() {
		VentanaCajaCierre ventana = new VentanaCajaCierre();
		ventana.setUpControlador();
		ventana.getControlador().setCaja(caja);
		transaccion.dispose();
		ventana.setVisible(true);
	}
}

