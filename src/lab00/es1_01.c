#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define MAX_THREADS 100

void *task(void* id_th) {
   sleep(rand()%10);
   printf("ptid_%d\n", *(int*)id_th);
   free(id_th);
}

int main(int argc, char ** argv) {
   int th_num;
   pthread_t *thread_list;
   int* id_th;
   if(argc<2)
   {
      fprintf(stderr, "Error: missing argument. Use: es1_01 THREAD_NUMBER\n");
      exit(EXIT_FAILURE);
   }
   th_num = atoi(argv[1]);
   if(th_num < 1 || th_num > MAX_THREADS)
   {
      //errore argomeno numero thread non valido
      fprintf(stderr, "Argument 2 in not valid. THREAD_NUMBER > 1 and THREAD_NUMBER < %d\n", MAX_THREADS);
      exit(EXIT_FAILURE);
   }
   thread_list = malloc(th_num*sizeof(pthread_t));
   if(thread_list == NULL)
   {
      perror(NULL);
      exit(EXIT_FAILURE);
   }
   for(int i = 0; i<th_num;i++)
   {
      id_th = malloc(sizeof(int));
      *id_th = i;
      if(pthread_create(&(thread_list[i]), NULL, task, (void* )id_th) != 0)
      {
         perror(NULL);
         exit(EXIT_FAILURE);
      }
      printf("Created thread %d\n", i);
   }
   for(int i = 0; i< th_num; i++)
   {
      if(pthread_join(thread_list[i],NULL) != 0)
      {
         perror(NULL);
         exit(EXIT_FAILURE);
      }
   }
   free(thread_list);
   exit(EXIT_SUCCESS);
}