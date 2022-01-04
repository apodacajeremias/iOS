package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.entidades.PedidoDetalleMaterial;

public class ModeloTablaPedidoDetalleMaterial extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -545738629065442036L;
	private String[] columnas = { "DETALLE", "PRECIO" };

	private List<PedidoDetalleMaterial> materiales = new ArrayList<>();

	public void setMateriales(List<PedidoDetalleMaterial> materiales) {
		this.materiales = materiales;
		fireTableDataChanged();
	}

	@Override
	public String getColumnName(int c) {

		return columnas[c];
	}

	@Override
	public int getColumnCount() {

		return columnas.length;
	}

	@Override
	public int getRowCount() {
		return materiales.size();
	}

	@Override
	public Object getValueAt(int r, int c) {
		switch (c) {
		case 0:
			return materiales.get(r).getMaterial();
		case 1:
			return EventosUtil.separadorMiles(materiales.get(r).getPrecio());
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int r, int c) {
		PedidoDetalleMaterial row = materiales.get(r);
		if (1 == c) {
			try {
				row.setPrecio((double) Double.parseDouble((String) aValue));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 1:
			return true;
		default:
			return false;
		}
	}
}