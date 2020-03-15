package com.request.transformation.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

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
}
