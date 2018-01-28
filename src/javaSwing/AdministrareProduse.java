package javaSwing;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import tranzactionSystem.Gestiune;
import tranzactionSystem.Produs;

public class AdministrareProduse extends JFrame {
	JFrame frame;
	TableRowSorter<TableModel> sorter;
	JTextField cautaProdus;
	BufferedImage image;
	
	public AdministrareProduse(String titlu){
		super( titlu );
		setResizable(false);
		setSize(520, 565);
		setLayout (new BorderLayout());
		frame = this;
		
		//Creeaza background
		try{
			image = ImageIO.read(new File( Gestiune.backgroundFilePath ));
		}catch( IOException e){
			e.printStackTrace();
		}
		
		//Centreaza fereastra principala
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - getHeight()) / 2);
	    setLocation(x, y);
		
		//Bordura
		TitledBorder title ;
		title = BorderFactory.createTitledBorder("");
		final JPanel panel = new JPanel(){
            @Override
			public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
		};
		panel.setPreferredSize (new Dimension (500 ,500) );
		panel.setBackground ( Color.lightGray );
		panel.setBorder ( title );
		
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
		sorter = new TableRowSorter<TableModel>(tabela.getModel());
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
		panel.add(scrollPane, BorderLayout.PAGE_START);
		
		Button btn1 = new Button("Adauga Produs");
		btn1.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	new AdaugaProdus("Adauga Produs", frame).setVisible(true);
		    }
		});
		panel.add(btn1, BorderLayout.LINE_START);
		Button btn2 = new Button("Sterge Produs");
		btn2.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	new StergeProdus("StergeProdus", frame).setVisible(true);
		    }
		});
		panel.add(btn2, BorderLayout.CENTER);
		Button btn3 = new Button("Editeaza Produs");
		btn3.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	new EditeazaProdus("Editeaza Produs", frame).setVisible(true);
		    }
		});
		panel.add(btn3, BorderLayout.LINE_END);
		Button btn4 = new Button("Cauta Produs");
		//panel.add(btn4, BorderLayout.LINE_START);
		
		JLabel lcautaProdus = new JLabel(" Cauta Produs: ");
		add( lcautaProdus, BorderLayout.LINE_START );
		cautaProdus = new JTextField("");
		cautaProdus.setPreferredSize(new Dimension(400, 100));
		add( cautaProdus, BorderLayout.LINE_END );
		
		cautaProdus.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = cautaProdus.getText();

                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

			@Override
			public void changedUpdate(DocumentEvent arg0) {
                String text = cautaProdus.getText();

                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
                String text = cautaProdus.getText();

                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
			}
        });
		
		add( panel, BorderLayout.PAGE_START );
	}
}
