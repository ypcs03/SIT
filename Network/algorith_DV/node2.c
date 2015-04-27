#include <stdio.h>
#include "project3.h"

void rtupdate2( struct RoutePacket *rcvdpkt );
extern int TraceLevel;
extern int NumberOfNodes;
struct distance_table {
  int costs[MAX_NODES][MAX_NODES];
};
struct distance_table dt2;
struct NeighborCosts   *neighbor2;

/* students to write the following two routines, and maybe some others */

void rtinit2() {
    int i, j;
    struct RoutePacket * first = (struct RoutePacket *)malloc(sizeof(struct RoutePacket));
    first->sourceid = 2;
    first->destid = 2;
    
    //acquire the neighbors
    neighbor2 = getNeighborCosts(2);

    //initialize the router table
    for(i = 0; i < NumberOfNodes; i ++)
        for(j = 0; j < NumberOfNodes; j ++){
            if(i == j)
                dt2.costs[i][j] = 0;
            else
                dt2.costs[i][j] = INFINITY;
        }

    for(i = 0; i < NumberOfNodes; i ++){
        first->mincost[i] = neighbor2->NodeCosts[i];
//        if(neighbor2->NodeCosts[i] != INFINITY){
//            dt2.costs[2][i] = neighbor2->NodeCosts[i];
//        }
    }
    rtupdate2(first);
}

void rtupdate2( struct RoutePacket *rcvdpkt ) {
    int i, j, k;
    int temp;  

    int updated = NO;
    struct RoutePacket pkt; 

    //Update the table
    for(i = 0; i < NumberOfNodes; i ++)
        for(j = 0; j < NumberOfNodes; j ++){
            if(dt2.costs[i][j] > rcvdpkt->mincost[j] + dt2.costs[i][rcvdpkt->sourceid]){
                dt2.costs[i][j] = rcvdpkt->mincost[j] + dt2.costs[i][rcvdpkt->sourceid];

                if(updated == NO && i == 2)
                    updated = YES;
            }
        }

    //create an event to update the neighbour router
    if(updated == YES){
        //table should be updated for each neighbor
        for(i = 0; i < neighbor2->NodesInNetwork; i ++){
            if(neighbor2->NodeCosts[i] != INFINITY){
                pkt.sourceid = 2;
                pkt.destid = i;

                for(j = 0; j < NumberOfNodes; j ++)
                    pkt.mincost[j] = dt2.costs[2][j];

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
void printdt2( int MyNodeNumber, struct NeighborCosts *neighbor, 
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
}    // End of printdt2

