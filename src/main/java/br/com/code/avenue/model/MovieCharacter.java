package br.com.code.avenue.model;

import java.util.List;

@SuppressWarnings("rawtypes")
public class MovieCharacter {
	private Long characterId;
	private String characterName;
	private List wordCountsList;
	
	public MovieCharacter(Long characterId, String characterName) {
		this.characterId = characterId;
		this.characterName = characterName;
		this.wordCountsList = null;
	}

	public Long getCharacterId() {
		return characterId;
	}

	public String getCharacterName() {
		return characterName;
	}

	public List getWordCounts() {
		return wordCountsList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MovieCharacter [characterId=").append(characterId).append(", characterName=")
				.append(characterName).append(", wordCounts=").append(wordCountsList).append("]");
		return builder.toString();
	}
	
	public String toJSON() {
		return "{ \"id\": \""+characterId+"\", \"name\": \""+characterName+"\", \"wordCounts\":[] }";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((characterName == null) ? 0 : characterName.hashCode());
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
		MovieCharacter other = (MovieCharacter) obj;
		if (characterName == null) {
			if (other.characterName != null)
				return false;
		} else if (!characterName.equals(other.characterName))
			return false;
		return true;
	}
}
