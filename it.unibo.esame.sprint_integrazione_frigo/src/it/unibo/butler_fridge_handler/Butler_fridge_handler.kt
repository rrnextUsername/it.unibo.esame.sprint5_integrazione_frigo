/* Generated by AN DISI Unibo */ 
package it.unibo.butler_fridge_handler

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Butler_fridge_handler ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						solve("consult('sysRules.pl')","") //set resVar	
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
				state("wait") { //this:State
					action { //it:State
					}
					 transition(edgeName="t09",targetState="handleMsgFridge",cond=whenDispatch("msgFridge"))
					transition(edgeName="t010",targetState="handleReply",cond=whenDispatch("replyFridge"))
				}	 
				state("handleMsgFridge") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("msgFridge(ACTION,NAME,CATEGORY)"), Term.createTerm("msgFridge(ACTION,NAME,CATEGORY)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("$name in ${currentState.stateName} | $currentMsg")
								solve("retract(currentFood(_))","") //set resVar	
								solve("assert(currentFood(${payloadArg(1)}))","") //set resVar	
								forward("msgFridge", "msgFridge(${payloadArg(0)},${payloadArg(1)},${payloadArg(2)})" ,"fridge" ) 
								solve("assert(done(actionMsgFridgeSync,${payloadArg(0)},${payloadArg(1)},${payloadArg(2)}))","") //set resVar	
						}
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
				state("handleReply") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("replyFridge(STATUS)"), Term.createTerm("replyFridge(present)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("actionComplete", "actionComplete(ok)" ,"butler_solver" ) 
								println("$name in ${currentState.stateName} | $currentMsg")
						}
						if( checkMsgContent( Term.createTerm("replyFridge(STATUS)"), Term.createTerm("replyFridge(null)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("actionComplete", "actionComplete(ok)" ,"butler_solver" ) 
								println("$name in ${currentState.stateName} | $currentMsg")
						}
						if( checkMsgContent( Term.createTerm("replyFridge(STATUS)"), Term.createTerm("replyFridge(absent)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("$name in ${currentState.stateName} | $currentMsg")
								solve("currentFood(CIBO)","") //set resVar	
								val Cibo= getCurSol("CIBO").toString()
								itunibo.robot.resourceModelSupport.updateMissingFoodModel(myself ,Cibo )
								forward("actionComplete", "actionComplete(fail)" ,"butler_solver" ) 
						}
					}
					 transition( edgeName="goto",targetState="wait", cond=doswitch() )
				}	 
			}
		}
}
