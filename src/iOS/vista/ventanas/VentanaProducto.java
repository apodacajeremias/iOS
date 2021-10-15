package iOS.vista.ventanas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import iOS.controlador.ventanas.VentanaProductoControlador;
import iOS.vista.componentes.CampoNumeroPersonalizado;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
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
	private JList<?> lstTipoProducto;
	private CampoNumeroPersonalizado tMedidaAlto;
	private CampoNumeroPersonalizado tPrecioProducto;
	private CampoNumeroPersonalizado tMedidaAncho;
	private JRadioButton rdMedidasFijas;
	private JRadioButton rdEsServicio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaProducto dialog = new VentanaProducto();
					dialog.setUpControlador();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setUpControlador() {
		controlador = new VentanaProductoControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
	public VentanaProducto() {
		getPanelFormulario().setBorder(new TitledBorder(null, "Informaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getPanelFormulario().setBounds(10, 10, 474, 130);
		setTitle("Formulario de Producto");
		tNombreProducto = new CampoTextoPersonalizado();
		tNombreProducto.setBounds(10, 36, 450, 25);
		tNombreProducto.mayusculas();
		tNombreProducto.limite(50);
		getPanelFormulario().add(tNombreProducto);
		
		tCodigoReferencia = new CampoTextoPersonalizado();
		tCodigoReferencia.setBounds(10, 93, 100, 25);
		tCodigoReferencia.mayusculas();
		tCodigoReferencia.limite(4);
		tCodigoReferencia.soloLetras();
		getPanelFormulario().add(tCodigoReferencia);
		
		LabelPersonalizado lblprsnlzdNombreCompleto = new LabelPersonalizado(0);
		lblprsnlzdNombreCompleto.setText("Descripci\u00F3n del producto");
		lblprsnlzdNombreCompleto.setBounds(10, 20, 152, 15);
		getPanelFormulario().add(lblprsnlzdNombreCompleto);
		
		LabelPersonalizado lblprsnlzdIdentificacion_1 = new LabelPersonalizado(0);
		lblprsnlzdIdentificacion_1.setText("Código Referencia");
		lblprsnlzdIdentificacion_1.setBounds(10, 77, 116, 15);
		getPanelFormulario().add(lblprsnlzdIdentificacion_1);
		
		rdEsServicio = new JRadioButton("Es servicio");
		rdEsServicio.setBackground(Color.WHITE);
		rdEsServicio.setFont(new Font("Tahoma", Font.BOLD, 12));
		rdEsServicio.setBounds(141, 96, 152, 23);
		getPanelFormulario().add(rdEsServicio);
		
		lstTipoProducto = new JList();
		lstTipoProducto.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lstTipoProducto.setBounds(10, 226, 474, 100);
		getContentPane().add(lstTipoProducto);
		lstTipoProducto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstTipoProducto.setModel(new AbstractListModel() {
			String[] values = new String[] {"METRO CUADRADO", "METRO LINEAL", "UNIDAD"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		lstTipoProducto.setSelectedIndex(0);
		lstTipoProducto.setBorder(new TitledBorder(null, "Tipo de Venta", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Informaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 151, 474, 64);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		rdMedidasFijas = new JRadioButton("Medidas fijas");
		rdMedidasFijas.setBackground(Color.WHITE);
		rdMedidasFijas.addItemListener(new ItemListener() {
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
		rdMedidasFijas.setFont(new Font("Tahoma", Font.BOLD, 12));
		rdMedidasFijas.setBounds(17, 22, 133, 23);
		panel.add(rdMedidasFijas);
		
		LabelPersonalizado lblprsnlzdIdentificacion_1_1 = new LabelPersonalizado(0);
		lblprsnlzdIdentificacion_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblprsnlzdIdentificacion_1_1.setBounds(156, 26, 26, 15);
		lblprsnlzdIdentificacion_1_1.setText("Alto");
		panel.add(lblprsnlzdIdentificacion_1_1);
		
		tMedidaAlto = new CampoNumeroPersonalizado();
		tMedidaAlto.setBounds(182, 19, 100, 25);
		panel.add(tMedidaAlto);
		

		
		LabelPersonalizado lblprsnlzdIdentificacion_1_2 = new LabelPersonalizado(0);
		lblprsnlzdIdentificacion_1_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblprsnlzdIdentificacion_1_2.setBounds(299, 26, 39, 15);
		lblprsnlzdIdentificacion_1_2.setText("Ancho");
		panel.add(lblprsnlzdIdentificacion_1_2);
		
		tMedidaAncho = new CampoNumeroPersonalizado();
		tMedidaAncho.setBounds(336, 20, 100, 25);
		panel.add(tMedidaAncho);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBorder(new TitledBorder(null, "Precio Sugerido", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 337, 474, 53);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		LabelPersonalizado lblprsnlzdIdentificacion_1_1_1 = new LabelPersonalizado(0);
		lblprsnlzdIdentificacion_1_1_1.setText("Valor");
		lblprsnlzdIdentificacion_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblprsnlzdIdentificacion_1_1_1.setBounds(10, 24, 33, 15);
		panel_1.add(lblprsnlzdIdentificacion_1_1_1);
		
		tPrecioProducto = new CampoNumeroPersonalizado();
		tPrecioProducto.setBounds(40, 18, 100, 25);
		panel_1.add(tPrecioProducto);
		
		tMedidaAlto.setEnabled(false);
		tMedidaAncho.setEnabled(false);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public JList<?> getLstTipoProducto() {
		return lstTipoProducto;
	}

	public CampoNumeroPersonalizado gettMedidaAlto() {
		return tMedidaAlto;
	}

	public CampoNumeroPersonalizado gettPrecioProducto() {
		return tPrecioProducto;
	}

	public CampoNumeroPersonalizado gettMedidaAncho() {
		return tMedidaAncho;
	}

	public JRadioButton getRdMedidasFijas() {
		return rdMedidasFijas;
	}

	public JRadioButton getRdEsServicio() {
		return rdEsServicio;
	}
	
}
