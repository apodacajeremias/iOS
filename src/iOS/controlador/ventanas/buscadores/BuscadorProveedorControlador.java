package iOS.controlador.ventanas.buscadores;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import iOS.modelo.dao.ProveedorDao;
import iOS.modelo.entidades.Proveedor;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.ProveedorInterface;
import iOS.vista.modelotabla.ModeloTablaProveedor;
import iOS.vista.ventanas.VentanaProveedor;
import iOS.vista.ventanas.buscadores.BuscadorProveedor;

public class BuscadorProveedorControlador implements KeyListener, MouseListener, AccionesABM, ProveedorInterface {

	// ATRIBUTOS
	private BuscadorProveedor bProveedor;
	private ModeloTablaProveedor mtProveedor;
	private ProveedorDao dao;
	private List<Proveedor> lista;
	private ProveedorInterface interfaz;
	private Proveedor proveedor;

	// CONSTRUCTOR
	public BuscadorProveedorControlador(BuscadorProveedor bProveedor) {
		this.bProveedor = bProveedor;
		this.bProveedor.getToolbar().setAcciones(this);
		mtProveedor = new ModeloTablaProveedor();
		this.bProveedor.getTable().setModel(mtProveedor);

		dao = new ProveedorDao();

		// agregamos escuchadores de evento
		setUpEvents();
		recuperarTodo();
	}
	
	public void setInterfaz(ProveedorInterface interfaz) {
		this.interfaz = interfaz;
	}

	// METODO QUE LEVANTA LOS EVENTOS
	private void setUpEvents() {
		bProveedor.gettBuscador().addKeyListener(this);
		bProveedor.getTable().addMouseListener(this);
	}

	// METODO QUE RECUPERA DATOS POR FILTRO EN EL BUSCADOR
	private void recuperarPorFiltro() {
		lista = dao.recuperarPorFiltro(bProveedor.gettBuscador().getText());
		mtProveedor.setLista(lista);
		mtProveedor.fireTableDataChanged();
	}

	private void recuperarTodo() {
		lista = dao.recuperarTodoOrdenadoPorNombre();
		mtProveedor.setLista(lista);
		mtProveedor.fireTableDataChanged();

	}

	private void seleccionarRegistro(int posicion) {
		proveedor = lista.get(posicion);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	// EVENTO DEL BOTON AL DAR CLIC RECUPERA POR FILTRO
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == bProveedor.gettBuscador() && e.getKeyCode() == KeyEvent.VK_ENTER) {
			recuperarPorFiltro();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	// EVENTO DEL MOUSE AL DAR DOBLE CLIC VA A SELECCIONAR UN REGISTRO
	@Override
	public void mouseClicked(MouseEvent e) {
		// doble clic en un registro de la tabla
		if (e.getSource() == bProveedor.getTable() && e.getClickCount() == 1 ) {
			seleccionarRegistro(bProveedor.getTable().getSelectedRow());
		}
		
		if (e.getSource() == bProveedor.getTable() && e.getClickCount() == 2) {
			seleccionarRegistro(bProveedor.getTable().getSelectedRow());
			interfaz.setProveedor(proveedor);
			bProveedor.dispose();
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void nuevo() {
		VentanaProveedor ventana = new VentanaProveedor();
		ventana.setUpControlador();
		ventana.setVisible(true);
		ventana.getControlador().nuevo();
	}

	@Override
	public void modificar() {
		VentanaProveedor ventana = new VentanaProveedor();
		ventana.setUpControlador();
		ventana.getControlador().setProveedor(proveedor);
		ventana.setVisible(true);
		ventana.getControlador().modificar();
		
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
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;

	}
}
