package iOS.controlador.ventanas.buscadores;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import iOS.modelo.dao.RolDao;
import iOS.modelo.entidades.Rol;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.RolInterface;
import iOS.vista.modelotabla.ModeloTablaRol;
import iOS.vista.ventanas.buscadores.BuscadorRol;
import iOS.vista.ventanas.transacciones.TransaccionRol;

public class BuscadorRolControlador implements KeyListener, MouseListener, AccionesABM, RolInterface {
	private BuscadorRol buscador;
	private ModeloTablaRol modeloTabla;
	private RolDao dao;
	private RolInterface interfaz;

	private List<Rol> lista = new ArrayList<Rol>();
	private Rol rol;


	public BuscadorRolControlador(BuscadorRol buscador) {
		this.buscador = buscador;
		this.buscador.getToolbar().setAcciones(this);
		modeloTabla = new ModeloTablaRol();
		this.buscador.getTable().setModel(modeloTabla);

		dao = new RolDao();

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
		modeloTabla.setRoles(lista);
		modeloTabla.fireTableDataChanged();
	}

	private void recuperarTodo() {
		lista = dao.recuperarTodoOrdenadoPorNombre();
		modeloTabla.setRoles(lista);
		modeloTabla.fireTableDataChanged();

	}

	public void setInterfaz(RolInterface interfaz) {
		this.interfaz = interfaz;
	}

	private void seleccionarRegistro(int posicion) {
		if (posicion < 0) {
			return;
		}
		rol = lista.get(posicion);
		
		if (rol == null) {
			return;
		}
	}

	@Override
	public void nuevo() {
		TransaccionRol transaccion = new TransaccionRol();
		transaccion.setUpControlador();
		transaccion.setVisible(true);
		transaccion.getControlador().nuevo();
	}

	@Override
	public void modificar() {
		TransaccionRol ventana = new TransaccionRol();
		ventana.setUpControlador();
		ventana.getControlador().setRol(rol);
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
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == buscador.getTable() && e.getClickCount() == 1 ) {
			seleccionarRegistro(buscador.getTable().getSelectedRow());
		}

		if (e.getSource() == buscador.getTable() && e.getClickCount() == 2) {
			seleccionarRegistro(buscador.getTable().getSelectedRow());
			interfaz.setRol(rol);
			buscador.dispose();
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
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == buscador.gettBuscador() && e.getKeyCode() == KeyEvent.VK_ENTER) {
			recuperarPorFiltro();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void setRol(Rol rol) {
		this.rol = rol;	
	}



}
