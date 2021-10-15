package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.entidades.PedidoDetalleConfeccion;

public class ModeloTablaPedidoConfeccionDetalle extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -545738629065442036L;
	private String[] columnas = {"PRODUCTO","INDICACIONES","CANT.","TAM.","MOLDE","PRECIO","SUBTOTAL"};

	private List<PedidoDetalleConfeccion> detalle = new ArrayList<>();

	public void setDetalle(List<PedidoDetalleConfeccion> detalle) {
		this.detalle = detalle;
		fireTableDataChanged();
	}

	@Override
	public String getColumnName(int c) {

		return columnas[c];
	}

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
			return detalle.get(r).getProducto();
		case 1:
			return detalle.get(r).getArchivo();
		case 2:
			return EventosUtil.separadorMiles(detalle.get(r).getCantidadDetalle());
		case 3:
			return detalle.get(r).getTamano();
		case 4:
			return detalle.get(r).getMolde();
		case 5:
			return EventosUtil.separadorMiles(detalle.get(r).getPrecioProducto());
		case 6:
			return EventosUtil.separadorMiles(detalle.get(r).getPrecioDetalle());
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int r, int c) {
		PedidoDetalleConfeccion row = detalle.get(r);		
		if (aValue == null || aValue.equals("")) {
			return;
		}

		if(1 == c) {
			try {
				row.setArchivo(aValue.toString().toUpperCase());
			} catch (Exception e) {
				return;
			}
		}
		else if(2 == c) {
			try {
				row.setCantidadDetalle(Double.parseDouble(aValue.toString().replace(".", "").replace(",", "").replace("\\s","")));
			} catch (NumberFormatException e) {
				return;
			}
		}
		else if(3 == c) {
			try {
				row.setTamano(aValue.toString().toUpperCase());
			} catch (Exception e) {
				return;
			}
		}
		else if(4 == c) {
			try {
				row.setMolde(aValue.toString().toUpperCase());
			} catch (Exception e) {
				return;
			}
		}
		else if(5 == c) {
			try {
				row.setPrecioProducto(Double.parseDouble(aValue.toString().replace(".", "").replace(",", "")));
			} catch (NumberFormatException e) {
				return;
			}
		}
	}
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 1:
			return true;
		case 2:
			return true;
		case 3:
			return true;
		case 4:
			return true;
		case 5:
			return true;
		default:
			return false;
		}
	}
}