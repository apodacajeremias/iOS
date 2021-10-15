package iOS.vista.ventanas.principales;



import java.awt.Color;
import java.awt.EventQueue;
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

	private static final long serialVersionUID = 5266841984275796870L;
	private JPasswordField tContra;
	private CampoTextoPersonalizado tUsuario;
	private MiBoton btnCancelar;
	private MiBoton btnIngresar;
	private JLabel lblGif;	
	private VentanaAccesoControlador controlador;
	private LabelPersonalizado lMensaje;

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
					Thread.sleep(10);
					dialog.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
					EventosUtil.formatException(ex);
				}
			}

		});
	}
	
	private void setUpControlador() {
		controlador = new VentanaAccesoControlador(this);
	}
	
	public VentanaAcceso() {
		getContentPane().setBackground(Color.WHITE);
		setTitle("INGRESAR");
		setResizable(false);
		setBounds(100, 100, 400, 300);
		setLocationRelativeTo(this);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		lblGif = new JLabel("");
		lblGif.setIcon(new ImageIcon(VentanaAcceso.class.getResource("/img/LOGO_IOS.png")));
		lblGif.setForeground(Color.DARK_GRAY);
		lblGif.setHorizontalAlignment(SwingConstants.CENTER);
		lblGif.setBounds(180, 52, 200, 160);
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
		tUsuario.setBounds(20, 83, 150, 30);
		getContentPane().add(tUsuario);
		tUsuario.setColumns(10);

		tContra = new JPasswordField();
		tContra.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					controlador.acceder();
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
		tContra.setBounds(20, 155, 150, 30);
		getContentPane().add(tContra);

		btnCancelar = new MiBoton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnCancelar.setText("Cancelar");
		btnCancelar.setActionCommand("Cancelar");
		btnCancelar.setBounds(20, 227, 100, 30);
		getContentPane().add(btnCancelar);

		btnIngresar = new MiBoton("Acceder");
		btnIngresar.setActionCommand("Acceder");
		btnIngresar.setBounds(130, 227, 100, 30);
		getContentPane().add(btnIngresar);

		LabelPersonalizado lblprsnlzdUsuario = new LabelPersonalizado(12);
		lblprsnlzdUsuario.setText("Usuario");
		
		lblprsnlzdUsuario.setBounds(20, 52, 100, 20);
		getContentPane().add(lblprsnlzdUsuario);

		LabelPersonalizado labelPersonalizado_1 = new LabelPersonalizado(12);
		labelPersonalizado_1.setText("Contraseña");
		labelPersonalizado_1.setBounds(20, 124, 100, 20);
		getContentPane().add(labelPersonalizado_1);
		
		lMensaje = new LabelPersonalizado(0);
		lMensaje.setBounds(10, 193, 374, 15);
		getContentPane().add(lMensaje);
	}
	
	

	public JPasswordField gettContra() {
		return tContra;
	}

	public void settContra(JPasswordField tContra) {
		this.tContra = tContra;
	}

	public CampoTextoPersonalizado gettUsuario() {
		return tUsuario;
	}

	public void settUsuario(CampoTextoPersonalizado tUsuario) {
		this.tUsuario = tUsuario;
	}

	public MiBoton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(MiBoton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public MiBoton getBtnIngresar() {
		return btnIngresar;
	}

	public void setBtnIngresar(MiBoton btnIngresar) {
		this.btnIngresar = btnIngresar;
	}

	public JLabel getLblGif() {
		return lblGif;
	}

	public void setLblGif(JLabel lblGif) {
		this.lblGif = lblGif;
	}

	public VentanaAccesoControlador getControlador() {
		return controlador;
	}

	public void setControlador(VentanaAccesoControlador controlador) {
		this.controlador = controlador;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public LabelPersonalizado getlMensaje() {
		return lMensaje;
	}
	
}
