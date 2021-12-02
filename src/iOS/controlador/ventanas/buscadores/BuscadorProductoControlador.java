package iOS.controlador.ventanas.buscadores;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import iOS.modelo.dao.ProductoDao;
import iOS.modelo.entidades.Producto;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.ProductoInterface;
import iOS.vista.modelotabla.ModeloTablaProducto;
import iOS.vista.ventanas.VentanaProducto;
import iOS.vista.ventanas.buscadores.BuscadorProducto;

public class BuscadorProductoControlador implements KeyListener, MouseListener, AccionesABM, ProductoInterface {

	// ATRIBUTOS
	private BuscadorProducto buscador;
	private ModeloTablaProducto mtProducto;
	private ProductoDao dao;
	private List<Producto> lista;
	private ProductoInterface interfaz;
	private Producto producto;

	public void setInterfaz(ProductoInterface interfaz) {
		this.interfaz = interfaz;
	}

	// CONSTRUCTOR
	public BuscadorProductoControlador(BuscadorProducto bProducto) {
		this.buscador = bProducto;
		this.buscador.getToolbar().setAcciones(this);
		mtProducto = new ModeloTablaProducto();
		this.buscador.getTable().setModel(mtProducto);
		dao = new ProductoDao();
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
		producto = null;
		lista = dao.recuperarPorFiltro(buscador.gettBuscador().getText());
		mtProducto.setLista(lista);
		mtProducto.fireTableDataChanged();
	}

	private void recuperarTodo() {
		producto = null;
		lista = dao.recuperarTodoOrdenadoPorNombre();
		mtProducto.setLista(lista);
		mtProducto.fireTableDataChanged();

	}

	private void seleccionarRegistro(int posicion) {
		if (posicion < 0) {
			producto = null;
			return;
		}
		producto = lista.get(posicion);
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
			interfaz.setProducto(producto);
			buscador.dispose();
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
		VentanaProducto ventana = new VentanaProducto();
		ventana.setUpControlador();
		ventana.getControlador().nuevo();
		ventana.getControlador().setInterfaz(this);
		ventana.setVisible(true);
	}

	@Override
	public void modificar() {
		if (producto == null) {
			return;
		}
		VentanaProducto ventana = new VentanaProducto();
		ventana.setUpControlador();
		ventana.getControlador().modificar();
		ventana.getControlador().setProducto(producto);
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
	public void setProducto(Producto producto) {
		buscador.gettBuscador().setText(producto.getDescripcion());
		recuperarPorFiltro();
	}
}
