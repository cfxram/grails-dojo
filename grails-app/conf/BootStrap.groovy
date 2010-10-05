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
  }


  def destroy = {
  }
}  