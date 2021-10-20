package iOS.vista.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import iOS.controlador.ventanas.VentanaCajaMovimientoControlador;
import iOS.vista.componentes.CampoNumeroPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;
import iOS.vista.componentes.VentanaGenerica;

public class VentanaCajaMovimiento extends VentanaGenerica {

	/**
	 * 
	 */
	private static final long serialVersionUID = 628264997096470372L;
	public String modulo = "CAJA";
	private MiBoton btnBuscar;
	@SuppressWarnings("rawtypes")
	private JList lstTipoPago;
	@SuppressWarnings("rawtypes")
	private JList lstTipoMovimiento;
	private CampoNumeroPersonalizado tValorGs;
	private CampoNumeroPersonalizado tValorRs;
	private JTextArea txtObservacion;
	@SuppressWarnings("rawtypes")
	private JList lstAsociarPor;
	private CampoNumeroPersonalizado tValorUs;
	private VentanaCajaMovimientoControlador controlador;
	private LabelPersonalizado lDatosCriticos;
	private LabelPersonalizado lDatosCriticos2;
	
	public void setUpControlador(boolean esIngreso) {
		controlador = new VentanaCajaMovimientoControlador(this, esIngreso);

	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	public VentanaCajaMovimiento() {
		getContentPane().setBackground(new Color(255, 255, 255));
		setFont(new Font("Tahoma", Font.BOLD, 12));
		setBackground(new Color(51, 51, 51));
		setLocationRelativeTo(this);
		setResizable(false);
		setModal(true);
		getContentPane().setLayout(null);
		
		getPanelFormulario().setLocation(10, 132);
		getPanelFormulario().setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos cr\u00EDticos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		getPanelFormulario().setSize(474, 100);
		
		lstAsociarPor = new JList();
		lstAsociarPor.setModel(new AbstractListModel() {
			String[] values = new String[] {"CLIENTE", "COLABORADOR"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		lstAsociarPor.setSelectedIndex(0);
		lstAsociarPor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstAsociarPor.setBounds(10, 15, 110, 40);
		getPanelFormulario().add(lstAsociarPor);
		
		lDatosCriticos = new LabelPersonalizado(0);
		lDatosCriticos.setBounds(130, 20, 334, 20);
		getPanelFormulario().add(lDatosCriticos);
		
		btnBuscar = new MiBoton("Buscar");
		btnBuscar.setActionCommand("Buscar");
		btnBuscar.setText("Buscar");
		btnBuscar.setBounds(10, 56, 100, 30);
		getPanelFormulario().add(btnBuscar);
		
		lDatosCriticos2 = new LabelPersonalizado(0);
		lDatosCriticos2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lDatosCriticos2.setBounds(130, 46, 334, 15);
		getPanelFormulario().add(lDatosCriticos2);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tipo de movimiento", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 11, 474, 120);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		lstTipoMovimiento = new JList();
		lstTipoMovimiento.setModel(new AbstractListModel() {
			String[] values = new String[] {"INGRESO", "RETIRO"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		lstTipoMovimiento.setSelectedIndex(0);
		lstTipoMovimiento.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstTipoMovimiento.setBounds(36, 20, 110, 49);
		panel.add(lstTipoMovimiento);
		
		lstTipoPago = new JList();
		lstTipoPago.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstTipoPago.setModel(new AbstractListModel() {
			String[] values = new String[] {"EFECTIVO", "CHEQUE", "TARJETA", "GIRO", "BANCO"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		lstTipoPago.setSelectedIndex(0);
		lstTipoPago.setBounds(182, 20, 110, 89);
		panel.add(lstTipoPago);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Valor del movimiento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(10, 243, 474, 166);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		LabelPersonalizado lblprsnlzdGs = new LabelPersonalizado(0);
		lblprsnlzdGs.setText("Gs.");
		lblprsnlzdGs.setBounds(20, 27, 20, 15);
		panel_1.add(lblprsnlzdGs);
		
		tValorGs = new CampoNumeroPersonalizado();
		tValorGs.setBounds(46, 22, 150, 25);
		panel_1.add(tValorGs);
		
		LabelPersonalizado lblprsnlzdRs = new LabelPersonalizado(0);
		lblprsnlzdRs.setText("Rs.");
		lblprsnlzdRs.setBounds(21, 74, 20, 15);
		panel_1.add(lblprsnlzdRs);
		
		tValorRs = new CampoNumeroPersonalizado();
		tValorRs.setBounds(46, 69, 150, 25);
		panel_1.add(tValorRs);
		
		LabelPersonalizado lblprsnlzdUs = new LabelPersonalizado(0);
		lblprsnlzdUs.setText("Us.");
		lblprsnlzdUs.setBounds(20, 121, 20, 15);
		panel_1.add(lblprsnlzdUs);
		
		tValorUs = new CampoNumeroPersonalizado();
		tValorUs.setBounds(46, 116, 150, 25);
		panel_1.add(tValorUs);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(206, 11, 258, 144);
		panel_1.add(scrollPane);
		
		txtObservacion = new JTextArea();
		txtObservacion = new JTextArea();
		txtObservacion.setLineWrap(true);
		txtObservacion.setWrapStyleWord(true);
		txtObservacion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Character c = evt.getKeyChar();
				if (Character.isLetter(c)) {
					evt.setKeyChar(Character.toUpperCase(c));
				}
				if (txtObservacion.getText().length() > 235) {
					evt.consume();
				}
			}
		});
		txtObservacion.setFont(new Font("Monospaced", Font.PLAIN, 15));
		txtObservacion.setToolTipText("Observaciones relevantes");
		txtObservacion.setBounds(20, 189, 420, 200);
		scrollPane.setViewportView(txtObservacion);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public MiBoton getBtnBuscar() {
		return btnBuscar;
	}
	@SuppressWarnings("rawtypes")
	public JList getLstTipoPago() {
		return lstTipoPago;
	}
	@SuppressWarnings("rawtypes")
	public JList getLstTipoMovimiento() {
		return lstTipoMovimiento;
	}

	public CampoNumeroPersonalizado gettValorGs() {
		return tValorGs;
	}

	public CampoNumeroPersonalizado gettValorRs() {
		return tValorRs;
	}

	public JTextArea getTxtObservacion() {
		return txtObservacion;
	}
	@SuppressWarnings("rawtypes")
	public JList getLstAsociarPor() {
		return lstAsociarPor;
	}

	public CampoNumeroPersonalizado gettValorUs() {
		return tValorUs;
	}

	public VentanaCajaMovimientoControlador getControlador() {
		return controlador;
	}

	public LabelPersonalizado getlDatosCriticos() {
		return lDatosCriticos;
	}

	public LabelPersonalizado getlDatosCriticos2() {
		return lDatosCriticos2;
	}
	
	
}
