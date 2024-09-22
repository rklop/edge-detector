README.txt

[COMPILE]
javac *.java

[RUN]
java Main

[USE]
Prerequisites:
Please make sure any images you are attempting to convert are bitmaps (.bmp) compatible with Windows 3.1 or
use the images provided in the zip. The simplest way to convert other images to these kinds of bitmaps is to open them in
MS Paint and "Save As" 24-bit color .bmps. The reason for this limitation is that .bmp files compatible with Windows 3.1
have their sizes determined directly by pixel-count without compression, yielding a better result after image filtering.

Directions:
1. Unzip all included files into one folder.

2. Compile and run the program according to the above terminal commands.

3. On the main screen, you have three options:
	(1) "Start" - Brings you to the window where you upload .bmp images to be filtered
	(2) "More Info" - Brief information on the application
	(3) "Exit" - Terminates the application

4. Press "Start" and then "Select File:". Then select a compatible .bmp image to use. You can select up to 10.
	(please read the Prerequisites about .bmp version articulated above carefully)

5. You will have four options:
	(1) "Edge Detection" - Runs an edge detection algorithm based on the Sobel operator and outlines the edge.
		Default color is white.
	(2) "Random Edge Color" - Randomly selects an RGB code to color the edge after running "Edge Detection."
		Must be applied BEFORE "Edge Detection" for anything to happen.
	(3) "Stars" - Runs an edge detection that outlines the edges of the image in a star-like constellation.
	(4) "Greyscale" - Converts the image to a greyscale based on RGB color averaging.
	
6. When the window refreshes completely, the filtered image will be outputted in your folder.