package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.modelo.entidades.Operacion;

public class ModeloTablaOperacion extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7615729955805818086L;
	
	private String[] columnas = { "OPERACION" };
	private List<Operacion> operaciones = new ArrayList<>();
	
	public void setOperaciones(List<Operacion> operaciones) {
		this.operaciones = operaciones;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return operaciones.size();
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
			return operaciones.get(r);
		default:
			break;
		}
		return null;
	}

}

