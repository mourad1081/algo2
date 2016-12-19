JC = javac
JCR = java

.SUFFIXES: .java .class

.java.class:
	$(JC) $*.java

CLASSES = \
	Sommet.java \
	Graphe.java \
	Main.java

default: classes
	@echo Commande pour lancer un de nos graphes tests : java Main
	@echo Commande pour lancer avec un fichier graphe : java Main '<nomFichier>'

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class *~
