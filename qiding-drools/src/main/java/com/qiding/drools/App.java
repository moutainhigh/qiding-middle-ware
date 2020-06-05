package com.qiding.drools;

import org.drools.core.base.RuleNameEndsWithAgendaFilter;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.command.Command;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.Agenda;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.command.CommandFactory;
import org.kie.internal.ruleunit.RuleUnitUtil;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws InterruptedException {
        System.out.println( "Hello World!" );

        KieServices kieServices = KieServices.Factory.get();
		kieServices.newKieBaseConfiguration();

		KieContainer kieContainer =kieServices.getKieClasspathContainer();
		KieBase kieBase=kieContainer.getKieBase();







		KieSession kieSession = kieContainer.newKieSession("all-rules");











		//基本对象
		final HelloWorld qi=	HelloWorld.builder().age(100).name("qi").gone(true).build();
		final HelloWorld ding=	HelloWorld.builder().age(100).name("ding").gone(false).build();
		StatelessKieSession statelessKieSession = kieContainer.newStatelessKieSession("all-rules-2");
		statelessKieSession.execute(CommandFactory.newInsert(qi));
		qi.setGone(true);
		//数组对象
		statelessKieSession.execute(Arrays.asList(qi,ding));
		//批量处理
		List<Command> commands= Stream.of(
			CommandFactory.newInsert(HelloWorld.builder().age(100).name("qi").gone(false).build(),"qi"),
			CommandFactory.newInsert(HelloWorld.builder().age(100).name("qi").gone(false).build(),"ding")
		).collect(Collectors.toList());
		ExecutionResults results=statelessKieSession.execute(CommandFactory.newBatchExecution(commands));
		System.out.println(results.getValue("qi"));
		System.out.println(results.getValue("ding"));












		final HelloWorld helloWorld=	HelloWorld.builder().age(100).name("qi").gone(false).build();
		BlockingQueue<HelloWorld> blockingQueue=new ArrayBlockingQueue<HelloWorld>(3);

		new Thread(()->{
//			HelloWorld input= null;
//			try {
//				input = blockingQueue.take();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			kieSession.insert(helloWorld);
			FactHandle handle=kieSession.getFactHandle(helloWorld);
			kieSession.fireAllRules();
			helloWorld.setGone(true);
			FactHandle handle2=kieSession.getFactHandle(helloWorld);
			System.out.println("11111");
		}).start();

		TimeUnit.SECONDS.sleep(5);
		helloWorld.setGone(false);

		Agenda agenda= kieSession.getAgenda();
		agenda.getAgendaGroup("ruleA-group").setFocus();
		kieSession.fireAllRules();

		kieSession.fireAllRules(new RuleNameEndsWithAgendaFilter("ruleA"));


		System.out.println("更新状态成功");


		TimeUnit.SECONDS.sleep(6);

		HelloWorld	helloWorld2=	HelloWorld.builder().age(100).name("ding").gone(false).build();
		kieSession.insert(helloWorld2);
		kieSession.fireAllRules();

		TimeUnit.SECONDS.sleep(7);


    }
}
