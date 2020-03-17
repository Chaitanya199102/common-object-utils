package com.request.transformation.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.request.transformation.model.Request01;
import com.request.transformation.model.Request02;
import com.request.transformation.util.MappingUtil;

@RestController
public class TransformationController {
	
	@Autowired
	private MappingUtil mappingUtil;
	
	private Logger logger = LoggerFactory.getLogger(TransformationController.class);

	@GetMapping("/")
	public ResponseEntity<?> transformAndCallApi(){
		//sample static request for testing, actually should come from requestBody
		Request01 reuqest01 = new Request01();
		reuqest01.setId("001");
		reuqest01.setAddress("White House");
		reuqest01.setName("Donal Trump");
		reuqest01.setAnything("He is calling emergency on Coronavirus");
		reuqest01.setCount(1);
		reuqest01.setAnotherCountAsString("11");
		reuqest01.setOneMoreCount("129");
		
		List<String> comments = new ArrayList<>();
		comments.add("Wow! he just woke up");
		comments.add("he handled it");
		reuqest01.setComments(comments);
		
		logger.info("calling in transformation on request01 {} ", reuqest01);		
		//Request02 request02 = (Request02) mappingUtil.transform(reuqest01, "request01_to_request02");
		Request02 request02 = (Request02) mappingUtil.transformUsingJSON(reuqest01, "request01_to_request02");
		
		logger.info("transformation done, request01->request02 {} ", request02);	
		return ResponseEntity.ok(request02);
	}
}
