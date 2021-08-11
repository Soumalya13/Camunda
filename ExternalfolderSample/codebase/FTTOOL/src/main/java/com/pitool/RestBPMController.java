package com.pitool;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestBPMController {
	
	@Autowired
	private  RuntimeService runtimeService;
	
	@Value("${config.url}")
	private String values;
	
	
	@Autowired
	private  ProcessEngine processEngine;
	@RequestMapping(value="/new")
	public void hello() {
		System.out.println("hello"+ values);
		ProcessInstance instance = runtimeService.startProcessInstanceByKey("Process_0j78ukv");
		System.out.println("Process end:." + instance.getId());
		//processEngine.getFormService().
	    if (instance.isEnded()) {
	    	System.out.println("Process end:." + instance.getId());
	    }
	}

}
