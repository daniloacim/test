package com.upp.auction;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuctionApplication.class, args);
	}
	
	@Bean
	InitializingBean usersAndGroupsInitializer(final IdentityService identityService) {

	    return new InitializingBean() {
	        public void afterPropertiesSet() throws Exception {

	        	Group group1 = identityService.newGroup("ROLE_USER");
	            group1.setName("ROLE_USER");
	            group1.setType("assignement");
	            identityService.saveGroup(group1);

	            Group group2 = identityService.newGroup("ROLE_FIRM");
	            group2.setName("ROLE_FIRM");
	            group2.setType("assignement");
	            identityService.saveGroup(group2);
	            User user1 = identityService.newUser("janko");
	            user1.setPassword("123");
	            user1.setEmail("jankoupp@gmail.com");
	            user1.setFirstName("Janko");
	            user1.setLastName("Jankovic");	            
	            identityService.saveUser(user1);
	            identityService.createMembership(user1.getId(),"ROLE_USER");

	            User user2 = identityService.newUser("marko");
	            user2.setPassword("123");
	            user2.setEmail("markoupp@gmail.com");
	            user2.setFirstName("Marko");
	            user2.setLastName("Markovic");
	            identityService.saveUser(user2);
	            identityService.createMembership(user2.getId(),"ROLE_USER"); 
	            
	            User user3 = identityService.newUser("pasa");
	            user3.setPassword("123");
	            user3.setEmail("danilo.acimovic@yahoo.com");
	            user3.setFirstName("pasa");
	            user3.setLastName("mrkalj");
	            identityService.saveUser(user3);
	            identityService.createMembership(user3.getId(),"ROLE_FIRM");
	            
	            User user4 = identityService.newUser("scepan");
	            user4.setPassword("123");
	            user4.setEmail("daniloacimovic68@gmail.com");
	            user4.setFirstName("Scepan");
	            user4.setLastName("Scekic");
	            identityService.saveUser(user4);
	            identityService.createMembership(user4.getId(),"ROLE_FIRM");       
	        }
	    };
	}
}