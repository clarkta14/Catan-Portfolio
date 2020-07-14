
(This project was created by four main contributors. My contributions are under "clarkta14")

**Settlers of Catan**

**Mutations**

[Here](https://docs.google.com/document/d/1RmSGyU0niUe0Z6ra4Vp2bjW37lNQ9MU6zigtQX2mL8U/edit) you can find a document that contains our PIT Mutation testing results and comments.

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
    *   Player builds a settlement: Player.VP + 1 [9a90bc1 : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/9a90bc1)
    *   Player builds a city: Player.VP + 1 [acd9342 : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/acd9342)
    *   Player gets a VP devo card: Player.VP + 1 Done
    *   Player receives Longest Road card: Player.VP + 2 Done
    *   Player loses Longest Road card: Player.VP - 2 Done
    *   Player receives Largest Army card: Player.VP + 2 Done
    *   Player loses Largest Army card: Player.VP - 2 Done
*   Victory Condition Calculation (boolean)
    *   No player has 10 or more VP: False Done
    *   Any player has 10 or more VP: True Done
*   Settlements (build)
    *   Distances from other settlements/cities (boolean)
        *   Is 0 away from another settlement/city (occupied): False [3b172d2 : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/3b172d2)
        *   Is 1 away from another settlement/city: False [28df728 : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/28df728)
        *   Is 2 away from another settlement/city: True [82a0b28 : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/82a0b28)
        *   Is 11 away from another settlement/city: True [9ac2872 : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/9ac2872)
    *   Connections to roads (boolean)
        *   Is connected to a road that is owned by the current player: True Done
        *   Is not connected to a road: False Done
    *   Has necessary resources to build settlement (boolean)
        *   Current player has at least 1 brick, 1 lumber, 1 grain, and 1 wool: True [18ccceb : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/18ccceb)
        *   Current player does not have the required resources: False [02dd93a : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/02dd93a)
*   Cities (build)
    *   Has necessary resources (boolean)
        *   Current player has at least 2 grain and 3 ore: True [1c57e33 : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/1c57e33)
        *   Current player does not have at least 2 grain and 3 ore: False [f7a7497 : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/f7a7497)
    *   Has selected a settlement that will be upgraded to a city (boolean)
        *   Current player has selected a settlement that they own: True [1c57e33 : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/1c57e33)
        *   Current player has selected a settlement that they do not own: False [0291f57 : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/0291f57)
        *   Current player has not selected a settlement: False [acd9342 : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/acd9342)
*   Roads (build)
    *   Has necessary resources (boolean)
        *   Current player has at least 1 brick, 1 lumber: True [78d26d2 : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/78d26d2)
        *   Current player does not have the required resources: False [c9a8c7c : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/c9a8c7c)
    *   Space connected to a road, settlement, or city owned by current player (boolean)
        *   Space connected to existing road, settlement, or city: True [4f44ed5 : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/4f44ed5)
        *   Space not connected to existing road, settlement, or city: False [88184a8 : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/88184a8)
        *   Space is occupied: False Done
        *   No space is selected: False Done
*   Turns
    *   Player’s turn: (interval, int [0, numPlayers - 1])
        *   Current player ends turn: (currentTurn + 1) % numPlayers [1855e2d : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/1855e2d)
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
        *   Player with the "Largest Army" card does not exist: True Done
        *   Current Player's number of Knight cards played is less than or equal to the number of Knight cards played by the player with the "Largest Army" card: False Done
        *   Current Player's number of Knight cards played is greater than the number of Knight cards played by the player with the "Largest Army" card: True Done
    *   Has necessary number of Knight cards played (boolean):
        *   Player has played less than 3 Knight cards: False Done
        *   Player has played 3 Knight cards or more: True Done
*   Development Card
    *   User has exact resources to buy card (1 ore, 1 wool, 1 grain): True [e6146bd : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/e6146bd)
    *   User has more resources to buy card: True Done
    *   User has insufficient resources to buy card (in 1 or more categories): False [e6146bd : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/e6146bd)
    *   **User plays more than 1 development card per turn: False** Done
    *   **User plays card they purchased in same turn: False** Done
    *   User plays 1 or 0 development cards not purchased in the same turn: True Done
    *   User plays obtained card:
        *   Knight (14 total) (boolean)
            *   Move the robber to a different hex
                *   Robber is moved to same hex/not moved: False Done
                *   Robber is moved to any other space: True Done
            *   Steal one random resource from one player that owns a city or settlement that is on that hex.
                *   User takes resource from player that does not own a city or settlement on the hex: False Done
                *   User takes more than one resource: Exception Done
                *   User takes resources from more than one player: Exception Done
                *   User is able to choose resource that is taken: Exception Done
        *   Monopoly (2 total) (boolean)
            *   User must choose 1 resource type
                *   User does not choose resource type: False Done
                *   User chooses more than one resource type: Exception Done
                *   User chooses 1 resource type and resources are taken from other players: True Done
                *   User chooses 1 resource type and resources are not taken from other players: False Done
        *   Road Building (2 total) (boolean)
            *   User places two roads (see road section): True Done
            *   User places one road: True Done
            *   User places no roads: True Done
        *   Year of Plenty (2 total) (boolean)
            *   User obtains two resources from the bank only: True #caa918d, #aa192fa
            *   User obtains more than two resources: False #35bf30a
            *   User obtains one or zero resources: False #35bf30a
        *   Victory Point Card (5 total) (integer)
            *   User victory point count increases by 1: Pass Done
            *   User victory point count increases by 0 or more than 1: False Done
            *   User victory point decreases: False Done
    *   Card is discarded after use, meaning it cannot be used again for the entire game.
        *   Card Remains in play: Exception Done
*   Results of Rolling
    *   On a roll that is not seven
        *   Resource payout:
            *   Players with settlements around hex tiles with rolled number are payed resource card (check count) (Integer) Done
    *   On a roll that is seven
        *   Card Discard:
            *   For each player:
                *   **If player has more than 7 resource cards**
                    *   Discard half rounded down (Integer)
                        *   Number of cards in hand is 7
                        *   Number of cards in hand is 8
                        *   Number of cards in hand is 6
        *   Move the robber
            *   Player that rolled 7 gets to move the robber (Boolean and position object):
                *   Robber is placed on the tile it was on already Done
                *   Robber is moved to tile that it was not on Done
            *   Robber is placed on new tile
                *   Player who moved robber gets to steal a single resource card from players who have settlements on the tile
                    *   Player who rolled seven tries to steal resource card from player who does not have any resource cards Done
                    *   Player who rolled seven tries to steal resource card from player who has one single resource cards Done
                    *   Player who rolled seven tries to steal resource card from player who has 2 resource cards Done
*   Trade
    *   No player can attempt to make trades unless it is their turn
        *   Can a player make a trade (Boolean): 
            *   If it is their turn: True Done
            *   If it isn't their turn: False Done
    *   4 resource cards of any type must be traded to the bank for a single resource card of the players choice. 
        *   Player attempts to make a trade (Boolean)
            *   Player did not pay 4 resource cards:
                *   throw new IllegalArgumentException: Not enough resources provided for the trade [b08dee5 : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/b08dee5)
            *   Player traded 4 resource cards for 1 resource card from the bank (True) [6f18634 : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/6f18634)
            *   Player traded 4 resource cards for a single card of the same resource type (False) [99df99d : CatanBoardTest.java](https://github.com/rhit-csse376/S2-C-Catan/commit/99df99d)
    *   Trade amongst players:
        *   On a players turn he/she provides an offer to another player in the game:
            *   Trade offer has a resource card offered up for trade and a resource card traded for Done
            *   Trade offer has no resource cards offered and a resource card traded for Done
            *   Trade offer has the same resource card offered as the one being traded for Done
            *   Trade offer has a resource card offered but no resource cards traded for Done
            *   Trade offer has multiple resource cards offered for multiple resource cards Done
    *   Maritime Trade:
        *   Prereq for trade:
            *   Does the player have a settlement on the border of that harbor (Boolean) Done
        *   Trade:
            *   Based on harbor details required number of resources for trade are payed (True) Done
            *   Based on harbor details required number of resources for trade are not payed (False) Done
            *   More resources are payed than what is required for trade (False) Done
