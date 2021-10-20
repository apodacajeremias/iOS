package iOS.controlador.ventanas.reportes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.CajaDao;
import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.CajaMovimiento;
import iOS.modelo.entidades.Cliente;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.interfaces.ClienteInterface;
import iOS.modelo.interfaces.ColaboradorInterface;
import iOS.vista.modelotabla.ModeloTablaCajaMovimiento;
import iOS.vista.modelotabla.ModeloTablaPedido;
import iOS.vista.ventanas.buscadores.BuscadorCliente;
import iOS.vista.ventanas.reportes.ReporteDeudasPagos;

public class ReporteDeudasPagosControlador implements ActionListener, ClienteInterface, ColaboradorInterface {
	private ReporteDeudasPagos reporte;

	private PedidoDao pedidoDao;
	private CajaDao cajaDao;

	private ModeloTablaPedido modeloTablaPedido;
	private ModeloTablaCajaMovimiento modeloTablaCaja;

	private Cliente cliente;
	
	private List<Pedido> pedidos = new ArrayList<Pedido>();
	private List<CajaMovimiento> pagos = new ArrayList<CajaMovimiento>();

	private Colaborador colaborador;



	public ReporteDeudasPagosControlador(ReporteDeudasPagos reporte) {
		this.reporte = reporte;
		
		modeloTablaCaja = new ModeloTablaCajaMovimiento();
		modeloTablaPedido = new ModeloTablaPedido();
		
		reporte.getTable().setModel(modeloTablaPedido);
		reporte.getTableDetalle().setModel(modeloTablaCaja);

		pedidoDao = new PedidoDao();
		cajaDao = new CajaDao();
		
		
		setUpEvents();
	}

	private void setUpEvents() {
		reporte.getBtnBuscarCliente().addActionListener(this);
		reporte.getBtnFiltrar().addActionListener(this);
		reporte.getBtnImprimir().addActionListener(this);

	}
	
	private void buscarPorCliente(Cliente c) {
		Double sumaPedidos = 0d;
		Double sumaPagos = 0d;
		try {
			pedidos = pedidoDao.recuperarPorCliente(c.getId());
			for (int i = 0; i < pedidos.size(); i++) {
				sumaPedidos += pedidos.get(i).getPrecioPagar();
			}		
			modeloTablaPedido.setPedidos(pedidos);
			modeloTablaPedido.fireTableDataChanged();
			reporte.getL1().setText("Deudas: "+EventosUtil.separadorMiles(sumaPedidos));
			
			pagos = cajaDao.recuperarPorCliente(c.getId());
			for (int i = 0; i < pagos.size(); i++) {
				sumaPagos += pagos.get(i).getValorGS();
			}
			modeloTablaCaja.setMovimiento(pagos);
			modeloTablaCaja.fireTableDataChanged();
			reporte.getL3().setText("Pagos: "+EventosUtil.separadorMiles(sumaPagos));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(reporte, "ERROR AL BUSCAR POR CLIENTE");
			return;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Imprimir":

			break;
		case "Filtrar":
			buscarPorCliente(cliente);
			break;
		case "Buscar":
			abrirBuscadorCliente();
			break;

		default:
			break;
		}

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
		reporte.getlCliente().setText(cliente.getNombreCompleto());

	}
	
	private void abrirBuscadorCliente() {
		BuscadorCliente ventana = new BuscadorCliente();
		ventana.setUpControlador();
		ventana.getControlador().setColaborador(colaborador);
		ventana.getControlador().setInterfaz(this);
		ventana.setVisible(true);
	}

	@Override
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
		gestionarColaborador();
	}

	@Override
	public void gestionarColaborador() {
		if (colaborador == null) {
			JOptionPane.showMessageDialog(reporte, "INICIE SESION");
			return;
		}
		
	}

}
