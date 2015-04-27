#include <stdio.h>
#include "project3.h"

void rtupdate1( struct RoutePacket *rcvdpkt );
extern int TraceLevel;
extern int NumberOfNodes;

struct distance_table {
  int costs[MAX_NODES][MAX_NODES];
};
struct distance_table dt1;
struct NeighborCosts   *neighbor1;

/* students to write the following two routines, and maybe some others */

void rtinit1() {
    int i, j;
    struct RoutePacket *first = (struct RoutePacket*)malloc(sizeof(struct RoutePacket));
    first->sourceid = 1;
    first->destid = 1;

    //acquire the neighbors
    neighbor1 = getNeighborCosts(1);

    //initialize the router table
    for(i = 0; i < NumberOfNodes; i ++)
        for(j = 0; j < NumberOfNodes; j ++){
            if(i == j)
                dt1.costs[i][j] = 0;
            else
                dt1.costs[i][j] = INFINITY;
        }

    for(i = 0; i < NumberOfNodes; i ++){
        first->mincost[i] = neighbor1->NodeCosts[i];
    }

    rtupdate1(first);
}


void rtupdate1( struct RoutePacket *rcvdpkt ) {
    int i, j, k;
    int temp;  

    int updated = NO;
    struct RoutePacket pkt; 

    //Update the table
    for(i = 0; i < NumberOfNodes; i ++)
        for(j = 0; j < NumberOfNodes; j ++){
            if(dt1.costs[i][j] > rcvdpkt->mincost[j] + dt1.costs[i][rcvdpkt->sourceid]){
                dt1.costs[i][j] = rcvdpkt->mincost[j] + dt1.costs[i][rcvdpkt->sourceid];

                if(updated == NO && i == 1)
                    updated = YES;
            }
        }

    //create an event to update the neighbour router
    if(updated == YES){
        //table should be updated for each neighbor
        for(i = 0; i < neighbor1->NodesInNetwork; i ++){
            if(neighbor1->NodeCosts[i] != INFINITY){
                pkt.sourceid = 1;
                pkt.destid = i;

                for(j = 0; j < NumberOfNodes; j ++)
                    pkt.mincost[j] = dt1.costs[1][j];

                toLayer2(pkt);
            }
        }
    }

}


/////////////////////////////////////////////////////////////////////
//  printdt
//  This routine is being supplied to you.  It is the same code in
//  each node and is tailored based on the input arguments.
//  Required arguments:
//  MyNodeNumber:  This routine assumes that you know your node
//                 number and supply it when making this call.
//  struct NeighborCosts *neighbor:  A pointer to the structure 
//                 that's supplied via a call to getNeighborCosts().
//                 It tells this print routine the configuration
//                 of nodes surrounding the node we're working on.
//  struct distance_table *dtptr: This is the running record of the
//                 current costs as seen by this node.  It is 
//                 constantly updated as the node gets new
//                 messages from other nodes.
/////////////////////////////////////////////////////////////////////
void printdt1( int MyNodeNumber, struct NeighborCosts *neighbor, 
        struct distance_table *dtptr ) {
    int       i, j;
    int       TotalNodes = neighbor->NodesInNetwork;     // Total nodes in network
    int       NumberOfNeighbors = 0;                     // How many neighbors
    int       Neighbors[MAX_NODES];                      // Who are the neighbors

    // Determine our neighbors 
    for ( i = 0; i < TotalNodes; i++ )  {
        if (( neighbor->NodeCosts[i] != INFINITY ) && i != MyNodeNumber )  {
            Neighbors[NumberOfNeighbors] = i;
            NumberOfNeighbors++;
        }
    }
    // Print the header
    printf("                via     \n");
    printf("   D%d |", MyNodeNumber );
    for ( i = 0; i < NumberOfNeighbors; i++ )
        printf("     %d", Neighbors[i]);
    printf("\n");
    printf("  ----|-------------------------------\n");

    // For each node, print the cost by travelling thru each of our neighbors
    for ( i = 0; i < TotalNodes; i++ )   {
        if ( i != MyNodeNumber )  {
            printf("dest %d|", i );
            for ( j = 0; j < NumberOfNeighbors; j++ )  {
                    printf( "  %4d", dtptr->costs[i][Neighbors[j]] );
            }
            printf("\n");
        }
    }
    printf("\n");
}    // End of printdt1

