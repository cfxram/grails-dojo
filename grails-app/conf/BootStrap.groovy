import org.dojotoolkit.Widget;

class BootStrap {

  def init = { servletContext ->
    new Widget(name: "iPod", color: "white", shape: "square").save();
    new Widget(name: "Zune", color: "brown", shape: "square").save();
    new Widget(name: "iPhone", color: "black", shape: "rectangle").save();
    new Widget(name: "Lightsaber", color: "green", shape: "cylinder").save();
    new Widget(name: "Slinky", color: "silver", shape: "cylinder").save();
    new Widget(name: "Frisbee", color: "red", shape: "circle").save();
    new Widget(name: "Roku", color: "black", shape: "square").save();
    new Widget(name: "Boxee Box", color: "green and black", shape: "diamond").save();
    new Widget(name: "Bucky Balls", color: "metalic", shape: "round").save();
    new Widget(name: "mp3 Player", color: "pink", shape: "triange").save();
    new Widget(name: "iPod 2", color: "white", shape: "square").save();
    new Widget(name: "Zune 2", color: "brown", shape: "square").save();
    new Widget(name: "iPhone 2", color: "black", shape: "rectangle").save();
    new Widget(name: "Lightsaber 2", color: "green", shape: "cylinder").save();
    new Widget(name: "Slinky 2", color: "silver", shape: "cylinder").save();
    new Widget(name: "Frisbee 2", color: "red", shape: "circle").save();
    new Widget(name: "Roku 2", color: "black", shape: "square").save();
    new Widget(name: "Boxee Box 2", color: "green and black", shape: "diamond").save();
    new Widget(name: "Bucky Balls 2", color: "metalic", shape: "round").save();
    new Widget(name: "mp3 Player 2", color: "pink", shape: "triange").save();
    new Widget(name: "Apple TV 2", color: "black", shape: "square").save();
    new Widget(name: "iPad 16GB", color: "Silver", shape: "rectangular").save(); 
    new Widget(name: "Sinclair ZX81", color: "Black", shape: "Thin").save();
    new Widget(name: "Windows Phone 7", color: "Various", shape: "Rect").save();
    new Widget(name: "Rim Blackberry", color: "Lame", shape: "Ugly").save();
    new Widget(name: "iPad 32GB 3G", color: "Same", shape: "Same").save();
    new Widget(name: "Amazon Kindle", color: "Black", shape: "Rect").save();
    new Widget(name: "Amazon Kindle", color: "White", shape: "Rect").save();
    new Widget(name: "B&N Nook", color: "Yes", shape: "Rect").save();
    
    
    10.upto(99){
      new Widget(name: "Prototype Widget ${it}", color: "Blue", shape: "Square").save();
    }
    100.upto(199){
      new Widget(name: "Mass Widget ${it}", color: "Green", shape: "Circle").save();
    }    
  }


  def destroy = {
  }
}  