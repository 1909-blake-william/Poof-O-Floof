package model;

public class AnimalBasic {
	private int animalId;
	private String animalType;
	private String species;
  
	private int age;
	private String gender;
	private double size;
	private String url;
	
	public AnimalBasic() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AnimalBasic(int animalId, String animalType, String species, int age, String gender, double size,
			String url) {
		super();
		this.animalId = animalId;
		this.animalType = animalType;
		this.species = species;
		this.age = age;
		this.gender = gender;
		this.size = size;
		this.url = url;
	}

	public int getAnimalId() {
		return animalId;
	}

	public void setAnimalId(int animalId) {
		this.animalId = animalId;
	}

	public String getAnimalType() {
		return animalType;
	}

	public void setAnimalType(String animalType) {
		this.animalType = animalType;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + animalId;
		result = prime * result + ((animalType == null) ? 0 : animalType.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		long temp;
		temp = Double.doubleToLongBits(size);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((species == null) ? 0 : species.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		AnimalBasic other = (AnimalBasic) obj;
		if (age != other.age)
			return false;
		if (animalId != other.animalId)
			return false;
		if (animalType == null) {
			if (other.animalType != null)
				return false;
		} else if (!animalType.equals(other.animalType))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (Double.doubleToLongBits(size) != Double.doubleToLongBits(other.size))
			return false;
		if (species == null) {
			if (other.species != null)
				return false;
		} else if (!species.equals(other.species))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AnimalBasic [animalId=" + animalId + ", animalType=" + animalType + ", species=" + species + ", age="
				+ age + ", gender=" + gender + ", size=" + size + ", url=" + url + "]";
	}
}
