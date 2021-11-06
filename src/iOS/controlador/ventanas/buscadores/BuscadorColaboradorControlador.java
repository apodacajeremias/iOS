package iOS.controlador.ventanas.buscadores;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import iOS.modelo.dao.ColaboradorDao;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.ColaboradorInterface;
import iOS.vista.modelotabla.ModeloTablaColaborador;
import iOS.vista.ventanas.VentanaColaborador;
import iOS.vista.ventanas.buscadores.BuscadorColaborador;



public class BuscadorColaboradorControlador implements KeyListener, MouseListener, AccionesABM {

	// ATRIBUTOS
	private BuscadorColaborador buscador;
	private ModeloTablaColaborador modeloTabla;
	private ColaboradorDao dao;
	private List<Colaborador> lista;
	private ColaboradorInterface interfaz;
	private Colaborador colaborador;

	public void setInterfaz(ColaboradorInterface interfaz) {
		this.interfaz = interfaz;
	}

	// CONSTRUCTOR
	public BuscadorColaboradorControlador(BuscadorColaborador buscador) {
		this.buscador = buscador;
		modeloTabla = new ModeloTablaColaborador();
		this.buscador.getTable().setModel(modeloTabla);
		this.buscador.getToolbar().setAcciones(this);
		this.buscador.getToolbar().setVisible(false);
		dao = new ColaboradorDao();
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
		lista = dao.recuperarPorFiltro(buscador.gettBuscador().getText());
		modeloTabla.setLista(lista);
		modeloTabla.fireTableDataChanged();
	}

	private void recuperarTodo() {
		lista = dao.recuperarTodoOrdenadoPorNombre();
		modeloTabla.setLista(lista);
		modeloTabla.fireTableDataChanged();

	}

	private void seleccionarRegistro(int posicion) {
		Colaborador cliente = lista.get(posicion);
		// Se pasa la categoria seleccionada a la interfaz
		interfaz.setColaborador(cliente);
		buscador.dispose();
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
		// doble clic en un registro de la tabla
		if (e.getSource() == buscador.getTable() && e.getClickCount() == 2) {
			seleccionarRegistro(buscador.getTable().getSelectedRow());
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
		VentanaColaborador ventana = new VentanaColaborador();
		ventana.setUpControlador();
		ventana.getControlador().nuevo();
		ventana.getControlador().setColaborador(colaborador);
		ventana.setVisible(true);
	}

	@Override
	public void modificar() {
		// TODO Auto-generated method stub
		
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
}
