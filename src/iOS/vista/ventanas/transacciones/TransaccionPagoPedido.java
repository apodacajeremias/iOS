package iOS.vista.ventanas.transacciones;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import iOS.controlador.util.ConnectionHelper;
import iOS.controlador.util.EventosUtil;
import iOS.controlador.ventanas.transacciones.TransaccionPagoPedidoControlador;
import iOS.vista.componentes.LabelPersonalizado;

public class TransaccionPagoPedido extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4188741087606045016L;
	private JTable tableCliente;
	private JTable tablePago;
	private JTable tablePedido;
	private JTable tableRelacion;
	private TransaccionPagoPedidoControlador controlador;

	/**
	 * 
	 */
	public static void main(String[] args) {
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
			ConnectionHelper.setUp();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					TransaccionPagoPedido dialog = new TransaccionPagoPedido();
					dialog.setUpControlador();
					dialog.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
					EventosUtil.formatException(ex);
				}
			}

		});
	}
	
	private void setUpControlador() {
		controlador = new TransaccionPagoPedidoControlador(this);

	}

	/**
	 * Create the dialog.
	 */
	public TransaccionPagoPedido() {
		getContentPane().setBackground(new Color(255, 255, 255));
		setFont(new Font("Tahoma", Font.BOLD, 12));
		setBackground(new Color(51, 51, 51));
		setBounds(100, 100, 1280, 640);
		setLocationRelativeTo(this);
		setResizable(false);
		setModal(true);
		setTitle("Registro de Pagos");
		getContentPane().setLayout(null);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(14, 48, 300, 552);
		getContentPane().add(scrollPane_3);

		tableCliente = new JTable();
		scrollPane_3.setViewportView(tableCliente);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(324, 426, 932, 180);
		getContentPane().add(scrollPane_2);

		tablePago = new JTable();
		scrollPane_2.setViewportView(tablePago);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(324, 22, 932, 180);
		getContentPane().add(scrollPane_1);

		tablePedido = new JTable();
		scrollPane_1.setViewportView(tablePedido);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(324, 224, 932, 180);
		getContentPane().add(scrollPane);

		tableRelacion = new JTable();
		scrollPane.setViewportView(tableRelacion);

		LabelPersonalizado lblprsnlzdCliente = new LabelPersonalizado(0);
		lblprsnlzdCliente.setText("Cliente");
		lblprsnlzdCliente.setBounds(16, 24, 200, 20);
		getContentPane().add(lblprsnlzdCliente);
		
		LabelPersonalizado lblprsnlzdPagoDisponibleEn = new LabelPersonalizado(0);
		lblprsnlzdPagoDisponibleEn.setText("Pago disponible en perfil del cliente");
		lblprsnlzdPagoDisponibleEn.setBounds(324, 405, 351, 20);
		getContentPane().add(lblprsnlzdPagoDisponibleEn);
		
		LabelPersonalizado lblprsnlzdPagosAsociadosAl = new LabelPersonalizado(0);
		lblprsnlzdPagosAsociadosAl.setText("Pagos asociados al pedido seleccionado");
		lblprsnlzdPagosAsociadosAl.setBounds(324, 203, 351, 20);
		getContentPane().add(lblprsnlzdPagosAsociadosAl);
		
		LabelPersonalizado lblprsnlzdPedidosDisponiblesEn = new LabelPersonalizado(0);
		lblprsnlzdPedidosDisponiblesEn.setText("Pedidos disponibles en el perfil del cliente");
		lblprsnlzdPedidosDisponiblesEn.setBounds(324, 1, 351, 20);
		getContentPane().add(lblprsnlzdPedidosDisponiblesEn);
		setType(Type.NORMAL);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JTable getTableCliente() {
		return tableCliente;
	}

	public JTable getTablePago() {
		return tablePago;
	}

	public JTable getTablePedido() {
		return tablePedido;
	}

	public JTable getTableRelacion() {
		return tableRelacion;
	}

	public TransaccionPagoPedidoControlador getControlador() {
		return controlador;
	}
}
