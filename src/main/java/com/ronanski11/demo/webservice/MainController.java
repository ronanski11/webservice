package com.ronanski11.demo.webservice;

import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ronanski11.demo.webservice.interfaces.EmailSenderService;
import com.ronanski11.demo.webservice.interfaces.TemplateUtils;
import com.ronanski11.demo.webservice.interfaces.UserCredentialsRepository;
import com.ronanski11.demo.webservice.interfaces.UserInfoRepository;
import com.ronanski11.demo.webservice.interfaces.implementations.UserCredentialsImplementation;
import com.ronanski11.demo.webservice.model.UserCredentials;
import com.ronanski11.demo.webservice.model.UserInfo;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class MainController {

	private boolean loggedIn = false;
	private boolean setupFinished;
	private UserCredentials currentUser;
	private String verificationCode;
	private UserInfo userInfo;

	@Autowired
	public UserCredentialsRepository usrCredRepo;

	@Autowired
	private UserCredentialsImplementation usrCredService;

	@Autowired
	private UserInfoRepository userInfoRepo;

	@Autowired
	private EmailSenderService emailSenderService;

	@GetMapping()
	public String Startpage() throws FileNotFoundException {
		return "redirect:login";
	}

	@GetMapping("verify/account")
	public String enterEmail(Model model) {
		return "EnterEmail";
	}

	@PostMapping("/verify/account")
	public String SendVerificationMail(@RequestParam("Email") String email) {
		verificationCode = generateVerificationCode(); // Implement this method to generate a random code

		this.userInfo.setEmail(email);

		Map<String, String> templateData = new HashMap<>();
		templateData.put("verificationCode", verificationCode);

		String emailContent = TemplateUtils
				.loadAndProcessTemplate("src/main/resources/templates/verification_email_2.html", templateData);
		emailSenderService.sendEmail(email, "Email Verification", emailContent);

		return "redirect:code";
	}

	@GetMapping("verify/code")
	public String EnterCode() {
		return "EnterCode";
	}

	@PostMapping("verify/code")
	public String ValidateCode(Model model, @RequestParam("input-1") String i1, @RequestParam("input-2") String i2,
			@RequestParam("input-3") String i3, @RequestParam("input-4") String i4, @RequestParam("input-5") String i5,
			@RequestParam("input-6") String i6) {
		String input = i1 + i2 + i3 + i4 + i5 + i6;
		if (!input.equals(verificationCode)) {
			model.addAttribute("Error", true);
			return "EnterCode";
		}
		userInfoRepo.save(userInfo);
		setupFinished = true;
		model.addAttribute("type", "verification");
		model.addAttribute("currentUsr", this.currentUser);
		return "successpage";
	}

	private String generateVerificationCode() {
		int random_int = (int) Math.floor(Math.random() * (999999 - 100000 + 1) + 100000);
		String result = Integer.toString(random_int);
		return result;
	}

	@GetMapping("create/account")
	public String CreateAccount(Model model) {
		model.addAttribute("usrCred", new UserCredentials());
		return "CreateAccount-2";
	}

	@PostMapping("create/account")
	public String CreateAccount(Model model, @ModelAttribute("usrCred") UserCredentials usrCred,
			@RequestParam("passwordCheck") String passwordString) {
		String password = usrCred.getPassword();
		for (UserCredentials usrCreds : usrCredRepo.findAll()) {
			if (usrCreds.getUsername().equals(usrCred.getUsername())) {
				model.addAttribute("usrCred", usrCred);
				model.addAttribute("errorType", "username");
				return "CreateAccount-2";
			}
		}
		if (password.equals(passwordString)) {
			password = hashPassword(password);
			usrCred.setPassword(password);
			this.usrCredRepo.save(usrCred);
		} else {
			model.addAttribute("usrCred", usrCred);
			model.addAttribute("errorType", "passwords");
			return "CreateAccount-2";
		}
		/* createStats(usrCred); */
		this.loggedIn = true;
		this.currentUser = usrCred;
		this.setupFinished = false;
		model.addAttribute("type", "createAccount");
		return "successpage";
	}

	@GetMapping("login")
	public String LoginPage(Model model, UserCredentials usrCred) {
		if (!loggedIn) {
			model.addAttribute("usrCred", new UserCredentials());
			return "LoginPage";
		} else {
			return "redirect:Homepage";
		}
	}

	@PostMapping("/login")
	public String addUser(@Valid UserCredentials usrCred, BindingResult result, Model model) {
		model.addAttribute("usrCred", usrCred);
		if (result.hasErrors()) {
			return "LoginPage";
		}
		if (checkCredentials(usrCredRepo, usrCred)) {
			usrCred.setPassword(hashPassword(usrCred.getPassword()));
			this.currentUser = usrCred;
			this.loggedIn = true;
			if (usrCredService.findAllInfoById(this.currentUser.getUsername()) == null) {
				this.setupFinished = false;
			} else {
				this.setupFinished = true;
				this.userInfo = usrCredService.findAllInfoById(this.currentUser.getUsername());
			}
			model.addAttribute("type", "login");
			return "successpage";
		} else {
			model.addAttribute("Error", true);
			return "LoginPage";
		}

	}

	@GetMapping("/users/{username}")
	public String showUserInformation(Model model) throws FileNotFoundException {
		if (loggedIn) {
			model.addAttribute("currentUsr", this.currentUser);
			if (setupFinished == true) {
				model.addAttribute("userInfo", usrCredService.findAllInfoById(this.currentUser.getUsername()));
			}
			model.addAttribute("setupFinished", setupFinished);
			return "Userpage";
		} else {
			return "redirect:../login";
		}
	}

	@GetMapping("Homepage")
	public String homepage(Model model) {
		if (loggedIn) {
			model.addAttribute("currentUsr", this.currentUser);
			return "Homepage";
		} else {
			return "redirect:login";
		}
	}

	@GetMapping("users/{username}/finishsetup")
	public String finishSetup(Model model) {
		if (loggedIn) {
			model.addAttribute("currentUsr", this.currentUser);
			if (this.setupFinished == true) {
				model.addAttribute("userInfo", usrCredService.findAllInfoById(this.currentUser.getUsername()));
			} else {
				UserInfo userInfo = new UserInfo();
				userInfo.setUsername(this.currentUser.getUsername());
				userInfo.setEmail("ronan.coughlan@roche.com");
				model.addAttribute("userInfo", userInfo);
			}
			model.addAttribute("setupFinished", setupFinished);
			return "editInfo";
		} else {
			return "redirect:../../login";
		}
	}

	@PostMapping("users/{username}/finishsetup")
	public String finishSetup(@Valid UserInfo userInfo, BindingResult result, Model model)
			throws FileNotFoundException {
		if (!loggedIn) {
			return "redirect:../../Homepage";
		}
		if (result.hasErrors()) {
			return "editInfo";
		} else {
			userInfo.setUsername(this.currentUser.getUsername());
			this.userInfo = userInfo;
			return "redirect:/verify/account";
		}
	}

	@GetMapping("users/{username}/delete")
	public String confirmDelete(Model model) {
		model.addAttribute("currentUsr", this.currentUser);
		return "ConfirmDelete";
	}

	@PostMapping("users/{username}/delete")
	public String deleteAccount() {
		try {
			userInfoRepo.deleteById(this.currentUser.getUsername());
		} catch (EmptyResultDataAccessException e) {
			System.out.println("No user info found, skipping...");
		}
		usrCredRepo.deleteById(this.currentUser.getUsername());
		loggedIn = false;
		setupFinished = false;
		currentUser = null;
		return "DeleteSuccess";
	}

	@GetMapping("index")
	public String tesst(Model model) {
		model.addAttribute("currentUsr", this.currentUser);
		model.addAttribute("setupFinished", this.setupFinished);
		if (this.setupFinished) {
			model.addAttribute("UserInfo", usrCredService.findAllInfoById(this.currentUser.getUsername()));
		}
		model.addAttribute("Errortype", null);
		return "index";
	}
	
	@PostMapping("updateUserInfo")
	public String updateUserInfo(@Valid UserInfo userInfo, BindingResult result, Model model) {
	    if (!loggedIn) {
	        return "redirect:../../Homepage";
	    }

	    if (result.hasErrors()) {
	        model.addAttribute("currentUsr", this.currentUser);
	        model.addAttribute("setupFinished", this.setupFinished);
	        if (this.setupFinished) {
	            model.addAttribute("UserInfo", usrCredService.findAllInfoById(this.currentUser.getUsername()));
	        }
	        model.addAttribute("Errortype", "userInfoError");
	        return "index";
	    } else {
	        userInfo.setUsername(this.currentUser.getUsername());
	        this.userInfo = userInfo;
	        userInfoRepo.save(userInfo);
	        return "redirect:index";
	    }
	}

	@PostMapping("updatePassword")
	public String updatePassword(@RequestParam("oldPassword") String oldPassword,
	                              @RequestParam("newPassword1") String newPassword1,
	                              @RequestParam("newPassword2") String newPassword2,
	                              Model model) {
	    if (!loggedIn) {
	        return "redirect:../../Homepage";
	    }

	    if (!oldPassword.equals(null)) {
	        if (hashPassword(oldPassword).equals(this.currentUser.getPassword())) {
	            if (newPassword1.equals(newPassword2)) {
	                this.currentUser.setPassword(hashPassword(newPassword1));
	                usrCredRepo.save(this.currentUser);
	                return "redirect:Homepage";
	            } else {
	                model.addAttribute("currentUsr", this.currentUser);
	                model.addAttribute("setupFinished", this.setupFinished);
	                if (this.setupFinished) {
	                    model.addAttribute("UserInfo", usrCredService.findAllInfoById(this.currentUser.getUsername()));
	                }
	                model.addAttribute("Errortype", "passwordMatchError");
	                return "index";
	            }
	        } else {
	            model.addAttribute("currentUsr", this.currentUser);
	            model.addAttribute("setupFinished", this.setupFinished);
	            if (this.setupFinished) {
	                model.addAttribute("UserInfo", usrCredService.findAllInfoById(this.currentUser.getUsername()));
	            }
	            model.addAttribute("Errortype", "oldPasswordError");
	            return "index";
	        }
	    } else {
	        return "index";
	    }
	}


	@GetMapping("test")
	public String test(Model model) {
		model.addAttribute("type", "verification");
		return "successpage";
	}

	@GetMapping("users/{username}/logout")
	public String confirmLogout(Model model) {
		model.addAttribute("currentUsr", this.currentUser);
		return "ConfirmLogout";
	}

	@PostMapping("users/{username}/logout")
	public String confirmLogout() {
		loggedIn = false;
		setupFinished = false;
		currentUser = null;
		return "redirect:../../";
	}

	public static boolean checkCredentials(UserCredentialsRepository usrCredRepo, UserCredentials usrCred) {
		String username = usrCred.getUsername();
		UserCredentials usrCredFromRepo = new UserCredentials();
		try {
			Optional<UserCredentials> optionalUserCredentials = usrCredRepo.findById(username);
			usrCredFromRepo = optionalUserCredentials.get();
		} catch (NoSuchElementException e) {
			return false;
		}
		String password = usrCred.getPassword();
		password = hashPassword(password).strip();
		String passwordRepo = usrCredFromRepo.getPassword().strip();
		return passwordRepo.equals(password);
	}

	public boolean SetupFinishedCheck(String username) throws FileNotFoundException {
		UserInfo userInfo = new UserInfo();
		userInfo = usrCredService.findAllInfoById(username);
		if (userInfo.getFirstname() == null) {
			return false;
		} else {
			return true;
		}
	}

	public static String byteArrayToHex(byte[] a) {
		StringBuilder sb = new StringBuilder(a.length * 2);
		for (byte b : a)
			sb.append(String.format("%02x", b));
		return sb.toString();
	}

	public static String hashPassword(String password) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(password.getBytes());
			return new String(byteArrayToHex(messageDigest.digest()));
		} catch (NoSuchAlgorithmException e) {
			// This should never happen since SHA-256 is a standard algorithm
			throw new RuntimeException(e);
		}
	}

}
