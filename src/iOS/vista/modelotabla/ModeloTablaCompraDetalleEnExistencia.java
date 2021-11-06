package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.modelo.entidades.CompraDetalle;

public class ModeloTablaCompraDetalleEnExistencia extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -545738629065442036L;

	private String[] columnas = {"MATERIAL","AREA M2","CANT."};

	private List<CompraDetalle> detalle = new ArrayList<>();

	public void setDetalle(List<CompraDetalle> detalle) {
		this.detalle = detalle;
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
		return detalle.size();
	}

	@Override
	public Object getValueAt(int r, int c) {
		switch (c) {
		case 0:
			return detalle.get(r).getMaterial().getDescripcion();
		case 1:
			return detalle.get(r).getMedidaDetalle();
		case 2:
			return detalle.get(r).getCantidadDetalle();
		default:
			return null;
		}
	}
}

