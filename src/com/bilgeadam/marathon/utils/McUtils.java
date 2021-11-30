package com.bilgeadam.marathon.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class McUtils {
private static Scanner scan = new Scanner(System.in);
	
	public static void footer() {
		System.out.println("\n\n\tProgramı kullandığınız için teşekkürler");
		System.out.println("\t\tTekrar görüşmek üzere");
		closeResources();
	}
	
	public static int menu(HashMap<Integer, String> menuItems) {
		boolean correctAnswer = true;
		int selection = -1;
		do {
			if (!correctAnswer) {
				System.err.println("\n\n\tLütfen doğru değer giriniz!\n\n");
			}
			showMenuItems(menuItems);
			selection = getUserSelection();
			correctAnswer = evaluateAnswer(menuItems, selection);
		} while (!correctAnswer);
		return selection;
	}
	
	private static boolean evaluateAnswer(HashMap<Integer, String> menuItems, int selection) {
		return menuItems.containsKey(selection);
	}
	
	private static int getUserSelection() {
		return readInt("Lütfen seçiminizi yapınız");
	}
	
	private static void showMenuItems(TreeMap<Integer, String> menuItems) { // en do�ru veri yap�s�
		Set<Entry<Integer, String>> items = menuItems.entrySet();
		for (Entry<Integer, String> entry : items) {
			System.out.println("\t(" + entry.getKey() + ") .... " + entry.getValue().trim());
		}
	}
	
	private static void showMenuItems(HashMap<Integer, String> menuItems) {
		ArrayList<Integer> keys = new ArrayList<Integer>(menuItems.keySet());
		
		Collections.sort(keys);
		
		// for (int i = 0; i < keys.size(); i++) {
		// int key = keys.get(i);
		// System.out.println("\t(" + key + ") .... " + menuItems.get(key).trim());
		// }
		
		for (Integer key : keys) {
			if (key / 10 > 0)
				System.out.println("\t(" + key + ") .... " + menuItems.get(key).trim());
			else
				System.out.println("\t(" + key + ") ..... " + menuItems.get(key).trim());
			
		}
		
		System.out.println();
	}
	
	public static void header(String title) {
		// ===========
		// ** title **
		// ===========
		int len = title.length();
		StringBuilder row = new StringBuilder("");
		
		for (int i = 0; i < (len + 6); i++) {
			row.append("=");
		}
		
		System.err.println("\n\n\t\t" + row);
		System.err.println("\t\t** " + title.toUpperCase() + " **");
		System.err.println("\t\t" + row + "\n");
	}
	
	public static String readString(String query) {
		showQuery(query);
		String retVal = scan.nextLine();
		// scan.nextLine();
		return retVal;
	}
	
	private static void showQuery(String query) {
		System.out.print("\t" + query + ": ");
	}
	
	public static int readInt(String query) {
		showQuery(query);
		do {
			boolean isDigit = true;
			String input = scan.next().trim();
			scan.nextLine();
			for (int i = 0; i < input.length(); i++) {
				if (!Character.isDigit(input.charAt(i))) {
					isDigit = false;
				}
			}
			if (isDigit)
				return Integer.parseInt(input + "");
			
		} while (true);
	}
	
	public static int[] readInt(String start, String query, int numElements) {
		showQuery(start + " " + numElements + " " + query + "\n");
		int[] retVal = new int[numElements];
		for (int i = 0; i < numElements; i++) {
			System.out.print((i + 1) + ". de�eri giriniz: ");
			retVal[i] = scan.nextInt();
		}
		// scan.nextLine();
		return retVal;
	}
	
	public static double readDouble(String query) {
		double retVal = Double.NEGATIVE_INFINITY;
		boolean retry = false;
		do {
			showQuery(query);
			try {
				retVal = scan.nextDouble();
				retry = false;
			} catch (InputMismatchException ex) {
				System.out.println("hatal� giri�");
				retry = true;
			} finally {
				scan.nextLine();
			}
		} while (retry);
		return retVal;
	}
	
	public static void closeResources() {
		scan.close();
	}
	
	public static boolean wantToEnd(String question, String negativeAnswer) {
		boolean retVal = true;
		
		showQuery(question);
		String answer = scan.next();
		
		retVal = answer.equalsIgnoreCase(negativeAnswer);
		scan.nextLine();
		return !retVal;
	}
	
}
