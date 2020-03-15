# REST-Request-Transformation
The following projects helps as an adapter to transform request from one from to another to make api calls and transforms response back too.
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
