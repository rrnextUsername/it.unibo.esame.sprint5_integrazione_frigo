/* Generated by AN DISI Unibo */ 
package it.unibo.butler_test_handler

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Butler_test_handler ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						solve("consult('sysRules.pl')","") //set resVar	
					}
					 transition( edgeName="goto",targetState="waitAction", cond=doswitch() )
				}	 
				state("waitAction") { //this:State
					action { //it:State
					}
					 transition(edgeName="t011",targetState="handleCheck",cond=whenDispatch("check"))
					transition(edgeName="t012",targetState="handleWait",cond=whenDispatch("wait"))
				}	 
				state("handleCheck") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("check(NUMBER)"), Term.createTerm("check(NUMBER)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("$name in ${currentState.stateName} | $currentMsg")
								solve("assert(done(check,${payloadArg(0)}))","") //set resVar	
								forward("actionComplete", "actionComplete(ok)" ,"butler_solver" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitAction", cond=doswitch() )
				}	 
				state("handleWait") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("wait(TIME)"), Term.createTerm("wait(TIME)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("$name in ${currentState.stateName} | $currentMsg")
								solve("assert(done(wait,${payloadArg(0)}))","") //set resVar	
								delay(payloadArg(0).toLong()*2000)
								forward("actionComplete", "actionComplete(ok)" ,"butler_solver" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitAction", cond=doswitch() )
				}	 
			}
		}
}