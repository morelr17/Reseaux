CFLAGS = -Wall -Wextra
CC = gcc

inst:
	 cc -lreadline -lhistory   socklab.o prim.o options.o tools.o tcp.o udp.o version.o /usr/lib/x86_64-linux-gnu/libreadline.a /usr/lib/x86_64-linux-gnu/libtinfo.a -o socklab

clean:
	rm -f *.o cat mcat-lib

.PHONY: all clean
