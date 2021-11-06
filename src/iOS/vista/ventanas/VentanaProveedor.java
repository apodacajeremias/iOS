package iOS.vista.ventanas;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import iOS.controlador.ventanas.VentanaProveedorControlador;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.VentanaGenerica;

public class VentanaProveedor extends VentanaGenerica {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8311047626471060076L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaProveedor dialog = new VentanaProveedor();
			dialog.setUpControlador();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private CampoTextoPersonalizado tNombreCompleto;
	private CampoTextoPersonalizado tIdentificacion;
	private CampoTextoPersonalizado tDireccion;
	private JTable tableContactos;
	private JTable tableCorreos;
	private VentanaProveedorControlador controlador;
	private JTable tableInformacionPago;
	private LabelPersonalizado lContactos;
	private LabelPersonalizado lCorreos;
	private LabelPersonalizado lInformacionPago;


	public void setUpControlador() {
		controlador = new VentanaProveedorControlador(this);

	}
	/**
	 * Create the dialog.
	 */
	public VentanaProveedor() {
		getPanelFormulario().setBounds(10, 10, 475, 520);
		getlMensaje().setLocation(10, 540);
		getMiToolBar().setLocation(10, 570);
		setBounds(100, 100, 500, 650);
		setLocationRelativeTo(this);
		setModal(true);
		getContentPane().setLayout(null);
		setTitle("Formulario de Proveedor");

		tNombreCompleto = new CampoTextoPersonalizado();
		tNombreCompleto.setBounds(12, 22, 450, 30);
		tNombreCompleto.mayusculas();
		tNombreCompleto.limite(50);
		getPanelFormulario().add(tNombreCompleto);

		tIdentificacion = new CampoTextoPersonalizado();
		tIdentificacion.setBounds(12, 76, 200, 30);
		tIdentificacion.mayusculas();
		tIdentificacion.limite(12);
		getPanelFormulario().add(tIdentificacion);

		tDireccion = new CampoTextoPersonalizado();
		tDireccion.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		tDireccion.setBounds(12, 131, 450, 30);
		tDireccion.limite(80);
		tDireccion.mayusculas();
		getPanelFormulario().add(tDireccion);

		LabelPersonalizado lblprsnlzdNombreCompleto = new LabelPersonalizado(0);
		lblprsnlzdNombreCompleto.setText("Nombre del proveedor");
		lblprsnlzdNombreCompleto.setBounds(12, 8, 141, 15);
		getPanelFormulario().add(lblprsnlzdNombreCompleto);

		LabelPersonalizado lblprsnlzdIdentificacion_1 = new LabelPersonalizado(0);
		lblprsnlzdIdentificacion_1.setText("Identificacion");
		lblprsnlzdIdentificacion_1.setBounds(12, 63, 85, 15);
		getPanelFormulario().add(lblprsnlzdIdentificacion_1);

		lContactos = new LabelPersonalizado(0);
		lContactos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lContactos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lContactos.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		lContactos.setToolTipText("Click para agregar contactos");
		lContactos.setText("Contactos");
		lContactos.setBounds(12, 168, 65, 15);
		getPanelFormulario().add(lContactos);

		LabelPersonalizado lblprsnlzdDireccion = new LabelPersonalizado(0);
		lblprsnlzdDireccion.setText("Direccion");
		lblprsnlzdDireccion.setBounds(12, 117, 60, 15);
		getPanelFormulario().add(lblprsnlzdDireccion);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 186, 115, 115);
		getPanelFormulario().add(scrollPane);

		tableContactos = new JTable();
		tableContactos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tableContactos);

		lCorreos = new LabelPersonalizado(0);
		lCorreos.setToolTipText("Click para agregar correos");
		lCorreos.setText("Correos");
		lCorreos.setBounds(135, 168, 60, 15);
		lCorreos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lCorreos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lCorreos.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		getPanelFormulario().add(lCorreos);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(135, 186, 327, 115);
		getPanelFormulario().add(scrollPane_1);

		tableCorreos = new JTable();
		tableCorreos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(tableCorreos);

		lInformacionPago = new LabelPersonalizado(0);
		lInformacionPago.setText("Informacion de Pago");
		lInformacionPago.setBounds(167, 320, 141, 15);
		lInformacionPago.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lInformacionPago.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lInformacionPago.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		getPanelFormulario().add(lInformacionPago);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(12, 338, 450, 170);
		getPanelFormulario().add(scrollPane_2);

		tableInformacionPago = new JTable();
		scrollPane_2.setViewportView(tableInformacionPago);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CampoTextoPersonalizado gettNombreCompleto() {
		return tNombreCompleto;
	}

	public CampoTextoPersonalizado gettIdentificacion() {
		return tIdentificacion;
	}

	public CampoTextoPersonalizado gettDireccion() {
		return tDireccion;
	}

	public JTable getTableContactos() {
		return tableContactos;
	}

	public JTable getTableCorreos() {
		return tableCorreos;
	}

	public VentanaProveedorControlador getControlador() {
		return controlador;
	}

	public JTable getTableInformacionPago() {
		return tableInformacionPago;
	}
	public LabelPersonalizado getlContactos() {
		return lContactos;
	}
	public LabelPersonalizado getlCorreos() {
		return lCorreos;
	}
	public LabelPersonalizado getlInformacionPago() {
		return lInformacionPago;
	}
	

}
