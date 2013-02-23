package projet;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import projet.CompagnieAgent.RespondBehaviour;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class PersonalAgent extends Agent {
	
	private Request userRequest;
	private List<Vol> preFilteredVols = new ArrayList<Vol>();
	private List<Vol> usableVols = new ArrayList<Vol>();
    
    public int IndexOfImmediateRoute;
    private List<AID> providerAgents;
    
    public void setup(){
    	
    	Object[] args = getArguments();
		if (args != null && args.length > 0) {			
			userRequest = (Request)args[0];	
			
			addBehaviour(new AskOffersBehaviour(this));
            addBehaviour(new ReceiveOffersBehaviour(this));
		}else{
			doDelete();
		}	
    	
    }
	
    public void write(String s) {
		
		System.out.println("["+getLocalName()+"]: "+s);
		
	}
   
    private void AddAndFilterVols(List<Vol> VolList){
    	
    	TravelClassEnum en = TravelClassEnum.BUSINESS_CLASS;
    	switch(userRequest.getTravelClass()){
    	case FIRST_CLASS : 
    		en = TravelClassEnum.FIRST_CLASS;
    		break;
    	case BUSINESS_CLASS : 
    		en = TravelClassEnum.BUSINESS_CLASS;
    		break;
    	case ECONOMY_CLASS :
    		en = TravelClassEnum.ECONOMY_CLASS;
    		break;
    	}
    	
    	//ajoute les vol disponibles correspondant à la requête
    	for(Vol f : VolList){  	    		
    		for(VolClass v : f.getTravelClasses()){
    			if(v.getTravelClass() == en && v.getSeatsAvailable() >= userRequest.getNumberOfSeat()){
    				usableVols.add(f);
    			}
    		}    		
    	}   	
    	
    }
    
    
    
    public class AskOffersBehaviour extends SimpleBehaviour {
    	
    	private PersonalAgent myAgent;
    	
    	public AskOffersBehaviour(PersonalAgent a ){ myAgent = a;}
    	
    	public void action(){
    		
    		Route route = myAgent.userRequest.getUserRoute();
    		
    		for(AID receiverAid : myAgent.providerAgents){
    			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST); 
    			ACLMessage msg = myAgent.receive(mt);
    			msg.addReceiver(receiverAid);
    			msg.setConversationId("ASK");
    			try {
					msg.setContentObject((Serializable) route);
				} catch (IOException e) {
					e.printStackTrace();
				}
    			myAgent.send(msg);
    			
    		}
    		
    	}
    	
		public boolean done() {
			
			return false;
		}
    	
    }
    
    public class ReceiveOffersBehaviour extends CyclicBehaviour{
    	
    	private PersonalAgent myAgent;
    	
    	public ReceiveOffersBehaviour(PersonalAgent a){myAgent = a;}
    	
    	public void action(){
    		MessageTemplate mt = MessageTemplate.MatchConversationId("OFFER"); 
			ACLMessage msg = myAgent.receive(mt);
			
			if(msg != null){
				myAgent.write("Personal agent reçoit la liste des vol de "+msg.getSender().getLocalName());
				List<Vol> VolList = null;
                //List<Vol> VolList = Utils.DeSerializeObject<List<Vol>>(msg.getContent());
                myAgent.write(myAgent.getLocalName() + " reçoit "+ VolList.size());
                myAgent.AddAndFilterVols(VolList);

			}
    	}
    }
    
}
