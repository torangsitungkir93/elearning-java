package com.lawencon.elearning.config.view;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.elearning.constant.RoleConst;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.ClazzService;
import com.lawencon.elearning.service.UserService;
import com.lawencon.elearning.service.impl.ClazzServiceImpl;
import com.lawencon.elearning.service.impl.UserServiceImpl;
import com.lawencon.elearning.util.ScannerUtil;

public class AdminView {
	private final UserService userService;
	private final ClazzService clazzService;
	private Long currentIdUser=null;
	
	public AdminView() throws SQLException {
		this.userService = new UserServiceImpl();
		this.clazzService = new ClazzServiceImpl();
	}

	public void show() throws SQLException {
		System.out.println("------- Admin -------");
        System.out.println("1. Make Class ");
        System.out.println("2. task2");
        System.out.println("3. Keluar");

        int adminChoice = (int) ScannerUtil.getScannerNumber("Masukkan Pilihan : ", 0);
        switch (adminChoice) {
            case 1:
                createClass();
                break;
            case 2:
//                manageUsers();
                break;
            case 3:
                System.out.println("Kembali Ke Menu Login");
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
	}
	
	public void createClass() throws SQLException {
		System.out.println("------- Buat Kelas Baru -------");

		final String nameClass = ScannerUtil.getScannerString("Nama Kelas : ");
		final String descClass = ScannerUtil.getScannerString("Deskripsi Kelas : ");
		final String fileName = ScannerUtil.getScannerString("Masukkan Foto Kelas (gambar.jpg): ");
		
		final List<User> users = userService.getByRoleCode(RoleConst.INSTRUCTOR.getRoleCode());
		int counterU = 0;
		System.out.println("+===============================+");
		System.out.println("+=====  Daftar Instructor  =====+");
		System.out.println("+===============================+");

		for (User u : users) {
			System.out.println((counterU + 1) + ". " + u.getProfile().getFullName());
			++counterU;
		}

		final int instructorChoice = (int) ScannerUtil.getScannerNumber("Pilih Instructor : ", counterU);
		clazzService.createClazz(nameClass, descClass, getIdInstructor(instructorChoice), fileName, currentIdUser);
	}
	
	public Long getIdInstructor(int index) throws SQLException {
		final List<User> users = userService.getByRoleCode(RoleConst.INSTRUCTOR.getRoleCode());
		final Long id = users.get(index - 1).getId();
		return id;
	}
	
	public void setCurrentUser(Long id) {
		this.currentIdUser = id;
	}
}
