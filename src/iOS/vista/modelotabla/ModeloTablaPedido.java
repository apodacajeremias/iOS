package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.entidades.Pedido;

public class ModeloTablaPedido extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1864475015388508652L;
	private String[] columnas = {"PEDIDO", "CLIENTE", "PRECIO","TIPO"};
	private List<Pedido> lista = new ArrayList<>();

	public void setPedidos(List<Pedido> lista){
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

	public String getColumnName(int column){
		return columnas[column];
	}

	@Override
	public Object getValueAt(int r, int c) {
		switch (c) {
		case 0:
			return lista.get(r);
		case 1:
			return lista.get(r).getCliente().getNombreCompleto();
		case 2:
			return EventosUtil.separadorMiles((double) lista.get(r).getPrecioPagar());
		case 3:
			if (lista.get(r).isEsPresupuesto()) {
				if (lista.get(r).isEstado()) {
					return "PRESUPUESTO";
				} else {
					return "PRESUPUESTO ANULADO";
				}
				
			}else {
				if (lista.get(r).isEstado()) {
					return "PEDIDO";
				} else {
					return "PEDIDO ANULADO";
				}
				
			}
		default:
			break;
		}
		return null;
	}

}

