package com.lawencon.elearning.config.view;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.elearning.service.UserService;
import com.lawencon.elearning.service.impl.UserServiceImpl;
import com.lawencon.elearning.util.ScannerUtil;
import com.lawencon.elearning.constant.RoleConst;
import com.lawencon.elearning.model.Role;
import com.lawencon.elearning.model.User;

public class MainView {
	private final UserService userService;

	public MainView() throws Exception {
		this.userService = new UserServiceImpl();
	}

	public void showMain() throws SQLException {
		int choice =0;
		System.out.println("========== Testing ==========");
		final List<User> users = userService.getAll();
		users.forEach(u -> {
			System.out.println("ID       : "+u.getId());
			System.out.println("Username : "+u.getEmail());
			System.out.println("Password : "+u.getRole().getId());
			System.out.println("=============================");
		});
		System.out.println("=============================");
		
		 do {
	         System.out.println("+=====================+");
	         System.out.println("|1. Login             |");
	         System.out.println("|2. Keluar            |");
	         System.out.println("+=====================+");
	         choice = (int) ScannerUtil.getScannerNumber("Masukkan pilihan Anda: ",0);

	         switch (choice) {
	             case 1:
	            	final String email = ScannerUtil.getScannerString("Masukkan Email : ");
	 				final String password = ScannerUtil.getScannerString("Masukkan Password : ");
	 				final User loggedInUser = userService.login(email, password);

	 				if (loggedInUser != null) {
	 					final Role userRole = loggedInUser.getRole();
	 					if (userRole != null) {
	 						final String currentRoleUser = userRole.getCode_role();
	 						final String currentNameUser = loggedInUser.getProfile().getFullName();
	 						Long idUser = loggedInUser.getId();
	 						if (currentRoleUser.equals(RoleConst.ADMIN.getRoleCode())) {
	 							final AdminView adminView = new AdminView();
	 							adminView.setCurrentUser(idUser);
	 							adminView.show();
	 						} else if (currentRoleUser.equals(RoleConst.INSTRUCTOR.getRoleCode())) {
	 							final InstructorView instructorView = new InstructorView();
	 							instructorView.setCurrentUser(idUser);
	 							instructorView.show();
	 						} else if (currentRoleUser.equals(RoleConst.STUDENT.getRoleCode())) {
	 							final StudentView studentView = new StudentView();
	 							studentView.setCurrentUser(idUser);
	 							studentView.show();
	 						}
	 					}else {
							System.out.println("Email atau password yang anda masukkan salah");
						}
	 				}
	                 break;
	             case 2:
	                 System.out.println("Anda memilih Keluar. Program berakhir.");
	                 break;
	             default:
	            	 System.out.println("Pilihan tidak ada,Input ulang --- ");
	         }
	     } while (choice != 2);
	}
}
