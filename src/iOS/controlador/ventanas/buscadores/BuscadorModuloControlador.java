package iOS.controlador.ventanas.buscadores;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.ModuloDao;
import iOS.modelo.entidades.Modulo;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.ModuloInterface;
import iOS.vista.modelotabla.ModeloTablaModulo;
import iOS.vista.ventanas.buscadores.BuscadorModulo;

public class BuscadorModuloControlador implements KeyListener, MouseListener, AccionesABM, ModuloInterface {
	private BuscadorModulo buscador;
	private ModeloTablaModulo modeloTabla;
	private ModuloDao dao;
	private ModuloInterface interfaz;

	private List<Modulo> lista = new ArrayList<Modulo>();
	private Modulo modulo;


	public BuscadorModuloControlador(BuscadorModulo buscador) {
		this.buscador = buscador;
		this.buscador.getToolbar().setAcciones(this);
		modeloTabla = new ModeloTablaModulo();
		this.buscador.getTable().setModel(modeloTabla);

		dao = new ModuloDao();

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
		modeloTabla.setModulos(lista);
		modeloTabla.fireTableDataChanged();
	}

	private void recuperarTodo() {
		lista = dao.recuperarTodoOrdenadoPorNombre();
		modeloTabla.setModulos(lista);
		modeloTabla.fireTableDataChanged();

	}

	public void setInterfaz(ModuloInterface interfaz) {
		this.interfaz = interfaz;
	}

	private void seleccionarRegistro(int posicion) {
		modulo = lista.get(posicion);
		
		if (modulo == null) {
			return;
		}
	}
	private boolean verificarModuloRepetido(String nombre) {
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getNombreModulo().equalsIgnoreCase(nombre)) {
				return false; //HAY REPETIDO
			}
		}
		return true;

	}


	@Override
	public void setModulo(Modulo modulo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nuevo() {
		String nombre = JOptionPane.showInputDialog("Introduzco el nombre del módulo");
		try{
			while (nombre.isEmpty()) {
				nombre = JOptionPane.showInputDialog("Corrija el modulo");
				if (nombre == null) {
					return;
				}
			}
			
			if (!verificarModuloRepetido(nombre)) {
				return;
			}
			modulo = new Modulo();
			modulo.setNombreModulo(nombre.toUpperCase());
			dao.insertar(modulo);
			dao.commit();
			recuperarTodo();
		} catch (Exception e) {
			dao.rollBack();
			EventosUtil.formatException(e);
		}

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

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == buscador.getTable() && e.getClickCount() == 1 ) {
			seleccionarRegistro(buscador.getTable().getSelectedRow());
		}

		if (e.getSource() == buscador.getTable() && e.getClickCount() == 2) {
			seleccionarRegistro(buscador.getTable().getSelectedRow());
			interfaz.setModulo(modulo);
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
}
