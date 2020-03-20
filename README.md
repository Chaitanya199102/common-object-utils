# REST-Request-Transformation
The following projects helps as an adapter to transform request from one form to another to make api calls and transforms response back too.
Adapter Pattern: https://en.wikipedia.org/wiki/Adapter_pattern.
Developed using Java Reflection and JSON mapping of source and destination

Run Steps:
mvn clean install (or) import as spring boot starter project and run the Application.java class

The following is the way in which mapping is stored:
File: src/main/resources/json/mapping.json

	{
	   "mapping": {	
		"request01_to_request02": {		
			"source_qualified_name": "com.request.transformation.model.Request01",			
			"destination_qualified_name": "com.request.transformation.model.Request02",			
			"fields_mapping":{			
				"name": "name",				
				"id": "identity",				
				"address": "addressCorrespondense",				
				"anything": "something",				
				"comments": "comments"				
			}			
		}		
	     }
	}


## Nested Mapping
For nested objects mapping can be as follows, mapping.json example for the same will be below the explaination

1 . Mapping a property from source to a property in nested json object of destination
	example: "consentInfo" : "additionalDetails.consentInformation"
	
2 . Mapping a property from source to a property a property in an array of destination object
	example: "messageCode": "description[0].code"
	
3 . Mapping array of objects of one kind in source to array of objects of other kind in destination
	example: "additionalDescriptions" : "additionalDesc@additionalDescription_to_additionalDesc"

	{
	   "mapping": {
		"request01_to_request02": {
			"source_qualified_name": "com.request.transformation.model.Request01",
			"destination_qualified_name": "com.request.transformation.model.Request02",
			"fields_mapping": {
				...
				"information" : "details@information_to_details" , //case1
				"message" : "description[0].message", //case2
				"messageCode": "description[0].code",
				"additionalDescriptions" : "additionalDesc@additionalDescription_to_additionalDesc" //case3
			}
		},
		"information_to_details": { //nested objects mapping should be maintained separately
			"source_qualified_name": "com.request.transformation.model.Information",
			"destination_qualified_name": "com.request.transformation.model.Details",
			"fields_mapping": {
				"information": "details",
				"informationCount": "detailsCount",
				"consentInfo" : "additionalDetails.consentInformation",
				"consentControllerInfo" : "additionalDetails.consentController"
			}
		},
		"additionalDescription_to_additionalDesc" : {
			"source_qualified_name": "com.request.transformation.model.AdditionalDescription",
			"destination_qualified_name": "com.request.transformation.model.AdditionalDescription2",
			"fields_mapping": {
				"code": "code",
				"message": "message"
			}
		}
	   }
     }

## Updates
UPCOMING CHANGES: Fields mentioned in the mapping classes will be verified automatically during application statrt.
BUGS: Exception handling is missing, working on it.

Suggestion and comments are welcome. Please write to chaitanya.ankam@gmail.com
