package javaSwing;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import tranzactionSystem.Gestiune;
import tranzactionSystem.Produs;

public class AdministrareProduse extends JFrame {
	JFrame frame;
	
	public AdministrareProduse(String titlu){
		super( titlu );
		setResizable(false);
		setSize(520, 545);
		setLayout (new BorderLayout());
		frame = this;
		
		//Bordura
		TitledBorder title ;
		title = BorderFactory.createTitledBorder(" Incarcare fisiere ");
		final JPanel panel = new JPanel();
		panel.setPreferredSize (new Dimension (500 ,500) );
		panel.setBackground ( Color.lightGray );
		panel.setBorder ( title );
		add( panel );
		
		//Creare vector pentru afisare cu JTable
		Gestiune gestiune = Gestiune.getInstance();
		Object data[][];
		data = new Object[gestiune.produse.size()][4];
		int i = 0;
		System.out.println();
		for( Produs prod : gestiune.produse )
		{
			data[i][0] = prod.getDenumire();
			data[i][1] = prod.getCategorie();
			data[i][2] = prod.getPret();
			data[i][3] = prod.getTaraOrigine();
			i++;
		}
		String[] coloane = {"Denumire", "Categorie", "Pret", "Tara Origine"};
		JTable tabela = new JTable(data, coloane);
		
		//Sortare tabel
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabela.getModel());
		tabela.setRowSorter(sorter);
		
		List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
		
		JScrollPane scrollPane = new JScrollPane(tabela, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
		        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tabela.setFillsViewportHeight(true);
		//panel.add(tabela.getTableHeader(), BorderLayout.PAGE_START);
		//panel.add(tabela, BorderLayout.CENTER);
		panel.add(scrollPane);
		
		Button btn1 = new Button("Adauga Produs");
		btn1.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	new AdaugaProdus("Adauga Produs", frame).setVisible(true);
		    }
		});
		panel.add(btn1);
		Button btn2 = new Button("Sterge Produs");
		btn2.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	new StergeProdus("StergeProdus", frame).setVisible(true);
		    }
		});
		panel.add(btn2);
		Button btn3 = new Button("Editeaza Produs");
		panel.add(btn3);
		Button btn4 = new Button("Cauta Produs");
		panel.add(btn4);
	}
}
