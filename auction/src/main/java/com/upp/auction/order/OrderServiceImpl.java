package com.upp.auction.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import static java.lang.Math.toIntExact;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.upp.auction.category.Category;
import com.upp.auction.category.CategoryRepository;
import com.upp.auction.firm.Firm;
import com.upp.auction.firm.FirmRepository;
import com.upp.auction.firm.FirmService;
import com.upp.auction.offer.Offer;
import com.upp.auction.user.User;
import com.upp.auction.user.UserService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	OrderRepository repository;

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	FirmRepository firmRepository;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	UserService userService;

	@Autowired
	IdentityService identityService;
	
	@Autowired
	FirmService firmService;
	
	@Override
	public List<OrderS> findAll() {
		// TODO Auto-generated method stub
		return (List<OrderS>) repository.findAll();

	}

	@Override
	public OrderS findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.delete(id);
	}

	@Override
	public OrderS save(Long category, String description, Long estimatedValue, Date offersDeadline, Long offersLimit,
			Date serviceDeadline,String instanceId) {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		User user = userService.findOneByUsername(authentication.getName());
		Category cat = categoryRepository.findOne(category);
		OrderS order = new OrderS(cat,description,estimatedValue,offersDeadline,offersLimit,serviceDeadline);
		order.setUser(user);
		repository.save(order);
		List<Firm> firmsByCategory= firmRepository.findByCategoryId(category);
		Collections.sort(firmsByCategory, 
                (o1, o2) -> o2.getAvgRank().compareTo(o1.getAvgRank()));
		
		List<Firm> finalList = new ArrayList<Firm>();
		for(Firm f : firmsByCategory) {
			if(f.getDistanceArea()>distance(f.getUser().getLatitude(), user.getLatitude(), f.getUser().getLongitude(), user.getLongitude(), 0, 0)) {
				finalList.add(f);
				f.getOrders().add(order);
				firmRepository.save(f);
			}
		}
		
		int limit = toIntExact(order.getOffersLimit());
		ArrayList<Firm> finalSubList = (ArrayList<Firm>) finalList;
		if(limit < finalList.size())
			finalSubList= new ArrayList<Firm>(finalList.subList(0, limit));
		
		order.setFirms(finalSubList);
		runtimeService.setVariable(instanceId, "offers", new ArrayList<Offer>());
		runtimeService.setVariable(instanceId, "numOfRepeats", 0);
		return order;
	}
	
	public void notifyEmptyFimsList(OrderS order) {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);

			helper.setFrom("auctionupp@gmail.com");
			helper.setTo(order.getUser().getEmail());
			helper.setSubject("Service request ");
			helper.setText("<div style = 'border:1px solid gray;padding:20px;font-size:20px;'>Dear "+order.getUser().getFirstName()+ " "
					+ "there is no any firm that belongs to requested category of business."
					+ "<div><a href='https://localhost:4200'>Home page</a></div></div>",true);
			mailSender.send(message);
			System.out.println("Mail sent.");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public void checkByMailToContinue(OrderS order,String definitionId,String instanceid) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);

			helper.setFrom("auctionupp@gmail.com");
			helper.setTo(order.getUser().getEmail());
			helper.setSubject("Service request ");
			helper.setText("<div style='border:1px solid gray;font-size:20px;padding:20px;'>The number of firms that you entered to apply is not available."
					+ "\nCurrently available number of firms"
					+ " in category " +order.getCategory().getName()+ " is <span style='color:red'>"+ order.getFirms().size()+"</span>"
					+ ".<br/>If you want to continue with that number click this link: "
					+ " <a href='http://localhost:8081/order/acceptLess?answer=true&processInstance="+instanceid+"&processDefinition="+definitionId+"'>Accept</a>"
							+ "<br/>If you want to cancel request click this link: "
							+ "<a href='http://localhost:8081/order/acceptLess?answer=false&processInstance="+instanceid+"&processDefinition="+definitionId+"'>Cancel</a></div>",true);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public String notifyFirm(Firm firm, List<Offer> offers) {
		for(Offer o :offers) {
			if(o.getFirm().getId().equals(firm.getId())) {
				return null;
			}
		}
		String assigne = "";
		User agent = firm.getUser();
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);

			helper.setFrom("auctionupp@gmail.com");
			helper.setTo(firm.getUser().getEmail());
			helper.setSubject("Service request");
			helper.setText("<div style='border:1px solid gray;font-size:20px;padding:20px;'>"
					+ "There is service request ! Log in to your account to send an offer ! </div>",true);
			mailSender.send(message);
			org.activiti.engine.identity.User activitiUser = identityService.createUserQuery().userId(agent.getUsername()).singleResult();
			assigne = activitiUser.getId();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		
		return assigne;
	}
	public OrderS setNewDeadline(Date newOffersDeadline,OrderS order) {
		order.setOffersDeadline(newOffersDeadline);
		return order;
	}
	
	public OrderS createNewList(OrderS order,List<Offer> offers,String instanceId) {
		List<Firm> firmsByCategory= firmRepository.findByCategoryId(order.getCategory().getId());
		List<Firm> newList = new ArrayList<>();
		for(Firm f : firmsByCategory) {
			if(!contains(order.getFirms(), f.getId())) {
				newList.add(f);
			}
		}
		order.setFirms(newList);
		Integer numOfRepeats =(Integer) runtimeService.getVariable(instanceId, "numOfRepeats");
		numOfRepeats++;
		runtimeService.setVariable(instanceId, "numOfRepeats", numOfRepeats);
		runtimeService.setVariable(instanceId, "offers", new ArrayList<Offer>());
		repository.save(order);
		
		return order;
	}
	public void test(List<Offer> offers,String instanceId) {
		System.out.println(offers.size());
		runtimeService.setVariable(instanceId, "offers", offers);
	}
	
	public void test1(){
		System.out.println("Kraj proces, ne sme vise od 2 puta da se ponovi proces!");
	}
	public boolean contains(final List<Firm> list, final Long id){
	    return list.stream().filter(o -> o.getId().equals(id)).findFirst().isPresent();
	}
	
	
	public static double distance(double lat1, double lat2, double lon1,
	        double lon2, double el1, double el2) {

	    final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    double height = el1 - el2;

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return Math.sqrt(distance);
	}
	
	public void saveMarks(Long markForClient,Long markForFirm,User user,Firm firm) {
		firm.getRanks().add(markForFirm.intValue());
		firmService.save(firm);
		
		user.getRanks().add(markForClient.intValue());
		userService.save(user);
	}
	public void saveMarkForClient(Long markForClient,User user) {

		
		user.getRanks().add(markForClient.intValue());
		userService.save(user);
	}
	public void saveMarkForFirm(Long markForFirm,Firm firm) {
		firm.getRanks().add(markForFirm.intValue());
		firmService.save(firm);
	}	
}
