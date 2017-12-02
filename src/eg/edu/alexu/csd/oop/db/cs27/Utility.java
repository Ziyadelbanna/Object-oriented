package eg.edu.alexu.csd.oop.db.cs27;



import java.io.IOException;

import java.nio.file.StandardOpenOption;

import java.util.ArrayList;

import java.util.LinkedHashMap;

import java.util.Map;

import java.util.regex.Matcher;

import java.util.regex.Pattern;



public class Utility {



	public static boolean matches(String regex, String input) {

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(input);

		return matcher.matches();

	}

	

	public static boolean matchTableParameters(String input, String firstMatch) {

		Pattern pattern = Pattern.compile(firstMatch);

		Matcher matcher = pattern.matcher(input);

		return matcher.matches();

	}

	

	public static Map<String, String> getTableCol(String input, String firstMatch, String tableColRegex) {

		if (input.equals("")) {

			return new LinkedHashMap<>();

		}

		if (!matchTableParameters(input, firstMatch)) {

			return null;

		}

		Pattern pattern = Pattern.compile(tableColRegex);

		Map<String, String> colMap = new LinkedHashMap<>();

		Matcher matcher = pattern.matcher(input);

		while (matcher.find()) {

			if (matcher.group().length() != 0) {

				colMap.put(matcher.group(1), matcher.group(2).toLowerCase());

			}

		}

		return colMap;

	}

	

	public static Map<String, String> getRowMap(Table table, String rowMapRegex, String input) {

		if (input.equals("")) {

			return new LinkedHashMap<>();

		}

		Pattern pattern = Pattern.compile(rowMapRegex);

		Matcher matcher = pattern.matcher(input);

		ArrayList<String>  values = new ArrayList<>(); 

		while (matcher.find()) {

			if (matcher.group().length() != 0) {

				String value = matcher.group(1);

				value = value.replaceAll("\'", "");

//				System.out.println(value);

				values.add(value);

			}

		}

		Map<String, String> tableMap = table.getMap();

		Map<String, String> rowMap = new LinkedHashMap<>();

		int cnt = 0;

		for (Map.Entry<String, String> entry : tableMap.entrySet()) {

			if(cnt >= values.size() || values.get(cnt) == null){

				return null ; 

			}

			String value = values.get(cnt);

			String identifier = entry.getValue();

			if (identifier.equals("int") && isDigit(value)) {

				rowMap.put(entry.getKey(), value);

			} else if (identifier.equals("varchar") && !isDigit(value)) {

				rowMap.put(entry.getKey(), value);

			} else {

				return null;

			}

			cnt++;

		}

		return rowMap;

	}

	

	public static boolean isDigit(String number) {

		try {

			Integer.parseInt(number);

			return true;

		} catch (Exception e) {

			return false;

		}

	}

	

	public static String getTableName(String regex, String input) {

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(input);

		while (matcher.find()) {

			if (matcher.group().length() != 0) {

				return matcher.group(1);

			}

		}

		return null;

	}

	public static String getTheNMatcher(String regex, String input , int whichGroup){

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(input);

		while (matcher.find()) {

			if (matcher.group().length() != 0) {

				return matcher.group(whichGroup);

			}

		}

		return null;

	}

	public static Table getTable(String name) {

		Folder folder = FolderBuilder.getFolder();

		if (!folder.contains(name)) {

			return null;

		}

		String path = folder.getPath() + name + ".xml";

		ReadXML reader = new ReadXML();

		Table table = null;

		try {

			reader.read(path);

		} catch (Exception e) {

			return null;

		}

		return table;

	}

	public static void printt(){

		try {

			java.nio.file.Files.write( java.nio.file.Paths.get("/debug/qq12.log"), "starting engine\n".getBytes() , StandardOpenOption.CREATE);

		} catch (IOException e1) {

			// TODO Auto-generated catch block

			e1.printStackTrace();

		}

	}

	

}