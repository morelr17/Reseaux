JFLAGS = -g
JC = javac
OPT = -O3
#OPT = -g
WARN = -Wall

all: server client

server: Server.java
	$(JC) $^ 

client: Client.java
	$(JC) $^

clean: *.class 
	$(RM) $^

doc: *.java 
	javadoc -d $@ $^