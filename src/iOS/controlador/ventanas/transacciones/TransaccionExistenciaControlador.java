package iOS.controlador.ventanas.transacciones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.CompraDetalleDao;
import iOS.modelo.dao.DepositoExistenciaDao;
import iOS.modelo.entidades.CompraDetalle;
import iOS.modelo.entidades.Existencia;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaCompraDetalleEnExistencia;
import iOS.vista.modelotabla.ModeloTablaExistencia;
import iOS.vista.ventanas.transacciones.TransaccionExistencia;

public class TransaccionExistenciaControlador implements ActionListener, MouseListener, KeyListener, PropertyChangeListener {
	private TransaccionExistencia transaccion;
	private ModeloTablaCompraDetalleEnExistencia mtCompraDetalle;
	private ModeloTablaExistencia mtExistencia;

	private DepositoExistenciaDao dao;
	private CompraDetalleDao daoCompraDetalle;

	private List<CompraDetalle> detalles = new ArrayList<CompraDetalle>();
	private List<Existencia> existencias = new ArrayList<Existencia>();

	private CompraDetalle compraDetalle;
	private Existencia existencia;
	
	private String accion;

	public TransaccionExistenciaControlador( TransaccionExistencia transaccion) {
		this.transaccion = transaccion;
		this.mtCompraDetalle = new ModeloTablaCompraDetalleEnExistencia();
		this.mtExistencia = new ModeloTablaExistencia();
		this.transaccion.getTableCompraDetalle().setModel(mtCompraDetalle);
		this.transaccion.getTableReferencia().setModel(mtExistencia);

		dao = new DepositoExistenciaDao();
		daoCompraDetalle = new CompraDetalleDao();

		estadoInicial(true);
		setUpEvents();
		recuperarDetallesSinExistencia();
	}

	private void setUpEvents() {
		this.transaccion.getTableCompraDetalle().addMouseListener(this);
		this.transaccion.getTableReferencia().addMouseListener(this);

		this.transaccion.getBtnDisponibilizar().addActionListener(this);
		this.transaccion.getBtnSalir().addActionListener(this);

	}

	private void estadoInicial(boolean b) {
		detalles = new ArrayList<>();
		mtCompraDetalle.setDetalle(detalles);
		mtCompraDetalle.fireTableDataChanged();

		existencias = new ArrayList<>();
		mtExistencia.setExistencia(existencias);
		mtExistencia.fireTableDataChanged();

		recuperarDetallesSinExistencia();
		
		accion = "NUEVO";
		System.out.println(accion);
	}

	private void recuperarDetallesSinExistencia() {
		detalles = daoCompraDetalle.recuperarDetallesSinExistencia();
		mtCompraDetalle.setDetalle(detalles);
		mtCompraDetalle.fireTableDataChanged();
	}
	
	private void asignarReferencias(){
		if (compraDetalle == null) {
			return;
		}
		//Para vaciar cada vez que se selecciona otra compraDetalle
		existencias = new ArrayList<>();
		
		//Se deben generar codigos de existencia por cada uno de los items comprados en relacion a su cantidad
		for (int i = 0; i < compraDetalle.getCantidadDetalle(); i++) {
			//Cada vez se instancia una existencia nueva
			existencia = new Existencia();
			existencia.setEstadoUso(false);
			existencia.setMaterial(compraDetalle.getMaterial());
			existencia.setReferencia(compraDetalle.getCompra().getId()+""+compraDetalle.getId()+""+i);
			existencia.setCompraDetalle(compraDetalle);
			existencias.add(existencia);
		}
		mtExistencia.setExistencia(existencias);
		mtExistencia.fireTableDataChanged();
	}
	
	//
	//	public boolean asignarReferencias(CompraDetalle compraDetalle) {
	//		if ((compraDetalle == null)) {
	//			return false;
	//		}
	//		existencias = new ArrayList<>();
	//		depositoExistencia = new DepositoExistencia();
	//		for (int i = 0; i < compraDetalle.getCantidadDetalle(); i++) {
	//			existencia = new Existencia();
	//			existencia.setDepositoExistencia(depositoExistencia);
	//			existencia.setDeposito(null);
	//			existencia.setCompraDetalle(compraDetalle);
	//			existencia.setEstadoUso(false);
	//			existencia.setMaterial(compraDetalle.getMaterial());
	//			existencia.setReferencia(compraDetalle.getCompra().getId()+""+compraDetalle.getId()+""+i);
	//			existencia.setCompraDetalle(compraDetalle);			
	//			existencias.add(existencia);
	//		}
	//		
	//		depositoExistencia.setExistencia(existencias);
	//		mtExistencia.setExistencia(existencias);
	//		mtExistencia.fireTableDataChanged();
	//		
	//		return true;
	//	}

	public void guardar() {
		if (accion.equals("NUEVO")) {
			compraDetalle = new CompraDetalle();
			compraDetalle.setColaborador(Sesion.getInstance().getColaborador());
		}
		compraDetalle.setExistenciaDisponible(true);
		compraDetalle.setExistencia(existencias);
		
		daoCompraDetalle = new CompraDetalleDao();
		
		try {
			if (accion.equals("NUEVO")) {
				daoCompraDetalle.insertar(compraDetalle);
			} else {
				daoCompraDetalle.modificar(compraDetalle);
			}
			dao.commit();
			estadoInicial(true);
		} catch (Exception e) {
			dao.rollBack();
			EventosUtil.formatException(e);
		}
	}

	public void salir() {
		transaccion.dispose();

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == transaccion.getTableCompraDetalle()) {
			compraDetalle = detalles.get(transaccion.getTableCompraDetalle().getSelectedRow());
			System.out.println(compraDetalle+" "+ compraDetalle.getMaterial());
			System.out.println("---------------------------------------------------------");
			asignarReferencias();
			accion = "ASIGNAR";
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
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Salir":
			salir();
			break;
		case "Disponibilizar":
			guardar();
			break;
		default:
			break;
		}
	}
}
