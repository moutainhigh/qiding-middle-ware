package com.qiding.agenet;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

import java.lang.instrument.Instrumentation;
import java.util.List;

public class AgentMain {
	public static void premain(String args, Instrumentation inst) {
		System.out.println("agent premain begin");
		//需要修改的类
//		String cname="org.apache.http.impl.client.InternalHttpClient";
//		String cname2="org.apache.http.impl.client.MinimalHttpClient";
		ClassPool classPool = ClassPool.getDefault();
		//添加字节码转换器
		inst.addTransformer((loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {
			try {
				String realClassName = className.replace("/", ".");
                               // System.out.println(className+"======="+realClassName);
                                if( realClassName.startsWith("com.sun")||realClassName.startsWith("apple")||realClassName.startsWith("com.apple")||realClassName.startsWith("java")||realClassName.startsWith("jdk")||realClassName.startsWith("sun")){
                                 //   System.out.println(realClassName+"startsWith java pass");
                                    return null;
                                }
				//System.out.println(realClassName);
				CtClass ctClass = classPool.getCtClass(realClassName);
				CtMethod[] methods = ctClass.getDeclaredMethods();
				for (CtMethod method : methods) {
					StringBuilder sb = new StringBuilder();
					sb.append("System.out.print(\"params:[\");");
//					int length = method.getMethodInfo().getCodeAttribute().getAttributes().size();
					int length=method.getParameterTypes().length;
					for (int i = 0; i <length; i++) {
						sb.append("System.out.print($" + (i + 1) + ");");
						sb.append("System.out.print(\",\");");
					}
					sb.append("System.out.print(\"];class:\");");
					sb.append("String className=$class.getName();");
					//获取方法sb.append("")
					sb.append("System.out.println(className);");
                                       // System.out.println(sb.toString());
					method.insertBefore(sb.toString());
					//					StringBuilder body=new StringBuilder();
//					body.append("System.err.print(\"params<<\");");
//					body.append("System.err.print($$);");
//					body.append("System.err.print(\">>\");");
//					body.append("return $proceed($$);");
//					method.setBody(body.toString());
				}
				return ctClass.toBytecode();
			} catch (Exception e) {
			  // e.printStackTrace();
			}
			//System.out.println(className);
			//if(className.replace("/",".").equals(cname)||className.replace("/",".").equals(cname2)){
			//开始修改原有的类
//			 	  classPool.insertClassPath(new LoaderClassPath(loader));
//				 //
//				 try {
//					 CtClass ctClass=classPool.getCtClass(cname);
//					 System.out.println(new String(ctClass.toBytecode()));
//					 CtMethod[] methods=ctClass.getDeclaredMethods();
//					 for(CtMethod method:methods){
//						 method.insertBefore("System.err.println($1);\nSystem.err.println(System.currentTimeMillis());");
//					 }
//					 return ctClass.toBytecode();
//				 } catch (Exception e) {
//					 e.printStackTrace();
//				 }
			//}
			//System.out.println(className);
			return null;
		});
		System.out.println("agent premain end");
	}
}
