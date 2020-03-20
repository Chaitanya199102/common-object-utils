package com.request.transformation.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.request.transformation.model.AdditionalDescription;
import com.request.transformation.model.Information;
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
		
		Information information = new Information();
		information.setInformation("you are cheater trump. Mexecio did not pay for the wall");
		information.setInformationCount(1);
		information.setConsentInfo("Provided consent to bully him");
		information.setConsentControllerInfo("Joe Biden");
		reuqest01.setInformation(information);
		reuqest01.setMessage("Coronavirus is a chinese virus");
		reuqest01.setMessageCode("COVED 19");
		
		List<String> comments = new ArrayList<>();
		comments.add("Wow! he just woke up");
		comments.add("he handled it");
		reuqest01.setComments(comments);
		
		
		List<AdditionalDescription> additionalDescriptions = new ArrayList<>();
		AdditionalDescription additionalDescription = new AdditionalDescription();
		additionalDescription.setCode("CORONA VIRUS");
		additionalDescription.setMessage("Deadly virus");
		
		AdditionalDescription additionalDescription2 = new AdditionalDescription();
		additionalDescription2.setCode("SARS");
		additionalDescription2.setMessage("Deadly virus too");
		additionalDescriptions.add(additionalDescription);
		additionalDescriptions.add(additionalDescription2);
		
		reuqest01.setAdditionalDescriptions(additionalDescriptions);
		
		logger.info("calling in transformation on request01 {} ", reuqest01);		
		//Request02 request02 = (Request02) mappingUtil.transform(reuqest01, "request01_to_request02");
		Request02 request02 = (Request02) mappingUtil.map(reuqest01, "request01_to_request02");
		
		logger.info("transformation done, request01->request02 {} ", request02);	
		return ResponseEntity.ok(request02);
	}
}
