package tranzactionSystem;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import javaSwing.*;

public class MainActivity {
	public static void main(String args[]){	
	
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		new FereastraLogin("Login").setVisible(true);		
	}
}
