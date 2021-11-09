package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.modelo.entidades.SectorProceso;

public class ModeloTablaProceso extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7615729955805818086L;

	private String[] columnas = { "PROCESO", "REPETIBLE" };
	private List<SectorProceso> lista = new ArrayList<>();

	public void setProcesos(List<SectorProceso> lista) {
		this.lista = lista;
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
			return lista.get(rowIndex).isEsRepetible();
		default:
			break;
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int r, int c) {
		SectorProceso row = lista.get(r);
		if (0 == c) {
			try {
				row.setNombreProceso((String) aValue.toString().toUpperCase());
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} else if (1 == c) {
			try {
				row.setEsRepetible(Boolean.parseBoolean(aValue.toString()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}

		super.setValueAt(aValue, r, c);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return false;

		default:
			return true;
		}
	}
}
