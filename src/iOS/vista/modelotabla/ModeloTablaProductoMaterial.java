package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.entidades.ProductoMaterial;

public class ModeloTablaProductoMaterial extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7615729955805818086L;

	private String[] columnas = { "MATERIAL", "MEDIDA FIJA", "ALTO cm", "ANCHO cm", "EDITABLE", "COSTO", "CANT",
			"SUBTOTAL" };
	private List<ProductoMaterial> lista = new ArrayList<>();

	public void setLista(List<ProductoMaterial> lista) {
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
			return lista.get(rowIndex).isTieneMedidaFija();
		case 2:
			return EventosUtil.separadorMiles(lista.get(rowIndex).getAncho());
		case 3:
			return EventosUtil.separadorMiles(lista.get(rowIndex).getAlto());
		case 4:
			return lista.get(rowIndex).isEditable();
		case 5:
			return EventosUtil.separadorMiles(lista.get(rowIndex).getCosto());
		case 6:
			return EventosUtil.separadorMiles(lista.get(rowIndex).getCantidad());
		case 7:
			return EventosUtil.separadorMiles(lista.get(rowIndex).getSubtotal());
		default:
			break;
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int r, int c) {
		ProductoMaterial registro = lista.get(r);
		if (aValue == null || aValue.equals("")) {
			return;
		}
		if (1 == c) {
			registro.setTieneMedidaFija(!registro.isTieneMedidaFija());
		} else if (2 == c) {
			try {
				registro.setAlto((double) aValue);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (3 == c) {
			try {
				registro.setAncho((double) aValue);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (4 == c) {
			registro.setEditable(!registro.isEditable());
		} else if (6 == c) {
			try {
				registro.setCantidad(
						Double.parseDouble(aValue.toString().replace(".", "").replace(",", "").replace("\\s", "")));
				registro.setSubtotal(subtotal(registro));
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		fireTableDataChanged();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 1:
			return false;
		case 2:
			return true;
		case 3:
			return true;
		case 4:
			return false;
		case 6:
			return true;
		default:
			return false;
		}
	}

	private double subtotal(ProductoMaterial registro) {
		double subtotal = 0;
		try {
			subtotal = registro.getCosto() * registro.getCantidad();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return subtotal;
	}

}
