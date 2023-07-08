package com.lawencon.elearning.config.view;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.lawencon.elearning.model.Clazz;
import com.lawencon.elearning.model.LearningSession;
import com.lawencon.elearning.service.ClazzService;
import com.lawencon.elearning.service.LearningSessionService;
import com.lawencon.elearning.service.UserService;
import com.lawencon.elearning.service.impl.ClazzServiceImpl;
import com.lawencon.elearning.service.impl.LearningSessionServiceImpl;
import com.lawencon.elearning.service.impl.UserServiceImpl;
import com.lawencon.elearning.util.ScannerUtil;

public class InstructorView {
	private final UserService userService;
	private final ClazzService clazzService;
	private final LearningSessionService learningSessionService;
	private Long currentIdUser=null;
	
	public InstructorView() throws SQLException {
		this.userService = new UserServiceImpl();
		this.clazzService = new ClazzServiceImpl();
		this.learningSessionService = new LearningSessionServiceImpl();
	}

	public void show() throws SQLException {
		System.out.println("=== Menu Pengajar ===");
        System.out.println("1. My Class");
        System.out.println("2. Buat Learning");
        System.out.println("3. Approve");
        System.out.println("4. Review Task");
        System.out.println("5. Keluar");

        final int choice = (int) ScannerUtil.getScannerNumber("Pilih Menu : ", 0); 

        switch (choice) {
            case 1:
                showMyClassMenu();
                break;
            case 2:
                createLearning();
                break;
            case 3:
                // Logika untuk menu Approve
                break;
            case 4:
                // Logika untuk menu Review Task
                break;
            case 5:
                System.out.println("Keluar dari menu Instruktur");
                break;
            default:
                System.out.println("Pilihan tidak valid");
                break;
        }
	}

	private void createLearning() throws SQLException {
		System.out.println(" ------ Menu Tambah Learning ------");

		final List<Clazz> clazzez = clazzService.getByInstructor(currentIdUser);

		if (clazzez.size() > 0) {
			System.out.println("+===============================+");
			System.out.println("+==========  My Class  =========+");
			System.out.println("+===============================+");
			for (int i = 0; i < clazzez.size(); i++) {
			    System.out.println((i + 1) + ". " + clazzez.get(i).getClassCode() + " " + " " + clazzez.get(i).getClassLearning());
			}
			final int choiceClazz = (int) ScannerUtil.getScannerNumber("Pilih Kelas : ",clazzez.size());
			final List<LearningSession> learningSessionList = learningSessionService.getAllByClass(getIdClazz(choiceClazz));
			
			if(learningSessionList.size()>0) {
				System.out.println("+===============================+");
				System.out.println("+====  My Learning From "+getNameClazz(choiceClazz)+" ====+");
				System.out.println("+===============================+");
				for (int i = 0; i < learningSessionList.size(); i++) {
					LearningSession session = learningSessionList.get(i);
			        LocalDateTime startTime = session.getStartTime();
			        LocalDateTime endTime = session.getEndTime();

			        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

			        String formattedStartDate = startTime.format(dateFormatter);
			        String formattedStartTime = startTime.format(timeFormatter);
			        String formattedEndTime = endTime.format(timeFormatter);

			        System.out.println((i + 1) + ". " + session.getClazz().getClassLearning() + " " + formattedStartDate + " | " + formattedStartTime + "-" + formattedEndTime);
				}
			}else {
				System.out.println("+===== Learning Belum Ada =====+");
				System.out.println("+----  Tambahkan Learning  ----");
			}
			
			Timestamp startTime = null;
			Timestamp endTime = null;
			do {
			System.out.println("-- Masukkan tanggal mulai dan waktu mulai sesuai format(dd/MM/yyyy HH:mm:ss) --");
			startTime = ScannerUtil.getTimeStamp("Masukkan : ");
			System.out.println("-- Masukkan tanggal selesai dan waktu selesai sesuai format(dd/MM/yyyy HH:mm:ss) --");
			endTime = ScannerUtil.getTimeStamp("Masukkan : ");
			}while(!ScannerUtil.checkValidTime(startTime,endTime));
			
			learningSessionService.createLearningSession(startTime, endTime, getIdClazz(choiceClazz), currentIdUser);
		}else {
			System.out.println("Anda belum memiliki Kelas,Hubungi admin terlebih dahulu !! ");
		}
	}

	private void showMyClassMenu() throws SQLException {
		  System.out.println("=== My Class ===");
	        System.out.println("1. Daftar kelas saya");
	        System.out.println("2. Detail Class");
	        System.out.println("3. Keluar");

	        final int choice = (int) ScannerUtil.getScannerNumber("Pilih Menu : ", 0); 

	        switch (choice) {
	            case 1:
	            	final List<Clazz> clazzez = clazzService.getByInstructor(currentIdUser);

	        		if (clazzez.size() > 0) {
	        			System.out.println("+===============================+");
	        			System.out.println("+==========  My Class  =========+");
	        			System.out.println("+===============================+");
	        			for (int i = 0; i < clazzez.size(); i++) {
	        			    System.out.println((i + 1) + ". " + clazzez.get(i).getClassCode() + " " + " " + clazzez.get(i).getClassLearning());
	        			}
	        		}else {
	        			System.out.println("Anda belum memiliki Kelas,Hubungi admin terlebih dahulu !! ");
	        		}
	                break;
	            case 2:
	                // Logika untuk menu Detail Class
	                break;
	            case 3:
	                System.out.println("Keluar dari menu My Class");
	                break;
	            default:
	                System.out.println("Pilihan tidak valid");
	                break;
	        }
	}
	
	public Long getIdClazz(int index) throws SQLException {
		final List<Clazz> clazzez= clazzService.getByInstructor(currentIdUser);
		final Long id = clazzez.get(index - 1).getId();
		return id;
	}
	public String getNameClazz(int index) throws SQLException {
		final List<Clazz> clazzez= clazzService.getByInstructor(currentIdUser);
		final String name = clazzez.get(index - 1).getClassLearning();
		return name;
	}
	
	public void setCurrentUser(Long id) {
		this.currentIdUser = id;
	}
}
