#include <stdio.h>
#include "project3.h"

extern int TraceLevel;

//Added by Peng
extern int NumberOfNodes;

struct distance_table {
  int costs[MAX_NODES][MAX_NODES];
};
struct distance_table dt0;
struct NeighborCosts   *neighbor0;

// Part of the assignment asks that you write node4.c and node5.c so that you
// end up with a total of 6 nodes.  But your startoff code only has
// 4 nodes; node0.c, node1.c, node2.c, and node3.c
// So here are the stubs for routines that could appear in
// node4.c and node5.c should you choose to write them.  If you do so,
// you should eliminate the entries here.
void    rtinit4(){ }
void    rtinit5(){ }
void    rtinit6(){ }
void    rtinit7(){ }
void    rtinit8(){ }
void    rtinit9(){ }
void    rtupdate4( struct RoutePacket *rcvdpkt ) { }
void    rtupdate5( struct RoutePacket *rcvdpkt ) { }
void    rtupdate6( struct RoutePacket *rcvdpkt ) { }
void    rtupdate7( struct RoutePacket *rcvdpkt ) { }
void    rtupdate8( struct RoutePacket *rcvdpkt ) { }
void    rtupdate9( struct RoutePacket *rcvdpkt ) { }

void rtupdate0( struct RoutePacket *rcvdpkt );
void printdt0( int MyNodeNumber, struct NeighborCosts *neighbor, struct distance_table *dtptr );
/* students to write the following two routines, and maybe some others */
//Added by Peng
void rtinit0() {
    int i, j;
    struct RoutePacket * first = (struct RoutePacket *)malloc(sizeof(struct RoutePacket));
    first->sourceid = 0;
    first->destid = 0;

    //acquire the neighbors
    neighbor0 = getNeighborCosts(0);

    //initialize the router table
    for(i = 0; i < NumberOfNodes; i ++)
        for(j = 0; j < NumberOfNodes; j ++){
            if(i == j)
                dt0.costs[i][j] = 0;
            else
                dt0.costs[i][j] = INFINITY;
        }

    for(i = 0; i < NumberOfNodes; i ++){
        first->mincost[i] = neighbor0->NodeCosts[i];
    }

    rtupdate0(first);
}

void print(){
    int i ,j;
    for(i = 0; i < 4; i ++){
        for(j = 0; j < 4; j ++)
            printf("%d\t", dt0.costs[i][j]);
        printf("\n");
    }
}


void rtupdate0( struct RoutePacket *rcvdpkt ) {
    int i, j, k;
    int temp;  

    int updated = NO;
    struct RoutePacket pkt; 
    
    //Update the table
    for(i = 0; i < NumberOfNodes; i ++)
        for(j = 0; j < NumberOfNodes; j ++){
            if(dt0.costs[i][j] > rcvdpkt->mincost[j] + dt0.costs[i][rcvdpkt->sourceid]){
                dt0.costs[i][j] = rcvdpkt->mincost[j] + dt0.costs[i][rcvdpkt->sourceid];

                if(updated == NO && i == 0)
                    updated = YES;
            }
        }

    //create an event to update the neighbour router
    if(updated == YES){
        //table should be updated for each neighbor
        for(i = 1; i < neighbor0->NodesInNetwork; i ++){
            if(neighbor0->NodeCosts[i] != INFINITY){
                pkt.sourceid = 0;
                pkt.destid = i;
                
                for(j = 0; j < NumberOfNodes; j ++)
                    pkt.mincost[j] = dt0.costs[0][j];           
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
void printdt0( int MyNodeNumber, struct NeighborCosts *neighbor, 
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
}    // End of printdt0

