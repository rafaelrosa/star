package br.com.code.avenue.model;

import java.util.List;

@SuppressWarnings("rawtypes")
public class MovieSettings {
	private Long id;
	private String settingName;
	private List charactersList; 
	
	public MovieSettings(Long id, String settingName) {
		this.id = id;
		this.settingName = settingName;
		this.charactersList = null;
	}

	public Long getId() {
		return id;
	}

	public String getSettingName() {
		return settingName;
	}
	
	public List getCharactersList() {
		return charactersList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MovieSettings [id=").append(id).append(", settingName=").append(settingName)
				.append(", charactersList=").append(charactersList).append("]");
		return builder.toString();
	}
	
	public String toJSON() {
		return "{ \"id\": \""+id+"\", \"name\": \""+settingName+"\", \"characters\":[] }";
	}
}
