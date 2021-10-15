package iOS.controlador.ventanas.buscadores;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import iOS.modelo.dao.ProductoDao;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.entidades.Producto;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.ColaboradorInterface;
import iOS.modelo.interfaces.ProductoInterface;
import iOS.vista.modelotabla.ModeloTablaProducto;
import iOS.vista.ventanas.VentanaProducto;
import iOS.vista.ventanas.buscadores.BuscadorProducto;

public class BuscadorProductoControlador implements KeyListener, MouseListener, AccionesABM, ProductoInterface, ColaboradorInterface {

	// ATRIBUTOS
	private BuscadorProducto bProducto;
	private ModeloTablaProducto mtProducto;
	private ProductoDao dao;
	private List<Producto> lista;
	private ProductoInterface interfaz;
	private Producto producto;
	
	@SuppressWarnings("unused")
	private boolean esServicio;
	private Colaborador colaborador;

	public void setInterfaz(ProductoInterface interfaz) {
		this.interfaz = interfaz;
	}

	// CONSTRUCTOR
	public BuscadorProductoControlador(BuscadorProducto bProducto, boolean esServicio) {
		this.bProducto = bProducto;
		this.esServicio = esServicio;
		this.bProducto.getToolbar().setAcciones(this);
		mtProducto = new ModeloTablaProducto();
		this.bProducto.getTable().setModel(mtProducto);

		dao = new ProductoDao();

		// agregamos escuchadores de evento
		setUpEvents();
		recuperarTodo();
	}

	// METODO QUE LEVANTA LOS EVENTOS
	private void setUpEvents() {
		bProducto.gettBuscador().addKeyListener(this);
		bProducto.getTable().addMouseListener(this);
	}

	// METODO QUE RECUPERA DATOS POR FILTRO EN EL BUSCADOR
	private void recuperarPorFiltro() {
		lista = dao.recuperarPorFiltro(bProducto.gettBuscador().getText());
		mtProducto.setLista(lista);
		mtProducto.fireTableDataChanged();
	}

	private void recuperarTodo() {
		lista = dao.recuperarTodoOrdenadoPorNombre();
		mtProducto.setLista(lista);
		mtProducto.fireTableDataChanged();

	}

	private void seleccionarRegistro(int posicion) {
		producto = lista.get(posicion);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	// EVENTO DEL BOTON AL DAR CLIC RECUPERA POR FILTRO
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == bProducto.gettBuscador() && e.getKeyCode() == KeyEvent.VK_ENTER) {
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
		if (e.getSource() == bProducto.getTable() && e.getClickCount() == 1 ) {
			seleccionarRegistro(bProducto.getTable().getSelectedRow());
		}
		
		if (e.getSource() == bProducto.getTable() && e.getClickCount() == 2) {
			seleccionarRegistro(bProducto.getTable().getSelectedRow());
			interfaz.setProducto(producto);
			bProducto.dispose();
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
		VentanaProducto ventana = new VentanaProducto();
		ventana.setUpControlador();
		ventana.getControlador().setColaborador(colaborador);
		ventana.setVisible(true);
	}

	@Override
	public void modificar() {
		VentanaProducto ventana = new VentanaProducto();
		ventana.setUpControlador();
		ventana.getControlador().setProducto(producto);
		ventana.getControlador().setColaborador(colaborador);		
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
		this.producto = producto;
		
	}

	@Override
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
		gestionarColaborador();
	}
	
	public void gestionarColaborador() {
		if (colaborador == null) {
			return;
		}
	}
}
