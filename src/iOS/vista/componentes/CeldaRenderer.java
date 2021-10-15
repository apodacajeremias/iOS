package iOS.vista.componentes;


import java.awt.Color;
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CeldaRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2920471479030586542L;
	private int col = -1;
	private String accion;

	public CeldaRenderer(int col, String accion) {
		this.col = col;
		this.accion = accion;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		switch (accion) {
		case "ComboBox":
			//retorna un combo con el valor seleccionado
			if (col == column) {//la columna que tiene el JComboBox
				@SuppressWarnings("rawtypes")
				JComboBox comboBox = new JComboBox();            
				comboBox.addItem(value);                        
				return comboBox;            
			}
			break;
		case "Color":
			 String estado = (String) table.getValueAt(row, 0);
			 
		        if (estado.equalsIgnoreCase("ingreso")) {
		            setBackground(Color.YELLOW);
		            setForeground(Color.BLACK);
		        } else {
		            setBackground(Color.RED);
		            setForeground(Color.WHITE);
		        }
		default:
			break;
		}

		return cellComponent;
	}

	//	@Override
	//	public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column)
	//	{        
	//		setBackground(Color.white);
	//		table.setForeground(Color.black);
	//		super.getTableCellRendererComponent(table, value, selected, focused, row, column);
	//		if(table.getValueAt(row,columna).equals("A"))
	//		{
	//			this.setForeground(Color.RED);
	//		}else if(table.getValueAt(row,columna).equals("B")){
	//			this.setForeground(Color.BLUE);
	//		}else if(table.getValueAt(row, columna).equals("C")){
	//			this.setForeground(Color.GREEN);
	//		}
	//		return this;
	//	}
	//}
}