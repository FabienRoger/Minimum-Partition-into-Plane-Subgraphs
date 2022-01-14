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
	CheckSolution.java \
	DsaturOptimizer.java \
	Edge.java \
	EdgeForDsatur.java \
	EdgeWithInfo.java \
	GenerateAllOuputs.java \
	Graph.java \
	GraphIntersection.java \
	GraphSolution.java \
	GraphViewer.java \
	GreedyOptimizer.java \
	HashGraph.java \
	HashGraphWithInfo.java \
	IO.java	\
	MarkovOptimizer.java \
	MyButton.java \
	Optimizer.java \
	Point.java \
	SimpleOptimizer.java \
	TC.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
