package financialAdviser;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.stream.*;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class RiskAllocatorAgent extends Agent {

	private String targetClient;
	private AID[] clientHandlerAgents;
	private int score;
	private int wait = 15000;
	private ArrayList<Integer> data;
	private Hashtable<String, Integer> scoreList;

	protected void setup() {

		// Printout a welcome message
		System.out.println("Hello! Risk Allocation Agent " + getAID().getName()+" is ready.");

		// Add a TickerBehaviour that checks for new clients
		addBehaviour(new TickerBehaviour(this, wait) {
			protected void onTick() {			
				if (ClientHandlerAgent.clientNames.isEmpty()) {
					System.out.println("No Clients: sleeping for 60 seconds");
					reset(60000);
				}
				else {
					// Get name of client to risk assess
					targetClient = ClientHandlerAgent.clientNames.get(0);		
					ClientHandlerAgent.clientNames.remove(0);
					System.out.println("Target client is "+ targetClient);
					System.out.println("Risk assessing "+ targetClient);
					reset(15000);

					// Update the list of clientHandlerAgents
					DFAgentDescription template = new DFAgentDescription();
					ServiceDescription sd = new ServiceDescription();
					sd.setType("new-client");
					template.addServices(sd);
					try {
						DFAgentDescription[] result = DFService.search(myAgent, template); 
						System.out.println("Found the following clientHandlers:");
						clientHandlerAgents = new AID[result.length];
						for (int i = 0; i < result.length; ++i) {
							clientHandlerAgents[i] = result[i].getName();
							System.out.println(clientHandlerAgents[i].getName());
						}
					}
					catch (FIPAException fe) {
						fe.printStackTrace();
					}
					// Perform the request
					myAgent.addBehaviour(new RequestPerformer());
				}
			}
		} );
	}
	// Put agent clean-up operations here
	protected void takeDown() {
		// Printout a dismissal message
		System.out.println("Risk Allocator Agent "+getAID().getName()+" terminating.");
	}

	/**
	   Inner class RequestPerformer.
	   This is the behaviour used by Book-buyer agents to request seller 
	   agents the target book.
	 */

	private class RequestPerformer extends Behaviour {

		private AID currentClient; // The clientHandlerAgent 
		private MessageTemplate mt; // The template to receive replies
		private int step = 0;

		public void action() {
			switch (step) {
			case 0:
				// Send the cfp to all clientHandlerAgents
				ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
				for (int i = 0; i < clientHandlerAgents.length; ++i) {
					cfp.addReceiver(clientHandlerAgents[i]);
				}

				cfp.setContent(targetClient);
				// debugging
				System.out.println("cfp message contents:"+targetClient);

				cfp.setConversationId("risk-assessment");
				cfp.setReplyWith("cfp"+System.currentTimeMillis()); // Unique value
				myAgent.send(cfp);

				// Prepare the template to get proposals
				mt = MessageTemplate.and(MessageTemplate.MatchConversationId("risk-assessment"),
						MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
				// debugging
				System.out.println("templates prepared");

				step = 1;
				break;

			case 1:
				// Receive all proposals/refusals from agents
				ACLMessage reply = myAgent.receive(mt);
				if (reply != null) {
					// Reply received
					if (reply.getPerformative() == ACLMessage.PROPOSE) {

						// This is the client's data
						String[] clientData = reply.getContent().split(", ");
						int[] answers = new int[6];

						// debugging
						// System.out.println(clientData[0]);

						for (int i = 0; i < clientData.length; i++) {
							if (clientData[i].equals("Strongly agree")) {
								answers[i] = 5;
							}
							else if (clientData[i].equals("Agree")) {
								answers[i] = 4;
							}
							else if (clientData[i].equals("Disagree")) {
								answers[i] = 2;
							}
							else if (clientData[i].equals("Strongly disagree")) {
								answers[i] = 1;
							}							
							else {
								answers[i] = 3;
							}
						}

						// sum the array 
						int sum = IntStream.of(answers).sum();

						// Do a simple risk classification
						if (sum < 10) {
							score = 1;
						}
						else if (sum >= 10 && sum < 20) {
							score = 2; 
						}
						else if (sum >= 20) {
							score = 3;
						}

						// debugging
						System.out.println("Scoring completed: Sum = "+sum);
						currentClient = reply.getSender();
						step = 2; 
					}
				}

				else {
					block();
				}
				break;

			case 2:
				// Send the risk assessment back to the client
				ACLMessage assessment = new ACLMessage(ACLMessage.INFORM);
				assessment.addReceiver(currentClient);
				assessment.setContent(targetClient);
				assessment.setConversationId("risk-assessment");
				assessment.setReplyWith(Integer.toString(score));
				myAgent.send(assessment);

				// Prepare the template to get the purchase order reply
				mt = MessageTemplate.and(MessageTemplate.MatchConversationId("risk-assessment"),
						MessageTemplate.MatchInReplyTo(assessment.getReplyWith()));
				step = 3;
				// debugging
				System.out.println("Risk assessment sent back to client");

				break;
			case 3:      
				// Receive the reply
				reply = myAgent.receive(mt);
				if (reply != null) {
					// Purchase order reply received
					if (reply.getPerformative() == ACLMessage.INFORM) {
						// Assessment successful. We can terminate.
						System.out.println("Score = "+score);
						myAgent.doDelete();
					}
					else {
						System.out.println("Attempt failed: already risk-assessed.");
					}
					step = 4;
				}
				else {
					block();
				}
				break;
			}        
		}

		public boolean done() {
			return (step == 4);
		}
	}  // End of inner class RequestPerformer
}

