package bean;
/**
 * @author 
 *
 */
public class Disease  {

	/**
	 * 
	 */
	private Integer id;

	public Disease() {
		super();
	}

	public Disease(String disease) {
		super();
		this.disease = disease;
	}

	/**
	 * 
	 */
	private String disease;

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setDisease(String disease){
		this.disease=disease;
	}

	public String getDisease(){
		return this.disease;
	}

}