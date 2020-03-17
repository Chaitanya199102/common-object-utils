package com.request.transformation.util;

import java.util.Iterator;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MappingUtil {
	
	private Logger logger = LoggerFactory.getLogger(MappingUtil.class);
	
	public Object transform(Object source, String mappingKey) {
		Object destination = null;
		Resource resource = new ClassPathResource("json/mapping.json");
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			 JsonNode mappingNode = objectMapper.readTree(resource.getInputStream()).get("mapping");
			 logger.info("mapping Node {}", mappingNode);
			 JsonNode mappingObject = mappingNode.get(mappingKey);
			 logger.info("mapping Object {}", mappingObject);
			 
			 //accessing fields of the source object
			 //Class<?> sourceClass = Class.forName(mappingObject.get("source_qualified_name").asText());
			 Class<?> destinationClass = Class.forName(mappingObject.get("destination_qualified_name").asText());
			 
			 //mapping from fields_mapping
			 JsonNode fieldsMapping = mappingObject.get("fields_mapping");
			 Iterator<String> sourceFieldsIterator = fieldsMapping.fieldNames();			 
			 
			 JSONObject sourceJSON = convertObjectToJSON(source);
			 JSONObject destinationJSON = new JSONObject();			 
			 
			 //setting properties from source to destination
			 while(sourceFieldsIterator.hasNext()){
				 String sourceFieldName = sourceFieldsIterator.next();
				 mapSourceFieldToDestinationField(sourceJSON, destinationJSON, sourceFieldName, mappingNode, mappingKey);
			 }
			 
			 destination = convertJSONToObject(destinationJSON, destinationClass);
			 
		} catch (Exception e) {
			logger.error("error transformaing ", e);
		}
		return destination;
	}
	
	public void mapSourceFieldToDestinationField(JSONObject sourceJSON, JSONObject destinationJSON, String mappingFieldName, 
			JsonNode mappingNode, String mappingKey) {
		
		JsonNode fieldsMapping = mappingNode.get(mappingKey).get("fields_mapping");
		String destinationFieldName = fieldsMapping.get(mappingFieldName).asText();
		
		if(destinationFieldName.contains("@")) {
			String[] mappingNameArr = destinationFieldName.split("@");
			String nestedFieldName = mappingNameArr[0];
			String nestedMappingKey = mappingNameArr[1];
			JsonNode nestedMapping = mappingNode.get(nestedMappingKey).get("fields_mapping");
			Iterator<String> sourceFieldsIterator = nestedMapping.fieldNames();
			
			JSONObject nestedDestnationJSON = new JSONObject();
			JSONObject nestedSourceJSON = sourceJSON.optJSONObject(mappingFieldName);
			
			while(nestedSourceJSON!=null && sourceFieldsIterator.hasNext()){
				 mapSourceFieldToDestinationField(nestedSourceJSON, nestedDestnationJSON, sourceFieldsIterator.next(), mappingNode, nestedMappingKey);
			}
			
			setValueToDestination(destinationJSON, nestedFieldName, nestedDestnationJSON);
		} else {
			setValueToDestination(destinationJSON, fieldsMapping.get(mappingFieldName).asText(), sourceJSON.get(mappingFieldName));
		}
	}
	
	
	public void setValueToDestination(JSONObject destinationJSON, String destinationFieldName, Object value) {
		if(destinationFieldName.contains(".")) {
			String[] fieldNameArr = destinationFieldName.split("\\.");
			String field = fieldNameArr[0];
			String property = fieldNameArr[1];
			JSONObject nestedObj = null;
			
			if(!destinationJSON.has(field)) {
				nestedObj = new JSONObject();
				destinationJSON.put(field, nestedObj);
			} else
				nestedObj = destinationJSON.getJSONObject(field);
			
			nestedObj.put(property, value);
		} else 
			destinationJSON.put(destinationFieldName, value);
	}
	
	
	public JSONObject convertObjectToJSON(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(object);
		JSONObject json = new JSONObject(jsonString);
		return json;
	}
	
	public Object convertJSONToObject(JSONObject jsonObject, Class<?> classNameRef) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(jsonObject.toString(), classNameRef);
	}
}
