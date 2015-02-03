#include <errno.h>
#include <stdio.h>
#include <unistd.h>
#include <iostream>
#include <limits.h>
#include <stdlib.h>

using namespace std;

int main()
{
    struct stat buf;

    int fd_old = 2;
    int fd_new = 3;

    if(fstat


    if(fstat(fd_new, &buf) == -1 && errno == EBADF)
        cout << fd_new << " isn't open" << endl;
    else if(fstat(fd_new, &buf) == 0)
    {
        cout << fd_new << " is open" << endl;
        close(fd_new);
    }

    cout << close(fd_new) << endl;

    }
}

int mydup2(int fd2, int fd)
{
    if(Isvalid(fd))
        exit(-1);
    else if(!Isvalid(fd2) && fd2!=fd )
    {
        close(fd2);
    }
    else if(fd2 == fd)
      return fd2;

    int array[fd2] = {-1};
    int i = 0;
    int tem;

    while((tem = dup(fd)) != fd2 && (Isvalid(tem) || !Isvalid(tem))
    {
        array[i++] = tem;
    }
    
    for(int j  = 0; j < i; j ++)
        close(array[j];
}

boolean Isvalid(int fd)
{
    struct stat buf;

    if(fd > 0 && fd < (sysconf(_SC_OPEN_MAX)-1))
        if(fstat(fd, &buf) == -1 && errno == EBADF)
        {
            cout << fd << " isn't open" << endl;
            return true;
        }
        else
        {
            cout << fd << " is open" << endl;
            return false;
        }
    else
    {
        cout << "fd isn't correct" << endl;
        exit(-1);
    }
}
