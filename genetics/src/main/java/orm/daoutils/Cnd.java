package orm.daoutils;

import java.sql.PreparedStatement;

/**
 * @author
 *
 */
public class Cnd{
	
	/**
	 */
	private String colName;
	
	/**
	 */
	private String operator;
	
	/**
	 * 
	 */
	private Object colVal;
	
	/**
	 * �Ƿ���prepared����
	 */
	private boolean prepared = true;
	
	public Cnd() {
	}

	private Cnd(String colName, String operator, Object colVal) {
		this.colName = colName;
		this.operator = operator;
		this.colVal = colVal;
	}
	
	private Cnd(String colName, String operator, Object colVal,
			Cnd prevCondition) {
		this.colName = colName;
		this.operator = operator;
		this.colVal = colVal;
		this.prevCondition = prevCondition;
	}
	private Cnd(String colName, String operator, Object colVal,
			Cnd prevCondition,boolean prepared) {
		this.colName = colName;
		this.operator = operator;
		this.colVal = colVal;
		this.prevCondition = prevCondition;
		this.prepared = prepared;
	}

	private Cnd nextCondition = null;
	
	private Cnd prevCondition = null;
	
	
	private String nextConnector = "";
	
	
	/**
	 * @param colName
	 * @param operator
	 * @param colVal
	 * @return
	 */
	public Cnd and(String colName, String operator, Object colVal){
		this.nextConnector = " AND ";
		return this.nextCondition = new Cnd(colName, operator, colVal,this);
	}
	/**
	 * @param colName
	 * @param operator
	 * @param colVal
	 * @return
	 */
	public Cnd or(String colName, String operator, Object colVal){
		this.nextConnector = " OR ";
		return this.nextCondition = new Cnd(colName, operator, colVal,this);
	}
	
	public Cnd asc(String colName){
		this.nextConnector = "";
		return this.nextCondition = new Cnd(" ORDER BY ",colName + " ASC ","" ,this,false);
	}
	
	public Cnd desc(String colName){
		this.nextConnector = "";
		return this.nextCondition = new Cnd(" ORDER BY ",colName + " DESC","" ,this,false);
	}
	
	
	public Cnd andIsNull(String colName){
		this.nextConnector = " AND ";
		return this.nextCondition = new Cnd(colName, "IS", "NULL", this, false);
	}
	public Cnd isNull(String colName){
		this.nextConnector = "";
		return this.nextCondition = new Cnd(colName, "IS", "NULL", this, false);
	}
	public Cnd orIsNull(String colName){
		this.nextConnector = " OR ";
		return this.nextCondition = new Cnd(colName, "IS", "NULL", this, false);
	}
	
	public String toPreparedSql(){
		
		//������cnd��Ĭ�Ϲ��캯����ʵ���������û������
		boolean hasCnd = hasCondition();
		
		String sql = "";
		if(hasCnd) sql += " WHERE ";
		
		Cnd curr = head();
		
		while( curr!= null){
			if(curr.colName!=null){
				if(curr.prepared){
					if(hasCnd)
						sql+=curr.colName+" " + curr.operator+" ?" + curr.nextConnector;
				}
				else{
					sql+=curr.colName+" " + curr.operator +" "+ curr.nextConnector;
				}
			}
			curr = curr.nextCondition;
		}
		
		return sql;
	}
	
	/**�ж�cnd�����Ƿ����where����
	 * @return
	 */
	public boolean hasCondition(){
		Cnd curr = head();
		
		while( curr!= null){
			if(curr.prepared && curr.colName!=null ) return true;
			curr = curr.nextCondition;
		}
		return false;
	}
	
	@SuppressWarnings("unused")
	private int getLength(){
		
		Cnd curr = this;
		int len = 1;
		while(curr.prevCondition !=null ){
			curr = curr.prevCondition;
			len++;
		}
		return len;
	}
	
	private Cnd head(){
		Cnd curr = this;
		while(curr.prevCondition !=null ){
			curr = curr.prevCondition;
		}
		
		return curr;
	}
	@SuppressWarnings("unused")
	private Cnd tail(){
		Cnd curr = this;
		while(curr.nextCondition !=null ){
			curr = curr.nextCondition;
		}
		
		return curr;
	}
	
	public void setAllStatementsVal(PreparedStatement ps,int beginIndex){
		
		Cnd curr = head();
		
		int i =beginIndex ;
		while( curr!= null){
			if(!curr.prepared || curr.colName == null){
				curr = curr.nextCondition;
				continue;
			}
			DBUtil.setStatementVal(ps,i,curr.colVal);
			i++;
			curr = curr.nextCondition;
		}
//		System.out.println();
	}
	
	public static Cnd where(String colName, String operator, Object colVal){
		
		return new Cnd(colName, operator, colVal);
	}
	
	public static Cnd where(){
		
		return new Cnd();
	}
}
