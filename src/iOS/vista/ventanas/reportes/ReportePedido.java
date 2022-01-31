package iOS.vista.ventanas.reportes;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import iOS.controlador.ventanas.reportes.ReportePedidoControlador;
import iOS.modelo.entidades.Sector;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.ReporteGenerico;

public class ReportePedido extends ReporteGenerico {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1203406937927609263L;
	private ReportePedidoControlador controlador;
	public String modulo = "PEDIDO";
	private JComboBox<Sector> cbSector;

	/**
	 * Create the dialog.
	 */

	public void setUpControlador() {
		controlador = new ReportePedidoControlador(this);
	}

	public ReportePedido() {
		JPanel panel = new JPanel();
		panel.setVisible(false);
		panel.setBounds(0, 377, 415, 57);
		getPanelEspecifico().add(panel);
		panel.setLayout(null);

		LabelPersonalizado lblprsnlzdSectorizarVentas = new LabelPersonalizado(0);
		lblprsnlzdSectorizarVentas.setBounds(0, 0, 415, 20);
		panel.add(lblprsnlzdSectorizarVentas);
		lblprsnlzdSectorizarVentas.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdSectorizarVentas.setText("Sectorizar ventas:");

		cbSector = new JComboBox<Sector>();
		cbSector.setBounds(0, 31, 415, 26);
		panel.add(cbSector);
		
		getPanelGeneral().getLblrdAlgunos()
				.setText("Busca todos los registros de presupuestos relacionados a tu perfil");
		getPanelGeneral().getRdAlgunos().setText("Encontrar y Mostrar Solo Presupuestos");
		setTitle("REPORTE DE PEDIDO");

		getPanelEspecifico().getRdTodoColaborador().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.setVisible(true);
			}
		});
		getPanelGeneral().getlInfo1().setText("Reporte de Pedido");
		getPanelEspecifico().getRdTipo3().setText("Confeccion");
		getPanelEspecifico().getRdTipo2().setText("Carteleria");
		getPanelEspecifico().getRdTipo1().setText("Carteleria + Confeccion");

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ReportePedidoControlador getControlador() {
		return controlador;
	}

	public String getModulo() {
		return modulo;
	}

	public JComboBox<Sector> getCbSector() {
		return cbSector;
	}

	
}
