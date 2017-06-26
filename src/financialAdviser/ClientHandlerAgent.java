package financialAdviser;
import java.util.ArrayList;
import java.util.Hashtable;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ClientHandlerAgent extends Agent {

	// The catalogue of clients
	public static ArrayList<String> clientNames = new ArrayList<String>();
	public Hashtable<String, Integer> scoreList;
	public Hashtable<String, ArrayList<String>> clientList;

	//The GUI for adding clients
	private InputGUI myGui;
	private RiskQuizGUI myRiskQuizGUI;

	protected void setup() {
		
		// Create a client list
		clientList = new Hashtable<String, ArrayList<String>>();

		// Create and show the Input GUI 
		//myGui = new InputGUI(this);
		//myGui.showGui();

		// Create and show the Risk Quiz GUI
		myRiskQuizGUI = new RiskQuizGUI(this);
		myRiskQuizGUI.showGui();

		// Register the new client in the yellow pages
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("new-client");
		sd.setName("JADE-new-client");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}

		// Add the behaviour serving queries from risk allocator agents
		addBehaviour(new NewClientRequestsServer());

		// Add the behaviour serving completed assessments from risk allocator agents
		addBehaviour(new FinishedAssessmentServer());
	}

	// Put agent clean-up operations here
	protected void takeDown() {
		// Deregister from the yellow pages
		try {
			DFService.deregister(this);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		// Close the GUI
		myGui.dispose();
		// Printout a dismissal message
		System.out.println("ClientHandlerAgent "+getAID().getName()+" terminating.");
	}

	/**
	     This is invoked by the GUI when the user inputs their information
	 * @return 
	 */
	public void updateClientList(final String name, final ArrayList<String> answers) {
		addBehaviour(new OneShotBehaviour() {
			public void action() {
				clientNames.add(name);
				clientList.put(name, answers);
				System.out.println(name+" inserted into client list. "
						+ "Answer 1 = "+ answers.get(0) + " "
						+ "Answer 2 = "+ answers.get(1) + " "
						+ "Answer 3 = "+ answers.get(2) + " "
						+ "Answer 4 = "+ answers.get(3) + " "
						+ "Answer 5 = "+ answers.get(4) + " "
						+ "Answer 6 = "+ answers.get(5));			
			}
		});
	}

	/**
	   Inner class NewClientRequestsServer.
	   This is the behaviour used by the ClientHandler agent to serve 
	   incoming requests for risk classification from RiskAllocatorAgents.
	   If the user is in the client list the ClientHandler agent replies 
	   with a PROPOSE message. Otherwise a REFUSE message is
	   sent back.
	 */

	private class NewClientRequestsServer extends CyclicBehaviour {
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				// CFP Message received. Process it
				String name = msg.getContent();
				ACLMessage reply = msg.createReply();

				// debugging
				System.out.println("Name:"+ name);
				ArrayList<String> data = clientList.get(name);
				if (data != null) {
					// The client has filled in their info, reply with their data
					reply.setPerformative(ACLMessage.PROPOSE);
					reply.setContent(data.toString());
				}
				else {
					// The client has NOT filled in their info.
					reply.setPerformative(ACLMessage.REFUSE);
					reply.setContent("not-available");
				}
				myAgent.send(reply);
			}
			else {
				block();
			}
		}
	}  // End of inner class

	/**
	   Inner class FinishedAssessmentServer.
	   This is the behaviour used by ClientHandler agents to serve incoming 
	   assessments from RiskAllocator agents.
	   The ClientHandler agent removes the client from its catalogue 
	 **/

	private class FinishedAssessmentServer extends CyclicBehaviour {
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {

				// ACCEPT_PROPOSAL Message received. Process it
				String name = msg.getContent();
				ACLMessage reply = msg.createReply();
				ArrayList<String> data = clientList.remove(name);
				if (data != null) {
					reply.setPerformative(ACLMessage.INFORM);
					System.out.println(name+" succcessfully assessed by "+msg.getSender().getName());
				}
				else {
					// The requested client has been assessed by another agent meanwhile .
					reply.setPerformative(ACLMessage.FAILURE);
					reply.setContent("not-available");
				}
				myAgent.send(reply);
			}
			else {
				block();
			}
		}
	}  // End of inner class
}

