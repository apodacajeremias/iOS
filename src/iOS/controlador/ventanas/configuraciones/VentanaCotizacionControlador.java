package iOS.controlador.ventanas.configuraciones;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.CotizacionDao;
import iOS.modelo.entidades.Cotizacion;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.CotizacionInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.ventanas.configuraciones.VentanaCotizacion;

public class VentanaCotizacionControlador implements CotizacionInterface, AccionesABM {
	private VentanaCotizacion ventana;

	private CotizacionDao dao;

	private Cotizacion cotizacion;

	private String accion;

	public VentanaCotizacionControlador(VentanaCotizacion ventana) {
		this.ventana = ventana;
		this.ventana.getMiToolBar().setAcciones(this);

		dao = new CotizacionDao();
		
		estadoInicial(true);
		
		setCotizacion(ultimaCotizacion());
	}

	private void estadoInicial(boolean b) {
		EventosUtil.limpiarCampoPersonalizado(ventana.gettCotizacionGS());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettCotizacionRS());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettCotizacionUS());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlUltimaActualizacion());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlMensaje());

	}

	private Cotizacion ultimaCotizacion() {
		cotizacion = dao.recuperarUltimaCotizacion();
		
		return cotizacion;
	}

	private boolean verificarCotizacion() {
		try {
			if (cotizacion.getCotizacionGS() == ventana.gettCotizacionGS().getValue()
					&& cotizacion.getCotizacionRS() == ventana.gettCotizacionRS().getValue()
					&& cotizacion.getCotizacionUS() == ventana.gettCotizacionUS().getValue()) {
				accion = "MODIFICAR";
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		accion = "NUEVO";
		return true;
	}

	@Override
	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
		gestionarCotizacion();
	}

	private void gestionarCotizacion() {
		if (cotizacion == null) {
			return;
		}

		ventana.getlUltimaActualizacion().setText(
				"Ultima actualizacion de cotizacion: " + EventosUtil.formatoFecha(cotizacion.getFechaRegistro()));
		ventana.gettCotizacionGS().setValue(cotizacion.getCotizacionGS());
		ventana.gettCotizacionRS().setValue(cotizacion.getCotizacionRS());
		ventana.gettCotizacionUS().setValue(cotizacion.getCotizacionUS());
	}

	@Override
	public void nuevo() {
		estadoInicial(true);
		accion = "NUEVO";
		cotizacion = null;
		ventana.getlMensaje().setText(accion + " REGISTRO");
		ventana.gettCotizacionGS().requestFocus();

	}

	@Override
	public void modificar() {
		estadoInicial(true);
		accion = "MODIFICAR";
		ventana.getlMensaje().setText(accion + " REGISTRO");
	}

	@Override
	public void eliminar() {
		accion = "ELIMINAR";
	}

	@Override
	public void guardar() {
		if (!verificarCotizacion()) {
			return;
		}
		
		accion = "NUEVO";

		if (accion.equals("NUEVO")) {
			cotizacion = new Cotizacion();
			cotizacion.setColaborador(Sesion.getInstance().getColaborador());
		}

		cotizacion.setCotizacionGS(ventana.gettCotizacionGS().getValue());
		cotizacion.setCotizacionRS(ventana.gettCotizacionRS().getValue());
		cotizacion.setCotizacionUS(ventana.gettCotizacionUS().getValue());

		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(cotizacion);
			} else {
				dao.modificar(cotizacion);
			}
			dao.commit();
			setCotizacion(cotizacion);
			ventana.getlMensaje().setText("REGISTRO GUARDADO CON ÉXITO");
		} catch (Exception e) {
			dao.rollBack();
			e.printStackTrace();
		}

	}

	@Override
	public void cancelar() {
		estadoInicial(true);
	}

	public void salir() {
		ventana.dispose();
	}
}
