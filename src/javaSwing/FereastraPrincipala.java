package javaSwing;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import tranzactionSystem.Gestiune;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class FereastraPrincipala extends JFrame {
	
	BufferedImage image;
	
	public FereastraPrincipala( String titlu ){
		super( titlu );
		setResizable(false);
		setSize(320, 445);
		setDefaultCloseOperation ( JFrame . EXIT_ON_CLOSE );
		
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
		
		//Bordura si Panel
		TitledBorder title ;
		title = BorderFactory.createTitledBorder("");
		final JPanel panel = new JPanel( new GridLayout(10, 1)){
            @Override
			public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
		};
		panel.setPreferredSize (new Dimension (300 ,400) );
		panel.setBorder ( title );
		add( panel );
		
		//Adaugam butoanele panoului principal
		JButton btn1 = new JButton (" Pagina de incarcare ");
		panel.add( btn1 );
		btn1.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        new IncarcareFisiere("Incarcare Fisiere").setVisible(true);
		    }
		});
				
		JButton btn2 = new JButton (" Afisare si administrare produse ");
		panel.add( btn2 );
		btn2.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	if( Gestiune.fFacturi != null && Gestiune.fProduse != null && Gestiune.fTaxe != null && Gestiune.filesLoaded == true)
		    		new AdministrareProduse("Afisare si Administrare Produse").setVisible(true);
		    	else
		    		JOptionPane.showMessageDialog(null, "Fisierele nu sunt pregatite.");
		    }
		});

		JButton btn3 = new JButton (" Statistici ");
		panel.add( btn3 );
		btn3.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	if( Gestiune.fFacturi != null && Gestiune.fProduse != null && Gestiune.fTaxe != null && Gestiune.filesLoaded == true )
		    		new FereastraStatistici("Statistici").setVisible(true);
		    	else
		    		JOptionPane.showMessageDialog(null, "Fisierele nu sunt pregatite.");
		    }
		});
	}
}

