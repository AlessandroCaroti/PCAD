#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdbool.h>
#include "utils.h"

pthread_mutex_t mutex;
pthread_cond_wait cond;
void *asynch(void* arg);
void *get(void* result);
struct result_st;

int main(int argc, char ** argv) {
   pthread_t th;
   pthread_attr_t attr;
   int thread_exit;
   struct result_st res;
   res.isValid = false;
   if(pthread_attr_init(&attr) != 0)
      fail_errno(NULL);
   if(pthread_attr_setdetachstate(&attr, PTHREAD_CREATE_DETACHED) != 0)
      fail_errno(NULL);
   if(pthread_create(&th, &attr, asynch, &res) != 0)
      fail_errno(NULL);
   thread_exit = (int) get(&res);
   printf("Il risultato del thread è %d\n", thread_exit);
   exit(EXIT_SUCCESS);
}

struct result_st{
   bool isValid;
   void* res;
};

void *asynch(void* arg)
{
   struct result_st* r = arg;
   puts("\t(DeepThought_thread)[INFO] start to computing The Answare");
   pthread_mutex_lock(&mutex);
   compute_theAnsware();
   r->res = THE_ANSWARE;
   r->isValid = true;
   pthread_cond_signal( &cond );
   pthread_mutex_unlock(&mutex);
   return NULL;
}

void *get(void* result)
{
   struct result_st* r = result;
   puts(" ");
   pthread_mutex_lock(&mutex);
   while(r->isValid == false) pthread_cond_wait(& cond, & mutex );
   pthread_mutex_unlock(&mutex);
   puts("\t...thread terminato.");
   return r->res;
}