import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;


public class FrontPageGUI extends JFrame implements ActionListener{

    //buttons
    JButton Start;
    JButton Exit;
    JButton About;

    FrontPageGUI(){
        
        //everything below is creating, editing, and placing the buttons.
        this.setTitle("Sobel's Edge Detection");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,600);
        this.setLayout(null);

        Start = new JButton("Start");
        Exit = new JButton("Exit");
        About = new JButton("More Info");

        Start.setBounds(100, 345, 200,50);
        About.setBounds(100,410,200,50);
        Exit.setBounds(100,475,200,50);

        Start.addActionListener(this);
        Exit.addActionListener(this);
        About.addActionListener(this);

        this.getContentPane().add(Start);
        this.getContentPane().add(Exit);
        this.getContentPane().add(About);
        
        Start.setBorder( new LineBorder(Color.BLACK) );
        Exit.setBorder( new LineBorder(Color.BLACK) );
        About.setBorder( new LineBorder(Color.BLACK) );

        Start.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        Exit.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        About.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        
        //Everything below is creating, editing, and placing the Labels
        JLabel title = new JLabel("Sobel's Edge Detector !");
       
        title.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
        title.setForeground(new Color(0,0,0));
        title.setBounds(35,-40,350,200);


        JLabel andothers = new JLabel("and others !...");
       
        andothers.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        andothers.setForeground(new Color(0,0,0));
        andothers.setBounds(280,-10,350,200); 
        
        //citation for the sobeloperator.jpeg image: 
        //Han, Leng, et al. “Research on Adaptive Orb-Surf Image Matching Algorithm Based on Fusion of Edge Features | IEEE Journals & Magazine | IEEE Xplore.” Https://Ieeexplore.Ieee.Org/Document/9911645, ieeexplore.ieee.org/document/9911645. Accessed 7 May 2024. 
        ImageIcon image = new ImageIcon("sobeloperator.jpeg");
        Image scaledimage = image.getImage().getScaledInstance(310, 210, Image.SCALE_SMOOTH);
        ImageIcon finalimage = new ImageIcon(scaledimage);
        JLabel pic = new JLabel(finalimage);
        
        pic.setBounds(40,125,310,210);
        
        this.getContentPane().add(pic);
        this.getContentPane().add(title);
        this.getContentPane().add(andothers);

        this.getContentPane().setBackground(new Color(255,255,255));
        this.setVisible(true);

        //we don't want users resizing the JPanels.
        this.setResizable(false);
    }

    @Override
	
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == Start){
            
            //close this JPanel
            setVisible(false);            
            dispose();
            
            //create new FileSelectionGUi
            new FileSelectionGUI();

            

        } else if (e.getSource() == Exit){
           
            //close this JPanel
            setVisible(false);            
            dispose();


        } else if (e.getSource() == About){

            //close this JPanel
            setVisible(false);            
            dispose();

            //create new InfoGUI
            new InfoGUI();
            

        }
    }
}