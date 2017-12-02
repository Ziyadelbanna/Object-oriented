package eg.edu.alexu.csd.oop.db.cs27;



import java.io.File;

import java.io.IOException;

import java.nio.file.StandardOpenOption;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;



public class Engine implements Database {



	ExpressionChain myChain = ExpressionChain.getInstance();

	ISelectChain selectChain = ISelectChain.getInstance();

	boolean callMeOnce = true ; 

	public Engine() {

		FolderBuilder.setFolder(null);

	}

	@Override

	public String createDatabase(String databaseName, boolean dropIfExists) {

		if(callMeOnce){

			callMeOnce = false ; 

			//Utility.printt(); 

		}

		//esht8l aboos 2ydak 

		String x = databaseName ;

		String y = Boolean.toString(dropIfExists) ;

		String z = "\n\n\n\n calling create dabatase with name = " + x + " and boolean is " + y  + "\n" ;

		String u = "\n\n\n\nSystem.out.println(eng.createDatabase(" +   "\"" + databaseName + "\"" + "," + y + "));\n" ;

//		try {

//			java.nio.file.Files.write( java.nio.file.Paths.get("/debug/qq12.log"), u.getBytes() , StandardOpenOption.APPEND);

//		} catch (IOException e1) {

//			// TODO Auto-generated catch block

//			e1.printStackTrace();

//		}

		databaseName = databaseName.toLowerCase() ;

		File data = new File("C:" + File.separator + databaseName) ;

		if(data.isDirectory()){

			if(dropIfExists){

				try {

					String[]entries = data.list();

					for(String s: entries){

					    File currentFile = new File(data.getPath(),s);

					    currentFile.delete();

					}

					data.delete() ;

					this.executeStructureQuery("create database " + databaseName) ;

				} catch (SQLException e) {

					FolderBuilder.setFolder(null);

				} 

			}else{

				FolderBuilder.create(data.getPath());

				String [] names = data.list() ;

				for(String i : names){

					System.out.println(i);

					if(i.endsWith(".dtd")){

						continue ; 

					}

					Table table = new Table(FolderBuilder.getFolder().getPath() + File.separator + i) ;

					FolderBuilder.getFolder().addTable(table);

				}

				return data.getPath() ;

			}

		}

		else{

			try {

				this.executeStructureQuery("create database " + databaseName) ;

			} catch (SQLException e) {

				FolderBuilder.setFolder(null);

			}

		}

		return FolderBuilder.getFolder().getPath() ; 

	}



	@Override

	public boolean executeStructureQuery(String query) throws SQLException {

		String x = "calling executeStructureQuery which create or drop, table or database with quiery = " + query + "\n"  ;

		String y = "System.out.println(eng.executeStructureQuery(" + "\"" +query  + "\"" + "));\n" ;

		



//		try {

//			java.nio.file.Files.write( java.nio.file.Paths.get("/debug/qq12.log"), y.getBytes() , StandardOpenOption.APPEND);

//		} catch (IOException e1) {

//			// TODO Auto-generated catch block

//			e1.printStackTrace();

//		}

		if(query == null || query.equals("")){

			return false;

		}

		Expression solver = myChain.getMatcher(query);

		if(solver == null){

			//query = query.substring(0, query.indexOf(")") + 1);

			solver = myChain.getMatcher(query);

			if (solver == null) {

				throw new SQLException(query) ;

			} else {

				if (solver.interpret(query) == 0) {

					return false;

				} else {

					return true;

				}

			}

			

		}

		if (solver.interpret(query) == 0) {

			return false;

		}

		return true;

	}



	@Override

	public Object[][] executeQuery(String query) throws SQLException {

		String z = "calling executeQuery which select from table with query = " + query + "\n" ;

		String y = "Object [][] ans = eng.executeQuery(" + "\""+ query  + "\"" + ");\n" ;

//		try {

//			java.nio.file.Files.write( java.nio.file.Paths.get("/debug/qq12.log"), y.getBytes() , StandardOpenOption.APPEND);

//		} catch (IOException e1) {

//			// TODO Auto-generated catch block

//			e1.printStackTrace();

//		}

		if(query == null || query.equals("")){

			return null;

		}

		try{

			ISelect solver = selectChain.getMatcher(query);

			if(solver.interpret(query) == null){

				return new Object [0][0] ; 

			}

			return solver.interpret(query);

		}

		catch(Exception e){

			throw new SQLException(query) ;

		}

	}



	@Override

	public int executeUpdateQuery(String query) throws SQLException {

		String y = "System.out.println(eng.executeUpdateQuery(" + "\"" + query  +"\"" + "));\n" ;

		String z = "calling executeUpdateQuery which Insert or update or delete the data with query = " + query + "\n" ;

//		try {

//			java.nio.file.Files.write( java.nio.file.Paths.get("/debug/qq12.log"), y.getBytes() , StandardOpenOption.APPEND);

//		} catch (IOException e1) {

//			// TODO Auto-generated catch block

//			e1.printStackTrace();

//		}

		if(query == null || query.equals("")){

			throw new SQLException() ;

		}

		

		Expression solver = myChain.getMatcher(query);

		if(solver == null){

			throw new SQLException("myChain failed, Calling select from Update") ;

		}



		return solver.interpret(query);

	}



}