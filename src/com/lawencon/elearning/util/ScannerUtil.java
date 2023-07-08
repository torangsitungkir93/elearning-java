package com.lawencon.elearning.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class ScannerUtil {
	public static float getScannerNumber(String desc, int range) {
		float choice = 0;
		String choiceString = "";
		Scanner scan = new Scanner(System.in);
		System.out.print(desc);

		if (range == 0) {
			choiceString = scan.nextLine().trim();
			while (!isValidNumber(choiceString)) {
				System.out.println("Input tidak valid mengandung spasi/huruf");
				System.out.print("Masukkan input baru : ");
				choiceString = scan.nextLine().trim();
			}
			choice = Float.parseFloat(choiceString);
		} else {
			choiceString = scan.nextLine().trim();
			while (!isValidNumber(choiceString)) {
				System.out.println("Input tidak valid mengandung spasi/huruf");
				System.out.print("Masukkan input baru : ");
				choiceString = scan.nextLine().trim();
			}
			choice = Float.parseFloat(choiceString);
			while (choice < 1 || choice > range) {
				System.out.println("Menu out of Range !! ");
				System.out.print("Masukkan input baru : ");
				choice = scan.nextInt();
			}
		}
		return choice;
	}

	public static boolean isValidNumber(String choiceString) {
		return !choiceString.isEmpty() && choiceString.matches("[-+]?[0-9]*\\.?[0-9]+");
	}

	public static String getScannerString(String desc) {
		String choiceString = "";
		Scanner scan = new Scanner(System.in);
		System.out.print(desc);
		choiceString = scan.nextLine().trim();
		while (!isValidString(choiceString)) {
			System.out.println("Input tidak valid mengandung spasi/karakter selain huruf");
			System.out.print("Masukkan input baru : ");
			choiceString = scan.nextLine().trim();
		}
		return choiceString;
	}

	public static boolean isValidString(String choiceString) {
		return !choiceString.isEmpty();
	}

	public static String getAlphaNumericString() {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder sb = new StringBuilder(5);

		for (int i = 0; i < 5; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}

	public static String getCurrentDateStamp() {
		SimpleDateFormat curDate = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String strDate = curDate.format(now);
		return strDate;
	}

	public static String getCurrentTimeStamp() {
		SimpleDateFormat curDate = new SimpleDateFormat("HH:mm a");
		Date now = new Date();
		String strDate = curDate.format(now);
		return strDate;
	}

	public static Timestamp getTimeStamp(String args) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime dateTime = null;
		Scanner scan = new Scanner(System.in);
		
		while (dateTime == null) {
			System.out.print(args);
			String inputDateTime = scan.nextLine();
			try {
				dateTime = LocalDateTime.parse(inputDateTime, formatter);
			} catch (Exception e) {
				System.out.println("Format salah! Input ulang sesuai format yang benar");
			}
		}
		return Timestamp.valueOf(dateTime);
	}
	
    public static boolean checkValidTime(Timestamp startTime, Timestamp endTime) {
        boolean isValid = false;
        if (startTime.toLocalDateTime().toLocalDate().isEqual(endTime.toLocalDateTime().toLocalDate())) {
            if (endTime.after(startTime)) {
                isValid = true;
            } else {
                System.out.println("Jam yang anda masukkan tidak valid");
            }
        } else {
            System.out.println("1 Sesi harus ditanggal yang sama");
        }
        return isValid;
    }
}