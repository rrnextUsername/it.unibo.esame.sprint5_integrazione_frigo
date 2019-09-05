%====================================================================================
% butler description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxbutler, "localhost",  "MQTT", "0" ).
 qactor( butler_solver, ctxbutler, "it.unibo.butler_solver.Butler_solver").
  qactor( butler_fridge_handler, ctxbutler, "it.unibo.butler_fridge_handler.Butler_fridge_handler").
  qactor( butler_test_handler, ctxbutler, "it.unibo.butler_test_handler.Butler_test_handler").
  qactor( fridge, ctxbutler, "it.unibo.fridge.Fridge").
  qactor( frontend_dummy, ctxbutler, "it.unibo.frontend_dummy.Frontend_dummy").
