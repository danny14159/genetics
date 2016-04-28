package bean;
/**
 * @author 
 *
 */
public class Rna  {

	public Rna(String rna) {
		super();
		this.rna = rna;
	}

	/**
	 * 
	 */
	private Integer id;

	public Rna() {
		super();
	}

	/**
	 * 
	 */
	private String rna;

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setRna(String rna){
		this.rna=rna;
	}

	public String getRna(){
		return this.rna;
	}

}