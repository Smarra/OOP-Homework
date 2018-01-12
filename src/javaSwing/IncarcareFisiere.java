package javaSwing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;

import tranzactionSystem.Factura;
import tranzactionSystem.Gestiune;
import tranzactionSystem.Magazin;
import tranzactionSystem.ProductFactory;
import tranzactionSystem.Produs;
import tranzactionSystem.ProdusComandat;

public class IncarcareFisiere extends JFrame{
	private JFrame frame;
	
	public IncarcareFisiere(String titlu){
		super( titlu );
		setResizable(false);
		setSize(320, 445);
		frame = this;
		//setLayout (new FlowLayout ());
		//setDefaultCloseOperation ( JFrame . EXIT_ON_CLOSE );
		
		//Bordura
		TitledBorder title ;
		title = BorderFactory.createTitledBorder(" Incarcare fisiere ");
		
		final JPanel panel = new JPanel();
		panel.setPreferredSize (new Dimension (300 ,400) );
		panel.setBackground ( Color.lightGray );
		panel.setBorder ( title );
		add( panel );
		
		//Gestiune
		Gestiune gestiune = Gestiune.getInstance();
		
		//Butoane de incarcare pentru fiecare fisier
		JButton btn1 = new JButton (" Incarca produse ");
		btn1.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int returnValue = jfc.showOpenDialog(null);
				// int returnValue = jfc.showSaveDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					Gestiune.fProduse = new File(selectedFile.getAbsolutePath());
				}
		    }
		});
		panel.add( btn1 );
		JButton btn2 = new JButton (" Incarca taxe ");
		btn2.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

				int returnValue = jfc.showOpenDialog(null);
				// int returnValue = jfc.showSaveDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					Gestiune.fTaxe = new File(selectedFile.getAbsolutePath());
				}
		    }
		});
		panel.add( btn2 );
		JButton btn3 = new JButton (" Incarca facturi ");
		btn3.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

				int returnValue = jfc.showOpenDialog(null);
				// int returnValue = jfc.showSaveDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					Gestiune.fFacturi = new File(selectedFile.getAbsolutePath());
				}
		    }
		});
		panel.add( btn3 );
		
		JButton btn4 = new JButton (" Apasa ");
		btn4.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
				if( Gestiune.fFacturi != null && Gestiune.fProduse != null && Gestiune.fTaxe != null )
				{
					loadFiles();
					frame.dispose();
				}
				else
				{
					String s = new String("");
					if( Gestiune.fProduse == null )
						s = s + "Produse.txt ";
					if( Gestiune.fTaxe == null )
						s = s + "Taxe.txt ";
					if( Gestiune.fFacturi == null )
						s = s + "Facturi.txt ";

					JOptionPane.showMessageDialog(null, "Load all the files. " + s + " remaining.");
				}
		    }
		});
		panel.add(btn4);
	}
	
	static void loadFiles(){
		File file;
		Scanner input;	
		Gestiune gestiune = Gestiune.getInstance();
		
		try{		
			//Folosim utilitarul Scanner pentru parcurgerea fisierului
			input = new Scanner(gestiune.fProduse);
			
			//Obtin lista tarilor din prima linie din fisier
			if( input.hasNextLine() )
			{
				StringTokenizer line = new StringTokenizer(input.nextLine());
				line.nextToken();
				line.nextToken();
				while( line.hasMoreElements() )
				{
					String tara = new String( line.nextToken() );
					gestiune.tari.add(tara);
				}
			}
			
			//Obtin pe rand fiecare produs ( vor fi create n*m produs, unde n = nr. produselor,
			//iar m = nr. tarilor de origine )
			while( input.hasNextLine() )
			{
				StringTokenizer line = new StringTokenizer(input.nextLine());
				String denumire = new String( line.nextToken() );
				String categorie = new String( line.nextToken() );
				
				int i = 0;
				while( line.hasMoreTokens() )
				{
					Double pret = Double.parseDouble( line.nextToken() );
					Produs prod = new Produs(denumire, categorie, gestiune.tari.get(i++), pret);
					gestiune.produse.add( prod );
				}
			}
			input.close();
			
			//parcurgem lista de taxe si actualizam TreeMap-ul de taxe
			input = new Scanner(gestiune.fTaxe);
			input.nextLine(); //sarim peste antet deoarece avem deja lista tarilor
			while( input.hasNextLine() )
			{
				String line = input.nextLine();
				StringTokenizer items = new StringTokenizer(line);
				String categorie = items.nextToken();
				
				int i = 0;
				while( items.hasMoreTokens() )
				{
					Double procent = Double.parseDouble( items.nextToken() );
					String tara = gestiune.tari.get(i++);
					if( gestiune.taxe.containsKey(tara) )
						gestiune.taxe.get(tara).put(categorie, procent);
					else
					{
						TreeMap<String, Double> map = new TreeMap<String, Double>();
						map.put(categorie, procent);
						gestiune.taxe.put(tara, map);
					}
				}
			}			
			input.close();
			
			//parcurgem lista de facturi
			ProductFactory productFactory = new ProductFactory();
			input = new Scanner(gestiune.fFacturi);
			
			while( input.hasNextLine() ){
				String line = input.nextLine();
				
				//Daca este magazin, il cream folosind ProductFactory si il adaugam in lista
				//de magazine
				if( line.startsWith("Magazin:") )
				{
					StringTokenizer mag = new StringTokenizer(line, ":");
					mag.nextToken();
					Magazin newMagazin = productFactory.createMagazin(mag.nextToken());
					newMagazin.nume = mag.nextToken();
					
					gestiune.magazine.add(newMagazin);
				}
				
				if( line.startsWith("Factura") )
				{
					Factura factura = new Factura();
					factura.denumire = line;
					input.nextLine();
					
					while(input.hasNextLine())
					{
						line = input.nextLine();
						if( line.compareTo("") == 0 )
							break;
						StringTokenizer items = new StringTokenizer(line);
						String denumire = items.nextToken();
						String tara = items.nextToken();
						int cantitate = Integer.parseInt(items.nextToken());
						for( Produs prod : gestiune.produse )
							if( prod.getDenumire().equals(denumire) && prod.getTaraOrigine().equals(tara))
							{
								Produs produs = new Produs(prod);
								double taxa = gestiune.taxe.get(tara).get(produs.getCategorie());
								ProdusComandat prodCom = new ProdusComandat(produs, taxa, cantitate);
								factura.lista.addElement(prodCom);
								break;
							}
						}
					gestiune.magazine.get(gestiune.magazine.size()-1).lista.addElement(factura);
					
				}
			}
			//System.out.println(gestiune.produse);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	
		//trecem la creearea fisierului out.txt
		try{
			file = new File("out");
			PrintWriter writer = new PrintWriter(file, "UTF-8");
	
			//Sortam magazinele dupa costul total ale acestora
			Collections.sort(gestiune.magazine);
			ArrayList<String> tipuri = new ArrayList<>();
			tipuri.add("MiniMarket");
			tipuri.add("MediumMarket");
			tipuri.add("HyperMarket");
			
			ArrayList<String> newTariSortate = new ArrayList<>( gestiune.tari );
			Collections.sort(newTariSortate);
			
			for( String tip : tipuri )
			{
				writer.println(tip);
				for( Magazin magazin : gestiune.magazine )
				{
					if( magazin.tip == tip )
					{
						writer.println(magazin.nume + "\n");
						writer.println("Total " + magazin.getTotalFaraTaxe() + " " + magazin.getTotalCuTaxe() + " " + 
										magazin.getTotalCuTaxeScutite() + "\n");
						writer.println("Tara");
						for( String tara : newTariSortate )
							writer.println(tara + " " + magazin.getTotalTaraFaraTaxe(tara) + " " +
										magazin.getTotalTaraCuTaxe(tara) + " " + magazin.getTotalTaraCuTaxeScutite(tara));
						writer.println();
						Collections.sort( magazin.lista );
						for( Factura fact : magazin.lista )
						{
							writer.println( fact.denumire + "\n");
							writer.println( "Total " + fact.getTotalFaraTaxe() + " " + fact.getTotalCuTaxe() + "\n");
							writer.println("Tara");
							for( String tara : newTariSortate )
								writer.println(tara + " " + fact.getTotalTaraFaraTaxe(tara) + " " + fact.getTotalTaraCuTaxe(tara));
							writer.println();
						}
					}
				}
			}
			
			writer.close();
			
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
}
