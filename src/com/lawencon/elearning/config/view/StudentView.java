package com.lawencon.elearning.config.view;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.elearning.model.ClassEnrollment;
import com.lawencon.elearning.model.Clazz;
import com.lawencon.elearning.service.ClassEnrollmentService;
import com.lawencon.elearning.service.ClazzService;
import com.lawencon.elearning.service.UserService;
import com.lawencon.elearning.service.impl.ClassEnrollmentServiceImpl;
import com.lawencon.elearning.service.impl.ClazzServiceImpl;
import com.lawencon.elearning.service.impl.UserServiceImpl;
import com.lawencon.elearning.util.ScannerUtil;

public class StudentView {
	private final UserService userService;
	private final ClazzService clazzService;
	private final ClassEnrollmentService classEnrollmentService;
	private Long currentIdUser = null;

	public StudentView() throws SQLException {
		this.userService = new UserServiceImpl();
		this.clazzService = new ClazzServiceImpl();
		this.classEnrollmentService = new ClassEnrollmentServiceImpl();
	}

	public void show() throws SQLException {
		System.out.println("------- Student -------");
		System.out.println("1. Enroll Class");
		System.out.println("2. My Class");
		System.out.println("3. Keluar");

		int studentChoice = (int) ScannerUtil.getScannerNumber("Masukkan Pilihan", 0);
		switch (studentChoice) {
		case 1:
			enrollClass();
			break;
		case 2:
			viewMyClass();
			break;
		case 3:
			System.out.println("Keluar ...");
			break;
		default:
			System.out.println("Invalid choice");
			break;
		}
	}

	public void enrollClass() throws SQLException {
		System.out.println("---- Tampilan semua Kelas Tersedia-----");
		final List<Clazz> clazzez = clazzService.getAll();

		if (clazzez.size() > 0) {
			System.out.println("+===============================+");
			System.out.println("+=========  All Class  =========+");
			System.out.println("+===============================+");
			System.out.println("| Nama Pengajar           |  Kelas");
			for (int i = 0; i < clazzez.size(); i++) {
				String formattedRow = String.format("| %d. %-20s | %s", (i + 1),
						clazzez.get(i).getInstructor().getProfile().getFullName(), clazzez.get(i).getClassLearning());
				System.out.println(formattedRow);
			}
			System.out.println("---------------------------------");
			final int choiceClazz = (int) ScannerUtil.getScannerNumber("Pilih Kelas : ", clazzez.size());
			classEnrollmentService.createClassEnroll(currentIdUser, getIdClazz(choiceClazz), currentIdUser);
			System.out.println("-- Berhasil Menambah Kelas");
		} else {
			System.out.println("Belum ada kelas Tersedia,hubungin admin atau pengajar");
		}
	}
	
	public void viewMyClass() throws SQLException {
		System.out.println("---- Tampilan semua Kelas Saya ----");
		final List<ClassEnrollment> classEnrollment = classEnrollmentService.getAllByStudent(currentIdUser);

		if (classEnrollment.size() > 0) {
			System.out.println("+===============================+");
			System.out.println("+======  All Enroll Class  =====+");
			System.out.println("+===============================+");
			System.out.println("| Nama Pengajar          |  Kelas");
			for (int i = 0; i < classEnrollment.size(); i++) {
				String formattedRow = String.format("| %d. %-20s | %s", (i + 1),
						classEnrollment.get(i).getClazz().getInstructor().getProfile().getFullName(), classEnrollment.get(i).getClazz().getClassLearning());
				System.out.println(formattedRow);
			}
			System.out.println("---------------------------------");
		} else {
			System.out.println("Belum ada kelas Tersedia,silahkan tambah terlebih dahulu");
		}
	}

	public Long getIdClazz(int index) throws SQLException {
		final List<Clazz> clazzez = clazzService.getAll();
		final Long id = clazzez.get(index - 1).getId();
		return id;
	}

	public void setCurrentUser(Long id) {
		this.currentIdUser = id;
	}
}
