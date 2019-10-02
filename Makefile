JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:  ;  $(JC) $(JFLAGS) $*.java

        
CLASSES = \
  GroceryBagging.java \
  Partition.java \
  Bag.java \
  Item.java 

default: classes

classes:  $(CLASSES:.java=.class)

clean:
	$(RM) *.class
