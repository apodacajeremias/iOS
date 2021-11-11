package iOS.controlador.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.CajaDao;
import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.Caja;
import iOS.modelo.entidades.CajaMovimiento;
import iOS.modelo.entidades.Cliente;
import iOS.modelo.entidades.Funcionario;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.CajaInterface;
import iOS.modelo.interfaces.ClienteInterface;
import iOS.modelo.interfaces.ColaboradorInterface;
import iOS.modelo.interfaces.PedidoInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.ventanas.VentanaCajaMovimiento;
import iOS.vista.ventanas.buscadores.BuscadorCliente;
import iOS.vista.ventanas.buscadores.BuscadorColaborador;

public class VentanaCajaMovimientoControlador implements ActionListener, MouseListener, KeyListener, PropertyChangeListener, ClienteInterface, ColaboradorInterface, AccionesABM, CajaInterface, PedidoInterface {
	private VentanaCajaMovimiento ventana;
	private boolean esIngreso;
	private String operacion;

	private CajaDao dao;
	private CajaInterface interfaz;
	private Caja caja;
	private CajaMovimiento cajaMovimiento;
	private Cliente cliente;
	private Funcionario colaborador;

	private List<CajaMovimiento> movimientos = new ArrayList<CajaMovimiento>();
	private String accion;
	private Pedido pedido;

	public VentanaCajaMovimientoControlador(VentanaCajaMovimiento ventana, boolean esIngreso, String operacion) {
		this.ventana = ventana;
		this.esIngreso = esIngreso;
		this.operacion = operacion;
		ventana.getMiToolBar().setAcciones(this);

		dao = new CajaDao();

		setUpEvents();
		estadoInicial(true);
		nuevo();
	}

	public void setInterfaz(CajaInterface interfaz) {
		this.interfaz = interfaz;
	}

	private void setUpEvents() {
		ventana.getRdCliente().addMouseListener(this);
		ventana.getRdColaborador().addMouseListener(this);

		ventana.getRd1().addMouseListener(this);
		ventana.getRd2().addMouseListener(this);
	}

	private void estadoInicial(boolean b) {		
		EventosUtil.limpiarCampoPersonalizado(ventana.gettValorGs());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettValorRs());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettValorUs());
		EventosUtil.limpiarCampoPersonalizado(ventana.getTxtObservacion());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlDatosCriticos());

		esIngreso();
		definirOperacion();
	}

	private void esIngreso() {
		if (esIngreso) {
			ventana.getRdIngreso().setSelected(true);
			ventana.getRdRetiro().setEnabled(false);
			ventana.getRd1().setText("Pago Corriente");
			ventana.getRd2().setText("Entrega");
		} else {
			ventana.getRdRetiro().setSelected(true);
			ventana.getRdIngreso().setEnabled(false);
			ventana.getRdColaborador().setSelected(true);
			ventana.getRdCliente().setEnabled(false);
			ventana.getRd1().setText("Gasto");
			ventana.getRd2().setText("Vale");
		}
	}

	private void definirOperacion() {
		switch (operacion) {
		case "Pago":
			ventana.getRd1().setSelected(true);
			ventana.getRd2().setEnabled(false);
			break;
		case "Entrega":
			ventana.getRd2().setSelected(true);
			ventana.getRd1().setEnabled(false);
			ventana.getRdCliente().setEnabled(false);
			ventana.getRdColaborador().setEnabled(false);
			break;
		case "Gasto":
			ventana.getRd1().setSelected(true);
			ventana.getRd2().setEnabled(false);
			break;
		case "Vale":
			ventana.getRd2().setSelected(true);
			ventana.getRd1().setEnabled(false);
			break;

		default:
			break;
		}

	}

	private String tipoPago() {
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
		}
		return null;

	}

	public boolean entrega() {
		if (caja == null) {
			JOptionPane.showMessageDialog(ventana, "Debe abrir el caja para realizar el pago de la entrega");
			return false;
		} else {
			String pedidoID = JOptionPane.showInputDialog("Introduzca la referencia del pedido");
			PedidoDao pedidoDao = new PedidoDao();
			Pedido pedido = pedidoDao.recuperarPorId(Integer.parseInt(pedidoID));
			if (pedido == null) {
				JOptionPane.showMessageDialog(ventana, "No se ha encontrado el pedido indicado.");
				return false;
			}
			setPedido(pedido);
			if (verificarValidezPago(pedido) <= 0) {
				return true;
			}else {
				String mensaje = "El cliente ya ha realizado una entrega de "+EventosUtil.separadorMiles(verificarValidezPago(pedido));
				JOptionPane.showMessageDialog(ventana, mensaje);
				return false;
			}

		}

	}

	private double verificarValidezPago(Pedido pedido) {
		List<CajaMovimiento> pagos = dao.recuperarEntregaPedido(pedido.getId());
		for (int i = 0; i < pagos.size(); i++) {
			if (!pagos.get(i).isEsAnulado()) {
				return pagos.get(i).getValorGS();
			}
		}
		return 0;
	}

	private boolean validarFormulario() {
		if (cliente == null && colaborador == null) {
			return false;
		}
		if (caja == null) {
			return false;
		}
		if (!accion.equals("NUEVO")) {
			return false;
		}
		if (pedido != null) {
			if (ventana.gettValorGs().getValue() > pedido.getPrecioPagar()) {
				JOptionPane.showMessageDialog(ventana, "El valor de la entrega es superior al valor total del pedido. \n"
						+ "Valor a Pagar: "+EventosUtil.separadorMiles((double) pedido.getPrecioPagar()));
				return false;
			}
		}
		if ((ventana.gettValorGs().getValue()+ventana.gettValorRs().getValue()+ventana.gettValorUs().getValue()) <= 0) {
			return false;
		}

		return true;
	}

	@Override
	public void guardar() {
		if (!validarFormulario()) {
			return;
		}

		cajaMovimiento = new CajaMovimiento();
		cajaMovimiento.setColaboradorQueRegistra(Sesion.getInstance().getColaborador());
		cajaMovimiento.setCaja(caja);
		cajaMovimiento.setCliente(cliente);
		cajaMovimiento.setColaborador(colaborador);
		cajaMovimiento.setPedido(pedido);
		cajaMovimiento.setEsAnulado(false);
		cajaMovimiento.setObservacion(ventana.getTxtObservacion().getText());
		cajaMovimiento.setValorGS(ventana.gettValorGs().getValue());
		cajaMovimiento.setValorRS(ventana.gettValorRs().getValue());
		cajaMovimiento.setValorUS(ventana.gettValorUs().getValue());
		cajaMovimiento.setEsRetiro(!esIngreso);
		cajaMovimiento.setObservacion(ventana.getTxtObservacion().getText());
		cajaMovimiento.setTipoValor(tipoPago());
		

		if (operacion.equalsIgnoreCase("VALE")) {
			cajaMovimiento.setEsVale(true);
		}
		
		movimientos.add(cajaMovimiento);
		caja.setCajaMovimientos(movimientos);


		try {
			dao = new CajaDao();
			dao.modificar(caja);
			dao.commit();
			interfaz.setCaja(caja);
			ventana.dispose();

		} catch (Exception e) {
			dao.rollBack();
			System.out.println("catch");
			EventosUtil.formatException(e);
		}

	}

	@Override
	public void nuevo() {
		estadoInicial(true);
		accion = "NUEVO";
	}

	@Override
	public void modificar() {
		estadoInicial(true);
		accion = "MODIFICAR";
	}

	@Override
	public void eliminar() {
		int respuesta = JOptionPane
				.showConfirmDialog(null,
						"¿Desea anular este pago?",
						"ATENCION", JOptionPane.YES_NO_OPTION);

		if (respuesta == JOptionPane.YES_OPTION) {
			cajaMovimiento.setEsAnulado(true);
			movimientos.add(cajaMovimiento);
			caja.setCajaMovimientos(movimientos);
			try {
				dao = new CajaDao();
				dao.modificar(caja);
				dao.commit();
			} catch (Exception e) {
				dao.rollBack();
			}
			ventana.dispose();
		}
	}

	@Override
	public void cancelar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
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
		if (e.getSource() == ventana.getRdCliente() && ventana.getRdCliente().isSelected()) {
			abrirBuscadorCliente();
		}

		if (e.getSource() == ventana.getRdColaborador() && ventana.getRdColaborador().isSelected()) {
			abrirBuscadorColaborador();
		}

		if (e.getSource() == ventana.getRd1() && ventana.getRd1().isSelected()) {
			System.out.println(ventana.getRd1().getText());
		}

		if (e.getSource() == ventana.getRd2() && ventana.getRd2().isSelected()) {
			System.out.println(ventana.getRd2().getText());
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
		case "Guardar":
			guardar();
			break;
		case "Anular":
			eliminar();
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
	}

	@Override
	public void setColaborador(Funcionario colaborador) {
		this.colaborador = colaborador;
		gestionarColaborador();
	}

	@Override
	public void gestionarColaborador() {
		if (colaborador == null) {
			return;
		}

		cliente = null;

		ventana.getlDatosCriticos().setText(colaborador.getNombreCompleto());
		ventana.getTxtObservacion().setText(operacion.toUpperCase()+" DE "+colaborador);
	}

	private void abrirBuscadorColaborador() {
		BuscadorColaborador buscador = new BuscadorColaborador();
		buscador.setUpControlador();
		buscador.getControlador().setInterfaz(this);
		buscador.setVisible(true);
	}

	@Override
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		gestionarCliente();
	}

	private void gestionarCliente() {
		if (cliente == null) {
			return;
		}

		colaborador = null;

		ventana.getlDatosCriticos().setText(cliente.getNombreCompleto());
		ventana.getTxtObservacion().setText(operacion.toUpperCase()+" DE "+cliente);
	}
	private void abrirBuscadorCliente() {
		BuscadorCliente buscador = new BuscadorCliente();
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

		setCliente(pedido.getCliente());
		ventana.getlDatosCriticos2().setText("ENTREGA DE PEDIDO "+pedido.getId());
		ventana.getTxtObservacion().setText("ENTREGA DE PEDIDO "+pedido.getId());
	}

}
