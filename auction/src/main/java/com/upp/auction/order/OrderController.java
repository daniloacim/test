package com.upp.auction.order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upp.auction.category.CategoryService;
import com.upp.auction.requests.OrderRequest;
import com.upp.auction.user.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/order")
public class OrderController{

	
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
	
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<String> createRequest(@RequestBody OrderRequest request){
		ProcessDefinition processDefiniton = repositoryService.createProcessDefinitionQuery().processDefinitionKey("myProcess").latestVersion().singleResult();
		Map<String,String> map = createMap(request);
		formService.submitStartFormData(processDefiniton.getId(), map);
		System.out.println("SUCCESS");
		return new ResponseEntity<String>("TEXT",HttpStatus.CREATED);
	}
	
	@GetMapping(value="/acceptLess")
	public void acceptLess(@RequestParam String processInstance, @RequestParam  String processDefinition,@RequestParam Boolean answer) {
		System.out.println("Definition id: " + processInstance);
		System.out.println("Instance id: " + processDefinition);
		System.out.println("Answer: " + answer.toString());
	    Task task = taskService.createTaskQuery()
	    		.processDefinitionId(processDefinition)
	    	    .processInstanceId(processInstance)
	    	    .singleResult();
	    Map<String,String> map = new HashMap <String, String>();
	    map.put("answer",answer.toString());
	    formService.submitTaskFormData(task.getId(), map);
	    System.out.println("Task name: " + task.getName());
		
	}
	
	private Map<String,String> createMap(OrderRequest request){
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
	/*@GetMapping("/test")
	public Map<String,String> getTasks(){
		String username = "pasa";
		
		List<Task> taskList = taskService.createTaskQuery().taskAssignee(username).list();
		Map<String,Object> variables = runtimeService.getVariables(taskList.get(0).getProcessInstanceId());
		return new HashMap<>();
	}*/
}
