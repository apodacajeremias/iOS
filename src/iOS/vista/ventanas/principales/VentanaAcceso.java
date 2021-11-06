package iOS.vista.ventanas.principales;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import iOS.controlador.util.ConnectionHelper;
import iOS.controlador.util.EventosUtil;
import iOS.controlador.ventanas.principales.VentanaAccesoControlador;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;

public class VentanaAcceso extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1641801496110656126L;

	public static void main(String[] args) {
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
			ConnectionHelper.setUp();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAcceso dialog = new VentanaAcceso();
					dialog.setUpControlador();
					dialog.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
					EventosUtil.formatException(ex);
				}
			}

		});
	}

	private VentanaAccesoControlador controlador;
	
	public void setUpControlador() {
		controlador = new VentanaAccesoControlador(this);

	}

	private JLabel lblGif;
	private CampoTextoPersonalizado tUsuario;
	private JPasswordField tContra;
	private MiBoton btnCancelar;
	private MiBoton btnIngresar;
	private LabelPersonalizado lMensaje;

	/**
	 * Create the dialog.
	 */
	public VentanaAcceso() {
		getContentPane().setBackground(Color.WHITE);
		setTitle("INGRESAR");
		setAlwaysOnTop(true);
		setResizable(false);
		setBounds(100, 100, 400, 260);
		setLocationRelativeTo(this);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		lblGif = new JLabel("");
		lblGif.setIcon(new ImageIcon(VentanaAcceso.class.getResource("/img/LOGO_IOS.png")));
		lblGif.setForeground(Color.DARK_GRAY);
		lblGif.setHorizontalAlignment(SwingConstants.CENTER);
		lblGif.setBounds(178, 11, 200, 133);
		getContentPane().add(lblGif);

		tUsuario = new CampoTextoPersonalizado();
		tUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				switch (e.getClickCount() % 2) {
				case 1:
					tContra.selectAll();
					break;
				default:
					tContra.setSelectionStart(0);
					break;
				}
				
			}
		});
		tUsuario.mayusculas();
		tUsuario.setBounds(14, 42, 150, 30);
		getContentPane().add(tUsuario);
		tUsuario.setColumns(10);

		tContra = new JPasswordField();
		tContra.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					controlador.comprobarAcceso();
				}
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
			}
		});
		tContra.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tContra.selectAll();				
			}
		});
		tContra.setBounds(10, 114, 150, 30);
		getContentPane().add(tContra);

		btnCancelar = new MiBoton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnCancelar.setText("Cancelar");
		btnCancelar.setActionCommand("Cancelar");
		btnCancelar.setBounds(31, 181, 150, 30);
		getContentPane().add(btnCancelar);

		btnIngresar = new MiBoton("Acceder");
		btnIngresar.setActionCommand("Acceder");
		btnIngresar.setBounds(212, 181, 150, 30);
		getContentPane().add(btnIngresar);

		LabelPersonalizado lblprsnlzdUsuario = new LabelPersonalizado(12);
		lblprsnlzdUsuario.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdUsuario.setText("Usuario");
		
		lblprsnlzdUsuario.setBounds(10, 11, 100, 20);
		getContentPane().add(lblprsnlzdUsuario);

		LabelPersonalizado labelPersonalizado_1 = new LabelPersonalizado(12);
		labelPersonalizado_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelPersonalizado_1.setText("Contraseña");
		labelPersonalizado_1.setBounds(10, 83, 100, 20);
		getContentPane().add(labelPersonalizado_1);
		
		lMensaje = new LabelPersonalizado(0);
		lMensaje.setBounds(10, 155, 374, 15);
		getContentPane().add(lMensaje);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public VentanaAccesoControlador getControlador() {
		return controlador;
	}

	public JLabel getLblGif() {
		return lblGif;
	}

	public CampoTextoPersonalizado gettUsuario() {
		return tUsuario;
	}

	public JPasswordField gettContra() {
		return tContra;
	}

	public MiBoton getBtnCancelar() {
		return btnCancelar;
	}

	public MiBoton getBtnIngresar() {
		return btnIngresar;
	}

	public LabelPersonalizado getlMensaje() {
		return lMensaje;
	}
	

}
