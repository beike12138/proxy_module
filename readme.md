�����Ŀʵ����һ��**���ڶ�̬���� Java ��Ĵ���ģʽ**��ͨ����̬���ɴ���������ǿ�ӿڷ�������Ϊ�������Ǹ���Ŀ�ĺ��Ľṹ�͹����ܽ᣺

---

### 1. ����Ŀ��

- ʵ��һ���򵥵� **��̬�������**���ڲ��޸�ԭʼ�ӿ�ʵ�ֵ�ǰ���£�ͨ���������Է������ý�����ǿ��

---

### 2. �ؼ����˵��

#### `MyInterface`

- �����˱������Ŀ��ӿڣ���������������
    - `func1()`
    - `func2()`
    - `func3()`

#### `MyInterfaceFactory`

- ��̬����������ĺ��Ĺ����ࡣ
- ��Ҫ���ܰ�����
    - `createJavaFile`�����ݴ���� `MyHandler` ��̬���� `.java` �ļ����ݣ��ַ���ƴ�ӷ�ʽ����Դ�룩��
    - `getClassName`��Ϊÿ������������Ψһ���ƣ��� `MyInterface$proxy1`��
    - `newInstance`��ʹ������������ر������ಢ����ʵ����
    - `createProxyObject`�����Ⱪ¶�ķ��������ڴ���������ǿ�߼��Ĵ������

#### `MyHandler`

- ��������ǿ�߼��Ľӿڡ�
- ���������ؼ�������
    - `functionBody(String methodName)`
      ������ָ������������ǿ�����塣
    - `setProxy(MyInterface proxy)`
      �����ô���������������/��ʽ���á�

#### `Main.java`

- ʾ���ͻ��˴��룬��ʾ�˲�ͬ��ǿ�����µĴ�����Ϊ��
- ʹ�������ֲ�ͬ�� `MyHandler` ʵ�֣�
    - `PrintFuctionName`����ӡ��������
    - `PrintFunctionName1`
      ����ӡ����1���ٴ�ӡ��������
    - `LogHandler`
      ��ǰ�ô�ӡ `"before"`������ԭ���������ô�ӡ `"after"`��

---

### 3. ��̬����ʵ������

1. �û�ͨ�� `MyInterfaceFactory.createProxyObject(handler)` �����������
2. �����ද̬���� Java Դ�ļ���`.java`���������ݸ��� handler �� `functionBody()` ����ֵ��䷽���塣
3. Դ�ļ�������Ϊ `.class` �ļ���ͨ�� `Compiler.compile(javaFile)`����Ȼδչʾ����ʵ�֣���
4. ���������ɵ��࣬��ͨ�����䴴����ʵ����
5. ���� `myHandler.setProxy(proxy)` ���ô������֧��Ƕ�״���������ǿ�߼���
6. ���շ�����ǿ��Ĵ�����󹩵��á�

---

### 4. �ص�������

- **����Ը�**��ͨ����ͬ�� `MyHandler` ʵ�ֿ������Ʒ�������ǰ��Ĵ����߼���
- **����չ��ǿ**��������������µ���ǿ���ԣ��������޸����д��롣
- **ģ��AOP˼��**���������������������̣�AOP����˼�룬������־��Ȩ�޿��ƵȺ��й�ע��Ľ��

---

### 5. ���ܸĽ���

| �Ľ����� | ���� |
|----------|------|
| ���빤�� | `Compiler.compile` ����δչʾʵ�֣����������ⲿ������ `javac` �� `JavaCompiler` API |
| ������ | ��������Ĵ����鲻�����ƣ����ظ��������﷨����� |
| �����Ż� | ��̬�����ࡢд�����ļ���������̿���Ӱ�����ܣ��ɿ��ǻ������ |

---

### 6. ���ó���

- ��Ҫ������ʱ��̬��ǿ������Ϊ�ĳ�����
- ѧϰ AOP������ģʽ������ػ��Ƶ�ԭ���ʾ����Ŀ��
- �Զ���������������������ط������á�

---

### �ܽ�

����һ��ͨ��**��̬���� Java ��ʵ�ִ������**��С��ʵ������Ŀ��չʾ�����ͨ���ַ���ƴ������Դ�롢���롢����ء�����ȼ���ʵ�ַ�����ǿ�߼�������һ���Ľ�ѧ�������չǱ����

## �ʼ�

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