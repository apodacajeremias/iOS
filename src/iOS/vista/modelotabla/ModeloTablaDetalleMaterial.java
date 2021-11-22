package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.entidades.DetalleMaterial;

public class ModeloTablaDetalleMaterial extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7615729955805818086L;
	
	private String[] columnas = {"DESCRIPCION", "VALOR"};
	private List<DetalleMaterial> lista = new ArrayList<>();
	
	public void setLista(List<DetalleMaterial> lista) {
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
			return lista.get(rowIndex).getMaterial();
		case 1:
			return EventosUtil.separadorMiles(lista.get(rowIndex).getPrecioMaterial());
		default:
			break;
		}
		return null;
	}
	
	@Override
	public void setValueAt(Object aValue, int r, int c) {
		DetalleMaterial row = lista.get(r);
		if (1 == c) {
			try {
				String nv = aValue.toString().replace(",", "").replace(".", "").replace("cm", "").replace("m", "")
						.replace("\\scm", "").replace("\\sm", "").replace("\\s", "");
				row.setPrecioMaterial(Double.parseDouble(nv));
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} 
	}
}

