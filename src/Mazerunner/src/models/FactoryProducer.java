package models;

public class FactoryProducer {
	
	public static AbstractFactory getFactory(String choice) {
		if(choice.equalsIgnoreCase("image")) {
			return new FactoryOfImages();
		}
		if(choice.equalsIgnoreCase("model")) {
			return new FactoryOfModels();
		}
		else return null;
	}

}
