package projet;


import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class CompagnieAgent extends Agent {
	
	private Compagnie compagnie;
	
	protected void setup(){
		
		Object[] args = getArguments();
		if (args != null && args.length > 0) {			
			compagnie = (Compagnie)args[0];	
			
			addBehaviour(new RespondBehaviour(this));
		}else{
			doDelete();
		}	
		
	}

	
	public void write(String s) {
		
		System.out.println("["+getLocalName()+"]: "+s);
		
	}
	
	public class RespondBehaviour extends CyclicBehaviour {

		private CompagnieAgent myAgent;
		
		public RespondBehaviour(CompagnieAgent a){ myAgent = a; }
		
		public void action() {
			
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
			ACLMessage msg = myAgent.receive(mt);
			
			if(msg != null){
				myAgent.write(myAgent.compagnie.compagnieName + " essaye de répondre à "+msg.getSender().getLocalName());
				
				Route route = null;
				try {
					route = (Route) msg.getContentObject();
				} catch (UnreadableException e) {
					e.printStackTrace();
				}
				List<Vol> volsTrouves = myAgent.compagnie.getCorrespondingVols(route);
				
				
				String title = msg.getContent();
				ACLMessage reply = msg.createReply();
				reply.setPerformative(ACLMessage.AGREE);
				reply.addReceiver(msg.getSender());
				reply.setConversationId("OFFER");
				
				myAgent.write(myAgent.compagnie.compagnieName + "envoie "+ volsTrouves.size()+" offres");
				try {
					reply.setContentObject((Serializable) volsTrouves);
				} catch (IOException e) {
					e.printStackTrace();
				}
				myAgent.send(reply);
				
			}else{
				
			}
			
		}
		
	}
	
	
}