package iOS.controlador.ventanas.buscadores;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.OperacionDao;
import iOS.modelo.entidades.Modulo;
import iOS.modelo.entidades.Operacion;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.ModuloInterface;
import iOS.modelo.interfaces.OperacionInterface;
import iOS.vista.modelotabla.ModeloTablaOperacion;
import iOS.vista.ventanas.buscadores.BuscadorModulo;
import iOS.vista.ventanas.buscadores.BuscadorOperacion;

public class BuscadorOperacionControlador implements KeyListener, MouseListener, AccionesABM, OperacionInterface, ModuloInterface {
	private BuscadorOperacion buscador;
	private ModeloTablaOperacion modeloTabla;
	private OperacionDao dao;
	private OperacionInterface interfaz;

	private List<Operacion> lista = new ArrayList<Operacion>();
	private Operacion operacion;
	private Modulo modulo;

	public BuscadorOperacionControlador(BuscadorOperacion buscador) {
		this.buscador = buscador;
		this.buscador.getToolbar().setAcciones(this);
		modeloTabla = new ModeloTablaOperacion();
		this.buscador.getTable().setModel(modeloTabla);

		dao = new OperacionDao();

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
		modeloTabla.setOperaciones(lista);
		modeloTabla.fireTableDataChanged();
	}

	private void recuperarTodo() {
		lista = dao.recuperarTodoOrdenadoPorNombre();
		modeloTabla.setOperaciones(lista);
		modeloTabla.fireTableDataChanged();

	}

	public void setInterfaz(OperacionInterface interfaz) {
		this.interfaz = interfaz;
	}

	private void seleccionarRegistro(int posicion) {
		operacion = lista.get(posicion);
		
		if (operacion == null) {
			return;
		}
	}
	private boolean moduloConOperacionRepetida(String nombre, Modulo modulo) {
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getNombreOperacion().equalsIgnoreCase(nombre) && lista.get(i).getModulo().getId() == modulo.getId()) {
				return true; //HAY REPETIDO
			}
		}
		return false;

	}
	
	private boolean moduloVacio(Modulo modulo){
		if (modulo == null) {
			return true;
		}
		if (modulo.getId() <= 0) {
			return true;
		}
		return false;
	}


	@Override
	public void setOperacion(Operacion modulo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nuevo() {
		String nombre = JOptionPane.showInputDialog("Defina la operacion");
		try{
			while (nombre.isEmpty()) {
				nombre = JOptionPane.showInputDialog("Operacion no definida");
				if (nombre == null) {
					return;
				}
			}
			
			abrirBuscadorModulo();
			
			if (moduloVacio(modulo)) {
				return;
			}
			
			if (moduloConOperacionRepetida(nombre, modulo)) {
				return;
			}
			
			operacion = new Operacion();
			operacion.setNombreOperacion(nombre.toUpperCase());
			operacion.setModulo(modulo);
			dao.insertar(operacion);
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
			interfaz.setOperacion(operacion);
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
	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
		
	}
	
	private void abrirBuscadorModulo() {
		BuscadorModulo buscador = new BuscadorModulo();
		buscador.setUpControlador();
		buscador.getControlador().setInterfaz(this);
		buscador.setVisible(true);

	}

}
