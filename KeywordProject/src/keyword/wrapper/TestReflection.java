package keyword.wrapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import wrapper.GenericWrapperMethods;

public class TestReflection {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, InterruptedException {

		Class<GenericWrapperMethods> wrapper = GenericWrapperMethods.class;
	    Object wM = wrapper.newInstance();
	    
	    Method[] methods = wrapper.getDeclaredMethods();
	    for (Method method : methods) {
			System.out.println(method.getName());
		}
	    Thread.sleep(10000L);
	    Constructor<?>[] c=wrapper.getConstructors();
	    System.out.println("Length " + c.length);
	    
	    for (Constructor<?> cons :  wrapper.getConstructors()) {
			System.out.println(cons.getName());
			System.out.println(cons.getParameterCount());
			System.out.println(cons.toString());
		}
	}

}
