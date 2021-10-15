package iOS.vista.componentes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JToolBar;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.interfaces.AccionesABM;

public class MiToolbar extends JToolBar implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 245038224502559673L;
	private MiBoton btNuevo;
	private MiBoton btModificar;
	private MiBoton btEliminar;
	private MiBoton btGuardar;
	private MiBoton btCancelar;
	private MiBoton btSalir;

	private AccionesABM acciones;
	private Component horizontalGlue;

	// METODO SET DE acciones
	public void setAcciones(AccionesABM acciones) {
		this.acciones = acciones;
	}

	public MiToolbar() {
		setBackground(new Color(255, 255, 255));
		setFloatable(false);
		
		horizontalGlue = Box.createHorizontalGlue();
		add(horizontalGlue);

		btNuevo = new MiBoton("Nuevo");
		btNuevo.setVisible(false);
		btNuevo.setFont(new Font("Tahoma", Font.BOLD, 14));
		btNuevo.setToolTipText("Nuevo Registro");
		add(btNuevo);

		btEliminar = new MiBoton("Eliminar");
		btEliminar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btEliminar.setVisible(false);
		btEliminar.setToolTipText("Eliminar Registro");
		add(btEliminar);

		btGuardar = new MiBoton("Guardar");
		btGuardar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btGuardar.setToolTipText("Guardar Registro");
		add(btGuardar);
		
				btModificar = new MiBoton("Modificar");
				btModificar.setVisible(false);
				btModificar.setFont(new Font("Tahoma", Font.BOLD, 14));
				btModificar.setToolTipText("Modificar Registro");
				add(btModificar);

		btCancelar = new MiBoton("Cancelar");
		btCancelar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btCancelar.setToolTipText("Cancelar Registro");
		add(btCancelar);

		btSalir = new MiBoton("Salir");
		btSalir.setFont(new Font("Tahoma", Font.BOLD, 14));
		btSalir.setToolTipText("Salir");
		add(btSalir);

		// se llama al metodo QUE AGREGA ESCUCHADOR DE EVENTOS
		// metoddo que fue creado mas abajo
		setUpEvents();

	}// fin de constructor

	public void estadoInicial(boolean b) {
		btNuevo.setEnabled(b);
		btModificar.setEnabled(b);
		btEliminar.setEnabled(b);
		btCancelar.setEnabled(b);
		btGuardar.setEnabled(b);

	}

	// METODO QUE AGREGA ESCUCHADOR DE EVENTOS
	private void setUpEvents() {
		btNuevo.addActionListener(this);
		btCancelar.addActionListener(this);
		btEliminar.addActionListener(this);
		btGuardar.addActionListener(this);
		btModificar.addActionListener(this);
		btSalir.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Nuevo":
			acciones.nuevo();

			EventosUtil.estadosBotones(btNuevo, true);
			EventosUtil.estadosBotones(btModificar, true);
			EventosUtil.estadosBotones(btGuardar, true);
			EventosUtil.estadosBotones(btCancelar, true);
			break;

		case "Modificar":
			acciones.modificar();
			EventosUtil.estadosBotones(btNuevo, false);
			EventosUtil.estadosBotones(btModificar, false);
			EventosUtil.estadosBotones(btEliminar, false);
			EventosUtil.estadosBotones(btGuardar, true);
			EventosUtil.estadosBotones(btCancelar, true);
			break;

		case "Eliminar":
			acciones.eliminar();
			EventosUtil.estadosBotones(btNuevo, true);
			EventosUtil.estadosBotones(btModificar, false);
			EventosUtil.estadosBotones(btEliminar, false);
			EventosUtil.estadosBotones(btGuardar, false);
			EventosUtil.estadosBotones(btCancelar, true);
			break;

		case "Guardar":
			acciones.guardar();
			break;

		case "Cancelar":
			acciones.cancelar();
			break;

		case "Salir":
			break;

		default:
			break;
		}

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public MiBoton getbtNuevo() {
		return btNuevo;
	}

	public MiBoton getbtModificar() {
		return btModificar;
	}

	public MiBoton getbtEliminar() {
		return btEliminar;
	}

	public MiBoton getbtGuardar() {
		return btGuardar;
	}

	public MiBoton getbtCancelar() {
		return btCancelar;
	}

	public MiBoton getbtSalir() {
		return btSalir;
	}

	public AccionesABM getAcciones() {
		return acciones;
	}

}
