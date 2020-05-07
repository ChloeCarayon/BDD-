# BDD

Projet Base de données semestre 6.

Nouvelles commandes SQL : 
	
ALTER TABLE COUPLE
	MODIFY Date_couple date DEFAULT NOW() ;
	
ALTER TABLE CLIENT
	ADD CONSTRAINT email_unique UNIQUE (mail);