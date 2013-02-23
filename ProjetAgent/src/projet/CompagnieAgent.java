package projet;


import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
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
				
				//volsTrouvés = compagnie.getVolsCorrespondant (départ, déstination) ?
				
				String title = msg.getContent();
				ACLMessage reply = msg.createReply();
				reply.setPerformative(ACLMessage.AGREE);
				reply.addReceiver(msg.getSender());
				reply.setConversationId("OFFER");
				
				//myAgent.write(myAgent.compagnie.compagnieName + "envoie "+ volsTrouves.size()+" offres");
				//reply.setContent(Utils.SerializeObject<List<VolWrapper>>(volTrouves));
				myAgent.send(reply);
				
			}else{
				
			}
			
		}
		
	}
	
	
}