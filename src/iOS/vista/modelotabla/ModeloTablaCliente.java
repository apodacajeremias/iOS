package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.modelo.entidades.Cliente;

public class ModeloTablaCliente extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7615729955805818086L;
	
	private String[] columnas = { "NOMBRE COMPLETO", "IDENTIFICACION", "CONTACTO" };
	private List<Cliente> lista = new ArrayList<>();
	
	public void setLista(List<Cliente> lista) {
		this.lista = lista;
		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return lista.size();
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
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return lista.get(rowIndex);
		case 1:
			return lista.get(rowIndex).getIdentificacion();
		case 2:
			return lista.get(rowIndex).getContacto();
		default:
			break;
		}
		return null;
	}

}
