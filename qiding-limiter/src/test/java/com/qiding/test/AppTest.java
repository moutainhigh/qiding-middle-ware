package com.qiding.test;

import static org.junit.Assert.assertTrue;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
	public void  testSplit(){
    	String domains="ding.com,qi.comg";
		List<String> infoList=Splitter.onPattern("(, |,| )")
			.omitEmptyStrings()
			.splitToList(domains)
			.stream()
			.collect(ImmutableList.toImmutableList());
		System.out.println(infoList);
	}

}
