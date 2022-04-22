JFLAGS = -g
JC = javac
OPT = -O3
#OPT = -g
WARN = -Wall

all: Tchat

ReceiveUDP: ReceiveUDP.java
	$(JC) $^

SendUDP: SendUDP.java
	$(JC) $^

Tchat: Tchat.java SendUDP ReceiveUDP
	$(JC) Tchat.java

clean: *.class 
	$(RM) $^

doc: *.java 
	javadoc -d $@ $^