package projet;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import projet.CompagnieAgent.RespondBehaviour;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class PersonalAgent extends Agent {
	
	private Request userRequest;
	private List<Vol> preFilteredVols = new ArrayList<Vol>();
	private List<Vol> usableVols = new ArrayList<Vol>();
	private AID[] sellerAgents;
    
    public int IndexOfImmediateRoute;
    private List<AID> providerAgents;
    
    public void setup(){
    	
    	Object[] args = getArguments();
		if (args != null && args.length > 0) {		
			
			System.out.println(args[0]);
			int noProposition = (Integer) args[0];
			Date dateDepart = (Date) args[1];
			Date dateArrivee = (Date) args[2];
			TravelClassEnum standing = (TravelClassEnum) args[3];
			int nbplaces = (Integer) args[4];
			String destination = (String) args[5];
			String departure = "Paris";
			userRequest.setNumberOfSeat(nbplaces);
			userRequest.setTravelClass(standing);
			Route route = new Route(departure, destination, dateDepart, dateArrivee);
			userRequest.setUserRoute(route);
			
			System.out.println(userRequest);
			
			DFAgentDescription template = new DFAgentDescription();
			
			try {
				DFAgentDescription[] result = DFService.search(this, template); 
				System.out.println("Found the following seller agents:");
				sellerAgents = new AID[result.length];
				for (int i = 0; i < result.length; ++i) {
					sellerAgents[i] = result[i].getName();
					System.out.println(sellerAgents[i].getName());
				}
			}
			catch (FIPAException fe) {
				fe.printStackTrace();
			}
			
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
    
    public class ReceiveOffersBehaviour extends Behaviour{
    	
    	private PersonalAgent myAgent;
    	private AID bestCompagnie;
    	private double bestPrice;
    	private Vol bestVol;
    	private int repliesCnt = 0;
    	private MessageTemplate mt;
    	private int step = 0;
    	
    	public ReceiveOffersBehaviour(PersonalAgent a){myAgent = a;}
    	
    	public void action(){
    		
    		switch(step){
    		case 0:
    			//reçoit toutes les offres
    			mt = MessageTemplate.MatchConversationId("OFFER"); 
    			ACLMessage msg = myAgent.receive(mt);
    			
    			if(msg != null){
    				if(msg.getConversationId().equals("OFFER")){
    					
    					myAgent.write("Personal agent reçoit la liste des vol de "+msg.getSender().getLocalName());

    					List<Vol> VolList = null;
        				try {
        					VolList = (List<Vol>) msg.getContentObject();
        				} catch (UnreadableException e) {
        					e.printStackTrace();
        				}
                        myAgent.write(myAgent.getLocalName() + " reçoit "+ VolList.size());
                        myAgent.AddAndFilterVols(VolList);
                        
                        for(Vol v : usableVols){
                        	double priceT = v.getTravelClassCost(myAgent.userRequest.getTravelClass());
                        	int nb = myAgent.userRequest.getNumberOfSeat();
                        	double price = priceT * nb;
                        	
                        	if(bestCompagnie == null || price < bestPrice){
                        		bestPrice = price;
                        		bestVol = v;
                        		bestCompagnie = msg.getSender();
                        	}
                        }
    					
    				}
    				repliesCnt++;
    				if(repliesCnt >= sellerAgents.length){
    					step = 1;
    				}
    				
                    
    			}else{
    				block();
    			}
    			
    			break;
    		case 1: 
    			// Send the purchase order to the seller that provided the best offer
				ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
				order.addReceiver(bestCompagnie);
				try {
					order.setContentObject((Serializable) bestVol);
				} catch (IOException e) {
					e.printStackTrace();
				}
				order.setConversationId("vol");
				order.setReplyWith("order"+System.currentTimeMillis());
				myAgent.send(order);
				// Prepare the template to get the purchase order reply
				mt = MessageTemplate.and(MessageTemplate.MatchConversationId("vol"),
						MessageTemplate.MatchInReplyTo(order.getReplyWith()));
				step = 3;
				break;
    		}
    		
    		
    	}

		@Override
		public boolean done() {
			
			return false;
		}
    }
    
}
