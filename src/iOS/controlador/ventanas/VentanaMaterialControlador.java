package iOS.controlador.ventanas;

import java.awt.Color;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.MaterialDao;
import iOS.modelo.entidades.Material;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.MaterialInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.ventanas.VentanaMaterial;

public class VentanaMaterialControlador implements AccionesABM, MaterialInterface {
	private VentanaMaterial ventana;
	private MaterialDao dao;
	private Material material;
	private String accion;

	private MaterialInterface interfaz;

	public void setInterfaz(MaterialInterface interfaz) {
		this.interfaz = interfaz;
	}

	public VentanaMaterialControlador(VentanaMaterial ventana) {
		this.ventana = ventana;
		this.ventana.getMiToolBar().setAcciones(this);
		dao = new MaterialDao();
		setUpEvents();
	}

	// Para iniciar
	private void setUpEvents() {
		// ACTION LISTENER

		// MOUSE LISTENER

		// KEY LISTENER

	}

	private void estadoInicial(boolean b) {
		EventosUtil.estadosCampoPersonalizado(ventana.gettNombreMaterial(), b);
		EventosUtil.estadosCampoPersonalizado(ventana.gettCodigoReferencia(), b);
		EventosUtil.estadosCampoPersonalizado(ventana.gettPrecioMaterial(), b);

		EventosUtil.limpiarCampoPersonalizado(ventana.gettNombreMaterial());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettCodigoReferencia());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettPrecioMaterial());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlMensaje());
		accion = null;
		material = null;
	}

	private boolean validarFormulario() {
		if (ventana.gettNombreMaterial().getText().isEmpty()) {
			ventana.getlMensaje().setText("El nombre está vacio");
			ventana.getlMensaje().setForeground(Color.RED);
			ventana.gettNombreMaterial().requestFocus();
			return false;
		}
		return true;
	}

	// Para trabajar con valores

	// private void sugerirPrecios() {
	// int precio = Integer.parseInt(ventanaMaterial.gettPrecio_5Menos().getText());
	// int costo = Integer.parseInt(ventanaMaterial.gettCosto().getText());
	// int suma = costo;
	// int nume = 7;
	// int[] list = new int[nume];
	//
	// if (verificarPrecio()) {
	// int calculo = ((precio-costo)/7);
	// Math.floor(Double.parseDouble(calculo+""));
	// for (int i = 0; i < list.length; i++) {
	// suma = calculo+suma;
	// int y = suma;
	// int x = y - y % 5000;
	//
	// list[i] = x;
	// }
	// ventanaMaterial.gettPrecio_5Mas().setText(list[6]+"");
	// ventanaMaterial.gettPrecio_10Mas().setText(list[5]+"");
	// ventanaMaterial.gettPrecio_50Mas().setText(list[4]+"");
	// ventanaMaterial.gettPrecio_100Mas().setText(list[3]+"");
	// ventanaMaterial.gettPrecio_200Mas().setText(list[2]+"");
	// ventanaMaterial.gettPrecio_300Mas().setText(list[1]+"");
	// }
	// }
	//
	// private boolean verificarPrecio() {
	// if (Integer.parseInt(ventanaMaterial.gettCosto().getText()) >=
	// Integer.parseInt(ventanaMaterial.gettPrecio_5Menos().getText())) {
	// ventanaMaterial.getLblMensaje().setForeground(Color.RED);
	// ventanaMaterial.getLblMensaje().setText("El costo es mayor al precio");
	// ventanaMaterial.gettPrecio_5Menos().requestFocus();
	// return false;
	// }
	// return true;
	// }

	@Override
	public void nuevo() {
		estadoInicial(true);
		accion = "NUEVO";
		ventana.getlMensaje().setText(accion + " REGISTRO");
		ventana.gettNombreMaterial().requestFocus();

	}

	@Override
	public void modificar() {
		estadoInicial(true);
		accion = "MODIFICAR";
		ventana.getlMensaje().setText(accion + " REGISTRO");
		ventana.gettNombreMaterial().requestFocus();

	}

	@Override
	public void eliminar() {
		// accion = "ELIMINAR";
		// int posicion = ventanaMaterial.getTable().getSelectedRow();
		// if (posicion < 0) {
		// return;
		// }
		// ventanaMaterial.getLblMensaje().setText(accion + " REGISTRO");
		// ventanaMaterial.getLblMensaje().setForeground(Color.RED);
		// int respuesta = JOptionPane
		// .showConfirmDialog(null,
		// "La eliminación del material " + material.getDescripcion()
		// + " conlleva la pérdida permanente del registro",
		// "ATENCION", JOptionPane.YES_NO_OPTION);
		// if (respuesta == JOptionPane.YES_OPTION) {
		// try {
		// dao.eliminar(material);
		// dao.commit();
		// ventanaMaterial.getLblMensaje().setText("REGISTRO ELIMINADO");
		// estadoInicial(true);
		// vaciarFormulario();
		// recuperarTodo();
		// } catch (Exception e) {
		// if (e.getCause().getClass() == ConstraintViolationException.class) {
		// JOptionPane.showMessageDialog(null, "NO ES POSIBLE ELIMINAR");
		// }
		// dao.rollBack();
		// e.printStackTrace();
		// }
		// }
		//
	}

	@Override
	public void guardar() {
		if (!validarFormulario()) {
			return;
		}

		if (accion.equals("NUEVO")) {
			material = new Material();
			material.setColaborador(Sesion.getInstance().getColaborador());
		}
		material.setDescripcion(ventana.gettNombreMaterial().getText());
		material.setCosto(ventana.gettPrecioMaterial().getValue());

		if (ventana.getRdMetroCuadrado().isSelected()) {
			material.setTipoCobro(ventana.getRdMetroCuadrado().getText().toUpperCase());
		} else if (ventana.getRdMetroLineal().isSelected()) {
			material.setTipoCobro(ventana.getRdMetroLineal().getText().toUpperCase());
		} else if (ventana.getRdUnidad().isSelected()) {
			material.setTipoCobro(ventana.getRdUnidad().getText().toUpperCase());
		}

		switch (ventana.gettCodigoReferencia().getText().length()) {
		case 0:
			material.setCodigoReferencia(null);
			break;
		default:
			material.setCodigoReferencia(ventana.gettCodigoReferencia().getText());
			break;
		}
		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(material);
				ventana.getlMensaje().setText("REGISTRO GUARDADO CON ÉXITO");
			} else {
				dao.modificar(material);
				ventana.getlMensaje().setText("REGISTRO MODIFICADO CON ÉXITO");
			}
//			Metodos.getInstance().registrar(material, accion, material.registrar());
			dao.commit();
			try {
				interfaz.setMaterial(material);
				ventana.dispose();
			} catch (Exception e) {
				estadoInicial(false);
				setMaterial(material);
				ventana.getlMensaje().setText("REGISTRO GUARDADO CON ÉXITO");
			}
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

	@Override
	public void setMaterial(Material material) {
		this.material = material;
		gestionarMaterial();
	}

	private void gestionarMaterial() {
		if (material == null) {
			return;
		}
		ventana.gettNombreMaterial().setText(material.getDescripcion());
		ventana.gettCodigoReferencia().setText(material.getCodigoReferencia());
		ventana.gettPrecioMaterial().setValue(material.getCosto());

		switch (material.getTipoCobro()) {
		case "METRO CUADRADO":
			ventana.getRdMetroCuadrado().setSelected(true);
			break;
		case "METRO LINEAL":
			ventana.getRdMetroLineal().setSelected(true);
			break;
		case "UNIDAD":
			ventana.getRdUnidad().setSelected(true);
			break;
		default:
			ventana.getRdMetroCuadrado().setSelected(false);
			ventana.getRdMetroLineal().setSelected(false);
			ventana.getRdUnidad().setSelected(true);
			break;
		}
	}
}
