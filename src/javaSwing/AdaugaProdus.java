package javaSwing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import tranzactionSystem.Gestiune;
import tranzactionSystem.Produs;

public class AdaugaProdus extends JFrame{
	
	JTextField t1, t2;
	JFrame parent;
	JFrame frame;
	ArrayList<JTextField> preturi = new ArrayList<>();
	BufferedImage image;
	
	public AdaugaProdus(String titlu, JFrame p){
		super( titlu );
		setResizable(true);
		setSize(320, 345);
		parent = p;
		frame = this;
		
		//Creeaza background
		try{
			image = ImageIO.read(new File( Gestiune.backgroundFilePath ));
		}catch( IOException e){
			e.printStackTrace();
		}
		
		//Creeaza panel
		JPanel panel = new JPanel(new GridLayout(10, 2)){
            @Override
			public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
		};
		panel.setPreferredSize (new Dimension (300 ,200) );
		panel.setBackground ( Color.lightGray );
		
		//Centreaza fereastra principala
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - getHeight()) / 2);
	    setLocation(x, y);
		
	    //Creeaza bordura
		TitledBorder title ;
		title = BorderFactory.createTitledBorder(" Adauga un produs ");
		panel.setBorder ( title );
		
		//Umple panel-ul cu label-uri si text field-uri care descriu un produs
		JLabel lb1 = new JLabel("Denumire: ");
		t1 = new JTextField(15);
		JLabel lb2 = new JLabel("Categorie: ");
		t2 = new JTextField(15);
		
		panel.add(lb1);
		panel.add(t1);
		panel.add(lb2);
		panel.add(t2);
		
		//Actualizam label-urile cu numele fiecarei tari
		for( String tara : Gestiune.getInstance().tari )
		{
			JLabel lb = new JLabel( tara );
			JTextField pret = new JTextField(15);
			preturi.add(pret);
			panel.add(lb);
			panel.add(pret);
		}
		
		//Adaugam la panel butoanele de interactiune cu baza de date
		JButton adaugaProdus = new JButton("Adauga Produs");
		panel.add(adaugaProdus);
		adaugaProdus.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	//verifica validitate
				boolean ok = true;
		    	for( JTextField pret : preturi )
					if( pret.getText().equals("") )
						ok = false;
		    	if( t1.getText().equals("") || t2.getText().equals("") )
		    		ok = false;
		    	
		    	if( ok == true )
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
		    	else
		    		JOptionPane.showMessageDialog(null, "Invalid input");
		    }
		});
		
		//Adauga la la frame-ul principal panelul
		add(panel, BorderLayout.CENTER );
	}
}
