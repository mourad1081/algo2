JC = javac
JCR = java

.SUFFIXES: .java .class

.java.class:
	$(JC) $*.java

CLASSES = \
	Sommet.java \
	Graphe.java \
	Main.java

TXT_FILES = \
	grapheTest\graphe1.txt \
	grapheTest\graphe2.txt \
	grapheTest\graphe3.txt \
	grapheTest\graphe4.txt \
	grapheTest\graphe5.txt \
	grapheTest\grapheProjet.txt \

default: classes
	@echo Commande pour lancer un de nos graphes tests : java Main
	@echo Commande pour lancer avec un fichier graphe : java Main '<nomFichier>'

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class *~

exec-tests: classes
	for file in $(TXT_FILES); do $(JCR) Main $$file; done;
