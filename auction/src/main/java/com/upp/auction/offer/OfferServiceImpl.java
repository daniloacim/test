package com.upp.auction.offer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.upp.auction.category.CategoryService;
import com.upp.auction.firm.Firm;
import com.upp.auction.firm.FirmService;
import com.upp.auction.order.OrderS;
import com.upp.auction.user.UserService;

@Service
@Transactional
public class OfferServiceImpl {

	
	@Autowired
	OfferRepository offerRepository;
	
	
	@Autowired
	RuntimeService runtimeService;

	@Autowired
	TaskService taskService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	UserService userService;	
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	FirmService firmService;
	
	public Offer save(Long price,Date deadline,Firm firm,OrderS order,String instanceId) {

		System.out.println("Process instance id: " + instanceId);
		Offer offer = new Offer();
		offer.setDeadline(deadline);
		offer.setFirm(firm);
		offer.setOrder(order);
		offer.setPrice(price);
		offerRepository.save(offer);
		
		return offer;
		
	}
	
	public void notifyLessOffers(String processId,OrderS order) {
		System.out.println("Nije ispunjen limit...sending mail...");
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);

			helper.setFrom("auctionupp@gmail.com");
			helper.setTo(order.getUser().getEmail());
			helper.setSubject("Service request ");
			helper.setText("<div style = 'border:1px solid gray;padding:20px;font-size:20px;'>Dear "+order.getUser().getFirstName()+ " "
					+ "Recieved number of offers is smaller than you requested. Please login  your account to make some action."
					+ "<div><a href='https://localhost:4200'>Home page</a></div></div>",true);
			mailSender.send(message);
			System.out.println("Mail sent.");
		} catch (MessagingException e) {
			e.printStackTrace();
		}		
	}
	public void notifyZeroOffers(String processId,OrderS order) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);

			helper.setFrom("auctionupp@gmail.com");
			helper.setTo(order.getUser().getEmail());
			helper.setSubject("Service request ");
			helper.setText("<div style = 'border:1px solid gray;padding:20px;font-size:20px;'>Dear "+order.getUser().getFirstName()+ " "
					+ "There is no received offers for your service request.. Please login  your account to make some action."
					+ "<div><a href='https://localhost:4200'>Home page</a></div></div>",true);
			mailSender.send(message);
			System.out.println("Mail sent.");
		} catch (MessagingException e) {
			e.printStackTrace();
		}	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void order(List<Offer> offers) {

		Collections.sort(offers, new Comparator() {

			public int compare(Object o1, Object o2) {

				Long x1 = ((Offer) o1).getPrice();
				Long x2 = ((Offer) o2).getPrice();
				int sComp = x1.compareTo(x2);

				if (sComp != 0) {
					return sComp;
				} else {
					Date d1 = ((Offer) o1).getDeadline();
					Date d2 = ((Offer) o2).getDeadline();
					return d1.compareTo(d2);
				}
			}
		});
	}
	
	
	public List<Offer> rangOffers(List<Offer> offers){
		order(offers);
		return offers;
	}
	
	public OrderS createNewList(OrderS order,List<Offer> offers) {
		return order;
	}
	
	public Firm requestAdditionalInfo(Long firmId) {
		
		System.out.println("Requesting additional info..Sending mail...");
		Firm firm = firmService.findOne(firmId);
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);

			helper.setFrom("auctionupp@gmail.com");
			helper.setTo(firm.getUser().getEmail());
			helper.setSubject("Service request ");
			helper.setText("<div style = 'border:1px solid gray;padding:20px;font-size:20px;'>Dear "+firm.getUser().getFirstName()+ " "
					+ "Client requested additional info. Please login  your account to make some action."
					+ "<div><a href='https://localhost:4200'>Home page</a></div></div>",true);
			mailSender.send(message);
			System.out.println("Mail sent.");
		} catch (MessagingException e) {
			e.printStackTrace();
		}	
		return firm;
	}
	
	public void forwardAdditionalInfo(String additionalInfo) {
		System.out.println("Forwarding additional info");
	}
	
	public String acceptAfterAdditionInfo(Firm firmAdditionalInfo) {
		
		return "accept";
	}
	
	public Firm notifyAccepted(Long firmId) {
		
		System.out.println("Accepted...");
		Firm firm = firmService.findOne(firmId);
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);

			helper.setFrom("auctionupp@gmail.com");
			helper.setTo(firm.getUser().getEmail());
			helper.setSubject("Service request ");
			helper.setText("<div style = 'border:1px solid gray;padding:20px;font-size:20px;'>Dear "+firm.getUser().getFirstName()+ " "
					+ "Client requested additional info. Please login  your account to make some action."
					+ "<div><a href='https://localhost:4200'>Home page</a></div></div>",true);
			mailSender.send(message);
			System.out.println("Mail sent.");
		} catch (MessagingException e) {
			e.printStackTrace();
		}		
		return firm;
	}
	
	public Long calculateRange(Long price,Date deadline,Firm firm,OrderS order,List<Offer> offers,String instanceId) {
		order(offers);
		ArrayList<Offer> tempOffers = new ArrayList<>();
		Offer tempOffer = new Offer();
		tempOffer.setDeadline(deadline);
		tempOffer.setPrice(price);
		tempOffers.add(tempOffer);
		tempOffer.setFirm(firm);
		for(Offer o : offers) {
			tempOffers.add(o);
		}
		order(tempOffers);
		Long result = (long) 0;
		for(int i =0; i < tempOffers.size();i++) {
			if(tempOffers.get(i).getFirm().getId().equals(firm.getId())) {
				result = (long) i+1;
				break;
			}
		}
		System.out.println("Usao u racunnje ranga");
		return result;
		
	}
}
