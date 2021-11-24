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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.CajaDao;
import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.Caja;
import iOS.modelo.entidades.CajaMovimiento;
import iOS.modelo.entidades.Cliente;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.interfaces.CajaInterface;
import iOS.modelo.interfaces.ClienteInterface;
import iOS.modelo.interfaces.ColaboradorInterface;
import iOS.modelo.interfaces.PedidoInterface;
import iOS.modelo.singleton.Metodos;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaCajaMovimiento;
import iOS.vista.ventanas.buscadores.BuscadorColaborador;
import iOS.vista.ventanas.buscadores.BuscadorPedido;
import iOS.vista.ventanas.transacciones.TransaccionCaja;

public class TransaccionCajaControlador implements ActionListener, MouseListener, KeyListener, PropertyChangeListener,
		CajaInterface, ClienteInterface, ColaboradorInterface, PedidoInterface {
	private TransaccionCaja ventana;
	private ModeloTablaCajaMovimiento mtCajaMovimiento;
	private String accion;

	private boolean esIngreso;
	private String operacion;

	private Caja caja;
	private CajaDao dao;

	private CajaMovimiento movimiento;
	private List<CajaMovimiento> movimientos = new ArrayList<CajaMovimiento>();

	private Cliente cliente;
	private Pedido pedido;
	private Colaborador colaborador;
	private boolean esVale;
	private double valorTotal;
	private double valorPago;
	private double valorPendiente;
	private ArrayList<Double> ingresos;
	private ArrayList<Double> retiros;
	private ArrayList<Double> saldoFinal;

	public TransaccionCajaControlador(TransaccionCaja transaccion) {
		this.ventana = transaccion;
		this.mtCajaMovimiento = new ModeloTablaCajaMovimiento();
		transaccion.getTableMovimientos().setModel(mtCajaMovimiento);
		tableMenu(transaccion.getTableMovimientos());

		dao = new CajaDao();
		setUpEvents();
		cajaAbierta();
	}

	private void estadoInicial(boolean b) {
		EventosUtil.estadosBotones(ventana.getBtnIngresarEntrega(), b);
		EventosUtil.estadosBotones(ventana.getBtnIngresarPago(), b);
		EventosUtil.estadosBotones(ventana.getBtnRetirarGasto(), b);
		EventosUtil.estadosBotones(ventana.getBtnRetirarVale(), b);
		EventosUtil.estadosBotones(ventana.getBtnConfirmar(), !b);
		EventosUtil.estadosBotones(ventana.getBtnCancelar(), !b);

		EventosUtil.limpiarCampoPersonalizado(ventana.getlDatosCriticos());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlDatosCriticos2());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlDatosCriticos3());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlDatosCriticos4());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlSaldoIngresoGS());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlSaldoIngresoRS());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlSaldoIngresoUS());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlSaldoInicialGS());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlSaldoInicialRS());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlSaldoInicialUS());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlSaldoRetiroGS());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlSaldoRetiroRS());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlSaldoRetiroUS());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlTitulo());

		EventosUtil.limpiarCampoPersonalizado(ventana.getRdCheque());
		EventosUtil.limpiarCampoPersonalizado(ventana.getRdCliente());
		EventosUtil.limpiarCampoPersonalizado(ventana.getRdColaborador());
		EventosUtil.limpiarCampoPersonalizado(ventana.getRdEfectivo());
		EventosUtil.limpiarCampoPersonalizado(ventana.getRdGiro());
		EventosUtil.limpiarCampoPersonalizado(ventana.getRdOtro());
		EventosUtil.limpiarCampoPersonalizado(ventana.getRdTarjeta());
		EventosUtil.limpiarCampoPersonalizado(ventana.getRdTransferencia());

		EventosUtil.limpiarCampoPersonalizado(ventana.gettValorGs());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettValorRs());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettValorUs());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettSaldoEntregadoGS());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettSaldoEntregadoRS());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettSaldoEntregadoUS());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettObservacion());

		ventana.getPanelGeneral().setVisible(false);
		ventana.getPanelValor1().setVisible(false);
		ventana.getPanelValor2().setVisible(false);
		
		cliente = null;
		colaborador = null;
		pedido = null;
	}

	private void setUpEvents() {
		this.ventana.getBtnIngresarEntrega().addActionListener(this);
		this.ventana.getBtnIngresarPago().addActionListener(this);
		this.ventana.getBtnRetirarGasto().addActionListener(this);
		this.ventana.getBtnRetirarVale().addActionListener(this);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnConfirmar().addActionListener(this);
		this.ventana.getBtnEstadoCaja().addActionListener(this);
		this.ventana.getBtnRetirarVale().addActionListener(this);

		this.ventana.getTableMovimientos().addMouseListener(this);
		this.ventana.getTableMovimientos().addPropertyChangeListener(this);
		this.ventana.getBtnEstadoCaja().addActionListener(this);

		this.ventana.getRdCliente().addMouseListener(this);
		this.ventana.getRdColaborador().addMouseListener(this);
	}

	private boolean cajaAbierta() {
		caja = dao.encontrarCajaHoy(Sesion.getInstance().getColaborador().getId());
		if (caja == null) {
			estadoInicial(false);
			ventana.getBtnEstadoCaja().setText("ABRIR CAJA");
			EventosUtil.estadosBotones(ventana.getBtnConfirmar(), false);
			EventosUtil.estadosBotones(ventana.getBtnCancelar(), false);
			return false;
		}
		if (caja.isCajaCerrada()) {
			estadoInicial(false);
			ventana.getBtnEstadoCaja().setText("REABRIR CAJA");
			setCaja(caja);
			EventosUtil.estadosBotones(ventana.getBtnConfirmar(), false);
			EventosUtil.estadosBotones(ventana.getBtnCancelar(), false);
			return false;
		} else {
			estadoInicial(true);
			ventana.getBtnEstadoCaja().setText("CERRAR CAJA");
			setCaja(caja);
			return true;
		}
	}

	private void abrirCaja() {
		estadoInicial(false);
		ventana.getlDatosCriticos().setText("CAJERO/A: " + Sesion.getInstance().getColaborador());
		ventana.getlDatosCriticos2().setText(EventosUtil.formatoFecha(new Date()));
		ventana.getlDatosCriticos3().setText("APERTURA DE CAJA");
		ventana.getlDatosCriticos4().setText("Ingrese los valores con los cuales abrirá el caja.");
		EventosUtil.estadosBotones(ventana.getBtnConfirmar(), true);
		EventosUtil.estadosBotones(ventana.getBtnCancelar(), true);
		ventana.getPanelGeneral().setVisible(true);
		ventana.getPanelValor1().setVisible(true);
	}

	private void reabrirCaja() {
		if (caja.getColaborador().getId() == Sesion.getInstance().getColaborador().getId()
				&& EventosUtil.formatoFecha(caja.getFechaRegistro()).equals(EventosUtil.formatoFecha(new Date()))) {

			ventana.getPanelGeneral().setVisible(true);
			ventana.getlDatosCriticos().setText("CAJERO/A: " + caja.getColaborador());
			ventana.getlDatosCriticos2().setText(EventosUtil.formatoFecha(caja.getFechaRegistro()));
			ventana.getlDatosCriticos3().setText("REAPERTURA DE CAJA");
			ventana.getlDatosCriticos4()
					.setText("La reapertura del caja sólo podrá ser realizar por el colaborador que la abrió.");
			ventana.getPanelGeneral().setVisible(true);
			EventosUtil.estadosBotones(ventana.getBtnConfirmar(), true);
			EventosUtil.estadosBotones(ventana.getBtnCancelar(), true);
			ventana.getPanelValor1().setVisible(false);
			ventana.getPanelValor2().setVisible(false);
			
		} else {
			JOptionPane.showMessageDialog(ventana,
					"El caja solo se puede reabrir por el mismo usuario que abrio y en el mismo dia.");
		}

//		ventana.getlDatosCriticos().setText("FUNCION NO DISPONIBLE");
//		ventana.getlDatosCriticos2().setText("FUNCION NO DISPONIBLE");
//		ventana.getlDatosCriticos3().setText("FUNCION NO DISPONIBLE");
//		ventana.getlDatosCriticos4().setText("FUNCION NO DISPONIBLE");
	}

	private void cerrarCaja() {
		if (caja.getColaborador().getId() == Sesion.getInstance().getColaborador().getId()
				&& EventosUtil.formatoFecha(caja.getFechaRegistro()).equals(EventosUtil.formatoFecha(new Date()))) {
			ventana.getlDatosCriticos().setText("CAJERO/A: " + caja.getColaborador());
			ventana.getlDatosCriticos2().setText(EventosUtil.formatoFecha(caja.getFechaRegistro()));
			ventana.getlDatosCriticos3().setText("CIERRE DE CAJA");
			ventana.getlDatosCriticos4().setText("En los primeros campos indique el valor que cuenta en caja.");
			ventana.gettObservacion().setText("En los campos de abajo ingrese el valor que entregará a la administracion.");
			ventana.getPanelGeneral().setVisible(true);
			ventana.getPanel_2().setVisible(true);
			EventosUtil.estadosBotones(ventana.getBtnConfirmar(), true);
			EventosUtil.estadosBotones(ventana.getBtnCancelar(), true);

			ventana.getPanelGeneral().setVisible(true);
			ventana.getPanelValor1().setVisible(true);
			ventana.getPanelValor2().setVisible(true);
		}
	}

	private void visualizarDetalles(int posicion) {
		if (posicion < 0) {
			return;
		}
		movimiento = movimientos.get(posicion);
		System.out.println(movimiento);
	}

	private void generarPago() {
		switch (operacion) {
		case "Pago":
			estadoInicial(false);
			esIngreso = true;
			esVale = false;
			ventana.getRdCliente().setText("Por cliente");
			ventana.getRdColaborador().setText("Por pedido");
			ventana.getRdCheque().setText("Cheque");
			ventana.getRdEfectivo().setText("Efectivo");
			ventana.getRdGiro().setText("Giro");
			ventana.getRdOtro().setText("Otro");
			ventana.getRdTarjeta().setText("Tarjeta");
			ventana.getRdTransferencia().setText("Transferencia");
			ventana.getPanelGeneral().setVisible(true);
			ventana.getPanelValor1().setVisible(true);
			break;
		case "Entrega":
			estadoInicial(false);
			esIngreso = true;
			esVale = false;
			ventana.getRdCliente().setText("Por cliente");
			ventana.getRdColaborador().setText("Por pedido");
			ventana.getRdCheque().setText("Cheque");
			ventana.getRdEfectivo().setText("Efectivo");
			ventana.getRdGiro().setText("Giro");
			ventana.getRdOtro().setText("Otro");
			ventana.getRdTarjeta().setText("Tarjeta");
			ventana.getRdTransferencia().setText("Transferencia");
			ventana.getPanelGeneral().setVisible(true);
			ventana.getPanelValor1().setVisible(true);
			break;
		case "Vale":
			estadoInicial(false);
			esIngreso = false;
			esVale = true;
			ventana.getRdCliente().setText("Por colaborador");
			ventana.getRdColaborador().setText("Por colaborador");
			ventana.getRdCheque().setText("Cheque");
			ventana.getRdEfectivo().setText("Efectivo");
			ventana.getRdGiro().setText("Giro");
			ventana.getRdOtro().setText("Otro");
			ventana.getRdTarjeta().setText("Tarjeta");
			ventana.getRdTransferencia().setText("Transferencia");
			ventana.getPanelGeneral().setVisible(true);
			ventana.getPanelValor1().setVisible(true);
			break;
		case "Gasto":
			estadoInicial(false);
			esIngreso = false;
			esVale = false;
			ventana.getRdCliente().setText("Por colaborador");
			ventana.getRdColaborador().setText("Por colaborador");
			ventana.getRdCheque().setText("Taxi/Envio");
			ventana.getRdEfectivo().setText("Luz/Agua");
			ventana.getRdGiro().setText("Taxi/Entrega");
			ventana.getRdOtro().setText("Otro");
			ventana.getRdTarjeta().setText("Combustible");
			ventana.getRdTransferencia().setText("Insumos");
			ventana.getPanelGeneral().setVisible(true);
			ventana.getPanelValor1().setVisible(true);
			break;
		default:
			System.out.println("No existe tipo de pago requerido = " + operacion);
			break;
		}
	}

	private String tipoValor() {
		if (ventana.getRdCheque().isSelected()) {
			return ventana.getRdCheque().getText();
		} else if (ventana.getRdEfectivo().isSelected()) {
			return ventana.getRdEfectivo().getText();
		} else if (ventana.getRdGiro().isSelected()) {
			return ventana.getRdGiro().getText();
		} else if (ventana.getRdTarjeta().isSelected()) {
			return ventana.getRdTarjeta().getText();
		} else if (ventana.getRdTransferencia().isSelected()) {
			return ventana.getRdTransferencia().getText();
		} else if (ventana.getRdOtro().isSelected()) {
			return ventana.getRdOtro().getText();
		}
		return null;
	}

	private ArrayList<Double> ingresos() {
		System.out.println(
				"INGRESO**********************************************************************************************************************");
		ingresos = new ArrayList<>();
		double gs = 0;
		double rs = 0;
		double us = 0;
		try {
			gs = movimientos.stream().filter(o -> o.isEsRetiro() == false && o.isEsAnulado() == false)
					.mapToDouble(o -> o.getValorGS()).sum();
			rs = movimientos.stream().filter(o -> o.isEsRetiro() == false && o.isEsAnulado() == false)
					.mapToDouble(o -> o.getValorRS()).sum();
			us = movimientos.stream().filter(o -> o.isEsRetiro() == false && o.isEsAnulado() == false)
					.mapToDouble(o -> o.getValorUS()).sum();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ventana.getlSaldoIngresoGS().setText(EventosUtil.separadorMiles(gs));
		ventana.getlSaldoIngresoGS().setText(EventosUtil.separadorDecimales(rs));
		ventana.getlSaldoIngresoGS().setText(EventosUtil.separadorDecimales(us));

		ingresos.add(gs);
		ingresos.add(rs);
		ingresos.add(us);

		return ingresos;

	}

	private ArrayList<Double> retiros() {
		System.out.println(
				"RETIRO**********************************************************************************************************************");
		retiros = new ArrayList<>();
		double gs = 0;
		double rs = 0;
		double us = 0;
		try {
			gs = movimientos.stream().filter(o -> o.isEsRetiro() == true && o.isEsAnulado() == false)
					.mapToDouble(o -> o.getValorGS()).sum();
			rs = movimientos.stream().filter(o -> o.isEsRetiro() == true && o.isEsAnulado() == false)
					.mapToDouble(o -> o.getValorRS()).sum();
			us = movimientos.stream().filter(o -> o.isEsRetiro() == true && o.isEsAnulado() == false)
					.mapToDouble(o -> o.getValorUS()).sum();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ventana.getlSaldoRetiroGS().setText(EventosUtil.separadorMiles(gs));
		ventana.getlSaldoRetiroRS().setText(EventosUtil.separadorDecimales(rs));
		ventana.getlSaldoRetiroUS().setText(EventosUtil.separadorDecimales(us));

		retiros.add(gs);
		retiros.add(rs);
		retiros.add(us);

		return retiros;
	}

	private ArrayList<Double> saldoFinal() {
		saldoFinal = new ArrayList<>();
		double gs = 0;
		double rs = 0;
		double us = 0;
		try {
			gs = ((ingresos.get(0) - retiros.get(0)) + caja.getSaldoInicialGS());
			rs = ((ingresos.get(1) - retiros.get(1)) + caja.getSaldoInicialRS());
			us = ((ingresos.get(2) - retiros.get(2)) + caja.getSaldoInicialUS());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		saldoFinal.add(gs);
		saldoFinal.add(rs);
		saldoFinal.add(us);

		return saldoFinal;
	}

	private void calcular() {
		ingresos();
		retiros();
		saldoFinal();
	}

	private boolean verificarValores() {
		calcular();
		double gs = ventana.gettValorGs().getValue() - saldoFinal.get(0);
		double rs = ventana.gettValorRs().getValue() - saldoFinal.get(1);
		double us = ventana.gettValorUs().getValue() - saldoFinal.get(2);
		if ((gs == 0) && (rs == 0) && (us == 0)) {
			return true;
		}
		if ((gs > 0) || (rs > 0) || (us > 0)) {
			JOptionPane.showMessageDialog(ventana,
					"Se ha detectado valores SOBRANTE en caja. Verifique nuevamente el monto que posee en caja");
			return false;
		}
		if ((gs < 0) || (rs < 0) || (us < 0)) {
			JOptionPane.showMessageDialog(ventana,
					"Se ha detectado valores FALTANTE en caja. Verifique nuevamente el monto que posee en caja");
			return false;
		}
		return false;
	}

	private void buscarPedidoPorNumero() {
		if (caja == null) {
			JOptionPane.showMessageDialog(ventana, "Debe abrir el caja para realizar el pago de la entrega");
			return;
		} else {
			String pedidoID = JOptionPane.showInputDialog("Introduzca la referencia del pedido");
			PedidoDao pedidoDao = new PedidoDao();
			Pedido pedido = pedidoDao.recuperarPorId(Integer.parseInt(pedidoID));
			if (pedido == null) {
				JOptionPane.showMessageDialog(ventana, "No se ha encontrado el pedido indicado.");
				return;
			}
			setPedido(pedido);
		}
	}

	private boolean validarFormulario() {
		if (pedido != null && cliente == null) {
			JOptionPane.showMessageDialog(ventana, "No se ha encontrado el cliente relacionado al pedido.");
			return false;
		}

		if (colaborador == null && pedido == null && cliente == null) {
			JOptionPane.showMessageDialog(ventana, "No se ha asociado el pago por ningun medio disponible.");
			return false;
		}
		if (caja == null) {
			ventana.dispose();
			JOptionPane.showMessageDialog(ventana, "Sin referencia de caja, cerrando ventana...");
			return false;
		}

		if (caja.isCajaCerrada()) {
			JOptionPane.showMessageDialog(ventana, "Caja cerrada, no puede ejecutar movimientos.");
			return false;
		}

		if (tipoValor().isEmpty()) {
			return false;
		}
		if (esIngreso == true && esVale == true) {
			ventana.dispose();
			JOptionPane.showMessageDialog(ventana, "Ha ocurrido un error, cerrando ventana...");
			return false;
		}
		if ((ventana.gettValorGs().getValue() + ventana.gettValorRs().getValue()
				+ ventana.gettValorUs().getValue()) <= 0) {
			ventana.gettValorGs().requestFocusInWindow();
			return false;
		}
		if (pedido!= null && (ventana.gettValorGs().getValue() + ventana.gettValorRs().getValue()
				+ ventana.gettValorUs().getValue()) > valorPendiente) {
			JOptionPane.showMessageDialog(ventana, "El valor ingresado es superior al valor pendiente");
			ventana.gettValorGs().requestFocusInWindow();
			return false;
		}

		System.out.println(ventana.getButtonGroup_1().getSelection().toString());
		System.out.println(ventana.getButtonGroup_2().getSelection().toString());

		return true;
	}

	public void guardar() {
		switch (accion) {
		case "ABRIR":
			if (caja == null) {
				caja = new Caja();
				caja.setCajaCerrada(false);
				caja.setColaborador(Sesion.getInstance().getColaborador());
				caja.setSaldoInicialGS(ventana.gettValorGs().getValue());
				caja.setSaldoInicialRS(ventana.gettValorRs().getValue());
				caja.setSaldoInicialUS(ventana.gettValorUs().getValue());
				int opcion = JOptionPane.showConfirmDialog(ventana, "¿Confirmar apertura?", "APERTURA",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (opcion == JOptionPane.OK_OPTION) {
					try {
						dao.insertar(caja);
						dao.commit();
						JOptionPane.showMessageDialog(ventana, "APERTURA DE CAJA EXITOSA");
						this.ventana.dispose();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						dao.rollBack();
					}
				}
				return;
			}
			break;
		case "REABRIR":
			if (caja.isCajaCerrada()) {
				caja.setCajaCerrada(false);
				caja.setFechaModificado(new Date());
				int opcion = JOptionPane.showConfirmDialog(ventana, "¿Confirmar reapertura de caja?", "APERTURA",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (opcion == JOptionPane.OK_OPTION) {
					try {
						dao.modificar(caja);
						dao.commit();
						JOptionPane.showMessageDialog(ventana, "REAPERTURA EXITOSA.");
						this.ventana.dispose();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						dao.rollBack();
					}
				}
				return;
			}
			break;
		case "CERRAR":
			if (!caja.isCajaCerrada()) {
				if (!verificarValores()) {
					int o = JOptionPane.showConfirmDialog(ventana, "Desea cerrar el caja de todas maneras?", "APERTURA",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if (o == JOptionPane.OK_OPTION) {
						
					} else {
						return;
					}
				}
				caja.setCajaCerrada(true);
				caja.setFechaModificado(new Date());
				caja.setSaldoDeclaradoGS(ventana.gettValorGs().getValue());
				caja.setSaldoDeclaradoRS(ventana.gettValorRs().getValue());
				caja.setSaldoDeclaradoUS(ventana.gettValorUs().getValue());
				caja.setSaldoEntregadoGS(ventana.gettSaldoEntregadoGS().getValue());
				caja.setSaldoEntregadoRS(ventana.gettSaldoEntregadoRS().getValue());
				caja.setSaldoEntregadoUS(ventana.gettSaldoEntregadoUS().getValue());
				int opcion = JOptionPane.showConfirmDialog(ventana, "¿Confirmar cierre de caja?", "APERTURA",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (opcion == JOptionPane.OK_OPTION) {
					try {
						dao.modificar(caja);
						dao.commit();
						JOptionPane.showMessageDialog(ventana, "CIERRE EXITOSO.");
						this.ventana.dispose();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						dao.rollBack();
					}
				}
				return;
			}
			break;
		case "PAGO":
			if (!validarFormulario()) {
				return;
			}

			movimiento = new CajaMovimiento();
			movimiento.setCaja(caja);
			movimiento.setCliente(cliente);
			movimiento.setColaborador(colaborador);
			movimiento.setColaboradorQueRegistra(Sesion.getInstance().getColaborador());
			movimiento.setEsAnulado(false);
			movimiento.setEsRetiro(!esIngreso);
			movimiento.setEsVale(esVale);
			movimiento.setObservacion(ventana.gettObservacion().getText());
			movimiento.setPedido(pedido);
			movimiento.setTipoValor(tipoValor());
			movimiento.setValorGS(ventana.gettValorGs().getValue());
			movimiento.setValorRS(ventana.gettValorRs().getValue());
			movimiento.setValorUS(ventana.gettValorUs().getValue());
			movimientos.add(movimiento);
			caja.setCajaMovimientos(movimientos);
			
			calcular();

			caja.setSaldoFinalGS(saldoFinal.get(0));
			caja.setSaldoFinalRS(saldoFinal.get(1));
			caja.setSaldoFinalUS(saldoFinal.get(2));

			caja.setSaldoIngresadoGS(ingresos.get(0));
			caja.setSaldoIngresadoRS(ingresos.get(1));
			caja.setSaldoIngresadoUS(ingresos.get(2));

			caja.setSaldoRetiradoGS(retiros.get(0));
			caja.setSaldoRetiradoRS(retiros.get(1));
			caja.setSaldoRetiradoUS(retiros.get(2));

			try {
				dao.modificar(caja);
				dao.commit();
				setCaja(caja);
			} catch (Exception e) {
				dao.rollBack();
				e.printStackTrace();
			}
			break;
		default:
			JOptionPane.showMessageDialog(ventana, "Error al guardar, verifique...");
			break;
		}

	}

	private void cancelar() {
		if (caja == null) {
			estadoInicial(false);
			EventosUtil.estadosBotones(ventana.getBtnConfirmar(), false);
			EventosUtil.estadosBotones(ventana.getBtnCancelar(), false);
		}
		if (caja.isCajaCerrada()) {
			estadoInicial(false);
			setCaja(caja);
			EventosUtil.estadosBotones(ventana.getBtnConfirmar(), false);
			EventosUtil.estadosBotones(ventana.getBtnCancelar(), false);
		} else {
			estadoInicial(true);
			setCaja(caja);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() == ventana.getTableMovimientos()) {
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
		if (e.getSource() == ventana.getTableMovimientos() && e.getClickCount() == 1) {
			visualizarDetalles(ventana.getTableMovimientos().getSelectedRow());
		}

		if (e.getSource() == ventana.getRdCliente() && e.getClickCount() > 0) {
			switch (operacion) {
			case "Pago":
				abrirBuscadorCliente();
				break;
			case "Entrega":
				abrirBuscadorCliente();
				break;
			case "Vale":
			case "Gasto":
				abrirBuscadorColaborador();
				break;
			default:
				break;
			}
		}
		if (e.getSource() == ventana.getRdColaborador() && e.getClickCount() > 0) {
			switch (operacion) {
			case "Pago":
				buscarPedidoPorNumero();
				break;
			case "Entrega":
				buscarPedidoPorNumero();
				break;
			case "Vale":
			case "Gasto":
				abrirBuscadorColaborador();
				break;
			default:
				break;
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
		case "Confirmar":
			guardar();
			break;
		case "Cancelar":
			cancelar();
			break;
		case "IngresarPago":
			operacion = "Pago";
			accion = "PAGO";
			generarPago();
			break;
		case "IngresarEntrega":
			operacion = "Entrega";
			accion = "PAGO";
			generarPago();
			break;
		case "RetirarVale":
			operacion = "Vale";
			accion = "PAGO";
			generarPago();
			break;
		case "RetirarGasto":
			operacion = "Gasto";
			accion = "PAGO";
			generarPago();
			break;
		case "EstadoCaja":
			switch (ventana.getBtnEstadoCaja().getText()) {
			case "ABRIR CAJA":
				abrirCaja();
				accion = "ABRIR";
				break;
			case "CERRAR CAJA":
				cerrarCaja();
				accion = "CERRAR";
				break;
			case "REABRIR CAJA":
				reabrirCaja();
				accion = "REABRIR";
				break;
			default:
				break;
			}
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
		if (caja.isCajaCerrada()) {
			estadoInicial(false);
		} else {
			estadoInicial(true);
		}
		ventana.getlSaldoInicialGS().setText(EventosUtil.separadorMiles(caja.getSaldoInicialGS()) + " Gs.");
		ventana.getlSaldoInicialRS().setText(EventosUtil.separadorDecimales(caja.getSaldoInicialRS()) + " Rs.");
		ventana.getlSaldoInicialUS().setText(EventosUtil.separadorDecimales(caja.getSaldoInicialUS()) + " Us.");

		ventana.getlSaldoIngresoGS().setText(EventosUtil.separadorMiles(caja.getSaldoIngresadoGS()) + " Gs.");
		ventana.getlSaldoIngresoRS().setText(EventosUtil.separadorDecimales(caja.getSaldoIngresadoRS()) + " Rs.");
		ventana.getlSaldoIngresoUS().setText(EventosUtil.separadorDecimales(caja.getSaldoIngresadoUS()) + " Us.");

		ventana.getlSaldoRetiroGS().setText(EventosUtil.separadorMiles(caja.getSaldoRetiradoGS()) + " Gs.");
		ventana.getlSaldoRetiroRS().setText(EventosUtil.separadorDecimales(caja.getSaldoRetiradoRS()) + " Rs.");
		ventana.getlSaldoRetiroUS().setText(EventosUtil.separadorDecimales(caja.getSaldoRetiradoUS()) + " Us.");

		movimientos = caja.getCajaMovimientos().stream().filter(c -> c.isEsAnulado() == false)
				.collect(Collectors.toList());
		try {
			movimientos.sort(Comparator.comparing(CajaMovimiento::getId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mtCajaMovimiento.setMovimiento(movimientos);
		mtCajaMovimiento.fireTableDataChanged();
	}

	// private void abrirVentanaCajaApertura() {
	// VentanaCajaApertura ventana = new VentanaCajaApertura();
	// if (EventosUtil.liberarAcceso(Sesion.getInstance().getColaborador(),
	// ventana.modulo, "ABRIR")) {
	// ventana.setUpControlador();
	// transaccion.dispose();
	// ventana.setVisible(true);
	// }
	// }

	// private void abrirVentanaCajaMovimiento(boolean esIngreso, String operacion)
	// {
	// VentanaCajaMovimiento ventana = new VentanaCajaMovimiento();
	// if (esIngreso == true) {
	// if (EventosUtil.liberarAcceso(Sesion.getInstance().getColaborador(),
	// ventana.modulo, "INGRESAR")) {
	// ventana.setUpControlador(esIngreso, operacion);
	// ventana.getControlador().setInterfaz(this);
	// ventana.getControlador().setCaja(caja);
	//
	// if (operacion.equalsIgnoreCase("ENTREGA")) {
	// if (!ventana.getControlador().entrega()) {
	// return;
	// }
	// }
	//
	// ventana.setVisible(true);
	// return;
	// }
	// }
	// if (esIngreso == false) {
	// if (EventosUtil.liberarAcceso(Sesion.getInstance().getColaborador(),
	// ventana.modulo, "RETIRAR")) {
	// ventana.setUpControlador(esIngreso, operacion);
	// ventana.getControlador().setInterfaz(this);
	// ventana.getControlador().setCaja(caja);
	// ventana.setVisible(true);
	// return;
	// }
	// }
	// }

	private void abrirBuscadorColaborador() {
		BuscadorColaborador buscador = new BuscadorColaborador();
		buscador.setUpControlador();
		buscador.getControlador().setInterfaz(this);
		buscador.setVisible(true);
	}

	private void abrirBuscadorCliente() {
		BuscadorPedido buscador = new BuscadorPedido();
		buscador.setUpControlador();
		buscador.getControlador().setInterfaz(this);
		buscador.setVisible(true);
	}

	@Override
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
		gestionarPedido();
	}

	private void gestionarPedido() {
		if (pedido == null) {
			return;
		}

		movimiento = null;
		colaborador = null;

		cliente = pedido.getCliente();
		setCliente(cliente);

		valorTotal = pedido.getPrecioPagar();
		valorPago = pedido.getPagosPedido().stream().filter(o -> o.isEsRetiro() == false && o.isEsAnulado() == false)
				.mapToDouble(o -> o.getValorGS()).sum();
		valorPendiente = valorTotal - valorPago;
		String t = operacion.toUpperCase() + " PEDIDO " + pedido.getId() + " DE CLIENTE " + cliente.toString();
		ventana.getlDatosCriticos3().setText("VENDEDOR: " + pedido.getColaborador().toString());
		ventana.getlDatosCriticos4()
				.setText("VALOR TOTAL " + EventosUtil.separadorMiles((double) valorTotal) + " VALOR PAGO "
						+ EventosUtil.separadorMiles(valorPago) + " VALOR PENDIENTE "
						+ EventosUtil.separadorMiles(valorPendiente));

		ventana.gettObservacion().setText(t);
	}

	@Override
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
		gestionarColaborador();
	}

	@Override
	public void gestionarColaborador() {
		if (colaborador == null) {
			return;
		}

		cliente = null;
		pedido = null;
		movimiento = null;
		String t = operacion.toUpperCase() + " DE COLABORADOR " + colaborador.toString();

		ventana.getlDatosCriticos().setText("COLABORADOR: " + colaborador.toString());
		ventana.getlDatosCriticos2()
				.setText("DOCUMENTO: " + colaborador.getIdentificacion() == null ? "Sin información."
						: colaborador.getIdentificacion() + "/ CONTACTO: " + colaborador.getContacto() == null
								? "Sin información."
								: colaborador.getContacto());
		ventana.gettObservacion().setText(t);

	}

	@Override
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		gestionarCliente();
	}

	private void gestionarCliente() {
		if (cliente == null) {
			JOptionPane.showMessageDialog(ventana, "No se ha encontrado al cliente relacionado al pedido.");
		}
		colaborador = null;
		movimiento = null;

		ventana.getlDatosCriticos().setText("CLIENTE: " + cliente.toString());
		ventana.getlDatosCriticos2()
				.setText("DOCUMENTO: " + cliente.getIdentificacion() + " TELEFONO " + cliente.getContacto());

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
				Metodos.getInstance().anularRegistro(movimientos.get(row));

			}
		});
		popup.add(imprimirItem);
		return popup;
	}

}
