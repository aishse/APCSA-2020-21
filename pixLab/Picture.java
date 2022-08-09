import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from
 * SimplePicture and allows the student to add functionality to
 * the Picture class.
 *
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture
{
  ///////////////////// constructors //////////////////////////////////

  /**
   * Constructor that takes no arguments
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor
     */
    super();
  }

  /**
   * Constructor that takes a file name and creates the picture
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }

  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }

  /**
   * Constructor that takes a picture and creates a
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }

  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }

  ////////////////////// methods ///////////////////////////////////////

  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() +
      " height " + getHeight()
      + " width " + getWidth();
    return output;

  }

  /**
   * Elimates all values of blue from image
   * @method zeroBlue
   */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }

  /** Method that mirrors the picture around a
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }

  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();

    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {

        leftPixel = pixels[row][col];
        rightPixel = pixels[row]
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }

  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic,
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow;
         fromRow < fromPixels.length &&
         toRow < toPixels.length;
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol;
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }


  /** Method to show large changes in color
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0;
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) >
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }




  /* Main method for testing - each class in Java can have a main
   * method
   */
  public static void main(String[] args)
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  /**
   * Keeps only blue values - gets rid of rest.
   * @method keepOnlyBlue
   */
  public void keepOnlyBlue() {
    Pixel[][] pixels = this.getPixels2D();
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < pixels[0].length; col++)
      {
        pixels[row][col].setGreen(0);
        pixels[row][col].setRed(0);
      }
    }

  }
  /**
   * Negates all pixels in the picture (x-ray effect)
   * @method negate
   */
  public void negate () {
    Pixel[][] pixels = this.getPixels2D();
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < pixels[0].length; col++)
      {
        pixels[row][col].setGreen(255 - pixels[row][col].getGreen());
        pixels[row][col].setBlue(255 - pixels[row][col].getBlue());
        pixels[row][col].setRed(255 - pixels[row][col].getRed());
      }
    }
  }

  /**
   * Turns all pixels into shades of grey
   */
    public void grayscale()
    {
      Pixel[][] pixels = this.getPixels2D();
      for (int row = 0; row < pixels.length; row++)
      {
        for (int col = 0; col < pixels[0].length; col++)
        {
          int avg = (pixels[row][col].getGreen() + pixels[row][col].getBlue() + pixels[row][col].getRed()) / 3;
          pixels[row][col].setGreen(avg);
          pixels[row][col].setBlue(avg);
          pixels[row][col].setRed(avg);
        }
      }
    }
  /**
   * Pixelates an image
   * @method pixelate
   * @param  size     the radius of each pixel (can be odd or even)
   */
  public void pixelate (int size)
  {
    Pixel[][] pixels = this.getPixels2D();
    int greenAvg = 0;
    int redAvg = 0;
    int blueAvg = 0;
    int r = 0;
    int c = 0;
    for (int row = 0; row < pixels.length; row+=size)
    {

      for (int col = 0; col < pixels[0].length; col+=size)
      {
       pixelateGrid(pixels, row, col, size);
      }
    }

  }
  /**
   * Does the pixelation functionality of pixelate by calculating the average color of each
   * of the grids based on the specified size and setting each grid to that color. Able to handle
   * images that do not perfectly fit the grid size
   * @method setAvgColor
   * @param  pixels      array of Pixel objects representing the image to be pixelated
   * @param  row         row index of grid
   * @param  col         col index of grid
   * @param  size        size of grid
   */
  private void pixelateGrid (Pixel [][] pixels, int row, int col, int size)
  {
    int greenAvg = 0;
    int redAvg = 0;
    int blueAvg = 0;
    int endRow = 0;
    int endCol = 0;
    if (row + size < pixels.length)
    {
      endRow = row + size;
    }
    else if (row + size >= pixels.length)
    {
      endRow = pixels.length;
    }
    if (col + size < pixels[0].length)
    {
      endCol = col + size;
    }
    else if (col + size >= pixels[0].length)
    {
      endCol = pixels[0].length;
    }
    for (int r = row; r < endRow; r++)
    {
      for (int c = col; c < endCol; c++)
      {
        greenAvg += pixels[r][c].getGreen();
        redAvg += pixels[r][c].getRed();
        blueAvg += pixels[r][c].getBlue();

      }
    }
    int denom = size * size;
    for (int r = row; r < endRow; r++)
    {
      for (int c = col; c < endCol; c++)
      {
        pixels[r][c].setColor(new Color(redAvg/denom, greenAvg/denom, blueAvg/denom));
      }
    }

  }

  /**
   * Method that blurs the picture
   * @method blur
   * @param  size      Blur size, greater is more blur
   * @return           Blurred picture
   */
  public Picture blur (int size)
  {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel [][] resultPixels = result.getPixels2D();

    for (int r = 0; r < resultPixels.length; r++)
    {
      for (int c = 0; c < resultPixels[0].length; c++)
      {
        resultPixels[r][c].setColor(getAvgPixel(pixels, r, c, size));
      }
    }

    return result;
  }
  /**
   * Method that enhances a picture by getting the average area Color
   * around a pixel then applies the following formula:
   *
   *    pixelColor <- 2 * currentValue - averageValue
   *
   * size is the area to sample for blur
   * @method enhance
   * @param  size    Larger means more area to average around pixel
   *                 and longer compute time.
   * @return         enhanced pictureI
   */
  public Picture enhance (int size)
  {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel [][] resultPixels = result.getPixels2D();

    for (int r = 0; r < resultPixels.length; r++)
    {
      for (int c = 0; c < resultPixels[0].length; c++)
      {
        Color currentCol = pixels[r][c].getColor();
        Color avgCol = getAvgPixel(pixels, r, c, size);
        int red = 2 * currentCol.getRed() - avgCol.getRed();
        int green = 2 * currentCol.getGreen() - avgCol.getGreen();
        int blue = 2 * currentCol.getBlue() - avgCol.getBlue();
        resultPixels[r][c].setColor(new Color (red, green, blue));
      }
    }

    return result;

  }
  /**
   * Gets the average color around a given pixel
   *
   * @method getAvgPixel
   * @param  pixels      image
   * @param  row         row location of pixel
   * @param  col         column location of pixel
   * @param  size        area of blur/enhance
   * @return             avg color
   */
  private Color getAvgPixel (Pixel [][] pixels, int row, int col, int size)
  {
    int redAvg = 0;
    int blueAvg = 0;
    int greenAvg = 0;
    int shift = size/2;
    int counter = 0;
    int [] startCoord = new int[2];
    int [] endCoord = new int[2];
    int [] temp = getStartAndEnd(pixels, row, col, shift);
    endCoord[0] = temp[0];
    endCoord[1] = temp[1];
    startCoord[0] = temp[2];
    startCoord[1] = temp[3];

    for (int r = startCoord[0]; r <= endCoord[0]; r++)
    {
      for (int c = startCoord[1]; c <= endCoord[1]; c++)
      {
        redAvg+= pixels[r][c].getRed();
        greenAvg += pixels[r][c].getGreen();
        blueAvg += pixels[r][c].getBlue();
        counter++;
      }
    }

    return new Color(redAvg/counter, greenAvg/counter, blueAvg/counter);
  }
  /**
   * Gets the start coordinates and end coordinates of area of blur/enchance,
   * in an array of integers.
   *
   * @method getStartAndEnd
   * @param  pixels         image
   * @param  row            row coordinate of pixel
   * @param  col            col coordinate of pixel
   * @param  shift          radius around pixel
   * @return                int coordinates of start and end in the form of an array
   */
  private int [] getStartAndEnd (Pixel [][] pixels, int row, int col, int shift)
  {
    int [] coords = new int[4];
    if (row + shift < pixels.length)
    {
      if (col + shift < pixels[0].length)
      {
        coords[0] = row + shift;
        coords[1] = col + shift;
      }
      else
      {
        coords[0] = row + shift;
        coords[1] = pixels[0].length-1;
      }
    }
    else
    {
      coords[0] = pixels.length-1;;
      if (col + shift < pixels[0].length)
        coords[1] = col + shift;
      else
        coords[1] = pixels[0].length - 1;
    }
    if (row - shift >= 0)
    {
      if (col - shift >= 0)
      {
        coords[2] = row - shift;
        coords[3] = col - shift;
      }
      else
      {
        coords[2] = row - shift;
        coords[3] = 0;
      }
    }
    else
    {
      coords[2] = 0;
      if (col - shift >= 0)
        coords[3] = col - shift;
      else
        coords[3] = 0;
    }
    return coords;
  }

  /**
   * Swaps the halves of the images
   *
   * @return swapped image
   */
  public Picture swapLeftRight ()
  {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel [][] resultPixels = result.getPixels2D();
    int width = pixels[0].length;

    for (int r = 0; r < pixels.length; r++)
    {
      for (int c = 0; c < pixels[0].length; c++)
      {
        int newColumn = (c + width / 2) % width;
        resultPixels[r][newColumn].setColor(pixels[r][c].getColor());
      }
    }
    return result;
  }

  /**
   * Creates a jagged picture
   * @method stairStep
   * @param  shiftCount  the shift for each step
   * @param  steps       number of steps
   * @return            [description]
   */
  public Picture stairStep(int shiftCount, int steps)
  {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel [][] resultPixels = result.getPixels2D();

    int stepheight = pixels.length / steps;
    int width = pixels[0].length;
    int shift = 0;

    for (int r = 0; r < pixels.length; r++)
    {
      for (int c = 0; c < pixels[0].length; c++)
      {
        int newColumn = (c + shift) % width;
        resultPixels[r][newColumn].setColor(pixels[r][c].getColor());
      }
      if (r % stepheight == 0)
        shift += shiftCount;
    }
    return result;
  }
/**
 * Distorts a picture horizontally across a bell curve.
 *
 * @method liquify
 * @param  maxHeight Max height (shift) of curve in pixels
 * @return           Liquified picture
 */
  public Picture liquify (int maxHeight)
  {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel [][] resultPixels = result.getPixels2D();

    int bellWidth = 20;
    int width = pixels[0].length;
    int height = pixels.length;
    double exponent = 0;
    int shift = 0;

    for (int row = 0; row < pixels.length; row++)
    {
      exponent = Math.pow(row-height / 2.0, 2) / (2.0 * Math.pow(bellWidth, 2));
      shift = (int)(maxHeight * Math.exp(-exponent));
      for (int col = 0; col < pixels[0].length; col++)
      {
        int newColumn = (col + shift) % width;
        resultPixels[row][newColumn].setColor(pixels[row][col].getColor());
      }

    }

    return result;

  }
/**
 * Distorts a picture by creating waves in a sinosodial pattern.
 *
 * @method wavy
 * @param  amplitude  max shift of pixels
 * @return            wavy picture
 */
  public Picture wavy(int amplitude)
  {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel [][] resultPixels = result.getPixels2D();
    int frequency = 6;
    int width = pixels[0].length;
    int height = pixels.length;
    double pi = Math.PI;

    for (int row = 0; row < pixels.length; row++)
    {
      int horShift = (int)(amplitude* Math.sin(2 * Math.PI * frequency * row+ row));
    //  System.out.println(horShift);

      for (int col = 0; col < pixels[0].length; col++)
      {
        int newColumn = (col + horShift) % width;
        if (newColumn < 0)
          resultPixels[row][(width-1)-Math.abs(newColumn)].setColor(pixels[row][col].getColor());
        else
          resultPixels[row][newColumn].setColor(pixels[row][col].getColor());


      }

    }
    return result;

  }
  /**
   * Method to detect changes in color vertically (top to bottom).
   * @method edgeDetection
   * @param  threshold     the difference in color
   * @return               black and white picture with edge detected.
   */
  public Picture edgeDetectionBelow(int threshold)
  {
      Pixel[][] pixels = this.getPixels2D();
      Picture result = new Picture(pixels.length, pixels[0].length);
      Pixel [][] resultPixels = result.getPixels2D();
      Pixel topPixel = null;
      Pixel downPixel = null;
      Color bottomColor = null;
      for (int row = 0; row < pixels.length-1; row++)
      {
        for (int col = 0; col < pixels[0].length; col++)
        {
          topPixel = pixels[row][col];
          downPixel = pixels[row+1][col];
          bottomColor = downPixel.getColor();
          if (topPixel.colorDistance(bottomColor) > threshold)
            resultPixels[row][col].setColor(Color.BLACK);
          else
            resultPixels[row][col].setColor(Color.WHITE);
        }
      }
      return result;
  }
/**
 * Uses three images to create one composite image in which two pets are sitting in a japanese house. Resizes
 * the images of both pets to fit proportionally to the background (hard coded in). Uses the Pixel class's color
 * distance functionality to get rid of the green screen in the background.
 *
 * @method greenScreen
 * @return a composite image
 */
  public Picture greenScreen()
  {
    Picture bkg = new Picture("IndoorJapeneseRoomBackground.jpg");
    Pixel[][] pixels = bkg.getPixels2D();

    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel [][] resultPixels = result.getPixels2D();

    Color chroma = new Color(51, 204, 51);

    Picture kitten = new Picture("kitten1GreenScreen.jpg");
    Pixel [][] kittenPixels = kitten.getPixels2D();

    Picture puppy = new Picture("puppy1GreenScreen.jpg");
    Pixel [][] puppyPixels = puppy.getPixels2D();

    kittenPixels = scaleDownImg(kittenPixels, 2);
    puppyPixels = scaleDownImg(puppyPixels, 2);


    int cCounter = 0;
    int rCounter = 0;

    for (int r = 0; r < pixels.length; r++)
    {
      for (int c = 0; c < pixels[0].length; c++)
      {
        resultPixels[r][c].setColor(pixels[r][c].getColor());

      }
    }

    for (int r = 375; r < pixels.length; r++)
    {
      cCounter = 0;
      for (int c = 500; c < pixels[0].length; c++)
      {
        if (rCounter < kittenPixels.length && cCounter < kittenPixels[0].length)
        {
          Pixel pix = kittenPixels[rCounter][cCounter];
          if (pix.colorDistance(chroma) > 100 && !pix.getColor().equals(Color.WHITE))
            resultPixels[r][c].setColor(kittenPixels[rCounter][cCounter].getColor());

          cCounter++;
        }
      }
      rCounter++;
    }
    rCounter = 0;
    cCounter = 0;
    for (int r = 370; r < pixels.length; r++)
    {
      cCounter = 0;
      for (int c = 268; c < pixels[0].length; c++)
      {
        if (rCounter < puppyPixels.length && cCounter < puppyPixels[0].length)
        {
          Pixel pix = puppyPixels[rCounter][cCounter];
            if (pix.colorDistance(chroma) > 100 && !pix.getColor().equals(Color.WHITE))
            resultPixels[r][c].setColor(puppyPixels[rCounter][cCounter].getColor());

          cCounter++;
        }
      }
      rCounter++;
    }
    return result;

  }

/**
 * Scales an image by a designated factor. The size of the pixel array does stay the same, so whitespace
 * has to be taken out once the image shrinks.
 * @method scaleDownImg
 * @param  image        image to be shrunk
 * @param  factor       factor of shrinking
 * @return              shrunken image with whitespace
 */
  private Pixel[][] scaleDownImg (Pixel [][] image, int factor)
  {
    int width = image[0].length;
    int height = image.length;

    int newWidth = width/factor;
    int newHeight = height/factor;

    Picture result = new Picture(width, height);
    Pixel [][] resultPixels = result.getPixels2D();
    int rCount = 0;
    int cCount = 0;

    for (int r = 0; r < height; r+= factor)
    {
      cCount = 0;
      for (int c = 0; c < width; c+= factor)
      {
        if (cCount < newWidth && rCount < newHeight)
          resultPixels[rCount][cCount].setColor(getAvgPixel(image, r, c, factor));
        cCount++;
      }
      rCount++;
    }
    return resultPixels;
  }








} // this } is the end of class Picture, put all new methods before this
