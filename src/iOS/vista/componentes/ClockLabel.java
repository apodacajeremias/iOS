package iOS.vista.componentes;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class ClockLabel extends JLabel implements ActionListener {

	String type;
	SimpleDateFormat sdf;

	public ClockLabel(String type) {
		this.type = type;
		setForeground(Color.green);

		switch (type) {
		case "fecha" : sdf = new SimpleDateFormat("  MMMM dd yyyy");
		setFont(new Font("sans-serif", Font.PLAIN, 12));
		setHorizontalAlignment(SwingConstants.LEFT);
		break;
		case "hora" : sdf = new SimpleDateFormat("hh:mm:ss a");
		setFont(new Font("sans-serif", Font.PLAIN, 40));
		setHorizontalAlignment(SwingConstants.CENTER);
		break;
		case "dia"  : sdf = new SimpleDateFormat("EEEE  ");
		setFont(new Font("sans-serif", Font.PLAIN, 16));
		setHorizontalAlignment(SwingConstants.RIGHT);
		break;
		default     : sdf = new SimpleDateFormat();
		break;
		}

		Timer t = new Timer(1000, this);
		t.start();
	}

	public void actionPerformed(ActionEvent ae) {
		Date d = new Date();
		setText(sdf.format(d));
	}
}