package iOS.controlador.ventanas.transacciones;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.DepositoExistenciaDao;
import iOS.modelo.dao.ExistenciaDao;
import iOS.modelo.dao.SectorDao;
import iOS.modelo.entidades.DepositoExistencia;
import iOS.modelo.entidades.Existencia;
import iOS.modelo.entidades.Sector;
import iOS.modelo.interfaces.DepositoInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.ventanas.transacciones.TransaccionDepositoExistencia;

public class TransaccionDepositoExistenciaControlador implements ActionListener, MouseListener, KeyListener, PropertyChangeListener, DepositoInterface {
	private TransaccionDepositoExistencia transaccion;
	
	private Sector deposito;
	private Existencia existencia;
	private DepositoExistencia depositoExistencia;
	
	private List<Sector> depositos = new ArrayList<Sector>();
	private List<Existencia> existencias = new ArrayList<Existencia>();
	private List<DepositoExistencia> depositosExistencias = new ArrayList<DepositoExistencia>();
	
	private SectorDao daoSector; 
	private ExistenciaDao daoExistencia;
	private DepositoExistenciaDao dao;

	public TransaccionDepositoExistenciaControlador(TransaccionDepositoExistencia transaccion) {
		this.transaccion = transaccion;
		
		dao = new DepositoExistenciaDao();
		daoSector = new SectorDao();
		daoExistencia = new ExistenciaDao();
		
		setUpEvents();
		estadoInicial();
		
	}
	private void setUpEvents() {
		transaccion.getBtnBuscarExistencia().addActionListener(this);
		transaccion.getBtnTransferir().addActionListener(this);
		
		transaccion.getCbDepositos().addMouseListener(this);

	}

	private void estadoInicial() {
		recuperarDepositos();
		EventosUtil.limpiarCampoPersonalizado(transaccion.gettReferenciaMaterial());
		EventosUtil.limpiarCampoPersonalizado(transaccion.getlMensaje());

	}
	
	
	@SuppressWarnings("unchecked")
	private void recuperarDepositos() {
		transaccion.getCbDepositos().removeAllItems();
		deposito = new Sector();
		depositos = daoSector.recuperarDepositos();
		if (depositos.isEmpty()) {
			return;
		}
		for (int i = 0; i < depositos.size(); i++) {
			transaccion.getCbDepositos().addItem(depositos.get(i));
		}
	}
	
	private boolean validarReferencia() {
		String referencia = transaccion.gettReferenciaMaterial().getText();
		daoExistencia = new ExistenciaDao();
		existencias = daoExistencia.verificarReferencia(referencia);
		
		if (existencias.size() <= 0) {
			transaccion.getlMensaje().setText("LA REFERENCIA INGRESADA NO ES VALIDA");
			transaccion.getlMensaje().setForeground(Color.red);
			return false;
		} 
		
		transaccion.getlMensaje().setText("LA REFERENCIA INGRESADA ES VALIDA");
		transaccion.getlMensaje().setForeground(Color.green);
		System.out.println(existencias.size());
		return true;
	}
	
	private boolean validarTransferencia() {
		if (deposito == null) {
			transaccion.getlMensaje().setText("DEBE INDICAR EL DEPOSITO DESTINO");
			transaccion.getlMensaje().setForeground(Color.red);
			return false;
		}	
		if (!validarReferencia()) {
			return false;
		}
		
		return true;
	}
	
	private void guardar() {
		if (!validarTransferencia()) {
			return;
		}
		
		existencia = existencias.get(0);
		
		depositoExistencia = new DepositoExistencia();
		depositoExistencia.setColaborador(Sesion.getInstance().getColaborador());
		depositoExistencia.setDeposito((Sector) transaccion.getCbDepositos().getSelectedItem());
		depositoExistencia.setExistencia(existencia);
		depositoExistencia.setFechaRegistro(new Date());
		depositosExistencias.add(depositoExistencia);
		
		existencia.setEstadoUso(true);
		existencia.setDepositoExistencia(depositosExistencias);
		
		
		try {
			daoExistencia.modificar(existencia);
			daoExistencia.commit();
			estadoInicial();
			
			transaccion.getlMensaje().setText("EL MATERIAL FUE TRANSFERIDO AL DEPOSITO INDICADO");
		} catch (Exception e) {
			dao.rollBack();
			EventosUtil.formatException(e);
		}

	}
	
	private void salir() {
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
		// TODO Auto-generated method stub
		
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
		case "BuscarExistencia":
			validarReferencia();
			break;
		case "Guardar":
			guardar();
			break;
		case "Salir":
			salir();
			break;
		default:
			break;
		}
		
	}

	@Override
	public void setDeposito(Sector deposito) {
		// TODO Auto-generated method stub
		
	}
}
