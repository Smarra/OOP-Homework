package javaSwing;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import tranzactionSystem.Factura;
import tranzactionSystem.Gestiune;
import tranzactionSystem.Magazin;
import tranzactionSystem.Produs;
import tranzactionSystem.ProdusComandat;

public class FereastraStatistici extends JFrame{	
	public FereastraStatistici(String titlu){
		super( titlu );
		setResizable(true);
		setSize(420, 745);
		
		setVisible(true);
		TitledBorder title ;
		title = BorderFactory.createTitledBorder(" Adauga un produs ");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setPreferredSize (new Dimension (400 ,300) );
		panel.setBackground ( Color.lightGray );
		panel.setBorder ( title );
		
		//Afisam pe rand fiecare statistica ceruta de enunt
		//1. Magazinul cu cele mai mari vanzari
		JLabel jl1 = new JLabel(" 1. Magazinul cu cele mai mari vanzari este: ");
		JLabel jl2 = null;
		Double bestPrice = new Double(-1);
		for( Magazin magazin : Gestiune.getInstance().magazine )
			if( bestPrice < magazin.getTotalCuTaxe() )
				bestPrice = magazin.getTotalCuTaxe();
		for( Magazin magazin : Gestiune.getInstance().magazine )
			if( bestPrice == magazin.getTotalCuTaxe() )
			{
				jl2 = new JLabel("<html>" + "	" + magazin.nume + "<br/> " + 
						"Total factura fara taxe: " + magazin.getTotalFaraTaxe() + "<br/> " + 
						"Total factura cu taxe: " + magazin.getTotalCuTaxe() + "<br/> " +
						"Total magazin cu taxe scutite: " + magazin.getTotalCuTaxeScutite() + "</html>");
				break;
			}
		
		panel.add(jl1);
		panel.add(jl2);
		
		//2. Magazinul cu cele mai mari vanzari pentru fiecare tara
		JLabel jl3 = new JLabel(" 2. Magazinul cu cele mai mari vanzari pentru fiecare tara este: ");
		panel.add(jl3);
		
		int i = 0;
		for( String tara : Gestiune.getInstance().tari )
		{
			JLabel jl = null;
			Double maxim = -1.0;
			for( Magazin magazin : Gestiune.getInstance().magazine )
				if( maxim < magazin.getTotalTaraCuTaxe(tara) )
					maxim = magazin.getTotalTaraCuTaxe(tara);
			for( Magazin magazin : Gestiune.getInstance().magazine )
				if( maxim == magazin.getTotalTaraCuTaxe(tara) )
				{
					jl = new JLabel("<html>" +
							tara  + ":" + "<br/> " + 
							magazin.nume + "<br/> " + 
							"Total factura fara taxe: " + magazin.getTotalTaraFaraTaxe(tara) + "<br/> " + 
							"Total factura cu taxe: " + magazin.getTotalTaraCuTaxe(tara) + "<br/> " +
							"Total magazin cu taxe scutite: " + magazin.getTotalTaraCuTaxeScutite(tara) + 
							"</html>");
					break;
				}
			panel.add(jl);
		}
		
		//3. Magazinul cu cele mai mari vanzari pentru fiecare categorie
		JLabel jl4 = new JLabel(" 3. Magazinul cu cele mai mari vanzari pentru fiecare categorie este: ");
		panel.add(jl4);
		
		//Gasim lista categoriilor
		ArrayList<String> categorii = new ArrayList<>();
		for( Produs produs : Gestiune.getInstance().produse )
			{
				String categorie = produs.getCategorie();
				if( !categorii.contains(categorie) )
				{
					categorii.add(categorie);
				}
			}
		for( String categorie : categorii )
		{
			JLabel jl = null;
			
			Double maxim = 0.0;
			for( Magazin magazin : Gestiune.getInstance().magazine )
				if( maxim < magazin.getTotalCategorieCuTaxe(categorie) )
					maxim = magazin.getTotalCategorieCuTaxe(categorie);
			for( Magazin magazin : Gestiune.getInstance().magazine )
				if( maxim == magazin.getTotalCategorieCuTaxe(categorie) )
				{
					jl = new JLabel("<html>" +
							categorie + ":" + "<br/> " + 
							magazin.nume + "<br/> " + 
							"Total factura fara taxe: " + magazin.getTotalCategorieFaraTaxe(categorie) + "<br/> " + 
							"Total factura cu taxe: " + magazin.getTotalCategorieCuTaxe(categorie) + "<br/> " +
							"Total magazin cu taxe scutite: " + magazin.getTotalCategorieCuTaxeScutite(categorie) + 
							"</html>");
					break;
				}
			panel.add(jl);
		}
		
		//3. Magazinul cu cele mai mari vanzari pentru fiecare categorie
		JLabel jl5 = new JLabel(" 4. Factura cu suma totala (fara taxe) cea mai mare este: ");
		panel.add(jl5);
		JLabel jl = null;
		
		Double maxim = -1.0;
		for( Magazin magazin : Gestiune.getInstance().magazine )
		{
			for( Factura factura : magazin.lista )
			{
				System.out.println(factura);
				if( maxim < factura.getTotalFaraTaxe() )
					maxim = factura.getTotalFaraTaxe();
			}
			for( Factura factura : magazin.lista )
				if( maxim == factura.getTotalFaraTaxe() )
				{
					jl = new JLabel("<html>" + "	" + factura.denumire + "<br/> " + 
									"Total factura fara taxe: " + factura.getTotalFaraTaxe() + "<br/> " + 
									"Total factura cu taxe: " + factura.getTotalCuTaxe() + "</html>");
					break;
				}	
		}
		panel.add(jl);
		
		// Adauga panel-ul la layout
		add(panel);
	}
}
