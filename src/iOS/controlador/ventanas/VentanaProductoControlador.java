package iOS.controlador.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.ProductoDao;
import iOS.modelo.entidades.Material;
import iOS.modelo.entidades.Proceso;
import iOS.modelo.entidades.Producto;
import iOS.modelo.entidades.ProductoMaterial;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.MaterialInterface;
import iOS.modelo.interfaces.ProcesoInterface;
import iOS.modelo.interfaces.ProductoInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.componentes.CellEditor;
import iOS.vista.componentes.CellRender;
import iOS.vista.modelotabla.ModeloTablaProceso;
import iOS.vista.modelotabla.ModeloTablaProductoMaterial;
import iOS.vista.ventanas.VentanaProducto;
import iOS.vista.ventanas.buscadores.BuscadorMaterial;
import iOS.vista.ventanas.buscadores.BuscadorProceso;

public class VentanaProductoControlador implements AccionesABM, ProductoInterface, ActionListener, KeyListener,
		MaterialInterface, ProcesoInterface, MouseListener, PropertyChangeListener {
	private VentanaProducto ventana;
	private ModeloTablaProductoMaterial tablaProductoMaterial;
	private ModeloTablaProceso tablaProceso;

	private ProductoDao dao;
	private Producto producto;
	private String accion;

	private ProductoInterface interfaz;

	private Proceso proceso;
	private List<Proceso> procesos = new ArrayList<Proceso>();

	private ProductoMaterial productoMaterial;
	private List<ProductoMaterial> productoMateriales = new ArrayList<ProductoMaterial>();

	public void setInterfaz(ProductoInterface interfaz) {
		this.interfaz = interfaz;
	}

	public VentanaProductoControlador(VentanaProducto ventana) {
		this.ventana = ventana;
		this.ventana.getMiToolBar().setAcciones(this);

		tablaProductoMaterial = new ModeloTablaProductoMaterial();
		ventana.getTableMateriales().setModel(tablaProductoMaterial);
		tableMenuMateriales(ventana.getTableMateriales());
		// Se crea el JCheckBox en la columna indicada en getColumn, en este caso, la
		// primera columna
		ventana.getTableMateriales().getColumnModel().getColumn(1).setCellEditor(new CellEditor());
		// para pintar la columna con el CheckBox en la tabla, en este caso, la primera
		// columna
		ventana.getTableMateriales().getColumnModel().getColumn(1).setCellRenderer(new CellRender());

		// Se crea el JCheckBox en la columna indicada en getColumn, en este caso, la
		// primera columna
		ventana.getTableMateriales().getColumnModel().getColumn(4).setCellEditor(new CellEditor());
		// para pintar la columna con el CheckBox en la tabla, en este caso, la primera
		// columna
		ventana.getTableMateriales().getColumnModel().getColumn(4).setCellRenderer(new CellRender());

		tablaProceso = new ModeloTablaProceso();
		ventana.getTableProcesos().setModel(tablaProceso);
		tableMenuProcesos(ventana.getTableProcesos());

		dao = new ProductoDao();
		setUpEvents();
	}

	// Para iniciar
	private void setUpEvents() {
		// ACTION LISTENER
		ventana.getBtnAgregarMaterial().addActionListener(this);
		ventana.getBtnAgregarProceso().addActionListener(this);
		ventana.getCbTipoCobro().addActionListener(this);
		// MOUSE LISTENER
		ventana.getTableMateriales().addMouseListener(this);
		ventana.getTableProcesos().addMouseListener(this);
		// KEY LISTENER
		ventana.gettPorcentajeSobreCosto().addKeyListener(this);
		ventana.gettPrecioVenta().addKeyListener(this);

		// PROPERTY LISTENER
		ventana.getTableMateriales().addPropertyChangeListener(this);

	}

	private void estadoInicial(boolean b) {
		EventosUtil.limpiarCampoPersonalizado(ventana.gettCodigoReferencia());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettCosto());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettMedidaAlto());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettMedidaAncho());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettNombreProducto());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettPorcentajeSobreCosto());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettPrecioVenta());

		ventana.getlMensaje().setText("");
		ventana.getLblValor().setText("Precio de Venta ");

		ventana.gettCosto().setEditable(false);

		vaciarTablas();
	}

	private void vaciarTablas() {
		productoMateriales = new ArrayList<>();
		tablaProductoMaterial.setLista(productoMateriales);

		procesos = new ArrayList<Proceso>();
		tablaProceso.setLista(procesos);
	}

	private void seleccionarMaterial(int posicion) {
		try {
			productoMaterial = productoMateriales.get(posicion);
		} catch (Exception e) {
			productoMaterial = null;
			e.printStackTrace();
			return;
		}

	}

	private void agregarMaterial(Material material) {
		try {
			productoMaterial = new ProductoMaterial();
			productoMaterial.setAlto(100);
			productoMaterial.setAncho(100);
			productoMaterial.setArea(1);
			productoMaterial.setCantidad(1);
			productoMaterial.setColaborador(Sesion.getInstance().getColaborador());
			productoMaterial.setCosto(material.getCosto());
			productoMaterial.setMaterial(material);
			productoMaterial.setSubtotal(material.getCosto());
			productoMaterial.setTieneMedidaFija(false);
			productoMateriales.add(productoMaterial);
			tablaProductoMaterial.setLista(productoMateriales);
			calcularPrecioVenta();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean quitarMaterial(int posicion) {
		try {
			productoMateriales.remove(posicion);
			tablaProductoMaterial.setLista(productoMateriales);
			calcularCosto();
			calcularPrecioVenta();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void seleccionarProceso(int posicion) {
		try {
			proceso = procesos.get(posicion);
		} catch (Exception e) {
			proceso = null;
			e.printStackTrace();
			return;
		}

	}

	private void agregarProceso(Proceso proceso) {
		try {
			procesos.add(proceso);
			tablaProceso.setLista(procesos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean quitarProceso(Proceso proceso) {
		try {
			boolean remove = procesos.remove(proceso);
			tablaProceso.setLista(procesos);
			return remove;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return false;
	}

	private double calcularCosto() {
		double costo = 0;
		try {
			costo = productoMateriales.stream().filter(p -> p.isEstado() == true).mapToDouble(m -> m.getSubtotal())
					.sum();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ventana.gettCosto().setValue(costo);
		return costo;
	}

	private double calcularPrecioVenta() {
		double precio = 0;
		double costo = 0;
		double porcentaje = 0;
		try {
			costo = ventana.gettCosto().getValue();
			porcentaje = (ventana.gettPorcentajeSobreCosto().getValue() + 100) / 100;
			precio = costo * porcentaje;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ventana.gettPrecioVenta().setValue(precio);
		return precio;
	}

	private double calcularPorcentajeVenta() {
		double precio = 0;
		double costo = 0;
		double porcentaje = 0;
		try {
			costo = ventana.gettCosto().getValue();
			precio = ventana.gettPrecioVenta().getValue();
			porcentaje = ((precio / costo) * 100) - 100;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ventana.gettPorcentajeSobreCosto().setValue(porcentaje);
		return porcentaje;
	}

	private boolean validarFormulario() {
		if ((!ventana.getRdCarteleria().isSelected() && !ventana.getRdConfeccion().isSelected())
				|| (ventana.getRdCarteleria().isSelected() && ventana.getRdConfeccion().isSelected())) {
			ventana.getRdCarteleria().requestFocus();
			JOptionPane.showMessageDialog(ventana, "Indique si el producto es carteleria o confeccion");
			return false;
		}
		if (ventana.gettNombreProducto().getText().isEmpty()) {
			ventana.gettNombreProducto().requestFocus();
			JOptionPane.showMessageDialog(ventana, "Faltan datos");
		}
		if (accion.equals("NUEVO")) {
			if (ventana.gettCodigoReferencia().getText().length() > 0) {
				if (!dao.codigoReferenciaDisponible(ventana.gettCodigoReferencia().getText())) {
					JOptionPane.showMessageDialog(ventana, "Codigo de Referencia rapida no disponible");
					return false;
				}
			}
		}
		if (ventana.gettCosto().getValue() <= 0) {
			JOptionPane.showMessageDialog(ventana, "Costo no puede ser cero");
			return false;
		}
		if (ventana.gettPorcentajeSobreCosto().getValue() <= 0) {
			JOptionPane.showMessageDialog(ventana, "Porcentaje de ganancia sobre costo no puede ser cero");
			return false;
		}
		if (ventana.gettPrecioVenta().getValue() <= 0) {
			JOptionPane.showMessageDialog(ventana, "Precio de venta no puede ser cero");
			return false;
		}

		if (ventana.gettPrecioVenta().getValue() <= ventana.gettCosto().getValue()) {
			JOptionPane.showMessageDialog(ventana, "Precio de venta no puede ser menor que el costo");
			return false;
		}

		if (ventana.getRdMedidasFijas().isSelected()) {
			if (ventana.gettMedidaAlto().getValue() + ventana.gettMedidaAncho().getValue() <= 0) {
				ventana.gettMedidaAlto().requestFocus();
				JOptionPane.showMessageDialog(ventana, "Si las medidas son fijas, no deben ser igual a 0");
				return false;
			}
		}

		if (productoMateriales == null || productoMateriales.size() <= 0) {
			ventana.getBtnAgregarMaterial().requestFocus();
			JOptionPane.showMessageDialog(ventana,
					"El producto debe registrarse con todos los materiales que usan para su produccion");
			return false;
		}

		if (procesos == null || procesos.size() <= 0) {
			ventana.getBtnAgregarProceso().requestFocus();
			JOptionPane.showMessageDialog(ventana,
					"El producto debe registrarse con todos los procesos y etapas que son necesarios para su produccion completa");
			return false;
		}

		return true;
	}

	@Override
	public void nuevo() {
		estadoInicial(true);
		accion = "NUEVO";
		ventana.getlMensaje().setText(accion + " REGISTRO");
		ventana.gettNombreProducto().requestFocus();

	}

	@Override
	public void modificar() {
		estadoInicial(true);
		accion = "MODIFICAR";
		ventana.getlMensaje().setText(accion + " REGISTRO");
		ventana.gettNombreProducto().requestFocus();

	}

	@Override
	public void eliminar() {
	}

	@Override
	public void guardar() {
		if (!validarFormulario()) {
			System.out.println("!validarFormulario");
			return;
		}
		if (accion.equals("NUEVO")) {
			producto = new Producto();
			producto.setColaborador(Sesion.getInstance().getColaborador());
		}

		System.out.println(accion);
		producto.setAltoProducto(ventana.gettMedidaAlto().getValue());
		producto.setAnchoProducto(ventana.gettMedidaAncho().getValue());
		producto.setCosto(ventana.gettCosto().getValue());
		producto.setDescripcion(ventana.gettNombreProducto().getText());
		producto.setPorcentajeSobreCosto(calcularPorcentajeVenta());
		producto.setPrecioMaximo(ventana.gettPrecioVenta().getValue());
		producto.setPrecioMinimo(ventana.gettPrecioVenta().getValue());
		producto.setProductoCarteleria(ventana.getRdCarteleria().isSelected());
		producto.setProductoCostura(ventana.getRdConfeccion().isSelected());
		producto.setTieneMedidaFija(ventana.getRdMedidasFijas().isSelected());
		producto.setTipoCobro(ventana.getCbTipoCobro().getSelectedItem().toString());
		switch (ventana.gettCodigoReferencia().getText().length()) {
		case 0:
			producto.setCodigoReferencia(null);
			break;
		default:
			producto.setCodigoReferencia(ventana.gettCodigoReferencia().getText());
			break;
		}

		System.out.println("switch (ventana.gettCodigoReferencia().getText().length())");

		for (int i = 0; i < productoMateriales.size(); i++) {
			productoMateriales.get(i).setProducto(producto);
		}
		producto.setMateriales(productoMateriales);
		producto.setProcesos(procesos);

		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(producto);
				estadoInicial(false);
				ventana.getlMensaje().setText("REGISTRO GUARDADO CON ÉXITO");
			} else {
				dao.modificar(producto);
				estadoInicial(false);
				ventana.getlMensaje().setText("REGISTRO MODIFICADO CON ÉXITO");
			}
			dao.commit();
			try {
				interfaz.setProducto(producto);
				ventana.dispose();
			} catch (Exception e) {
				modificar();
				setProducto(producto);
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

	private void abrirBuscadorMaterial() {
		BuscadorMaterial ventana = new BuscadorMaterial();
		ventana.setUpControlador();
		ventana.getControlador().setInterfaz(this);
		ventana.setVisible(true);

	}

	private void abrirBuscadorProceso() {
		BuscadorProceso ventana = new BuscadorProceso();
		ventana.setUpControlador();
		ventana.getControlador().setInterfaz(this);
		ventana.setVisible(true);

	}

	@Override
	public void setProducto(Producto producto) {
		this.producto = producto;
		try {
			ventana.getRdCarteleria().setSelected(producto.isProductoCarteleria());
			ventana.getRdConfeccion().setSelected(producto.isProductoCostura());
			ventana.getCbTipoCobro().setSelectedItem(producto.getTipoCobro());
			ventana.gettCodigoReferencia().setText(producto.getCodigoReferencia());
			ventana.gettCodigoReferencia().setEditable(false);
			ventana.gettCosto().setValue(producto.getCosto());
			ventana.gettMedidaAlto().setValue(producto.getAltoProducto());
			ventana.gettMedidaAncho().setValue(producto.getAnchoProducto());
			ventana.gettNombreProducto().setText(producto.getDescripcion());
			ventana.gettPorcentajeSobreCosto().setValue(producto.getPorcentajeSobreCosto());
			ventana.gettPrecioVenta().setValue(producto.getPrecioMaximo());

			productoMateriales = producto.getMateriales();
			tablaProductoMaterial.setLista(productoMateriales);
			procesos = producto.getProcesos();
			tablaProceso.setLista(procesos);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void setMaterial(Material material) {
		try {
			agregarMaterial(material);
			calcularCosto();
			calcularPrecioVenta();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
		try {
			agregarProceso(proceso);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "comboBoxChanged":
			ventana.getLblValor().setText("Precio Venta: " + ventana.getCbTipoCobro().getSelectedItem().toString());
			break;
		case "AgregarMaterial":
			abrirBuscadorMaterial();
			break;
		case "AgregarProceso":
			abrirBuscadorProceso();
			break;
		default:
			break;
		}

	}

	private void tableMenuMateriales(final JTable table) {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int r = table.rowAtPoint(e.getPoint());
				if (r >= 0 && r < table.getRowCount()) {
					table.setRowSelectionInterval(r, r);
				} else {
					table.clearSelection();
				}

				int rowindex = table.getSelectedRow();
				if (rowindex < 0) {
					return;
				}
				if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
					JPopupMenu popup = tablePopupMateriales(table, rowindex);
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	private JPopupMenu tablePopupMateriales(final JTable table, final int row) {
		JPopupMenu popup = new JPopupMenu("Popup");
		JMenuItem quitarItem = new JMenuItem("Quitar item");
		quitarItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				quitarMaterial(row);

			}
		});
		popup.add(quitarItem);
		return popup;
	}

	private void tableMenuProcesos(final JTable table) {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int r = table.rowAtPoint(e.getPoint());
				if (r >= 0 && r < table.getRowCount()) {
					table.setRowSelectionInterval(r, r);
				} else {
					table.clearSelection();
				}

				int rowindex = table.getSelectedRow();
				if (rowindex < 0) {
					return;
				}
				if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
					JPopupMenu popup = tablePopupProcesos(table, rowindex);
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	private JPopupMenu tablePopupProcesos(final JTable table, final int row) {
		JPopupMenu popup = new JPopupMenu("Popup");
		JMenuItem quitarItem = new JMenuItem("Quitar item");
		quitarItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				quitarProceso(proceso);

			}
		});
		popup.add(quitarItem);
		return popup;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == ventana.getTableMateriales()) {
			seleccionarMaterial(ventana.getTableMateriales().getSelectedRow());

			if (ventana.getTableMateriales().getSelectedColumn() == 1) {
				tablaProductoMaterial.setValueAt(false, ventana.getTableMateriales().getSelectedRow(),
						ventana.getTableMateriales().getSelectedColumn());
			}

			if (ventana.getTableMateriales().getSelectedColumn() == 4) {
				tablaProductoMaterial.setValueAt(false, ventana.getTableMateriales().getSelectedRow(),
						ventana.getTableMateriales().getSelectedColumn());
			}
		}
		if (e.getSource() == ventana.getTableProcesos()) {
			seleccionarProceso(ventana.getTableProcesos().getSelectedRow());
		}
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
	public void mousePressed(MouseEvent e) {
		if (e.getSource() == ventana.getTableMateriales()) {
			seleccionarMaterial(ventana.getTableMateriales().getSelectedRow());
		}
		if (e.getSource() == ventana.getTableProcesos()) {
			seleccionarProceso(ventana.getTableProcesos().getSelectedRow());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getSource() == ventana.getTableMateriales()) {
			seleccionarMaterial(ventana.getTableMateriales().getSelectedRow());
		}
		if (e.getSource() == ventana.getTableProcesos()) {
			seleccionarProceso(ventana.getTableProcesos().getSelectedRow());
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() == ventana.gettPrecioVenta()) {
			calcularPorcentajeVenta();
		}
		if (e.getSource() == ventana.gettPorcentajeSobreCosto()) {
			calcularPrecioVenta();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getSource() == ventana.getTableMateriales()) {
			calcularCosto();
			calcularPrecioVenta();
		}

	}
}
