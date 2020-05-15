package objects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class PlayersController {
	private ArrayList<Player> players;
	private boolean initialSetup;
	private boolean backwardsSetup;
	private int currentPlayer;
	private int totalNumOfPlayers;
	private Player playerWithLargestArmy;
	private Player playerWithLongestRoad;
	private LongestRoad longestRoadHelper;
	private int curLongestRoadLength = -1;

	public PlayersController(int numOfPlayers) {
		this.players = createPlayers(numOfPlayers);
		this.longestRoadHelper = new LongestRoad(numOfPlayers);
		this.initialSetup = true;
		this.backwardsSetup = false;
		this.currentPlayer = 0;
		this.totalNumOfPlayers = numOfPlayers;
		this.playerWithLargestArmy = null;
		this.playerWithLongestRoad = null;
	}

	private ArrayList<Player> createPlayers(int num) {
		ArrayList<Player> plyrs = new ArrayList<>();
		Player p = new Player(Color.BLUE);
		plyrs.add(p);
		if (num != 3) {
			p = new Player(Color.RED);
			plyrs.add(p);
		}
		p = new Player(Color.WHITE);
		plyrs.add(p);
		p = new Player(Color.ORANGE);
		plyrs.add(p);
		Collections.shuffle(plyrs);
		return plyrs;
	}

	public Player getCurrentPlayer() {
		return this.players.get(this.currentPlayer);
	}

	public Player getPlayer(int playerNum) {
		return this.players.get(playerNum);
	}

	public int getCurrentPlayerNum() {
		return this.currentPlayer;
	}

	public void nextPlayer() {
		if (this.initialSetup) {
			if (this.backwardsSetup) {
				if (this.currentPlayer - 1 < 0) {
					this.initialSetup = false;
					this.backwardsSetup = false;
				} else {
					this.currentPlayer--;
				}
			} else {
				if (this.currentPlayer + 1 == this.players.size()) {
					this.backwardsSetup = true;
				} else {
					this.currentPlayer++;
				}
			}
		} else {
			this.currentPlayer = (this.currentPlayer + 1) % this.players.size();
		}
	}

	public boolean isInitialSetup() {
		return this.initialSetup;
	}

	public boolean isBackwardsSetup() {
		return this.backwardsSetup;
	}

	public int getTotalNumOfPlayers() {
		return this.totalNumOfPlayers;
	}

	public void determineLargestArmy(Player chosenPlayer) {
		if (chosenPlayer.knightsPlayed >= 3) {
			if (playerWithLargestArmy == null) {
				addLargestArmyCardToPlayer(chosenPlayer);
			} else if (!chosenPlayer.equals(playerWithLargestArmy)
					&& chosenPlayer.knightsPlayed > playerWithLargestArmy.knightsPlayed) {
				removeLargestArmyCardFromPreviousCardOwner();
				addLargestArmyCardToPlayer(chosenPlayer);
			}
		}
	}

	private void removeLargestArmyCardFromPreviousCardOwner() {
		playerWithLargestArmy.removeDevelopmentCard(DevelopmentCardType.largest_army_card);
		playerWithLargestArmy.alterVictoryPoints(VictoryPoints.largest_army_remove);
	}

	private void addLargestArmyCardToPlayer(Player chosenPlayer) {
		chosenPlayer.addDevelopmentCard(new LargestArmyDevelopmentCard());
		chosenPlayer.alterVictoryPoints(VictoryPoints.largest_army_add);
		playerWithLargestArmy = chosenPlayer;
	}

	public int getNumKnightsOfLargestArmy() {
		return (playerWithLargestArmy == null) ? 0 : playerWithLargestArmy.knightsPlayed;
	}

	public void addRoadForLongestRoad(int tile, ArrayList<Integer> corners) {
		this.longestRoadHelper.addRoadForPlayer(this.currentPlayer, tile, corners);
		int curPlayerLongestRoad = this.longestRoadHelper.getLongestRoadForPlayer(this.getCurrentPlayerNum());
		if(curPlayerLongestRoad >= 5 && curPlayerLongestRoad > this.curLongestRoadLength) {
			this.curLongestRoadLength = curPlayerLongestRoad;
			addLongestRoadVP();
		}
	}
	
	private void addLongestRoadVP() {
		if(this.playerWithLongestRoad != null) {
			this.playerWithLongestRoad.alterVictoryPoints(VictoryPoints.longest_road_remove);
			this.playerWithLongestRoad.removeDevelopmentCard(DevelopmentCardType.longest_road_card);
		}
		this.playerWithLongestRoad = this.getCurrentPlayer();
		this.playerWithLongestRoad.addDevelopmentCard(new LongestRoadDevelopmentCard());
		this.playerWithLongestRoad.alterVictoryPoints(VictoryPoints.longest_road_add);
		
	}
}
