package com.request.transformation.util;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
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
	
	private static final String FIELDS_MAPPING = "fields_mapping";
	private static final String NESTED_MAPPING_REF = "@";
	private static final String ARRAY_PATTERN_WITH_PROPERTY = "\\[(.*?)\\].";
	
	private Logger logger = LoggerFactory.getLogger(MappingUtil.class);
	
	public Object map(Object source, String mappingKey) {
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
			 
			 JSONObject sourceJSON = convertObjectToJSON(source);
			 JSONObject destinationJSON = mapSourceToDestination(sourceJSON, mappingNode, mappingKey);
			 destination = convertJSONToObject(destinationJSON, destinationClass);
			 
		} catch (Exception e) {
			logger.error("error transformaing ", e);
		}
		return destination;
	}
	
	
	public JSONObject mapSourceToDestination(JSONObject source, JsonNode mappingNode, String mappingKey) {
		JSONObject destination = new JSONObject();
		JsonNode fieldsMapping = mappingNode.get(mappingKey).get(FIELDS_MAPPING);
		Iterator<String> sourceFieldsIterator = fieldsMapping.fieldNames();
		
		while(sourceFieldsIterator.hasNext()){
			String sourceFieldName = sourceFieldsIterator.next();
			String destinationFieldName = fieldsMapping.get(sourceFieldName).asText();
			
			if(destinationFieldName.contains(NESTED_MAPPING_REF)) {
				String[] mappingNameArr = destinationFieldName.split(NESTED_MAPPING_REF);
				String nestedFieldName = mappingNameArr[0];
				String nestedMappingKey = mappingNameArr[1];
				
				JSONObject nestedSource = null;
				JSONArray nestedSourceArray = null;
				Object json = source.get(sourceFieldName);
				
				if (json instanceof JSONArray)
					nestedSourceArray = (JSONArray) json;
				else if (json instanceof JSONObject)
					nestedSource = (JSONObject) json;
				
				if(nestedSource!=null) {
					JSONObject nestedDestnation = mapSourceToDestination(nestedSource, mappingNode, nestedMappingKey);
					setValueToDestination(destination, nestedFieldName, nestedDestnation);
				} else if(nestedSourceArray!=null) {
					JSONArray nestedDestnationArray = new JSONArray();
					for(int i=0;i<nestedSourceArray.length();i++) {
						JSONObject sourceArrayItem = nestedSourceArray.getJSONObject(i);
						JSONObject nestedDestnation = mapSourceToDestination(sourceArrayItem, mappingNode, nestedMappingKey);
						nestedDestnationArray.put(nestedDestnation);
					}
					setValueToDestination(destination, nestedFieldName, nestedDestnationArray);
				}
			} else 
				setValueToDestination(destination, fieldsMapping.get(sourceFieldName).asText(), source.get(sourceFieldName));
		}
		
		return destination;
	}

	
	public void setValueToDestination(JSONObject destinationJSON, String destinationFieldName, Object value) {
		
		//The following is array mapping - mapping a property in source with a
		//property of object at certain index in an array of destination object
		if(destinationFieldName.contains("[")) {	
			Pattern pattern = Pattern.compile(ARRAY_PATTERN_WITH_PROPERTY);
		    Matcher matcher = pattern.matcher(destinationFieldName);
		    
		    if(matcher.find()) {		    	
		    	String arrayField = destinationFieldName.substring(0, matcher.start());
				String property = destinationFieldName.substring(matcher.end(), destinationFieldName.length());
				int index = Integer.parseInt(destinationFieldName.substring(matcher.start()+1, matcher.end()-2));
				
				JSONObject nestedObj = null;
				JSONArray nestedJSONArray = null;
				
				if(destinationJSON.has(arrayField))
					nestedJSONArray = destinationJSON.getJSONArray(arrayField);
				else {
					nestedJSONArray = new JSONArray();
					destinationJSON.put(arrayField, nestedJSONArray);
				}
					
				if(nestedJSONArray.isNull(index)) {
					nestedObj = new JSONObject();
					nestedJSONArray.put(nestedObj);					
				} else 
					nestedObj = nestedJSONArray.getJSONObject(index);
				
				nestedObj.put(property, value);
		    }
			
		} 
		
		// mapping a property in source with a property of nested object in destination
		else if(destinationFieldName.contains(".")) {		
			
			String[] fieldNameArr = destinationFieldName.split("\\.");
			String field = fieldNameArr[0];
			String property = fieldNameArr[1];
			JSONObject nestedObj = null;
			
			if(destinationJSON.has(field))
				nestedObj = destinationJSON.getJSONObject(field);
			else {
				nestedObj = new JSONObject();
				destinationJSON.put(field, nestedObj);
			}
			
			nestedObj.put(property, value);
			
		} 
		
		// mapping a property in source with destination
		else 
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
