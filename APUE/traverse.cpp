#include <iostream>
#include <sys/stat.h>
#include <string>
#include <string.h>
#include <dirent.h>
#include <vector>
#include <stdio.h>
#include <map>
#include <unistd.h>
#include <algorithm>

using namespace std;

map<string, int> sys_stastic;
vector<long> sys_inodes;

void traverse(string);
void mythrow(const char *);

int main( )
{
    string path = "/";

    try{
        traverse(path);
    }catch(char * ch)
    {
        cout << ch << endl;
    }

    sys_stastic.insert(pair<string,int>("regular file",0));
    sys_stastic.insert(pair<string,int>("block file",0));
    sys_stastic.insert(pair<string,int>("character file",0));
    sys_stastic.insert(pair<string,int>("fifo file",0));
    sys_stastic.insert(pair<string,int>("soft link file",0));
    sys_stastic.insert(pair<string,int>("hard link file",0));
    sys_stastic.insert(pair<string,int>("socket file",0));
    sys_stastic.insert(pair<string,int>("dir file",0));
    sys_stastic.insert(pair<string,int>("open failed file",0));
    sys_stastic.insert(pair<string,int>("Access denied", 0));

    map<string, int>::iterator it;
    for(it = sys_stastic.begin(); it != sys_stastic.end(); it ++)
    {
        cout << it->first << " : " << it->second << endl;
    }

    return 0;
}

void traverse(string path)
{
    struct stat stat_buf;
    char buf[1024];
    
    if(access(path.c_str(), R_OK) == -1)
    {
        sys_stastic["Access denied"] ++;
        return ;
    }
    
    if(lstat(path.c_str(),&stat_buf) == -1)
        mythrow(path.c_str());

    vector<long>::iterator result = find(sys_inodes.begin(), sys_inodes.end(),stat_buf.st_ino);
    
    if(result == sys_inodes.end())
    {
        sys_inodes.push_back(stat_buf.st_ino);
        switch(stat_buf.st_mode & S_IFMT)
        {
            case S_IFREG:
                sys_stastic["regular file"] ++;
                break;
            case S_IFBLK:
                sys_stastic["block file"] ++;
                break;
            case S_IFCHR:
                sys_stastic["character file"] ++;
                break;
            case S_IFIFO:
                sys_stastic["fifo file"] ++;
                break;
            case S_IFLNK:
                sys_stastic["soft link file"] ++;
                break;
            case S_IFSOCK:
                sys_stastic["socket file"] ++;
                break;
            case S_IFDIR:
                sys_stastic["dir file"] ++;
                DIR *dp;
                struct dirent *dirp;

                if((dp = opendir(path.c_str())) == NULL)
                    mythrow("opendir error ");
                while((dirp = readdir(dp)) != NULL)
                {
                    if(!strcmp(dirp->d_name,".") || !strcmp(dirp->d_name, ".."))
                        continue;
                   
                    string newpath = path;
                    if((newpath.compare("/")))
                        newpath.append("/");
                    newpath.append(dirp->d_name);
                   cout << "entering " << newpath << endl;
                    traverse(newpath);
                }
                closedir(dp);
                break;
            default:
                mythrow("switch error");
                break;
        }
    }
    else
    {
        sys_stastic["hard link file"] ++;
    }

}

void mythrow(const char* info_err)
{
           // dup2 + read end of the file
            //fmemopen std = file*
            //fseek + stdou + buf
    char temfile[128];
    
    FILE* tem_file = NULL;
    
    char buf[1024];
    temfile = tmpnam(NULL);
    tem_file = freopen(temfile, "w+", stdout);
    perror(info_err);
    fseek(tem_file,0,SEEK_SET);
    fscanf(tem_file,"%[^\n]",buf);
    fclose(tem_file);
    freopen("/dev/tty", "w", stdout);
    throw buf;
}



