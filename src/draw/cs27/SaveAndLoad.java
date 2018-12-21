package eg.edu.alexu.csd.oop.draw.cs27;

import java.awt.Color;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.cs27.json.JSONArray;
import eg.edu.alexu.csd.oop.draw.cs27.json.JSONObject;
import eg.edu.alexu.csd.oop.draw.cs27.json.parser.JSONParser;
import eg.edu.alexu.csd.oop.draw.cs27.json.parser.ParseException;

public class SaveAndLoad {

	public void save(String path, LinkedList<LinkedList<Shape>> shapeslists) {
		LinkedList<Shape> Shapes = new LinkedList<Shape>();
		Shapes = shapeslists.getLast();
		JSONObject allShapes = new JSONObject();
		JSONArray shapesArray = new JSONArray();
		for (int i = 0; i < Shapes.size(); i++) {
			JSONObject singleShape = new JSONObject();
			String name = null;
			try {
				name = ((DrawShape) Shapes.get(i)).getClass().getSimpleName();
			} catch (Exception e) {
				// TODO
			}
			singleShape.put("Type", name);

			JSONArray properties = new JSONArray();
			try {
				if (name != null) {
					properties.add(String.valueOf(Shapes.get(i).getPosition().getX()));
					properties.add(String.valueOf(Shapes.get(i).getPosition().getY()));

					properties.add(String.valueOf(Shapes.get(i).getColor().getRed()));
					properties.add(String.valueOf(Shapes.get(i).getColor().getGreen()));
					properties.add(String.valueOf(Shapes.get(i).getColor().getBlue()));

					if (Shapes.get(i).getFillColor() == null) {
						properties.add(null);
						properties.add(null);
						properties.add(null);
					} else {
						properties.add(String.valueOf(Shapes.get(i).getFillColor().getRed()));
						properties.add(String.valueOf(Shapes.get(i).getFillColor().getGreen()));
						properties.add(String.valueOf(Shapes.get(i).getFillColor().getBlue()));
					}
					for (final Map.Entry<String, Double> s : Shapes.get(i).getProperties().entrySet()) {
						properties.add(s.getValue().toString());
					}

				}

				singleShape.put("Properties", properties);
				shapesArray.add(singleShape);
			} catch (Exception e) {
				// TODO
			}
		}
		try {
			allShapes.put("ShapesArray", shapesArray);
		} catch (Exception e) {
			// TODO
		}

		// try-with-resources statement based on post comment below
		try (FileWriter file = new FileWriter(path)) {
			file.write(allShapes.toJSONString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public LinkedList load(String path) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(path));
			JSONObject Shapes = (JSONObject) obj;
			JSONArray ShapesArray = new JSONArray();
			MyDrawEngine MyDE = new MyDrawEngine();
			ShapesArray = (JSONArray) Shapes.get("ShapesArray");
			for (int i = 0; i < ShapesArray.size(); i++) {
				try {
					JSONObject oneShape = (JSONObject) obj;
					oneShape = (JSONObject) ShapesArray.get(i);
					if (oneShape.get("Type") == null) {
						MyDE.addShape(null);
					} else {
						for (int k = 0; k < MyDE.getSupportedShapes().size(); k++) {
							if (oneShape.get("Type").equals(MyDE.getSupportedShapes().get(k).getSimpleName())) {
								JSONArray properties = new JSONArray();
								properties = (JSONArray) oneShape.get("Properties");
								Constructor constructor = null;
								try {
									constructor = MyDE.getSupportedShapes().get(k).getConstructor();
								} catch (NoSuchMethodException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (SecurityException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								DrawShape shapeObject = null;
								try {
									shapeObject = (DrawShape) constructor.newInstance();
								} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
										| InvocationTargetException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								final Double x = Double.valueOf((String) properties.get(0));
								final Double y = Double.valueOf((String) properties.get(1));
								Point pos = new Point(x.intValue(), y.intValue());
								shapeObject.setPosition(pos);

								Double Red = Double.valueOf((String) properties.get(2));
								Double Green = Double.valueOf((String) properties.get(3));
								Double Blue = Double.valueOf((String) properties.get(4));
								Color color = new Color(Red.intValue(), Green.intValue(), Blue.intValue());
								shapeObject.setColor(color);
								if (properties.get(5) == null) {
									Color fillC = null;
									shapeObject.setFillColor(fillC);
								} else {
									Red = Double.valueOf((String) properties.get(5));
									Green = Double.valueOf((String) properties.get(6));
									Blue = Double.valueOf((String) properties.get(7));
									Color fillC = new Color(Red.intValue(), Green.intValue(), Blue.intValue());
									shapeObject.setFillColor(fillC);
								}
								if (properties.size() == 8) {
									final Map<String, Double> oneShapeProp = new HashMap<>();
									shapeObject.setProperties(oneShapeProp);
								} else {
									int m = 0;
									Map<String, Double> oneShapeProp = new HashMap<>();
									oneShapeProp = shapeObject.getProperties();
									for (Map.Entry<String, Double> entry : oneShapeProp.entrySet()) {
										oneShapeProp.put(entry.getKey(),
												Double.parseDouble((String) properties.get(8 + m)));
										m++;
									}
									shapeObject.setProperties(oneShapeProp);
								}
								MyDE.addShape(shapeObject);
							}
						}

					}
				} catch (Exception e) {
					// TODO
				}
			}
			return MyDE.shapeslists;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
