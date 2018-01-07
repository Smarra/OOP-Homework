package javaSwing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class FereastraLogin extends JFrame{
	
	JFrame frame;
	JPasswordField pass;
	JTextField user;
	File file = new File("login");
	
	public FereastraLogin( String titlu ){
		super( titlu );
		setResizable(false);
		setSize(420, 145);
		frame = this;
		
		TitledBorder title ;
		title = BorderFactory.createTitledBorder(" Login ");
		JPanel panel = new JPanel(new GridLayout(4, 1));
		panel.setPreferredSize (new Dimension (400 ,100) );
		panel.setBackground ( Color.lightGray );
		panel.setBorder ( title );
		
		JLabel lb1 = new JLabel("Username: ");
		user = new JTextField(15);
		JLabel lb2 = new JLabel("Password: ");
		pass = new JPasswordField(15);
		
		JButton login = new JButton("Login");
		JButton create = new JButton("Create account");
		
		panel.add(lb1);
		panel.add(user);
		panel.add(lb2);
		panel.add(pass);
		panel.add(login);
		panel.add(create);
		
		if( !file.exists() )
		{
			try{
				file.createNewFile();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		login.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {				
				if( pass.getPassword().length == 0 || user.getText().length() == 0 )
					JOptionPane.showMessageDialog(null, "Invalid Login");
				else
					if( checkLogin(pass.getPassword(), user.getText(), file) )
					{
						new FereastraPrincipala("Main Menu").setVisible(true);
						frame.dispose();
					}
					else
						JOptionPane.showMessageDialog(null, "Wrong account.");
		    }
		});
		
		create.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
				//Verificarea existentei fisierului cu datele de intrare si verificare acestora
				FileWriter writer;
				try{
					writer = new FileWriter(file, true);			
					if( pass.getPassword().length == 0 || user.getText().length() == 0 )
						JOptionPane.showMessageDialog(null, "Invalid Account");
					else
						if( !checkLogin(pass.getPassword(), user.getText(), file) )
						{
							System.out.println("created");
							writer.append(user.getText() + System.lineSeparator());
							writer.append(new String(pass.getPassword()) + System.lineSeparator());
						}
						else
							JOptionPane.showMessageDialog(null, "Invalid Account. It is already in the database.");
					writer.close();
				}catch( Exception ex){
					ex.printStackTrace();
				}
		    }
		});
		
		add(panel, BorderLayout.CENTER );

	}
	
	private boolean checkLogin( char[] password, String username, File file){
		try{
			Scanner scanner = new Scanner( file );
			String parola = new String(password);
			while( scanner.hasNextLine() )
			{
				String utilizator = scanner.nextLine();
				String par = scanner.nextLine();
				if( utilizator.equals(username) && parola.equals(par) )
					return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
}
