package com.upp.auction.registration;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.upp.auction.category.Category;
import com.upp.auction.category.CategoryService;
import com.upp.auction.firm.Firm;
import com.upp.auction.firm.FirmService;
import com.upp.auction.user.EnumRole;
import com.upp.auction.user.User;
import com.upp.auction.user.UserService;

@Component
public class RegistrationService {

	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	UserService userService;
	
	@Autowired
	FirmService firmService;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	private CategoryService categoryService;
	
	
	@Autowired
	IdentityService identityService;
	public void sendMail(User user,String task) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);

			helper.setFrom("uppauctionmain@gmail.com");
			helper.setTo(user.getEmail());
			helper.setSubject("Registratcija");
			helper.setText("Please click on link to confirm your registration"
					+ ""
					+ " http://localhost:8081/registration/confirm/"+user.getConfirmationCode()+"/"+task);
			mailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Mail sent");
	}
	
	public User save(String firstName,String lastName,String email,String username,String password,
			String address,String place,Long zipCode,EnumRole role) {
		User user = new User(username, encoder.encode(password), email,
				firstName, lastName, address, place,
				zipCode, role, UUID.randomUUID().toString(), false);
		GeoApiContext context = new GeoApiContext();
		context.setApiKey("AIzaSyASF6y07zHHdxEu3TANs2q6-ZGkh8UvKYk");
		GeocodingResult[] results;
		try {
			results = GeocodingApi.geocode(context, user.getCity()).await();
			System.out.println("Grad: " + user.getCity());
			//Gson gson = new GsonBuilder().setPrettyPrinting().create();
			System.out.println("Longitude: " + (long)results[0].geometry.location.lng);
			user.setLongitude((long) results[0].geometry.location.lng);
			System.out.println("Latitude: " + (long) results[0].geometry.location.lat);

			user.setLatitude((long) results[0].geometry.location.lat);
			// u.setLatitude(Long.parseLong(gson.toJson(results[0].geometry.location.lat)));
			// u.setLongitude(Long.parseLong(gson.toJson(results[0].geometry.location.lng)))'
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		userService.save(user);
		createMembership(user, user.getRole().toString());

		return user;
		}
	
	public Firm saveFirm(Long category,Long distanceArea,User user) {
		
		Category cat = categoryService.findOne(category);
		Firm firm = new Firm();
		firm.setCategory(cat);
		firm.setDistanceArea(distanceArea.intValue());
		firm.setUser(user);
		firm = firmService.save(firm);
		return firm;
	}
	
	public void activateUser(Long id) {
		User user = userService.findOne(id);
		user.setConfirmed(true);
		userService.save(user);
		//createMembership(user, user.getRole().toString());
		System.out.println("Aktivirao");

	}

	public void deleteUser(Long id) {
		Firm firm = firmService.findByUserId(id);
		if(firm != null) {
			firmService.delete(firm.getId());
		}else {
			userService.delete(id);
		}
		System.out.println("Obrisao");
	}
	private void createMembership(User u,String role) {
		org.activiti.engine.identity.User user1 = identityService.newUser(u.getUsername());
        user1.setPassword(u.getPassword());
        user1.setEmail(u.getEmail());
        user1.setFirstName(u.getFirstName());
        user1.setLastName(u.getLastName());	            
        identityService.saveUser(user1);
        identityService.createMembership(user1.getId(),role);
	}	
	
}
