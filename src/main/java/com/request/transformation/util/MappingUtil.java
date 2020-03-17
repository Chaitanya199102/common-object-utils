package com.request.transformation.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Arrays;
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
			 JsonNode mappingNode = objectMapper.readTree(resource.getInputStream());
			 logger.info("mapping Node {}", mappingNode);
			 JsonNode mappingObject = mappingNode.get("mapping").get(mappingKey);
			 logger.info("mapping Object {}", mappingObject);
			 
			 //accessing fields of the source object
			 Class<?> sourceClass = Class.forName(mappingObject.get("source_qualified_name").asText());
			 Class<?> destinationClass = Class.forName(mappingObject.get("destination_qualified_name").asText());
			 Field[] allFieldsSource = sourceClass.getDeclaredFields();
			 Field[] allFieldsDestination = destinationClass.getDeclaredFields();

			 //print and check all the fields
			 logger.info("Fields in Source\n");
			 Arrays.stream(allFieldsSource).forEach(System.out::println);
			 logger.info("Fields in Destination\n");
			 Arrays.stream(allFieldsDestination).forEach(System.out::println);
			 
			 
			 //mapping from fields_mapping
			 JsonNode fieldsMapping = mappingObject.get("fields_mapping");
			 Iterator<String> sourceFieldsIterator = fieldsMapping.fieldNames();
			 
			 //creating destination object
			 destination = destinationClass.newInstance();
			 
			 //setting properties from source to destination
			 while(sourceFieldsIterator.hasNext()){
				 String sourceFieldName = sourceFieldsIterator.next();
				 Object sourceFieldValue = new PropertyDescriptor(sourceFieldName, sourceClass).getReadMethod().invoke(source);
				 
				 PropertyDescriptor destinationProperty = new PropertyDescriptor(fieldsMapping.get(sourceFieldName).asText(), destinationClass);
				 destinationProperty.getWriteMethod().invoke(destination, sourceFieldValue);
			 }
			 
		} catch (Exception e) {
			logger.error("error transformaing ", e);
		}
		return destination;
	}
	
	
	public Object transformUsingJSON(Object source, String mappingKey) {
		Object destination = null;
		Resource resource = new ClassPathResource("json/mapping.json");
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			 JsonNode mappingNode = objectMapper.readTree(resource.getInputStream());
			 logger.info("mapping Node {}", mappingNode);
			 JsonNode mappingObject = mappingNode.get("mapping").get(mappingKey);
			 logger.info("mapping Object {}", mappingObject);
			 
			 //accessing fields of the source object
			 Class<?> sourceClass = Class.forName(mappingObject.get("source_qualified_name").asText());
			 Class<?> destinationClass = Class.forName(mappingObject.get("destination_qualified_name").asText());
			 Field[] allFieldsSource = sourceClass.getDeclaredFields();
			 Field[] allFieldsDestination = destinationClass.getDeclaredFields();

			 //print and check all the fields
			 logger.info("Fields in Source\n");
			 Arrays.stream(allFieldsSource).forEach(System.out::println);
			 logger.info("Fields in Destination\n");
			 Arrays.stream(allFieldsDestination).forEach(System.out::println);
			 
			 
			 //mapping from fields_mapping
			 JsonNode fieldsMapping = mappingObject.get("fields_mapping");
			 Iterator<String> sourceFieldsIterator = fieldsMapping.fieldNames();
			 
			 //creating destination object
			 destination = destinationClass.newInstance();
			 
			 
			 JSONObject sourceJSON = convertObjectToJSON(source);
			 JSONObject destinationJSON = convertObjectToJSON(destination);
			 
			 
			 //setting properties from source to destination
			 while(sourceFieldsIterator.hasNext()){
				 String sourceFieldName = sourceFieldsIterator.next();
				 destinationJSON.put(fieldsMapping.get(sourceFieldName).asText(), 
						 sourceJSON.get(sourceFieldName));
			 }
			 
			 destination = convertJSONToObject(destinationJSON, destinationClass);
			 
		} catch (Exception e) {
			logger.error("error transformaing ", e);
		}
		return destination;
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
