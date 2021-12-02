package iOS.controlador.ventanas.buscadores;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import iOS.modelo.dao.ClienteDao;
import iOS.modelo.entidades.Cliente;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.ClienteInterface;
import iOS.vista.modelotabla.ModeloTablaCliente;
import iOS.vista.ventanas.VentanaCliente;
import iOS.vista.ventanas.VentanaCliente2;
import iOS.vista.ventanas.buscadores.BuscadorCliente;



public class BuscadorClienteControlador implements KeyListener, MouseListener, AccionesABM, ClienteInterface {

	// ATRIBUTOS
	private BuscadorCliente buscador;
	private ModeloTablaCliente mtCliente;
	private ClienteDao dao;
	private List<Cliente> lista;
	private ClienteInterface interfaz;
	private Cliente cliente;

	public void setInterfaz(ClienteInterface interfaz) {
		this.interfaz = interfaz;
	}

	// CONSTRUCTOR
	public BuscadorClienteControlador(BuscadorCliente bCliente) {
		this.buscador = bCliente;
		this.buscador.getToolbar().setAcciones(this);
		mtCliente = new ModeloTablaCliente();
		this.buscador.getTable().setModel(mtCliente);
		dao = new ClienteDao();
		setUpEvents();
		recuperarTodo();
	}

	// METODO QUE LEVANTA LOS EVENTOS
	private void setUpEvents() {
		buscador.gettBuscador().addKeyListener(this);
		buscador.getTable().addMouseListener(this);
	}

	// METODO QUE RECUPERA DATOS POR FILTRO EN EL BUSCADOR
	private void recuperarPorFiltro() {
		cliente = null;
		lista = dao.recuperarPorFiltro(buscador.gettBuscador().getText());
		mtCliente.setLista(lista);
		mtCliente.fireTableDataChanged();
	}

	private void recuperarTodo() {
		cliente = null;
		lista = dao.recuperarTodoOrdenadoPorNombre();
		mtCliente.setLista(lista);
		mtCliente.fireTableDataChanged();

	}

	private void seleccionarRegistro(int posicion) {
		if (posicion < 0) {
			cliente = null;
			return;
		}
		cliente = lista.get(posicion);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	// EVENTO DEL BOTON AL DAR CLIC RECUPERA POR FILTRO
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == buscador.gettBuscador() && e.getKeyCode() == KeyEvent.VK_ENTER) {
			recuperarPorFiltro();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	// EVENTO DEL MOUSE AL DAR DOBLE CLIC VA A SELECCIONAR UN REGISTRO
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == buscador.getTable()) {
			seleccionarRegistro(buscador.getTable().getSelectedRow());
		}
		// doble clic en un registro de la tabla
		if (e.getSource() == buscador.getTable() && e.getClickCount() == 2) {
			seleccionarRegistro(buscador.getTable().getSelectedRow());
			try {
				interfaz.setCliente(cliente);
				buscador.dispose();
			} catch (Exception e2) { 
				VentanaCliente2 ventana = new VentanaCliente2();
				ventana.setUpControlador();
				ventana.getControlador().setCliente(cliente);
				ventana.setVisible(true);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource() == buscador.getTable()) {
			seleccionarRegistro(buscador.getTable().getSelectedRow());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getSource() == buscador.getTable()) {
			seleccionarRegistro(buscador.getTable().getSelectedRow());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == buscador.getTable()) {
			seleccionarRegistro(buscador.getTable().getSelectedRow());
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == buscador.getTable()) {
			seleccionarRegistro(buscador.getTable().getSelectedRow());
		}
	}

	@Override
	public void nuevo() {
		VentanaCliente ventana = new VentanaCliente();
		ventana.setUpControlador();
		ventana.getControlador().nuevo();
		ventana.getControlador().setInterfaz(this);
		ventana.setVisible(true);
	}

	@Override
	public void modificar() {
		if (cliente == null) {
			return;
		}
		VentanaCliente ventana = new VentanaCliente();
		ventana.setUpControlador();
		ventana.getControlador().modificar();
		ventana.getControlador().setCliente(cliente);
		ventana.getControlador().setInterfaz(this);
		ventana.setVisible(true);
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCliente(Cliente cliente) {
		buscador.gettBuscador().setText(cliente.toString());
		recuperarPorFiltro();
	}
}
