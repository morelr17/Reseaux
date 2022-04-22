JFLAGS = -g
JC = javac
OPT = -O3
#OPT = -g
WARN = -Wall

all: server

server: Server.java
	$(JC) $^ 

clean: *.class 
	$(RM) $^

doc: *.java 
	javadoc -d $@ $^