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
	private BuscadorMaterial bMaterial;
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
		this.bMaterial = bMaterial;
		this.bMaterial.getToolbar().setAcciones(this);
		mtMaterial = new ModeloTablaMaterial();
		this.bMaterial.getTable().setModel(mtMaterial);

		dao = new MaterialDao();

		// agregamos escuchadores de evento
		setUpEvents();
		recuperarTodo();
	}

	// METODO QUE LEVANTA LOS EVENTOS
	private void setUpEvents() {
		bMaterial.gettBuscador().addKeyListener(this);
		bMaterial.getTable().addMouseListener(this);
	}

	// METODO QUE RECUPERA DATOS POR FILTRO EN EL BUSCADOR
	private void recuperarPorFiltro() {
		lista = dao.recuperarPorFiltro(bMaterial.gettBuscador().getText());
		mtMaterial.setLista(lista);
		mtMaterial.fireTableDataChanged();
	}

	private void recuperarTodo() {
		lista = dao.recuperarTodoOrdenadoPorNombre();
		mtMaterial.setLista(lista);
		mtMaterial.fireTableDataChanged();

	}

	private void seleccionarRegistro(int posicion) {
		material = lista.get(posicion);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	// EVENTO DEL BOTON AL DAR CLIC RECUPERA POR FILTRO
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == bMaterial.gettBuscador() && e.getKeyCode() == KeyEvent.VK_ENTER) {
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
		if (e.getSource() == bMaterial.getTable() && e.getClickCount() == 1 ) {
			seleccionarRegistro(bMaterial.getTable().getSelectedRow());
		}
		
		if (e.getSource() == bMaterial.getTable() && e.getClickCount() == 2) {
			seleccionarRegistro(bMaterial.getTable().getSelectedRow());
			interfaz.setMaterial(material);
			bMaterial.dispose();
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
		VentanaMaterial ventana = new VentanaMaterial();
		ventana.setUpControlador();
		ventana.setVisible(true);
		ventana.getControlador().nuevo();
	}

	@Override
	public void modificar() {
		VentanaMaterial ventana = new VentanaMaterial();
		ventana.setUpControlador();
		ventana.getControlador().setMaterial(material);
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
	public void setMaterial(Material material) {
		this.material = material;
		
	}

}
