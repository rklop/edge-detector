import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Desktop;
import java.net.URL;

public class InfoGUI extends JFrame implements ActionListener{

    //buttons
    JButton Back;
    JButton MoreInfo;

    InfoGUI(){
        
        //everything below is creating, editing, and placing the buttons.
        this.setTitle("Sobel's Edge Detection");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,600);
        this.setLayout(null);

        Back = new JButton("Back");
        MoreInfo = new JButton("Even More Info!");

        Back.setBounds(100, 505, 200,50);
        MoreInfo.setBounds(100,440,200,50);

        Back.addActionListener(this);
        MoreInfo.addActionListener(this);

        this.getContentPane().add(Back);
        this.getContentPane().add(MoreInfo);
        
        Back.setBorder( new LineBorder(Color.BLACK) );
        MoreInfo.setBorder( new LineBorder(Color.BLACK) );

        Back.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        MoreInfo.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        
        //everything below is creating, editing, and placing the Labels
        JLabel title = new JLabel("What is Sobel's Edge Detection ?");
    
        title.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        title.setForeground(new Color(0,0,0));
        title.setBounds(20,-180,400,400);

        JLabel secondtitle = new JLabel("And How Does It Work ?");
        
        secondtitle.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        secondtitle.setForeground(new Color(0,0,0));
        secondtitle.setBounds(20,-155,400,400);

        //we had to create a JTextArea so that the long explanation of our Sobel operator can wrap, and fit into the JPanel.
        JTextArea info = new JTextArea("Hi! We are attempting to implement our own Sobel operator, which, in function, will detect edges. We convolve a 3x3 kernel, or filter, with the image to calculate the gradients across x and y. Convolution describes a mathematical operation that adds each element of an image to its neighbor, multiplied by a corresponding kernel value. The Sobel Operator implements kernels of x and y gradients, which aim to detect shifts in pixel values in the x direction and y direction. So, our convolution operation finds the gradient or change in x/y by weighing the kernel values onto pixels against its neighbors, subtracting the left pixels from the right pixels (changes in x). The same process works from top to bottom (changes in y). We then calculate the magnitude associated with this value and the y version, which does the same process, but top to down (to get rid of any negative values). The algorithm then compares the magnitude to some threshold, which we have adjusted to be randomized to provide a clearer visual of how it affects the 'sensitivity' of our edge detection. ");

        info.setBounds(20,60, 370, 360); 
       
        //here, we make it so that it's wrap-able.
        info.setLineWrap(true);
        info.setWrapStyleWord(true); 
        info.setEditable(false); 
        
        info.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
        info.setCaretColor(info.getBackground());


        this.getContentPane().add(info);
        this.getContentPane().add(title);
        this.getContentPane().add(info);
        this.getContentPane().add(secondtitle);

        this.getContentPane().setBackground(new Color(255,255,255));
        this.setVisible(true);
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == Back){
            
            //close this JPanel
            setVisible(false);            
            dispose();

            //create new FrontPageGUI
            new FrontPageGUI();


        } else if (e.getSource() == MoreInfo){

            //this opens the wikipedia page on Sobel Operator's, for more information
            //I used a StackOverFlow website to learn how to do this, link below.
            //https://stackoverflow.com/questions/10967451/open-a-link-in-browser-with-java-button

            try {
                Desktop.getDesktop().browse(new URL("https://en.wikipedia.org/wiki/Sobel_operator").toURI());
            } catch (Exception x) {}
        } 
    }

}