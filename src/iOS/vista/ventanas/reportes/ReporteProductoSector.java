package iOS.vista.ventanas.reportes;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import iOS.controlador.ventanas.reportes.ReporteProductoSectorControlador;
import iOS.modelo.entidades.Sector;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.ReporteGenerico;

public class ReporteProductoSector extends ReporteGenerico {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1203406937927609263L;
	private ReporteProductoSectorControlador controlador;
	public String modulo = "ProductoSector";
	private JComboBox<Sector> cbSector;
	private JTable table2;

	/**
	 * Create the dialog.
	 */

	public void setUpControlador() {
		controlador = new ReporteProductoSectorControlador(this);
	}

	public ReporteProductoSector() {
		getScrollPane().setSize(800, 260);
		getPanelGeneral().getPanel_3().setLocation(0, 440);

		getPanelGeneral().getLblrdAlgunos()
				.setText("Busca todos los registros de presupuestos relacionados a tu perfil");
		getPanelGeneral().getRdAlgunos().setText("Encontrar y Mostrar Por Sector");
		setTitle("REPORTE DE ProductoSector");
		getPanelGeneral().getlInfo1().setText("Reporte Ventas de Producto por Sector");
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 374, 415, 44);
		getPanelGeneral().add(panel);
		panel.setLayout(null);
		
		LabelPersonalizado lblprsnlzdSectorizarVentas = new LabelPersonalizado(0);
		lblprsnlzdSectorizarVentas.setBounds(0, 0, 415, 20);
		panel.add(lblprsnlzdSectorizarVentas);
		lblprsnlzdSectorizarVentas.setText("Sectorizar ventas:");
		lblprsnlzdSectorizarVentas.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		cbSector = new JComboBox<Sector>();
		cbSector.setBounds(0, 24, 415, 20);
		panel.add(cbSector);
		getPanelEspecifico().getRdTipo3().setText("Confeccion");
		getPanelEspecifico().getRdTipo2().setText("Carteleria");
		getPanelEspecifico().getRdTipo1().setText("Carteleria + Confeccion");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(454, 330, 800, 260);
		getContentPane().add(scrollPane);
		
		table2 = new JTable();
		scrollPane.setViewportView(table2);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ReporteProductoSectorControlador getControlador() {
		return controlador;
	}

	public String getModulo() {
		return modulo;
	}

	public JComboBox<Sector> getCbSector() {
		return cbSector;
	}

	public JTable getTable2() {
		return table2;
	}
	
	
}
