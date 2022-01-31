package iOS.controlador.ventanas.buscadores;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import iOS.modelo.dao.SectorDao;
import iOS.modelo.entidades.Sector;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.SectorInterface;
import iOS.vista.modelotabla.ModeloTablaSector;
import iOS.vista.ventanas.VentanaSector;
import iOS.vista.ventanas.buscadores.BuscadorSector;

public class BuscadorSectorControlador implements KeyListener, MouseListener, AccionesABM {

	// ATRIBUTOS
	private BuscadorSector buscador;
	private ModeloTablaSector modeloTabla;
	private SectorDao dao;
	private SectorInterface interfaz;

	private Sector sector;
	private List<Sector> sectores = new ArrayList<Sector>();

	// CONSTRUCTOR
	public BuscadorSectorControlador(BuscadorSector buscador) {
		this.buscador = buscador;
		this.buscador.getToolbar().setAcciones(this);
		modeloTabla = new ModeloTablaSector();
		this.buscador.getTable().setModel(modeloTabla);

		dao = new SectorDao();

		// agregamos escuchadores de evento
		setUpEvents();
		recuperarTodo();
	}

	public void setInterfaz(SectorInterface interfaz) {
		this.interfaz = interfaz;
	}

	// METODO QUE LEVANTA LOS EVENTOS
	private void setUpEvents() {
		buscador.gettBuscador().addKeyListener(this);
		buscador.getTable().addMouseListener(this);
	}

	// METODO QUE RECUPERA DATOS POR FILTRO EN EL BUSCADOR
	private void recuperarPorFiltro() {
		sectores = dao.recuperarPorFiltro(buscador.gettBuscador().getText());
		modeloTabla.setSectores(sectores);
		modeloTabla.fireTableDataChanged();
	}

	private void recuperarTodo() {
		sectores = dao.recuperarTodoOrdenadoPorNombre();
		modeloTabla.setSectores(sectores);
		modeloTabla.fireTableDataChanged();

	}

	private void seleccionarRegistro(int posicion) {
		if (posicion < 0) {
			sector = null;
			return;
		}
		sector = sectores.get(posicion);
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
		if (e.getSource() == buscador.getTable() && e.getClickCount() == 1) {
			seleccionarRegistro(buscador.getTable().getSelectedRow());
		}

		if (e.getSource() == buscador.getTable() && e.getClickCount() == 2) {
			seleccionarRegistro(buscador.getTable().getSelectedRow());
			// Se pasa el valor seleccionado a la interfaz
			try {
				interfaz.setSector(sector);
				buscador.dispose();
			} catch (Exception e1) {
				modificar();
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource() == buscador.getTable() && e.getClickCount() == 1) {
			seleccionarRegistro(buscador.getTable().getSelectedRow());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getSource() == buscador.getTable() && e.getClickCount() == 1) {
			seleccionarRegistro(buscador.getTable().getSelectedRow());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void nuevo() {
		VentanaSector ventana = new VentanaSector();
		ventana.setUpControlador();
		ventana.getControlador().nuevo();
		ventana.setLocationRelativeTo(buscador);
		ventana.setVisible(true);
	}

	@Override
	public void modificar() {
		if (sector == null) {
			return;
		}
		VentanaSector ventana = new VentanaSector();
		ventana.setUpControlador();
		ventana.getControlador().modificar();
		ventana.getControlador().setSector(sector);
		ventana.setLocationRelativeTo(buscador);
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
}
