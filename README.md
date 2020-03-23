# Settlers of Catan

## Definition of Done
The program is "done" when it can determine a winner according to the rules and procedures of the game. A player is determined to be the winner when they gain at least 10 Victory Points. Victory Points are gained as settlements (worth 1 Victory Point each) and cities (worth 1 Victory Point each) are built along the board. Players can buy settlements and upgrade existing settlements into cities by collecting enough resources it takes to purchase them. Resources (which consist of brick, grain, lumber, ore, and wool) are gained when a player places their second settlement on the board, when a player trades resources with an opponent or the bank, and when the number rolled from the dice on the player's turn matches the number token of a tile that a player's settlement borders.

## Game Components:
- 19 terrain hexes (3 brick, 4 lumber, 3 ore, 4 grain, 4 wool, and 1 desert)
- 9 harbor pieces (4x General 3:1, 1x Sheep 2:1, 1x Brick 2:1, 1x Lumber 2:1, 1x Grain 3:1, 1x Ore 2:1)
- 18 number tokens (1x black 2, 2x black 3, 2x black 4, 2x black 5, 2x red 6, 2x red 8, 2x black 9, 2x black 10, 2x 11, 1x black 12)
- 95 resources (19 brick, 19 grain, 19 lumber, 19 ore, 19 wool)
- 25 development cards (14 knight, 2 road building, 2 year of plenty, 2 monopoly, 5 Victory Point)
- 2 special cards: "Longest Road" and "Largest Army"
- 16 cities (4 for each player)
- 20 settlements (5 for each player)
- 60 roads (15 for each player)
- 2 dice
- 1 robber

## Board Setup:
(For beginners use default setup in .png)
- Randomize all 19 terrain hexes into one large hexagon shape on the board
- Place all harbor pieces around the board adjacent to every other hex (if there is a harbor piece between two hexes, the hex to which they point toward will be randomized by the program)
- Place all number tokens onto each non-desert hex in random order
- Place robber on the desert hex

## Player Setup:
- Each player will take turns and place one settlement onto an intersection on the board, then place a road connected to the settlement onto one of the edges. (Settlements and cities cannot be placed within two spaces of each other).
- Repeat the last step. A player now gains one resource for every settlement that borders a hex with that resource.

## Player Turn:
- Roll the dice (Random number between 2-12). If any players own a settlement or city next to a hex matching the number rolled, the player gains one resource for each settlement on that resource and two for each city on the resource.
- The player then has the option to either Buy, Trade, Play a development card (if the player has one), or end their turn.

## Rolling a 7:
- If the number rolled is 7, each player with 8 or more resources must discard half of those resources.
- Then, the player who rolled must move the robber to another hex (which will now no longer produce resources).
- If an opponent is adjacent to the tile, the player can choose the adjactent oppoent (if more than one) for the player to steal a random resource from.

## Buying:
- Road = 1 lumber + 1 brick
- Build Settlement = 1 lumber + 1 brick + 1 grain + 1 wool (settlements can only be built if there are three empty intersections adjacent to them; each settlement must also connect to one or your roads)
- Replace Settlement with City = 2 grain + 3 ore
- Development card = 1 grain + 1 wool + 1 ore

## Trading:
- A player can either trade resources with another player (1:1 trades only) or trade resourses with the bank (a harbor piece that a settlement is adjacent to).
- If the player decides to trade with the bank, they must trade a ratio of a demanded resource (if specified) for one of any resource that they player chooses (i.e. if the harbor piece says "Grains 2:1", the player must trade 2 grain resources for one of any resource).

## Development cards:
You can play a development card anytime during your turn except for the turn you picked one up on.
- Knight card = lets player move the robber
- Road Building = the player can place 2 roads as if they just built them
- Year of Plenty = the player can draw 2 resource cards of their choice from the bank
- Monopoly = the player can claim all resource cards of a specific declared type
- Victory Point = 1 additional victory point is added to the player's total and doesn't need to be played to win
