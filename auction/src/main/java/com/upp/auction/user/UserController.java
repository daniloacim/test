package com.upp.auction.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.upp.auction.firm.Firm;
import com.upp.auction.offer.Offer;
import com.upp.auction.order.OrderS;
import com.upp.auction.order.OrderService;
import com.upp.auction.response.UserTaskResponse;

@RestController
@RequestMapping(value = "/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

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

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping
	public ResponseEntity<Map<String, UserTaskResponse>> getUserTasks() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		User user = userService.findOneByUsername(authentication.getName());

		List<Task> taskList = taskService.createTaskQuery().taskAssignee(user.getUsername()).list();
		Map<String, UserTaskResponse> result = createResponseMap(taskList);
		// Map<String,Object> variables =
		// runtimeService.getVariables(taskList.get(0).getProcessInstanceId());

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/{taskId}")
	public ResponseEntity<Map<String, UserTaskResponse>> save(@PathVariable String taskId,@RequestBody Map<String,String> data){
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		User user = userService.findOneByUsername(authentication.getName());
		formService.submitTaskFormData(taskId, data);
		
		List<Task> taskList = taskService.createTaskQuery().taskAssignee(user.getUsername()).list();
		Map<String, UserTaskResponse> result = createResponseMap(taskList);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}


	private Map<String, UserTaskResponse> createResponseMap(List<Task> taskList) {
		Map<String, UserTaskResponse> result = new HashMap<String, UserTaskResponse>();

		for (Task t : taskList) {
			Map<String, Object> variables = runtimeService.getVariables(t.getProcessInstanceId());
			OrderS order = (OrderS) variables.get("order");
			String additionalInfo = (String) variables.get("additionalInfoResponse");
			Firm firmAdditionalInfo = (Firm) variables.get("firmAdditionalInfo");
			@SuppressWarnings("unchecked")
			List<Offer> offers =(List<Offer>) variables.get("offers");
			UserTaskResponse response = new UserTaskResponse(t.getName(), order.getDescription(), offers.size(), order.getOffersLimit(), order.getServiceDeadline(), order.getOffersDeadline(),additionalInfo);
			if(firmAdditionalInfo != null)
				response.setFirmAdditionalInfo(firmAdditionalInfo.getName());
			result.put(t.getId(), response);
		}
		
		return result;
	}
}
