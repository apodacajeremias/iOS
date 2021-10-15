package iOS.vista.componentes;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;

@SuppressWarnings("rawtypes")
public class ComboBoxPersonalizado extends JComboBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7573436194642023450L;
	private Connection sqlCon;
	private Statement st;

	public ComboBoxPersonalizado() {
		super();
		initComponents();
	}

	private void initComponents() {
		try {             
			st = sqlCon.createStatement();
			loadCombobox();
		} catch (SQLException sqle) {
			System.out.println(sqle);
		}
	}



	@SuppressWarnings("unchecked")
	public void loadCombobox(){ 
		this.removeAllItems();
		this.addItem("Por favor elegir...");

		try {
			ResultSet rs = st.executeQuery("select nombre, apellidos from myTable"); 
			while (rs.next()) {
				this.addItem(rs.getString("nombre") + " " + rs.getString("apellidos"));
			}



		} catch (SQLException sqle) {
			System.out.println(sqle);
		}
	}
}	
