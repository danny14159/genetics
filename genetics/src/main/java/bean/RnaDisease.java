package bean;
/**
 * @author 
 *
 */
public class RnaDisease  {

	/**
	 * 
	 */
	private Integer disease;

	/**
	 * 
	 */
	private Integer rna;

	/**
	 * 
	 */
	private String description;

	public void setDisease(Integer disease){
		this.disease=disease;
	}

	public Integer getDisease(){
		return this.disease;
	}

	public void setRna(Integer rna){
		this.rna=rna;
	}

	public RnaDisease(Integer disease, Integer rna) {
		super();
		this.disease = disease;
		this.rna = rna;
	}

	public RnaDisease() {
		super();
	}

	public Integer getRna(){
		return this.rna;
	}

	public void setDescription(String description){
		this.description=description;
	}

	public String getDescription(){
		return this.description;
	}

}