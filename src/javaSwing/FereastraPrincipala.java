package javaSwing;

import javax.swing.*;
import javax.swing.border.*;

import tranzactionSystem.Gestiune;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FereastraPrincipala extends JFrame {
	public FereastraPrincipala( String titlu ){
		super( titlu );
		setResizable(false);
		setSize(320, 445);
		//setLayout (new FlowLayout ());
		setDefaultCloseOperation ( JFrame . EXIT_ON_CLOSE );
		//FereastraInterna f1 = new FereastraInterna();
		//JDesktopPane desktop = new JDesktopPane();
		//desktop.add(f1);
		//setContentPane( desktop );
		
		//Bordura
		TitledBorder title ;
		title = BorderFactory.createTitledBorder(" Pagina Principala ");
		final JPanel panel = new JPanel();
		panel.setPreferredSize (new Dimension (300 ,400) );
		panel.setBackground ( Color.lightGray );
		panel.setBorder ( title );
		add( panel );
		//Adaugam butoanele panoului principal
		JButton btn1 = new JButton (" Pagina de incarcare ");
		btn1.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        new IncarcareFisiere("Incarcare Fisiere").setVisible(true);
		    }
		});
		panel.add( btn1 );
		JButton btn2 = new JButton (" Afisare si administrare produse ");
		btn2.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	if( Gestiune.fFacturi != null && Gestiune.fProduse != null && Gestiune.fTaxe != null )
		    		new AdministrareProduse("Afisare si Administrare Produse").setVisible(true);
		    	else
		    		JOptionPane.showMessageDialog(null, "Fisierele nu sunt pregatite.");
		    }
		});
		panel.add( btn2 );
		JButton btn3 = new JButton (" Statistici ");
		btn3.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	if( Gestiune.fFacturi != null && Gestiune.fProduse != null && Gestiune.fTaxe != null )
		    		new FereastraStatistici("Statistici").setVisible(true);
		    	else
		    		JOptionPane.showMessageDialog(null, "Fisierele nu sunt pregatite.");
		    }
		});
		panel.add( btn3 );
	}
}

