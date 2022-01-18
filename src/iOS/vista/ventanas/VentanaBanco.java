package iOS.vista.ventanas;

import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import iOS.controlador.ventanas.VentanaBancoControlador;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.VentanaGenerica;

public class VentanaBanco extends VentanaGenerica {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1196814908659540277L;
	public String modulo = "BANCO";
	private CampoTextoPersonalizado tNombreBanco;
	private JTable tProveedorCuenta;
	private VentanaBancoControlador controlador;
	private CampoTextoPersonalizado tSitioWeb;
	private CampoTextoPersonalizado tNumeroAtencion;
	
	public void setUpControlador() {
		controlador = new VentanaBancoControlador(this);

	}

	/**
	 * Create the dialog.
	 */
	public VentanaBanco() {
		
		LabelPersonalizado lblprsnlzdNombreDelBanco = new LabelPersonalizado(0);
		lblprsnlzdNombreDelBanco.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdNombreDelBanco.setText("Nombre del banco");
		lblprsnlzdNombreDelBanco.setBounds(10, 10, 450, 20);
		getPanelFormulario().add(lblprsnlzdNombreDelBanco);
		
		tNombreBanco = new CampoTextoPersonalizado();
		tNombreBanco.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tNombreBanco.mayusculas();
		tNombreBanco.setBounds(10, 30, 450, 30);
		getPanelFormulario().add(tNombreBanco);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 214, 450, 156);
		getPanelFormulario().add(scrollPane);
		
		tProveedorCuenta = new JTable();
		tProveedorCuenta.setEnabled(false);
		scrollPane.setViewportView(tProveedorCuenta);
		
		LabelPersonalizado lblprsnlzdProveedoresYCuentas = new LabelPersonalizado(0);
		lblprsnlzdProveedoresYCuentas.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdProveedoresYCuentas.setText("Proveedores y Cuentas");
		lblprsnlzdProveedoresYCuentas.setBounds(10, 194, 450, 20);
		getPanelFormulario().add(lblprsnlzdProveedoresYCuentas);
		
		tSitioWeb = new CampoTextoPersonalizado();
		tSitioWeb.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tSitioWeb.setBounds(10, 91, 450, 30);
		getPanelFormulario().add(tSitioWeb);
		
		LabelPersonalizado lblprsnlzdSitioWeb = new LabelPersonalizado(0);
		lblprsnlzdSitioWeb.setText("Sitio Web");
		lblprsnlzdSitioWeb.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdSitioWeb.setBounds(10, 71, 450, 20);
		getPanelFormulario().add(lblprsnlzdSitioWeb);
		
		tNumeroAtencion = new CampoTextoPersonalizado();
		tNumeroAtencion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tNumeroAtencion.setBounds(10, 152, 450, 30);
		getPanelFormulario().add(tNumeroAtencion);
		
		LabelPersonalizado lblprsnlzdNombreDelBanco_1_1 = new LabelPersonalizado(0);
		lblprsnlzdNombreDelBanco_1_1.setText("Numero de Atencion al Cliente");
		lblprsnlzdNombreDelBanco_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdNombreDelBanco_1_1.setBounds(10, 132, 450, 20);
		getPanelFormulario().add(lblprsnlzdNombreDelBanco_1_1);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getModulo() {
		return modulo;
	}

	public CampoTextoPersonalizado gettNombreBanco() {
		return tNombreBanco;
	}

	public JTable gettProveedorCuenta() {
		return tProveedorCuenta;
	}

	public VentanaBancoControlador getControlador() {
		return controlador;
	}

	public CampoTextoPersonalizado gettSitioWeb() {
		return tSitioWeb;
	}

	public CampoTextoPersonalizado gettNumeroAtencion() {
		return tNumeroAtencion;
	}

	
}
