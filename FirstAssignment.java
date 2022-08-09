/**
 *	FirstAssignment.java
 *	Display a brief description of your summer vacation on the screen.
 *
 *	To compile:	javac -cp .:acm.jar FirstAssignment.java
 *	To execute:	java -cp .:acm.jar FirstAssignment
 *
 *	@author	Anishka Chauhan
 *	@since	8/20/2020
 */
import java.awt.Font;

import acm.program.GraphicsProgram;
import acm.graphics.GLabel;

public class FirstAssignment extends GraphicsProgram {

    public void run() {
    	//	The font to be used
    	Font f = new Font("Serif", Font.BOLD, 18);

    	GLabel s1 = new GLabel("What I did on my summer vacation ...", 10, 20);
    	s1.setFont(f);
    	add(s1);

    	//	Continue adding lines until you have 12 to 15 lines
      GLabel s2 = new GLabel("Went on long walks with my family", 10, 40);
      s2.setFont(f);
      add(s2);

      GLabel s3 = new GLabel("Visited the beach", 10, 65);
      s3.setFont(f);
      add(s3);

      GLabel s4 = new GLabel("Did a lot of baking", 10, 85);
      s4.setFont(f);
      add(s4);

      GLabel s5 = new GLabel("Played video games with friends", 10, 110);
      s5.setFont(f);
      add(s5);

      GLabel s6 = new GLabel("Went online shopping", 10, 135);
      s6.setFont(f);
      add(s6);

      GLabel s7 = new GLabel("Studied math", 10, 160);
      s7.setFont(f);
      add(s7);

      GLabel s8 = new GLabel("Facetimed with friends and family", 10, 185);
      s8.setFont(f);
      add(s8);

      GLabel s9 = new GLabel("Painted and did some digital art", 10, 210);
      s9.setFont(f);
      add(s9);

      GLabel s10 = new GLabel("Listened to lots of music", 10, 235);
      s10.setFont(f);
      add(s10);

      GLabel s11 = new GLabel("Redecorated my room", 10, 260);
      s11.setFont(f);
      add(s11);

      GLabel s12 = new GLabel("Watched a lot of movies and TV shows", 10, 285);
      s12.setFont(f);
      add(s12);


    }

}
