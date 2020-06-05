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

            if (realClassName.startsWith("java")) {
            	return null;
            	
            }

            if (realClassName.startsWith("sun.nio.ch")||realClassName.startsWith("sun.nio.cs")||realClassName.startsWith("sun.text.normalizer")){
            	return null;
            	
            }

               
           if (realClassName.startsWith("jdk.internal.logger")) {
            	return null;
            	
            }

            if (realClassName.startsWith("sun.util.logging")) {
            	return null;
            	
            } 


            if (realClassName.startsWith("sun.util.locale")||realClassName.startsWith("sun.util.locale")) {
            	return null;
            	
            }             

            if(realClassName.startsWith("sun.security")){
            	return null;
            }

            if(realClassName.startsWith("com.sun.crypto.provider")){
            	return null;
            }

            if(realClassName.startsWith("jdk.internal.reflect")){
            	return null;
            }

            if(realClassName.startsWith("sun.reflect.generics")){
            	return null;
            }

            if(realClassName.startsWith("sun.util.calendar")){
            	return null;
            }


            // if(realClassName.startsWith("jdk.internal.reflect")){
            // 	return null;
            // }
              
              
           


     

            //  if(realClassName.startsWith("java")){
            //  	return null;
            //  }


			CtClass ctClass=classPool.getOrNull(realClassName);
			if (ctClass==null){
				System.out.println(realClassName +"不存在");
				return null;
			}
			
			if (ctClass.isInterface()) {
				System.out.println("接口不处理");
				return  null;
			}

			CtMethod[] methods = ctClass.getDeclaredMethods();
			for (CtMethod method : methods) {
				if (method.isEmpty()) {
					continue;
				}
				StringBuilder sb = new StringBuilder();
				// sb.append("System.out.print(\"params:[\");");
				// int length =0;
				// try {
				// 	length=method.getParameterTypes().length;
				// }catch (Exception e){
				// 	//e.printStackTrace();
				// 	System.out.println("参数不存在");
				// 	continue;
				// }
				// for (int i = 0; i < length; i++) {
				// 	//sb.append("new Throwable().printStackTrace();");
				// 	sb.append("System.out.print($" + (i + 1) + ");");
				// 	sb.append("System.out.print(\",\");");
				// }
				// sb.append("System.out.print(\"];class:\");");
				//sb.append("String className=$class.getName();");
				//获取方法sb.append("")
				sb.append("System.out.println(this.getClass().getName());");
				// System.out.println(sb.toString());
				try {
					method.insertBefore(sb.toString());
				}catch (Exception e){
					//e.printStackTrace();
                    continue;
				}
			}
			try {
				return ctClass.toBytecode();
			}catch (Exception e){
				//e.printStackTrace();
               return  null;
			}
         }catch(Exception e){
         	return null;
         }

		});
		System.out.println("agent premain end");
	}
}
