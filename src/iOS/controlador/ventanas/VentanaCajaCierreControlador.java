package iOS.controlador.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.CajaDao;
import iOS.modelo.entidades.Caja;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.interfaces.CajaInterface;
import iOS.modelo.interfaces.ColaboradorInterface;
import iOS.vista.ventanas.VentanaCajaCierre;

public class VentanaCajaCierreControlador implements ActionListener, MouseListener, KeyListener, PropertyChangeListener, CajaInterface, ColaboradorInterface{
	private VentanaCajaCierre ventana;

	private Caja caja;
	private CajaDao dao;

	private Colaborador colaborador;

	private ArrayList<Double> ingresos = new ArrayList<>();
	private ArrayList<Double> retiros = new ArrayList<>();
	private ArrayList<Double> saldoInicial = new ArrayList<>();
	private ArrayList<Double> saldoFinal = new ArrayList<>();

	public VentanaCajaCierreControlador(VentanaCajaCierre ventana) {
		this.ventana = ventana;
		dao = new CajaDao();
		estadoInicial();
		setUpEvents();
	}
	private void setUpEvents() {
		ventana.getBtnCerrarCaja().addActionListener(this);
	}

	private void estadoInicial() {
		EventosUtil.limpiarCampoPersonalizado(ventana.gettValorEntregadoGS());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettValorEntregadoRS());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettValorEntregadoUS());

		EventosUtil.limpiarCampoPersonalizado(ventana.gettSaldoDeclaradoGS());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettSaldoDeclaradoRS());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettSaldoDeclaradoUS());

	}
	private ArrayList<Double> ingresos() {
		double gs = 0;
		double rs = 0;
		double us = 0;
		for (int i = 0; i < caja.getCajaMovimientos().size(); i++) {
			if (!caja.getCajaMovimientos().get(i).isEsRetiro() && !caja.getCajaMovimientos().get(i).isEsAnulado()) {
				gs =+ gs + caja.getCajaMovimientos().get(i).getValorGS();
				rs =+ rs + caja.getCajaMovimientos().get(i).getValorRS();
				us =+ us + caja.getCajaMovimientos().get(i).getValorUS();
			}

		}
		ingresos.add(gs);
		ingresos.add(rs);
		ingresos.add(us);

		return ingresos;

	}

	private ArrayList<Double> retiros() {
		double gs = 0;
		double rs = 0;
		double us = 0;
		for (int i = 0; i < caja.getCajaMovimientos().size(); i++) {
			if (caja.getCajaMovimientos().get(i).isEsRetiro() && !caja.getCajaMovimientos().get(i).isEsAnulado()) {
				gs =+ gs + caja.getCajaMovimientos().get(i).getValorGS();
				rs =+ rs + caja.getCajaMovimientos().get(i).getValorRS();
				us =+ us + caja.getCajaMovimientos().get(i).getValorUS();
			}
		}
		retiros.add(gs);
		retiros.add(rs);
		retiros.add(us);

		return retiros;

	}
	private ArrayList<Double> saldoInicial() {
		saldoInicial.add(caja.getSaldoInicialGS());
		saldoInicial.add(caja.getSaldoInicialRS());
		saldoInicial.add(caja.getSaldoInicialUS());

		return saldoInicial;

	}

	private ArrayList<Double> arqueo( ArrayList<Double> ingreso, ArrayList<Double> retiro,  ArrayList<Double> saldoInicial) {
		double gs = (ingreso.get(0)-retiro.get(0)+saldoInicial.get(0));
		double rs = (ingreso.get(1)-retiro.get(1)+saldoInicial.get(1));
		double us = (ingreso.get(2)-retiro.get(2)+saldoInicial.get(2));
		
		saldoFinal.add(gs);
		saldoFinal.add(rs);
		saldoFinal.add(us);

		return saldoFinal;

	}

	private void guardar() {
		if (caja == null) {
			return;
		}
		if (arqueo(ingresos(),retiros(),saldoInicial()) == null) {
			return;
		}


		caja.setCajaCerrada(true);

		caja.setSaldoIngresadoGS(ingresos().get(0));
		caja.setSaldoIngresadoRS(ingresos().get(1));
		caja.setSaldoIngresadoUS(ingresos().get(2));

		caja.setSaldoRetiradoGS(retiros().get(0));
		caja.setSaldoRetiradoRS(retiros().get(1));
		caja.setSaldoRetiradoUS(retiros().get(2));

		caja.setSaldoFinalGS(saldoFinal.get(0));
		caja.setSaldoFinalRS(saldoFinal.get(1));
		caja.setSaldoFinalUS(saldoFinal.get(2));
		
		caja.setSaldoEntregadoGS(ventana.gettValorEntregadoGS().getValue());
		caja.setSaldoEntregadoRS(ventana.gettValorEntregadoRS().getValue());
		caja.setSaldoEntregadoUS(ventana.gettValorEntregadoUS().getValue());
		
		caja.setSaldoDeclaradoGS(ventana.gettSaldoDeclaradoGS().getValue());
		caja.setSaldoDeclaradoRS(ventana.gettSaldoDeclaradoRS().getValue());
		caja.setSaldoDeclaradoUS(ventana.gettSaldoDeclaradoUS().getValue());

		try {
			dao.modificar(caja);
			dao.commit();
			estadoInicial();
			ventana.dispose();
			JOptionPane.showMessageDialog(null, "Caja cerrada exitosamente.");
		} catch (Exception e) {
			dao.rollBack();
			EventosUtil.formatException(e);
		}

	}

	@Override
	public void setCaja(Caja caja) {
		this.caja = caja;
		gestionarCaja();
	}

	private void gestionarCaja() {
		if (caja == null) {
			return;
		}

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
		case "CerrarCaja":
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
	}

}
