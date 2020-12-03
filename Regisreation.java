package chat;



import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Regisreation extends JFrame implements ActionListener { 

// Components of the Form 
private Container c; 
private JLabel title; 
private JLabel name; 
private JTextField tname; 
private JLabel mno; 
private JTextField tmno; 
private JButton sub; 
private JButton reset; 
private JTextArea tout; 
private JLabel res; 
private JTextArea resadd; 
private JFrame frame;

HashMap<String, Integer> map = new HashMap<>();

private int port = 8818;




// constructor, to initialize the components 
// with default values. 
public Regisreation() 
{ 

    setTitle("Registration Form"); 
    setBounds(300, 90, 900, 600); 
    setDefaultCloseOperation(EXIT_ON_CLOSE); 
    setResizable(false); 

    c = getContentPane(); 
    c.setLayout(null); 

    title = new JLabel("Registration Form"); 
    title.setFont(new Font("Arial", Font.PLAIN, 30)); 
    title.setSize(300, 30); 
    title.setLocation(300, 30); 
    c.add(title); 

    name = new JLabel("Name"); 
    name.setFont(new Font("Arial", Font.PLAIN, 20)); 
    name.setSize(100, 20); 
    name.setLocation(100, 100); 
    c.add(name); 

    tname = new JTextField(); 
    tname.setFont(new Font("Arial", Font.PLAIN, 15)); 
    tname.setSize(190, 20); 
    tname.setLocation(200, 100); 
    c.add(tname); 

    mno = new JLabel("Password"); 
    mno.setFont(new Font("Arial", Font.PLAIN, 20)); 
    mno.setSize(100, 20); 
    mno.setLocation(100, 150); 
    c.add(mno); 

    tmno = new JTextField(); 
    tmno.setFont(new Font("Arial", Font.PLAIN, 15)); 
    tmno.setSize(150, 20); 
    tmno.setLocation(200, 150); 
    c.add(tmno); 

    sub = new JButton("Submit"); 
    sub.setFont(new Font("Arial", Font.PLAIN, 15)); 
    sub.setSize(100, 20); 
    sub.setLocation(150, 450); 
    sub.addActionListener(this); 
    c.add(sub);

    tout = new JTextArea(); 
    tout.setFont(new Font("Arial", Font.PLAIN, 15)); 
    tout.setSize(300, 400); 
    tout.setLocation(500, 100); 
    tout.setLineWrap(true); 
    tout.setEditable(false); 
    c.add(tout); 

    res = new JLabel(""); 
    res.setFont(new Font("Arial", Font.PLAIN, 20)); 
    res.setSize(500, 25); 
    res.setLocation(100, 500); 
    c.add(res); 

    resadd = new JTextArea(); 
    resadd.setFont(new Font("Arial", Font.PLAIN, 15)); 
    resadd.setSize(200, 75); 
    resadd.setLocation(580, 175); 
    resadd.setLineWrap(true); 
    c.add(resadd); 

    setVisible(true); 
} 

// method actionPerformed() 
// to get the action performed 
// by the user and act accordingly 
/*public void actionPerformed(ActionEvent e) 
{ 
    if (e.getSource() == sub) { 
       
            String name=tname.getText();
            String password=mno.getText();
            boolean flag = map.containsValue(name);
            if(flag==true) {
            
            res.setText("Registration failed choose another name.."); 
            }else {
            	int inum = Integer.parseInt(password);

            	map.put(name, inum);
            	res.setText("Registration success.."); 
            }
        } 
      
    } 
    */


/*public void actionPerformed(ActionEvent e) {
	if (e.getSource() == sub) { 
	       
        String name=tname.getText();
        String password=mno.getText();
        
            //FileWriter writer=new FileWriter(file, true);//true 3lshan my3mlsh overwrite yzod 3la s\el file
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
            String row = reader.readLine();
            while(row!=null){ 
                String[] fields=row.split(" ");
                String currentUsername=fields[0];
                if(name.equals(currentUsername)) {
                   return false;
                }
                row = reader.readLine();
            }
            reader.close();
            PrintWriter writer = new PrintWriter(new FileWriter("users.txt", true));
            String newRow = name;
            newRow = newRow.concat(" ");
            newRow = newRow.concat(password);
            writer.println(newRow);
            writer.close();
            return true;
            
	}
}
*/
public void actionPerformed(ActionEvent e) {
	try {
		String password=mno.getText();
		String id = tname.getText(); // username entered by user
		Socket s = new Socket("localhost", port); // create a socket
		DataInputStream inputStream = new DataInputStream(s.getInputStream()); // create input and output stream
		DataOutputStream outStream = new DataOutputStream(s.getOutputStream());
		outStream.writeUTF(id); // send username to the output stream	
		
		boolean flag = map.containsValue(id);
        if(flag==true) {
        
      //  res.setText("Registration failed choose another name.."); 
        String msgFromServer = new DataInputStream(s.getInputStream()).readUTF(); // receive message on socket
         if(msgFromServer.equals("Username already taken")) {//if server sent this message then prompt user to enter other username
			JOptionPane.showMessageDialog(frame,  "Username ... already taken\n"); 
			System.exit(0); // stop program
			frame.dispose(); // close window
			frame.setVisible(true); // hide windo
			
        }
        else {
        	int inum = Integer.parseInt(password);

        	map.put(id, inum);
        	res.setText("Registration success.."); 
        }
		
	
        }else {
			new ClientView(id, s); // otherwise just create a new thread of Client view and close the register jframe
			frame.dispose();
		}
	}catch(Exception ex) {
		ex.printStackTrace();
	}
}

 


public static void main(String[] args) { // main function which will make UI visible
	
	
	Regisreation f = new Regisreation(); 
}
}

