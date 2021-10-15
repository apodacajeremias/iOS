package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.modelo.entidades.Modulo;

public class ModeloTablaModulo extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7615729955805818086L;
	
	private String[] columnas = { "MODULO" };
	private List<Modulo> modulo = new ArrayList<>();
	
	public void setModulos(List<Modulo> modulo) {
		this.modulo = modulo;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return modulo.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnas.length;
	}
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return columnas[column];
	}
	
	
	@Override
	public Object getValueAt(int r, int c) {
		switch (c) {
		case 0:
			return modulo.get(r);
		default:
			break;
		}
		return null;
	}

}

