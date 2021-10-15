package iOS.vista.ventanas;

import java.awt.EventQueue;

import javax.swing.AbstractListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import iOS.controlador.ventanas.VentanaMaterialControlador;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.VentanaGenerica;

public class VentanaMaterial extends VentanaGenerica {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3901641652725084536L;
	private CampoTextoPersonalizado tNombreMaterial;
	private CampoTextoPersonalizado tCodigoReferencia;
	private VentanaMaterialControlador controlador;
	private JList<?> lstTipoMaterial;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMaterial dialog = new VentanaMaterial();
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
		controlador = new VentanaMaterialControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
	public VentanaMaterial() {
		setTitle("Formulario de Material");
		tNombreMaterial = new CampoTextoPersonalizado();
		tNombreMaterial.setBounds(0, 36, 450, 30);
		tNombreMaterial.mayusculas();
		tNombreMaterial.limite(50);
		getPanelFormulario().add(tNombreMaterial);
		
		tCodigoReferencia = new CampoTextoPersonalizado();
		tCodigoReferencia.setBounds(0, 93, 200, 30);
		tCodigoReferencia.mayusculas();
		tCodigoReferencia.limite(4);
		tCodigoReferencia.soloLetras();
		getPanelFormulario().add(tCodigoReferencia);
		
		LabelPersonalizado lblprsnlzdNombreCompleto = new LabelPersonalizado(0);
		lblprsnlzdNombreCompleto.setText("Descripci\u00F3n del material");
		lblprsnlzdNombreCompleto.setBounds(0, 20, 144, 15);
		getPanelFormulario().add(lblprsnlzdNombreCompleto);
		
		LabelPersonalizado lblprsnlzdIdentificacion_1 = new LabelPersonalizado(0);
		lblprsnlzdIdentificacion_1.setText("Código Referencia");
		lblprsnlzdIdentificacion_1.setBounds(0, 77, 116, 15);
		getPanelFormulario().add(lblprsnlzdIdentificacion_1);
		
		lstTipoMaterial = new JList();
		lstTipoMaterial.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstTipoMaterial.setBounds(0, 134, 200, 80);
		getPanelFormulario().add(lstTipoMaterial);
		lstTipoMaterial.setModel(new AbstractListModel() {
			String[] values = new String[] {"Metro Cuadrado", "Metro Lineal", "Unidad"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		lstTipoMaterial.setSelectedIndex(0);
		lstTipoMaterial.setBorder(new TitledBorder(null, "Tipo de Venta", TitledBorder.LEADING, TitledBorder.TOP, null, null));

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CampoTextoPersonalizado gettNombreMaterial() {
		return tNombreMaterial;
	}

	public CampoTextoPersonalizado gettCodigoReferencia() {
		return tCodigoReferencia;
	}
	
	public JList<?> getLstTipoMaterial() {
		return lstTipoMaterial;
	}

	public VentanaMaterialControlador getControlador() {
		return controlador;
	}
		
}
