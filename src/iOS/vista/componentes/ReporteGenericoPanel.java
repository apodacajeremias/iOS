package iOS.vista.componentes;

import javax.swing.JPanel;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.JRadioButton;

public class ReporteGenericoPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8635421035353331637L;
	private JTable table;
	private LabelPersonalizado lInfo2;
	private LabelPersonalizado lInfo1;
	private JRadioButton rdTodo;

	/**
	 * Create the dialog.
	 */
	public ReporteGenericoPanel() {
		setBounds(100, 100, 1254, 570);
		setLayout(null);
		
		MiBoton btnActualizar = new MiBoton("Actualizar");
		btnActualizar.setText("Actualizar");
		btnActualizar.setBounds(1144, 11, 100, 30);
		add(btnActualizar);
		
		MiBoton btnImprimir = new MiBoton("Imprimir");
		btnImprimir.setText("Imprimir");
		btnImprimir.setBounds(1034, 11, 100, 30);
		add(btnImprimir);
		
		table = new JTable();
		table.setBounds(454, 52, 800, 507);
		add(table);
		
		lInfo1 = new LabelPersonalizado(0);
		lInfo1.setText("Reporte de Caja");
		lInfo1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lInfo1.setBounds(10, 52, 400, 20);
		add(lInfo1);
		
		lInfo2 = new LabelPersonalizado(0);
		lInfo2.setText("Se han encontrado 1089 registros");
		lInfo2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lInfo2.setBounds(20, 74, 390, 15);
		add(lInfo2);
		
		LabelPersonalizado lblprsnlzdFiltrar = new LabelPersonalizado(0);
		lblprsnlzdFiltrar.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdFiltrar.setText("Indique los criterios para filtro");
		lblprsnlzdFiltrar.setBounds(10, 100, 400, 20);
		add(lblprsnlzdFiltrar);
		
		rdTodo = new JRadioButton("Encontrar y Mostrar Todo");
		rdTodo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdTodo.setBounds(10, 130, 227, 20);
		add(rdTodo);
		
		LabelPersonalizado lblprsnlzdBuscaTodosLos = new LabelPersonalizado(0);
		lblprsnlzdBuscaTodosLos.setText("Busca todos los registros relacionados a tu perfil en el sistema");
		lblprsnlzdBuscaTodosLos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblprsnlzdBuscaTodosLos.setBounds(35, 150, 390, 15);
		add(lblprsnlzdBuscaTodosLos);

	}
}
