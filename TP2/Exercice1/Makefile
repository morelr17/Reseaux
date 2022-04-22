JFLAGS = -g
JC = javac
OPT = -O3
#OPT = -g
WARN = -Wall

all: ReceiveUDP SendUDP

ReceiveUDP: ReceiveUDP.java
	$(JC) $^

SendUDP: SendUDP.java
	$(JC) $^

clean: *.class 
	$(RM) $^

doc: *.java 
	javadoc -d $@ $^