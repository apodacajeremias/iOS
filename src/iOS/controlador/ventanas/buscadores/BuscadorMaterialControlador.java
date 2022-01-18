package iOS.controlador.ventanas.buscadores;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import iOS.modelo.dao.MaterialDao;
import iOS.modelo.entidades.Material;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.MaterialInterface;
import iOS.vista.modelotabla.ModeloTablaMaterial;
import iOS.vista.ventanas.VentanaMaterial;
import iOS.vista.ventanas.buscadores.BuscadorMaterial;

public class BuscadorMaterialControlador implements KeyListener, MouseListener, AccionesABM, MaterialInterface {

	// ATRIBUTOS
	private BuscadorMaterial buscador;
	private ModeloTablaMaterial mtMaterial;
	private MaterialDao dao;
	private List<Material> lista;
	private MaterialInterface interfaz;
	private Material material;

	public void setInterfaz(MaterialInterface interfaz) {
		this.interfaz = interfaz;
	}

	// CONSTRUCTOR
	public BuscadorMaterialControlador(BuscadorMaterial bMaterial) {
		this.buscador = bMaterial;
		this.buscador.getToolbar().setAcciones(this);
		mtMaterial = new ModeloTablaMaterial();
		this.buscador.getTable().setModel(mtMaterial);
		dao = new MaterialDao();
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
		material = null;
		lista = dao.recuperarPorFiltro(buscador.gettBuscador().getText());
		mtMaterial.setLista(lista);
		mtMaterial.fireTableDataChanged();
	}

	private void recuperarTodo() {
		material = null;
		lista = dao.recuperarTodoOrdenadoPorNombre();
		mtMaterial.setLista(lista);
		mtMaterial.fireTableDataChanged();

	}

	private void seleccionarRegistro(int posicion) {
		if (posicion < 0) {
			material = null;
			return;
		}
		material = lista.get(posicion);
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
			interfaz.setMaterial(material);
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
		VentanaMaterial ventana = new VentanaMaterial();
		ventana.setUpControlador();
		ventana.getControlador().nuevo();
		ventana.getControlador().setInterfaz(this);
		ventana.setAlwaysOnTop(true);
		ventana.setLocationRelativeTo(buscador);
		ventana.setVisible(true);
	}

	@Override
	public void modificar() {
		if (material == null) {
			return;
		}
		VentanaMaterial ventana = new VentanaMaterial();
		ventana.setUpControlador();
		ventana.getControlador().modificar();
		ventana.getControlador().setMaterial(material);
		ventana.getControlador().setInterfaz(this);
		ventana.setAlwaysOnTop(true);
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

	@Override
	public void setMaterial(Material material) {
		buscador.gettBuscador().setText(material.getDescripcion());
		recuperarPorFiltro();
	}
}
