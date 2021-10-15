package iOS.controlador.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JOptionPane;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.CajaDao;
import iOS.modelo.entidades.Caja;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.interfaces.CajaInterface;
import iOS.modelo.interfaces.ColaboradorInterface;
import iOS.vista.ventanas.VentanaCajaApertura;

public class VentanaCajaAperturaControlador implements ActionListener, MouseListener, KeyListener, PropertyChangeListener, CajaInterface, ColaboradorInterface{
	private VentanaCajaApertura ventana;

	private Caja caja;
	private CajaDao dao;

	private Colaborador colaborador;

	public VentanaCajaAperturaControlador(VentanaCajaApertura ventana) {
		this.ventana = ventana;
		dao = new CajaDao();
		estadoInicial();
		setUpEvents();
	}
	
	private void setUpEvents() {
		ventana.getBtnAbrirCaja().addActionListener(this);
	}
	
	private void estadoInicial() {
		EventosUtil.limpiarCampoPersonalizado(ventana.getlColaborador());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlFecha());
		
		EventosUtil.limpiarCampoPersonalizado(ventana.gettSaldoGS());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettSaldoRS());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettSaldoUS());
	}

	private void guardar() {
		if (colaborador == null) {
			return;
		}
		caja = new Caja();
		caja.setColaborador(colaborador);
		caja.setSaldoInicialGS(ventana.gettSaldoGS().getValue());
		caja.setSaldoInicialRS(ventana.gettSaldoRS().getValue());
		caja.setSaldoInicialUS(ventana.gettSaldoUS().getValue());
		caja.setCajaCerrada(false);

		try {
			dao.insertar(caja);
			dao.commit();
			ventana.dispose();
			JOptionPane.showMessageDialog(null, 
					"Caja abierta exitosamente. \n"
							+ "Gs: "+EventosUtil.separadorMiles(ventana.gettSaldoGS().getValue())+" \n"
							+ "Rs: "+EventosUtil.separadorDecimales(ventana.gettSaldoRS().getValue())+" \n"
							+ "Us: "+EventosUtil.separadorDecimales(ventana.gettSaldoUS().getValue()));
		} catch (Exception e) {
			dao.rollBack();
			EventosUtil.formatException(e);
		}

	}

	@Override
	public void setCaja(Caja caja) {
		this.caja = caja;
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
		case "AbrirCaja":
			guardar();
			break;
		}
	}

	@Override
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
		gestionarColaborador();
	}
	
	public void gestionarColaborador() {
		if (colaborador == null) {
			return;
		}
		ventana.getlColaborador().setText(colaborador.toString());
	}

}
