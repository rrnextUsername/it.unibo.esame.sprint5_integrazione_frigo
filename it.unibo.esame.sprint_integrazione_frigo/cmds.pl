comando( testMain, null ) :-
	assert( azione( check, 1, 1, 1, 1 ) ),
	assert( azione( wait, 1, 1, 1, 1 ) ),
	assert( azione( check, 2, 2, 2, 2 ) ).
	
comando( testModificaInventario, null ) :-
	assert( azione( notificaFrigo, aggiungi, budino, cibo, null ) ),
	assert( azione( notificaFrigo, rimuovi, torta, cibo, null ) ),
	assert( azione( aggiungiOggetto, butlerInv, piatto, silverware, null ) ),
	assert( azione( spostaOggetto, butlerInv, dishwasherInv, tazza, silverware ) ),
	assert( azione( rimuoviOggetto, tableInv, pizza, cibo, null ) ),
	assert( azione( notificaFrigo, conferma, budino, cibo, null ) ),
	assert( azione( notificaFrigo, conferma, torta, cibo, null ) ),
	assert( azione( notificaFrigo, conferma, pasta, cibo, null ) ).
	
comando( testSync, NomeCibo ) :-
	assert( azione( notificaFrigo, conferma, NomeCibo, cibo, null ) ),
	assert( azione( continuaPiano, testSyncSuccess, NomeCibo, null, null ) ).
	
continua( testSyncSuccess, NomeCibo) :-
	assert( azione( check, 1, 2, 3, 4 ) ).
	
	
	
comando( testASync, null ):-
	assert( azione( notificaFrigo, aggiungi, crema, cibo, null ) ).
	

%% Comandi Interni %%

spostaTutto( I1, I2, Categ ) :-
	inventario( I1, L ),
	spostaTutto_( L, I1, I2, Categ ).

spostaTutto_( [], I1, I2, Categ ).
spostaTutto_( [( Nome, Categ )|T], I1, I2, Categ  ) :-
	assert( azione( spostaOggetto, I1, I2, Nome, Categ ) ), !,
	spostaTutto_( T, I1, I2, Categ ).
spostaTutto_( [(A,B)|T], I1, I2, Categ  ) :-
	spostaTutto_( T, I1, I2, Categ ).

msgFrigoTutto( Azione, Inventario, Categ ) :-
	inventario( Inventario, Lista ),
	msgFrigoTutto_( Lista, Azione, Categ ).

msgFrigoTutto_( [], Azione, Categ ).
msgFrigoTutto_( [(Nome, Categ)|T], Azione, Categ ) :-
	assert( azione( notificaFrigo, Azione, Nome, Categ, null ) ), !,
	msgFrigoTutto_( T, Azione, Categ ).
msgFrigoTutto_( [_|T], Azione, Categ ) :-
	msgFrigoTutto_( T, Azione, Categ ).

rimuoviTutto( Inventario, Categ ) :-
	output( rimozione ),
	output( Inventario ),
	output( Categ ),	
	inventario( Inventario, Lista ),
	output( inventario( Inventario, Lista ) ),
	%%rimuoviTuttoRic( a , b , c ),
	output( ciao ),
	rimuoviTuttoRic( Lista, Inventario, Categ ).
	
rimuoviTuttoRic( [], Inventario, Categ ) :-
	output( done ).
	
rimuoviTuttoRic( [(Nome, Categ)|T], Inventario, Categ ) :-
	output( match ),
	output( Nome ),
	output( Categ ),
	output( Inventario ),
	output( T ),
	assert( azione( rimuoviOggetto, Inventario, Nome, Categ, null ) ), !,
	rimuoviTuttoRic( T, Inventario, Categ ).
	
rimuoviTuttoRic( [_|T], Inventario, Categ ) :-
	output( nomatch ),
	output( Categ ),
	output( Inventario ),
	output( T ),
	rimuoviTuttoRic( T, Inventario, Categ).



	

	


