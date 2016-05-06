//package com.ai.appframe2.common;
//
//import com.ai.appframe2.util.StringUtils;
//import com.ai.appframe2.util.locale.AppframeLocaleFactory;
//import com.sun.tools.javac.Main;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.PrintWriter;
//import java.lang.reflect.Method;
//import java.net.URL;
//import java.net.URLClassLoader;
//import java.rmi.RemoteException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Comparator;
//import java.util.List;
//import javassist.ClassPool;
//import javassist.CtClass;
//import javassist.CtMethod;
//import javassist.NotFoundException;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//public class ClassLoaderUtil
//{
//  private static transient Log log = LogFactory.getLog(ClassLoaderUtil.class);
//  public static String tmpDir = "AppframeTemp";
//  public static String CLASS_PATH = null;
//  
//  static {
//    try {
//      deleteTempDir();
//      File parentDir = new File(System.getProperty("user.dir") + "/" + tmpDir);
//      if (!parentDir.exists()) {
//        parentDir.mkdir();
//      }
//    }
//    catch (Throwable e) {
//      log.error(e.getMessage(), e);
//    }
//  }
//  
//  public String getTmpDir() {
//    return System.getProperty("user.dir") + "/" + tmpDir;
//  }
//  
//  public static Class parentLoadClass(ClassLoader loader, String name) throws ClassNotFoundException
//  {
//    Class clasz = null;
//    if (clasz == null) {
//      try {
//        clasz = loader.getClass().getClassLoader().loadClass(name);
//      }
//      catch (Throwable e) {}
//    }
//    if (clasz == null) {
//      try {
//        clasz = Thread.currentThread().getContextClassLoader().loadClass(name);
//      } catch (Throwable e) {}
//    }
//    return clasz;
//  }
//  
//  public static Class findLoadedClass(ClassLoader loader, String name) throws ClassNotFoundException {
//    Method m = null;
//    try {
//      m = ClassLoader.class.getDeclaredMethod("findLoadedClass", new Class[] { String.class });
//      
//      m.setAccessible(true);
//      Class result = (Class)m.invoke(loader, new Object[] { name });
//      if (result == null) {
//        result = (Class)m.invoke(loader.getClass().getClassLoader(), new Object[] { name });
//      }
//      if (result == null) {
//        result = (Class)m.invoke(Thread.currentThread().getContextClassLoader(), new Object[] { name });
//      }
//      return result;
//    }
//    catch (Exception ex) {
//      log.error(ex.getMessage(), ex);
//      throw new ClassNotFoundException(ex.getMessage());
//    }
//    finally {
//      if (m != null) {
//        m.setAccessible(false);
//      }
//    }
//  }
//  
//  public static byte[] enhanceClass(ClassPool pool, String className) {
//    try {
//      CtClass implClass = pool.get(className);
//      
//      if (implClass.isInterface() == true) {
//        return null;
//      }
//      CtMethod[] methods = implClass.getDeclaredMethods();
//      
//      for (int i = 0; i < methods.length; i++) {
//        CtMethod tmpMethod = methods[i];
//        String newFunction = getProxyMethodSource(implClass, tmpMethod);
//        
//        tmpMethod.setName(tmpMethod.getName() + "$impl");
//        CtMethod ctMethod = CtMethod.make(newFunction, implClass);
//        implClass.addMethod(ctMethod);
//      }
//      
//      return implClass.toBytecode();
//    }
//    catch (Throwable ex) {
//      log.error(AppframeLocaleFactory.getResource("com.ai.appframe2.common.ClassLoaderUtil.enhance_error", new String[] { className }), ex);
//      
//      if ((ex instanceof RuntimeException)) throw ((RuntimeException)ex);
//      throw new RuntimeException(ex);
//    }
//  }
//  
//  public static Class[] sortExceptions(Class[] exceptions) {
//    Arrays.sort(exceptions, new Comparator() {
//      public int compare(Object o1, Object o2) {
//        if (o1.equals(o2))
//          return 0;
//        if (((Class)o1).isAssignableFrom((Class)o2)) {
//          return 1;
//        }
//        return -1;
//      }
//      
//      public boolean equals(Object obj) {
//        String msg = AppframeLocaleFactory.getResource("com.ai.appframe2.no_impl");
//        throw new RuntimeException(msg + "equals(Object obj)");
//      }
//    });
//    return exceptions;
//  }
//  
//  public static CtClass[] sortExceptions(CtClass[] exceptions) {
//    Arrays.sort(exceptions, new Comparator() {
//      public int compare(Object o1, Object o2) {
//        CtClass c1 = (CtClass)o1;
//        CtClass c2 = (CtClass)o2;
//        if (o1.equals(o2)) {
//          return 0;
//        }
//        for (;;) {
//          CtClass parent;
//          try {
//            parent = c1.getSuperclass();
//          }
//          catch (NotFoundException ex) {
//            throw new RuntimeException(ex);
//          }
//          if (parent == null) {
//            break;
//          }
//          if (parent.equals(c2))
//            return -1;
//          c1 = parent;
//        }
//        return 1;
//      }
//      
//      public boolean equals(Object obj) {
//        String msg = AppframeLocaleFactory.getResource("com.ai.appframe2.no_impl");
//        throw new RuntimeException(msg + "equals(Object obj)");
//      }
//    });
//    return exceptions;
//  }
//  
//  public static String getMethodStatement(String className, CtMethod m) throws Exception
//  {
//    StringBuilder buffer = new StringBuilder();
//    buffer.append(className).append(".").append(m.getName());
//    CtClass[] pList = m.getParameterTypes();
//    buffer.append("(");
//    for (int i = 0; i < pList.length; i++) {
//      if (i > 0) {
//        buffer.append(",");
//      }
//      buffer.append(pList[i].getName());
//    }
//    buffer.append(")");
//    return buffer.toString();
//  }
//  
//  public static String getProxyMethodSource(CtClass ctProxyClass, CtMethod method) throws Exception {
//    StringBuilder sb = new StringBuilder();
//    
//    sb.append(DataType.getModifyName(method.getModifiers()) + " ");
//    sb.append(DataType.getClassName(method.getReturnType().getName()) + " ");
//    sb.append(method.getName() + "(");
//    CtClass[] parameterClazz = method.getParameterTypes();
//    boolean hasResult = true;
//    if (method.getReturnType().getName().equals("void")) {
//      hasResult = false;
//    }
//    
//    String tmpCallVarStr = "";
//    for (int j = 0; j < parameterClazz.length; j++) {
//      if (j > 0) {
//        sb.append(",");
//        tmpCallVarStr = tmpCallVarStr + ",";
//      }
//      tmpCallVarStr = tmpCallVarStr + "var" + j;
//      sb.append(DataType.getClassName(parameterClazz[j].getName()) + " " + "var" + j);
//    }
//    sb.append(")");
//    
//    CtClass[] exceptionClazz = method.getExceptionTypes();
//    sortExceptions(exceptionClazz);
//    if ((exceptionClazz != null) && (exceptionClazz.length != 0)) {
//      sb.append("throws ");
//      for (int j = 0; j < exceptionClazz.length; j++) {
//        sb.append(exceptionClazz[j].getName());
//        if (j != exceptionClazz.length - 1) {
//          sb.append(",");
//        }
//      }
//    }
//    
//    sb.append("{\n");
//    
//
//
//
//
//    sb.append("String[] argTypes = new String[" + parameterClazz.length + "];\n");
//    sb.append("Object[] args = new Object[" + parameterClazz.length + "];\n");
//    sb.append("String returnType = \"" + DataType.getClassName(method.getReturnType().getName()) + "\";\n");
//    
//    for (int j = 0; j < parameterClazz.length; j++) {
//      sb.append("argTypes[" + j + "] = \"" + DataType.getClassName(parameterClazz[j].getName()) + "\";\n");
//      if (parameterClazz[j].isPrimitive() == true) {
//        sb.append("args[" + j + "] = new " + DataType.getPrimitiveClass(parameterClazz[j].getName()) + "(var" + j + ");\n");
//      }
//      else {
//        sb.append("args[" + j + "] = var" + j + ";\n");
//      }
//    }
//    
//    sb.append("Object result = null;\n");
//    sb.append("Throwable resultException = null;\n");
//    if (hasResult == true) {
//      sb.append(DataType.getClassName(method.getReturnType().getName()) + " resultReal;\n");
//    }
//    
//
//
//    sb.append("try{\n");
//    
//    if (hasResult == true) {
//      sb.append("resultReal = " + method.getName() + "$impl(" + tmpCallVarStr + ");\n");
//      if (method.getReturnType().isPrimitive()) {
//        sb.append("result = new " + DataType.getPrimitiveClass(method.getReturnType().getName()) + "(resultReal);\n");
//
//      }
//      else
//      {
//        sb.append("result = resultReal;\n");
//      }
//      
//
//
//      sb.append("return resultReal;\n");
//
//    }
//    else
//    {
//      sb.append(method.getName() + "$impl(" + tmpCallVarStr + ");\n");
//    }
//    boolean hasThrowable = false;
//    for (int i = 0; i < exceptionClazz.length; i++)
//    {
//      if (!exceptionClazz[i].getName().equalsIgnoreCase(RemoteException.class.getName()))
//      {
//
//
//        sb.append("}catch(" + exceptionClazz[i].getName() + " e" + i + " ){\n");
//        sb.append("resultException = e" + i + ";\n");
//        
//
//        sb.append("throw e" + i + ";\n");
//        if (exceptionClazz[i].getName().equals(Throwable.class.getName()))
//          hasThrowable = true;
//      }
//    }
//    if (!hasThrowable) {
//      sb.append("}catch(Throwable e ){\n");
//      sb.append("resultException = e;\n");
//      
//
//      sb.append("if(e instanceof RuntimeException){\n throw e ;\n} else {\n throw new RuntimeException(e);\n};\n");
//    }
//    sb.append("}");
//    
//
//
//
//
//    sb.append("}\n");
//    return sb.toString();
//  }
//  
//  public static Class loadClass(String name) throws ClassNotFoundException {
//    Class result = null;
//    try {
//      result = Thread.currentThread().getContextClassLoader().loadClass(name);
//    }
//    catch (ClassNotFoundException ex) {}
//    
//
//    if (result == null) {
//      result = Class.forName(name);
//    }
//    return result;
//  }
//  
//  public static String getClassPath() {
//    if (CLASS_PATH != null) {
//      return CLASS_PATH;
//    }
//    StringBuilder sb = new StringBuilder();
//    ClassLoader parent = ClassLoaderUtil.class.getClassLoader();
//    List tmpClassLoader = new ArrayList();
//    List urls = new ArrayList();
//    
//    while ((parent != null) && 
//      (!tmpClassLoader.contains(parent)))
//    {
//
//      if ((parent instanceof URLClassLoader)) {
//        if (log.isDebugEnabled()) {
//          log.debug("ClassLoader:" + parent.getClass().getName());
//        }
//        URLClassLoader uL = (URLClassLoader)parent;
//        URL[] path = uL.getURLs();
//        if (path != null) {
//          for (int i = 0; i < path.length; i++) {
//            sb.append(File.pathSeparator).append(path[i].getPath());
//            urls.add(path[i].getFile());
//            
//            if (log.isDebugEnabled()) {
//              log.debug(path[i].getPath());
//            }
//            
//          }
//        }
//      }
//      else if (log.isDebugEnabled())
//      {
//        log.debug(AppframeLocaleFactory.getResource("com.ai.appframe2.common.ClassLoaderUtil.handle_classloader_error", new String[] { parent.getClass().getName() }));
//      }
//      
//
//      tmpClassLoader.add(parent);
//      parent = parent.getParent();
//    }
//    parent = Thread.currentThread().getContextClassLoader();
//    while ((parent != null) && 
//      (!tmpClassLoader.contains(parent)))
//    {
//
//      if ((parent instanceof URLClassLoader)) {
//        if (log.isDebugEnabled()) {
//          log.debug("ClassLoader:" + parent.getClass().getName());
//        }
//        
//        URLClassLoader uL = (URLClassLoader)parent;
//        URL[] path = uL.getURLs();
//        if (path != null) {
//          for (int i = 0; i < path.length; i++) {
//            sb.append(File.pathSeparator).append(path[i].getPath());
//            urls.add(path[i].getFile());
//            
//            if (log.isDebugEnabled()) {
//              log.debug(path[i].getPath());
//            }
//            
//          }
//        }
//      }
//      else if (log.isDebugEnabled())
//      {
//        log.debug(AppframeLocaleFactory.getResource("com.ai.appframe2.common.ClassLoaderUtil.handle_classloader_error", new String[] { parent.getClass().getName() }));
//      }
//      
//      tmpClassLoader.add(parent);
//      parent = parent.getParent();
//    }
//    String path = Util.getResource(ClassLoaderUtil.class, "com/ai/appframe2/service/proxy/BaseProxyCreate.class").getPath();
//    if (path.indexOf("jar") < 0) {
//      path = Util.getResource(LogFactory.class, "org/apache/commons/logging/LogFactory.class").getPath();
//    }
//    path = path.replaceAll("%3A", ":");
//    if (log.isDebugEnabled()) {
//      log.debug(AppframeLocaleFactory.getResource("com.ai.appframe2.common.ClassLoaderUtil.path", new String[] { path }));
//    }
//    int index = path.indexOf("!");
//    if (index > 0) {
//      String tmp = path.substring(0, index);
//      index = tmp.lastIndexOf("/");
//      if (index == -1) {
//        index = tmp.lastIndexOf(File.separator);
//      }
//      tmp = tmp.substring(0, index);
//      if (!urls.contains(tmp)) {
//        try {
//          String[] files = com.ai.appframe2.util.FileUtils.getFileArray(tmp, new String[] { "jar" }, true);
//          
//          for (int i = 0; i < files.length; i++) {
//            sb.append(File.pathSeparator).append(files[i]);
//            log.debug(files[i]);
//          }
//        }
//        catch (Exception e) {
//          log.error(e.getMessage(), e);
//        }
//      }
//    }
//    path = Util.getResource(ClassLoaderUtil.class, "AIConfig.xml").getPath();
//    path = path.replaceAll("%3A", ":");
//    if (log.isDebugEnabled()) {
//      log.debug(AppframeLocaleFactory.getResource("com.ai.appframe2.common.ClassLoaderUtil.path", new String[] { path }));
//    }
//    index = path.indexOf("!");
//    if (index > 0) {
//      String tmp = path.substring(0, index);
//      if (log.isDebugEnabled()) {
//        log.debug(tmp);
//      }
//      
//      sb.append(File.pathSeparator).append(tmp);
//      if (!tmp.endsWith("jar")) {
//        tmp = tmp + File.separator + "WEB-INF" + File.separator + "classes";
//        log.debug(tmp);
//        sb.append(File.pathSeparator).append(tmp);
//      }
//    }
//    else {
//      index = path.indexOf("classes");
//      if (index > 0) {
//        String tmp = path.substring(0, index + 7);
//        log.debug(tmp);
//        sb.append(File.pathSeparator).append(tmp);
//      }
//    }
//    CLASS_PATH = sb.toString();
//    return CLASS_PATH;
//  }
//  
//  public static void writeJavaFile(String className, String javaCode) throws Exception
//  {
//    File dirFile = new File(System.getProperty("user.dir") + "/" + tmpDir);
//    int index = className.lastIndexOf(".");
//    if (index >= 0) {
//      String packageName = className.substring(0, index);
//      createDependDir(packageName);
//    }
//    
//    File file = new File(dirFile, className.replace('.', '/') + ".java");
//    
//    PrintWriter out = null;
//    try {
//      out = new PrintWriter(new FileOutputStream(file));
//      out.write(javaCode.toString());
//      out.flush();
//    }
//    catch (Exception e) {
//      log.error(e.getMessage(), e);
//    }
//    finally {
//      if (out != null) {
//        out.close();
//      }
//    }
//  }
//  
//  public static byte[] complieJavaCode(String className, String javaCode) throws Exception {
//    File dirFile = new File(System.getProperty("user.dir") + "/" + tmpDir);
//    writeJavaFile(className, javaCode);
//    StringBuilder sbClassPath = new StringBuilder();
//    sbClassPath.append(getClassPath()).append(File.pathSeparatorChar).append(System.getProperty("user.dir")).append(File.separator).append(tmpDir);
//    
//
//
//    String[] args = { "-classpath", sbClassPath.toString(), "-d", System.getProperty("user.dir") + "/" + tmpDir, tmpDir + "/" + className.replace('.', '/') + ".java" };
//    
//
//
//
//
//    int status = Main.compile(args);
//    
//    if (status != 0)
//    {
//      String msg = AppframeLocaleFactory.getResource("com.ai.appframe2.common.ClassLoaderUtil.compile_error");
//      throw new AIException(msg + className);
//    }
//    File classFile = new File(dirFile, className.replace('.', '/') + ".class");
//    
//    if (log.isDebugEnabled()) {
//      log.debug(AppframeLocaleFactory.getResource("com.ai.appframe2.common.ClassLoaderUtil.gen_proxy_error", new String[] { className }));
//    }
//    return org.apache.commons.io.FileUtils.readFileToByteArray(classFile);
//  }
//  
//  public static void createDependDir(String packageName)
//    throws Exception
//  {
//    File parentDir = new File(System.getProperty("user.dir") + "/" + tmpDir);
//    
//    String[] list = StringUtils.split(packageName, '.');
//    for (int i = 0; i < list.length; i++) {
//      parentDir = new File(parentDir, list[i]);
//      if (!parentDir.exists()) {
//        parentDir.mkdir();
//      }
//    }
//  }
//  
//  public static void deleteTempDir() throws Exception {
//    File parentDir = new File(System.getProperty("user.dir") + "/" + tmpDir);
//    if (parentDir.exists() == true) {
//      org.apache.commons.io.FileUtils.forceDelete(parentDir);
//    }
//  }
//  
//  public ClassLoaderUtil() {}
//}
//
