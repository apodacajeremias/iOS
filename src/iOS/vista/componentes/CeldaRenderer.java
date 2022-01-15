package iOS.vista.componentes;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CeldaRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2920471479030586542L;
	private int col = -1;
	private String accion;
	private JComponent component = new JCheckBox();

	public CeldaRenderer(int col, String accion) {
		this.col = col;
		this.accion = accion;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		switch (accion) {
		case "ComboBox":
			// retorna un combo con el valor seleccionado
			if (col == column) {// la columna que contiene el JComboBox
				@SuppressWarnings("rawtypes")
				JComboBox comboBox = new JComboBox();
				comboBox.addItem(value);
				return comboBox;
			}
			break;
		case "CheckBox":
			System.out.println("CheckBox");
			// retorna un checkbox con el valor seleccionado
			if (col == column) {// la columna que contiene el JComboBox
				  //Color de fondo en modo edicion
		        ( (JCheckBox) component).setBackground( new Color(200,200,0) );
		        //obtiene valor de celda y coloca en el JCheckBox
		        boolean b = ((Boolean) value).booleanValue();
		        ( (JCheckBox) component).setSelected( b );
		        return ( (JCheckBox) component);
			}
			break;
		case "Color":
			String estado = (String) table.getValueAt(row, 0);
			if (estado.startsWith(("INGRESO"))) {
			} else {
				setBackground(Color.RED);
				setForeground(Color.WHITE);
			}
			break;
		default:
			setBackground(new Color(0xffffff));
			return this;
		}
		return cellComponent;
	}
}