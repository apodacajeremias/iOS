package iOS.controlador.ventanas.buscadores;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import iOS.modelo.dao.MarcaDao;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.entidades.Marca;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.ColaboradorInterface;
import iOS.modelo.interfaces.MarcaInterface;
import iOS.vista.modelotabla.ModeloTablaMarca;
import iOS.vista.ventanas.VentanaMarca;
import iOS.vista.ventanas.buscadores.BuscadorMarca;

public class BuscadorMarcaControlador implements KeyListener, MouseListener, AccionesABM, MarcaInterface, ColaboradorInterface {

	// ATRIBUTOS
	private BuscadorMarca bMarca;
	private ModeloTablaMarca mtMarca;
	private MarcaDao dao;
	private List<Marca> lista;
	private MarcaInterface interfaz;
	private Marca marca;
	private Colaborador colaborador;

	// CONSTRUCTOR
	public BuscadorMarcaControlador(BuscadorMarca bMarca) {
		this.bMarca = bMarca;
		this.bMarca.getToolbar().setAcciones(this);
		mtMarca = new ModeloTablaMarca();
		this.bMarca.getTable().setModel(mtMarca);

		dao = new MarcaDao();

		// agregamos escuchadores de evento
		setUpEvents();
		recuperarTodo();
	}
	
	public void setInterfaz(MarcaInterface interfaz) {
		this.interfaz = interfaz;
	}

	// METODO QUE LEVANTA LOS EVENTOS
	private void setUpEvents() {
		bMarca.gettBuscador().addKeyListener(this);
		bMarca.getTable().addMouseListener(this);
	}

	// METODO QUE RECUPERA DATOS POR FILTRO EN EL BUSCADOR
	private void recuperarPorFiltro() {
		lista = dao.recuperarPorFiltro(bMarca.gettBuscador().getText());
		mtMarca.setLista(lista);
		mtMarca.fireTableDataChanged();
	}

	private void recuperarTodo() {
		lista = dao.recuperarTodoOrdenadoPorNombre();
		mtMarca.setLista(lista);
		mtMarca.fireTableDataChanged();

	}

	private void seleccionarRegistro(int posicion) {
		marca = lista.get(posicion);
		// Se pasa el valor seleccionado a la interfaz
		interfaz.setMarca(marca);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	// EVENTO DEL BOTON AL DAR CLIC RECUPERA POR FILTRO
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == bMarca.gettBuscador() && e.getKeyCode() == KeyEvent.VK_ENTER) {
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
		if (e.getSource() == bMarca.getTable() && e.getClickCount() == 1 ) {
			seleccionarRegistro(bMarca.getTable().getSelectedRow());
		}
		
		if (e.getSource() == bMarca.getTable() && e.getClickCount() == 2) {
			seleccionarRegistro(bMarca.getTable().getSelectedRow());
			bMarca.dispose();
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
		VentanaMarca ventana = new VentanaMarca();
		ventana.setUpControlador();
		ventana.setVisible(true);
		ventana.getControlador().nuevo();
	}

	@Override
	public void modificar() {
		VentanaMarca ventana = new VentanaMarca();
		ventana.setUpControlador();
		ventana.getControlador().setMarca(marca);
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
	public void setMarca(Marca marca) {
		this.marca = marca;

	}
	
	@Override
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
		
		gestionarColaborador();
	}

	public void gestionarColaborador() {
		if(colaborador == null) {
			return;
		}
	}
}
