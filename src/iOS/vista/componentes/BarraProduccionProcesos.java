package iOS.vista.componentes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import iOS.modelo.dao.ProcesoDao;
import iOS.modelo.entidades.Proceso;

public class BarraProduccionProcesos implements ActionListener {

	/**
	 * 
	 */
	private List<MiBoton> listaBotones = new ArrayList<>();
	private JTextField tf;
	JFrame frame;
	private JPanel panelContenedor, panelAux;
	private MiBoton boton;
	private List<Proceso> procesos = new ArrayList<Proceso>();
	private ProcesoDao dao = new ProcesoDao();

	public BarraProduccionProcesos() {
		frame = new JFrame("Botones en tiempo de ejecucion!");
		frame.getContentPane().setLayout(new BorderLayout());

		boton = new MiBoton("Dame clic");
		boton.addActionListener(this);

		tf = new JTextField(10);

		panelContenedor = new JPanel();
		panelContenedor.setLayout(new GridLayout(5, 5));

		panelAux = new JPanel();
		panelAux.setLayout(new FlowLayout());
		panelAux.add(tf);
		panelAux.add(boton);

		frame.add(panelAux, BorderLayout.NORTH);
		frame.add(panelContenedor, BorderLayout.CENTER);

		frame.setSize(600, 400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int numero = Integer.parseInt(tf.getText());
		procesos = dao.recuperarTodoOrdenadoPorNombre();
		// Primero llenamos con el numero de elementos requeridos
		for (int i = 0; i < procesos.size(); i++) {
			MiBoton boton = new MiBoton(procesos.get(i));
			boton.setActionCommand(procesos.get(i).getDescripcion());
//			listaBotones.add(new JButton("Boton " + i));
			listaBotones.add(boton);
		}
		// Despues lo agregamos al panel
		Iterator<MiBoton> itera = listaBotones.listIterator();
		while (itera.hasNext()) {
			panelContenedor.add(itera.next());
		}
		frame.validate();

	}

	public static void main(String args[]) {
		new BarraProduccionProcesos();
	}
}
