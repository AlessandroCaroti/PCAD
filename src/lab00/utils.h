#ifndef UTILS
#define UTILS

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>

#define SEVEN_AND_HALF_MILLION_YEARS 5
#define compute_theAnsware() sleep(SEVEN_AND_HALF_MILLION_YEARS)
#define THE_ANSWARE (void *)((2<<4)+(2<<2)+2)

void fail_errno(const char * const msg)
{
	perror(msg);
	exit(EXIT_FAILURE);
}

void mySleep(long nSec)
{
   struct timespec *t = my_malloc(sizeof(struct timespec));
   t->tv_sec = 0;
   t->tv_nsec = nSec;
   nanosleep(t, NULL);
}
void dialogo_inizio()
{
   puts("F&L: O Deep Thougth computer, the task we have designed you to perforn is the this.");
   mySleep(500);
   puts("We want you tell us The Answer of Life, the Univers and Everything!");
   mySleep(500);
   puts("Can you do it?");
   mySleep(500);
   puts("Yes, I can do it. But I'll have to think about it for seven and hal milion.");
}
void dialogo_fine()
{
   puts("DT: The Answare to the Grate Question... ");
   mySleep(500);
   puts("DT: Of Life, the Univers and Everything.. ");
   mySleep(500);
   puts("DT: is... ");
   mySleep(500);
}
#endif