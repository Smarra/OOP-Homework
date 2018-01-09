package javaSwing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import tranzactionSystem.Gestiune;
import tranzactionSystem.Produs;

public class AdaugaProdus extends JFrame{
	
	JTextField t1, t2, t3, t4;
	JFrame parent;
	JFrame frame;
	ArrayList<JTextField> preturi = new ArrayList<>();
	
	public AdaugaProdus(String titlu, JFrame p){
		super( titlu );
		setResizable(true);
		setSize(320, 245);
		parent = p;
		frame = this;
		
		TitledBorder title ;
		title = BorderFactory.createTitledBorder(" Adauga un produs ");
		JPanel panel = new JPanel(new GridLayout(10, 2));
		panel.setPreferredSize (new Dimension (300 ,200) );
		panel.setBackground ( Color.lightGray );
		panel.setBorder ( title );
		
		JLabel lb1 = new JLabel("Denumire: ");
		t1 = new JTextField(15);
		JLabel lb2 = new JLabel("Categorie: ");
		t2 = new JTextField(15);
		JLabel lb3 = new JLabel("Tara: ");
		t3 = new JTextField(15);
		JLabel lb4 = new JLabel("Pret: ");
		t4 = new JTextField(15);
		
		panel.add(lb1);
		panel.add(t1);
		panel.add(lb2);
		panel.add(t2);
		panel.add(lb3);
		panel.add(t3);
		panel.add(lb4);
		panel.add(t4);
		
		Gestiune gestiune = Gestiune.getInstance();
		for( String tara : gestiune.tari )
		{
			JLabel lb = new JLabel( tara );
			JTextField pret = new JTextField(15);
			preturi.add(pret);
			panel.add(lb);
			panel.add(pret);
		}
		
		JButton adaugaProdus = new JButton("Adauga Produs");
		panel.add(adaugaProdus);
		JButton adaugaProdusToateTarile = new JButton("Adauga Produs Toate Tarile");
		panel.add(adaugaProdusToateTarile);
		
		adaugaProdus.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	Produs prod = new Produs( t1.getText(), t2.getText(), t3.getText(), Double.parseDouble(t4.getText()));
		    	Gestiune gestiune = Gestiune.getInstance();
		    	if( !gestiune.checkProdus( prod ) )
		    	{
		    		gestiune.produse.add(prod);
		    		FileWriter writer;
					try{
						writer = new FileWriter(Gestiune.fProduse, true);
						System.out.println(gestiune.tari);
						writer.append(System.lineSeparator() + prod.getDenumire() + " " + prod.getCategorie() + " ");
						for( String tara : gestiune.tari)
							if( tara.equals(prod.getTaraOrigine()) )
								writer.append("" + prod.getPret() + " ");
							else
								writer.append("0 ");
						writer.close();
					}catch( Exception ex){
						ex.printStackTrace();
					}
		    		parent.dispose();
		    		frame.dispose();
		    		new AdministrareProduse("Afisare si Administrare Produse").setVisible(true);
		    	}
		    	else
		    		JOptionPane.showMessageDialog(null, "Produsul se afla deja in baza de date.");
		    }
		});
		
		adaugaProdusToateTarile.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	Gestiune gestiune = Gestiune.getInstance();
		    	Produs dummy = new Produs( t1.getText(), t2.getText(), "", 0);
			    if( !gestiune.checkProdus( dummy) )
			    {
			    	FileWriter writer;
					try{
						writer = new FileWriter(Gestiune.fProduse, true);
						System.out.println(gestiune.tari);
						writer.append(System.lineSeparator() + t1.getText() + " " + t2.getText() + " ");
						int i = 0;
						for( JTextField pret : preturi )
						{
							Produs prod = new Produs( t1.getText(), t2.getText(), 
														gestiune.tari.get(i++), Double.parseDouble(pret.getText()) );
							gestiune.produse.add(prod);
							writer.append("" + pret.getText() + " ");
						}
						writer.close();
					}catch( Exception ex){
						ex.printStackTrace();
					}
			    	parent.dispose();
			    	frame.dispose();
			    	new AdministrareProduse("Afisare si Administrare Produse").setVisible(true);
			    }
			    else
			    	JOptionPane.showMessageDialog(null, "Produsul se afla deja in baza de date.");
		    }
		});
		
		add(panel, BorderLayout.CENTER );
	}
}
