
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Generator {

	private JFrame frame;
	private JTextField textField;
	private File file;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Generator window = new Generator();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Generator() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Query Processing Engine");
		
		
		JButton btnGenerator = new JButton("Generate Code");
		btnGenerator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Project pj = new Project();
				if(file == null){
					JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
					return;
				}
				pj.begin(file);
			}
		});
		
		btnGenerator.setBounds(58, 158, 138, 29);
		frame.getContentPane().add(btnGenerator);
		
		JButton btnChooseFile = new JButton("Choose File");
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
			    fc.setFileFilter(filter);
				
				fc.setCurrentDirectory(new java.io.File("."));
				
				if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
					file = fc.getSelectedFile();
				}
				
				if(file != null){
					textField.setText(file.getName());
				}
				
				
			}
		});
		btnChooseFile.setBounds(226, 80, 112, 23);
		frame.getContentPane().add(btnChooseFile);
		
		textField = new JTextField();
		textField.setBounds(58, 81, 158, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnDeleteFile = new JButton("Delete File");
		btnDeleteFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BufferedWriter out = new BufferedWriter(new FileWriter("EMF_QUERY.java"));
			         out.write("aString1\n");
			         out.close();
			         boolean success = (new File ("EMF_QUERY.java")).delete();
			         if (success) {
			            System.out.println("The file has been successfully deleted"); 
			         }
				}catch (IOException e1) {
		            System.out.println("exception occoured"+ e1);
		            System.out.println("File does not exist or you are trying to read a file that has been deleted");
		        }         
		    }
		});
		btnDeleteFile.setBounds(241, 160, 117, 27);
		frame.getContentPane().add(btnDeleteFile);
	}
}
