package bytecode.javassist;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.testng.annotations.Test;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import orm.jdbc.CrudRepository;

/**
 * 
 * 
 * @author mypiglet
 *
 */
public class JavassistTest {

	/**
	 * 查看字节码：javap -c classname
	 */
	@Test(enabled = false)
	public void test() throws NotFoundException, CannotCompileException, IOException, InstantiationException,
			IllegalAccessException {

		ClassPool pool = ClassPool.getDefault();
		ClassClassPath cp = new ClassClassPath(CrudRepository.class);
		pool.insertClassPath(cp);
		CtClass cc = pool.getCtClass("bytecode.javassist.UserHandler");
		CtMethod m = cc.getDeclaredMethod("getUser");
		m.insertBefore("{ System.out.println($1); }");
		cc.writeFile();

	}

	@Test(enabled = false)
	public void test2() throws CannotCompileException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.makeClass("MyUser");
		CtMethod mthd = CtNewMethod.make("public void handle() { System.out.println(\"处理中\"); }", cc);
		cc.addMethod(mthd);
		Class<?> clazz = cc.toClass();
		Object instance = clazz.newInstance();

		Method[] arr = instance.getClass().getDeclaredMethods();

		Method m = arr[0];
		m.invoke(instance);

		System.out.println(arr);

	}

	@Test(enabled = true)
	public void test3() {

		

	}

}
