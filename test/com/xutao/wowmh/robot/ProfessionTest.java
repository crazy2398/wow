package com.xutao.wowmh.robot;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class ProfessionTest {
	private final String testTarget;

	private final Profession expecting;

	public ProfessionTest(String testTarget, Profession expecting) {
		this.testTarget = testTarget;
		this.expecting = expecting;
	}
	
    @Parameters  
    public static Collection<?> usernameData() {  
  
        return Arrays.asList(new Object[][] {
        	{ "潜行者",  Profession.Bandit},
        	{ "潜",  Profession.Bandit},
        	{ "行者",  Profession.Bandit},
        	{ "潜abc",  Profession.Bandit},
        	{ "a行者",  Profession.Bandit},
        	{ "",  null},
        	{ null,  null},
        	{ "12345",  null},
        	{ "猎",  null},
        	{ "猎人",  Profession.Hunter},
        	{ "死亡骑士",  Profession.DeathKnight},
        	{ "圣骑士",  Profession.Paladin},
        	{ "骑士",  null},
        	{ "牧师",  Profession.Priest},
        	{ "牧",  Profession.Priest},
        	{ "战",  Profession.Brave},
        	{ "法",  Profession.Mage},
        	{ "术",  Profession.Warlock},
        	{ "士",  null},
        	{ "师",  null}
        	
        });  
  
    } 
    
    @Test  
    public void byAnyWordsTest() { 
    	Assert .assertSame(expecting, Profession.byAnyWords(testTarget));
    }
    
}
