package iOS.vista.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import iOS.controlador.ventanas.VentanaProductoControlador;
import iOS.vista.componentes.CampoNumeroPersonalizado;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;
import iOS.vista.componentes.VentanaGenerica;

public class VentanaProducto extends VentanaGenerica {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3901641652725084536L;
	public String modulo = "PRODUCTO";
	private CampoTextoPersonalizado tNombreProducto;
	private CampoTextoPersonalizado tCodigoReferencia;
	private VentanaProductoControlador controlador;
	private CampoNumeroPersonalizado tMedidaAlto;
	private CampoNumeroPersonalizado tPrecioVenta;
	private CampoNumeroPersonalizado tMedidaAncho;
	private JRadioButton rdMedidasFijas;
	private JComboBox<String> cbTipoCobro;
	private JTable tableMateriales;
	private JTable tableProcesos;
	private MiBoton btnAgregarMaterial;
	private MiBoton btnAgregarProceso;
	private LabelPersonalizado lblValor;
	private CampoNumeroPersonalizado tCosto;
	private CampoNumeroPersonalizado tPorcentajeSobreCosto;
	private JRadioButton rdCarteleria;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdConfeccion;

	public void setUpControlador() {
		controlador = new VentanaProductoControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public VentanaProducto() {
		getlMensaje().setLocation(10, 489);
		getlMensaje().setSize(300, 20);
		getlMensaje().setFont(new Font("Tahoma", Font.BOLD, 14));
		getMiToolBar().setBounds(8, 520, 298, 40);
		setBounds(100, 100, 1000, 600);
		getPanelFormulario().setBorder(null);
		getPanelFormulario().setBounds(8, 11, 300, 467);

		tNombreProducto = new CampoTextoPersonalizado();
		tNombreProducto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tNombreProducto.setBounds(15, 104, 270, 30);
		tNombreProducto.mayusculas();
		tNombreProducto.limite(50);
		getPanelFormulario().add(tNombreProducto);

		tCodigoReferencia = new CampoTextoPersonalizado();
		tCodigoReferencia.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tCodigoReferencia.setBounds(15, 166, 270, 30);
		tCodigoReferencia.mayusculas();
		tCodigoReferencia.limite(4);
		tCodigoReferencia.soloLetras();
		getPanelFormulario().add(tCodigoReferencia);

		LabelPersonalizado lblprsnlzdNombreCompleto = new LabelPersonalizado(0);
		lblprsnlzdNombreCompleto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdNombreCompleto.setText("Descripcion detallada del producto");
		lblprsnlzdNombreCompleto.setBounds(15, 78, 270, 20);
		getPanelFormulario().add(lblprsnlzdNombreCompleto);

		LabelPersonalizado lblprsnlzdIdentificacion_1 = new LabelPersonalizado(0);
		lblprsnlzdIdentificacion_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdIdentificacion_1.setText("C\u00F3digo de Referencia R\u00E1pida");
		lblprsnlzdIdentificacion_1.setBounds(15, 140, 270, 20);
		getPanelFormulario().add(lblprsnlzdIdentificacion_1);

		lblValor = new LabelPersonalizado(0);
		lblValor.setBounds(15, 326, 270, 20);
		getPanelFormulario().add(lblValor);
		lblValor.setText("Precio Venta ");
		lblValor.setFont(new Font("Tahoma", Font.PLAIN, 18));

		tPrecioVenta = new CampoNumeroPersonalizado();
		tPrecioVenta.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tPrecioVenta.setBounds(15, 352, 270, 30);
		getPanelFormulario().add(tPrecioVenta);

		rdMedidasFijas = new JRadioButton("Medidas fijas");
		rdMedidasFijas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rdMedidasFijas.isSelected() == false) {
					tMedidaAlto.setEnabled(false);
					tMedidaAncho.setEnabled(false);
				}
				if (rdMedidasFijas.isSelected() == true) {
					tMedidaAlto.setEnabled(true);
					tMedidaAncho.setEnabled(true);
				}
			}
		});
		rdMedidasFijas.setBounds(15, 388, 270, 30);
		getPanelFormulario().add(rdMedidasFijas);
		rdMedidasFijas.setBackground(Color.WHITE);
		rdMedidasFijas.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (rdMedidasFijas.isSelected() == false) {
					tMedidaAlto.setEnabled(false);
					tMedidaAncho.setEnabled(false);
				}
				if (rdMedidasFijas.isSelected() == true) {
					tMedidaAlto.setEnabled(true);
					tMedidaAncho.setEnabled(true);
				}
			}
		});
		rdMedidasFijas.setFont(new Font("Tahoma", Font.PLAIN, 18));

		LabelPersonalizado lblprsnlzdIdentificacion_1_1 = new LabelPersonalizado(0);
		lblprsnlzdIdentificacion_1_1.setBounds(20, 424, 50, 30);
		getPanelFormulario().add(lblprsnlzdIdentificacion_1_1);
		lblprsnlzdIdentificacion_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblprsnlzdIdentificacion_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdIdentificacion_1_1.setText("Alto");

		tMedidaAlto = new CampoNumeroPersonalizado();
		tMedidaAlto.setBounds(90, 424, 50, 30);
		getPanelFormulario().add(tMedidaAlto);

		tMedidaAlto.setEnabled(false);

		LabelPersonalizado lblprsnlzdIdentificacion_1_2 = new LabelPersonalizado(0);
		lblprsnlzdIdentificacion_1_2.setBounds(160, 424, 50, 30);
		getPanelFormulario().add(lblprsnlzdIdentificacion_1_2);
		lblprsnlzdIdentificacion_1_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblprsnlzdIdentificacion_1_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdIdentificacion_1_2.setText("Ancho");

		tMedidaAncho = new CampoNumeroPersonalizado();
		tMedidaAncho.setBounds(230, 424, 50, 30);
		getPanelFormulario().add(tMedidaAncho);
		tMedidaAncho.setEnabled(false);

		tPorcentajeSobreCosto = new CampoNumeroPersonalizado();
		tPorcentajeSobreCosto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tPorcentajeSobreCosto.setBounds(15, 290, 270, 30);
		getPanelFormulario().add(tPorcentajeSobreCosto);

		LabelPersonalizado lblprsnlzdSobreCosto = new LabelPersonalizado(0);
		lblprsnlzdSobreCosto.setText("% sobre costo");
		lblprsnlzdSobreCosto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdSobreCosto.setBounds(15, 264, 270, 20);
		getPanelFormulario().add(lblprsnlzdSobreCosto);

		cbTipoCobro = new JComboBox<String>();
		cbTipoCobro.setBounds(15, 42, 270, 30);
		getPanelFormulario().add(cbTipoCobro);
		cbTipoCobro.setModel(
				new DefaultComboBoxModel<String>(new String[] { "UNIDAD", "METRO CUADRADO", "METRO LINEAL" }));
		cbTipoCobro.setFont(new Font("Tahoma", Font.PLAIN, 18));

		LabelPersonalizado lblprsnlzdCosto = new LabelPersonalizado(0);
		lblprsnlzdCosto.setText("Costo");
		lblprsnlzdCosto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdCosto.setBounds(15, 202, 270, 20);
		getPanelFormulario().add(lblprsnlzdCosto);

		tCosto = new CampoNumeroPersonalizado();
		tCosto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tCosto.setBounds(15, 228, 270, 30);
		getPanelFormulario().add(tCosto);

		rdCarteleria = new JRadioButton("Carteleria");
		buttonGroup.add(rdCarteleria);
		rdCarteleria.setFont(new Font("Dialog", Font.PLAIN, 18));
		rdCarteleria.setBounds(15, 6, 130, 30);
		getPanelFormulario().add(rdCarteleria);

		rdConfeccion = new JRadioButton("Confeccion");
		buttonGroup.add(rdConfeccion);
		rdConfeccion.setFont(new Font("Dialog", Font.PLAIN, 18));
		rdConfeccion.setBounds(155, 6, 130, 30);
		getPanelFormulario().add(rdConfeccion);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(316, 61, 668, 250);
		getContentPane().add(scrollPane);

		tableMateriales = new JTable();
		scrollPane.setViewportView(tableMateriales);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(317, 356, 668, 200);
		getContentPane().add(scrollPane_1);

		tableProcesos = new JTable();
		scrollPane_1.setViewportView(tableProcesos);

		LabelPersonalizado lblprsnlzdMateriales = new LabelPersonalizado(0);
		lblprsnlzdMateriales.setHorizontalAlignment(SwingConstants.CENTER);
		lblprsnlzdMateriales.setText("Materiales");
		lblprsnlzdMateriales.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdMateriales.setBounds(316, 20, 220, 30);
		getContentPane().add(lblprsnlzdMateriales);

		btnAgregarMaterial = new MiBoton("Nuevo");
		btnAgregarMaterial.setText("Agregar");
		btnAgregarMaterial.setActionCommand("AgregarMaterial");
		btnAgregarMaterial.setBounds(546, 20, 100, 30);
		getContentPane().add(btnAgregarMaterial);

		LabelPersonalizado lblprsnlzdProcesos = new LabelPersonalizado(0);
		lblprsnlzdProcesos.setText("Procesos");
		lblprsnlzdProcesos.setHorizontalAlignment(SwingConstants.CENTER);
		lblprsnlzdProcesos.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdProcesos.setBounds(318, 322, 220, 30);
		getContentPane().add(lblprsnlzdProcesos);

		btnAgregarProceso = new MiBoton("Nuevo");
		btnAgregarProceso.setText("Agregar");
		btnAgregarProceso.setActionCommand("AgregarProceso");
		btnAgregarProceso.setBounds(548, 322, 100, 30);
		getContentPane().add(btnAgregarProceso);

		LabelPersonalizado labelPersonalizado_1 = new LabelPersonalizado(0);
		labelPersonalizado_1.setBounds(546, 489, 84, 40);
		getContentPane().add(labelPersonalizado_1);
	}

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getModulo() {
		return modulo;
	}

	public CampoTextoPersonalizado gettNombreProducto() {
		return tNombreProducto;
	}

	public CampoTextoPersonalizado gettCodigoReferencia() {
		return tCodigoReferencia;
	}

	public VentanaProductoControlador getControlador() {
		return controlador;
	}

	public CampoNumeroPersonalizado gettMedidaAlto() {
		return tMedidaAlto;
	}

	public CampoNumeroPersonalizado gettPrecioVenta() {
		return tPrecioVenta;
	}

	public CampoNumeroPersonalizado gettMedidaAncho() {
		return tMedidaAncho;
	}

	public JRadioButton getRdMedidasFijas() {
		return rdMedidasFijas;
	}

	public JComboBox<String> getCbTipoCobro() {
		return cbTipoCobro;
	}

	public JTable getTableMateriales() {
		return tableMateriales;
	}

	public JTable getTableProcesos() {
		return tableProcesos;
	}

	public MiBoton getBtnAgregarMaterial() {
		return btnAgregarMaterial;
	}

	public MiBoton getBtnAgregarProceso() {
		return btnAgregarProceso;
	}

	public LabelPersonalizado getLblValor() {
		return lblValor;
	}

	public CampoNumeroPersonalizado gettCosto() {
		return tCosto;
	}

	public CampoNumeroPersonalizado gettPorcentajeSobreCosto() {
		return tPorcentajeSobreCosto;
	}

	public JRadioButton getRdCarteleria() {
		return rdCarteleria;
	}

	public ButtonGroup getButtonGroup() {
		return buttonGroup;
	}

	public JRadioButton getRdConfeccion() {
		return rdConfeccion;
	}
}
