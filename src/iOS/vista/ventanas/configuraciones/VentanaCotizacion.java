package iOS.vista.ventanas.configuraciones;

import java.awt.Font;

import iOS.controlador.ventanas.configuraciones.VentanaCotizacionControlador;
import iOS.vista.componentes.CampoNumeroPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.VentanaGenerica;

public class VentanaCotizacion extends VentanaGenerica {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3642036693994011467L;
	private CampoNumeroPersonalizado tCotizacionUS;
	private CampoNumeroPersonalizado tCotizacionRS;
	private CampoNumeroPersonalizado tCotizacionGS;
	private LabelPersonalizado lUltimaActualizacion;
	private VentanaCotizacionControlador controlador;
	
	public void setUpControlador() {
		controlador = new VentanaCotizacionControlador(this);
	}


	/**
	 * Create the dialog.
	 */
	public VentanaCotizacion() {
		
		lUltimaActualizacion = new LabelPersonalizado(0);
		lUltimaActualizacion.setFont(new Font("Tahoma", Font.BOLD, 14));
		lUltimaActualizacion.setText("Ultima actualizacion de cotizacion: 05 enero, 2022");
		lUltimaActualizacion.setBounds(20, 20, 444, 15);
		getPanelFormulario().add(lUltimaActualizacion);
		
		LabelPersonalizado lblprsnlzdGuaranies = new LabelPersonalizado(0);
		lblprsnlzdGuaranies.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblprsnlzdGuaranies.setText("Guaranies");
		lblprsnlzdGuaranies.setBounds(20, 46, 75, 15);
		getPanelFormulario().add(lblprsnlzdGuaranies);
		
		LabelPersonalizado lblprsnlzdReales = new LabelPersonalizado(0);
		lblprsnlzdReales.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblprsnlzdReales.setText("Reales");
		lblprsnlzdReales.setBounds(20, 98, 75, 15);
		getPanelFormulario().add(lblprsnlzdReales);
		
		LabelPersonalizado lblprsnlzdDolares = new LabelPersonalizado(0);
		lblprsnlzdDolares.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblprsnlzdDolares.setText("Dolares");
		lblprsnlzdDolares.setBounds(20, 150, 75, 15);
		getPanelFormulario().add(lblprsnlzdDolares);
		
		tCotizacionGS = new CampoNumeroPersonalizado();
		tCotizacionGS.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tCotizacionGS.setBounds(20, 62, 200, 25);
		getPanelFormulario().add(tCotizacionGS);
		
		tCotizacionRS = new CampoNumeroPersonalizado();
		tCotizacionRS.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tCotizacionRS.setBounds(20, 114, 200, 25);
		getPanelFormulario().add(tCotizacionRS);
		
		tCotizacionUS = new CampoNumeroPersonalizado();
		tCotizacionUS.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tCotizacionUS.setBounds(20, 166, 200, 25);
		getPanelFormulario().add(tCotizacionUS);
	}


	public CampoNumeroPersonalizado gettCotizacionUS() {
		return tCotizacionUS;
	}


	public CampoNumeroPersonalizado gettCotizacionRS() {
		return tCotizacionRS;
	}


	public CampoNumeroPersonalizado gettCotizacionGS() {
		return tCotizacionGS;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public LabelPersonalizado getlUltimaActualizacion() {
		return lUltimaActualizacion;
	}


	public VentanaCotizacionControlador getControlador() {
		return controlador;
	}
	
	
	
}
