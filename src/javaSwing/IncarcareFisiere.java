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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
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
	JProgressBar progressBar = null;
	boolean ok1 = false, ok2 = false, ok3 = false;
	BufferedImage image;
	
	public IncarcareFisiere(String titlu){
		super( titlu );
		setResizable(false);
		setSize(320, 445);
		frame = this;
		
		//Creeaza background
		try{
		image = ImageIO.read(new File(Gestiune.backgroundFilePath));
		}catch( IOException e){
			e.printStackTrace();
		}
		
		//Panel
		final JPanel panel = new JPanel( new GridLayout(10, 1)){
            @Override
			public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
		};
		panel.setPreferredSize (new Dimension (100 ,100) );
		panel.setBackground ( Color.lightGray );
		
		//Centreaza fereastra principala
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - getHeight()) / 2);
	    setLocation(x, y);
		
		//Bordura
		TitledBorder title ;
		title = BorderFactory.createTitledBorder(" Incarcare fisiere ");
		panel.setBorder ( title );
		add( panel );
		
		//ProgressBar
		progressBar = new JProgressBar();
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		
		//Butoane de incarcare pentru fiecare fisier
		JButton btn1 = new JButton (" Incarca produse ");
		panel.add( btn1 );
		btn1.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int returnValue = jfc.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					Gestiune.fProduse = new File(selectedFile.getAbsolutePath());
					if( ok1 == false )
					{
						progressBar.setValue( progressBar.getValue() + 33 );
						if( progressBar.getValue() == 99 )
							progressBar.setValue( 100 );
						ok1 = true;
					}
				}
		    }
		});
		
		JButton btn2 = new JButton (" Incarca taxe ");
		panel.add( btn2 );
		btn2.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

				int returnValue = jfc.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					Gestiune.fTaxe = new File(selectedFile.getAbsolutePath());
					if( ok2 == false )
					{
						progressBar.setValue( progressBar.getValue() + 33 );
						if( progressBar.getValue() == 99 )
							progressBar.setValue( 100 );
						ok2 = true;
					}
				}
		    }
		});
		
		JButton btn3 = new JButton (" Incarca facturi ");
		panel.add( btn3 );
		btn3.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int returnValue = jfc.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					Gestiune.fFacturi = new File(selectedFile.getAbsolutePath());
					if( ok3 == false )
					{
						progressBar.setValue( progressBar.getValue() + 33 );
						if( progressBar.getValue() == 99 )
							progressBar.setValue( 100 );
						ok3 = true;
					}
				}
		    }
		});
		
		
		JButton btn4 = new JButton (" Actualizeaza baza de date ");
		panel.add(btn4);
		btn4.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
				if( Gestiune.fFacturi != null && Gestiune.fProduse != null && Gestiune.fTaxe != null )
				{
					loadFiles();
					Gestiune.filesLoaded = true;
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
		
		
		//Creare dummy pentru spatiere
		JLabel dummy = new JLabel("");
		panel.add(dummy);
		
		//Adaugare ProgressBar la panel
		panel.add(progressBar);
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
