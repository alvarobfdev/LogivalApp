/*
 * Created by JFormDesigner on Thu Feb 04 09:04:07 CET 2016
 */

package app;

import java.awt.CardLayout;

import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import com.mysql.jdbc.MySQLConnection;
import com.toedter.calendar.*;

/**
 * @author asd asd
 */
public class ResumenFactura extends JPanel implements MyJPanel {
	public ResumenFactura(CardLayout cardLayout) {
		
		super(cardLayout);

		initComponents();
		
	}
	
	public void execute() {
		
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - asd asd
		label1 = new JLabel();
		label2 = new JLabel();
		dateChooser1 = new JDateChooser();
		label3 = new JLabel();
		dateChooser2 = new JDateChooser();

		//======== this ========

		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

		setLayout(new FormLayout(
			"115dlu, 200dlu, [216dlu,default]",
			"5*(default, $lgap), default"));

		//---- label1 ----
		label1.setText("LISTADO RESUMEN DE FACTURAS EMITIDAS");
		add(label1, CC.xy(2, 3, CC.CENTER, CC.DEFAULT));

		//---- label2 ----
		label2.setText("Periodo inicio:");
		add(label2, CC.xy(1, 7, CC.RIGHT, CC.DEFAULT));
		add(dateChooser1, CC.xy(2, 7));

		//---- label3 ----
		label3.setText("Periodo final:");
		add(label3, CC.xy(1, 9, CC.RIGHT, CC.DEFAULT));
		add(dateChooser2, CC.xy(2, 9));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - asd asd
	private JLabel label1;
	private JLabel label2;
	private JDateChooser dateChooser1;
	private JLabel label3;
	private JDateChooser dateChooser2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
