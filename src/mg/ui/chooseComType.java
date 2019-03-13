package mg.ui;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class chooseComType extends JFrame{
	private JTable table;
	JScrollPane scrollPane;
	public chooseComType() {
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 246, 283, -233);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setBounds(20, 237, 273, -212);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		scrollPane.setColumnHeaderView(table);
		/*table = new JTable(data,columnNames);*/
		//getContentPane().add(table);
		
	}
}
