package iOS.controlador.ventanas.buscadores;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import iOS.modelo.dao.BancoDao;
import iOS.modelo.entidades.EntidadBancaria;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.BancoInterface;
import iOS.vista.modelotabla.ModeloTablaBanco;
import iOS.vista.ventanas.VentanaBanco;
import iOS.vista.ventanas.buscadores.BuscadorBanco;

public class BuscadorBancoControlador implements KeyListener, MouseListener, AccionesABM, BancoInterface {

	// ATRIBUTOS
	private BuscadorBanco buscador;
	private ModeloTablaBanco modeloTabla;
	private BancoDao dao;
	private List<EntidadBancaria> lista;
	private BancoInterface interfaz;
	private EntidadBancaria banco;

	// CONSTRUCTOR
	public BuscadorBancoControlador(BuscadorBanco buscador) {
		this.buscador = buscador;
		this.buscador.getToolbar().setAcciones(this);
		modeloTabla = new ModeloTablaBanco();
		this.buscador.getTable().setModel(modeloTabla);

		dao = new BancoDao();

		// agregamos escuchadores de evento
		setUpEvents();
		recuperarTodo();
	}
	
	public void setInterfaz(BancoInterface interfaz) {
		this.interfaz = interfaz;
	}

	// METODO QUE LEVANTA LOS EVENTOS
	private void setUpEvents() {
		buscador.gettBuscador().addKeyListener(this);
		buscador.getTable().addMouseListener(this);
	}

	// METODO QUE RECUPERA DATOS POR FILTRO EN EL BUSCADOR
	private void recuperarPorFiltro() {
		lista = dao.recuperarPorFiltro(buscador.gettBuscador().getText());
		modeloTabla.setBanco(lista);
		modeloTabla.fireTableDataChanged();
	}

	private void recuperarTodo() {
		lista = dao.recuperarTodoOrdenadoPorNombre();
		modeloTabla.setBanco(lista);
		modeloTabla.fireTableDataChanged();

	}

	private void seleccionarRegistro(int posicion) {
		banco = lista.get(posicion);
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
		if (e.getSource() == buscador.getTable() && e.getClickCount() == 1 ) {
			seleccionarRegistro(buscador.getTable().getSelectedRow());
		}
		
		if (e.getSource() == buscador.getTable() && e.getClickCount() == 2) {
			seleccionarRegistro(buscador.getTable().getSelectedRow());
			// Se pasa el valor seleccionado a la interfaz
			interfaz.setBanco(banco);
			buscador.dispose();
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
		VentanaBanco ventana = new VentanaBanco();
		ventana.setUpControlador();
		ventana.setVisible(true);
		ventana.getControlador().nuevo();
	}

	@Override
	public void modificar() {
		VentanaBanco ventana = new VentanaBanco();
		ventana.setUpControlador();
		ventana.getControlador().setBanco(banco);
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
	public void setBanco(EntidadBancaria banco) {
		this.banco = banco;

		gestionarBanco();
	}
	
	private void gestionarBanco() {
		if (banco == null) {
			return;
		}

	}
}
