JFLAGS = -g -cp .:core3.jar
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

# This uses the line continuation character (\) for readability
# You can list these all on a single line, separated by a space instead.
# If your version of make can't handle the leading tabs on each
# line, just remove them (these are also just added for readability).
CLASSES = \
	Point.java \
	Graph.java \
	HashGraph.java \
	Edge.java \
	HashGraphWithInfo.java \
	GraphSolution.java \
	Optimizer.java \
	EdgeForDsatur.java \
	DsaturOptimizer.java \
	IO.java	\
	TC.java \
	GraphViewer.java \
	GenerateAllOuputs.java \
	GenerateAllOuputsWithHeuristic.java \
	DsaturOptimizerWithHeuristic.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
