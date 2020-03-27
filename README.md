
**Settlers of Catan**

**Definition of Done**

Below can be found the list of game rules that the game will need to support to be considered done. Below this list we have compiled a list of test cases based on BVA analysis of the cases related to each of the sections in the game rule document. We will consider the project done when the test cases mentioned below all pass.

**Game Rules:**

*   Settlements
       *   Settlements are placed on intersections connecting to roads on the board. Each player owns 5 settlements (meaning there must not be 6 or more of their settlements on the map) and each settlement is worth 1 Victory Point towards the player's score. The player's first two settlements (along with one road each) are free, but any additional settlement costs 1 brick + 1 lumber + 1 grain + 1 wool. No two settlements can be within two spaces of each other and no single settlement can stand alone on the board without connecting to a road.

*   Cities
       *   A settlement can be replaced by a city, which can increase the player's Victory Point score by 1 (meaning each city on the map is worth 2 Victory Points). Each player owns 4 cities, meaning they cannot have 5 or more of their cities on the map. Every time a player replaces a settlement with a city, they must pay 2 grain + 3 ore.

*   Longest Road
    *   Player Maximum of 15 roads
    *   Road must always connect to existing road, settlement, or city
    *   Only 1 road can be built on any given path
    *   First player to build a continuous road (not counting forks) of length 5 receives the longest road card
    *   **IF ANYONE BUILDS A ROAD LONGER THAN THE CURRENT LONGEST ROAD HOLDER, THEY TAKE THE CARD AND THE VICTORY POINTS ARE TRANSFERRED.**
    *   Constructing 1 road requires the player to have 1 brick + 1 lumber.
*   Largest Army
    *   If you are the first player to have played three knight cards, then you may take the “Largest Army” special card. This special card is worth 2 Victory Points. 
    *   **IF ANOTHER PLAYER PLAYS MORE KNIGHT CARDS THAN YOU HAVE, HE/SHE IMMEDIATELY TAKES THE LARGEST ARMY CARD. CONSEQUENTLY, THE 2 POINTS NOW COUNT FOR THE NEW OWNER OF THE CARD.**
    *   A knight card can only be obtained from the development card deck by paying 1 wool + 1 grain + 1 ore resource cards.
*   Devo Card: Victory Point Card
    *   Certain development cards are "Victory Point" cards. The player that plays one automatically earns a Victory Point and keeps the card throughout the remainder of the game.
*   Other rules for the game:
*   Turns
    *   Only the player whose turn it is can take any actions.
    *   The only “actions” that the other players can take are
        *   receiving resources from the roll phase.
        *   accepting/declining a trade request from the player whose turn it is.
*   Devo Card: Knight
    *   Move the robber to a different hex and steal one random resource from one player that owns a city or settlement that is on that hex.
*   Devo Card: Monopoly
    *   When you play this card announce one type of resource and all other players must pay you all cards they own of that resource type.
*   Devo Card: Road
    *   Place two roads as if you paid for them
*   Devo Card: Year of Plenty
    *   Get any two resources from the bank
*   Devo Cards
    *   A player can buy a development card (costs 1 wool + 1 grain + 1 ore) during their turn.
    *   A player cannot use a devo card on the same turn that they buy them.
    *   If a devo card is used, it is discarded, meaning it cannot be used again for the entire game.
*   Roads
    *   Roads cost 1 brick, 1 lumber
    *   Must be built attached to another city, settlement, or road that you own.
*   Results of rolling
    *   Not a 7: Every player gets resources from the tiles that they are on that have the corresponding number. They get 1 resource for every settlement on that tile, and 2 for every city on that tile. If there are not enough resources left to satisfy everyone's production, no one gains resources.
        *   If the robber is on a tile that had its number rolled, nobody gains any resources from that tile.
    *   7:
        *   All players with more than 7 resource cards in their hand must discard rounded down.
        *   The robber gets moved by the player that rolled a 7 to any other tile. **THE ROBBER MUST BE MOVED FROM ITS CURRENT TILE**.
        *   The player that moved the robber to the new space can steal one random resource from one player that has a city or settlement connected to that new tile. **THIS HAPPENS AFTER THE DISCARD PHASE DETAILED AS THE FIRST STEP OF THE 7 ROLLED CASE**. If there are no players with a settlement or city on that tile, no card may be stolen.
*   Robber
    *   Robber starts in the desert space.
    *   Robber is moved when a 7 is rolled or a knight card is played.
*    Trade
    *   In the second phase of your turn, you may trade with other players. The other players may not trade among themselves, only with the player whose turn it is. 
    *   You may not trade with the bank during another player’s turn
    *   Between player and bank.
        *   Trade 4 of an identical resource for a single resource of another type from the bank
    *   Between player and player.
        *   You and another player may agree to exchange any number of different resource types.
        *   You cannot give away cards
        *   You cannot trade development cards
        *   You cannot trade cards of the same resource type
    *   Maritime trade.
        *   On your turn, you can trade resources using maritime trade during the trade phase even without involving another player. 
        *   For this kind of trade you need to have a settlement or a city that borders the harbor.
        *   Generic Harbor (3:1): You may exchange 3 identical resource cards for any 1 other resource card during your trade phase.
        *   Special Harbor (2:1): There is only 1 special harbor for each type of resource. So, if you earn plenty of a certain type of resource, it can be useful to build on the special harbor for that resource type. The exchange rate of 2:1 only applies to the resource shown on the harbor location. A special harbor does not permit you to trade any other resource type at a more favorable rate (not even 3:1)!

**Test cases from BVA Rules:**



*   Victory Points Calculation (interval, int [0, MAX_VALUE])
    *   Player builds a settlement: Player.VP + 1
    *   Player builds a city: Player.VP + 1
    *   Player plays a VP devo card: Player.VP + 1
    *   Player receives Longest Road card: Player.VP + 2
    *   Player loses Longest Road card: Player.VP - 2
    *   Player receives Largest Army card: Player.VP + 2
    *   Player loses Largest Army card: Player.VP - 2
*   Victory Condition Calculation (boolean)
    *   No player has 10 or more VP: False
    *   Any player has 10 or more VP: True
*   Settlements (build)
    *   Distances from other settlements/cities (boolean)
        *   Is &lt;0 away from another settlement/city: IllegalArgumentExcpetion
        *   Is 0 away from another settlement/city (occupied): False
        *   Is 1 away from another settlement/city: False
        *   Is 2 away from another settlement/city: True
        *   Is 11 away from another settlement/city: True
        *   Is >11 away from another settlement/city: IllegalArgumentExcpetion
    *   Connections to roads (boolean)
        *   Is connected to a road that is owned by the current player: True
        *   Is not connected to a road: False
    *   Has necessary resources (boolean)
        *   Current player has at least 1 brick, 1 lumber, 1 grain, and 1 wool: True
        *   Current player does not have the required resources: False
*   Cities (build)
    *   Has necessary resources (boolean)
        *   Current player has at least 2 grain and 3 ore: True
        *   Current player does not have at least 2 grain and 3 ore: False
    *   Has selected a settlement that will be upgraded to a city (boolean)
        *   Current player has selected a settlement that they own: True
        *   Current player has selected a settlement that they do not own: False
        *   Current player has not selected a settlement: False
*   Roads (build)
    *   Has necessary resources (boolean)
        *   Current player has at least 1 brick, 1 lumber: True
        *   Current player does not have the required resources: False
    *   Space connected to a road, settlement, or city owned by current player (boolean)
        *   Space connected to existing road, settlement, or city: True
        *   Space not connected to existing road, settlement, or city: False
        *   Space is occupied: False
        *   No space is selected: False
*   Turns
    *   Player’s turn: (interval, int [0, numPlayers - 1])
        *   Current player ends turn: (currentTurn + 1) % numPlayers
        *   Player’s turn set to -1: IllegalArgumentException
        *   Player’s turn set to numPlayers: IllegalArgumentException
*   Longest Road
    *   Has maximum number of roads on board (boolean)
        *   Current player has less than 15 roads on board: False
        *   Current player has 15 roads on board: True
    *   Has no other roads on any given path (boolean)
        *   Has at least one road on other paths: False
        *   Has no other roads on the board: True
    *   Has a larger number of roads than another player with the "Longest Road" card (boolean):
        *   Player with the "Longest Road" card does not exist: True
        *   Current Player's number of roads is less than or equal to the number of roads owned by the player with the "Longest Road" card: False
        *   Current Player's number of roads is greater than the number of roads owned by the player with the "Longest Road" card: True
    *   Has necessary number of roads on one continuous road (boolean):
        *   Continuous road consists of less than 5 roads: False
        *   Continuous road consists of 5 roads or more: True
*   Largest Army
    *   Has a larger number of Knight cards played than another player with the "Largest Army" card (boolean):
        *   Player with the "Largest Army" card does not exist: True
        *   Current Player's number of Knight cards played is less than or equal to the number of Knight cards played by the player with the "Largest Army" card: False
        *   Current Player's number of Knight cards played is greater than the number of Knight cards played by the player with the "Largest Army" card: True
    *   Has necessary number of Knight cards played (boolean):
        *   Player has played less than 3 Knight cards: False
        *   Player has played 3 Knight cards or more: True
*   Development Card
    *   User has exact resources to buy card (1 ore, 1 wool, 1 grain): True
    *   User has more resources to buy card: True
    *   User has insufficient resources to buy card (in 1 or more categories): False
    *   User plays more than 1 development card per turn: False
    *   User plays card they purchased in same turn: False
    *   User plays 1 or 0 development cards not purchased in the same turn: True
    *   User plays obtained card:
        *   Knight (14 total) (boolean)
            *   Move the robber to a different hex
                *   Robber is moved to same hex/not moved: False
                *   Robber is moved to any other space: True
            *   Steal one random resource from one player that owns a city or settlement that is on that hex.
                *   User takes resource from player that does not own a city or settlement on the hex: False
                *   User takes more than one resource: Exception
                *   User takes resources from more than one player: Exception
                *   User is able to choose resource that is taken: Exception
        *   Monopoly (2 total) (boolean)
            *   User must choose 1 resource type
                *   User does not choose resource type: False
                *   User chooses more than one resource type: Exception
                *   User chooses 1 resource type and resources are taken from other players: True
                *   User chooses 1 resource type and resources are not taken from other players: False
        *   Road Building (2 total) (boolean)
            *   User places two roads (see road section): True
            *   User places one road: False
            *   User places no roads: False
        *   Year of Plenty (2 total) (boolean)
            *   User obtains two resources from the bank only: True
            *   User obtains more than two resources: False
            *   User obtains one or zero resources: False
        *   Victory Point Card (5 total) (integer)
            *   User victory point count increases by 1: Pass
            *   User victory point count increases by 0 or more than 1: Pass
            *   User victory point decreases: Pass
            *   See Victory point calculation
    *   Card is discarded after use, meaning it cannot be used again for the entire game.
        *   Card Remains in play: Exception
*   Results of Rolling
    *   On a roll that is not seven
        *   Resource payout:
            *   Settlements around hex tiles with the rolled number (Array)
            *   Players who own settlements around the hex tiles with the rolled number (Array)
            *   Players with settlements around hex tiles with rolled number are payed resource card (check count) (Integer)
                *   Player has no resources of type and is payed with a single resource of the type
                *   Player has 
        *   
    *   On a roll that is seven
        *   Card Discard:
            *   For each player:
                *   If player has more than 7 resource cards
                    *   Discard half rounded down (Integer)
                        *   Number of cards in hand is 7
                        *   Number of cards in hand is 8
                        *   Number of cards in hand is 6
        *   Move the robber
            *   Player that rolled 7 gets to move the robber (Boolean and position object):
                *   Robber is placed on the tile it was on already
                *   Robber is moved to tile that it was not on
            *   Robber is placed on new tile
                *   Settlements on tile (Array)
                *   Players who own that settlement (Array)
                *   Player who moved robber gets to steal a single resource card from players who have settlements on the tile
                    *   Player who rolled seven tries to steal resource card from player who does not have any resource cards
                    *   Player who rolled seven tries to steal resource card from player who has one single resource cards
                    *   Player who rolled seven tries to steal resource card from player who has 2 resource cards
*   Trade
    *   No player can attempt to make trades unless it is their turn
        *   Can a player make a trade (Boolean): 
            *   If it is their turn: True
            *   If it isn't their turn: False
    *   4 resource cards of any type must be traded to the bank for a single resource card of the players choice. 
        *   Player attempts to make a trade (Boolean)
            *   Player did not pay 4 resource cards:
                *   throw new IllegalArgumentException: Not enough resources provided for the trade 
            *   Player traded 4 resource cards for 1 resource card from the bank (True)
            *   Player traded 4 resource cards for a single card of the same resource type (False)
    *   Trade amongst players:
        *   On a players turn he/she provides an offer to another player in the game:
            *   Trade offer has a resource card offered up for trade and a resource card traded for
            *   Trade offer has no resource cards offered and a resource card traded for
            *   Trade offer has the same resource card offered as the one being traded for
            *   Trade offer has a resource card offered but no resource cards traded for
            *   Trade offer has multiple resource cards offered for multiple resource cards
    *   Maritime Trade:
        *   Prereq for trade:
            *   Does the player have a settlement on the border of that harbor (Boolean)
        *   Trade:
            *   Based on harbor details required number of resources for trade are payed (True)
            *   Based on harbor details required number of resources for trade are not payed (False)
            *   More resources are payed than what is required for trade (False)
