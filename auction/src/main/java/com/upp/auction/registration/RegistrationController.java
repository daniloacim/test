package com.upp.auction.registration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.upp.auction.category.Category;
import com.upp.auction.category.CategoryService;
import com.upp.auction.firm.Firm;
import com.upp.auction.firm.FirmService;
import com.upp.auction.requests.OrderRequest;
import com.upp.auction.requests.RegistrationFirmRequest;
import com.upp.auction.requests.RegistrationRequest;
import com.upp.auction.response.RegistrationResponse;
import com.upp.auction.user.EnumRole;
import com.upp.auction.user.User;
import com.upp.auction.user.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/registration")
public class RegistrationController {

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	RuntimeService runtimeService;

	@Autowired
	TaskService taskService;

	@Autowired
	RepositoryService repositoryService;

	@Autowired
	UserService userService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	FirmService firmService;
	
	@Autowired
	FormService formService;	
	
	@Autowired
	IdentityService identityService;
//	@PostMapping
//	public ResponseEntity<RegistrationResponse> createUser(@RequestBody RegistrationRequest request) {
//		EnumRole role = EnumRole.ROLE_USER;
//		if (request.getRole() == 2)
//			role = EnumRole.ROLE_FIRM;
//		User user = new User(request.getUsername(), encoder.encode(request.getPassword()), request.getEmail(),
//				request.getFirstName(), request.getLastName(), request.getAddress(), request.getPlace(),
//				request.getZipCode(), role, UUID.randomUUID().toString(), false);
//		GeoApiContext context = new GeoApiContext();
//		context.setApiKey("AIzaSyASF6y07zHHdxEu3TANs2q6-ZGkh8UvKYk");
//		GeocodingResult[] results;
//		try {
//			results = GeocodingApi.geocode(context, user.getCity()).await();
//			System.out.println("Grad: " + user.getCity());
//			//Gson gson = new GsonBuilder().setPrettyPrinting().create();
//			System.out.println("Longitude: " + (long)results[0].geometry.location.lng);
//			user.setLongitude((long) results[0].geometry.location.lng);
//			System.out.println("Latitude: " + (long) results[0].geometry.location.lat);
//
//			user.setLatitude((long) results[0].geometry.location.lat);
//			// u.setLatitude(Long.parseLong(gson.toJson(results[0].geometry.location.lat)));
//			// u.setLongitude(Long.parseLong(gson.toJson(results[0].geometry.location.lng)))'
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		User savedUser = userService.save(user);
//
//		String taskId = getTask();
//		Task task = taskService.createTaskQuery().active().taskId(taskId).singleResult();
//		HashMap<String, Object> variables = (HashMap<String, Object>) runtimeService
//				.getVariables(task.getProcessInstanceId());
//		variables.put("user", savedUser);
//		taskService.complete(taskId, variables);
//		RegistrationResponse response = new RegistrationResponse();
//		if (role.equals(EnumRole.ROLE_FIRM)) {
//			Task nextTask = taskService.createTaskQuery().active().list()
//					.get(taskService.createTaskQuery().active().list().size() - 1);
//			Map<String, Object> taskMap = new HashMap<String, Object>();
//			taskMap.put("taskId", nextTask.getId());
//			taskMap.put("name", nextTask.getName());
//			response.setTaskMap(taskMap);
//			response.setUserId(savedUser.getId());
//		}
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}

	@PostMapping
	public ResponseEntity<RegistrationResponse> newRegistration(@RequestBody RegistrationRequest request) {
		ProcessDefinition processDefiniton = repositoryService.createProcessDefinitionQuery().processDefinitionKey("registration").latestVersion().singleResult();
		HashMap<String, Object> result = new HashMap<String, Object>();
		Map<String,String> map = createMap(request);
		if(request.getRole() == 1) {
			map.put("role","ROLE_USER");
			result = null;
		}
		else {
			map.put("role","ROLE_FIRM");
		}
		formService.submitStartFormData(processDefiniton.getId(), map);
		RegistrationResponse response = new RegistrationResponse();
		User user = userService.findOneByUsername(request.getUsername());
		response.setTaskMap(result);
		response.setUserId(user.getId());

		return new ResponseEntity<>(response,HttpStatus.OK);

	}
	private Map<String,String> createMap(RegistrationRequest request){
		ObjectMapper mapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = mapper.convertValue(request, Map.class);
		Map<String, String> stringMap = new HashMap<String, String>();
		for(String strKey: map.keySet())
		{
			
			if(strKey.equals("serviceDeadline") || strKey.equals("offersDeadline")) {
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(Long.valueOf((Long) map.get(strKey)));

				map.put(strKey, formatter.format(calendar.getTime()));
			}
			stringMap.put(strKey, String.valueOf(map.get(strKey)));
		}
		return stringMap;

	}	
//	@PostMapping("/firm")
//	public ResponseEntity<String> createFirm(@RequestBody RegistrationFirmRequest request) {
//		if(request.getCategory() == null || request.getDistanceArea() == null || request.getTaskId() == null || request.getUserId() == 0) {
//			return new ResponseEntity<String>("Bad request", HttpStatus.BAD_REQUEST);
//
//		}
//		User user = userService.findOne(request.getUserId());
//		Category category = categoryService.findOne(request.getCategory());
//		Firm firm = new Firm();
//		firm.setCategory(category);
//		firm.setDistanceArea(request.getDistanceArea());
//		firm.setUser(user);
//		firm = firmService.save(firm);
//		Task task = taskService.createTaskQuery().active().taskId(request.getTaskId()).singleResult();
//		HashMap<String, Object> variables = (HashMap<String, Object>) runtimeService.getVariables(task.getProcessInstanceId());
//		variables.put("user", user);
//		System.out.println(variables);
//		taskService.complete(request.getTaskId(), variables);
//		return new ResponseEntity<String>("Firm success added", HttpStatus.CREATED);
//	}

	@PostMapping("/firm")
	public ResponseEntity<String> newCreateFirm(@RequestBody RegistrationFirmRequest request) {
		User user = userService.findOne(request.getUserId());
		Map<String, String> map = new HashMap<String, String>();
		map.put("distanceArea", request.getDistanceArea().toString());
		map.put("category", request.getCategory().toString());
		List<Task> taskList = taskService.createTaskQuery().taskAssignee(user.getUsername()).list();
		formService.submitTaskFormData(taskList.get(0).getId(), map);
		return new ResponseEntity<String>("Firm success added", HttpStatus.CREATED);
	}
	
	@GetMapping("/confirm/{userId}/{taskId}")
	public RedirectView confirmRegistration(@PathVariable String userId, @PathVariable String taskId) {
		Execution execution = runtimeService.createExecutionQuery().processInstanceId(taskId)
				.signalEventSubscriptionName("activateUser").singleResult();
		runtimeService.signalEventReceived("activateUser", execution.getId());

		System.out.println(userId);
		return new RedirectView("http://localhost:4200");
	}
	
	private String getTask() {
		runtimeService.startProcessInstanceByKey("registratonProcess");
		Task task = taskService.createTaskQuery().active().list()
				.get(taskService.createTaskQuery().active().list().size() - 1);

		return task.getId();
	}
}
