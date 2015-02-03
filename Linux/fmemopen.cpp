#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <iostream>

#define BSZ 48
using namespace std;

int main()
{
    FILE *fp;
    char buf[BSZ];

    memset(buf, 'a', BSZ-2);
//    buf[BSZ-2] = '\0';
    buf[BSZ-2] = 'x';
    buf[BSZ-1] = '\0';

    if((fp = fmemopen(buf, BSZ, "w+")) == NULL)
        perror("fmemopen failed");

    printf("initial buffer contents: %s\n", buf);
    fprintf(fp, "hello, world");
    printf("before flush: %s\n", buf);
    fflush(fp);
    printf("after fflush: %s\n", buf);
    printf("Length of string in buf = %ld\n", (long)strlen(buf));

    memset(buf, 'b', BSZ-2);
    buf[BSZ-2] = '\0';
    buf[BSZ-1] = 'x';
    fprintf(fp,"******hello, worldjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
    cout << buf[43] << endl;
    fseek(fp, 0, SEEK_SET);
    printf("after fseek: %s\n", buf);
    printf("Length of string in buf = %ld\n", (long)strlen(buf));

    memset(buf, 'c', BSZ-2);
    buf[BSZ-2] = '\0';
    buf[BSZ-1] = 'x';
    fprintf(fp, "shhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhdhffdsh helosd");
    fclose(fp);

    printf("AFter fclose: %s\n", buf);
    printf("len of string in buf = %ld\n", (long)strlen(buf));
    return (0);
}
