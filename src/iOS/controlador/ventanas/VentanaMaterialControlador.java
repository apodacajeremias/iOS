package iOS.controlador.ventanas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.MaterialDao;
import iOS.modelo.entidades.Material;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.MaterialInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.ventanas.VentanaMaterial;

public class VentanaMaterialControlador implements AccionesABM, ActionListener, MouseListener, KeyListener, MaterialInterface{
	private VentanaMaterial ventana;
	private MaterialDao dao;
	private Material producto;
	private String accion;

	public VentanaMaterialControlador(VentanaMaterial ventana) {
		this.ventana = ventana;
		this.ventana.getMiToolBar().setAcciones(this);

		dao = new MaterialDao();


		nuevo();
		estadoInicial(true);
		setUpEvents();
	}


	//Para iniciar
	private void setUpEvents() {
		//ACTION LISTENER


		//MOUSE LISTENER

		//KEY LISTENER
		this.ventana.gettNombreMaterial().addKeyListener(this);
		this.ventana.gettCodigoReferencia().addKeyListener(this);

	}

	private void estadoInicial(boolean b) {		
		EventosUtil.estadosCampoPersonalizado(ventana.gettNombreMaterial(), b);
		EventosUtil.estadosCampoPersonalizado(ventana.gettCodigoReferencia(), b);
		EventosUtil.estadosCampoPersonalizado(ventana.gettMedidaAlto(), b);
		EventosUtil.estadosCampoPersonalizado(ventana.gettMedidaAncho(), b);
		EventosUtil.estadosCampoPersonalizado(ventana.gettPrecioMaterial(), b);

		EventosUtil.limpiarCampoPersonalizado(ventana.gettNombreMaterial());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettCodigoReferencia());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettMedidaAlto());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettMedidaAncho());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettPrecioMaterial());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlMensaje());
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

	//Para trabajar con valores

	//	private void sugerirPrecios() {
	//		int precio = Integer.parseInt(ventanaMaterial.gettPrecio_5Menos().getText());
	//		int costo = Integer.parseInt(ventanaMaterial.gettCosto().getText());
	//		int suma = costo;
	//		int nume = 7;
	//		int[] list = new int[nume];
	//
	//		if (verificarPrecio()) {
	//			int calculo = ((precio-costo)/7);
	//			Math.floor(Double.parseDouble(calculo+""));
	//			for (int i = 0; i < list.length; i++) {
	//				suma = calculo+suma;
	//				int y = suma; 
	//				int x = y - y % 5000;
	//
	//				list[i] = x;
	//			}
	//			ventanaMaterial.gettPrecio_5Mas().setText(list[6]+"");
	//			ventanaMaterial.gettPrecio_10Mas().setText(list[5]+"");
	//			ventanaMaterial.gettPrecio_50Mas().setText(list[4]+"");
	//			ventanaMaterial.gettPrecio_100Mas().setText(list[3]+"");
	//			ventanaMaterial.gettPrecio_200Mas().setText(list[2]+"");
	//			ventanaMaterial.gettPrecio_300Mas().setText(list[1]+"");
	//		}
	//	}
	//
	//	private boolean verificarPrecio() {
	//		if (Integer.parseInt(ventanaMaterial.gettCosto().getText()) >= Integer.parseInt(ventanaMaterial.gettPrecio_5Menos().getText())) {
	//			ventanaMaterial.getLblMensaje().setForeground(Color.RED);
	//			ventanaMaterial.getLblMensaje().setText("El costo es mayor al precio");
	//			ventanaMaterial.gettPrecio_5Menos().requestFocus();
	//			return false;
	//		}
	//		return true;
	//	}


	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		//		if (e.getSource() == ventanaMaterial.gettBuscador() && e.getKeyCode() == KeyEvent.VK_ENTER) {
		//			recuperarPorFiltro();
		//		}
		//
		//		if (e.getSource() == ventanaMaterial.gettPrecio_5Menos() && e.getKeyCode() == KeyEvent.VK_ENTER) {
		//			sugerirPrecios();
		//		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//		if (e.getSource() == ventanaMaterial.getLstTipoMaterial() && e.getClickCount() == 1) {
		//			ventanaMaterial.getLstTipoMaterial().getSelectedValue().toString().toUpperCase();
		//			System.out.println("getLstTipoPago");
		//		}

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
		// TODO Auto-generated method stub

	}

	@Override
	public void nuevo() {
		accion = "NUEVO";
		estadoInicial(true);
		ventana.getlMensaje().setText(accion + " REGISTRO");
		ventana.gettNombreMaterial().requestFocus();

	}

	@Override
	public void modificar() {
		accion = "MODIFICAR";
		ventana.getlMensaje().setText(accion + " REGISTRO");
		ventana.gettNombreMaterial().requestFocus();

	}

	@Override
	public void eliminar() {
		//		accion = "ELIMINAR";
		//		int posicion = ventanaMaterial.getTable().getSelectedRow();
		//		if (posicion < 0) {
		//			return;
		//		}
		//		ventanaMaterial.getLblMensaje().setText(accion + " REGISTRO");
		//		ventanaMaterial.getLblMensaje().setForeground(Color.RED);
		//		int respuesta = JOptionPane
		//				.showConfirmDialog(null,
		//						"La eliminación del producto " + producto.getDescripcion()
		//						+ " conlleva la pérdida permanente del registro",
		//						"ATENCION", JOptionPane.YES_NO_OPTION);
		//		if (respuesta == JOptionPane.YES_OPTION) {
		//			try {
		//				dao.eliminar(producto);
		//				dao.commit();
		//				ventanaMaterial.getLblMensaje().setText("REGISTRO ELIMINADO");
		//				estadoInicial(true);
		//				vaciarFormulario();
		//				recuperarTodo();
		//			} catch (Exception e) {
		//				if (e.getCause().getClass() == ConstraintViolationException.class) {
		//					JOptionPane.showMessageDialog(null, "NO ES POSIBLE ELIMINAR");
		//				}
		//				dao.rollBack();
		//				e.printStackTrace();
		//			}
		//		}
		//
	}


	@Override
	public void guardar() {
		if (!validarFormulario()) {
			return;
		}

		if (accion.equals("NUEVO")) {
			producto = new Material();
			producto.setColaboradorQueRegistra(Sesion.getInstance().getColaborador());
		}
		producto.setDescripcion(ventana.gettNombreMaterial().getText());
		
		producto.setPrecioMinimo(ventana.gettPrecioMaterial().getValue());
		producto.setPrecioMaximo(ventana.gettPrecioMaterial().getValue());
		

		if (ventana.getRdMetroCuadrado().isSelected()) {
			producto.setTipoCobro(ventana.getRdMetroCuadrado().getText().toUpperCase());
		} else if (ventana.getRdMetroLineal().isSelected()) {
			producto.setTipoCobro(ventana.getRdMetroLineal().getText().toUpperCase());
		} else if (ventana.getRdUnidad().isSelected()) {
			producto.setTipoCobro(ventana.getRdUnidad().getText().toUpperCase());
		}

		switch (ventana.gettCodigoReferencia().getText().length()) {
		case 0:
			producto.setCodigoReferencia(null);
			break;
		default:
			producto.setCodigoReferencia(ventana.gettCodigoReferencia().getText());
			break;
		}
		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(producto);
				dao.commit();
				ventana.getlMensaje().setText("REGISTRO GUARDADO CON ÉXITO");
				estadoInicial(true);
			} else {
				dao.modificar(producto);
				dao.commit();
				ventana.getlMensaje().setText("REGISTRO MODIFICADO CON ÉXITO");
				estadoInicial(true);
			}
		} catch (Exception e) {
			dao.rollBack();
			EventosUtil.formatException(e);
		}
	}

	@Override
	public void cancelar() {
		EventosUtil.limpiarCampoPersonalizado(ventana.getlMensaje());
		estadoInicial(true);
	}

	public void salir(){
		ventana.dispose();
	}

	@Override
	public void setMaterial(Material producto) {
		this.producto = producto;
		gestionarMaterial();
	}

	private void gestionarMaterial() {
		if (producto == null) {
			return;
		}
		ventana.gettNombreMaterial().setText(producto.getDescripcion());
		ventana.gettCodigoReferencia().setText(producto.getCodigoReferencia());
		ventana.gettPrecioMaterial().setValue(producto.getPrecioMinimo());

		switch (producto.getTipoCobro()) {
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
			ventana.getRdUnidad().setSelected(false);
			break;
		}
	}
}

