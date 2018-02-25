package com.xutao.wowmh.robot;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class ClassesTest {
	private final String testTarget;

	private final Classes expecting;

	public ClassesTest(String testTarget, Classes expecting) {
		this.testTarget = testTarget;
		this.expecting = expecting;
	}
	
    @Parameters  
    public static Collection<?> usernameData() {  
  
        return Arrays.asList(new Object[][] {
        	{ "潜行者",  Classes.Rogue},
        	{ "潜",  Classes.Rogue},
        	{ "行者",  Classes.Rogue},
        	{ "潜abc",  Classes.Rogue},
        	{ "a行者",  Classes.Rogue},
        	{ "",  null},
        	{ null,  null},
        	{ "12345",  null},
        	{ "猎",  null},
        	{ "猎人",  Classes.Hunter},
        	{ "死亡骑士",  Classes.DeathKnight},
        	{ "圣骑士",  Classes.Paladin},
        	{ "骑士",  null},
        	{ "牧师",  Classes.Priest},
        	{ "牧",  Classes.Priest},
        	{ "战",  Classes.Brave},
        	{ "法",  Classes.Mage},
        	{ "术",  Classes.Warlock},
        	{ "士",  null},
        	{ "师",  null}
        	
        });  
  
    } 
    
    @Test  
    public void byAnyWordsTest() { 
    	Assert .assertSame(expecting, Classes.byAnyWords(testTarget));
    }
    
}
