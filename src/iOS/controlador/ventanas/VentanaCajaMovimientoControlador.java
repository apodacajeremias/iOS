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
import iOS.modelo.entidades.Caja;
import iOS.modelo.entidades.CajaMovimiento;
import iOS.modelo.entidades.Cliente;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.CajaInterface;
import iOS.modelo.interfaces.ClienteInterface;
import iOS.modelo.interfaces.ColaboradorInterface;
import iOS.modelo.interfaces.PedidoInterface;
import iOS.vista.ventanas.VentanaCajaMovimiento;
import iOS.vista.ventanas.buscadores.BuscadorCliente;
import iOS.vista.ventanas.buscadores.BuscadorColaborador;

public class VentanaCajaMovimientoControlador implements ActionListener, MouseListener, KeyListener, PropertyChangeListener, ClienteInterface, ColaboradorInterface, AccionesABM, CajaInterface, PedidoInterface {
	private VentanaCajaMovimiento ventana;
	private CajaDao dao;
	private CajaInterface interfaz;
	private Caja caja;
	private CajaMovimiento cajaMovimiento;
	private Cliente cliente;
	private Colaborador colaborador;

	private List<CajaMovimiento> movimientos = new ArrayList<CajaMovimiento>();

	private boolean esIngreso;

	private String accion;
	private Colaborador colaboradorQueRegistra;
	private Pedido pedido;

	public VentanaCajaMovimientoControlador(VentanaCajaMovimiento ventana, boolean esIngreso) {
		this.ventana = ventana;
		this.esIngreso = esIngreso;
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
		ventana.getBtnBuscar().addActionListener(this);
		ventana.getLstAsociarPor().addPropertyChangeListener(this);
		ventana.getLstTipoMovimiento().addPropertyChangeListener(this);
		ventana.getLstTipoPago().addPropertyChangeListener(this);

		ventana.getLstTipoMovimiento().addMouseListener(this);
	}

	private void estadoInicial(boolean b) {
		EventosUtil.limpiarCampoPersonalizado(ventana.gettValorGs());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettValorRs());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettValorUs());
		EventosUtil.limpiarCampoPersonalizado(ventana.getTxtObservacion());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlDatosCriticos());

		if (esIngreso) {
			ingresar();
		} else
			retirar();
	}

	private void ingresar() {
		ventana.getLstTipoMovimiento().setSelectedValue("INGRESO", true);
		ventana.getLstTipoMovimiento().setEnabled(false);

	}

	private void retirar() {
		ventana.getLstTipoMovimiento().setSelectedValue("RETIRO", true);
		ventana.getLstTipoMovimiento().setEnabled(false);

		ventana.getLstAsociarPor().setSelectedValue("COLABORADOR", true);
		ventana.getLstAsociarPor().setEnabled(false);
	}

	private boolean esIngreso() {
		switch (ventana.getLstTipoMovimiento().getSelectedIndex()) {
		case 0:
			System.out.println("ingreso");
			return true;
		case 1:
			System.out.println("egreso");
			return false;
		default:
			return false;
		}

	}

	private String seAsociaPorCliente(){
		switch (ventana.getLstAsociarPor().getSelectedIndex()) {
		case 0:
			return "t";
		case 1:
			return "f";
		default:
			return "f";
		}
	}

	private void limitarListas() {
		if (esIngreso()) {
			switch (seAsociaPorCliente()) {
			case "t":
				ventana.getLstAsociarPor().setSelectedIndex(0);
				ventana.getLstTipoPago().setSelectedIndex(0);
				break;
			case "f":
				ventana.getLstAsociarPor().setSelectedIndex(1);
				ventana.getLstTipoPago().setSelectedIndex(0);
				break;
			default:
				break;
			}
		}
	}

	private void asociarPagoPor() {
		switch (ventana.getLstAsociarPor().getSelectedIndex()) {
		case 0:
			abrirBuscadorCliente();
			break;
		case 1:
			abrirBuscadorColaborador();
			break;
		default: break;
		}

	}

	private boolean validarFormulario() {
		if (!esIngreso() && seAsociaPorCliente().equals("t")) {
			return false;
		}
		if (seAsociaPorCliente().equals("f") && ventana.getLstTipoPago().getSelectedIndex() != 0) {
			return false;
		}
		if (cliente == null && colaborador == null) {
			ventana.getBtnBuscar().requestFocus();
			return false;
		}

		if ((ventana.gettValorGs().getValue()+ventana.gettValorRs().getValue()+ventana.gettValorUs().getValue())<= 0) {
			ventana.gettValorGs().error();
			ventana.gettValorRs().error();
			ventana.gettValorUs().error();
			return false;
		}

		return true;
	}

	public void guardar() {	
		if (!accion.equals("NUEVO")) {
			return;
		}
		if (caja == null) {
			return;
		}
		
		if (pedido != null) {
			if (ventana.gettValorGs().getValue() > pedido.getPrecioPagar()) {
				JOptionPane.showMessageDialog(ventana, "El valor de la entrega es superior al valor total del pedido. \n"
						+ "Valor a Pagar: "+EventosUtil.separadorMiles((double) pedido.getPrecioPagar()));
				return;
			}
		}
		if (!validarFormulario()) {
			return;
		}


		cajaMovimiento = new CajaMovimiento();
		cajaMovimiento.setColaboradorQueRegistra(colaboradorQueRegistra);
		cajaMovimiento.setCaja(caja);
		cajaMovimiento.setCliente(cliente);
		cajaMovimiento.setColaborador(colaborador);
		cajaMovimiento.setPedido(pedido);
		cajaMovimiento.setEsAnulado(false);
		cajaMovimiento.setObservacion(ventana.getTxtObservacion().getText());
		cajaMovimiento.setTipoValor(ventana.getLstTipoPago().getSelectedValue().toString());
		cajaMovimiento.setValorGS(ventana.gettValorGs().getValue());
		cajaMovimiento.setValorRS(ventana.gettValorRs().getValue());
		cajaMovimiento.setValorUS(ventana.gettValorUs().getValue());

		if (!esIngreso()) {
			cajaMovimiento.setEsRetiro(true);
		} else {
			cajaMovimiento.setEsRetiro(false);
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
		System.out.println(accion);
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
		if (evt.getSource() == ventana.getLstTipoMovimiento()) {
			limitarListas();
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
		if (e.getSource() == ventana.getLstTipoMovimiento()) {
			limitarListas();
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
		case "Buscar":
			asociarPagoPor();
			break;
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
		movimientos = caja.getCajaMovimientos();

	}
	//	private void gestionarCajaMovimiento() {
	//		if (cajaMovimiento == null) {
	//			return;
	//		}			
	//		ventana.gettValorGs().setValue(cajaMovimiento.getValorGS());
	//		ventana.gettValorRs().setValue(cajaMovimiento.getValorRS());
	//		ventana.gettValorUs().setValue(cajaMovimiento.getValorUS());
	//		ventana.getTxtObservacion().setText(cajaMovimiento.getObservacion());
	//		ventana.getRdAnulado().setSelected(cajaMovimiento.isEsAnulado());
	//		
	//		if (cajaMovimiento.getColaborador() != null) {
	//			ventana.getlDatosCriticos().setText(cajaMovimiento.getColaborador().toString());
	//			ventana.getLstAsociarPor().setSelectedValue("COLABORADOR", true);
	//		}
	//		
	//		if (cajaMovimiento.getCliente() != null) {
	//			ventana.getlDatosCriticos().setText(cajaMovimiento.getCliente().toString());
	//			ventana.getLstAsociarPor().setSelectedValue("CLIENTE", true);
	//		}
	//
	//	}

	@Override
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
		gestionarColaborador();
	}

	public void gestionarColaborador() {
		if (colaborador == null) {
			return;
		}

		cliente = null;

		ventana.getlDatosCriticos().setText(colaborador.getNombreCompleto());
	}

	private void abrirBuscadorColaborador() {
		BuscadorColaborador buscador = new BuscadorColaborador();
		buscador.setUpControlador();
		buscador.getControlador().setColaborador(colaborador);
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
	}
	private void abrirBuscadorCliente() {
		BuscadorCliente buscador = new BuscadorCliente();
		buscador.setUpControlador();
		buscador.getControlador().setColaborador(colaborador);
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
	}
}
