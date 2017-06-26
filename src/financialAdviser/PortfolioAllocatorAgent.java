package financialAdviser;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.stream.IntStream;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class PortfolioAllocatorAgent extends Agent {

	private AID[] riskAllocatorAgents;
	private int wait = 15000;
	private ArrayList<Integer> data;
	private Hashtable<String, Integer> scoreList;
	
	
	protected void setup() {

		// Printout a welcome message
		System.out.println("Hello! Portfolio Allocation Agent "+getAID().getName()+" is ready.");
		
	}
	
	private class RequestScoresPerformer extends Behaviour {

		private AID currentRiskAllocator; // The clientRiskAllocator
		private MessageTemplate mt; // The template to receive replies
		private int step = 0;

		public void action() {
			switch (step) {
			case 0:
				// Send the cfp to all riskAllocatorAgents
				ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
				for (int i = 0; i < riskAllocatorAgents.length; ++i) {
					cfp.addReceiver(riskAllocatorAgents[i]);
				}

				cfp.setContent(targetClient);
				// debugging
				System.out.println("cfp message contents:" + targetClient);

				cfp.setConversationId("portfolio-allocation");
				cfp.setReplyWith("cfp"+System.currentTimeMillis()); // Unique value
				myAgent.send(cfp);

				// Prepare the template to get proposals
				mt = MessageTemplate.and(MessageTemplate.MatchConversationId("portfolio-allocation"),
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
						String[] clientScore = reply.getContent().split(", ");
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
	
		// Create a Cyclic Behavior that checks for new scores
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

	}
}