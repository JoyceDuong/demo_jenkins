package utilities;

import com.github.javafaker.Faker;

public class FakerUtil {
	Faker faker;
 
	public static FakerUtil getData() {
		return new FakerUtil();
	}
	
	public FakerUtil(){
		 faker = new Faker();
	}
	
	public String getFirstName() {
		return faker.name().firstName();
	}
	
	public String getLastName() {
		return faker.name().lastName();
	}
	
	
	public String getFullName() {
		return getFirstName() + " " + getLastName() ;
	}public String getEmail() {
		return faker.name().firstName() + "@gmail.com";
	}
	
	
}
