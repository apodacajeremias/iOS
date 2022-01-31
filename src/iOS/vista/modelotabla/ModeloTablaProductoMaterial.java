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
			switch (lista.get(rowIndex).getMaterial().getTipoCobro()) {
			case "UNIDAD":
				if (lista.get(rowIndex).isTieneMedidaFija()) {
					return EventosUtil.separadorMiles(lista.get(rowIndex).getAlto());
				} else {
					return "ɸ";
				}
			case "METRO CUADRADO":
				if (lista.get(rowIndex).isTieneMedidaFija()) {
					return EventosUtil.separadorMiles(lista.get(rowIndex).getAlto());
				} else {
					return "~";
				}
			case "METRO LINEAL":
				if (lista.get(rowIndex).isTieneMedidaFija()) {
					return EventosUtil.separadorMiles(lista.get(rowIndex).getAlto());
				} else {
					return "~";
				}
			default:
				break;
			}
		case 3:
			switch (lista.get(rowIndex).getMaterial().getTipoCobro()) {
			case "UNIDAD":
				if (lista.get(rowIndex).isTieneMedidaFija()) {
					return EventosUtil.separadorMiles(lista.get(rowIndex).getAncho());
				} else {
					return "ɸ";
				}
			case "METRO CUADRADO":
				if (lista.get(rowIndex).isTieneMedidaFija()) {
					return EventosUtil.separadorMiles(lista.get(rowIndex).getAncho());
				} else {
					return "~";
				}
			case "METRO LINEAL":
				if (lista.get(rowIndex).isTieneMedidaFija()) {
					return EventosUtil.separadorMiles(lista.get(rowIndex).getArea());
				} else {
					return "~";
				}
			default:
				break;
			}
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
				registro.setAlto(Double.parseDouble((String) aValue));
				registro.setArea(area(registro));
				registro.setSubtotal(subtotal(registro));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (3 == c) {
			try {
				registro.setAncho(Double.parseDouble((String) aValue));
				registro.setArea(area(registro));
				registro.setSubtotal(subtotal(registro));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (4 == c) {
			registro.setEditable(!registro.isEditable());
		} else if (6 == c) {
			try {
				registro.setCantidad(Double.parseDouble(aValue.toString().replace(",", ".").replace("\\s", "")));
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

	private double area(ProductoMaterial registro) {
		double area = 0;
		try {
			area = (registro.getAlto() * registro.getAncho()) / 10000;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return area;
	}

	private double subtotal(ProductoMaterial registro) {
		double subtotal = 0;
		try {
			switch (registro.getMaterial().getTipoCobro()) {
			case "UNIDAD":
				subtotal = registro.getCosto() * registro.getCantidad();
				break;
			case "METRO CUADRADO":
				subtotal = (registro.getCosto() * registro.getCantidad() * registro.getArea());
				break;
			case "METRO LINEAL":
				subtotal = (registro.getCosto() * registro.getCantidad() * registro.getArea());
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return subtotal;
	}
}
