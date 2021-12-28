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
	private String[] columnas = { "PEDIDO", "CLIENTE", "PRECIO", "TIPO" };
	private List<Pedido> lista = new ArrayList<>();

	public void setPedidos(List<Pedido> lista) {
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
		return columnas[column];
	}

	@Override
	public Object getValueAt(int r, int c) {
		switch (c) {
		case 0:
			if (lista.get(r).isGeneraDeuda()) {
				return "\u2714" + lista.get(r).getId() + " - "
						+ EventosUtil.formatoFecha(lista.get(r).getFechaRegistro());
			} else {
				return "\u274C" + lista.get(r).getId() + " - "
						+ EventosUtil.formatoFecha(lista.get(r).getFechaRegistro());
			}

		case 1:
			return lista.get(r).getCliente().getNombreCompleto();
		case 2:
			return EventosUtil.separadorMiles((double) lista.get(r).getPrecioPagar());
		case 3:
			String pedido = "";
			String estado = "";
			String tipo = "";

			if (lista.get(r).isEsPresupuesto()) {
				pedido = "PRESUPUESTO";
			} else {
				pedido = "PEDIDO";
			}
			if (lista.get(r).getPedidoCarteleria() != null && lista.get(r).getPedidoCarteleria() == true) {
				tipo = "CARTELERIA";
			}
			if (lista.get(r).getPedidoCostura() != null && lista.get(r).getPedidoCostura() == true) {
				tipo = "CONFECCION";
			}
			if (lista.get(r).isEstado()) {
				estado = "VIGENTE";
			} else {
				estado = "ANULADO";
			}

			return pedido + " " + tipo + " " + estado;

//			if (lista.get(r).isEsPresupuesto()) {
//				if (lista.get(r).isEstado()) {
//					return "PRESUPUESTO CARTELERIA";
//				} else {
//					return "PRESUPUESTO ANULADO";
//				}
//				
//			}else {
//				if (lista.get(r).isEstado()) {
//					return "PEDIDO CARTELERIA";
//				} else {
//					return "PEDIDO ANULADO";
//				}
//			}
		default:
			break;
		}
		return null;
	}

}
