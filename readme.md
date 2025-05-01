```javamarkdown
public static MyInterface newInstance(String className)throws Exception{
        // 1. ʹ�õ�ǰ��������������ָ�����Ƶ���
        Class<?> aClass=MyInterfaceFactory.class.getClassLoader().loadClass(className);

        // 2. ��ȡ������޲ι��췽��
        Constructor<?> constructor=aClass.getConstructor();

        // 3. ͨ��������ù��췽������ʵ������ǿ��ת��ΪMyInterface�ӿ�����
        MyInterface proxy=(MyInterface)constructor.newInstance();

        return proxy;
        }
```

### ���������

1. **����ؽ׶�**  
   `loadClass(className)` ͨ��������������� `createJavaFile()` ��̬���ɵ��ࣨ�� `MyInterface$proxy1`
   ��������δ���������·�����󣬻��׳� `ClassNotFoundException`��

2. **���췽����ȡ**  
   `getConstructor()` ��ȡĿ������޲ι������췽���������ɵ���û����ʽ�����޲ι����������׳� `NoSuchMethodException`��

3. **ʵ������������ת��**  
   `newInstance()` ͨ��������ù��췽������ʵ������ǿ��ת��Ϊ `MyInterface` �ӿ����͡���Ŀ����δ��ȷʵ�ָýӿڣ����׳� `ClassCastException`��

4. **�쳣����**  
   ���������׳� `Exception`�����ڵ���ʱ��������ܵ��쳣���������ʧ�ܡ������������á�ʵ�����쳣�ȣ���

### �ؼ��㣺

- **��̬�������**���˷������ڼ��ز�ʵ������̬���ɵĴ����ࣨ�� `MyInterface$proxy1`������ʵ�ֶ�̬����Ĺؼ����衣
- **����������**����ȷ�� `createJavaFile()` ���ɵ� `.java` �ļ���ͨ�� `Compiler.compile()` �ɹ�����Ϊ `.class` �ļ������� `loadClass` ��ʧ�ܡ�
- **�̰߳�ȫ**��ʹ�� `AtomicInteger` ��֤���ɵ�����Ψһ��������߳��³�ͻ��

### ���͵������̣�

```javamarkdown
// 1. ����JavaԴ�ļ�
File
javaFile = MyInterfaceFactory.createJavaFile();
// 2. ����Դ�ļ�Ϊ�ֽ���
Compiler.compile(javaFile);
// 3. ���ز�ʵ�������ɵ���
MyInterface
proxy = MyInterfaceFactory.newInstance("MyInterface$proxy1");
```

## ΪʲôMyInterfaceFactory������������Լ��ؼ��ش����������Ӧ��Class����

### ԭ�������

1. **��������ļ̳й�ϵ**  
   `MyInterfaceFactory.class.getClassLoader()` ���ص��� **Ӧ�����������AppClassLoader��**�������������λ�õ��ࣺ
    - ��Ŀ������ `target/classes` Ŀ¼
    - �����⣨�� `jar` �ļ���

   ����̬���ɵ� `MyInterface$proxy1.class` �����뵽 `target/classes/com/han/` Ŀ¼ʱ��Ӧ�����������ɨ�赽���ࡣ

2. **��·����Classpath����ƥ��**
    - ���ɵ��� `MyInterface$proxy1` ����������Ϊ `com.han.MyInterface$proxy1`
    - �� `.class` �ļ�·������� Java �淶��`target/classes/com/han/MyInterface$proxy1.class`
    - Ӧ���������Ĭ��ɨ�� `target/classes` Ŀ¼������ܶ�λ���ࡣ

3. **����ص�˫��ί��ģ��**  
   ����ع�����ѭ **˫��ί�ɻ���**��
   ```
   AppClassLoader��Ӧ�����������
       �� ί�м���
   ExtClassLoader����չ���������
       �� ί�м���
   BootstrapClassLoader���������������
   ```

    - �������� Java ���Ŀ⣨�� `java.lang.Object`������ `BootstrapClassLoader` ����
    - ����������չ�⣨�� `javax.*`������ `ExtClassLoader` ����
    - ������ `AppClassLoader` �Լ�����

   ��̬���ɵ� `com.han.MyInterface$proxy1` �����ں���/��չ��⣬������ `AppClassLoader` ���ء�

4. **��������·���İ�**  
   �� `main` �����е����� `Compiler.compile(javaFile)`������÷���ʵ���ˣ�
   ```javamarkdown
   // α����ʾ��
   public static void compile(File javaFile) {
       // ���� javac ���� .java �ļ��� target/classes Ŀ¼
       runCommand("javac -d target/classes " + javaFile.getAbsolutePath());
   }
   ```

   ��ȷ���˱����� `.class` �ļ�λ����·���£����������ֱ�ӷ��ʡ�

---

### �ؼ����ۣ�

- **�����Զ����������**����Ϊ��̬���ɵ��౻���뵽Ĭ����·����`target/classes`����Ӧ�����������Ȼ֧�ּ��ء�
- **������ȷ�ı������**���� `Compiler.compile()` δ��ȷ��������·������`loadClass()` ���׳� `ClassNotFoundException`��
- **����һ����**�����ɵ���������ļ�·��ƥ�䣨�� `com.han.MyInterface$proxy1` ��Ӧ `com/han/MyInterface$proxy1.class`����

---

### ��֤��ʽ��

���� `newInstance` ��������ӵ�����־��

```javamarkdown
System.out.println("��·��: "+System.getProperty("java.class.path"));
        System.out.println("����������: "+MyInterfaceFactory.class.getClassLoader().getClass().getName());
```

���ʾ����

```
��·��: /path/to/project/target/classes:/path/to/dependency.jar
����������: jdk.internal.loader.ClassLoaders$AppClassLoader
```

ȷ����·���ͼ��������ͷ���Ԥ�ڡ�