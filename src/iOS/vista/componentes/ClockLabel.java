package iOS.vista.componentes;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
		case "date":
			sdf = new SimpleDateFormat("MMMM dd yyyy");
			setFont(new Font("sans-serif", Font.PLAIN, 12));
			setHorizontalAlignment(SwingConstants.LEFT);
			break;
		case "time":
			sdf = new SimpleDateFormat("hh:mm:ss a");
			setFont(new Font("sans-serif", Font.PLAIN, 40));
			setHorizontalAlignment(SwingConstants.CENTER);
			break;
		case "day":
			sdf = new SimpleDateFormat("EEEE");
			setFont(new Font("sans-serif", Font.PLAIN, 16));
			setHorizontalAlignment(SwingConstants.RIGHT);
			break;
		default:
			sdf = new SimpleDateFormat();
			break;
		}

		Timer t = new Timer(1000, this);
		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Date d = new Date();
		setText(sdf.format(d));
	}

	static class DigitalClock {

		public static void main(String[] arguments) {

			ClockLabel dateLable = new ClockLabel("date");
			ClockLabel timeLable = new ClockLabel("time");
			ClockLabel dayLable = new ClockLabel("day");

			JFrame.setDefaultLookAndFeelDecorated(true);
			JPanel f = new JPanel();
			f.setSize(300, 150);
			f.setLayout(new GridLayout(3, 1));

			f.add(dateLable);
			f.add(timeLable);
			f.add(dayLable);

			f.setBackground(Color.white);

			f.setVisible(true);
		}
	}
}