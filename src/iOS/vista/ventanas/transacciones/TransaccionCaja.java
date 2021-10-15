package iOS.vista.ventanas.transacciones;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import iOS.controlador.ventanas.transacciones.TransaccionCajaControlador;
import iOS.vista.componentes.CeldaRenderer;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;

public class TransaccionCaja extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6998066400600940031L;
	public String modulo = "CAJA";
	private LabelPersonalizado lFecha;
	private LabelPersonalizado lSaldoInicialGS;
	private LabelPersonalizado lSaldoInicialRS;
	private LabelPersonalizado lSaldoInicialUS;
	private JTable tableMovimientos;
	private MiBoton btnIngresar;
	private MiBoton btnAnular;
	private TransaccionCajaControlador controlador;
	private MiBoton btnCerrarCaja;
	private JPanel panel;
	private JPanel panel_1;
	private LabelPersonalizado lSaldoIngresoGS;
	private LabelPersonalizado lSaldoIngresoRS;
	private LabelPersonalizado lSaldoIngresoUS;
	private JPanel panel_2;
	private LabelPersonalizado lSaldoRetiroGS;
	private LabelPersonalizado lSaldoRetiroRS;
	private LabelPersonalizado lSaldoRetiroUS;
	private MiBoton btnRetirar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransaccionCaja dialog = new TransaccionCaja();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setUpControlador();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setUpControlador() {
		controlador = new TransaccionCajaControlador(this);

	}

	/**
	 * Create the dialog.
	 */
	public TransaccionCaja() {
		getContentPane().setBackground(Color.WHITE);
		setBounds(100, 100, 800, 600);
		setLocationRelativeTo(this);
		setModal(true);
		getContentPane().setLayout(null);
		
		LabelPersonalizado lblprsnlzdFechaDeHoy = new LabelPersonalizado(0);
		lblprsnlzdFechaDeHoy.setText("Fecha de Hoy");
		lblprsnlzdFechaDeHoy.setBounds(22, 64, 81, 15);
		getContentPane().add(lblprsnlzdFechaDeHoy);
		
		lFecha = new LabelPersonalizado(0);
		lFecha.setFont(new Font("Tahoma", Font.BOLD, 20));
		lFecha.setBounds(22, 78, 150, 25);
		getContentPane().add(lFecha);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(22, 114, 750, 2);
		getContentPane().add(separator);
		
		btnIngresar = new MiBoton("Guardar");
		btnIngresar.setActionCommand("IngresarValor");
		btnIngresar.setText("Ingresar");
		btnIngresar.setBounds(22, 123, 100, 30);
		getContentPane().add(btnIngresar);
		
		btnAnular = new MiBoton("Eliminar");
		btnAnular.setVisible(true);
		btnAnular.setActionCommand("Anular");
		btnAnular.setText("Anular");
		btnAnular.setBounds(562, 123, 100, 30);
		getContentPane().add(btnAnular);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(9, 164, 775, 396);
		getContentPane().add(scrollPane);
		
		tableMovimientos = new JTable();
		tableMovimientos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tableMovimientos);
		
		btnCerrarCaja = new MiBoton("Salir");
		btnCerrarCaja.setActionCommand("CerrarCaja");
		btnCerrarCaja.setText("Cerrar Caja");
		btnCerrarCaja.setBounds(672, 123, 100, 30);
		getContentPane().add(btnCerrarCaja);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Saldo Inicial", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBackground(Color.WHITE);
		panel.setBounds(302, 7, 150, 100);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		lSaldoInicialGS = new LabelPersonalizado(0);
		lSaldoInicialGS.setBounds(10, 15, 110, 20);
		panel.add(lSaldoInicialGS);
		
		lSaldoInicialRS = new LabelPersonalizado(0);
		lSaldoInicialRS.setBounds(10, 43, 110, 20);
		panel.add(lSaldoInicialRS);
		
		lSaldoInicialUS = new LabelPersonalizado(0);
		lSaldoInicialUS.setBounds(10, 70, 110, 20);
		panel.add(lSaldoInicialUS);
		
		panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ingreso", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(462, 7, 150, 100);
		getContentPane().add(panel_1);
		
		lSaldoIngresoGS = new LabelPersonalizado(0);
		lSaldoIngresoGS.setBounds(10, 15, 110, 20);
		panel_1.add(lSaldoIngresoGS);
		
		lSaldoIngresoRS = new LabelPersonalizado(0);
		lSaldoIngresoRS.setBounds(10, 43, 110, 20);
		panel_1.add(lSaldoIngresoRS);
		
		lSaldoIngresoUS = new LabelPersonalizado(0);
		lSaldoIngresoUS.setBounds(10, 70, 110, 20);
		panel_1.add(lSaldoIngresoUS);
		
		panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Retiro", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(622, 7, 150, 100);
		getContentPane().add(panel_2);
		
		lSaldoRetiroGS = new LabelPersonalizado(0);
		lSaldoRetiroGS.setBounds(10, 15, 110, 20);
		panel_2.add(lSaldoRetiroGS);
		
		lSaldoRetiroRS = new LabelPersonalizado(0);
		lSaldoRetiroRS.setBounds(10, 43, 110, 20);
		panel_2.add(lSaldoRetiroRS);
		
		lSaldoRetiroUS = new LabelPersonalizado(0);
		lSaldoRetiroUS.setBounds(10, 70, 110, 20);
		panel_2.add(lSaldoRetiroUS);
		
		btnRetirar = new MiBoton("Cancelar");
		btnRetirar.setText("Retirar");
		btnRetirar.setActionCommand("RetirarValor");
		btnRetirar.setBounds(132, 123, 100, 30);
		getContentPane().add(btnRetirar);
		
		tableMovimientos.setDefaultRenderer(Object.class, new CeldaRenderer(0, "Color"));
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public LabelPersonalizado getlFecha() {
		return lFecha;
	}

	public LabelPersonalizado getlSaldoInicialGS() {
		return lSaldoInicialGS;
	}

	public LabelPersonalizado getlSaldoInicialRS() {
		return lSaldoInicialRS;
	}

	public LabelPersonalizado getlSaldoInicialUS() {
		return lSaldoInicialUS;
	}

	public JTable getTableMovimientos() {
		return tableMovimientos;
	}

	public MiBoton getBtnIngresar() {
		return btnIngresar;
	}

	public MiBoton getBtnAnular() {
		return btnAnular;
	}

	public TransaccionCajaControlador getControlador() {
		return controlador;
	}

	public MiBoton getBtnCerrarCaja() {
		return btnCerrarCaja;
	}

	public LabelPersonalizado getlSaldoIngresoGS() {
		return lSaldoIngresoGS;
	}

	public LabelPersonalizado getlSaldoIngresoRS() {
		return lSaldoIngresoRS;
	}

	public LabelPersonalizado getlSaldoIngresoUS() {
		return lSaldoIngresoUS;
	}

	public LabelPersonalizado getlSaldoRetiroGS() {
		return lSaldoRetiroGS;
	}

	public LabelPersonalizado getlSaldoRetiroRS() {
		return lSaldoRetiroRS;
	}

	public LabelPersonalizado getlSaldoRetiroUS() {
		return lSaldoRetiroUS;
	}

	public MiBoton getBtnRetirar() {
		return btnRetirar;
	}
	
	
}
