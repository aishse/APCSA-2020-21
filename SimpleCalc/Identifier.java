import java.util.ArrayList;
import java.util.List;
/**
 *	Contains a name and a value attritubte, which is used by SimpleCalc to store values
 *	in a database.
 *
 *	@author Anishka Chauhan
 *	@since 4/6/2021
 */
public class Identifier {
  private String name; // name of identifier
  private double value; // value of identifier

  public Identifier (String n, double v)
  {
    name = n;
    value = v;
  }
  /**
   * Sets the name
   */
  public void setName(String s) {
    name = s;
  }
  /**
   * Sets the value
   */
  public void setValue (double v) {
    value = v;
  }
  /**
   * returns the name
   */
  public String getName() {
    return name;
  }
  /**
   * returns the value
   */
  public double getValue() {
    return value;
  }

}
