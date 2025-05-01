package com.han;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicInteger;

public class MyInterfaceFactory {

    private static final AtomicInteger count = new AtomicInteger();

    private static File createJavaFile(String className, MyHandler myHandler) throws IOException {
        String func1Body = myHandler.functionBody("func1");
        String func2Body = myHandler.functionBody("func2");
        String func3Body = myHandler.functionBody("func3");
        String content = "package com.han;\n" +
                "\n" +
                "public class " + className + " implements MyInterface{\n" +
                "MyInterface myInterface;\n" +
                "    @Override\n" +
                "    public void func1() {\n" +
                "        " + func1Body + "\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void func2() {\n" +
                "        " + func2Body + "\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void func3() {\n" +
                "        " + func3Body + "\n" +
                "    }\n" +
                "}";
        File javaFile = new File(className + ".java");
        Files.writeString(javaFile.toPath(), content);
        return javaFile;
    }

    private static String getClassName() {
        return "MyInterface$proxy" + count.incrementAndGet();
    }

    //将生成的class类加载到虚拟机中
    private static MyInterface newInstance(String className, MyHandler myHandler) throws Exception {
        // 1. 使用当前类的类加载器加载指定名称的类
        Class<?> aClass = MyInterfaceFactory.class.getClassLoader().loadClass(className);

        // 2. 获取该类的无参构造方法
        Constructor<?> constructor = aClass.getConstructor();

        // 3. 通过反射调用构造方法创建实例，并强制转换为MyInterface接口类型
        MyInterface proxy = (MyInterface) constructor.newInstance();
        myHandler.setProxy(proxy);
        return proxy;

    }

    public static MyInterface createProxyObject(MyHandler myHandler) throws Exception {
        String className = getClassName();
        File javaFile = createJavaFile(className, myHandler);
        Compiler.compile(javaFile);
        return newInstance("com.han." + className, myHandler);

    }

}
