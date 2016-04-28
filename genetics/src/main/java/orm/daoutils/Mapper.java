package orm.daoutils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

abstract public class Mapper {
	
	public static Mapper make(String key,Object value){
		DefaultMapper mapper = new DefaultMapper();
		return mapper.put(key, value);
	}
	public static Mapper make(){
		DefaultMapper mapper = new DefaultMapper();
		return mapper;
	}
	
	public abstract Mapper put(String key,Object value);
	
	public abstract Map<String,Object> toMap();
	public abstract Map<String,Object> getMap();
	
	/**将javaBean中不为null的属性加到map中
	 * @param obj
	 * @return
	 */
	public abstract Mapper joinBean(Object obj);
	
	/**将pageNum和pageSize转化成offset和row值加入map
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public static Mapper pageTransfer(Integer pageNum,Integer pageSize){
		Mapper mapper = Mapper.make();
		if(null != pageNum && null!=pageSize){
			mapper.put("rows", pageSize);
			mapper.put("offset", pageSize*(pageNum-1));
		}
		return mapper;
	}
}
class DefaultMapper extends Mapper{
	private Map<String,Object> map = new HashMap<String,Object>();
	
	public Mapper put(String key,Object value){
		map.put(key, value);
		return this;
	}
	
	public Map<String,Object> toMap(){
		return map;
	}
	public Map<String,Object> getMap(){
		return map;
	}

	@Override
	public Mapper joinBean(Object obj) {
		Class<?> clz = obj.getClass();
		Method[] mds = clz.getMethods();
		for(Method m:mds){
			if(m.getName().startsWith("get")){
				String field =m.getName().substring(3);
				String fieldName = (field.charAt(0)+"").toLowerCase()+field.substring(1);
				//System.out.println(fieldName);
				try {
					Object f = m.invoke(obj);
					if(f!=null)
						map.put(fieldName, f);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return this;
	}

/*	@Override
	public Mapper pageTransfer(int pageNum, int pageSize) {
		map.put("rows", pageSize);
		map.put("offset", pageSize*(pageNum-1));
		return this;
	}*/
}
