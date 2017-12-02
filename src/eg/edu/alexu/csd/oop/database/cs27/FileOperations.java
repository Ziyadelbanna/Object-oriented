package eg.edu.alexu.csd.oop.database.cs27;



import java.io.File;

import java.io.IOException;



public class FileOperations {



	public static boolean createDirectory(String directoryName) {

		File file = new File(directoryName);

		if (!file.exists()) {

			return file.mkdirs();

		} else {

			return true;

		}

	}



	public static boolean deleteDirectory(String directoryName) {

		File directory = new File(directoryName);

		// make sure directory exists

		if (!directory.exists()) {

			System.out.println("Directory does not exist.");

			return false;

		} else {

			try {

				delete(directory);

			} catch (IOException e) {

				System.out.println("Error in Delete: " + e.getMessage());

				return false;

			}

		}

		return true;

	}



	private static void delete(File file) throws IOException {

		if (file.isDirectory()) {

			// directory is empty, then delete it

			if (file.list().length == 0) {

				file.delete();

				System.out.println("Directory is deleted : " + file.getAbsolutePath());

			} else {

				// list all the directory contents

				String files[] = file.list();

				for (String temp : files) {

					// construct the file structure

					File fileDelete = new File(file, temp);

					// recursive delete

					delete(fileDelete);

				}

				// check the directory again, if empty then delete it

				if (file.list().length == 0) {

					file.delete();

					System.out.println("Directory is deleted : " + file.getAbsolutePath());

				}

			}

		} else {

			// if file, then delete it

			file.delete();

			System.out.println("File is deleted : " + file.getAbsolutePath());

		}

	}



	public static boolean isFileExist(String tablePath) {

		File file = new File(tablePath);

		return file.exists();

	}



	public static boolean isDirectoryExist(String directory) {

		return true;

	}



}