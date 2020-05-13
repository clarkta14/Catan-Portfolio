package objects;

import gui.Messages;

public enum DevelopmentCardType {
	knight(Messages.getString("DevelopmentCardType.knight")),
	progress(Messages.getString("DevelopmentCardType.progress")),
	victory_point(Messages.getString("DevelopmentCardType.victory_point")),
	monopoly_card(Messages.getString("DevelopmentCardType.monopoly_card")),
	year_of_plenty_card(Messages.getString("DevelopmentCardType.year_of_plenty_card")),
	road_building_card(Messages.getString("DevelopmentCardType.road_building_card")),
	largest_army_card(Messages.getString("DevelopmentCardType.largest_army_card"));
	
	private String devCardTypeText;

	private DevelopmentCardType(String devCardTypeText) {
		this.devCardTypeText = devCardTypeText;
	}

	public String getDevCardTypeText() {
		return devCardTypeText;
	}
}
