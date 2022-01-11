package iOS.controlador.ventanas.buscadores;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import iOS.modelo.dao.ProcesoDao;
import iOS.modelo.entidades.Proceso;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.ProcesoInterface;
import iOS.vista.modelotabla.ModeloTablaProceso;
import iOS.vista.ventanas.VentanaProceso;
import iOS.vista.ventanas.buscadores.BuscadorProceso;

public class BuscadorProcesoControlador implements KeyListener, MouseListener, AccionesABM, ProcesoInterface {

	// ATRIBUTOS
	private BuscadorProceso buscador;
	private ModeloTablaProceso mtProceso;
	private ProcesoDao dao;
	private List<Proceso> lista;
	private ProcesoInterface interfaz;
	private Proceso proceso;

	public void setInterfaz(ProcesoInterface interfaz) {
		this.interfaz = interfaz;
	}

	// CONSTRUCTOR
	public BuscadorProcesoControlador(BuscadorProceso bProceso) {
		this.buscador = bProceso;
		this.buscador.getToolbar().setAcciones(this);
		mtProceso = new ModeloTablaProceso();
		this.buscador.getTable().setModel(mtProceso);
		dao = new ProcesoDao();
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
		proceso = null;
		lista = dao.recuperarPorFiltro(buscador.gettBuscador().getText());
		mtProceso.setLista(lista);
		mtProceso.fireTableDataChanged();
	}

	private void recuperarTodo() {
		proceso = null;
		lista = dao.recuperarTodoOrdenadoPorNombre();
		mtProceso.setLista(lista);
		mtProceso.fireTableDataChanged();

	}

	private void seleccionarRegistro(int posicion) {
		if (posicion < 0) {
			proceso = null;
			return;
		}
		proceso = lista.get(posicion);
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
				interfaz.setProceso(proceso);
				buscador.dispose();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				VentanaProceso ventana = new VentanaProceso();
				ventana.setUpControlador();
				ventana.getControlador().modificar();
				ventana.getControlador().setProceso(proceso);
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
		VentanaProceso ventana = new VentanaProceso();
		ventana.setUpControlador();
		ventana.getControlador().nuevo();
		ventana.getControlador().setInterfaz(this);
		ventana.setVisible(true);
	}

	@Override
	public void modificar() {
		if (proceso == null) {
			return;
		}
		VentanaProceso ventana = new VentanaProceso();
		ventana.setUpControlador();
		ventana.getControlador().modificar();
		ventana.getControlador().setProceso(proceso);
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
	public void setProceso(Proceso proceso) {
		buscador.gettBuscador().setText(proceso.getDescripcion());
		recuperarPorFiltro();
	}
}
