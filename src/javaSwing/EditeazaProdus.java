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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import tranzactionSystem.Gestiune;
import tranzactionSystem.Produs;

public class EditeazaProdus extends JFrame {
	
	JFrame frame;
	JFrame parent;
	JComboBox<String> dropList;
	static File src;
	BufferedImage image;
	
	JLabel dummy = new JLabel(" ");
	
	JTextField denumire = new JTextField("Denumire");
	JTextField categorie = new JTextField("Categorie");
	JTextField []preturi = new JTextField[ Gestiune.getInstance().tari.size() ];
	
	JLabel ldenumire = new JLabel("Denumire");
	JLabel lcategorie = new JLabel("Categorie");
	JLabel []lpreturi = new JLabel[ Gestiune.getInstance().tari.size() ];
	
	public EditeazaProdus( String titlu, JFrame par ){
		
		super( titlu );
		setResizable(true);
		setSize(420, 345);
		frame = this;
		parent = par;
		
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
		title = BorderFactory.createTitledBorder(" Editeaza un produs ");
		
		//Panel
		JPanel panel = new JPanel(new GridLayout(7, 2)){
            @Override
			public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
		};
		panel.setPreferredSize (new Dimension (300 ,200) );
		panel.setBackground ( Color.lightGray );
		panel.setBorder ( title );
		
		//Creare ComboBox ( lista cu produsele )
		String[] lista = new String[Gestiune.getInstance().produse.size()];
		int i = 0;
		for( Produs prod : Gestiune.getInstance().produse )
		{
			boolean ok = false;
			for( int j = 0; j < i; j++ )
				if( lista[j].equals(prod.getDenumire() ) )
				{
					ok = true;
					break;
				}
			if( ok == false )
				lista[i++] = prod.getDenumire();
		}
		dropList = new JComboBox<>(lista);
		dropList.setSelectedIndex(0);
		panel.add(dropList);
		panel.add(dummy);
		
		//Instantieri text fields
		panel.add( ldenumire );
		panel.add( denumire );
		panel.add( lcategorie );
		panel.add( categorie );
		if( !Gestiune.getInstance().produse.isEmpty() )
		{
			Produs prod = Gestiune.getInstance().produse.get(0);
			denumire.setText( prod.getDenumire());
			categorie.setText( prod.getCategorie());
			int j = 0;
			for( String tara : Gestiune.getInstance().tari )
			{
				Produs pr = Gestiune.getInstance().produse.get(j);
				lpreturi[j] = new JLabel( tara );
				preturi[j] = new JTextField( "" + pr.getPret() );
				panel.add( lpreturi[j] );
				panel.add( preturi[j] );
				j++;
			}
		}
		
		dropList.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
				if( !Gestiune.getInstance().produse.isEmpty() )
				{
					Produs prod = Gestiune.getInstance().getProdusByNameAndCountry( dropList.getSelectedItem().toString(), 
																Gestiune.getInstance().tari.get(0));
					denumire.setText( prod.getDenumire());
					categorie.setText( prod.getCategorie());
					int j = 0;
					for( String tara : Gestiune.getInstance().tari )
					{
						
						Produs pr = Gestiune.getInstance().getProdusByNameAndCountry(prod.getDenumire(), tara);
						System.out.println(pr);
						preturi[j].setText( "" + pr.getPret() );
						j++;
					}
				}
		    }
		});
		
		JButton editeazaProdus = new JButton("Editeaza Produs");
		panel.add(editeazaProdus);
		
		editeazaProdus.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	int i = 0;
		    	for( String tara : Gestiune.getInstance().tari )
		    	{
		    		Produs prod = Gestiune.getInstance().getProdusByNameAndCountry( denumire.getText(), tara);
		    		Gestiune.getInstance().produse.remove(prod);
		    		prod.setPret( Double.parseDouble(preturi[i].getText()));
		    		prod.setCategorie( categorie.getText() );
		    		Gestiune.getInstance().produse.add(prod);
		    		editFromFile( denumire.getText(), prod.getCategorie());
		    		i++;
		    	}

		    	
    			parent.dispose();
	    		frame.dispose();
	    		new AdministrareProduse("Afisare si Administrare Produse").setVisible(true);
		    }
		});
		
		add(panel, BorderLayout.CENTER );
	}
	
	void editFromFile( String nume, String categorie ){
		
		File tempFile = new File("tempFile");
		try{
			tempFile.createNewFile();
			Scanner scanner = new Scanner( Gestiune.fProduse );
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(tempFile, true)));
			while( scanner.hasNextLine() )
			{
				String line = scanner.nextLine();
				if( !line.startsWith( nume ) )
				{
					//System.out.println(line);
					writer.append(line + System.lineSeparator());
				}
				else
				{
					writer.append(denumire.getText() + " " + categorie);
					int i = 0;
					for( String tara : Gestiune.getInstance().tari )
						writer.append( " " + preturi[i++].getText());
					writer.append(System.lineSeparator());
				}
			}
			writer.flush();
			src = tempFile;
			copy();
			writer.close();
			scanner.close();

		    //Stergem continutul destinatiei
		    PrintWriter write = new PrintWriter(tempFile);
		    write.print("");
		    write.flush();
		    write.close();
			
		}catch( Exception e ){
			e.printStackTrace();
		}
		
		
	}
	
	public static void copy() throws IOException {
	    Scanner scanner = new Scanner( src );
	    System.out.println("New");
	    
	    //Stergem continutul destinatiei
	    PrintWriter writer = new PrintWriter(Gestiune.fProduse);
	    writer.print("");
	    writer.flush();
	    writer.close();
	    
	    //Copiem tot din stc in dest
	    FileWriter write = new FileWriter(Gestiune.fProduse, true);
	    while( scanner.hasNextLine() )
	    {
	    	String s = scanner.nextLine();
	    	System.out.println(s);
	    	if( scanner.hasNextLine() )
	    		write.append(s + System.lineSeparator());
	    	else
	    		write.append(s);
	    }
	    write.close();
	    scanner.close();
	}
}
