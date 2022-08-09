/**
 * This class contains class (static) methods
 * that will help you test the Picture class
 * methods.  Uncomment the methods and the code
 * in the main to test.
 *
 * @author Barbara Ericson
 */
public class PictureTester
{
  /** Method to test zeroBlue */
  public static void testZeroBlue()
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }

  /** Method to test mirrorVertical */
  public static void testMirrorVertical()
  {
    Picture caterpillar = new Picture("images/caterpillar.jpg");
    caterpillar.explore();
    caterpillar.mirrorVertical();
    caterpillar.explore();
  }

  /** Method to test mirrorTemple */
  public static void testMirrorTemple()
  {
    Picture temple = new Picture("temple.jpg");
    temple.explore();
    temple.mirrorTemple();
    temple.explore();
  }

  /** Method to test the collage method */
  public static void testCollage()
  {
    Picture canvas = new Picture("640x480.jpg");
    canvas.createCollage();
    canvas.explore();
  }

  /** Method to test edgeDetection */
  public static void testEdgeDetection()
  {
    Picture swan = new Picture("swan.jpg");
    swan.edgeDetection(10);
    swan.explore();
  }
  /** Method to test keepOnlyBlue  */
  public static void testKeepOnlyBlue ()
  {
    Picture beach = new Picture("beach.jpg");
    beach.keepOnlyBlue();
    beach.explore();
  }

  public static void testNegate ()
  {
    Picture beach = new Picture("beach.jpg");
    beach.negate();
    beach.explore();
  }

  /** tests greyscale method */
  public static void testGrayscale ()
  {
    Picture beach = new Picture("beach.jpg");
    beach.grayscale();
    beach.explore();
  }

  public static void testPixelate ()
  {
    Picture beach = new Picture("artwork.jpg");
    beach.pixelate(5);
    beach.explore();
  }
  public static void testBlur ()
  {
    Picture beach = new Picture("beach.jpg");
    Picture beach1 = beach.blur(13);

    beach1.explore();
  }
  public static void testEnhance ()
  {
    Picture beach = new Picture("water.jpg");
    Picture beach1 = beach.enhance(5);

    beach1.explore();
  }
  public static void testSwap ()
  {
    Picture beach = new Picture("beach.jpg");
    Picture beach1 = beach.swapLeftRight();

    beach1.explore();
  }
  public static void testStair ()
  {
    Picture beach = new Picture("beach.jpg");
    Picture beach1 = beach.stairStep(6, 40);

    beach1.explore();
  }
  public static void testLiquify ()
  {
    Picture beach = new Picture("artwork.jpg");
    Picture beach1 = beach.liquify(100);

    beach1.explore();
  }
  public static void testWaves ()
  {
    Picture beach = new Picture("swan.jpg");
    Picture beach1 = beach.wavy(8);

    beach1.explore();
  }
  public static void testEdgeDetectionDown ()
  {
    Picture beach = new Picture("swan.jpg");
    Picture beach1 = beach.edgeDetectionBelow(20);

    beach1.explore();
  }
  public static void testGreenScreen ()
  {
    Picture beach = new Picture();
    Picture beach1 = beach.greenScreen();
    beach1.explore();
  }

  /** Main method for testing.  Every class can have a main
    * method in Java */
  public static void main(String[] args)
  {
    // uncomment a call here to run a test
    // and comment out the ones you don't want
    // to run
    //();
  //testBlur();
  //  testEnhance();
  //  testStair();
  //  testLiquify();
  //   testWaves();
  //  testGreenScreen();
     ///testEdgeDetectionDown();
    //testSwap();
    //testZeroBlue();
    //testKeepOnlyBlue();
    //testKeepOnlyRed();
    //testKeepOnlyGreen();
    testNegate();
    //testGrayscale();
    //testFixUnderwater();
    //testMirrorVertical();
    //testMirrorTemple();
    //testMirrorArms();
    //testMirrorGull();
    //testMirrorDiagonal();
    //testCollage();
    //testCopy();
    //testEdgeDetection();
    //testEdgeDetection2();
    //testChromakey();
    //testEncodeAndDecode();
    //testGetCountRedOverValue(250);
    //testSetRedToHalfValueInTopHalf();
    //testClearBlueOverValue(200);
    //testGetAverageForColumn(0);
  }
}
