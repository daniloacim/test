package com.upp.auction.offer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upp.auction.category.CategoryService;
import com.upp.auction.order.OrderS;
import com.upp.auction.order.OrderService;
import com.upp.auction.response.OrderResponse;
import com.upp.auction.user.User;
import com.upp.auction.user.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/offers")
public class OfferController {

	
	@Autowired
	RuntimeService runtimeService;

	@Autowired
	TaskService taskService;

	@Autowired
	RepositoryService repositoryService;
		
	@Autowired
	FormService formService;	
	
	@Autowired
	OrderService orderService;	
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	UserService userService;
		
	
	@PreAuthorize("hasRole('ROLE_FIRM')")
	@PostMapping("/{taskId}")
	public Map<String,OrderResponse> save(@PathVariable String taskId,@RequestBody Map<String,String> data){
		System.out.println(taskId);
		formService.submitTaskFormData(taskId, data);
		
		
		return createMap();
	}
	@SuppressWarnings("unchecked")
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/{taskId}")
	public List<Offer> getByInstance(@PathVariable String taskId){
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();		
		Map<String,Object> variables = runtimeService.getVariables(task.getProcessInstanceId());
		List<Offer> offers =(List<Offer>) variables.get("offers");
		return offers;
	}
	public Map<String,OrderResponse> createMap() {
		Map<String,OrderResponse> result = new HashMap<String,OrderResponse>();
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		User user = userService.findOneByUsername(authentication.getName());
		
		List<Task> taskList = taskService.createTaskQuery().taskAssignee(user.getUsername()).list();
		for(Task t : taskList) {
			Map<String,Object> variables = runtimeService.getVariables(t.getProcessInstanceId());
			OrderS order = (OrderS) variables.get("order");
			String firmAdditionalInfo = (String) variables.get("additionalInfo");
			Long rang = (Long) variables.get("range");

			OrderResponse response = new OrderResponse(order.getId(), order.getCategory().getId(), order.getDescription(), order.getEstimatedValue(), order.getOffersDeadline(),
					order.getOffersLimit(), order.getServiceDeadline(),order.getUser().getFirstName(),order.getUser().getLastName(),t.getName(),firmAdditionalInfo,rang);

			result.put(t.getId(), response);
		}
		return result;
	}
}
