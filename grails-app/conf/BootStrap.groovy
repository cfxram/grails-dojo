import org.dojotoolkit.Widget;

class BootStrap {

  def init = { servletContext ->
    new Widget(name: "iPod", color: "white", shape: "square", category:"Music", discounted: false).save();
    new Widget(name: "Zune", color: "brown", shape: "square", category:"Music", discounted: true).save();
    new Widget(name: "iPhone", color: "black", shape: "rectangle", category:"Phone", discounted: false).save();
    new Widget(name: "Lightsaber", color: "green", shape: "cylinder", category:"Weapon", discounted: false).save();
    new Widget(name: "Slinky", color: "silver", shape: "cylinder", category:"Toy", discounted: false).save();
    new Widget(name: "Frisbee", color: "red", shape: "circle", category:"Toy", discounted: false).save();
    new Widget(name: "Roku", color: "black", shape: "square", category:"TV", discounted: true).save();
    new Widget(name: "Roku2", color: "black", shape: "tiny", category:"TV", discounted: false).save();
    new Widget(name: "Boxee Box", color: "green and black", shape: "diamond", category:"Music", discounted: false).save();
    new Widget(name: "Bucky Balls", color: "metalic", shape: "round", category:"Toy", discounted: false).save();
    new Widget(name: "mp3 Player", color: "pink", shape: "triange", category:"Music", discounted: false).save();
    new Widget(name: "iPod 2", color: "white", shape: "square", category:"Music", discounted: false).save();
    new Widget(name: "Zune 2", color: "brown", shape: "square", category:"Music", discounted: true).save();
    new Widget(name: "iPhone 2", color: "black", shape: "rectangle", category:"Music", discounted: false).save();
    new Widget(name: "Lightsaber 2", color: "green", shape: "cylinder", category:"Weapon", discounted: false).save();
    new Widget(name: "Slinky 2", color: "silver", shape: "cylinder", category:"Toy", discounted: false).save();
    new Widget(name: "Frisbee 2", color: "red", shape: "circle", category:"Toy", discounted: false).save();
    new Widget(name: "Roku 2", color: "black", shape: "square", category:"TV", discounted: false).save();
    new Widget(name: "Boxee Box 2", color: "green and black", shape: "diamond", category:"TV", discounted: false).save();
    new Widget(name: "Bucky Balls 2", color: "metalic", shape: "round", category:"Toy", discounted: false).save();
    new Widget(name: "mp3 Player 2", color: "pink", shape: "triange", category:"Music", discounted: false).save();
    new Widget(name: "Apple TV 2", color: "black", shape: "square", category:"TV", discounted: false).save();
    new Widget(name: "iPad 16GB", color: "Silver", shape: "rectangular", category:"Computer", discounted: false).save();
    new Widget(name: "Sinclair ZX81", color: "Black", shape: "Thin", category:"Computer", discounted: false).save();
    new Widget(name: "Windows Phone 7", color: "Various", shape: "Rect", category:"Phone", discounted: false).save();
    new Widget(name: "Rim Blackberry", color: "Black", shape: "Rect", category:"Phone", discounted: false).save();
    new Widget(name: "iPad 32GB 3G", color: "Same", shape: "Same", category:"Computer", discounted: false).save();
    new Widget(name: "Amazon Kindle", color: "Black", shape: "Rect", category:"Reader", discounted: false).save();
    new Widget(name: "Amazon Kindle", color: "White", shape: "Rect", category:"Reader", discounted: false).save();
    new Widget(name: "B&N Nook", color: "White", shape: "Rect", category:"Reader", discounted: false).save();
    new Widget(name: "Rim Playbook", color: "Black", shape: "Rect", category:"Computer", discounted: true).save();
    new Widget(name: "GalaxyTab", color: "Black", shape: "Rect", category:"Computer", discounted: false).save();

    
    10.upto(99){
      new Widget(name: "Prototype Widget ${it}", color: "Blue", shape: "Square", category:"Square Prototype", discounted: false).save();
    }
    100.upto(199){
      new Widget(name: "Mass Widget ${it}", color: "Green", shape: "Circle", category:"Circle Prototype", discounted: false).save();
    }    
  }


  def destroy = {
  }
}  