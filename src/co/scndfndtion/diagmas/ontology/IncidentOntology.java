package co.scndfndtion.diagmas.ontology;

import jade.content.onto.*;
import jade.content.schema.*;
import java.util.*;


/**
   Javadoc documentation for the file EmploymentOntology
   @author Giovanni Caire - CSELT S.p.A.
   @version $Date: 2002-07-31 17:27:34 +0200 (mer, 31 lug 2002) $ $Revision: 3315 $
 */

public class IncidentOntology extends Ontology {
	// The name identifying this ontology
	public static final String ONTOLOGY_NAME = "Incident-ontology";

	// Concepts
	public static final String INCIDENT = "Incident";
	public static final String INCIDENT_ID = "id";
	public static final String INCIDENT_SERVICE = "service";
	public static final String INCIDENT_DIAGNOSIS = "diagnosis";
		
	public static final String SERVICE = "Service";
	public static final String SERVICE_ID = "id";
	public static final String SERVICE_PORTS = "ports";
	
	public static final String DEVICE = "Device";
	public static final String DEVICE_NAME = "name";
	public static final String DEVICE_IP = "ip";
	public static final String DEVICE_PORT = "port";
	
	public static final String PORT = "Port";
	public static final String PORT_NAME = "name";
	public static final String PORT_LOCALIZATION = "localization";
	public static final String PORT_DEVICE = "device";
	
	
	public static final String DIAGNOSIS = "Diagnosis";
	public static final String DIAGNOSIS_NAME = "name";
	public static final String DIAGNOSIS_SERVICE = "service";
	public static final String DIAGNOSIS_RECOMMENDATION = "recommendation";
	public static final String DIAGNOSIS_TESTS = "tests";
	
	public static final String TEST = "test";
	public static final String TEST_NAME = "name";
	public static final String TEST_RESULT = "result";
	public static final String TEST_DEVICE = "device";
	
	

	//Predicates
	public static final String OWNS = "Owns";
	public static final String OWNS_OWNER = "owner";
	public static final String OWNS_ITEM = "item";
	
	
	//Agent actions i.e. special concepts that indicate actions that can be performed by some agents 
	public static final String CHECK = "Check";
	public static final String CHECK_DEVICE = "device";
	public static final String CHECK_TEST = "test";
	
	
	// The singleton instance of this ontology
	private static Ontology theInstance = new IncidentOntology();

	// This is the method to access the singleton music shop ontology object
	public static Ontology getInstance() {
		return theInstance;
	} 


	// Private constructor
	private IncidentOntology() {
		// The music shop ontology extends the basic ontology
		super(ONTOLOGY_NAME, BasicOntology.getInstance());
		try {
			add(new ConceptSchema(INCIDENT), Incident.class);
			add(new ConceptSchema(SERVICE), Service.class); 
			add(new ConceptSchema(DEVICE), Device.class);
			add(new ConceptSchema(TEST), Test.class);
			
			
			add(new ConceptSchema(DIAGNOSIS), Diagnosis.class);
			
			add(new PredicateSchema(OWNS), Owns.class);
			
			add(new AgentActionSchema(CHECK), Check.class);

			// Structure of the schema for the Incident concept
			ConceptSchema cs = (ConceptSchema) getSchema(INCIDENT);
			cs.add(INCIDENT_ID, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL); // The serial-number slot is optional and
			// allowed values are integers.
			
			// Structure of the schema for the Service concept 
			cs = (ConceptSchema) getSchema(SERVICE);
			cs.addSuperSchema((ConceptSchema) getSchema(INCIDENT));
			cs.add(SERVICE_ID, (PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(SERVICE_PORTS, (ConceptSchema) getSchema(PORT), 1,
					ObjectSchema.UNLIMITED); // The tracks slot has cardinality > 1
			
			// Structure of the schema for the Track concept
			cs = (ConceptSchema) getSchema(DEVICE);
			cs.add(DEVICE_NAME, (PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(DEVICE_IP, (PrimitiveSchema)
					getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);

			
			// Structure of the schema for the Test concept
			cs = (ConceptSchema) getSchema(TEST);
			cs.add(TEST_NAME, (PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(TEST_RESULT, (PrimitiveSchema)
					getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);

			
			
			// Structure of the schema for the Diagnosis concept
			cs = (ConceptSchema) getSchema(DIAGNOSIS);
			cs.addSuperSchema((ConceptSchema) getSchema(INCIDENT));
			cs.add(DIAGNOSIS_NAME, (PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(DIAGNOSIS_TESTS, (ConceptSchema) getSchema(TEST), 1,
					ObjectSchema.UNLIMITED); // The tracks slot has cardinality > 1
			
			// Structure of the schema for the Owns predicate
			PredicateSchema ps = (PredicateSchema) getSchema(OWNS);
			ps.add(OWNS_OWNER, (ConceptSchema) getSchema(BasicOntology.AID));
			ps.add(OWNS_ITEM, (ConceptSchema) getSchema(INCIDENT));
			
			// Structure of the schema for the Check agent action
			AgentActionSchema as = (AgentActionSchema) getSchema(CHECK);
			as.add(CHECK_TEST, (ConceptSchema) getSchema(INCIDENT));
			as.add(CHECK_DEVICE, (ConceptSchema) getSchema(BasicOntology.AID));
		}
		catch (OntologyException oe) {
			oe.printStackTrace();
		}
	} 
}