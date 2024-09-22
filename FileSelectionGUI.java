import java.io.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.imageio.ImageIO;
import java.nio.file.*;
import java.util.Random;
import java.awt.Font;


public class FileSelectionGUI extends JFrame implements ActionListener, image_editing_process{

    //Buttons
    JButton fileUploader;
    JButton startButton;
    JButton deleteButton;
    JButton randomcolor;
    JButton stars;
    JButton greyscale;

    //Filter
    FileNameExtensionFilter filter;
   
    //position variables
    int file_x = 35;
    int file_y = 75;
 
    int filecounter = 1;

    int deletebutton_x = 50;
    int deletebutton_y = 50;

    String a = "255";
    String b = "255";
    String c = "255";

    boolean limit = false;

    String [] files;
    int counter = 0;
    
    @Override
    public void duplicate(){
            //this first for-loop creates a new [EDITED].bmp, based on the title of the original.bmp
            //It goes by i = i + 2, because now we're placing in the [EDITED].bmp's in between the original.bmp's
            //Again, the format of the String [] files is: original, [EDITED].bmp, original, [EDITED].bmp, ...

            for (int i = 0; i < files.length; i = i + 2) {
                
                //if it's null, then stop.
                if (files[i] == null) {
                    break;
                }
                
                //this creates a new sub-string with [EDITED].
                String s = files[i];
                int placeholder = s.lastIndexOf(".bmp");
                s = s.substring(0, placeholder) + "[EDITED]" + s.substring(placeholder);
                
                //this creates a new file based on the new [EDITED] name.
                File finalfile = new File(s);
                File tempfile = new File(files[i]);
                
                //here, we create the new [EDITED].bmp, and we copy the original.bmp information over... this will be changed later. 
                try {
                    BufferedImage OG = ImageIO.read(tempfile);
                    ImageIO.write(OG, "bmp", finalfile);
                    files[i+1] = finalfile.getName();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                //here, we close the new [EDITED].bmp
                try {
                    FileInputStream fileInputStream = new FileInputStream(finalfile);
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
    }
    public void reset(){
        //This just removes all JLabels and the 'Convert' button, and then redraws only the 'Select Files' button.
        this.getContentPane().removeAll();
        this.getContentPane().add(fileUploader);
        this.getContentPane().repaint();
        this.getContentPane().revalidate();

        //reset all of the variables and String [] files to use again. 
        file_y = 75;
        file_x = 35;
        filecounter = 1;
        counter = 0;
        String [] nulls;
        nulls = new String[20];
        files = nulls;
        a = "255";
        b = "255";
        c = "255";

        //reset the randomcolor button to white
        randomcolor.setBackground(new Color(255,255,255));
        randomcolor.setOpaque(true);



    }

    FileSelectionGUI(){

        this.setTitle("Sobel's Edge Detection: Select Files");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,550);
        this.setLayout(null);

        //initalize the varius buttons, and the file filter.
        fileUploader = new JButton("Select File:");
        startButton = new JButton("Edge Detection");
        randomcolor = new JButton("Random Edge Color!");
        stars = new JButton("Stars");
        greyscale = new JButton("Greyscale");
        filter = new FileNameExtensionFilter("bmp Files", "bmp");

        //set bounds of buttons
        fileUploader.setBounds(150, 10, 200,50);
        startButton.setBounds(15,410,225,50);
        randomcolor.setBounds(260,410,225,50);
        stars.setBounds(15,460,225,50);
        greyscale.setBounds(260,460,225,50);

        //add the buttons
        fileUploader.addActionListener(this);
        startButton.addActionListener(this);
        randomcolor.addActionListener(this);
        stars.addActionListener(this);
        greyscale.addActionListener(this);
        
        this.getContentPane().add(fileUploader);
        
        //comic sans!
        this.getContentPane().setBackground(new Color(255, 255, 255));
        fileUploader.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        startButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        randomcolor.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        stars.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        greyscale.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        this.setVisible(true);

        //this prevents the user from resizing the JFrame
        this.setResizable(false);

        //create array string files, length 20. 10 for the files, 10 for the [EDITED] files.
        files = new String[20];
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == fileUploader){
           
            if (!limit) {

            //create a new file filter
            JFileChooser fileSelector = new JFileChooser();

            fileSelector.setCurrentDirectory(new File(System.getProperty("user.home")));

            fileSelector.setFileFilter(filter);
            int response = fileSelector.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                
                File file1 = new File(fileSelector.getSelectedFile().getAbsolutePath());
                
                //save the name of the file in String [] files
                files[counter] = file1.getName();
                
                //because String [] files stores files in the order of: original, [EDITED], original, [EDITED]... We have to leave space for
                //the [EDITED] files, therefore, we increase the counter by 2, rather than one.
                counter = counter + 2;

                
                //change the BMP to a .jpeg for smoother images, and to get a ScaledInstance.
                //create a temporary file named temp.jpeg.
                File temp = new File("temp.jpeg");
                
                //read the original .bmp, and then copy it onto the new temp.jpeg.
                try {
                    BufferedImage OG = ImageIO.read(file1);
                    ImageIO.write(OG, "jpeg", temp);
                    
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
    
                //create an icon from the temp.jpeg.
                ImageIcon icon = new ImageIcon(temp.getAbsolutePath());

                //get a ScaledInstance of the icon, so that all the icons are the same size (more aesthetic)
                Image image = icon.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);

                ImageIcon Icon = new ImageIcon(image);

                //create a label for the icon and file name.
                JLabel label1 = new JLabel(file1.getName(), Icon, 10);

                //create a label for the number.
                JLabel label2 = new JLabel(filecounter + ".");
                filecounter ++;

                //if we have 11 files, then we reached the limit.
                if (filecounter == 11) {
                    limit = true;
                }

                //set font white.
                label2.setForeground(new Color(0,0,0));
                label1.setForeground(new Color(0,0,0));


                //set bounds
                label2.setBounds(file_x - 20,file_y - 25, 100,100);
                label1.setBounds(file_x, file_y,200,50);

                
                //the y-coordinate spacing between files
                file_y = file_y + 65;

                //if file_y > 350, then move to the second column.
                if (file_y > 350) {
                    file_y = 75;
                    file_x = 285;
                }

                //add the labels
                this.add(label1);
                this.add(label2);
                
                this.getContentPane().add(startButton);
                this.getContentPane().add(randomcolor);
                this.getContentPane().add(stars);
                this.getContentPane().add(greyscale);

                this.getContentPane().revalidate();

                this.getContentPane().repaint();

                //delete the temporary .jpeg, because it shouldn't stay on the user's desktop. 
                Path path = Paths.get(temp.getAbsolutePath());

                try {
                    Files.delete(path);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                deleteButton = new JButton("delete");



            }
        } else {
            //if the limit is reached, send out: Sorry, no more space!

            System.out.println("Sorry, you ran out of space! Upgrade to premium to add more than 10 files at once!");
        }
            
        } else if (e.getSource() == startButton){

            duplicate();

            // this for-loop runs all the pairs of original.bmp and [EDITED].bmp's, which are situated in the String [] list in
            // (list[0], list[1]) , (list[2], list[3]), etc.
            for (int i = 0; i < files.length/2; i = i + 2) {

                //if files[i] == null, we want to exit the for-loop.
                if (files[i] == null) {
                    break;
                }
                    //here, we create a new ProcessBuilder, which runs EdgeFilter.java, and enters the arguments files[i] and files[i+1]
                    //it also takes the arguments a, b, c, which are the respective random R, G, B values. 
                    //I pulled how to do this from a StackOverflow website, link below.
                    //https://stackoverflow.com/questions/15218892/running-a-java-program-from-another-java-program
                    try {
                    ProcessBuilder edgedetection = new ProcessBuilder("java", "EdgeFilter.java", files[i], files[i+1], a, b, c);
                
                    edgedetection.inheritIO(); 
                    Process edge = edgedetection.start();
                    int exitCode = edge.waitFor(); 
                    if (exitCode != 0) {
                    }
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
                // end of the StackOverflow website.
            }

            reset();
            
        } else if (e.getSource() == randomcolor){

            Random random = new Random();

            //here, we are generating three int values, ranging from 1 to 255, for the three values in RGB.
            int x = random.nextInt(255) + 1;
            int y = random.nextInt(255) + 1;
            int z = random.nextInt(255) + 1;

            //then, we change those values to Strings, because arguments only accepts strings.
            a = String.valueOf(x);
            b = String.valueOf(y);
            c = String.valueOf(z);

            //set the 'random' button color to the randomized color to indicate which color the resulting edge will be traced in
            randomcolor.setBackground(new Color(x,y,z));
            randomcolor.setOpaque(true);

            //repaint
            this.getContentPane().revalidate();
            this.getContentPane().repaint();
            
        } else if (e.getSource() == stars){

            duplicate();

            for (int i = 0; i < files.length/2; i = i + 2) {

                //if files[i] == null, we want to exit the for-loop.
                if (files[i] == null) {
                    break;
                }
                    //here, we create a new ProcessBuilder, which runs Stars.java, and enters the arguments files[i] and files[i+1].2
                    //I pulled how to do this from a StackOverflow website, link below.
                    //https://stackoverflow.com/questions/15218892/running-a-java-program-from-another-java-program
                    try {
                    ProcessBuilder stars = new ProcessBuilder("java", "Stars.java", files[i], files[i+1]);
                
                    stars.inheritIO(); 
                    Process edge = stars.start();
                    int exitCode = edge.waitFor(); 
                    if (exitCode != 0) {
                    }
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
                // end of the StackOverflow website.

                //This just removes all JLabels and the 'Convert' button, and then redraws only the 'Select Files' button.
                this.getContentPane().removeAll();
                this.getContentPane().add(fileUploader);
                this.getContentPane().repaint();
                this.getContentPane().revalidate();
                
            }

            reset();

        } else if (e.getSource() == greyscale){

          duplicate();

            for (int i = 0; i < files.length/2; i = i + 2) {

                //if files[i] == null, we want to exit the for-loop.
                if (files[i] == null) {
                    break;
                }
                    //here, we create a new ProcessBuilder, which runs GreyscaleFilter.java, and enters the arguments files[i] and files[i+1].2
                    //I pulled how to do this from a StackOverflow website, link below.
                    //https://stackoverflow.com/questions/15218892/running-a-java-program-from-another-java-program
                    try {
                    ProcessBuilder greyscale = new ProcessBuilder("java", "GreyscaleFilter.java", files[i], files[i+1]);
                
                    greyscale.inheritIO(); 
                    Process edge = greyscale.start();
                    int exitCode = edge.waitFor(); 
                    if (exitCode != 0) {
                    }
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
                // end of the StackOverflow website.

                //This just removes all JLabels and the 'Convert' button, and then redraws only the 'Select Files' button.
                this.getContentPane().removeAll();
                this.getContentPane().add(fileUploader);
                this.getContentPane().repaint();
                this.getContentPane().revalidate();

            }

            reset();

        }
    }
}
