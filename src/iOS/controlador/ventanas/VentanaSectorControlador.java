package iOS.controlador.ventanas;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.SectorDao;
import iOS.modelo.entidades.Sector;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaDeposito;
import iOS.vista.ventanas.VentanaSector;

public class VentanaSectorControlador implements AccionesABM, MouseListener {
	private VentanaSector ventana;
	private SectorDao dao;	
	private List<Sector> depositos = new ArrayList<Sector>();
	private ModeloTablaDeposito mtDeposito;
	private String accion;
	private Sector sector;
	
	public VentanaSectorControlador(VentanaSector ventana) {
		this.ventana = ventana;
		this.ventana.getMiToolBar().setAcciones(this);

		this.mtDeposito = new ModeloTablaDeposito();
		this.ventana.getTableDeposito().setModel(mtDeposito);

		dao = new SectorDao();
		nuevo();
		estadoInicial(true);
		setUpEvents();
	}

	private void setUpEvents() {
		//MOUSE LISTENER
		ventana.getTableDeposito().addMouseListener(this);
		ventana.getLblDeposito().addMouseListener(this);

	}

	private void estadoInicial(boolean b) {
		//LABEL DE MENSAJES EMPIEZA OBLIGATORIAMENTE CON AMARILLO
		ventana.getlMensaje().setForeground(Color.yellow);
		ventana.getlMensaje().setText(null);


		//LIMPIAR JTEXTFIELD
		EventosUtil.limpiarCampoPersonalizado(ventana.gettNombreSector());
		
		depositos = new ArrayList<>();
		mtDeposito.setLista(depositos);
		mtDeposito.fireTableDataChanged();
		
	}	
	private boolean validarFormulario(){
		if (ventana.gettNombreSector().getText().isEmpty()) {
			ventana.gettNombreSector().setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			ventana.gettNombreSector().requestFocus();
			return false;
		}
		return true;
	}
	

	@Override
	public void nuevo() {
		accion = "NUEVO";
		estadoInicial(true);
		ventana.getlMensaje().setText(accion+" REGISTRO");
		ventana.gettNombreSector().requestFocus();
	}

	@Override
	public void modificar() {
		accion = "MODIFICAR";
		estadoInicial(true);
		ventana.getlMensaje().setText(accion+" REGISTRO");
		ventana.gettNombreSector().requestFocus();
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void guardar() {
		if (!validarFormulario()) {
			ventana.getlMensaje().setForeground(Color.red);
			ventana.getlMensaje().setText("VERIFIQUE LA INFORMACION NUEVAMENTE");			
			return;
		}
		if (accion.equals("NUEVO")) {
			sector = new Sector();
			sector.setColaborador(Sesion.getInstance().getColaborador());
		}
		sector.setDescripcion(ventana.gettNombreSector().getText());		
		ventana.getlMensaje().setForeground(Color.yellow);

		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(sector);
				ventana.getlMensaje().setText("REGISTRO GUARDADO CON ÉXITO");
			} else {
				dao.modificar(sector);
				ventana.getlMensaje().setText("REGISTRO MODIFICADO CON ÉXITO");
			}
			dao.commit();
			estadoInicial(true);
		} catch (Exception e) {
			dao.rollBack();
			EventosUtil.formatException(e);
		}
	}

	@Override
	public void cancelar() {
		estadoInicial(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
}
