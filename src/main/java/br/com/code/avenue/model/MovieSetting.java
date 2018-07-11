package br.com.code.avenue.model;

import java.util.List;

@SuppressWarnings("rawtypes")
public class MovieSetting { 
	private Long id;
	private String settingName;
	private List<MovieCharacter> charactersList; 
	
	public MovieSetting(Long id, String settingName) {
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
	
	public void setCharactersList(List<MovieCharacter> charactersList) {
		if(charactersList != null) {
			this.charactersList = charactersList;
		}
		else 
			this.charactersList.addAll(charactersList);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MovieSetting [id=").append(id).append(", settingName=").append(settingName)
				.append(", charactersList=").append(charactersList).append("]");
		return builder.toString();
	}
	
	public String toJSON() {
		return "{ \"id\": \""+id+"\", \"name\": \""+settingName+"\", \"characters\":"+charsListToJSON()+" }";
	}
	
	private String charsListToJSON() {
		StringBuilder sb = new StringBuilder("[");
		for (MovieCharacter movieCharacter : charactersList) {
			sb.append(movieCharacter.toJSON()).append(",");
		}
		int lastComma = sb.lastIndexOf(",");
		if(lastComma > 0) sb.deleteCharAt(lastComma);
		
		sb.append("]");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((settingName == null) ? 0 : settingName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovieSetting other = (MovieSetting) obj;
		if (settingName == null) {
			if (other.settingName != null)
				return false;
		} else if (!settingName.equals(other.settingName))
			return false;
		return true;
	}	
}
